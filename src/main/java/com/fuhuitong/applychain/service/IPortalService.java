package com.fuhuitong.applychain.service;

import com.fuhuitong.applychain.model.GoodsInfo;
import com.fuhuitong.applychain.model.MerchantUsers;
import com.fuhuitong.applychain.model.MerchantsGroups;
import com.google.code.kaptcha.impl.DefaultKaptcha;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface IPortalService extends IBaseService
{
	public String login(HttpServletRequest request, HttpSession session, MerchantUsers user);
	
	public String menuData(HttpServletRequest request, HttpSession session);
	
	public String savePersonal(HttpServletRequest request, HttpSession session, MerchantUsers user);
	
	public String reloadMerUser(HttpServletRequest request, HttpSession session);
	
	public String updateMerchantUserPwd(HttpServletRequest request, HttpSession session, MerchantUsers user);
	
	public String merchantGroupsData(HttpServletRequest request, HttpSession session);
	
	public String deleteMerchantGroups(HttpServletRequest request, HttpSession session, String merGroupId);
	
	public String saveMerGroup(HttpServletRequest request, HttpSession session, MerchantsGroups group);
	
	public MerchantsGroups loadMerGroup(String merGroupId);
	
	public String merchantGroupsUsersData(HttpServletRequest request, HttpSession session, String merGroupId);
	
	public String merGroupsUsers(HttpServletRequest request, HttpSession session);
	
	public String merGroupsUsersInfo(HttpServletRequest request, HttpSession session, String merUserId);
	
	public String saveMerGroupsUsers(HttpServletRequest request, HttpSession session, MerchantUsers user);
	
	public String deleteMerGroupsUsers(HttpServletRequest request, HttpSession session, String merUserId);
	
	public String merMerchantStores(HttpServletRequest request, HttpSession session);
	
	public String merStoresData(HttpServletRequest request, HttpSession session, MerchantsGroups store);
	
	public String merStoresInfo(HttpServletRequest request, HttpSession session, MerchantsGroups merGroup);
	
	public String getAreas(HttpServletRequest request, HttpSession session, String parentAreaId);
	
	public String saveMerStore(HttpServletRequest request, HttpSession session, MerchantsGroups store);
	
	public String deleteMerchantStores(HttpServletRequest request, HttpSession session, String merGroupId);
	
	public String merStoresUsers(HttpServletRequest request, HttpSession session, String merGroupId);
	
	public String merStoresUsersData(HttpServletRequest request, HttpSession session, String merGroupId);
	
	public String merStoresUsersInfo(HttpServletRequest request, HttpSession session, MerchantUsers storeUser);
	
	public String saveMerStoresUsers(HttpServletRequest request, HttpSession session, MerchantUsers storeUser);
	
	public String deleteMerStoresUsers(HttpServletRequest request, HttpSession session, MerchantUsers storeUser);
	
	public String changeMerUserStatus(HttpServletRequest request, HttpSession session, MerchantUsers storeUser);
	
	public void verifyCode(HttpSession session, HttpServletResponse response, DefaultKaptcha captchaProducer);
	
	public String merGoodsProviders(HttpServletRequest request, HttpSession session);
	
	public String merGoodsProvidersData(HttpServletRequest request, HttpSession session, Integer status);
	
	public String merGoodsProviderInfo(HttpServletRequest request, HttpSession session, MerchantUsers merUser);
	
	public String saveMerGoodsProviders(HttpServletRequest request, HttpSession session, MerchantUsers merUser);
	
	public String deleteMerGoodsProviders(HttpServletRequest request, HttpSession session, MerchantUsers merUser);
	
	public String merProviderGoods(HttpServletRequest request, HttpSession session, MerchantUsers merUser);
	
	public String merProviderGoodsData(HttpServletRequest request, HttpSession session);
	
	public String deleteMerProviderGoods(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo);
	
	public String changeMerProviderGoodsStatus(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo);
}
