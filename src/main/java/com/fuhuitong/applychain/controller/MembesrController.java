package com.fuhuitong.applychain.controller;

import com.fuhuitong.applychain.FuHuiTongContext;
import com.fuhuitong.applychain.model.*;
import com.fuhuitong.applychain.service.IMembersService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MembesrController 
{
	private Logger logger = Logger.getLogger(getClass());
	
	@Resource
	private IMembersService membersService;
	
	@RequestMapping("/backstage/member_client_level.html")
	public String clientLevel(HttpServletRequest request, HttpSession session)
	{
		// 设置客户级别类型： 3 会员
		session.setAttribute("ClientLevelType", FuHuiTongContext.CLIENT_LEVEL_MEMBER);
		session.setAttribute("ClientLevelTypeName", "会员级别管理");
		session.setAttribute("AddClientLevelTypeName", "增加会员级别");
		
		return this.membersService.clientLevel(request, session);
	}
	
	@RequestMapping("/backstage/client_level_data.html")
	public @ResponseBody String clientLevelData(HttpServletRequest request, HttpSession session)
	{
		return this.membersService.clientLevelData(request, session);
	}
	
	@RequestMapping("/backstage/client_level_info.html")
	public String clientLevelInfo(HttpServletRequest request, HttpSession session, ClientLevel param)
	{
		return this.membersService.clientLevelInfo(request, session, param);
	}
	
	@RequestMapping("/backstage/save_client_level.html")
	public @ResponseBody String saveClientLevel(HttpServletRequest request, HttpSession session, ClientLevel param)
	{
		return this.membersService.saveClientLevel(request, session, param);
	}
	
	@RequestMapping("/backstage/delete_client_level.html")
	public @ResponseBody String deleteClientLevel(HttpServletRequest request, HttpSession session, ClientLevel param)
	{
		return this.membersService.deleteClientLevel(request, session, param);
	}
	
	@RequestMapping("/backstage/member_price_select.html")
	public String memberPriceSelect(HttpServletRequest request, HttpSession session)
	{
		// 设置客户级别类型： 3 会员
		session.setAttribute("ClientLevelType", FuHuiTongContext.CLIENT_LEVEL_MEMBER);
		session.setAttribute("ClientLevelTypeName", "会员级别管理");
		session.setAttribute("AddClientLevelTypeName", "增加会员级别");
		
		return this.membersService.memberPriceSelect(request, session);
	}
	
	/**
	 * 进入会员管理主界面
	 * @param request
	 * @param session
	 * @param param
	 * @return
	 */
	@RequestMapping("/backstage/members.html")
	public String members(HttpServletRequest request, HttpSession session, ClientLevel param)
	{
		return this.membersService.members(request, session, param);
	}
	
	@RequestMapping("/backstage/members_data.html")
	public @ResponseBody String membersData(HttpServletRequest request, HttpSession session, Members param)
	{
		return this.membersService.membersData(request, session, param);
	}
	
	@RequestMapping("/backstage/member_info.html")
	public String membersInfo(HttpServletRequest request, HttpSession session, Members param)
	{
		return this.membersService.membersInfo(request, session, param);
	}
	
	/**
	 * 保存会员信息
	 * @param request
	 * @param session
	 * @param param
	 * @return
	 */
	@RequestMapping("/backstage/members_save_data.html")
	public @ResponseBody String membersSaveData(HttpServletRequest request, HttpSession session, Members param)
	{
		return this.membersService.membersSaveData(request, session, param);
	}
	
	/**
	 * 保存会员信息
	 * @param request
	 * @param session
	 * @param param
	 * @return
	 */
	@RequestMapping("/backstage/members_delete_data.html")
	public @ResponseBody String membersDeleteData(HttpServletRequest request, HttpSession session, Members param)
	{
		return this.membersService.membersDeleteData(request, session, param);
	}
	
	/**
	 * 卡券管理首页
	 * @param request
	 * @param session
	 * @param param
	 * @return
	 */
	@RequestMapping("/backstage/card_coupons.html")
	public String cardCoupons(HttpServletRequest request, HttpSession session)
	{
		return this.membersService.cardCoupons(request, session);
	}
	
	/**
	 * 进入新建卡券页面
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backstage/card_coupon_info.html")
	public String cardCouponInfo(HttpServletRequest request, HttpSession session, CardCoupon param)
	{
		return this.membersService.cardCouponInfo(request, session, param);
	}
	
	/**
	 * 卡券数据
	 * @param request
	 * @param session
	 * @param param
	 * @return
	 */
	@RequestMapping("/backstage/card_coupons_data.html")
	public @ResponseBody String cardCouponsData(HttpServletRequest request, HttpSession session, CardCoupon param)
	{
		return this.membersService.cardCouponsData(request, session, param);
	}
	
	@RequestMapping("/backstage/card_coupons_save_data.html")
	public @ResponseBody String cardCouponsSaveData(HttpServletRequest request, HttpSession session, CardCoupon param)
	{
		return this.membersService.cardCouponsSaveData(request, session, param);
	}
	
	@RequestMapping("/backstage/card_coupons_delete_data.html")
	public @ResponseBody String cardCouponsDeleteData(HttpServletRequest request, HttpSession session, CardCoupon param)
	{
		return this.membersService.cardCouponsDeleteData(request, session, param);
	}
	
	@RequestMapping("/backstage/card_coupons_change_valid_status.html")
	public @ResponseBody String cardCouponsChangeValidStatus(HttpServletRequest request, HttpSession session, CardCoupon param)
	{
		return this.membersService.cardCouponsChangeValidStatus(request, session, param);
	}
	
	@RequestMapping("/backstage/member_card_coupons.html")
	public String memberCardCoupons(HttpServletRequest request, HttpSession session, CardCoupon param)
	{
		return this.membersService.memberCardCoupons(request, session, param);
	}
	
	@RequestMapping("/backstage/member_card_coupons_data.html")
	public @ResponseBody String memberCardCouponsData(HttpServletRequest request, HttpSession session, MemberCardCoupons param)
	{
		return this.membersService.memberCardCouponsData(request, session, param);
	}
	
	@RequestMapping("/backstage/discount_items.html")
	public String discountItems(HttpServletRequest request, HttpSession session, Discounts param)
	{
		return this.membersService.discountItems(request, session, param);
	}
	
	@RequestMapping("/backstage/discounts.html")
	public String discounts(HttpServletRequest request, HttpSession session, Discounts param)
	{
		return this.membersService.discounts(request, session, param);
	}
	
	@RequestMapping("/backstage/discount_items_data.html")
	public @ResponseBody String discountItemsData(HttpServletRequest request, HttpSession session, DiscountItems param)
	{
		return this.membersService.discountItemsData(request, session, param);
	}
	
	@RequestMapping("/backstage/discount_items1_data.html")
	public @ResponseBody String discountItems1Data(HttpServletRequest request, HttpSession session, DiscountItems param)
	{
		return this.membersService.discountItems1Data(request, session, param);
	}
	
	@RequestMapping("/backstage/discount_items2_data.html")
	public @ResponseBody String discountItems2Data(HttpServletRequest request, HttpSession session, DiscountItems param)
	{
		return this.membersService.discountItems2Data(request, session, param);
	}
	
	@RequestMapping("/backstage/discount_items5_data.html")
	public @ResponseBody String discountItems5Data(HttpServletRequest request, HttpSession session, DiscountItems param)
	{
		return this.membersService.discountItems5Data(request, session, param);
	}
	
	@RequestMapping("/backstage/discount_items_delete.html")
	public @ResponseBody String discountItemsDelete(HttpServletRequest request, HttpSession session, DiscountItems param)
	{
		return this.membersService.discountItemsDelete(request, session, param);
	}
	
	@RequestMapping("/backstage/discounts_data.html")
	public @ResponseBody String discountsData(HttpServletRequest request, HttpSession session, Discounts param)
	{
		return this.membersService.discountsData(request, session, param);
	}
	
	@RequestMapping("/backstage/discount_items_info1.html")
	public String discountItemsInfo1(HttpServletRequest request, HttpSession session, DiscountItems param)
	{
		return this.membersService.discountItemsInfo1(request, session, param);
	}
	
	@RequestMapping("/backstage/discounts1_info.html")
	public String discounts1Info(HttpServletRequest request, HttpSession session, Discounts param)
	{
		return this.membersService.discounts1Info(request, session, param);
	}
	
	@RequestMapping("/backstage/discounts1_save_info.html")
	public @ResponseBody String discounts1SaveInfo(HttpServletRequest request, HttpSession session, Discounts param)
	{
		return this.membersService.discounts1SaveInfo(request, session, param);
	}
	
	@RequestMapping("/backstage/query_goods_info_indiscount.html")
	public @ResponseBody String queryGoodsInfo(HttpServletRequest request, HttpSession session, String goodsCode)
	{
		return this.membersService.queryGoodsInfo(request, session, goodsCode);
	}
	
	/**
	 * 保存商品降价优惠
	 * @param request
	 * @param session
	 * @param goodsCode
	 * @return
	 */
	@RequestMapping("/backstage/save_goods_discount.html")
	public @ResponseBody String saveGoodsDiscount(HttpServletRequest request, HttpSession session, DiscountItems item)
	{
		return this.membersService.saveGoodsDiscount(request, session, item);
	}
	
	/**
	 * 保存订单整额折扣
	 * @param request
	 * @param session
	 * @param item
	 * @return
	 */
	@RequestMapping("/backstage/save_order_discount.html")
	public @ResponseBody String saveOrderDiscount(HttpServletRequest request, HttpSession session, DiscountItems item)
	{
		return this.membersService.saveOrderDiscount(request, session, item);
	}
	
	@RequestMapping("/backstage/save_group_discount.html")
	public @ResponseBody String saveGroupDiscount(HttpServletRequest request, HttpSession session, DiscountItems item)
	{
		return this.membersService.saveGroupDiscount(request, session, item);
	}
	
	/**
	 * 修改优惠促销活动状态
	 * @param request
	 * @param session
	 * @param item
	 * @return
	 */
	@RequestMapping("/backstage/change_discount_status.html")
	public @ResponseBody String changeDiscountStatus(HttpServletRequest request, HttpSession session, Discounts item)
	{
		return this.membersService.changeDiscountStatus(request, session, item);
	}
	
	@RequestMapping("/backstage/delete_discounts.html")
	public @ResponseBody String deleteDiscounts(HttpServletRequest request, HttpSession session, Discounts item)
	{
		return this.membersService.deleteDiscounts(request, session, item);
	}
	
	/**
	 * 进入选择参与分店界面
	 * @param request
	 * @param session
	 * @param item
	 * @return
	 */
	@RequestMapping("/backstage/discount_stores.html")
	public String discountStores(HttpServletRequest request, HttpSession session, Discounts item)
	{
		return this.membersService.discountStores(request, session, item);
	}
	
	@RequestMapping("/backstage/discount_stores_data.html")
	public @ResponseBody String discountStoresData(HttpServletRequest request, HttpSession session)
	{
		return this.membersService.discountStoresData(request, session);
	}
	
	@RequestMapping("/backstage/discount_save_stores.html")
	public @ResponseBody String discountSaveStores(HttpServletRequest request, HttpSession session, Discounts item)
	{
		return this.membersService.discountSaveStores(request, session, item);
	}
	
	@RequestMapping("/backstage/discount_delete_stores.html")
	public @ResponseBody String discountDeleteStores(HttpServletRequest request, HttpSession session, DiscountStores discountStore)
	{
		return this.membersService.discountDeleteStores(request, session, discountStore);
	}
}
