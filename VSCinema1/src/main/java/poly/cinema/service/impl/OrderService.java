package poly.cinema.service.impl;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poly.cinema.common.Utils;
import poly.cinema.dao.AccountDAO;
import poly.cinema.dao.OrderDAO;
import poly.cinema.dao.OrderFoodDAO;
import poly.cinema.dao.PaymentDAO;
import poly.cinema.dao.TicketCouponDAO;
import poly.cinema.dao.TicketDAO;
import poly.cinema.dto.CreateOrderRequest;
import poly.cinema.dto.FoodRequest;
import poly.cinema.dto.OrderRequest;
import poly.cinema.dto.QueryDR;
import poly.cinema.dto.UrlRequest;
import poly.cinema.entity.Account;
import poly.cinema.entity.Order;
import poly.cinema.entity.OrderFood;
import poly.cinema.entity.Payment;
import poly.cinema.entity.Seat;
import poly.cinema.entity.Show;
import poly.cinema.entity.Ticket;
import poly.cinema.entity.TicketCoupon;
import poly.cinema.payment.Config;
import poly.cinema.service.MailerService;
@Service
public class OrderService implements poly.cinema.service.OrderService {
	@Autowired
	OrderDAO dao;
	@Autowired
	OrderFoodDAO ordfdao;
	
	@Autowired
	TicketDAO ticketDAO;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	AccountDAO accountDAO;
	
	@Autowired
	TicketCouponDAO ticketCouponDAO;
	
	@Autowired
	PaymentDAO paymentDAO;
	
	@Autowired
	MailerService mailerService;
	
