package com.fuhuitong.applychain.model.response;

/**
 * 响应数据中会员可用的优惠券
 * @author haoqingfeng
 *
 */
public class DiscountCouponItem 
{
	private Long couponId;
	private int price;
	private String expired;
	private String desc;
	private boolean valid;
	private String invalidDesc;
	public Long getCouponId() {
		return couponId;
	}
	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public String getInvalidDesc() {
		return invalidDesc;
	}
	public void setInvalidDesc(String invalidDesc) {
		this.invalidDesc = invalidDesc;
	}
	
	
}
