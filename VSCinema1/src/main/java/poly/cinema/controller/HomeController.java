package poly.cinema.controller;
import poly.cinema.dao.OrderDAO;
import poly.cinema.dao.PaymentDAO;
import poly.cinema.dao.SaleDAO;
import poly.cinema.entity.Sale;
import poly.cinema.entity.Show;
import poly.cinema.entity.Ticket;
import poly.cinema.payment.Config;
import poly.cinema.service.SaleService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import poly.cinema.dao.ShowDAO;
import poly.cinema.entity.Cinema;
import poly.cinema.entity.Movie;
import poly.cinema.entity.Order;
import poly.cinema.entity.OrderFood;
import poly.cinema.entity.Payment;
import poly.cinema.service.MailerService;
import poly.cinema.service.MovieService;
import poly.cinema.service.ShowService;

@Controller
public class HomeController {

	@Autowired
	HttpSession session;
	
	@Autowired
	ShowDAO showDao;
	
	@Autowired 
	MovieService movieService;
	
	@Autowired 
	ShowService showService;
	
	@Autowired 
	SaleService saleService;
	
	@Autowired
	SaleDAO saleDao;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	OrderDAO orderDAO;
	
	@Autowired
	PaymentDAO paymentDAO;
	
	@Autowired
	MailerService mailerService;
	
	@RequestMapping({ "/admin", "/admin/home/index" })
	public String admin() {
		return "redirect:/assets/admin/index.html";
	}

	@RequestMapping("/home")
	public String home(Model model) { 
		List<Movie> movie = movieService.findMovieByShowdate();
		model.addAttribute("movies", movie);
		model.addAttribute("title", "Trang chủ");
		List<Sale> sale = saleService.findAll();
		model.addAttribute("sales", sale);
		List<Movie> comingsoon = movieService.findComingSoonMovies();
		model.addAttribute("comingsoon", comingsoon);

		return "home/index";
	}
	
	@RequestMapping("/security/login/form")
	public String login() {
		return "account/login";
	}
   
	
	@RequestMapping("/security/signup/form")
	public String signup() {
		return "account/signup";
	}

	@RequestMapping("/security/update/form")
	public String update() {
		return "account/update_account";
	}

	@RequestMapping("/security/change/form")
	public String changePassword() {
		return "account/change_password";
	}

	@RequestMapping("/security/token/change")
	public String tokenChangPassword() {
		return "account/change_token";
	}

	@RequestMapping("/security/forgot/form")
	public String forgotPassword() {
		return "account/forgot_password";
	}

	@RequestMapping("/security/token/forgot")
	public String tokenForgotPassword() {
		return "account/forgot_token";
	}

	@RequestMapping("/security/login/success")
	public String loginSuccess(Model model) {
		model.addAttribute("message", "Đăng nhập thành công!");
		return "redirect:/home";
	}

	@RequestMapping("/security/logoff/success")
	public String logoff(Model model) {
		return "redirect:/home";
	}
	
	@RequestMapping("/security/login/error")
	public String loginError(Model model) {
		Object message = session.getAttribute("error");
		if (message != null) {
			model.addAttribute("message", message);
		} else {
			model.addAttribute("message", "Sai thông tin đăng nhập!");
		}
		return "account/login";
	}
	