	@Override
	public List<Order> findAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}
	@Override
	public List<OrderFood> findByOrderId(String orderid) {
		// TODO Auto-generated method stub
		return ordfdao.findByOrderId(orderid);
	}
	@Override
	public Order updateActive(String id,Order order) {
		order=dao.findById(id).get();
		order.setActive(0);
		return dao.save(order);
	}
	@Override
	public List<Order> findByDate(Date createdate) {
		return dao.findByDate(createdate);
	}

	@Override
	public List<Object[]> listSumRevenues() {
		// TODO Auto-generated method stub
		return dao.getListSumRevebues();
	}
	@Override
	public Long getSumRevenuesOfDay() {
		// TODO Auto-generated method stub
		return dao.revenueOfDay(new Date());
	}
	@Override
	public Long getSumRevenuesOfMonth() {
		// TODO Auto-generated method stub
		return dao.revenueOfMonth(new Date());
	}
	@Override
	public Long getSumRevenuesOfYear() {
		// TODO Auto-generated method stub
		return dao.revenueOfYears(new Date());
	}
	
	@Override
	public UrlRequest createOrder(CreateOrderRequest data) throws UnsupportedEncodingException {
		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
		List<Order> listOrder = dao.findAll();
		List<Ticket> list = ticketDAO.findAll();
		
		Order order = new Order();
		
		String id = "";
        for (int i = 0; i < 2; i++) {
            id = Config.getRandomNumber(8);
            if(Config.checkCouponsID(id, listOrder) == true){
                i = 0;
            } else {
            	order.setId(id);
                i = 3;
            }
        }
		
		String username = request.getRemoteUser();
		Account user = accountDAO.findById(username).get();
		
		order.setId(id);
		order.setUser(user);
		order.setCreatedate(cld.getTime());
		order.setTime(cld.getTime());
		order.setTotalmoney(data.getAmount());
		order.setActive(0);
		
		List<OrderFood> listOrderFood = new ArrayList<OrderFood>();
		for(FoodRequest f:data.getFood()) {
			OrderFood orderFood = new OrderFood();
			if(f.getQuantity() > 0) {
				if(f.getSale().getId() == null) {
					orderFood.setOrder(order);
					orderFood.setFood(f.getFood());
					orderFood.setDiscount(0.0);
					orderFood.setPrice(f.getFood().getPrice());
					orderFood.setQuantity(f.getQuantity());
					listOrderFood.add(orderFood);
				} else {
					orderFood.setOrder(order);
					orderFood.setFood(f.getFood());
					orderFood.setDiscount(f.getSale().getDiscount());
					orderFood.setPrice(f.getFood().getPrice());
					orderFood.setQuantity(f.getQuantity());
					listOrderFood.add(orderFood);
				}
			}
		}
		
		
		List<Ticket> listTicket = new ArrayList<Ticket>();
		for(Seat s:data.getSeat()) {
			Ticket ticket = new Ticket();
			String ticketID;
	        for (int i = 0; i < 2; i++) {
	        	ticketID = Utils.getTicketRandom();
	            if(Utils.checkTicketID(ticketID, list) == true){
	                i = 0;
	            } else {
	            	ticket.setId(ticketID);
	                i = 3;
	            }
	        }
			if(data.getCoupon().getId() == null) {
		        ticket.setOrder(order);
		        ticket.setShow(data.getShow());
		        ticket.setSeat(s);
		        ticket.setCreateDate(cld.getTime());
		        int sum = (data.getShow().getPriceShow().getPrice()+s.getType().getSurcharge());
		        ticket.setTotalmoney(sum);
		        ticket.setTicketprice(data.getShow().getPriceShow().getPrice());
		        ticket.setDiscount(0.0);
		        ticket.setActivity(true);
		        listTicket.add(ticket);
			} else {
				ticket.setOrder(order);
		        ticket.setShow(data.getShow());
		        ticket.setSeat(s);
		        ticket.setCreateDate(cld.getTime());
		        int sum = (int) ((data.getShow().getPriceShow().getPrice()+s.getType().getSurcharge()) - (((data.getShow().getPriceShow().getPrice()+s.getType().getSurcharge())*data.getCoupon().getDiscount())/100));
		        ticket.setTotalmoney(sum);
		        ticket.setTicketprice(data.getShow().getPriceShow().getPrice());
		        ticket.setDiscount(data.getCoupon().getDiscount());
		        ticket.setActivity(true);
		        listTicket.add(ticket);
			}
	
		}
		if(data.getCoupon().getId() != null) {
			TicketCoupon ticketCoupon = new TicketCoupon();
			ticketCoupon.setAccount(user);
			ticketCoupon.setCoupon(data.getCoupon());
			ticketCoupon.setExpiry(cld.getTime());
			ticketCouponDAO.save(ticketCoupon);
		}
		if(listOrderFood.size() > 0) {
			order.setOrderDetails(listOrderFood);
			dao.save(order);
			ordfdao.saveAllAndFlush(listOrderFood);
			ticketDAO.saveAllAndFlush(listTicket);
		} else {
			dao.save(order);
			ticketDAO.saveAllAndFlush(listTicket);
		}
		
		String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        
        String vnp_OrderInfo = "Thanh toan don hang";
        String orderType = "billpayment";
        String vnp_TxnRef = order.getId();
        
        String vnp_IpAddr = Config.getIpAddress(request);
        String vnp_TmnCode = Config.vnp_TmnCode;
        int amount = order.getTotalmoney() * 100;
        
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        String bank_code = data.getBank();
        if (bank_code != null && !bank_code.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bank_code);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);
        
        String locate = "vn";
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);     
        
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString())); 
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
        
        List<Payment> listPayment = new ArrayList<Payment>();
        Payment payment = new Payment();
        payment.setVnp_TxnRef(order);
        payment.setVnp_Amount(amount);
        payment.setVnp_Orderinfo(vnp_OrderInfo);
        payment.setVnp_Ordertype(orderType);
        payment.setVnp_Bankcode(bank_code);
        payment.setVnp_Currcode("VND");
        payment.setVnp_Locale(locate);
        payment.setVnp_Createdate(vnp_CreateDate);
        listPayment.add(payment);
        paymentDAO.saveAllAndFlush(listPayment);
        
        UrlRequest result = new UrlRequest();
        result.setStatus("200");
        result.setMessage("success");
        result.setUrl(paymentUrl);
		
		return result;
	}
	
	@Override
	public List<OrderRequest> findByActivity() {
		String username = request.getRemoteUser();
		List<Order> listOrder = dao.findByUsername(username);
		List<OrderRequest> listOrderRequest = new ArrayList<OrderRequest>();
		for(Order o:listOrder) {
			OrderRequest orderRequest = new OrderRequest();
			Show show = new Show();
			String seat ="";
			String food = "";
			for(Ticket t : o.getTickets()) {
				if(show.getId() != null) {
					seat+= t.getSeat().getName()+" ";
				} else {
					show = t.getShow();
					seat+= t.getSeat().getName()+" ";
				}
			}
			for(OrderFood f:o.getOrderDetails()) {
				food+= f.getFood().getName()+"("+f.getQuantity()+") " +"| ";
			}
			orderRequest.setOrder(o);
			orderRequest.setShow(show);
			orderRequest.setSeat(seat);
			orderRequest.setFood(food);
			listOrderRequest.add(orderRequest);
		}
		return listOrderRequest;
	}
	
	@Override
	public QueryDR checkOrder(String id) throws IOException {
		QueryDR querydr = new QueryDR();
		Payment payment = paymentDAO.findByOrderID(id);
		Order order = dao.findById(id).get();
		String vnp_TxnRef = payment.getVnp_TxnRef().getId();
        String vnp_TransDate = payment.getVnp_Createdate();
        String vnp_TmnCode = Config.vnp_TmnCode;
        String vnp_IpAddr = Config.getIpAddress(request);

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "querydr");
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Kiem tra ket qua GD");
        vnp_Params.put("vnp_TransDate", vnp_TransDate);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_apiUrl + "?" + queryUrl;
        URL url = new URL(paymentUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        String Rsp = response.toString();
        String respDecode = URLDecoder.decode(Rsp, "UTF-8");
        String[] responseData = respDecode.split("&|\\=");
        Map<String, String> success = new HashMap<>();
        for(int i = 0; i < responseData.length; i++) {
        	success.put(responseData[i], responseData[i+1]);
        	System.out.println(responseData[i]+": "+responseData[i+1]);
        	i++;
        }
        String vnp_TransactionStatus = success.get("vnp_TransactionStatus");
        String vnp_TransactionNo = success.get("vnp_TransactionNo");
        String vnp_ResponseCode = success.get("vnp_ResponseCode");
        if(vnp_ResponseCode.equals("00")) {
        	if(vnp_TransactionStatus.equals("00")) {
        		querydr.setCode(vnp_TransactionStatus);
        		querydr.setMessage("Vui lòng kiểm tra email của bạn.");
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
            	SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy"); 
        		SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
            	String date = formatDate.format(show.getShowDate());
            	String start= formatTime.format(show.getStartTime());
            	String end = formatTime.format(show.getEndTime());
            	payment.setVnp_Responsecode(vnp_ResponseCode);
                payment.setVnp_Transactionno(vnp_TransactionNo);
                payment.setVnp_Transactionstatus(vnp_TransactionStatus);
                dao.save(order);
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
        	} else {
        		querydr.setCode(vnp_TransactionStatus);
        		querydr.setMessage("Giao dịch chưa hoàn tất.");
        		order.setActive(2);
        		payment.setVnp_Responsecode(vnp_ResponseCode);
                payment.setVnp_Transactionno(vnp_TransactionNo);
                payment.setVnp_Transactionstatus(vnp_TransactionStatus);
                dao.save(order);
                paymentDAO.save(payment);
        	}
        } else {
        	querydr.setCode(vnp_ResponseCode);
    		querydr.setMessage("Vui lòng kiểm tra lại sau 24h.");
        }
        
		return querydr;
	}
	
	
}