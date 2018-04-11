package com.fuhuitong.applychain.service;

import com.fuhuitong.applychain.model.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface IMembersService extends IBaseService
{
	public String clientLevel(HttpServletRequest request, HttpSession session);
	
	public String memberPriceSelect(HttpServletRequest request, HttpSession session);
	
	public String clientLevelData(HttpServletRequest request, HttpSession session);
	
	public String clientLevelInfo(HttpServletRequest request, HttpSession session, ClientLevel param);
	
	public String saveClientLevel(HttpServletRequest request, HttpSession session, ClientLevel param);
	
	public String deleteClientLevel(HttpServletRequest request, HttpSession session, ClientLevel param);
	
	public String members(HttpServletRequest request, HttpSession session, ClientLevel param);
	
	public String membersData(HttpServletRequest request, HttpSession session, Members param);
	
	public String membersInfo(HttpServletRequest request, HttpSession session, Members param);
	
	public String membersSaveData(HttpServletRequest request, HttpSession session, Members param);
	
	public String membersDeleteData(HttpServletRequest request, HttpSession session, Members param);
	
	public String cardCoupons(HttpServletRequest request, HttpSession session);
	
	public String cardCouponsData(HttpServletRequest request, HttpSession session, CardCoupon param);
	
	public String cardCouponInfo(HttpServletRequest request, HttpSession session, CardCoupon param);
	
	public String cardCouponsSaveData(HttpServletRequest request, HttpSession session, CardCoupon param);
	
	public String cardCouponsDeleteData(HttpServletRequest request, HttpSession session, CardCoupon param);
	
	public String cardCouponsChangeValidStatus(HttpServletRequest request, HttpSession session, CardCoupon param);
	
	public String memberCardCoupons(HttpServletRequest request, HttpSession session, CardCoupon param);
	
	public String memberCardCouponsData(HttpServletRequest request, HttpSession session, MemberCardCoupons param);
	
	public String discountItems(HttpServletRequest request, HttpSession session, Discounts param);
	
	public String discountItemsData(HttpServletRequest request, HttpSession session, DiscountItems param);
	
	public String discountItems1Data(HttpServletRequest request, HttpSession session, DiscountItems param);
	
	public String discountItems2Data(HttpServletRequest request, HttpSession session, DiscountItems param);
	
	public String discountItems5Data(HttpServletRequest request, HttpSession session, DiscountItems param);
	
	public String discountItemsDelete(HttpServletRequest request, HttpSession session, DiscountItems param);
	
	public String discountsData(HttpServletRequest request, HttpSession session, Discounts param);
	
	public String discountItemsInfo1(HttpServletRequest request, HttpSession session, DiscountItems param);
	
	public String queryGoodsInfo(HttpServletRequest request, HttpSession session, String goodsCode);
	
	public String discounts(HttpServletRequest request, HttpSession session, Discounts param);
	
	public String discounts1Info(HttpServletRequest request, HttpSession session, Discounts param);
	
	public String discounts1SaveInfo(HttpServletRequest request, HttpSession session, Discounts param);
	
	public String saveGoodsDiscount(HttpServletRequest request, HttpSession session, DiscountItems item);
	
	public String saveOrderDiscount(HttpServletRequest request, HttpSession session, DiscountItems item);
	
	public String saveGroupDiscount(HttpServletRequest request, HttpSession session, DiscountItems item);
	
	public String changeDiscountStatus(HttpServletRequest request, HttpSession session, Discounts item);
	
	public String deleteDiscounts(HttpServletRequest request, HttpSession session, Discounts item);
	
	public String discountStores(HttpServletRequest request, HttpSession session, Discounts item);
	
	public String discountStoresData(HttpServletRequest request, HttpSession session);
	
	public String discountSaveStores(HttpServletRequest request, HttpSession session, Discounts item);
	
	public String discountDeleteStores(HttpServletRequest request, HttpSession session, DiscountStores discountStore);
}
