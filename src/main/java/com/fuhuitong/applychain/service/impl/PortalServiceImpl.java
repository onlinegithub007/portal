package com.fuhuitong.applychain.service.impl;


import com.fuhuitong.applychain.FuHuiTongContext;
import com.fuhuitong.applychain.SysDefaultRole;
import com.fuhuitong.applychain.dao.*;
import com.fuhuitong.applychain.model.*;
import com.fuhuitong.applychain.service.IPortalService;
import com.fuhuitong.applychain.service.IShortMessageService;
import com.fuhuitong.applychain.utils.MD5;
import com.fuhuitong.applychain.utils.MyUUID;
import com.fuhuitong.applychain.utils.StringUtilEx;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

@Service
public class PortalServiceImpl extends BaseService implements IPortalService 
{
	@Resource
	private MerchantUsersMapper merUserMapper;
	
	@Resource
	private SysDictionaryMapper sysDictMapper;
	
	@Resource
	private ProductUserRoleMapper productUserRoleMapper;
	
	@Resource
	private MerchantUserRoleMapper merUserRoleMapper;
	
	@Resource
	private MerchantsGroupsMapper merGroupsMapper;
	
	@Resource
	private MerchantsMapper merchantMapper;
	
	@Resource
	private AreasMapper areaMapper;
	
	@Resource
	private MerchantClientsMapper merClientMapper;
	
	@Resource
	private GoodsInfoMapper goodsInfoMapper;
	
	@Resource
	private GoodsProvidersMapper  goodsProviderMapper;
	
	@Resource
	private ProductMenusMapper productMenusMapper;
	
	@Resource
	private UserRoleMenusMapper userRoleMenusMapper;
	
	@Resource
	private IShortMessageService shortMessageService;
	
	@Override
	public String login(HttpServletRequest request, HttpSession session, MerchantUsers user) 
	{
		if (StringUtils.isEmpty(user.getUserAccount()))
		{
			return getRetCodeWithJson(1, getMessage("login.accountNotNull"));
		}
		
		if (StringUtils.isEmpty(user.getUserPassword()))
		{
			return getRetCodeWithJson(1, getMessage("login.passwordNotNull"));
		}
		
		// 先检查验证码是否正确  FuHuiTongContext.VERIFY_CODE
		if (StringUtils.isEmpty(user.getVerifyCode()))
		{
			return getRetCodeWithJson(1, getMessage("login.verifyCodeNotNull"));
		}
		
		String realVerifyCode = (String) session.getAttribute(FuHuiTongContext.VERIFY_CODE);
		if (realVerifyCode == null || !realVerifyCode.equalsIgnoreCase(user.getVerifyCode()))
		{
			return getRetCodeWithJson(1, getMessage("login.verifyCodeNotRight"));
		}
		
		// 先用第一账号登录
		MerchantUsers loggedUser = this.merUserMapper.login(user.getUserAccount());
		if (loggedUser == null)
		{
			// 用户不存在，再用第二账号登录
			loggedUser = this.merUserMapper.selectByMerCode(user.getUserAccount());
			if (loggedUser == null)
			{
				return getRetCodeWithJson(1, getMessage("login.loginFailed"));
			}
		}
		
		if (!loggedUser.getUserPassword().equalsIgnoreCase(MD5.encrypt(user.getUserPassword())))
		{
			// 密码不相同
			return getRetCodeWithJson(1, getMessage("login.loginFailed"));
		}
		
		// 更新登录时间和IP
		loggedUser.setLastLoginDate(new Date());
		loggedUser.setLastLoginIp(request.getRemoteAddr());
		this.merUserMapper.updateByPrimaryKeySelective(loggedUser);
		
		int userRole = loggedUser.getUserRole();
		ProductUserRole userRoleObj = this.productUserRoleMapper.selectByPrimaryKey(userRole);
		if (userRoleObj != null)
		{
			loggedUser.setUserRoleName(userRoleObj.getUserRoleName());
		}
		
		// 保存在session中
		session.setAttribute(CURRENT_MER_USER, loggedUser);
		
		return getRetCodeWithJson(0, "index.html");
	}
	
