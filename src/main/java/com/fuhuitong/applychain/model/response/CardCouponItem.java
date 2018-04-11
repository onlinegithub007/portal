package com.fuhuitong.applychain.model.response;

public class CardCouponItem 
{
	private Long cardCouponId;
	
	private int cardAmount;
	
	private String expired;
	
	private int price;
	
	private int cardBlance;

	public Long getCardCouponId() {
		return cardCouponId;
	}

	public void setCardCouponId(Long cardCouponId) {
		this.cardCouponId = cardCouponId;
	}

	public int getCardAmount() {
		return cardAmount;
	}

	public void setCardAmount(int cardAmount) {
		this.cardAmount = cardAmount;
	}

	public String getExpired() {
		return expired;
	}

	public void setExpired(String expired) {
		this.expired = expired;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getCardBlance() {
		return cardBlance;
	}

	public void setCardBlance(int cardBlance) {
		this.cardBlance = cardBlance;
	}
	
}
