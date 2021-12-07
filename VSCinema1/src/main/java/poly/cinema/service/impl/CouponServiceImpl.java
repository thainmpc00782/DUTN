   
package poly.cinema.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poly.cinema.common.Utils;
import poly.cinema.dao.CouponDAO;
import poly.cinema.dao.TicketCouponDAO;
import poly.cinema.dto.ConfirmCouponRequest;
import poly.cinema.dto.CouponRequest;
import poly.cinema.entity.Coupon;
import poly.cinema.entity.TicketCoupon;
import poly.cinema.service.CouponService;

@Service
public class CouponServiceImpl implements CouponService{

	@Autowired
	CouponDAO couponDAO;
	
	@Autowired
	Utils utils;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	TicketCouponDAO ticketCouponDAO;

	@Override
	public List<Coupon> findAll() {
		return couponDAO.findAll();
	}

	@Override
	public CouponRequest autoCreateCoupons() {
		List<Coupon> listCoupons = couponDAO.findAll();
		CouponRequest coupon = new CouponRequest();
		String code = "";
        for (int i = 0; i < 2; i++) {
            code = utils.getAlphaNumericString();
            if(utils.checkCouponsID(code, listCoupons) == true){
                i = 0;
            } else {
                coupon.setCode(code);
                i = 3;
            }
        }
		return coupon;
	}

	@Override
	public Coupon create(Coupon coupon) {
		return couponDAO.save(coupon);
	}

	@Override
	public Coupon update(Coupon coupon) {
		return couponDAO.save(coupon);
	}
	
	@Override
	public ConfirmCouponRequest findById(String id) {
		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
		String username = request.getRemoteUser();
		ConfirmCouponRequest confirm = new ConfirmCouponRequest();
		try {
			Coupon coupon = couponDAO.findById(id).get();
			if(coupon.getExpiry().getTime() < cld.getTime().getTime()) {
				confirm.setCoupon(coupon);
				confirm.setMessage("Mã khuyến mãi đã hết hạn.");
			} else if(coupon.getQuantity() <= 0) {
				confirm.setCoupon(coupon);
				confirm.setMessage("Mã khuyến mãi đã hết số lượng nhập.");
			} else if(coupon.getActivity() == false) {
				confirm.setCoupon(coupon);
				confirm.setMessage("Mã khuyến mãi đã bị vô hiệu.");
			} else {
				try {
					TicketCoupon ticketCoupon = ticketCouponDAO.findByIdAndUsername(id, username);
					if(ticketCoupon.getId() != null) {
						confirm.setCoupon(coupon);
						confirm.setMessage("Bạn đã sử dụng mã khuyên mãi này.");
					}
				} catch (Exception ee) {
					confirm.setCoupon(coupon);
					confirm.setMessage(null);
				}
			}
		} catch (Exception e) {
			confirm.setCoupon(null);
			confirm.setMessage("Mã khuyến mãi không chính xác.");
		}
		return confirm;
	}
	
}