	@Override
	public String savePersonal(HttpServletRequest request, HttpSession session, MerchantUsers user) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			return getRetCodeWithJson(1, getMessage("login.merUserNotLogin"));
		}
		
		if (StringUtils.isEmpty(user.getUserName()))
		{
			return getRetCodeWithJson(1, getMessage("savePersonal.userNameNotNull"));
		}
		
		// 用户身份角色信息不能修改
		user.setSuperAdmin(this.merUser.isSuperAdmin());
		user.setUserRole(merUser.getUserRole());
		
		this.merUserMapper.updateByPrimaryKeySelective(user);
		
		// 刷新当前会话
		session.setAttribute(CURRENT_MER_USER, user);
		
		return getRetCodeWithJson(0, getMessage("savePersonal.success"));
	}

	@Override
	public String reloadMerUser(HttpServletRequest request, HttpSession session) {
		
		String userId = this.merUser.getMerUserId();
		
		MerchantUsers user = this.merUserMapper.selectByPrimaryKey(userId);
		
		// 刷新session
		session.setAttribute(this.CURRENT_MER_USER, user);
		
		return null;
	}

	@Override
	public String updateMerchantUserPwd(HttpServletRequest request, HttpSession session, MerchantUsers pwdObj) 
	{
		if (!this.merUserHasAccessRight(request, session))
		{
			return getRetCodeWithJson(1, getMessage("login.merUserNotLogin"));
		}
		
		if (StringUtils.isEmpty(pwdObj.getOldPwd()))
		{
			return getRetCodeWithJson(-1, getMessage("modifyPwdSave.oldPwdNotNull"));
		}
		
		if (StringUtils.isEmpty(pwdObj.getNewPwd1()))
		{
			return getRetCodeWithJson(-1, getMessage("modifyPwdSave.newPwd1NotNull"));
		}
		
		if (StringUtils.isEmpty(pwdObj.getNewPwd2()))
		{
			return getRetCodeWithJson(-1, getMessage("modifyPwdSave.newPwd2NotNull"));
		}
		
		// 两次输入新密码不相同
		if (!pwdObj.getNewPwd1().equals(pwdObj.getNewPwd2()))
		{
			return getRetCodeWithJson(-1, getMessage("modifyPwdSave.newPwdsNotSame"));
		}
		
		if (!MD5.encrypt(pwdObj.getOldPwd()).equals(this.merUser.getUserPassword()))
		{
			// 老密码不相同
			return getRetCodeWithJson(-1, getMessage("modifyPwdSave.oldPwdsNotRight"));
		}
		
		// 更新密码
		pwdObj.setMerUserId(this.merUser.getMerUserId());
		pwdObj.setUserPassword(MD5.encrypt(pwdObj.getNewPwd2()));
		this.merUser.setUserPassword(pwdObj.getUserPassword());
		
		this.merUserMapper.updatePassword(pwdObj);
		
		return getRetCodeWithJson(0, getMessage("modifyPwdSave.modPwdsSuccess"));
	}

	@Override
	public String merchantGroupsData(HttpServletRequest request, HttpSession session) 
	{
		if (!this.merUserHasAccessRight(request, session))
		{
			return getTableData(null, "", 0);
		}
		
		ArrayList<MerchantsGroups> groups = this.merGroupsMapper.selectAllGroups(this.merUser.getMerId());
		if (groups != null && !groups.isEmpty())
		{
			return getTableData(groups, "", groups.size());
		}
		
		return getTableData(null, "", 0);
	}

	@Override
	public String saveMerGroup(HttpServletRequest request, HttpSession session, MerchantsGroups group) 
	{
		if (!this.merUserHasAccessRight(request, session))
		{
			return getRetCodeWithJson(1, getMessage("login.merUserNotLogin"));
		}
		
		if (StringUtils.isEmpty(group.getGroupName()))
		{
			return getRetCodeWithJson(1, getMessage("saveMerGroup.groupNameNotNull"));
		}
		
		if (StringUtils.isEmpty(group.getMerGroupId()))
		{
			// 新增
			group.setMerGroupId(MyUUID.randomUUID());
			group.setCreateDate(new Date());
			group.setGroupIndex(0);
			group.setGroupType(0);
			group.setMerId(merUser.getMerId());
			
			this.merGroupsMapper.insertSelective(group);
			
			return getRetCodeWithJson(0, getMessage("saveMerGroup.createSuccess"));
		}
		else
		{
			// 更新
			this.merGroupsMapper.updateByPrimaryKeySelective(group);
			
			return getRetCodeWithJson(0, getMessage("saveMerGroup.modifySuccess"));
		}
		
	}

	@Override
	public MerchantsGroups loadMerGroup(String merGroupId) 
	{
		return this.merGroupsMapper.selectByPrimaryKey(merGroupId);
	}

	@Override
	public String deleteMerchantGroups(HttpServletRequest request, HttpSession session, String merGroupId) 
	{
		if (!this.merUserHasAccessRight(request, session))
		{
			return getRetCodeWithJson(1, getMessage("login.merUserNotLogin"));
		}
		
		if (!StringUtils.isEmpty(merGroupId))
		{
			this.merGroupsMapper.deleteByPrimaryKey(merGroupId);
		}
		
		return getRetCodeWithJson(0);
	}

	@Override
	public String merchantGroupsUsersData(HttpServletRequest request, HttpSession session, String merGroupId) 
	{
		if (!this.merUserHasAccessRight(request, session))
		{
			return getTableData(null, null, 0);
		}
		
		MerchantUsers query = new MerchantUsers();
		query.setMerId(this.merUser.getMerId());
		
		if (!StringUtils.isEmpty(merGroupId))
		{
			query.setMerGroupId(merGroupId);
		}
		
		ArrayList<MerchantUsers> merUsers = this.merUserMapper.selectByMerGroupId(query);
		
		return getTableData(merUsers, "", merUsers.size());
	}

	@Override
	public String merGroupsUsers(HttpServletRequest request, HttpSession session) 
	{
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		// 查询所有部门
		String merId = this.merUser.getMerId();
		ArrayList<MerchantsGroups> groups = this.merGroupsMapper.selectAllGroups(merId);
		
		if (groups != null && !groups.isEmpty())
		{
			session.setAttribute("MerchantsGroups", groups);
		}
		
		logger.info("Current user was logged in.");
		return "page/merchant_users";
	}

	@Override
	public String merGroupsUsersInfo(HttpServletRequest request, HttpSession session, String merUserId) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		session.removeAttribute("CurrMerUser");
		
		// 加载所有的可见角色
		ArrayList<MerchantUserRole> userRoles = this.merUserRoleMapper.selectByMerId(this.merUser.getMerId());
		
		if (!StringUtils.isEmpty(merUserId))
		{
			// 加载用户信息
			MerchantUsers user = this.merUserMapper.selectByPrimaryKey(merUserId);
			if (user != null)
			{
				session.setAttribute("CurrMerUser", user);
			}
			
			// 查询所有部门
			String merId = this.merUser.getMerId();
			ArrayList<MerchantsGroups> groups = this.merGroupsMapper.selectAllGroups(merId);
			
			if (groups != null && !groups.isEmpty())
			{
				if (!StringUtils.isEmpty(user.getMerGroupId()))
				{
					for (MerchantsGroups group : groups)
					{
						if (group.getMerGroupId().equalsIgnoreCase(user.getMerGroupId()))
						{
							group.setSelected(" selected ");
						}
					}
				}
				session.setAttribute("MerchantsGroups", groups);
			}
			
			// 进入修改页面
			if (userRoles != null && !userRoles.isEmpty())
			{
				for (MerchantUserRole role : userRoles)
				{
					if (role.getMerUserRoleId() == user.getUserRole())
					{
						role.setSelected(" selected ");
					}
						
				}
				session.setAttribute("MerchantUserRole", userRoles);
			}
			
			return "page/merchant_users_info2";
		}
		else
		{
			if (userRoles != null && !userRoles.isEmpty())
			{
				session.setAttribute("MerchantUserRole", userRoles);
			}
			
			return "page/merchant_users_info";
		}
	}

	@Override
	public String saveMerGroupsUsers(HttpServletRequest request, HttpSession session, MerchantUsers user) 
	{
		if (!this.merUserHasAccessRight(request, session))
		{
			return getRetCodeWithJson(1, getMessage("login.merUserNotLogin"));
		}
		
		if (StringUtils.isEmpty(user.getUserAccount()))
		{
			return getRetCodeWithJson(1, getMessage("saveMerGroupsUsers.userAccountNotNull"));
		}
		
		if (StringUtils.isEmpty(user.getMerGroupId()))
		{
			return getRetCodeWithJson(1, getMessage("saveMerGroupsUsers.merGroupIdNotNull"));
		}
		
		if (StringUtils.isEmpty(user.getUserName()))
		{
			return getRetCodeWithJson(1, getMessage("saveMerGroupsUsers.userNameNotNull"));
		}
		
		if (StringUtils.isEmpty(user.getUserPhone()))
		{
			return getRetCodeWithJson(1, getMessage("saveMerGroupsUsers.userPhoneNotNull"));
		}
		
		if (StringUtils.isEmpty(user.getMerUserId()))
		{
			// 检查用户账号是否重复
			if (this.merUserMapper.login(user.getUserAccount()) != null)
			{
				logger.warn(user.getUserAccount() + " already exists.");
				return getRetCodeWithJson(1, getMessage("saveMerGroupsUsers.userAccountExists"));
			}
			
			// 新增
			user.setMerUserId(MyUUID.randomUUID());
			user.setMerId(this.merUser.getMerId());
			user.setMerName(this.merUser.getMerName());
			
			String defaultPwd = this.sysDictMapper.selectByPrimaryKey(FuHuiTongContext.PARAM_DEFAULT_PASSWORD).getDicValues();
			user.setUserPassword(MD5.encrypt(defaultPwd));
			
			// 用户角色，内部用户
//			user.setUserRole(SysDefaultRole.ROLE_MERCHANT_USER);
			user.setSuperAdmin(false);
			
			this.merUserMapper.insertSelective(user);
			
			return getRetCodeWithJson(0, getMessage("saveMerGroupsUsers.createUserSuccess"));
		}
		else
		{
			// 修改
			this.merUserMapper.updateByPrimaryKeySelective(user);
			
			return getRetCodeWithJson(0, getMessage("updateUserSuccess.createUserSuccess"));
		}
	}

	@Override
	public String deleteMerGroupsUsers(HttpServletRequest request, HttpSession session, String merUserId) 
	{
		if (!this.merUserHasAccessRight(request, session))
		{
			return getRetCodeWithJson(1, getMessage("login.merUserNotLogin"));
		}
		
		if (StringUtils.isEmpty(merUserId))
		{
			logger.warn("userId was null!");
			return getRetCodeWithJson(1, getMessage("deleteMerGroupsUsers.merUserIdNull"));
		}
		
		// 如果是缺省管理员用户，则不允许删除
		MerchantUsers user = this.merUserMapper.selectByPrimaryKey(merUserId);
		if (user.isSuperAdmin())
		{
			logger.info("The super admin " + user.getUserAccount() + "/" + user.getMerCode() + " can not be deleted.");
			return getRetCodeWithJson(1, getMessage("deleteMerGroupsUsers.sysDefaultSuperAdminCantDeleted"));
		}
		
		this.merUserMapper.deleteByPrimaryKey(merUserId);
		
		return getRetCodeWithJson(0);
	}

	@Override
	public String merMerchantStores(HttpServletRequest request, HttpSession session) 
	{
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		// 查询地区
		// 加载所有顶级地区
		ArrayList<Areas> areas = this.areaMapper.selectTopAreas();
		if (areas != null && !areas.isEmpty())
		{
			session.setAttribute("Provinces", areas);
		}
		
		return "page/merchant_stores";
	}

	@Override
	public String merStoresData(HttpServletRequest request, HttpSession session, MerchantsGroups store) 
	{
		if (!this.merUserHasAccessRight(request, session))
		{
			return getTableData(null, "", 0);
		}
		if (StringUtils.isEmpty(store.getAreaId()))
		{
			if (!StringUtils.isEmpty(store.getCity()))
			{
				store.setAreaId(store.getCity());
			}
			else
			{
				store.setAreaId(store.getProvince());
			}
		}
		if (StringUtils.isEmpty(store.getAreaId()))
		{
			store.setAreaId(null);
		}
		else
		{
			String areaId = store.getAreaId();
			if (areaId.endsWith("0000"))
			{
				areaId = areaId.substring(0, 2);
			}
			else if (areaId.endsWith("00"))
			{
				areaId = areaId.substring(0, 4);
			}
			store.setAreaId(areaId);
		}
		
		if (StringUtils.isEmpty(store.getGroupName()))
		{
			store.setGroupName(null);
		}
		else
		{
			try {
				String groupName = new String(store.getGroupName().getBytes("iso-8859-1"), "utf-8");
				logger.info(groupName);
				store.setGroupName(groupName);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		store.setMerId(this.merUser.getMerId());
		Integer dataCount = this.merGroupsMapper.selectAllStores2Count(store);
		if (dataCount != null && dataCount.intValue() > 0)
		{
			ArrayList<MerchantsGroups> groups = this.merGroupsMapper.selectAllStores2(store);
			if (groups != null && !groups.isEmpty())
			{
				return getTableData(groups, "", dataCount);
			}
		}
		
		return getTableData(null, "", 0);
	}

	@Override
	public String merStoresInfo(HttpServletRequest request, HttpSession session, MerchantsGroups merGroup) 
	{
		if (!merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		// 查询地区
		// 加载所有顶级地区
		ArrayList<Areas> areas = this.areaMapper.selectTopAreas();
		if (areas != null && !areas.isEmpty())
		{
			session.setAttribute("Provinces", areas);
		}
		
		if (!StringUtils.isEmpty(merGroup.getMerGroupId()))
		{
			// 更新，从数据库中查询
			MerchantsGroups currMerStore = loadMerGroup(merGroup.getMerGroupId());
			session.setAttribute("CurrentMerStore", currMerStore);
			
			// 加载门店所处地区信息
			Areas area = this.areaMapper.selectByPrimaryKey(currMerStore.getAreaId());
			Areas cityArea = this.areaMapper.selectByPrimaryKey(area.getParentAreaId());
			Areas proviceArea = this.areaMapper.selectByPrimaryKey(cityArea.getParentAreaId());
			
			// 遍历省份，自动定位选中的项目
			for (Areas a : areas)
			{
				if (a.getAreaId().equals(proviceArea.getAreaId()))
				{
					a.setSelected(" selected ");
				}
			}
			
			// 获取选定的省份的所有城市列表
			ArrayList<Areas> cityAreaList = this.areaMapper.selectLevelAreas(proviceArea.getAreaId());
			
			// 遍历城市
			for (Areas a : cityAreaList)
			{
				if (a.getAreaId().equals(cityArea.getAreaId()))
				{
					a.setSelected(" selected ");
				}
			}
			session.setAttribute("CityAreas", cityAreaList);
			
			// 获取选定城市的区域列表
			ArrayList<Areas> areaList = this.areaMapper.selectLevelAreas(cityArea.getAreaId());
			
			// 遍历城市
			for (Areas a : areaList)
			{
				if (a.getAreaId().equals(area.getAreaId()))
				{
					a.setSelected(" selected ");
				}
			}
			session.setAttribute("AreasList", areaList);
			
			return "page/merchant_stores_info2";
		}
		else
		{
			return "page/merchant_stores_info";
		}
		
	}

	@Override
	public String getAreas(HttpServletRequest request, HttpSession session, String parentAreaId)
	{
		// 判断是否已经登录，如果已经登录，则重定向到主页，否则重定向到登录界面
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.warn("Current user was not logged in.");
			return getTableData(null, null, 0);
		}
		
		ArrayList<Areas> areas = null;
		
		if (StringUtils.isEmpty(parentAreaId))
		{
			areas = this.areaMapper.selectTopAreas();
		}
		else
		{
			areas = this.areaMapper.selectLevelAreas(parentAreaId);
		}
		
		if (areas != null && !areas.isEmpty())
		{
			return getTableData(areas, "", areas.size());
		}
		else
		{
			return getTableData(null, null, 0);
		}
	}

	@Override
	public String saveMerStore(HttpServletRequest request, HttpSession session, MerchantsGroups store) 
	{
		if (!this.merUserHasAccessRight(request, session))
		{
			return getRetCodeWithJson(1, getMessage("login.merUserNotLogin"));
		}
		
		if (StringUtils.isEmpty(store.getGroupName()))
		{
			return getRetCodeWithJson(1, getMessage("saveMerStore.groupNameNotNull"));
		}
		
		// 检查地区
		if (StringUtils.isEmpty(store.getAreaId()))
		{
			return getRetCodeWithJson(1, getMessage("saveMerStore.areaNotNull"));
		}
		
		if (StringUtils.isEmpty(store.getMerGroupId()))
		{
			// 新增分店
			
			// 检查店长信息
			if (StringUtils.isEmpty(store.getUserAccount()))
			{
				return getRetCodeWithJson(1, getMessage("saveMerStore.userAccountNotNull"));
			}
			
			// 检查账号是否重复
			if (this.merUserMapper.login(store.getUserAccount()) != null)
			{
				return getRetCodeWithJson(1, getMessage("saveMerStore.userAccountExists"));
			}
			
			// 店长姓名
			if (StringUtils.isEmpty(store.getUserName()))
			{
				return getRetCodeWithJson(1, getMessage("saveMerStore.userNameNotNull"));
			}
			
			// 店长手机号
			if (StringUtils.isEmpty(store.getUserPhone()))
			{
				return getRetCodeWithJson(1, getMessage("saveMerStore.userPhoneNotNull"));
			}
			
			store.setMerGroupId(MyUUID.randomUUID());
			store.setCreateDate(new Date());
			store.setGroupIndex(0);
			store.setGroupType(3);
			store.setMerId(merUser.getMerId());
			
			this.merGroupsMapper.insertSelective(store);
			
			// 生成店长用户
			MerchantUsers storeMaster = new MerchantUsers();
			storeMaster.setMerUserId(MyUUID.randomUUID());
			storeMaster.setMerGroupId(store.getMerGroupId());
			storeMaster.setUserName(store.getUserName());
			storeMaster.setMerId(this.merUser.getMerId());
			storeMaster.setMerName(this.merUser.getMerName());
			storeMaster.setUserAccount(store.getUserAccount());
			storeMaster.setUserPhone(store.getUserPhone());
			storeMaster.setSuperAdmin(false);
			// 设置店长身份
			
			// 查询店长身份
			MerchantUserRole query = new MerchantUserRole();
			query.setMerId(this.merUser.getMerId());
			query.setSysDef(SysDefaultRole.ROLE_STORE_MASTER);
			
			MerchantUserRole masterRole = this.merUserRoleMapper.selectDefRoleByMerId(query);
			
			storeMaster.setUserRole(masterRole.getMerUserRoleId());
			storeMaster.setUserMemo("店长");
			
			// 设置完整行政规划地址
			store.setAreaFullName(this.areaMapper.selectByPrimaryKey(store.getAreaId()).getFullName());
			
			// 设置密码
			storeMaster.setUserPassword(MD5.encrypt(this.sysDictMapper.selectByPrimaryKey(FuHuiTongContext.PARAM_DEFAULT_PASSWORD).getDicValues()));
			
			// 写入到数据库中
			this.merUserMapper.insertSelective(storeMaster);
			
//			// 连续发送两封邮件，一封给企业管理员，另外一封给平台运营管理员
//			// 查询当前商户系统管理员账号
//			
//			Merchants merchant = this.merchantMapper.selectByPrimaryKey(this.merUser.getMerId());
//			
//			ArrayList<MerchantUsers> superUsers = this.merUserMapper.selectSuperAdmin(this.merUser.getMerId());
//			if (superUsers != null && !superUsers.isEmpty())
//			{
//				sendStoreEmail(superUsers.get(0).getUserEmail(), merchant, store, superUsers.get(0));
//			}
//			
//			// 给系统管理员发邮件
//			String sysEmail = this.sysDictMapper.selectByPrimaryKey(FuHuiTongContext.PARAM_SYSTEM_EMAIL).getDicValues();
//			sendStoreEmail(sysEmail, merchant, store, superUsers.get(0));
			
			return getRetCodeWithJson(0, getMessage("saveMerStore.createSuccess"));
		}
		else
		{
			// 更新
			this.merGroupsMapper.updateByPrimaryKeySelective(store);
			
			return getRetCodeWithJson(0, getMessage("saveMerStore.modifySuccess"));
		}
	}

	@Override
	public String deleteMerchantStores(HttpServletRequest request, HttpSession session, String merGroupId) 
	{
		if (!this.merUserHasAccessRight(request, session))
		{
			return getRetCodeWithJson(1, getMessage("login.merUserNotLogin"));
		}
		
		if (!StringUtils.isEmpty(merGroupId))
		{
			this.merGroupsMapper.deleteByPrimaryKey(merGroupId);
		}
		
		return getRetCodeWithJson(0);
	}
	
	private void sendStoreEmail(String to, Merchants merchant, MerchantsGroups store, MerchantUsers superUser)
	{
		String emailHost = this.sysDictMapper.selectByPrimaryKey(FuHuiTongContext.PARAM_SMTP_SERVER).getDicValues();
		int smtpPort = Integer.parseInt(this.sysDictMapper.selectByPrimaryKey(FuHuiTongContext.PARAM_SMTP_SERVER_PORT).getDicValues());
		String smtpUser = this.sysDictMapper.selectByPrimaryKey(FuHuiTongContext.PARAM_SMTP_SENDER).getDicValues();
		String smtpPwd = this.sysDictMapper.selectByPrimaryKey(FuHuiTongContext.PARAM_SMTP_PASSWORD).getDicValues();
		
		String defaultPwd = this.sysDictMapper.selectByPrimaryKey(FuHuiTongContext.PARAM_DEFAULT_PASSWORD).getDicValues();
		
		String webUrl = this.sysDictMapper.selectByPrimaryKey(FuHuiTongContext.PARAM_WEB_PORTAL_URL).getDicValues();
		
		String csPhone = this.sysDictMapper.selectByPrimaryKey(FuHuiTongContext.PARAM_CUSTOMER_SERVICE_PHONE).getDicValues();

		String emailTempFile = FuHuiTongContext.getInstance().getWebAppDir() + "WEB-INF" + File.separator + "assets" + File.separator;
		emailTempFile += this.sysDictMapper.selectByPrimaryKey(FuHuiTongContext.PARAM_CREATING_STORE_EMAIL_TEMP).getDicValues();
		
		String emailTitle = this.sysDictMapper.selectByPrimaryKey(FuHuiTongContext.PARAM_CREATING_STORE_EMAIL_TITLE).getDicValues();
		logger.info("title is " + emailTitle);
		
		try {
			String emailMessage = FileUtils.readFileToString(new File(emailTempFile), "utf-8");
			
			// 替换变量，亲爱的{merName}的{linkMan}：
			emailMessage = StringUtils.replace(emailMessage, "{merName}", merchant.getMerFullName());
			emailMessage = StringUtils.replace(emailMessage, "{linkMan}", merchant.getLinkMan());
			
			// <p> 我们已经成功为您新建门店 <b>{store}</b>，信息如下：</p>
			emailMessage = StringUtils.replace(emailMessage, "{store}", store.getGroupName());
			
			// <p> 地址：{address}</p>
			emailMessage = StringUtils.replace(emailMessage, "{address}", store.getAreaFullName() + "," + store.getDetailAddress());
			
			// <p> 店长：{storeMaster},联系电话为:{userPhone},登录账号为 <b>{userAccount}</b>,密码是<b>{password}</b></p>
			emailMessage = StringUtils.replace(emailMessage, "{storeMaster}", store.getUserName());
			emailMessage = StringUtils.replace(emailMessage, "{userPhone}", store.getUserPhone());
			emailMessage = StringUtils.replace(emailMessage, "{userAccount}", store.getUserAccount());
			emailMessage = StringUtils.replace(emailMessage, "{password}", defaultPwd);
			
			//{web.url}
			emailMessage = StringUtils.replace(emailMessage, "{web.url}", webUrl);
			
			// {customer.service.phone}
			emailMessage = StringUtils.replace(emailMessage, "{customer.service.phone}", csPhone);
			
			HtmlEmail htmlEmail = new HtmlEmail();
			htmlEmail.setHostName(emailHost);
			htmlEmail.setSmtpPort(smtpPort);
			htmlEmail.setAuthentication(smtpUser, smtpPwd);
			htmlEmail.setCharset("UTF-8");
			
			htmlEmail.setFrom(smtpUser);
			htmlEmail.setSubject(emailTitle);
			
			// 先发送给管理员
			htmlEmail.setHtmlMsg(emailMessage);
			htmlEmail.addTo(to);
			htmlEmail.send();
		} catch (IOException | EmailException e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
	}

	@Override
	public String merStoresUsers(HttpServletRequest request, HttpSession session, String merGroupId) 
	{
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		if (!StringUtils.isEmpty(merGroupId))
		{
			MerchantsGroups store = this.merGroupsMapper.selectByPrimaryKey(merGroupId);
			if (store != null)
			{
				session.setAttribute("CurrentStore", store);
			}
		}
		
		return "page/merchant_stores_users";
	}

	@Override
	public String merStoresUsersData(HttpServletRequest request, HttpSession session, String merGroupId) 
	{
		if (!this.merUserHasAccessRight(request, session))
		{
			return getTableData(null, null, 0);
		}
		
		MerchantUsers query = new MerchantUsers();
		query.setMerId(this.merUser.getMerId());
		
		if (!StringUtils.isEmpty(merGroupId))
		{
			query.setMerGroupId(merGroupId);
		}
		
		ArrayList<MerchantUsers> merUsers = this.merUserMapper.selectByMerStoreId(query);
		
		return getTableData(merUsers, "", merUsers.size());
	}

	@Override
	public String merStoresUsersInfo(HttpServletRequest request, HttpSession session, MerchantUsers storeUser) 
	{
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		if (!StringUtils.isEmpty(storeUser.getMerUserId()))
		{
			MerchantUsers currStoreUser = this.merUserMapper.selectByPrimaryKey(storeUser.getMerUserId());
			session.setAttribute("CurrentStoreUser", currStoreUser);
			
			return "page/merchant_stores_users_info2";
		}
		else
		{
			return "page/merchant_stores_users_info";
		}
	}

	@Override
	public String saveMerStoresUsers(HttpServletRequest request, HttpSession session, MerchantUsers storeUser) 
	{
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1, getMessage("login.merUserNotLogin"));
		}
		
		if (StringUtils.isEmpty(storeUser.getUserAccount()))
		{
			return getRetCodeWithJson(1, getMessage("saveMerStoresUsers.userAccountNotNull"));
		}
		
		if (StringUtils.isEmpty(storeUser.getUserName()))
		{
			return getRetCodeWithJson(1, getMessage("saveMerStoresUsers.userNameNotNull"));
		}

		if (StringUtils.isEmpty(storeUser.getUserPhone()))
		{
			return getRetCodeWithJson(1, getMessage("saveMerStoresUsers.userPhoneNotNull"));
		}
		
		if (!StringUtils.isEmpty(storeUser.getMerUserId()))
		{
			// 修改
			this.merUserMapper.updateByPrimaryKeySelective(storeUser);
			
			session.removeAttribute("CurrentStoreUser");
			
			return getRetCodeWithJson(0, getMessage("saveMerStoresUsers.modifySuccess"));
		}
		else
		{
			// 新增，判断账号是否存在
			if (this.merUserMapper.login(storeUser.getUserAccount()) != null)
			{
				return getRetCodeWithJson(1, getMessage("saveMerStoresUsers.userAccountExists"));
			}
			
			MerchantsGroups store = (MerchantsGroups) session.getAttribute("CurrentStore");
			
			// 新增店员用户
			MerchantUsers storeOperator = new MerchantUsers();
			storeOperator.setMerUserId(MyUUID.randomUUID());
			
			// 生成店长用户
			storeOperator.setMerGroupId(store.getMerGroupId());
			storeOperator.setUserName(storeUser.getUserName());
			storeOperator.setMerId(this.merUser.getMerId());
			storeOperator.setMerName(this.merUser.getMerName());
			storeOperator.setUserAccount(storeUser.getUserAccount());
			storeOperator.setUserPhone(storeUser.getUserPhone());
			storeOperator.setSuperAdmin(false);
			
			String defaultPwd = this.sysDictMapper.selectByPrimaryKey(FuHuiTongContext.PARAM_DEFAULT_PASSWORD).getDicValues();
			storeOperator.setUserPassword(MD5.encrypt(defaultPwd));
			
			// TODO 设置店员身份
			
			// 查询店员身份
			MerchantUserRole query = new MerchantUserRole();
			query.setMerId(this.merUser.getMerId());
			query.setSysDef(SysDefaultRole.ROLE_STORE_OPERATOR);
			
			MerchantUserRole opRole = this.merUserRoleMapper.selectDefRoleByMerId(query);
			storeOperator.setUserRole(opRole.getMerUserRoleId());
			storeOperator.setUserMemo("店员");
			
			this.merUserMapper.insertSelective(storeOperator);
			
			return getRetCodeWithJson(0);
		}
	}

	@Override
	public String deleteMerStoresUsers(HttpServletRequest request, HttpSession session, MerchantUsers storeUser) 
	{
		if (!this.merUserHasAccessRight(request, session))
		{
			return getRetCodeWithJson(1, getMessage("login.merUserNotLogin"));
		}
		
		if (StringUtils.isEmpty(storeUser.getMerUserId()))
		{
			logger.warn("userId was null!");
			return getRetCodeWithJson(1, getMessage("deleteMerGroupsUsers.merUserIdNull"));
		}
		
		// 如果是店长用户，则不允许删除
		MerchantUsers user = this.merUserMapper.selectByPrimaryKey(storeUser.getMerUserId());
		if (user.getUserRole() == SysDefaultRole.ROLE_STORE_MASTER)
		{
			logger.info("The store master " + user.getUserAccount() + " can not be deleted.");
			return getRetCodeWithJson(1, getMessage("deleteMerStoreUsers.storeMasterCantDeleted"));
		}
		
		this.merUserMapper.deleteByPrimaryKey(storeUser.getMerUserId());
		
		return getRetCodeWithJson(0);
	}

	@Override
	public String changeMerUserStatus(HttpServletRequest request, HttpSession session, MerchantUsers user) {
		if (!this.merUserHasAccessRight(request, session))
		{
			return getRetCodeWithJson(1, getMessage("login.merUserNotLogin"));
		}
		
		if (!StringUtils.isEmpty(user.getMerUserId()))
		{
			// 系统管理员不能禁用
			// 如果是缺省管理员用户，则不允许删除
			MerchantUsers queryUser = this.merUserMapper.selectByPrimaryKey(user.getMerUserId());
			if (queryUser.isSuperAdmin())
			{
				logger.info("The super admin " + user.getUserAccount() + "/" + user.getMerCode() + " can not be changed.");
				return getRetCodeWithJson(1, getMessage("changeMerUsers.sysDefaultSuperAdminCantChange"));
			}
			
			this.merUserMapper.changeUserStatus(user);
			return getRetCodeWithJson(0);
		}
		else
		{
			return getRetCodeWithJson(1);
		}
	}

	@Override
	public void verifyCode(HttpSession session, HttpServletResponse response, DefaultKaptcha captchaProducer) {
		
		// 得到图形验证码的内容
		String capText = captchaProducer.createText();  
		
		// 保存到当前会话中
		session.setAttribute(FuHuiTongContext.VERIFY_CODE, capText);
		BufferedImage bi = captchaProducer.createImage(capText);
		response.setContentType("image/jpeg");
		
        try {
			ServletOutputStream out = response.getOutputStream();
			ImageIO.write(bi, "jpg", out);  
			out.flush();

			IOUtils.closeQuietly(out);
		} catch (IOException e) {
			e.printStackTrace();
		}  
        
	}

	@Override
	public String merGoodsProviders(HttpServletRequest request, HttpSession session) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		return "page/merchant_providers";
	}

	@Override
	public String merGoodsProvidersData(HttpServletRequest request, HttpSession session, Integer status) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			return getTableData(null, null, 0);
		}
		
		MerchantUsers param = new MerchantUsers();
		param.setMerId(this.merUser.getMerId());
		if (status != null)
		{
			param.setStatus(status);
		}
		else
		{
			param.setStatus(null);
		}
		
		ArrayList<MerchantUsers> gps = this.merUserMapper.selectGoodsProviders(param);
		
		if (gps == null || gps.isEmpty())
		{
			return getTableData(null, null, 0);
		}
		
		return getTableData(gps, "", gps.size());
	}

	@Override
	public String merGoodsProviderInfo(HttpServletRequest request, HttpSession session, MerchantUsers merUser) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		session.removeAttribute("CurrentGpsUser");
		
		if (!StringUtils.isEmpty(merUser.getMerUserId()))
		{
			MerchantUsers currGpsUser = this.merUserMapper.selectByPrimaryKey(merUser.getMerUserId());
			if (currGpsUser != null)
			{
				session.setAttribute("CurrentGpsUser", currGpsUser);
			}
		}
		
		return "page/merchant_gps_info";
	}

	@Override
	public String saveMerGoodsProviders(HttpServletRequest request, HttpSession session, MerchantUsers merUser) {

		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1, getMessage("login.merUserNotLogin"));
		}
		
		if (StringUtils.isEmpty(merUser.getUserName())){
			return getRetCodeWithJson(1, getMessage("saveMerGoodsProviders.userNameNotNull"));
		}
		
