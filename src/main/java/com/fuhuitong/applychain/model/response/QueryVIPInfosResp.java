package com.fuhuitong.applychain.model.response;

import java.util.ArrayList;

/**
 * POS终端查询订单信息的数据响应类
 * @author haoqingfeng
 *
 */
public class QueryVIPInfosResp 
{
	private HeadResp headResp;
	
	private String retCode;
	
	private String memberCode;
	
	private String memberName;
	
	private String memberPayToken;
	
	private int memberLevel;
	
	private int memberScore;
	
	private ArrayList<DiscountCouponItem> discountCoupons = new ArrayList<DiscountCouponItem>();
	
	private ArrayList<CardCouponItem> cardCoupons = new ArrayList<CardCouponItem>();
	
	private int balance;
	
	private int balancePay;
	
	private ArrayList<GoodsInOrderResp> goods = new ArrayList<GoodsInOrderResp>();
	
	private int finalPrice;
	
	private int totalPrice;

	public HeadResp getHeadResp() {
		return headResp;
	}

	public void setHeadResp(HeadResp headResp) {
		this.headResp = headResp;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberPayToken() {
		return memberPayToken;
	}

	public void setMemberPayToken(String memberPayToken) {
		this.memberPayToken = memberPayToken;
	}

	public int getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(int memberLevel) {
		this.memberLevel = memberLevel;
	}

	public int getMemberScore() {
		return memberScore;
	}

	public void setMemberScore(int memberScore) {
		this.memberScore = memberScore;
	}

	public ArrayList<DiscountCouponItem> getDiscountCoupons() {
		return discountCoupons;
	}

	public ArrayList<CardCouponItem> getCardCoupons() {
		return cardCoupons;
	}
	
	public void addDiscountCouponItem(DiscountCouponItem item)
	{
		this.discountCoupons.add(item);
	}
	
	public void addCardCoupon(CardCouponItem cardCoupon)
	{
		this.cardCoupons.add(cardCoupon);
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public int getBalancePay() {
		return balancePay;
	}

	public void setBalancePay(int balancePay) {
		this.balancePay = balancePay;
	}

	public ArrayList<GoodsInOrderResp> getGoods() {
		return goods;
	}

	public void addGoods(GoodsInOrderResp goods)
	{
		this.goods.add(goods);
	}

	public int getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(int finalPrice) {
		this.finalPrice = finalPrice;
	}
	
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public int getTotalPrice() {
		return totalPrice;
	}
	
}
