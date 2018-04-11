package com.fuhuitong.applychain.controller;

import com.fuhuitong.applychain.model.GoodsInfo;
import com.fuhuitong.applychain.model.MerchantUsers;
import com.fuhuitong.applychain.model.MerchantsGroups;
import com.fuhuitong.applychain.service.IPortalService;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class PortalController{
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Resource
	private IPortalService portalService;
	
	@Resource
	private DefaultKaptcha captchaProducer;
	
	@RequestMapping("/backstage/index.html")
	public String index(HttpServletRequest request, HttpSession session)
	{
		if (!portalService.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "login";
		}
		else
		{
			logger.info("Current user was logged in.");
			return "index";
		}
	}
	
	@RequestMapping("/backstage/login.html")
	public @ResponseBody String login(HttpServletRequest request, HttpSession session, MerchantUsers merUser)
	{
		
		return portalService.login(request, session, merUser);
	}
	
	@RequestMapping("/backstage/menu_data.html")
	public @ResponseBody String menuData(HttpServletRequest request, HttpSession session)
	{
		
		return portalService.menuData(request, session);
	}
	
	@RequestMapping("/backstage/main.html")
	public String main(HttpServletRequest request, HttpSession session)
	{
		if (!portalService.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		else
		{
			logger.info("Current user was logged in.");
			return "main";
		}
	}
	
	@RequestMapping("/backstage/logout.html")
	public String logout(HttpSession session)
	{
		session.invalidate();
		
		return "redirect:index.html";
	}
	
	@RequestMapping("/backstage/personal.html")
	public String merUserPersonal(HttpServletRequest request, HttpSession session)
	{
		if (!portalService.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		logger.info("Current user was logged in.");
		return "page/personal";
	}
	
	@RequestMapping(value = "/backstage/save_merUser_personal.html", method = RequestMethod.POST)
	public @ResponseBody String saveMerUserPersonal(HttpServletRequest request, HttpSession session, MerchantUsers merUser)
	{
		return portalService.savePersonal(request, session, merUser);
	}
	
	@RequestMapping("/backstage/password.html")
	public String merUserPassword(HttpServletRequest request, HttpSession session)
	{
		if (!portalService.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		logger.info("Current user was logged in.");
		return "page/password";
	}
	
	@RequestMapping("/backstage/save_password.html")
	public @ResponseBody String modifyPwdSave(HttpServletRequest request, HttpSession session, MerchantUsers user)
	{
		return this.portalService.updateMerchantUserPwd(request, session, user);
	}
	
	@RequestMapping("/backstage/merchant_groups.html")
	public String merGroups(HttpServletRequest request, HttpSession session)
	{
		if (!portalService.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		logger.info("Current user was logged in.");
		return "page/merchant_groups";
	}
	
	@RequestMapping("/backstage/merchant_groups_info.html")
	public String merGroupsInfo(HttpServletRequest request, HttpSession session, MerchantsGroups merGroup)
	{
		if (!portalService.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		if (!StringUtils.isEmpty(merGroup.getMerGroupId()))
		{
			// 更新，从数据库中查询
			MerchantsGroups currMerGroup = portalService.loadMerGroup(merGroup.getMerGroupId());
			session.setAttribute("CurrentMerGroup", currMerGroup);
		}
		
		logger.info("Current user was logged in.");
		return "page/merchant_groups_info";
	}

	@RequestMapping("/backstage/merchant_groups_data.html")
	public @ResponseBody String merchantGroupsData(HttpServletRequest request, HttpSession session)
	{
		return this.portalService.merchantGroupsData(request, session);
	}
	
	@RequestMapping("/backstage/save_merchant_group.html")
	public @ResponseBody String saveMerGroups(HttpServletRequest request, HttpSession session, MerchantsGroups group)
	{
		return this.portalService.saveMerGroup(request, session, group);
	}
	
	@RequestMapping("/backstage/delete_merchant_groups.html")
	public @ResponseBody String deleteMerchantGroups(HttpServletRequest request, HttpSession session, String merGroupId)
	{
		return this.portalService.deleteMerchantGroups(request, session, merGroupId);
	}
	
	@RequestMapping("/backstage/merchant_users.html")
	public String merGroupsUsers(HttpServletRequest request, HttpSession session, String merGroupId)
	{
		return this.portalService.merGroupsUsers(request, session);
	}
	
	@RequestMapping("/backstage/merchant_users_info.html")
	public String merGroupsUsersInfo(HttpServletRequest request, HttpSession session, String merUserId)
	{
		return this.portalService.merGroupsUsersInfo(request, session, merUserId);
	}
	
	@RequestMapping("/backstage/merchant_users_data.html")
	public @ResponseBody String merGroupsUsersData(HttpServletRequest request, HttpSession session, String merGroupId)
	{
		return portalService.merchantGroupsUsersData(request, session, merGroupId);
	}
	
	@RequestMapping("/backstage/save_merchant_user.html")
	public @ResponseBody String saveMerGroupsUsers(HttpServletRequest request, HttpSession session, MerchantUsers user)
	{
		return portalService.saveMerGroupsUsers(request, session, user);
	}
	
	@RequestMapping("/backstage/delete_merchant_users.html")
	public @ResponseBody String deleteMerGroupsUsers(HttpServletRequest request, HttpSession session, String merUserId)
	{
		return portalService.deleteMerGroupsUsers(request, session, merUserId);
	}
	
	@RequestMapping("/backstage/merchant_stores.html")
	public String merMerchantStores(HttpServletRequest request, HttpSession session)
	{
		return this.portalService.merMerchantStores(request, session);
	}
	
	@RequestMapping("/backstage/merchant_stores_data.html")
	public @ResponseBody String merStoresData(HttpServletRequest request, HttpSession session, MerchantsGroups store)
	{
		return portalService.merStoresData(request, session, store);
	}
	
	@RequestMapping("/backstage/merchant_stores_info.html")
	public String merStoresInfo(HttpServletRequest request, HttpSession session, MerchantsGroups merGroup)
	{
		return portalService.merStoresInfo(request, session, merGroup);
	}
	
	@RequestMapping("/backstage/areas.html")
	public @ResponseBody String getAreas(HttpServletRequest request, HttpSession session, String parentAreaId)
	{
		return portalService.getAreas(request, session, parentAreaId);
	}
	
	@RequestMapping("/backstage/save_merchant_store.html")
	public @ResponseBody String saveMerStore(HttpServletRequest request, HttpSession session, MerchantsGroups merGroup)
	{
		return portalService.saveMerStore(request, session, merGroup);
	}
	
	@RequestMapping("/backstage/delete_merchant_stores.html")
	public @ResponseBody String deleteMerStore(HttpServletRequest request, HttpSession session, String merGroupId)
	{
		return portalService.deleteMerchantStores(request, session, merGroupId);
	}
	
	@RequestMapping("/backstage/merchant_stores_users.html")
	public String merStoresUsers(HttpServletRequest request, HttpSession session, String merGroupId)
	{
		return this.portalService.merStoresUsers(request, session, merGroupId);
	}
	
	@RequestMapping("/backstage/merchant_stores_users_data.html")
	public @ResponseBody String merStoresUsersData(HttpServletRequest request, HttpSession session, String merGroupId)
	{
		return portalService.merStoresUsersData(request, session, merGroupId);
	}
	
	@RequestMapping("/backstage/merchant_stores_users_info.html")
	public String merStoresUsersInfo(HttpServletRequest request, HttpSession session, MerchantUsers storeUser)
	{
		return this.portalService.merStoresUsersInfo(request, session, storeUser);
	}
	
	@RequestMapping("/backstage/save_merchant_store_user.html")
	public @ResponseBody String saveMerStoresUsers(HttpServletRequest request, HttpSession session, MerchantUsers storeUser)
	{
		return portalService.saveMerStoresUsers(request, session, storeUser);
	}
	
	@RequestMapping("/backstage/delete_merchant_store_user.html")
	public @ResponseBody String deleteMerStoresUsers(HttpServletRequest request, HttpSession session, MerchantUsers storeUser)
	{
		return portalService.deleteMerStoresUsers(request, session, storeUser);
	}
	
	@RequestMapping("/backstage/change_mer_user_status.html")
	public @ResponseBody String changeMerUserStatus(HttpServletRequest request, HttpSession session, MerchantUsers storeUser)
	{
		return portalService.changeMerUserStatus(request, session, storeUser);
	}
	
	@RequestMapping("/backstage/verifyCode.jpg")
	public void verifyCode(HttpServletResponse response, HttpSession session)
	{
		portalService.verifyCode(session, response, this.captchaProducer);
	}
	
	@RequestMapping("/backstage/merchant_providers.html")
	public String merGoodsProviders(HttpServletRequest request, HttpSession session)
	{
		return this.portalService.merGoodsProviders(request, session);
	}
	
	@RequestMapping("/backstage/merchant_goods_providers_data.html")
	public @ResponseBody String merGoodsProvidersData(HttpServletRequest request, HttpSession session, Integer status)
	{
		return portalService.merGoodsProvidersData(request, session, status);
	}
	
	@RequestMapping("/backstage/merchant_gps_info.html")
	public String merGoodsProviderInfo(HttpServletRequest request, HttpSession session, MerchantUsers merUser)
	{
		return this.portalService.merGoodsProviderInfo(request, session, merUser);
	}
	
	/**
	 * 保存供应商的信息
	 * @param request
	 * @param session
	 * @param merUser
	 * @return
	 */
	@RequestMapping("/backstage/save_merchant_gps.html")
	public @ResponseBody String saveMerGoodsProviders(HttpServletRequest request, HttpSession session, MerchantUsers merUser)
	{
		return portalService.saveMerGoodsProviders(request, session, merUser);
	}
	
	@RequestMapping("/backstage/delete_merchant_gps.html")
	public @ResponseBody String deleteMerGoodsProviders(HttpServletRequest request, HttpSession session, MerchantUsers merUser)
	{
		return portalService.deleteMerGoodsProviders(request, session, merUser);
	}
	
	@RequestMapping("/backstage/merchant_provider_goods.html")
	public String merProviderGoods(HttpServletRequest request, HttpSession session, MerchantUsers merUser)
	{
		return this.portalService.merProviderGoods(request, session, merUser);
	}
	
	@RequestMapping("/backstage/merchant_provider_goods_data.html")
	public @ResponseBody String merProviderGoodsData(HttpServletRequest request, HttpSession session)
	{
		return portalService.merProviderGoodsData(request, session);
	}
	
	@RequestMapping("/backstage/delete_provider_goods.html")
	public @ResponseBody String deleteMerProviderGoods(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo)
	{
		return portalService.deleteMerProviderGoods(request, session, goodsInfo);
	}
	
	@RequestMapping("/backstage/change_mer_provider_goods_status.html")
	public @ResponseBody String changeMerProviderGoodsStatus(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo)
	{
		return portalService.changeMerProviderGoodsStatus(request, session, goodsInfo);
	}
}