//		if (StringUtils.isEmpty(merUser.getUserAccount())){
//			return getRetCodeWithJson(1, getMessage("saveMerGoodsProviders.userAccountNotNull"));
//		}
		
		if (StringUtils.isEmpty(merUser.getMerName())){
			return getRetCodeWithJson(1, getMessage("saveMerGoodsProviders.merNameNotNull"));
		}
		
		if (StringUtils.isEmpty(merUser.getUserPhone())){
			return getRetCodeWithJson(1, getMessage("saveMerGoodsProviders.userPhoneNotNull"));
		}
		
		if (StringUtils.isEmpty(merUser.getUserEmail())){
			return getRetCodeWithJson(1, getMessage("saveMerGoodsProviders.userEmailNotNull"));
		}
		
		if (StringUtils.isEmpty(merUser.getMerUserId()))
		{
			// 先判断登录账号是否存在
//			if (this.merUserMapper.login(merUser.getUserAccount()) != null)
//			{
//				return getRetCodeWithJson(1, getMessage("saveMerGoodsProviders.userAccountExists"));
//			}
			
			merUser.setUserAccount(merUser.getUserPhone() + "_" + StringUtilEx.getRandString());
			// 新建供应商，自动生成账号和id
			merUser.setMerUserId(MyUUID.randomUUID());
			
			String defaultPwd = this.sysDictMapper.selectByPrimaryKey(FuHuiTongContext.PARAM_DEFAULT_PASSWORD).getDicValues();
			merUser.setUserPassword(MD5.encrypt(defaultPwd));
			merUser.setSuperAdmin(false);
			merUser.setStatus(0);
			
			this.merUserMapper.insertSelective(merUser);
			
//			// TODO 发送短信
//			logger.info("Send sms to provider.");
//			try
//			{
//				String webUrl = this.sysDictMapper.selectByPrimaryKey(FuHuiTongContext.PARAM_WEB_PORTAL_URL).getDicValues();
//				this.shortMessageService.sendProviderRegMessage(merUser.getUserPhone(), merUser.getMerName(), 
//						merUser.getUserName(), merUser.getUserAccount(), defaultPwd, webUrl);
//			}
//			catch(Exception e)
//			{
//				logger.error(e.toString());
//			}
			
			// 生成供应链关系表
			MerchantClients client = new MerchantClients();
			client.setMerId(this.merUser.getMerId());
			client.setMerUserId(merUser.getMerUserId());
			// 客户关系类型  0：下游分销商，1：上游客户
			client.setRelationship(1);
			
			this.merClientMapper.insertSelective(client);
			
			return getRetCodeWithJson(0);
		}
		else
		{
			// 先判断登录账号是否存在
//			MerchantUsers queryUser = this.merUserMapper.login(merUser.getUserAccount());
//			if (queryUser != null && !queryUser.getMerUserId().equalsIgnoreCase(merUser.getMerUserId()))
//			{
//				return getRetCodeWithJson(1, getMessage("saveMerGoodsProviders.userAccountExists"));
//			}
						
			this.merUserMapper.updateGoodsProvider(merUser);
			
			session.removeAttribute("CurrentGpsUser");
			
			return getRetCodeWithJson(0);
		}
	}

	@Override
	public String deleteMerGoodsProviders(HttpServletRequest request, HttpSession session, MerchantUsers merUser) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1, getMessage("login.merUserNotLogin"));
		}
		
		if (!StringUtils.isEmpty(merUser.getMerUserId()))
		{
			this.merUserMapper.deleteByPrimaryKey(merUser.getMerUserId());
		}
		
		return getRetCodeWithJson(0);
	}

	@Override
	public String merProviderGoods(HttpServletRequest request, HttpSession session, MerchantUsers merUser) {
		
		if (!this.merUserHasAccessRight(request, session) || StringUtils.isEmpty(merUser.getMerUserId()))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		MerchantUsers goodsProvider = this.merUserMapper.selectByPrimaryKey(merUser.getMerUserId());
		if (goodsProvider != null)
		{
			session.setAttribute("CurrentGpsUser", goodsProvider);
		}
		
		return "page/merchant_provider_goods";
	}

	@Override
	public String merProviderGoodsData(HttpServletRequest request, HttpSession session) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			return getTableData(null, null, 0);
		}
		
		MerchantUsers merUser = (MerchantUsers) session.getAttribute("CurrentGpsUser");
		
		ArrayList<GoodsInfo> merProviderGoods = this.goodsInfoMapper.selectMerProviderGoods(merUser.getMerUserId());
		if (merProviderGoods == null || merProviderGoods.isEmpty())
		{
			return getTableData(null, null, 0);
		}
		
		return getTableData(merProviderGoods, "", merProviderGoods.size());
	}

	@Override
	public String deleteMerProviderGoods(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo) {
		
		if (!this.merUserHasAccessRight(request, session) || StringUtils.isEmpty(goodsInfo.getGpId()))
		{
			return getRetCodeWithJson(1);
		}
		
		this.goodsProviderMapper.deleteByPrimaryKey(goodsInfo.getGpId());
		
		return getRetCodeWithJson(0);
	}

	@Override
	public String changeMerProviderGoodsStatus(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo) {
		
		if (!this.merUserHasAccessRight(request, session) || StringUtils.isEmpty(goodsInfo.getGpId()))
		{
			return getRetCodeWithJson(1);
		}
		
		GoodsProviders param = new GoodsProviders();
		param.setGpId(goodsInfo.getGpId());
		param.setGpStatus(goodsInfo.getGpStatus());
		
		this.goodsProviderMapper.changeGpStatus(param);
		
		return getRetCodeWithJson(0);
	}

	@Override
	public String menuData(HttpServletRequest request, HttpSession session) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			return "[]";
		}
		
		ArrayList<UserRoleMenus> userMenus = this.userRoleMenusMapper.selectByUserRole(this.merUser.getUserRole());
		if (userMenus == null || userMenus.isEmpty())
		{
			return "[]";
		}
		
		// 放到一个查询表中
		Hashtable<Integer, UserRoleMenus> userMenusTable = new Hashtable<>();
		for (UserRoleMenus userMenu : userMenus)
		{
			userMenusTable.put(userMenu.getProductMenuId(), userMenu);
		}

		// 加载系统所有菜单
		ArrayList<ProductMenus> allMenus = this.productMenusMapper.selectAllMenus();
		
		// 把用户拥有的权限选择上
		for (ProductMenus menu : allMenus)
		{
			if (userMenusTable.get(menu.getProductMenuId()) != null)
			{
				menu.setSelected(true);
			}
		}
		
		// 组装成一个树形机构
		ArrayList<ProductMenus> topMenus = new ArrayList<ProductMenus>();
		
		// 按照递归方式，组成树形结构，首先取出顶级节点
		for (ProductMenus menu : allMenus)
		{
			if (menu.getTopProductMenuId() == null)
			{
				topMenus.add(menu);
			}
		}
		
		// 从数据集合中去掉顶级节点
		for (ProductMenus menu : topMenus)
		{
			allMenus.remove(menu);
		}
		
		// 利用递归开始将剩下的子节点装入到各个top节点的children中
		for (ProductMenus menu : topMenus)
		{
			loadTreeNodes(menu, allMenus);
		}
		
		// 此时组成了系统功能菜单全集，需要把 selected = false的叶子节点去掉，保留非叶子节点
		visitChildren(topMenus);
		
		// 输出JSON数据
		StringBuffer tempBuffer = new StringBuffer();
		tempBuffer.append("[");
		for (ProductMenus menu : topMenus)
		{
			tempBuffer.append(menu.toJsonMenuString()).append(",");
		}
		if (topMenus.size() > 0)
		{
			tempBuffer.deleteCharAt(tempBuffer.length() - 1);
		}
		tempBuffer.append("]");
		
		logger.info(tempBuffer.toString());
		
		return tempBuffer.toString();
	}
	
	private void visitChildren(ArrayList<ProductMenus> childrenMenu)
	{
		ArrayList<ProductMenus> branchNodes = new ArrayList<>();
		
		// 先找出未选中的叶子节点
		ArrayList<ProductMenus> leafNodes = new ArrayList<>();
		
		for (ProductMenus menu : childrenMenu)
		{
			if ((menu.getChildren() == null || menu.getChildren().size() == 0) && !menu.isSelected())
			{
				leafNodes.add(menu);
			}
			
			// 统计非叶子节点
			if (menu.getChildren() != null && menu.getChildren().size() > 0)
			{
				branchNodes.add(menu);
			}
		}
		
		// 删除非选中叶子节点
		for (ProductMenus leaf : leafNodes)
		{
			childrenMenu.remove(leaf);
		}
		
		leafNodes.clear();
		
		// 继续遍历当前剩余非叶子节点
		for (ProductMenus branch : branchNodes)
		{
			visitChildren(branch.getChildren());
			
			// 清理完下级节点后，本身又成了叶子节点
			if (branch.getChildren().size() == 0)
			{
				leafNodes.add(branch);
			}
		}
		
		// 删除清空了下级叶子节点之后形成的新叶子节点
		for (ProductMenus leaf : leafNodes)
		{
			childrenMenu.remove(leaf);
		}
	}
	
	private void loadTreeNodes(ProductMenus topMenu, ArrayList<ProductMenus> allMenus)
	{
		ArrayList<ProductMenus> subList = new ArrayList<ProductMenus>();
		
		// 找出所有属于 parentGroup 的子节点
		for (ProductMenus menu : allMenus)
		{
			if (menu.getTopProductMenuId().intValue() == topMenu.getProductMenuId().intValue())
			{
				subList.add(menu);
			}
		}
		
		if (subList.size() > 0)
		{
			// 从当前allMenus去掉已经找到的子节点
			for (ProductMenus menu : subList)
			{
				allMenus.remove(menu);
				
				// 装入 parentGroup的children中
				topMenu.addChild(menu);
			}
			
			// 继续执行剩余子节点
			for (ProductMenus menu : subList)
			{
				loadTreeNodes(menu, allMenus);
			}
		}
	}
}