	@RequestMapping("/home/success")
	public String paymentSuccess(Model model) throws UnsupportedEncodingException, ParseException {
		SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy"); 
		SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
		Map fields = new HashMap();
	    for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
	        //String fieldName = (String) params.nextElement();
	        //String fieldValue = request.getParameter(fieldName);
	        String fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
	        String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
	        if ((fieldValue != null) && (fieldValue.length() > 0)) {
	            fields.put(fieldName, fieldValue);
	        }
	    }
	    String vnp_SecureHash = request.getParameter("vnp_SecureHash");
	    if (fields.containsKey("vnp_SecureHashType")) {
	        fields.remove("vnp_SecureHashType");
	    }
	    if (fields.containsKey("vnp_SecureHash")) {
	        fields.remove("vnp_SecureHash");
	    }
	    String signValue = Config.hashAllFields(fields);
	    if (signValue.equals(vnp_SecureHash)) {
	        //Kiem tra chu ky OK
	        /* Kiem tra trang thai don hang trong DB: checkOrderStatus, 
	        - Neu trang thai don hang OK, tien hanh cap nhat vao DB, tra lai cho VNPAY RspCode=00
	        - Neu trang thai don hang (da cap nhat roi) => khong cap nhat vao DB, tra lai cho VNPAY RspCode=02
	         */
	        boolean checkOrderId = true; // vnp_TxnRef đơn hàng có tồn tại trong database merchant
	        boolean checkAmount = true; // vnp_Amount is valid  (so sánh số tiền VNPAY request và sô tiền của giao dịch trong database merchant)
	        boolean checkOrderStatus = true; // PaymnentStatus = 0 (pending)
	        if (checkOrderId) {
	            if (checkAmount) {
	                if (checkOrderStatus) {
	                    if ("00".equals(request.getParameter("vnp_ResponseCode"))) {
	                    	String vnp_TxnRef = request.getParameter("vnp_TxnRef");
	                    	String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
	                    	String vnp_TransactionNo = request.getParameter("vnp_TransactionNo");
	                    	String vnp_TransactionStatus = request.getParameter("vnp_TransactionStatus");
	                        Payment payment = paymentDAO.findByOrderID(vnp_TxnRef);
	                        Order order = orderDAO.findById(vnp_TxnRef).get();
	                        if(order.getTickets().size() == 0) {
	                        	if(payment.getVnp_Responsecode() == null && payment.getVnp_Transactionno() == null && payment.getVnp_Transactionstatus() == null) {
		                        	order.setActive(3);
		                        	String foods = "";
		                        	Cinema cinema = new Cinema();
		                        	for(OrderFood f:order.getOrderDetails()) {
		                        		cinema = f.getFood().getCinema();
		                        		foods+= f.getFood().getName()+" (SL: "+f.getQuantity()+")"+" | ";
		                        	}
		                        	payment.setVnp_Responsecode(vnp_ResponseCode);
			                        payment.setVnp_Transactionno(vnp_TransactionNo);
			                        payment.setVnp_Transactionstatus(vnp_TransactionStatus);
			                        orderDAO.save(order);
			                        paymentDAO.save(payment);
			                        try {
			                			mailerService.send("hieunnpc00795@fpt.edu.vn", "THÔNG TIN HÓA ĐƠN", "<!DOCTYPE html>\n"
			                					+ "<html>\n"
			                					+ "<head>\n"
			                					+ "<title>Movie Tickets - Code With Bishal</title>\n"
			                					+ "<link rel=\"stylesheet\"\n"
			                					+ "	href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css\" />\n"
			                					+ "	<style type=\"text/css\">\n"
			                					+ "		body{\n"
			                					+ "	margin: 0;\n"
			                					+ "	padding: 0;\n"
			                					+ "	font-family: 'Poppins', sans-serif;\n"
			                					+ "}\n"
			                					+ ".movie_card {\n"
			                					+ "color: #fff;\n"
			                					+ "height: 420px;\n"
			                					+ "margin: 50px auto;\n"
			                					+ "display: flex;\n"
			                					+ "max-width: 100%;\n"
			                					+ "background: linear-gradient(#373B44, #4286f4);\n"
			                					+ "box-shadow: 0 0 40px rgba(0,0,0,0.3);\n"
			                					+ "}\n"
			                					+ ".left {\n"
			                					+ "	width: 80%;\n"
			                					+ "}\n"
			                					+ "\n"
			                					+ ".datails {\n"
			                					+ "	width: 90%;\n"
			                					+ "	padding: 30px;\n"
			                					+ "	margin-top: -25px;\n"
			                					+ "}\n"
			                					+ ".c_right img {\n"
			                					+ "	right: 15px;\n"
			                					+ "	height: 390px;\n"
			                					+ "  width:70%;\n"
			                					+ "  background: #fff;\n"
			                					+ "}\n"
			                					+ ".head {\n"
			                					+ "	width: 100%;\n"
			                					+ "	display: flex;\n"
			                					+ "	justify-content: space-between;\n"
			                					+ "}\n"
			                					+ "\n"
			                					+ ".rated, .year, .genre, .time {\n"
			                					+ "	color: #fff;\n"
			                					+ "	padding: 10px;\n"
			                					+ "	font-weight: bold;\n"
			                					+ "	border-radius: 15px;\n"
			                					+ "}\n"
			                					+ "\n"
			                					+ ".rated {\n"
			                					+ "	box-shadow: 0 0 50px rgba(0, 0, 0, 0.568);\n"
			                					+ "}\n"
			                					+ "a {\n"
			                					+ "	color: black;\n"
			                					+ "	display: block;\n"
			                					+ "	text-decoration: none;\n"
			                					+ "	outline: none;\n"
			                					+ "}\n"
			                					+ ".buttons {\n"
			                					+ "	margin-left: -10px;\n"
			                					+ "}\n"
			                					+ "button, .rated, .play_btn{\n"
			                					+ "	background: linear-gradient(#b24591c9, #f15f7ac4);\n"
			                					+ "}\n"
			                					+ "button {\n"
			                					+ "	color: #fff;\n"
			                					+ "	border: none;\n"
			                					+ "	padding: 20px;\n"
			                					+ "	outline: none;\n"
			                					+ "	font-size: 12px;\n"
			                					+ "	margin-top: 30px;\n"
			                					+ "	margin-left: 5px;\n"
			                					+ "	border-radius: 12px;\n"
			                					+ "	box-shadow: 0 0 20px rgba(0, 0, 0, 0.616);\n"
			                					+ "	cursor: pointer;\n"
			                					+ "	transition: 200ms ease-in-out;\n"
			                					+ "}\n"
			                					+ "\n"
			                					+ "button:hover {\n"
			                					+ "	transform: scale(1.1);\n"
			                					+ "}\n"
			                					+ ".play_btn {\n"
			                					+ "	height: 100%;\n"
			                					+ "	margin: -394px auto;\n"
			                					+ "	position: relative;\n"
			                					+ "	text-align: center;\n"
			                					+ "	background: linear-gradient(#fc5c7c65, #6a82fb5b);\n"
			                					+ "	box-shadow: 0 0 50px rgba(0,0,0,0.2);\n"
			                					+ "}\n"
			                					+ ".fa-play-circle {\n"
			                					+ "	color: black;\n"
			                					+ "	font-size: 160px;\n"
			                					+ "	margin-top: 110px;\n"
			                					+ "	animation: bounce 1s infinite;\n"
			                					+ "}\n"
			                					+ ".c_right:hover{\n"
			                					+ "	cursor: pointer;\n"
			                					+ "	transform: scale(1.1);\n"
			                					+ "}\n"
			                					+ ".credits{\n"
			                					+ "  font-size: 20px;\n"
			                					+ "}\n"
			                					+ "@keyframes bounce {\n"
			                					+ "	from{opacity: 1;}\n"
			                					+ "	to{opacity: 0;}\n"
			                					+ "}\n"
			                					+ "@media (max-width: 700px){\n"
			                					+ "  .movie_card{\n"
			                					+ "    display: block;\n"
			                					+ "  }\n"
			                					+ "}\n"
			                					+ "@media (max-width: 600px){\n"
			                					+ "  button{\n"
			                					+ "    display: none;\n"
			                					+ "  }\n"
			                					+ "}\n"
			                					+ "	</style>\n"
			                					+ "</head>\n"
			                					+ "<!-- created by Code With Bishal - www.codewithbishal.com\n"
			                					+ "-->\n"
			                					+ "<body>\n"
			                					+ "	<div class=\"wrapper\">\n"
			                					+ "		<div class=\"movie_card\">\n"
			                					+ "			<div class=\"left\">\n"
			                					+ "				<div class=\"datails\">\n"
			                					+ "					<h1>THÔNG TIN HÓA ĐƠN</h1>\n"
			                					+ "					<div class=\"head\">\n"
			                					+ "						<p class=\"rated\">Thanh toán thành công</p>\n"
			                					+ "						<p class=\"year\"></p>\n"
			                					+ "						<p class=\"genre\"></p>\n"
			                					+ "						<p class=\"time\"> </p>\n"
			                					+ "						<p class=\"time\"></p>\n"
			                					+ "					</div>\n"
			                					+ "					<h3 class=\"read_more\">"+cinema.getName()+"</h3>\n"
			                					+ "					<p class=\"description\">"+cinema.getAddress()+"</p>\n"
			                					+ "					<hr>\n"	
			                					+ "					<p class=\"description\">"+foods+"</p>\n"
			                					+ "					<div class=\"buttons\">\n"
			                					+ "						<button>Mã hóa đơn: "+vnp_TxnRef+"</button>\n"
			                					+ "						<button>Khách hàng: "+order.getUser().getFullname()+"</button>\n"
			                					+ "						<button>Tổng tiền: "+order.getTotalmoney()+" VND</button>\n"
			                					+ "					</div>\n"
			                					+ "				</div>\n"
			                					+ "			</div>\n"
			                					+ "			<div class=\"c_right\">\n"
			                					+ "				<div class=\"play_btn\">\n"
			                					+ "					<a href=\"https://codewithbishal.com\" target=\"_blank\"\n"
			                					+ "						title=\"Play Trailer\"> <i class=\"fas fa-play-circle\"></i>\n"
			                					+ "					</a>\n"
			                					+ "				</div>\n"
			                					+ "			</div>\n"
			                					+ "		</div>\n"
			                					+ "	</div>\n"
			                					+ "</body>\n"
			                					+ "</html>");
			                		} catch (MessagingException e) {
			                			e.printStackTrace();
			                		}
		                        }
		                        String AlphaNumericString = payment.getVnp_Createdate();
		                		StringBuilder sb = new StringBuilder();
		                		for (int i = 0; i < 14; i++) {
		                            if(i == 4 || i == 6) {
		                            	sb.append('-');
		                            } else if(i == 8) {
		                            	sb.append(' ');
		                            } else if(i == 10 || i == 12) {
		                            	sb.append(':');
		                            }
		                            sb.append(AlphaNumericString.charAt(i));
		                        }
		                		String textTime = sb.toString();
		                		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		                		SimpleDateFormat formatterr = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		                		Date date = formatter.parse(textTime);
		                		String time = formatterr.format(date);
		                		model.addAttribute("time", time);
		                        model.addAttribute("payment", payment);
		                        return "/ticket/vnpay_return";
	                        } else {
	                        	if(payment.getVnp_Responsecode() == null && payment.getVnp_Transactionno() == null && payment.getVnp_Transactionstatus() == null) {
		                        	order.setActive(3);
		                        	String seats = "";
		                        	String foods = "";
		                        	Show show = new Show();
		                        	for(Ticket t:order.getTickets()) {
		                        		seats += t.getSeat().getName()+" ";
		                        		show = t.getShow();
		                        	}
		                        	for(OrderFood f:order.getOrderDetails()) {
		                        		foods+= f.getFood().getName()+" (SL: "+f.getQuantity()+")"+" | ";
		                        	}
		                        	String date = formatDate.format(show.getShowDate());
		                        	String start= formatTime.format(show.getStartTime());
		                        	String end = formatTime.format(show.getEndTime());
		                        	payment.setVnp_Responsecode(vnp_ResponseCode);
			                        payment.setVnp_Transactionno(vnp_TransactionNo);
			                        payment.setVnp_Transactionstatus(vnp_TransactionStatus);
			                        orderDAO.save(order);
			                        paymentDAO.save(payment);
			                        try {
			                			mailerService.send("hieunnpc00795@fpt.edu.vn", "THÔNG TIN HÓA ĐƠN", "<!DOCTYPE html>\n"
			                					+ "<html>\n"
			                					+ "<head>\n"
			                					+ "<title>Movie Tickets - Code With Bishal</title>\n"
			                					+ "<link rel=\"stylesheet\"\n"
			                					+ "	href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css\" />\n"
			                					+ "	<style type=\"text/css\">\n"
			                					+ "		body{\n"
			                					+ "	margin: 0;\n"
			                					+ "	padding: 0;\n"
			                					+ "	font-family: 'Poppins', sans-serif;\n"
			                					+ "}\n"
			                					+ ".movie_card {\n"
			                					+ "color: #fff;\n"
			                					+ "height: 420px;\n"
			                					+ "margin: 50px auto;\n"
			                					+ "display: flex;\n"
			                					+ "max-width: 100%;\n"
			                					+ "background: linear-gradient(#373B44, #4286f4);\n"
			                					+ "box-shadow: 0 0 40px rgba(0,0,0,0.3);\n"
			                					+ "}\n"
			                					+ ".left {\n"
			                					+ "	width: 80%;\n"
			                					+ "}\n"
			                					+ "\n"
			                					+ ".datails {\n"
			                					+ "	width: 90%;\n"
			                					+ "	padding: 30px;\n"
			                					+ "	margin-top: -25px;\n"
			                					+ "}\n"
			                					+ ".c_right img {\n"
			                					+ "	right: 15px;\n"
			                					+ "	height: 390px;\n"
			                					+ "  width:70%;\n"
			                					+ "  background: #fff;\n"
			                					+ "}\n"
			                					+ ".head {\n"
			                					+ "	width: 100%;\n"
			                					+ "	display: flex;\n"
			                					+ "	justify-content: space-between;\n"
			                					+ "}\n"
			                					+ "\n"
			                					+ ".rated, .year, .genre, .time {\n"
			                					+ "	color: #fff;\n"
			                					+ "	padding: 10px;\n"
			                					+ "	font-weight: bold;\n"
			                					+ "	border-radius: 15px;\n"
			                					+ "}\n"
			                					+ "\n"
			                					+ ".rated {\n"
			                					+ "	box-shadow: 0 0 50px rgba(0, 0, 0, 0.568);\n"
			                					+ "}\n"
			                					+ "a {\n"
			                					+ "	color: black;\n"
			                					+ "	display: block;\n"
			                					+ "	text-decoration: none;\n"
			                					+ "	outline: none;\n"
			                					+ "}\n"
			                					+ ".buttons {\n"
			                					+ "	margin-left: -10px;\n"
			                					+ "}\n"
			                					+ "button, .rated, .play_btn{\n"
			                					+ "	background: linear-gradient(#b24591c9, #f15f7ac4);\n"
			                					+ "}\n"
			                					+ "button {\n"
			                					+ "	color: #fff;\n"
			                					+ "	border: none;\n"
			                					+ "	padding: 20px;\n"
			                					+ "	outline: none;\n"
			                					+ "	font-size: 12px;\n"
			                					+ "	margin-top: 30px;\n"
			                					+ "	margin-left: 5px;\n"
			                					+ "	border-radius: 12px;\n"
			                					+ "	box-shadow: 0 0 20px rgba(0, 0, 0, 0.616);\n"
			                					+ "	cursor: pointer;\n"
			                					+ "	transition: 200ms ease-in-out;\n"
			                					+ "}\n"
			                					+ "\n"
			                					+ "button:hover {\n"
			                					+ "	transform: scale(1.1);\n"
			                					+ "}\n"
			                					+ ".play_btn {\n"
			                					+ "	height: 100%;\n"
			                					+ "	margin: -394px auto;\n"
			                					+ "	position: relative;\n"
			                					+ "	text-align: center;\n"
			                					+ "	background: linear-gradient(#fc5c7c65, #6a82fb5b);\n"
			                					+ "	box-shadow: 0 0 50px rgba(0,0,0,0.2);\n"
			                					+ "}\n"
			                					+ ".fa-play-circle {\n"
			                					+ "	color: black;\n"
			                					+ "	font-size: 160px;\n"
			                					+ "	margin-top: 110px;\n"
			                					+ "	animation: bounce 1s infinite;\n"
			                					+ "}\n"
			                					+ ".c_right:hover{\n"
			                					+ "	cursor: pointer;\n"
			                					+ "	transform: scale(1.1);\n"
			                					+ "}\n"
			                					+ ".credits{\n"
			                					+ "  font-size: 20px;\n"
			                					+ "}\n"
			                					+ "@keyframes bounce {\n"
			                					+ "	from{opacity: 1;}\n"
			                					+ "	to{opacity: 0;}\n"
			                					+ "}\n"
			                					+ "@media (max-width: 700px){\n"
			                					+ "  .movie_card{\n"
			                					+ "    display: block;\n"
			                					+ "  }\n"
			                					+ "}\n"
			                					+ "@media (max-width: 600px){\n"
			                					+ "  button{\n"
			                					+ "    display: none;\n"
			                					+ "  }\n"
			                					+ "}\n"
			                					+ "	</style>\n"
			                					+ "</head>\n"
			                					+ "<!-- created by Code With Bishal - www.codewithbishal.com\n"
			                					+ "-->\n"
			                					+ "<body>\n"
			                					+ "	<div class=\"wrapper\">\n"
			                					+ "		<div class=\"movie_card\">\n"
			                					+ "			<div class=\"left\">\n"
			                					+ "				<div class=\"datails\">\n"
			                					+ "					<h1>"+show.getMovie().getName()+"</h1>\n"
			                					+ "					<div class=\"head\">\n"
			                					+ "						<p class=\"rated\">"+show.getRoom().getName()+"</p>\n"
			                					+ "						<p class=\"year\">Ghế: "+seats+"</p>\n"
			                					+ "						<p class=\"genre\">"+date+" "+start+" | "+date+" "+end+"</p>\n"
			                					+ "						<p class=\"time\"> </p>\n"
			                					+ "						<p class=\"time\"></p>\n"
			                					+ "					</div>\n"
			                					+ "					<h3 class=\"read_more\">"+show.getRoom().getCinema().getName()+"</h3>\n"
			                					+ "					<p class=\"description\">"+show.getRoom().getCinema().getAddress()+"</p>\n"
			                					+ "					<hr>\n"	
			                					+ "					<p class=\"description\">"+foods+"</p>\n"
			                					+ "					<div class=\"buttons\">\n"
			                					+ "						<button>Mã hóa đơn: "+vnp_TxnRef+"</button>\n"
			                					+ "						<button>Khách hàng: "+order.getUser().getFullname()+"</button>\n"
			                					+ "						<button>Tổng tiền: "+order.getTotalmoney()+" VND</button>\n"
			                					+ "					</div>\n"
			                					+ "				</div>\n"
			                					+ "			</div>\n"
			                					+ "			<div class=\"c_right\">\n"
			                					+ "				<div class=\"play_btn\">\n"
			                					+ "					<a href=\"https://codewithbishal.com\" target=\"_blank\"\n"
			                					+ "						title=\"Play Trailer\"> <i class=\"fas fa-play-circle\"></i>\n"
			                					+ "					</a>\n"
			                					+ "				</div>\n"
			                					+ "			</div>\n"
			                					+ "		</div>\n"
			                					+ "	</div>\n"
			                					+ "</body>\n"
			                					+ "</html>");
			                		} catch (MessagingException e) {
			                			e.printStackTrace();
			                		}
		                        }
		                        String AlphaNumericString = payment.getVnp_Createdate();
		                		StringBuilder sb = new StringBuilder();
		                		for (int i = 0; i < 14; i++) {
		                            if(i == 4 || i == 6) {
		                            	sb.append('-');
		                            } else if(i == 8) {
		                            	sb.append(' ');
		                            } else if(i == 10 || i == 12) {
		                            	sb.append(':');
		                            }
		                            sb.append(AlphaNumericString.charAt(i));
		                        }
		                		String textTime = sb.toString();
		                		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		                		SimpleDateFormat formatterr = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		                		Date date = formatter.parse(textTime);
		                		String time = formatterr.format(date);
		                		model.addAttribute("time", time);
		                        model.addAttribute("payment", payment);
		                        return "/ticket/vnpay_return";
	                        }
	                    } else {
	                        if("04".equals(request.getParameter("vnp_TransactionStatus"))){
	                        	String vnp_TxnRef = request.getParameter("vnp_TxnRef");
		                    	String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
		                    	String vnp_TransactionNo = request.getParameter("vnp_TransactionNo");
		                    	String vnp_TransactionStatus = request.getParameter("vnp_TransactionStatus");
		                        Payment payment = paymentDAO.findByOrderID(vnp_TxnRef);
		                        Order order = orderDAO.findById(vnp_TxnRef).get();
		                        if(order.getTickets().size() == 0) {
		                        	if(payment.getVnp_Responsecode() == null && payment.getVnp_Transactionno() == null && payment.getVnp_Transactionstatus() == null) {
			                        	order.setActive(1);
			                        	String foods = "";
			                        	Cinema cinema = new Cinema();
			                        	for(OrderFood f:order.getOrderDetails()) {
			                        		cinema = f.getFood().getCinema();
			                        		foods+= f.getFood().getName()+" (SL: "+f.getQuantity()+")"+" | ";
			                        	}
			                        	payment.setVnp_Responsecode(vnp_ResponseCode);
				                        payment.setVnp_Transactionno(vnp_TransactionNo);
				                        payment.setVnp_Transactionstatus(vnp_TransactionStatus);
				                        orderDAO.save(order);
				                        paymentDAO.save(payment);
				                        try {
				                			mailerService.send("hieunnpc00795@fpt.edu.vn", "THÔNG TIN HÓA ĐƠN", "<!DOCTYPE html>\n"
				                					+ "<html>\n"
				                					+ "<head>\n"
				                					+ "<title>Movie Tickets - Code With Bishal</title>\n"
				                					+ "<link rel=\"stylesheet\"\n"
				                					+ "	href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css\" />\n"
				                					+ "	<style type=\"text/css\">\n"
				                					+ "		body{\n"
				                					+ "	margin: 0;\n"
				                					+ "	padding: 0;\n"
				                					+ "	font-family: 'Poppins', sans-serif;\n"
				                					+ "}\n"
				                					+ ".movie_card {\n"
				                					+ "color: #fff;\n"
				                					+ "height: 420px;\n"
				                					+ "margin: 50px auto;\n"
				                					+ "display: flex;\n"
				                					+ "max-width: 100%;\n"
				                					+ "background: linear-gradient(#373B44, #4286f4);\n"
				                					+ "box-shadow: 0 0 40px rgba(0,0,0,0.3);\n"
				                					+ "}\n"
				                					+ ".left {\n"
				                					+ "	width: 80%;\n"
				                					+ "}\n"
				                					+ "\n"
				                					+ ".datails {\n"
				                					+ "	width: 90%;\n"
				                					+ "	padding: 30px;\n"
				                					+ "	margin-top: -25px;\n"
				                					+ "}\n"
				                					+ ".c_right img {\n"
				                					+ "	right: 15px;\n"
				                					+ "	height: 390px;\n"
				                					+ "  width:70%;\n"
				                					+ "  background: #fff;\n"
				                					+ "}\n"
				                					+ ".head {\n"
				                					+ "	width: 100%;\n"
				                					+ "	display: flex;\n"
				                					+ "	justify-content: space-between;\n"
				                					+ "}\n"
				                					+ "\n"
				                					+ ".rated, .year, .genre, .time {\n"
				                					+ "	color: #fff;\n"
				                					+ "	padding: 10px;\n"
				                					+ "	font-weight: bold;\n"
				                					+ "	border-radius: 15px;\n"
				                					+ "}\n"
				                					+ "\n"
				                					+ ".rated {\n"
				                					+ "	box-shadow: 0 0 50px rgba(0, 0, 0, 0.568);\n"
				                					+ "}\n"
				                					+ "a {\n"
				                					+ "	color: black;\n"
				                					+ "	display: block;\n"
				                					+ "	text-decoration: none;\n"
				                					+ "	outline: none;\n"
				                					+ "}\n"
				                					+ ".buttons {\n"
				                					+ "	margin-left: -10px;\n"
				                					+ "}\n"
				                					+ "button, .rated, .play_btn{\n"
				                					+ "	background: linear-gradient(#b24591c9, #f15f7ac4);\n"
				                					+ "}\n"
				                					+ "button {\n"
				                					+ "	color: #fff;\n"
				                					+ "	border: none;\n"
				                					+ "	padding: 20px;\n"
				                					+ "	outline: none;\n"
				                					+ "	font-size: 12px;\n"
				                					+ "	margin-top: 30px;\n"
				                					+ "	margin-left: 5px;\n"
				                					+ "	border-radius: 12px;\n"
				                					+ "	box-shadow: 0 0 20px rgba(0, 0, 0, 0.616);\n"
				                					+ "	cursor: pointer;\n"
				                					+ "	transition: 200ms ease-in-out;\n"
				                					+ "}\n"
				                					+ "\n"
				                					+ "button:hover {\n"
				                					+ "	transform: scale(1.1);\n"
				                					+ "}\n"
				                					+ ".play_btn {\n"
				                					+ "	height: 100%;\n"
				                					+ "	margin: -394px auto;\n"
				                					+ "	position: relative;\n"
				                					+ "	text-align: center;\n"
				                					+ "	background: linear-gradient(#fc5c7c65, #6a82fb5b);\n"
				                					+ "	box-shadow: 0 0 50px rgba(0,0,0,0.2);\n"
				                					+ "}\n"
				                					+ ".fa-play-circle {\n"
				                					+ "	color: black;\n"
				                					+ "	font-size: 160px;\n"
				                					+ "	margin-top: 110px;\n"
				                					+ "	animation: bounce 1s infinite;\n"
				                					+ "}\n"
				                					+ ".c_right:hover{\n"
				                					+ "	cursor: pointer;\n"
				                					+ "	transform: scale(1.1);\n"
				                					+ "}\n"
				                					+ ".credits{\n"
				                					+ "  font-size: 20px;\n"
				                					+ "}\n"
				                					+ "@keyframes bounce {\n"
				                					+ "	from{opacity: 1;}\n"
				                					+ "	to{opacity: 0;}\n"
				                					+ "}\n"
				                					+ "@media (max-width: 700px){\n"
				                					+ "  .movie_card{\n"
				                					+ "    display: block;\n"
				                					+ "  }\n"
				                					+ "}\n"
				                					+ "@media (max-width: 600px){\n"
				                					+ "  button{\n"
				                					+ "    display: none;\n"
				                					+ "  }\n"
				                					+ "}\n"
				                					+ "	</style>\n"
				                					+ "</head>\n"
				                					+ "<!-- created by Code With Bishal - www.codewithbishal.com\n"
				                					+ "-->\n"
				                					+ "<body>\n"
				                					+ "	<div class=\"wrapper\">\n"
				                					+ "		<div class=\"movie_card\">\n"
				                					+ "			<div class=\"left\">\n"
				                					+ "				<div class=\"datails\">\n"
				                					+ "					<h1>THÔNG TIN HÓA ĐƠN</h1>\n"
				                					+ "					<div class=\"head\">\n"
				                					+ "						<p class=\"rated\">Thanh toán thành công</p>\n"
				                					+ "						<p class=\"year\"></p>\n"
				                					+ "						<p class=\"genre\"></p>\n"
				                					+ "						<p class=\"time\"> </p>\n"
				                					+ "						<p class=\"time\"></p>\n"
				                					+ "					</div>\n"
				                					+ "					<h3 class=\"read_more\">"+cinema.getName()+"</h3>\n"
				                					+ "					<p class=\"description\">"+cinema.getAddress()+"</p>\n"
				                					+ "					<hr>\n"	
				                					+ "					<p class=\"description\">"+foods+"</p>\n"
				                					+ "					<div class=\"buttons\">\n"
				                					+ "						<button>Mã hóa đơn: "+vnp_TxnRef+"</button>\n"
				                					+ "						<button>Khách hàng: "+order.getUser().getFullname()+"</button>\n"
				                					+ "						<button>Tổng tiền: "+order.getTotalmoney()+" VND</button>\n"
				                					+ "					</div>\n"
				                					+ "				</div>\n"
				                					+ "			</div>\n"
				                					+ "			<div class=\"c_right\">\n"
				                					+ "				<div class=\"play_btn\">\n"
				                					+ "					<a href=\"https://codewithbishal.com\" target=\"_blank\"\n"
				                					+ "						title=\"Play Trailer\"> <i class=\"fas fa-play-circle\"></i>\n"
				                					+ "					</a>\n"
				                					+ "				</div>\n"
				                					+ "			</div>\n"
				                					+ "		</div>\n"
				                					+ "	</div>\n"
				                					+ "</body>\n"
				                					+ "</html>");
				                		} catch (MessagingException e) {
				                			e.printStackTrace();
				                		}
			                        }
			                        String AlphaNumericString = payment.getVnp_Createdate();
			                		StringBuilder sb = new StringBuilder();
			                		for (int i = 0; i < 14; i++) {
			                            if(i == 4 || i == 6) {
			                            	sb.append('-');
			                            } else if(i == 8) {
			                            	sb.append(' ');
			                            } else if(i == 10 || i == 12) {
			                            	sb.append(':');
			                            }
			                            sb.append(AlphaNumericString.charAt(i));
			                        }
			                		String textTime = sb.toString();
			                		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			                		SimpleDateFormat formatterr = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			                		Date date = formatter.parse(textTime);
			                		String time = formatterr.format(date);
			                		model.addAttribute("time", time);
			                        model.addAttribute("payment", payment);
			                        return "/ticket/vnpay_return";
		                        } else {
		                        	if(payment.getVnp_Responsecode() == null && payment.getVnp_Transactionno() == null && payment.getVnp_Transactionstatus() == null) {
			                        	order.setActive(1);
			                        	String seats = "";
			                        	String foods = "";
			                        	Show show = new Show();
			                        	for(Ticket t:order.getTickets()) {
			                        		seats += t.getSeat().getName()+" ";
			                        		show = t.getShow();
			                        	}
			                        	for(OrderFood f:order.getOrderDetails()) {
			                        		foods+= f.getFood().getName()+" (SL: "+f.getQuantity()+")"+" | ";
			                        	}
			                        	String date = formatDate.format(show.getShowDate());
			                        	String start= formatTime.format(show.getStartTime());
			                        	String end = formatTime.format(show.getEndTime());
			                        	payment.setVnp_Responsecode(vnp_ResponseCode);
				                        payment.setVnp_Transactionno(vnp_TransactionNo);
				                        payment.setVnp_Transactionstatus(vnp_TransactionStatus);
				                        orderDAO.save(order);
				                        paymentDAO.save(payment);
				                        try {
				                			mailerService.send("hieunnpc00795@fpt.edu.vn", "THÔNG TIN HÓA ĐƠN", "<!DOCTYPE html>\n"
				                					+ "<html>\n"
				                					+ "<head>\n"
				                					+ "<title>Movie Tickets - Code With Bishal</title>\n"
				                					+ "<link rel=\"stylesheet\"\n"
				                					+ "	href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css\" />\n"
				                					+ "	<style type=\"text/css\">\n"
				                					+ "		body{\n"
				                					+ "	margin: 0;\n"
				                					+ "	padding: 0;\n"
				                					+ "	font-family: 'Poppins', sans-serif;\n"
				                					+ "}\n"
				                					+ ".movie_card {\n"
				                					+ "color: #fff;\n"
				                					+ "height: 420px;\n"
				                					+ "margin: 50px auto;\n"
				                					+ "display: flex;\n"
				                					+ "max-width: 100%;\n"
				                					+ "background: linear-gradient(#373B44, #4286f4);\n"
				                					+ "box-shadow: 0 0 40px rgba(0,0,0,0.3);\n"
				                					+ "}\n"
				                					+ ".left {\n"
				                					+ "	width: 80%;\n"
				                					+ "}\n"
				                					+ "\n"
				                					+ ".datails {\n"
				                					+ "	width: 90%;\n"
				                					+ "	padding: 30px;\n"
				                					+ "	margin-top: -25px;\n"
				                					+ "}\n"
				                					+ ".c_right img {\n"
				                					+ "	right: 15px;\n"
				                					+ "	height: 390px;\n"
				                					+ "  width:70%;\n"
				                					+ "  background: #fff;\n"
				                					+ "}\n"
				                					+ ".head {\n"
				                					+ "	width: 100%;\n"
				                					+ "	display: flex;\n"
				                					+ "	justify-content: space-between;\n"
				                					+ "}\n"
				                					+ "\n"
				                					+ ".rated, .year, .genre, .time {\n"
				                					+ "	color: #fff;\n"
				                					+ "	padding: 10px;\n"
				                					+ "	font-weight: bold;\n"
				                					+ "	border-radius: 15px;\n"
				                					+ "}\n"
				                					+ "\n"
				                					+ ".rated {\n"
				                					+ "	box-shadow: 0 0 50px rgba(0, 0, 0, 0.568);\n"
				                					+ "}\n"
				                					+ "a {\n"
				                					+ "	color: black;\n"
				                					+ "	display: block;\n"
				                					+ "	text-decoration: none;\n"
				                					+ "	outline: none;\n"
				                					+ "}\n"
				                					+ ".buttons {\n"
				                					+ "	margin-left: -10px;\n"
				                					+ "}\n"
				                					+ "button, .rated, .play_btn{\n"
				                					+ "	background: linear-gradient(#b24591c9, #f15f7ac4);\n"
				                					+ "}\n"
				                					+ "button {\n"
				                					+ "	color: #fff;\n"
				                					+ "	border: none;\n"
				                					+ "	padding: 20px;\n"
				                					+ "	outline: none;\n"
				                					+ "	font-size: 12px;\n"
				                					+ "	margin-top: 30px;\n"
				                					+ "	margin-left: 5px;\n"
				                					+ "	border-radius: 12px;\n"
				                					+ "	box-shadow: 0 0 20px rgba(0, 0, 0, 0.616);\n"
				                					+ "	cursor: pointer;\n"
				                					+ "	transition: 200ms ease-in-out;\n"
				                					+ "}\n"
				                					+ "\n"
				                					+ "button:hover {\n"
				                					+ "	transform: scale(1.1);\n"
				                					+ "}\n"
				                					+ ".play_btn {\n"
				                					+ "	height: 100%;\n"
				                					+ "	margin: -394px auto;\n"
				                					+ "	position: relative;\n"
				                					+ "	text-align: center;\n"
				                					+ "	background: linear-gradient(#fc5c7c65, #6a82fb5b);\n"
				                					+ "	box-shadow: 0 0 50px rgba(0,0,0,0.2);\n"
				                					+ "}\n"
				                					+ ".fa-play-circle {\n"
				                					+ "	color: black;\n"
				                					+ "	font-size: 160px;\n"
				                					+ "	margin-top: 110px;\n"
				                					+ "	animation: bounce 1s infinite;\n"
				                					+ "}\n"
				                					+ ".c_right:hover{\n"
				                					+ "	cursor: pointer;\n"
				                					+ "	transform: scale(1.1);\n"
				                					+ "}\n"
				                					+ ".credits{\n"
				                					+ "  font-size: 20px;\n"
				                					+ "}\n"
				                					+ "@keyframes bounce {\n"
				                					+ "	from{opacity: 1;}\n"
				                					+ "	to{opacity: 0;}\n"
				                					+ "}\n"
				                					+ "@media (max-width: 700px){\n"
				                					+ "  .movie_card{\n"
				                					+ "    display: block;\n"
				                					+ "  }\n"
				                					+ "}\n"
				                					+ "@media (max-width: 600px){\n"
				                					+ "  button{\n"
				                					+ "    display: none;\n"
				                					+ "  }\n"
				                					+ "}\n"
				                					+ "	</style>\n"
				                					+ "</head>\n"
				                					+ "<!-- created by Code With Bishal - www.codewithbishal.com\n"
				                					+ "-->\n"
				                					+ "<body>\n"
				                					+ "	<div class=\"wrapper\">\n"
				                					+ "		<div class=\"movie_card\">\n"
				                					+ "			<div class=\"left\">\n"
				                					+ "				<div class=\"datails\">\n"
				                					+ "					<h1>"+show.getMovie().getName()+"</h1>\n"
				                					+ "					<div class=\"head\">\n"
				                					+ "						<p class=\"rated\">"+show.getRoom().getName()+"</p>\n"
				                					+ "						<p class=\"year\">Ghế: "+seats+"</p>\n"
				                					+ "						<p class=\"genre\">"+date+" "+start+" | "+date+" "+end+"</p>\n"
				                					+ "						<p class=\"time\"> </p>\n"
				                					+ "						<p class=\"time\"></p>\n"
				                					+ "					</div>\n"
				                					+ "					<h3 class=\"read_more\">"+show.getRoom().getCinema().getName()+"</h3>\n"
				                					+ "					<p class=\"description\">"+show.getRoom().getCinema().getAddress()+"</p>\n"
				                					+ "					<hr>\n"	
				                					+ "					<p class=\"description\">"+foods+"</p>\n"
				                					+ "					<div class=\"buttons\">\n"
				                					+ "						<button>Mã hóa đơn: "+vnp_TxnRef+"</button>\n"
				                					+ "						<button>Khách hàng: "+order.getUser().getFullname()+"</button>\n"
				                					+ "						<button>Tổng tiền: "+order.getTotalmoney()+" VND</button>\n"
				                					+ "					</div>\n"
				                					+ "				</div>\n"
				                					+ "			</div>\n"
				                					+ "			<div class=\"c_right\">\n"
				                					+ "				<div class=\"play_btn\">\n"
				                					+ "					<a href=\"https://codewithbishal.com\" target=\"_blank\"\n"
				                					+ "						title=\"Play Trailer\"> <i class=\"fas fa-play-circle\"></i>\n"
				                					+ "					</a>\n"
				                					+ "				</div>\n"
				                					+ "			</div>\n"
				                					+ "		</div>\n"
				                					+ "	</div>\n"
				                					+ "</body>\n"
				                					+ "</html>");
				                		} catch (MessagingException e) {
				                			e.printStackTrace();
				                		}
			                        }
			                        String AlphaNumericString = payment.getVnp_Createdate();
			                		StringBuilder sb = new StringBuilder();
			                		for (int i = 0; i < 14; i++) {
			                            if(i == 4 || i == 6) {
			                            	sb.append('-');
			                            } else if(i == 8) {
			                            	sb.append(' ');
			                            } else if(i == 10 || i == 12) {
			                            	sb.append(':');
			                            }
			                            sb.append(AlphaNumericString.charAt(i));
			                        }
			                		String textTime = sb.toString();
			                		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			                		SimpleDateFormat formatterr = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			                		Date date = formatter.parse(textTime);
			                		String time = formatterr.format(date);
			                		model.addAttribute("time", time);
			                        model.addAttribute("payment", payment);
			                        return "/ticket/vnpay_return";
		                        }
	                        } else {
	                        	String vnp_TxnRef = request.getParameter("vnp_TxnRef");
		                    	String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
		                    	String vnp_TransactionNo = request.getParameter("vnp_TransactionNo");
		                    	String vnp_TransactionStatus = request.getParameter("vnp_TransactionStatus");
		                        Payment payment = paymentDAO.findByOrderID(vnp_TxnRef);
		                        Order order = orderDAO.findById(vnp_TxnRef).get();
		                        if(payment.getVnp_Responsecode() == null && payment.getVnp_Transactionno() == null && payment.getVnp_Transactionstatus() == null) {
		                        	order.setActive(2);
		                        	payment.setVnp_Responsecode(vnp_ResponseCode);
			                        payment.setVnp_Transactionno(vnp_TransactionNo);
			                        payment.setVnp_Transactionstatus(vnp_TransactionStatus);
			                        orderDAO.save(order);
			                        paymentDAO.save(payment);
		                        }
		                        String AlphaNumericString = payment.getVnp_Createdate();
		                		StringBuilder sb = new StringBuilder();
		                		for (int i = 0; i < 14; i++) {
		                            if(i == 4 || i == 6) {
		                            	sb.append('-');
		                            } else if(i == 8) {
		                            	sb.append(' ');
		                            } else if(i == 10 || i == 12) {
		                            	sb.append(':');
		                            }
		                            sb.append(AlphaNumericString.charAt(i));
		                        }
		                		String textTime = sb.toString();
		                		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		                		SimpleDateFormat formatterr = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		                		Date date = formatter.parse(textTime);
		                		String time = formatterr.format(date);
		                		model.addAttribute("time", time);
		                        model.addAttribute("payment", payment);
		                        return "/ticket/vnpay_return";
	                        }
	                    }
	                } else {
	                    //Don hang nay da duoc cap nhat roi, Merchant khong cap nhat nua (Duplicate callback)
	                    System.out.print("{\"RspCode\":\"02\",\"Message\":\"Order already confirmed\"}");
	                }
	            } else {
	            	System.out.print("{\"RspCode\":\"04\",\"Message\":\"Invalid Amount\"}");
	            }
	        } else {
	        	System.out.print("{\"RspCode\":\"01\",\"Message\":\"Order not Found\"}");
	        }

	    } else {
	    	System.out.print("{\"RspCode\":\"97\",\"Message\":\"Invalid Checksum\"}");
	    }
		return "ticket/vnpay_return";
	}

}
