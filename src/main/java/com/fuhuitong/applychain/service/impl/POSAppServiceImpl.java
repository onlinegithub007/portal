package com.fuhuitong.applychain.service.impl;

import com.alibaba.fastjson.JSON;
import com.fuhuitong.applychain.FuHuiTongContext;
import com.fuhuitong.applychain.OrderID;
import com.fuhuitong.applychain.dao.*;
import com.fuhuitong.applychain.model.*;
import com.fuhuitong.applychain.model.request.*;
import com.fuhuitong.applychain.model.response.*;
import com.fuhuitong.applychain.service.IPOSAppService;
import com.fuhuitong.applychain.utils.MD5;
import com.fuhuitong.applychain.utils.OrderBillUtils;
import com.fuhuitong.applychain.utils.StringUtilEx;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.UUID;

@Service
public class POSAppServiceImpl implements IPOSAppService {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Resource
	private AppVersionsMapper appVersionMapper;
	
	@Resource
	private SysDictionaryMapper sysDictMapper;
	
	@Resource
	private AppSoftwareMapper appSoftMapper;
	
	@Resource
	private AppDownloadMapper appDownMapper;
	
	@Resource
	private MerchantUsersMapper merchantUserMapper;
	
	@Resource
	private MerchantUsersSessionMapper merchantUserSessionMapper;
	
	@Resource
	private GoodsInfoMapper goodsInfoMapper;
	
	@Resource
	private GoodsStockDetailMapper goodsStockDetailMapper;
	
	@Resource
	private GoodsStockMapper goodsStockMapper;
	
	@Resource
	private OrderDetailsMapper orderDetailMapper;
	
	@Resource
	private MembersMapper memberMapper;
	
	@Resource
	private TempPosOrdersMapper tempPosOrderMapper;
	
	@Resource
	private MemberTokensMapper memberTokenMpper;
	
	@Resource
	private BankCardBinMapper bankCardBinMapper;
	
	@Resource
	private GoodsMemberPriceMapper goodsMemberPriceMapper;
	
	@Resource
	private CardCouponMapper cardCouponMapper;
	
	@Resource
	private OrdersMapper ordersMapper;
	
	@Resource
	private MerchantsGroupsMapper merchantGroupMapper;
	
	@Resource
	private MemberDiscountCouponMapper memberDiscountMapper;
	
	@Resource
	private MerchantsMapper merchantMapper;
	
	@Resource
	private ClientLevelMapper clientLevelMapper;
	
	@Resource
	private DiscountItemsMapper discountItemsMapper;
	
	@Resource
	private MerchantsSettingsMapper merchantsSettingsMapper;
	
	@Resource
	private BindingGoodsMapper bindingGoodsMapper;
	
	@Resource
	private BindingGoodsDetailsMapper bindingGoodsDetailMapper;

	@Resource
	private  ChargeMapper chargeMapper;
	
	@Override
	public CheckNewVersionResp checkNewVersion(AppDownload appDownloadParam) 
	{
		CheckNewVersionResp resp = new CheckNewVersionResp(); 
		
		logger.info("soft id = " + appDownloadParam.getSoftId());
		logger.info("version = " + appDownloadParam.getVersion());
		logger.info("os = " + appDownloadParam.getOs());
		logger.info("longitude = " + appDownloadParam.getLongitude());
		logger.info("latitude = " + appDownloadParam.getLatitude());
		
		if (StringUtils.isEmpty(appDownloadParam.getSoftId())
				|| StringUtils.isEmpty(appDownloadParam.getVersion())
				|| StringUtils.isEmpty(appDownloadParam.getOs()))
		{
			logger.warn("Param is null!");
			resp.setRetCode("1");
			
			return resp;
		}
		
		// 先看软件版本对不对
		AppSoftware soft = this.appSoftMapper.selectByPrimaryKey(appDownloadParam.getSoftId());
		if (soft == null)
		{
			logger.error("No such soft " + appDownloadParam.getSoftId());
			resp.setRetCode("1");
			return resp;
		}
		
		// 根据softId查询最新的版本,如果没有新版本也正常返回
		AppVersions lastedVersionObj = this.appVersionMapper.selectLastedVersion(appDownloadParam.getSoftId().intValue());
		if (lastedVersionObj == null)
		{
			logger.warn("Not such version! " + appDownloadParam.getSoftId());
			resp.setRetCode("0");
			resp.setHasNewVersion(false);
			
			return resp;
		}
		
		// 比较版本号
		String clientVersion = appDownloadParam.getVersion();
		String lastVersion = lastedVersionObj.getVersion();
		
		logger.info("clientVersion is " + clientVersion);
		logger.info("lastVersion is " + lastVersion);
		
		if (clientVersion.equalsIgnoreCase(lastVersion))
		{
			// 版本相同，没有新版本
			resp.setRetCode("0");
			resp.setHasNewVersion(false);
			
			return resp;
		}
		
		// 版本号不用，比较版本号大小
		String[] clientVersionArray = clientVersion.split("\\.");
		String[] lastVersionArray = lastVersion.split("\\.");
		
		if (clientVersionArray.length != lastVersionArray.length && lastVersionArray.length != 3)
		{
			logger.info("Version format is not right.");
			resp.setRetCode("1");
			return resp;
		}
		
		resp.setRetCode("0");
		
		if (Integer.parseInt(lastVersionArray[0]) > Integer.parseInt(clientVersionArray[0]))
		{
			resp.setHasNewVersion(true);
		}
		else
		{
			if (Integer.parseInt(lastVersionArray[1]) > Integer.parseInt(clientVersionArray[1]))
			{
				resp.setHasNewVersion(true);
			}
			else
			{
				if (Integer.parseInt(lastVersionArray[2]) > Integer.parseInt(clientVersionArray[2]))
				{
					resp.setHasNewVersion(true);
				}
				else
				{
					resp.setHasNewVersion(false);
				}
			}
		}
		
		if (resp.isHasNewVersion())
		{
			// 版本升级说明
			resp.setNewVersionHint(lastedVersionObj.getAppDesc());
			
			// 升级选项 0：可选，1：必选
			resp.setForceUpgrade(lastedVersionObj.getUpgradeOption() == 1L);
			
			// MD5
			resp.setMd5(lastedVersionObj.getMd5());
			
			// 下载地址
			String downurl = sysDictMapper.selectByPrimaryKey(FuHuiTongContext.PARAM_DOWNLOAD_URL).getDicValues();
			downurl += "?softId=" + lastedVersionObj.getSoftId();
			
			resp.setUpgradeUrl(downurl);
		}
		
		return resp;
	}

	private HeadResp checkLastedVersion(long softId, String version)
	{
		HeadResp headResp = new HeadResp();
		
		// 根据softId查询最新的版本
		AppVersions lastedVersionObj = this.appVersionMapper.selectLastedVersion((int)softId);
		if (lastedVersionObj == null)
		{
			logger.warn("Not such version! " + softId);
			
			// 没有任何新版本
			headResp.setRetCode("0");
			return headResp;
		}
		
		// 比较版本号
		String lastVersion = lastedVersionObj.getVersion();
		
		logger.info("clientVersion is " + version);
		logger.info("lastVersion is " + lastVersion);
		
		if (version.equalsIgnoreCase(lastVersion))
		{
			// 正常
			headResp.setRetCode("0");
			return headResp;
		}
		
		// 版本号不用，比较版本号大小
		String[] clientVersionArray = version.split("\\.");
		String[] lastVersionArray = lastVersion.split("\\.");
		
		if (clientVersionArray.length != lastVersionArray.length && lastVersionArray.length != 3)
		{
			logger.info("Version format is not right.");
			
			// 版本已下架
			headResp.setRetCode("2");
			return headResp;
		}
		
		if (Integer.parseInt(lastVersionArray[0]) > Integer.parseInt(clientVersionArray[0]))
		{
			// 有新版本
			if (lastedVersionObj.getUpgradeOption() == 1L)
			{
				headResp.setRetCode("3");
			}
			else
			{
				// 可选升级返回0
				headResp.setRetCode("0");
			}
		}
		else
		{
			if (Integer.parseInt(lastVersionArray[1]) > Integer.parseInt(clientVersionArray[1]))
			{
				// 有新版本
				if (lastedVersionObj.getUpgradeOption() == 1L)
				{
					headResp.setRetCode("3");
				}
				else
				{
					// 可选升级返回0
					headResp.setRetCode("0");
				}
			}
			else
			{
				if (Integer.parseInt(lastVersionArray[2]) > Integer.parseInt(clientVersionArray[2]))
				{
					// 有新版本
					if (lastedVersionObj.getUpgradeOption() == 1L)
					{
						headResp.setRetCode("3");
					}
					else
					{
						// 可选升级返回0
						headResp.setRetCode("0");
					}
				}
				else
				{
					// 正常
					headResp.setRetCode("0");
					return headResp;
				}
			}
		}
		
		if (headResp.getRetCode().equalsIgnoreCase("0"))
		{
			return headResp;
		}
		
		// 版本升级说明
		headResp.setRetMsg1(lastedVersionObj.getAppDesc());
		
		// 下载地址
		String downurl = sysDictMapper.selectByPrimaryKey(FuHuiTongContext.PARAM_DOWNLOAD_URL).getDicValues();
		downurl += "?softId=" + lastedVersionObj.getSoftId();
		headResp.setRetMsg2(downurl);
		
		return headResp;
	}

	@Override
	public ResponseEntity<byte[]> downloadApp(AppDownload appDownloadParam, String ipAddress) 
	{
		// 先查询该版本，得到最新版本信息
		AppVersions lastedVersionObj = this.appVersionMapper.selectLastedVersion(appDownloadParam.getSoftId().intValue());
		if (lastedVersionObj == null)
		{
			logger.warn("Not such version! " + appDownloadParam.getSoftId());
			return null;
		}
		
		// 查询软件版本信息
		AppSoftware appSoft = this.appSoftMapper.selectByPrimaryKey(appDownloadParam.getSoftId());
		if (appSoft == null)
		{
			logger.warn("Not such version! " + appDownloadParam.getSoftId());
			return null;
		}
		
		String filePath = lastedVersionObj.getFilePath();
		String fileName = lastedVersionObj.getFileName();
		
		logger.info(filePath);
		logger.info(fileName);
		
		File file = new File(filePath);
		if (!file.exists())
		{
			logger.warn(filePath + " File not exists.");
			return null;
		}
		
		// 保存文件下载信息
		AppDownload appDown = new AppDownload();
		appDown.setCreateDate(new Date());
		appDown.setAppVersionId(lastedVersionObj.getAppVersionId());
		appDown.setIpAddress(ipAddress);
		appDown.setSoftId(appDownloadParam.getSoftId());
		appDown.setVersion(lastedVersionObj.getVersion());
		appDown.setLatitude(appDownloadParam.getLatitude());
		appDown.setLongitude(appDownloadParam.getLongitude());
		
		// 保存的是下载APP的操作系统
		appDown.setOs(appDownloadParam.getOs());
		
		this.appDownMapper.insertSelective(appDown);
		
		// 更新下载次数
		logger.info("update download count.");
		this.appSoftMapper.updateDownloadCount(appDownloadParam.getSoftId());
		
		HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		
        logger.info("download app lasted version, size : " + file.length());
        
        try {
			ResponseEntity<byte[]> downloadData = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
			return downloadData;
        } 
        catch (IOException e)
        {
			logger.error(e.toString());
			return null;
		}
	}


	@Override
	public PosUserLoginResp posUserLogin(MerchantUsers user, String ipAddress)
	{
		PosUserLoginResp resp = new PosUserLoginResp();
		
		// 先检验头部，判断软件版本可用性
		HeadResp headResp = this.checkLastedVersion(user.getSoftId(), user.getVersion());
		
		resp.setHeadResp(headResp);
		
		if (!(headResp.getRetCode().equals("0") || headResp.getRetCode().equals("3") || headResp.getRetCode().equals("4")))
		{
			// 头部校验异常，直接返回
			return resp;
		}
		
		// 校验用户名密码
		if (StringUtils.isEmpty(user.getUserAccount()) || StringUtils.isEmpty(user.getUserPassword()))
		{
			logger.info("account or password must not be null!");
			resp.setLoginRet("1");
			return resp;
		}
		
		// 开始登录
		logger.info("Pos user " + user.getUserAccount() + " login...");
		MerchantUsers loggedUser = this.merchantUserMapper.login(user.getUserAccount());
		if (loggedUser == null)
		{
			// 用户不存在
			resp.setLoginRet("1");
			return resp;
		}
		
		// 用户被禁用，则不允许登录
		if (loggedUser.getStatus() == 1)
		{
			resp.setLoginRet("3");
			return resp;
		}
		
		if (!loggedUser.getUserPassword().equalsIgnoreCase(user.getUserPassword()))
		{
			// 密码不相同
			resp.setLoginRet("2");
			return resp;
		}
		
		// 判断该用户属于哪个分店，如果分店信息为空，则不允许登录
		if (StringUtils.isEmpty(loggedUser.getMerGroupId()))
		{
			resp.setLoginRet("3");
			return resp;
		}
		
//		if (!(loggedUser.getUserRole() == SysDefaultRole.ROLE_STORE_MASTER 
//				|| loggedUser.getUserRole() == SysDefaultRole.ROLE_STORE_OPERATOR))
//		{
//			// 用户角色不对，只有店长和收银员才可登录
//			resp.setLoginRet("3");
//			return resp;
//		}
		
		// 查询分店信息
		MerchantsGroups store = this.merchantGroupMapper.selectByPrimaryKey(loggedUser.getMerGroupId());
		
		// 查询商户名称
		Merchants merchant = this.merchantMapper.selectByPrimaryKey(loggedUser.getMerId());
		
		// 先禁用该用户的老会话
		this.merchantUserSessionMapper.updateSession(loggedUser.getMerUserId());
		
		// 登录成功，生成TOKEN
		String token = MD5.encrypt(loggedUser.getMerUserId() + loggedUser.getUserAccount() + StringUtilEx.getRandString());
		MerchantUsersSession session = new MerchantUsersSession();
		session.setMerUserToken(token);
		
		// 有效期30分钟
		Date now = new Date();
		session.setCreateDate(now);
		session.setExpiredDate(DateUtils.addMinutes(now, 30));
		session.setValid(true);
		
		// 记录位置信息
		session.setIpAddress(ipAddress);
		session.setLatitude(user.getLatitude());
		session.setLongitude(user.getLongitude());
		
		// 用户关联关系
		session.setMerGroupId(loggedUser.getMerGroupId());
		session.setGroupName(store.getGroupName());
		session.setMerId(loggedUser.getMerId());
		session.setMerUserId(loggedUser.getMerUserId());
		session.setUserName(loggedUser.getUserName());
		
		// 记录设备信息
		session.setVersion(user.getVersion());
		session.setOs(user.getOs());
		
		// 保存进数据库
		this.merchantUserSessionMapper.insertSelective(session);
		
		// 返回给终端响应报文
		resp.setToken(session.getMerUserToken());
		resp.setUserName(loggedUser.getUserName());
		resp.setUserId(loggedUser.getMerUserId());
		resp.setStoreId(loggedUser.getMerGroupId());
		
		if (store != null)
			resp.setStoreName(store.getGroupName());
		
		resp.setMerName(merchant.getMerFullName());
		resp.setMerchantId(loggedUser.getMerId());
		
		// 微信支付商户号
		resp.setWeixinMerCode(merchant.getWeixinPayCode());
		// 支付宝商户号
		resp.setAlipayMerCode(merchant.getAlipayPayCode());
		// 翼支付商户号
		resp.setBestPayCode(merchant.getBestPayCode());
		// 银行卡收单商户号
		resp.setBankPayCode(merchant.getBankPayCode());
		
		return resp;
	}

	private MerchantUsersSession tokenExpired(String token)
	{
		// 查询token
		MerchantUsersSession currentSession = this.merchantUserSessionMapper.selectByPrimaryKey(token);
		if (currentSession == null)
		{
			logger.warn("Token not exists.");
			return currentSession;
		}
		
		if (!currentSession.getValid())
		{
			logger.warn("Token expired.");
			return currentSession;
		}
		
		// 检查时间是否过期
		Date expiredDate = currentSession.getExpiredDate();
		Date now = new Date();
		if (expiredDate.getTime() < now.getTime())
		{
			// 已过期
			logger.warn("Token expired.");
			
			// 更新过期状态
			currentSession.setValid(false);
			this.merchantUserSessionMapper.updateByPrimaryKeySelective(currentSession);
			
			return currentSession;
		}
		
		currentSession.setValid(true);
		// 没有过期，离过期小于10分钟时顺延续，延期expired到30分钟后
		int mins = (int)(expiredDate.getTime() - now.getTime());
		if (mins <= 600000)
		{
			logger.info("Add 30 minuts to current token.");
			// 小于10分钟，后续30分钟
			expiredDate = DateUtils.addMinutes(now, 30);
			currentSession.setExpiredDate(expiredDate);
			
			this.merchantUserSessionMapper.updateByPrimaryKeySelective(currentSession);
		}
		
		return currentSession;
	}

	@Override
	public PosQueryGoodsResp posQueryGoodsInfo(GoodsInfo goodsInfo, String ipAddress) {
		
		PosQueryGoodsResp resp = new PosQueryGoodsResp();
		
		// 先检验头部，判断软件版本可用性
		HeadResp headResp = this.checkLastedVersion(goodsInfo.getSoftId(), goodsInfo.getVersion());
		
		resp.setHeadResp(headResp);
		
		if (!(headResp.getRetCode().equals("0") || headResp.getRetCode().equals("3") || headResp.getRetCode().equals("4")))
		{
			// 头部校验异常，直接返回
			return resp;
		}
		
		// 检查会话是否过期
		MerchantUsersSession session = tokenExpired(goodsInfo.getToken());
		if (session == null || !session.getValid())
		{
			logger.warn("Token expired!");
			
			headResp.setRetMsg1(null);
			headResp.setRetMsg2(null);
			headResp.setRetCode("1");
			
			return resp;
		}
		
		// 加载当前商户配置信息
		boolean stockLessZero = false;
		MerchantsSettings merSetting = this.merchantsSettingsMapper.selectByMerId(session.getMerId());
		if (merSetting != null)
		{
			// 读取商户库存是否可以小于0的配置
			stockLessZero = merSetting.getStockLessZero();
		}
		logger.info("读取商户库存是否可以小于0的配置, stockLessZero = " + stockLessZero);
		
		// 返回商品信息
		if (StringUtils.isEmpty(goodsInfo.getGoodsCode()))
		{
			// 商品查不到信息
			resp.setRetCode("1");
			return resp;
		}
		
		// 查询该商品在当前分店的销售价格,传入分店ID 与 商品编码
		goodsInfo.setMerGroupId(session.getMerGroupId());
		GoodsInfo goodsInfoObj = this.goodsInfoMapper.selectStoreGoodsPriceBy(goodsInfo);
		if (goodsInfoObj == null)
		{
			// 查询是否存在绑定商品
			BindingGoods bindingQuery = new BindingGoods();
			bindingQuery.setGoodsCode(goodsInfo.getGoodsCode());
			bindingQuery.setMerGroupId(session.getMerGroupId());
			
			BindingGoods bindingGoods = this.bindingGoodsMapper.selectByCode(bindingQuery);
			if (bindingGoods == null)
			{
				// 商品查不到信息
				resp.setRetCode("1");
				return resp;
			}
			
			// 此时购买的是捆绑的商品
			logger.info("此时购买的是捆绑的商品");
			if (!bindingGoods.getBindActive())
			{
				logger.info("此时购买的是捆绑的商品，已经下架");
				// 商品查不到信息
				resp.setRetCode("1");
				return resp;
			}
			
			// 正常处理购买的商品
			resp.setRetCode("0");
			resp.setGoodsName(bindingGoods.getGoodsName());
			resp.setGoodsPrice(bindingGoods.getGoodsPrice());
		}
		else
		{
			if (goodsInfoObj.getStatus() == 1)
			{
				// 下架禁用
				resp.setRetCode("1");
				return resp;
			}
			
			if (!stockLessZero)
			{
				// 查询商品库存是否足够
				GoodsStockDetail query = new GoodsStockDetail();
				query.setOwnerId(session.getMerGroupId());
				query.setGoodsId(goodsInfoObj.getGoodsId());
				query.setHasQualityPeriod(goodsInfo.getHasQualityPeriod());
				
				Integer goodsStockCount = this.goodsStockDetailMapper.selectStockCountByGoodsId(query);
				if (goodsStockCount == null || goodsStockCount <= 0)
				{
					logger.info(goodsInfoObj.getGoodsCode() + " stock is empty!");
					resp.setRetCode("2");
					
					return resp;
				}
			}
			
			resp.setRetCode("0");
			resp.setGoodsName(goodsInfoObj.getGoodsName());
			
			// 先检查是否有门店价格
			if (goodsInfoObj.getStoreGoodsPrice() != null)
			{
				// 设置其分店价格
				resp.setGoodsPrice(goodsInfoObj.getStoreGoodsPrice());
			}
			else
			{
				// 没有设置分店价格，按照总部销售价格
				resp.setGoodsPrice(goodsInfoObj.getGoodsPrice());
			}
		}
		
		return resp;
	}
	
	@Override
	public CreateOrderResp createOrder(CreateOrderReq reqParam, String ipAddress) 
	{
		CreateOrderResp resp = new CreateOrderResp();
		
		// 先检验头部，判断软件版本可用性
		HeadResp headResp = this.checkLastedVersion(reqParam.getSoftId(), reqParam.getVersion());
		
		resp.setHeadResp(headResp);
		
		if (!(headResp.getRetCode().equals("0") || headResp.getRetCode().equals("3") || headResp.getRetCode().equals("4")))
		{
			// 头部校验异常，直接返回
			return resp;
		}
		
		// 判断商品数据是否为空
		if (StringUtils.isEmpty(reqParam.getToken()) || StringUtils.isEmpty(reqParam.getGoodsCodes()))
		{
			logger.warn("Token or goods codes is null!");
			headResp.setRetCode("1");
			return resp;
		}
		
		// 检查会话是否过期
		MerchantUsersSession session = tokenExpired(reqParam.getToken());
		if (session == null || !session.getValid())
		{
			logger.warn("Token expired!");
			
			headResp.setRetMsg1(null);
			headResp.setRetMsg2(null);
			headResp.setRetCode("1");
			
			return resp;
		}
		
		// 加载当前商户配置信息
		boolean stockLessZero = false;
		MerchantsSettings merSetting = this.merchantsSettingsMapper.selectByMerId(session.getMerId());
		if (merSetting != null)
		{
			// 读取商户库存是否可以小于0的配置
			stockLessZero = merSetting.getStockLessZero();
		}
		logger.info("读取商户库存是否可以小于0的配置, stockLessZero = " + stockLessZero);
		
		Members member = null;
		
		// 解析是否有会员数据
		if (StringUtils.isEmpty(reqParam.getMemberPayToken()))
		{
			logger.info("没有会员信息");
		}
		else
		{
			logger.info("会员 tocken" + reqParam.getMemberPayToken());
			
			// 读取会员信息
			MemberTokens memberToken =  this.memberTokenMpper.selectByToken(reqParam.getMemberPayToken());
			if (memberToken != null)
			{
				member = this.memberMapper.selectByPrimaryKey(memberToken.getMemberId());
			}
		}
		
		// 开始解析商品数据
		logger.info("Goods codes : " + reqParam.getGoodsCodes());
		Hashtable<String, Integer> goodsCountsTable = new Hashtable<String, Integer>();
		
		// 数据格式： goodsCode|goodsCount, goodsCode|goodsCount
		String[] goodsCodeArray = reqParam.getGoodsCodes().split(",");
		
		// 记录商品的顺序
		ArrayList<String> reqGoodsCodes = new ArrayList<>();
		
		// 先梳理一下商品数据，合并相同商品
		for (String goodsCodeCount : goodsCodeArray)
		{
			String[] arr = goodsCodeCount.split("\\|");
			
			String goodsCode = arr[0].trim();
			Integer goodsCount = Integer.parseInt(arr[1]);
			
			logger.info("goodsCode : " + goodsCode);
			logger.info("goodsCount : " + goodsCount);
			
			Integer exitsCount = goodsCountsTable.get(goodsCode);
			if (exitsCount != null)
			{
				// 累加
				exitsCount += goodsCount;
				goodsCountsTable.put(goodsCode, exitsCount);
			}
			else
			{
				goodsCountsTable.put(goodsCode, goodsCount);
				
				// 记录不重复商品的顺序
				reqGoodsCodes.add(goodsCode);
			}
		}
		
		ArrayList<GoodsInfo> orderGoodsList = new ArrayList<GoodsInfo>();
		ArrayList<GoodsInfo> noStockGSoodsList = new ArrayList<GoodsInfo>();
		
		for (String code : reqGoodsCodes)
		{
			GoodsInfo query = new GoodsInfo();
			query.setGoodsCode(code);
			query.setMerGroupId(session.getMerGroupId());
			
			// 这里查询的商品价格是总部给门店定制的价格
			GoodsInfo goodsInfo = this.goodsInfoMapper.selectStoreGoodsPriceBy(query);
			if (goodsInfo != null)
			{
				// TODO 这里要检查库存是否足够
				// 查询商品库存是否足够
				GoodsStockDetail queryParam = new GoodsStockDetail();
				queryParam.setOwnerId(session.getMerGroupId());
				queryParam.setGoodsId(goodsInfo.getGoodsId());
				queryParam.setHasQualityPeriod(goodsInfo.getHasQualityPeriod());
				
				if (!stockLessZero)
				{
					// 严格检查库存
					Integer goodsStockCount = this.goodsStockDetailMapper.selectStockCountByGoodsId(queryParam);
					if (goodsStockCount == null || goodsStockCount <= 0)
					{
						logger.info(goodsInfo.getGoodsCode() + " stock is empty!");
						noStockGSoodsList.add(goodsInfo);
					}
					else
					{
						if (goodsStockCount.intValue() < goodsCountsTable.get(code).intValue())
						{
							// 库存不足
							logger.info(goodsInfo.getGoodsCode() + " stock is " + goodsStockCount);
							logger.info(goodsInfo.getGoodsCode() + " amount in order is " + goodsCountsTable.get(code));
							goodsInfo.setGoodsCount(goodsStockCount);
							noStockGSoodsList.add(goodsInfo);
							
							break;
						}
						else
						{
							goodsInfo.setGoodsCount(goodsCountsTable.get(code));
							orderGoodsList.add(goodsInfo);
						}
					}
				}
				else
				{
					logger.info("stockLessZero = true, 不计库存是否足够");
					goodsInfo.setGoodsCount(goodsCountsTable.get(code));
					orderGoodsList.add(goodsInfo);
				}
			}
			else
			{
				// 查询不到商品，检查是否是捆绑销售的商品
				BindingGoods bindingQuery = new BindingGoods();
				bindingQuery.setGoodsCode(code);
				bindingQuery.setMerGroupId(session.getMerGroupId());
				
				BindingGoods bindingGoods = this.bindingGoodsMapper.selectByCode(bindingQuery);
				if (bindingGoods != null)
				{
					logger.info("购买了捆绑销售的商品");
					goodsInfo = new GoodsInfo();
					goodsInfo.setGoodsId(bindingGoods.getBindGoodsId());
					goodsInfo.setGoodsName(bindingGoods.getGoodsName());
					goodsInfo.setGoodsCode(code);
					goodsInfo.setGoodsPrice(bindingGoods.getGoodsPrice());
					goodsInfo.setStoreGoodsPrice(bindingGoods.getGoodsPrice());
					goodsInfo.setGoodsCount(goodsCountsTable.get(code));
					goodsInfo.setGoodsBinding(1);
					
					orderGoodsList.add(goodsInfo);
				}
			}
		}
		
		if (noStockGSoodsList.size() > 0)
		{
			// 存在库存不足的商品
			resp.setRetCode("2");
			String addtional = "";
			for (GoodsInfo goods : noStockGSoodsList)
			{
				addtional += goods.getGoodsName() + ",";
			}
			addtional = addtional.substring(0, addtional.length() - 1);
			resp.setAddtional(addtional);
			logger.info("有库存不足的商品..." + addtional);
			return resp;
		}
		
		if (orderGoodsList == null || orderGoodsList.isEmpty())
		{
			// 查询不到商品
			logger.warn("No goods searched.");
			resp.setRetCode("1");
			return resp;
		}
		
		logger.info("Create temp order info ...");
		// 创建临时订单
		TempPosOrders tempOrder = new TempPosOrders();
		
		// 生成订单号
		tempOrder.setTmpOrderId(UUID.randomUUID().toString());
		
		// 设置关联信息
		tempOrder.setMerGroupId(session.getMerGroupId());
		tempOrder.setMerId(session.getMerId());
		tempOrder.setMerUserId(session.getMerUserId());
		tempOrder.setCreateDate(new Date());
		tempOrder.setOperatorId(session.getMerUserId());
		tempOrder.setOperatorName(session.getUserName());
		
		if (member != null)
		{
			tempOrder.setMemberCode(member.getMemberCode());
		}

		ArrayList<OrderDetails> orderDetails = new ArrayList<OrderDetails>();
		int orderTotalPrice = 0;
		
		// TODO 这里需要根据营销政策去匹配商品的价格
		logger.info("这里需要根据营销政策去匹配商品的价格, 订单商品种类个数 " + orderGoodsList.size());
		orderGoodsList = catchDiscount(session, member, tempOrder, orderGoodsList);
		logger.info("根据营销政策去匹配商品后, 订单商品种类个数 " + orderGoodsList.size());
		
		// 返回商品数据
		ArrayList<GoodsInOrderResp> goodsResp = new ArrayList<>();
		
		// 保存商品明细到订单明细表中,需要过滤掉 数量为0的清单
		for (GoodsInfo goods : orderGoodsList)
		{
			if (goods.getGoodsCount() == 0)
			{
				continue;
			}
			
			OrderDetails orderDetail = new OrderDetails();

			orderDetail.setOrderId(tempOrder.getTmpOrderId());
			orderDetail.setGoodsId(goods.getGoodsId());
			orderDetail.setMerGroupId(session.getMerGroupId());
			orderDetail.setMerId(session.getMerId());
			orderDetail.setGoodsCode(goods.getGoodsCode());
			
			if (goods.getGoodsBinding() == null)
			{
				orderDetail.setGoodsBinding(0);
			}
			else
			{
				orderDetail.setGoodsBinding(goods.getGoodsBinding());
			}
			
			String goodsCode = goods.getGoodsCode();
			Integer goodsCount = goods.getGoodsCount();
			
			logger.info("goodsCode : " + goodsCode);
			logger.info("goodsCount : " + goodsCount);
			
			orderDetail.setGoodsAmount(goodsCount);
			orderDetail.setGoodsName(goods.getGoodsName());
			
			// 设置门店价格
			orderDetail.setGoodsSalePrice(goods.getStoreGoodsPrice());
			
			// 这里备份一份数据
			orderDetail.setGoodsPrice(goods.getStoreGoodsPrice());
			
			int totalPrice = goodsCount * orderDetail.getGoodsSalePrice();
			orderDetail.setGoodsTotalPrice(totalPrice);
			
			orderTotalPrice += totalPrice;
			
			orderDetails.add(orderDetail);
			
			if (goods.getGoodsBinding() != null && goods.getGoodsBinding().intValue() == 1)
			{
				logger.info("加载当前捆绑的商品清单");
				ArrayList<BindingGoodsDetails> bindingDetails = bindingGoodsDetailMapper.selectByBindGoodsId(goods.getGoodsId());
				if (bindingDetails != null && !bindingDetails.isEmpty())
				{
					for (BindingGoodsDetails bindingDetail : bindingDetails)
					{
						OrderDetails orderDetailBinding = new OrderDetails();

						orderDetailBinding.setOrderId(tempOrder.getTmpOrderId());
						orderDetailBinding.setGoodsId(bindingDetail.getGoodsId());
						orderDetailBinding.setMerGroupId(session.getMerGroupId());
						orderDetailBinding.setMerId(session.getMerId());
						orderDetailBinding.setGoodsCode(bindingDetail.getGoodsCode());
						
						// 设定当前是被绑定的商品
						orderDetailBinding.setGoodsBinding(2);
						orderDetailBinding.setBindingId(goods.getGoodsId());
						
						orderDetailBinding.setGoodsAmount(bindingDetail.getBindGoodsCount() * goods.getGoodsCount());
						orderDetailBinding.setGoodsName(bindingDetail.getGoodsName());
						
						// 捆绑销售的产品销售价设定为0
						// 设置门店价格
						orderDetailBinding.setGoodsSalePrice(0);
						
						// 这里备份一份数据
						orderDetailBinding.setGoodsPrice(0);
						
						orderDetailBinding.setGoodsTotalPrice(0);
						
						orderDetails.add(orderDetailBinding);
					}
				}
			}
			
			// 放到返回给终端的商品数据中
			GoodsInOrderResp goodsInOrderResp = new GoodsInOrderResp();
			goodsInOrderResp.setGoodsCode(orderDetail.getGoodsCode());
			goodsInOrderResp.setGoodsPrice(orderDetail.getGoodsSalePrice());
			goodsInOrderResp.setGoodsCount(orderDetail.getGoodsAmount());
			goodsInOrderResp.setGoodsAmount(orderDetail.getGoodsTotalPrice());
			goodsInOrderResp.setGoodsName(orderDetail.getGoodsName());
			
			goodsResp.add(goodsInOrderResp);
		}
		
		// 保存到临时订单表中
		tempOrder.setTotalPrice(orderTotalPrice);
		tempOrder.setFinalPrice(orderTotalPrice);
		
		// 整单打折中有折扣，捆绑中有折扣
		if (tempOrder.getDiscountType() == 1)
		{
			if (tempOrder.getOrderDiscountType() == 1)
			{
				// 直接减
				tempOrder.setFinalPrice(orderTotalPrice - tempOrder.getOrderDiscountValue());
			}
			else
			{
				// 整单打折
				tempOrder.setFinalPrice(orderTotalPrice * tempOrder.getOrderDiscountPercent() / 100);
			}
		}
		// 捆绑销售
		else if (tempOrder.getDiscountType() == 4)
		{
			if (tempOrder.getGroupDiscountType() == 3)
			{
				tempOrder.setFinalPrice(orderTotalPrice - tempOrder.getGroupOrderDiscountValue());
			}
		}
		
		tempOrder.setDiscountPrice(tempOrder.getTotalPrice() - tempOrder.getFinalPrice());
		
		this.tempPosOrderMapper.insertSelective(tempOrder);
		
		// 批量保存到数据库
		this.orderDetailMapper.insertBatch(orderDetails);
		
		// 设置返回报文给终端
		resp.setRetCode("0");
		resp.setOrderId(tempOrder.getTmpOrderId());
		resp.setTotalPrice(tempOrder.getTotalPrice());
		resp.setFinalPrice(tempOrder.getFinalPrice());
		resp.setDiscountPrice(tempOrder.getTotalPrice() - tempOrder.getFinalPrice());
		resp.setGoods(goodsResp);
		
		return resp;
	}

	/**
	 * 匹配营销政策，刷新商品列表，只使用一条
	 * @param orderGoodsInfoList
	 * @return
	 */
	ArrayList<GoodsInfo> catchDiscount(MerchantUsersSession session, Members member, TempPosOrders tempOrder, ArrayList<GoodsInfo> orderGoodsInfoList)
	{
		logger.info("匹配当前门店的营销政策");
		
		DiscountItems query = new DiscountItems();
		query.setMerId(session.getMerId());
		query.setStoreId(session.getMerGroupId());
		
		ArrayList<DiscountItems> discountItems = this.discountItemsMapper.selectActiveDiscounts(query);
		
		// 新增的商品清单
		ArrayList<GoodsInfo> addtionOrderGoodsInfoList = new ArrayList<>();
		
		if (discountItems == null || discountItems.isEmpty())
		{
			// 没有找到可用的营销政策
			logger.info(session.getGroupName() + ",没有找到可用的营销政策");
			return orderGoodsInfoList;
		}
		
		// 订单整体价格
		int totalGoodsPrice = 0;
		
		// 先将当前订单索引起来，方便后面的快速寻找
		// 被缓冲的商品数据可能会被修改，影响原始订单
		Hashtable<String, GoodsInfo> orderGoodsMap = new Hashtable<>();
		
		// 被缓冲的商品数据不影响原始订单，只用于标记订单商品中适配促销政策的次数
		Hashtable<String, GoodsInfo> markedOrderGoodsMap = new Hashtable<>();
		
		for (GoodsInfo goodsInfo : orderGoodsInfoList)
		{
			orderGoodsMap.put(goodsInfo.getGoodsId(), goodsInfo);
			
			// 复制一份进行索引，标记订单商品中适配促销政策的次数
			GoodsInfo markedGoodsInfo = new GoodsInfo();
			markedGoodsInfo.setGoodsId(goodsInfo.getGoodsId());
			markedGoodsInfo.setGoodsCount(goodsInfo.getGoodsCount());
			markedOrderGoodsMap.put(goodsInfo.getGoodsId(), markedGoodsInfo);
			
			totalGoodsPrice += goodsInfo.getGoodsCount() * goodsInfo.getStoreGoodsPrice();
		}
		
		for (DiscountItems item : discountItems)
		{
			if (!StringUtils.isEmpty(item.getMemberLevelId()))
			{
				if (member == null)
				{
					logger.info("该促销针对会员，当前未有会员");
					// 该促销针对会员，当前未有会员
					continue;
				}
				else
				{
					// 比较会员身份是否一致
					if (!item.getMemberLevelId().equalsIgnoreCase(member.getClientLevelId()))
					{
						// 当设定了限定会员，当前会员级别不对，直接略过
						logger.info("当前销售策略限定了会员级别，与当前会员级别不符，规则略过");
						continue;
					}
				}
			}
			
			// 0：降价，1：整额折扣，2：数量折扣，3：买赠促销，4：捆绑销售，5：满减促销
			if (item.getDiscountType() == 0)
			{
				// 0 商品直接降价，看现在订单中有没有符合的商品
				String goodsId = item.getGoodsId();
				GoodsInfo goodsInOrder = orderGoodsMap.get(goodsId);
				if (goodsInOrder != null && !goodsInOrder.isCatchedDiscount())
				{
					logger.info("Goods " + goodsInOrder.getGoodsCode() + " 符合直接降价策略");
					// 计算降价,商品降价促销子类型，1：直接降价，2：绝对值降价，3：百分比折扣
					goodsInOrder.setGoodsName(goodsInOrder.getGoodsName() + "(降)");
					goodsInOrder.setCatchedDiscount(true);
					
					if (item.getGoodsDiscountType() == 1)
					{
						// 1：直接降价
						goodsInOrder.setStoreGoodsPrice(item.getGoodsDiscountPrice());
					}
					else if (item.getGoodsDiscountType() == 2)
					{
						// 2：绝对值降价
						goodsInOrder.setStoreGoodsPrice(goodsInOrder.getStoreGoodsPrice() - item.getGoodsDiscountValue());
					}
					else if (item.getGoodsDiscountType() == 3)
					{
						// 3：百分比折扣
						goodsInOrder.setStoreGoodsPrice((int)(goodsInOrder.getStoreGoodsPrice() * item.getGoodsDiscountPercent() / 100));
					}
				}
			}
			else if (item.getDiscountType() == 1)
			{
				// 1：整额折扣，订单达到指定的金额后整体降价
				if (totalGoodsPrice >= item.getOrderReachValue())
				{
					logger.info("Curr order total price " + totalGoodsPrice + " is great than order reach value " + item.getOrderReachValue());
					tempOrder.setOrderDiscount(true);
					tempOrder.setOrderDiscountType(item.getOrderDiscountType());
					
					// 整单折扣类型，1：绝对值折扣，2：百分比折扣
					if (item.getOrderDiscountType() == 1)
					{
						tempOrder.setOrderDiscountValue(item.getOrderDiscountValue());
					}
					else
					{
						tempOrder.setOrderDiscountPercent(item.getOrderDiscountPercent());
					}
					
					// 享受一次折扣
					tempOrder.setDiscountType(item.getDiscountType());
					break;
				}
			}
			else if (item.getDiscountType() == 4)
			{
				tempOrder.setGroupDiscountType(item.getGroupDiscountType());
				tempOrder.setDiscountType(item.getDiscountType());
				// 4：捆绑销售，判断所设定的商品组合，是否在当前订单里面
				String groupGoodsIds = item.getGroupGoodsIds();
				if (groupGoodsIds == null)
				{
					continue;
				}
				
				String[] goodsIds = groupGoodsIds.split(",");
				if (goodsIds == null && goodsIds.length == 0)
				{
					continue;
				}
				
				// 捆绑有数量对应关系
				boolean fullCatched = false;
				
				// 循环
				while(true)
				{
					boolean catched = true;
					for (String goodsId : goodsIds)
					{
						GoodsInfo g = markedOrderGoodsMap.get(goodsId);
						if (g == null)
						{
							catched = false;
							break;
						}
						else
						{
							if (g.getGoodsCount() == 0)
							{
								markedOrderGoodsMap.remove(g.getGoodsId());
								catched = false;
								break;
							}
						}
					}
					
					if (!catched)
					{
						break;
					}
					
					// 完整匹配一次
					fullCatched = true;
					
					logger.info("当前订单符合捆绑销售"); 
					// 1：捆绑赠商品，2：捆绑降价，3：捆绑绝对值降价，4：捆绑百分比折扣
					if (item.getGroupDiscountType() == 1)
					{
						String groupPresentGoodsId = item.getGroupPresentGoodsId();
						GoodsInfo groupPresentGoodsInfo = this.goodsInfoMapper.selectByPrimaryKey(groupPresentGoodsId);
						if (groupPresentGoodsInfo != null)
						{
							logger.info("捆绑赠送 " + groupPresentGoodsInfo.getGoodsName());
							groupPresentGoodsInfo.setGoodsName(groupPresentGoodsInfo.getGoodsName() + "(赠)");
							groupPresentGoodsInfo.setStoreGoodsPrice(0);
							groupPresentGoodsInfo.setGoodsPrice(0);
							groupPresentGoodsInfo.setGoodsCount(1);
							addtionOrderGoodsInfoList.add(groupPresentGoodsInfo);
							
							// 符合捆绑的商品数量减去1
							for (String goodsId : goodsIds)
							{
								GoodsInfo g = markedOrderGoodsMap.get(goodsId);
								int goodsCount = g.getGoodsCount();
								goodsCount--;
								if (goodsCount == 0)
								{
									markedOrderGoodsMap.remove(goodsId);
								}
								else
								{
									g.setGoodsCount(goodsCount);
									markedOrderGoodsMap.remove(goodsId);
									markedOrderGoodsMap.put(goodsId, g);
								}
							}
							
							continue;
						}
					}
					else if (item.getGroupDiscountType() == 2)
					{
						// 2：捆绑降价,选购的这几样商品总价为 降价后的价格
						ArrayList<GoodsInfo> discountList = new ArrayList<>();
						for (String goodsId : goodsIds)
						{
							GoodsInfo g = orderGoodsMap.get(goodsId);
							try {
								GoodsInfo newGoods = (GoodsInfo) BeanUtils.cloneBean(g);
								newGoods.setGoodsCount(1);
								newGoods.setStoreGoodsPrice(0);
								newGoods.setGoodsPrice(0);
								newGoods.setGoodsName(newGoods.getGoodsName() + "(促)");
								
								discountList.add(newGoods);
							} catch (IllegalAccessException | InstantiationException | InvocationTargetException
									| NoSuchMethodException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							// 重新从标记映射表中读取
							g = markedOrderGoodsMap.get(goodsId);
							// 把标原价的商品数量减去1
							g.setGoodsCount(g.getGoodsCount()-1);
							
							// 由于改变了原始订单商品价格，这新增了打折后的新的商品清单，需要从原始订单商品中把数量减去
							GoodsInfo g0 = orderGoodsMap.get(g.getGoodsId());
							g0.setGoodsCount(g0.getGoodsCount() - 1);
							orderGoodsMap.remove(g0.getGoodsId());
							orderGoodsMap.put(g0.getGoodsId(), g0);
							
							if (g.getGoodsCount() == 0)
							{
								markedOrderGoodsMap.remove(g.getGoodsId());
							}
							else
							{
								markedOrderGoodsMap.remove(g.getGoodsId());
								markedOrderGoodsMap.put(g.getGoodsId(), g);
							}
						}
						// 在第一个商品上标价
						discountList.get(0).setStoreGoodsPrice(item.getGroupOrderDiscountPrice());
						
						//加入到当前商品清单中
						addtionOrderGoodsInfoList.addAll(discountList);
					}
					else if (item.getGroupDiscountType() == 3)
					{
						// 3：捆绑绝对值降价
						tempOrder.setGroupOrderDiscountValue(tempOrder.getGroupOrderDiscountValue() + item.getGroupOrderDiscountValue());
						
						for (String goodsId : goodsIds)
						{
							GoodsInfo g = markedOrderGoodsMap.get(goodsId);
							
							// 把参与过活动的商品计数建去1
							g.setGoodsCount(g.getGoodsCount()-1);
							
							if (g.getGoodsCount() == 0)
							{
								markedOrderGoodsMap.remove(g.getGoodsId());
							}
							else
							{
								markedOrderGoodsMap.remove(g.getGoodsId());
								markedOrderGoodsMap.put(g.getGoodsId(), g);
							}
						}
					}
					else if (item.getGroupDiscountType() == 4)
					{
						// 4：捆绑百分比折扣
						ArrayList<GoodsInfo> discountList = new ArrayList<>();
						for (String goodsId : goodsIds)
						{
							GoodsInfo g = orderGoodsMap.get(goodsId);
							
							try {
								GoodsInfo newGoods = (GoodsInfo) BeanUtils.cloneBean(g);
								newGoods.setStoreGoodsPrice(g.getStoreGoodsPrice() * item.getGroupOrderDiscountPercent() / 100);
								newGoods.setGoodsPrice(newGoods.getStoreGoodsPrice());
								newGoods.setGoodsCount(1);
								newGoods.setGoodsName(newGoods.getGoodsName() + "(促)");
								
								discountList.add(newGoods);
							} catch (IllegalAccessException | InstantiationException | InvocationTargetException
									| NoSuchMethodException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							// 此时要修改原始订单的商品数量,相当于把订单中商品替换成打折的价格
							GoodsInfo g0 = orderGoodsMap.get(goodsId);
							g0.setGoodsCount(g0.getGoodsCount()-1);
							orderGoodsMap.remove(g0.getGoodsId());
							orderGoodsMap.put(g0.getGoodsId(), g0);
							
							// 从标记对照表中重新读取
							g = markedOrderGoodsMap.get(goodsId);
							// 把标原价的商品数量减去1
							g.setGoodsCount(g.getGoodsCount()-1);
							
							if (g.getGoodsCount() == 0)
							{
								markedOrderGoodsMap.remove(g.getGoodsId());
							}
							else
							{
								markedOrderGoodsMap.remove(g.getGoodsId());
								markedOrderGoodsMap.put(g.getGoodsId(), g);
							}
						}
						
						//加入到当前商品清单中
						addtionOrderGoodsInfoList.addAll(discountList);
					}
				}
			}
		}
		
		logger.info("Addtional goods " + addtionOrderGoodsInfoList.size());
		
		if (addtionOrderGoodsInfoList.size() > 0)
		{
			// 合并一下赠品或者打折的商品中有相同的
			ArrayList<GoodsInfo> discountList2 = new ArrayList<>();
			Hashtable<String, GoodsInfo> discountMap = new Hashtable<>();
			for (GoodsInfo g : addtionOrderGoodsInfoList)
			{
				String key = g.getGoodsId() + g.getStoreGoodsPrice() + g.getGoodsName();
				logger.info("key is : " + key);
				
				GoodsInfo g1 = discountMap.get(key);
				if (g1 == null)
				{
					discountMap.put(key, g);
					discountList2.add(g);
				}
				else
				{
					g1.setGoodsCount(g1.getGoodsCount() + 1);
				}
			}
			
			orderGoodsInfoList.addAll(discountList2);
		}
		
		return orderGoodsInfoList;
	}

	@Override
	public QueryVIPInfosResp posQueryVIPInfos(QueryVIPInfosReq reqParam, String ipAddress) 
	{
		QueryVIPInfosResp resp = new QueryVIPInfosResp();
		
		// 先检验头部，判断软件版本可用性
		HeadResp headResp = this.checkLastedVersion(reqParam.getSoftId(), reqParam.getVersion());
		
		resp.setHeadResp(headResp);
		
		if (!(headResp.getRetCode().equals("0") || headResp.getRetCode().equals("3") || headResp.getRetCode().equals("4")))
		{
			// 头部校验异常，直接返回
			return resp;
		}
		
		// 检查会话是否过期
		MerchantUsersSession session = tokenExpired(reqParam.getToken());
		if (session == null || !session.getValid())
		{
			logger.warn("Token expired!");
			
			headResp.setRetMsg1(null);
			headResp.setRetMsg2(null);
			headResp.setRetCode("1");
			
			return resp;
		}
		
		// 判断会员号和订单号是否完整
		if (StringUtils.isEmpty(reqParam.getMemberCode()) || StringUtils.isEmpty(reqParam.getOrderId()))
		{
			logger.warn("member code or order id is null!");
			resp.setRetCode("1");
			return resp;
		}
		
		// 查询会员信息
		logger.info("Member code is " + reqParam.getMemberCode());
		Members queryParm = new Members();
		queryParm.setMemberCode(reqParam.getMemberCode());
		queryParm.setMerId(session.getMerId());
		
		Members member = this.memberMapper.selectByCode(queryParm);
		if (member == null)
		{
			logger.info("Cannot find number of " + reqParam.getMemberCode());
			resp.setRetCode("1");
			return resp;
		}
		
		if (member.isLocked())
		{
			// 会员支付被锁定，有会员支付未完成
			logger.warn("Member pay was locked.");
			resp.setRetCode("2");
			return resp;
		}
		
		// 加载临时订单表
		logger.info("order id is " + reqParam.getOrderId());
		TempPosOrders tmpOrder = this.tempPosOrderMapper.selectByPrimaryKey(reqParam.getOrderId());
		if (tmpOrder == null)
		{
			logger.info("Cannot find order id " + reqParam.getOrderId());
			resp.setRetCode("1");
			return resp;
		}
		
		// 查询订单明细信息
		ArrayList<OrderDetails> orderDetails = this.orderDetailMapper.selectByOrderId(reqParam.getOrderId());
		if (orderDetails == null || orderDetails.isEmpty())
		{
			logger.info("Cannot find order details " + reqParam.getOrderId());
			resp.setRetCode("1");
			return resp;
		}
		
		resp.setMemberCode(member.getMemberCode());
		resp.setMemberName(member.getMemberName());
		resp.setMemberLevel(member.getLevelValue());
		resp.setMemberScore(member.getMemberScore());
		
		// 生成会员支付用的token,对会员MK和orderId/会员token计算MD5
		String memberToken = MD5.encrypt(member.getMemberMk() + reqParam.getOrderId() + session.getMerUserToken());
		resp.setMemberPayToken(memberToken);
		
		MemberTokens memToken = new MemberTokens();
		memToken.setMemberId(member.getMemberId());
		memToken.setMenmberToken(memberToken);
		
		Date now = new Date();
		memToken.setCreateDate(now);
		//10 分钟后过期
		memToken.setExpiredDate(DateUtils.addMinutes(now, 10));
		
		logger.info("Create and save member token.");
		// 保存会员token
		this.memberTokenMpper.insertSelective(memToken);
		
		Hashtable<String, OrderDetails> orderDetailsTable = new Hashtable<String, OrderDetails>();
		
		int totalPrice = 0;
		// 并且刷新会员商品价格
		for (OrderDetails detail : orderDetails)
		{
			// 放在临时对照表，便于后面识别是否在优惠券指定的商品范围内
			orderDetailsTable.put(detail.getGoodsId(), detail);
			
			GoodsMemberPrice query = new GoodsMemberPrice();
			query.setClientLevelId(member.getClientLevelId());
			query.setGoodsId(detail.getGoodsId());
			
			// 查询会员价
			GoodsMemberPrice goodsMemPrice = this.goodsMemberPriceMapper.selectByMemberLevel(query);
			if (goodsMemPrice != null)
			{
				// 更新订单商品明细终端的价格为会员零售价,门店价 乘以 会员折扣
				int memberPrice = (int)(goodsMemPrice.getPricePercent() * detail.getGoodsPrice() / 100);
				detail.setGoodsSalePrice(memberPrice);
				
				// 更新该商品单类总价
				detail.setGoodsTotalPrice(detail.getGoodsAmount() * detail.getGoodsSalePrice());
				// 更新到数据库
				this.orderDetailMapper.updateByPrimaryKeySelective(detail);
			}

			// 重新计算整单的订单价格
			totalPrice += detail.getGoodsTotalPrice();
		}
		
		// 更新包含会员价的订单总价
		tmpOrder.setTotalPrice(totalPrice);
		
		// 优惠券折让金额
		int discountPrice = 0;
		
		// 最终实收金额，初始值与订单总金额相同
		int finalPrice = totalPrice;
		
//		// 判断该会员是否有可用的优惠券，优惠券是指定商品，并且金额达到一定条件
//		ArrayList<DiscountCoupon> discoutCoupons = this.discountCouponMapper.selectMemberDiscountCoupon(member.getMemberId());
//		if (discoutCoupons != null && !discoutCoupons.isEmpty())
//		{
//			// 该会员有可用优惠券,进一步判断所购买的商品是否在范围内，金额是否达到满减条件
//			logger.info("Continue to find valid goods in discount coupons.");
//			
//			// 遍历会员所领取的有效期内未使用的优惠券
//			for (DiscountCoupon coupon : discoutCoupons)
//			{
//				if (orderDetailsTable.size() == 0)
//				{
//					break;
//				}
//				
//				long couponId = coupon.getCouponId();
//				
//				// 查询该优惠券针对的商品
//				ArrayList<DiscountCouponGoods> discountGoodsList = this.discountGoodsMapper.selectGoodsByDiscountCouponId(couponId);
//				
//				// 看当前所选购的商品是否包含在优惠券指定的商品范围内
//				if (discountGoodsList != null && !discountGoodsList.isEmpty())
//				{
//					// 遍历优惠券指定的商品
//					for (DiscountCouponGoods discountGood : discountGoodsList)
//					{
//						// 看看该优惠指定的商品是否在会员的购物车里面
//						OrderDetails goodsInOrder = orderDetailsTable.get(discountGood.getGoodsId());
//						if (goodsInOrder != null && (goodsInOrder.getGoodsTotalPrice() >= coupon.getTopAmount()))
//						{
//							// 有商品在优惠范围内，且达到了满减条件
//							logger.info(goodsInOrder.getGoodsCode() + " in the discount goods and reach the top value.");
//							
//							// 优惠券面额，单位为分
//							logger.info("The discount coupon value is " + coupon.getCouponAmount() + ", is top value is " + coupon.getTopAmount());
//							
//							// 记录会员的该coupon
//							DiscountCouponItem item = new DiscountCouponItem();
//							item.setCouponId(coupon.getCouponId());
//							item.setDesc(coupon.getCouponDesc());
//							item.setExpired(DateFormatUtils.format(coupon.getExpiredDate(), "yyyy/MM/dd"));
//							
//							// 折扣金额
//							item.setPrice(coupon.getCouponAmount());
//							discountPrice += coupon.getCouponAmount();
//							
//							// 从实收金额中减去折扣金额
//							finalPrice -= coupon.getCouponAmount();
//							
//							item.setValid(true);
//							
//							resp.addDiscountCouponItem(item);
//							
//							// 一类商品只能参与一种优惠券
//							orderDetailsTable.remove(discountGood.getGoodsId());
//						}
//					}
//				}
//			}
//		}
		
//		// 判断是否有卡券可用
//		ArrayList<CardCoupon> memberCoupons = this.cardCouponMapper.selectMemberCardCoupon(member.getMemberId());
//		if (memberCoupons != null && !memberCoupons.isEmpty())
//		{
//			logger.info(member.getMemberCode() + " has " + memberCoupons.size() + " card coupons.");
//			
//			int i = 1;
//			// 遍历会员所有的可用未过期的卡券
//			for (CardCoupon cardCoupon : memberCoupons)
//			{
//				CardCouponItem item = new CardCouponItem();
//				
//				logger.info("member card (" + i + ") coupon : " + cardCoupon.getCardCouponId());
//				// 记录会员卡券ID
//				item.setCardCouponId(cardCoupon.getCardCouponId());
//				
//				// 面额
//				item.setCardAmount(cardCoupon.getCouponAmount());
//				item.setCardBlance(cardCoupon.getCouponBalance());
//				item.setExpired(DateFormatUtils.format(cardCoupon.getExpiredDate(), "yyyy/MM/dd"));
//
//				logger.info("CouponAmount " + cardCoupon.getCouponAmount());
//				logger.info("Balance " + cardCoupon.getCouponBalance());
//				logger.info("Expired " + item.getExpired());
//				
//				// 实收金额大于当前卡券可用余额，该卡券用完
//				if (finalPrice >= cardCoupon.getCouponBalance())
//				{
//					// 可用于支付的金额,并从实收金额中减去
//					item.setPrice(cardCoupon.getCouponBalance());
//					finalPrice -= cardCoupon.getCouponBalance();
//				}
//				else
//				{
//					// 订单完全支付完，卡券余额用不完
//					item.setPrice(finalPrice);
//					finalPrice = 0;
//				}
//				
//				i++;
//				resp.addCardCoupon(item);
//				
//				logger.info("Card coupon can pay " + item.getPrice());
//				logger.info("finalPrice : " + finalPrice);
//				
//				// 订单实收金额为0，不用再判断其他卡券了
//				if (finalPrice == 0)
//				{
//					break;
//				}
//			}
//		}
		
		resp.setBalance(member.getMemberBalance());
		// 判断会员余额是否可以用来支付,实收金额大于0时才进入
		if (finalPrice > 0 && member.getMemberBalance() > 0)
		{
			if (finalPrice >= member.getMemberBalance())
			{
				// 会员余额用光
				finalPrice -= member.getMemberBalance();
				resp.setBalancePay(member.getMemberBalance());
			}
			else
			{
				// 用户余额用不完，把实收金额用完
				resp.setBalancePay(finalPrice);
				finalPrice = 0;
			}
		}
		
		// 刷新商品信息,返回给收银台终端
		for (OrderDetails details : orderDetails)
		{
			GoodsInOrderResp goodsInOrder = new GoodsInOrderResp();
			
			goodsInOrder.setGoodsCode(details.getGoodsCode());
			
			// 设置会员价格
			goodsInOrder.setGoodsPrice(details.getGoodsSalePrice());
			
			// 下面两个没错，就是这样子
			goodsInOrder.setGoodsCount(details.getGoodsAmount());
			goodsInOrder.setGoodsAmount(details.getGoodsTotalPrice());
			
			resp.addGoods(goodsInOrder);
		}
		
		// 订单总价
		resp.setTotalPrice(totalPrice);
		
		// 实收金额
		resp.setFinalPrice(finalPrice);
		
		logger.info("totalPrice = " + totalPrice);
		logger.info("finalPrice = " + finalPrice);
		
		// 锁定会员的token，防止其他地方使用该会员身份
		member.setLocked(true);
		member.setLockedToken(session.getMerUserToken());
		this.memberMapper.updateByPrimaryKeySelective(member);
		
		// 最后一步，需要把创建订单的这些信息保存在数据库中 TEMP_POS_ORDERS，在用户确认支付完成后，把涉及到的优惠券、卡券、余额等信息生效
		tmpOrder.setMemberCode(member.getMemberCode());
		tmpOrder.setMemberName(member.getMemberName());
		tmpOrder.setMemberLevel(member.getLevelValue());
		tmpOrder.setMemberScore(member.getMemberScore());
		
		tmpOrder.setBalancePay(resp.getBalancePay());
		tmpOrder.setBalance(resp.getBalance());
		tmpOrder.setFinalPrice(resp.getFinalPrice());
		
		// 记录优惠券折让金额
		tmpOrder.setDiscountPrice(discountPrice);
		
		// 优惠券JSON信息
		String discountCouponStr = JSON.toJSONString(resp.getDiscountCoupons());
		logger.info(discountCouponStr);
		tmpOrder.setDiscountCoupon(discountCouponStr);
		
		// 卡券JSON信息
		String cardCouponStr = JSON.toJSONString(resp.getCardCoupons());
		logger.info(cardCouponStr);
		tmpOrder.setCardCoupon(cardCouponStr);
		
		// 更新到数据库
		this.tempPosOrderMapper.updateByPrimaryKeySelective(tmpOrder);
		
		return resp;
	}



	@Override
	public POSPaySuccessResp posPaySuccess(POSPaySuccessReq reqParam, String ipAddress) 
	{
		POSPaySuccessResp resp = new POSPaySuccessResp();
		
		// 先检验头部，判断软件版本可用性
		HeadResp headResp = this.checkLastedVersion(reqParam.getSoftId(), reqParam.getVersion());
		
		resp.setHeadResp(headResp);
		
		if (!(headResp.getRetCode().equals("0") || headResp.getRetCode().equals("3") || headResp.getRetCode().equals("4")))
		{
			// 头部校验异常，直接返回
			return resp;
		}
		
		// 检查会话是否过期
		MerchantUsersSession session = tokenExpired(reqParam.getToken());
		if (session == null || !session.getValid())
		{
			logger.warn("Token expired!");
			
			headResp.setRetMsg1(null);
			headResp.setRetMsg2(null);
			headResp.setRetCode("1");
			
			return resp;
		}
		
		// 判断会员号和订单号是否完整
		if (StringUtils.isEmpty(reqParam.getOrderId()))
		{
			logger.warn("order id is null!");
			resp.setRetCode("1");
			return resp;
		}
		
		// 加载临时订单表
		logger.info("order id is " + reqParam.getOrderId());
		TempPosOrders tmpOrder = this.tempPosOrderMapper.selectByPrimaryKey(reqParam.getOrderId());
		if (tmpOrder == null)
		{
			logger.info("Cannot find order id " + reqParam.getOrderId());
			resp.setRetCode("1");
			return resp;
		}
		
		if (reqParam.getPayStatus() == 1)
		{
			logger.info("Order " + reqParam.getOrderId() + " pay failed.");
			
			if (!StringUtils.isEmpty(tmpOrder.getMemberCode()))
			{
				// 如果支付失败，解锁会员TOKEN
				Members queryParam = new Members();
				queryParam.setMemberCode(tmpOrder.getMemberCode());
				queryParam.setMerId(session.getMerId());
				
				Members member = this.memberMapper.selectByCode(queryParam);
				member.setLocked(false);
				member.setLockedToken(null);
				
				this.memberMapper.updateByPrimaryKeySelective(member);
			}
			
			resp.setRetCode("1");
			return resp;
		}
		
		int payAmount = 0;
		// 校验订单总金额是否相同
		if (reqParam.getPayMethod() == 0)
		{
			// 现金支付
			payAmount = reqParam.getCashAmount() - reqParam.getCashChange();
		}
		else
		{
			payAmount = reqParam.getPayAmount();
		}
		
		logger.info("payAmount is " + payAmount);
		
		if (payAmount != tmpOrder.getFinalPrice())
		{
			logger.info("Pay amount is not same as final price!");
			resp.setRetCode("3");
			return resp;
		}
		
		// 支付成功
		Orders order = new Orders();
		
		// 更新订单环境信息
		order.setVersion(session.getVersion());
		order.setOs(session.getOs());
		order.setDeviceType(session.getDeviceType());
		order.setLongitude(session.getLongitude());
		order.setLatitude(session.getLatitude());
		order.setIpAddress(ipAddress);
		
		order.setOrderId(tmpOrder.getTmpOrderId());
		order.setOrderType(tmpOrder.getOrderType());
		order.setMerId(tmpOrder.getMerId());
		order.setMerUserId(tmpOrder.getMerUserId());
		order.setMerGroupId(tmpOrder.getMerGroupId());
		order.setCreateDate(tmpOrder.getCreateDate());
		order.setTotalPrice(tmpOrder.getTotalPrice());
		order.setFinalPrice(tmpOrder.getFinalPrice());
		order.setDiscountAmount(tmpOrder.getDiscountPrice());
		order.setOrderStatus(0);
		
		order.setOperatorId(tmpOrder.getOperatorId());
		order.setOperatorName(tmpOrder.getOperatorName());
		
		Date payDate = null;
		if (reqParam.getPayMethod() != 0)
		{
			// 非现金支付
			try {
				payDate = DateUtils.parseDate(reqParam.getPayDate(), new String[]{"yyyy-MM-dd HH:mm:ss"});
			} catch (ParseException e) {
				payDate = new Date();
			}
		}
		else{
			payDate = new Date();
		}
		
		order.setPayDate(payDate);
		order.setPayMethod(reqParam.getPayMethod());
		if (order.getPayMethod() != 0)
		{
			// 电子化支付方式，还需要记录电子化支付订单编号，便于后续对账
			if (!StringUtils.isEmpty(reqParam.getPayBillNumber()))
			{
				order.setPayBillNumber(reqParam.getPayBillNumber());
			}
			
			// 三方支付金额
			order.setPayAmount(reqParam.getPayAmount());
			
			// 三方支付机构序列号
			if (!StringUtils.isEmpty(reqParam.getSecondPayNumber()))
			{
				order.setSecondPayNumber(reqParam.getSecondPayNumber());
			}
			//获得机器的sn号
			order.setTermSn(reqParam.getTermSn());
			
			// 银行卡支付
			if (order.getPayMethod() == 4)
			{
				order.setMerCode(reqParam.getMerCode());
				order.setMerName(reqParam.getMerName());
				order.setTermCode(reqParam.getTermCode());
				order.setBankName(reqParam.getBankName());
				order.setBankCard(reqParam.getBankCard());
				BankCardBin cardBin = this.bankCardBinMapper.selectByCardNo(reqParam.getBankCard());
				if (cardBin != null)
				{
					if (cardBin.getCardType() == 0)
					{
						order.setBankCardType("借记卡");
					}
					else if (cardBin.getCardType() == 1)
					{
						order.setBankCardType("贷记卡");
					}
					else
					{
						order.setBankCardType("其他");
					}
				}
				order.setBankCardExpired(reqParam.getBankCardExpired());
				order.setAuthorCode(reqParam.getAuthorCode());
				order.setTradeBatchCode(reqParam.getTradeBatchCode());
			}
		}
		
		// 现金支付金额
		order.setCashAmount(reqParam.getCashAmount());
		// 现金找零
		order.setCashChange(reqParam.getCashChange());

		Charge charge = this.chargeMapper.findByMerId(tmpOrder.getMerId());

		if (order.getPayMethod()==1){//1 微信
			order.setChargeRate(""+charge.getWechat());
			Double payAmounts = reqParam.getPayAmount()+0.0;
			order.setChargeAmount((payAmounts*charge.getWechat())+"");
		}else if (order.getPayMethod()==2){//2 支付宝
			order.setChargeRate(""+charge.getAlipay());
			Double payAmounts = reqParam.getPayAmount()+0.0;
			order.setChargeAmount((payAmounts*charge.getAlipay())+"");
		}else if (order.getPayMethod()==3){//2 翼支付
			order.setChargeRate(""+charge.getBestpay());
			Double payAmounts = reqParam.getPayAmount()+0.0;
			order.setChargeAmount((payAmounts*charge.getBestpay())+"");
		}else if (order.getPayMethod()==4){//2 银行卡

				BankCardBin cardBin = this.bankCardBinMapper.selectByCardNo(reqParam.getBankCard());
				if (cardBin != null)
				{
					if (cardBin.getCardType() == 0)
					{
						order.setBankCardType("借记卡");
						order.setChargeRate(""+charge.getDebit());
						Double payAmounts = reqParam.getPayAmount()+0.0;
						Double payDebit = payAmounts*charge.getDebit();
						if(payDebit>charge.getDebitMax()){
							order.setChargeAmount(charge.getDebitMax()+"");
						}else {
							order.setChargeAmount(payDebit + "");
						}

					}
					else if (cardBin.getCardType() == 1)
					{
						order.setBankCardType("贷记卡");
						order.setChargeRate(""+charge.getCredit());
						Double payAmounts = reqParam.getPayAmount()+0.0;
						Double payCredit = payAmounts*charge.getCredit();
						if(payCredit>charge.getCreditMax()){
							order.setChargeAmount(charge.getCreditMax()+"");
						}else {
							order.setChargeAmount(payCredit + "");
						}
					}
					else
					{
						order.setBankCardType("其他");
					}
				}


		}else {
			order.setChargeRate(""+0);
			order.setChargeAmount(""+0);
		}


		
		// 生成可识别的，用于小票打印的订单号码
		MerchantsGroups merchantGroup = this.merchantGroupMapper.selectByPrimaryKey(tmpOrder.getMerGroupId());
		String orderCode = merchantGroup.getGroupCode() + "-" + OrderID.getInstance().getOrderCounter();
		logger.info(orderCode);
		order.setOrderCode(orderCode);
		
		// 返回响应信息给终端
		resp.setRetCode("0");
		resp.setOrderId(tmpOrder.getTmpOrderId());
		
		// 看订单中是否涉及到会员
		if (!StringUtils.isEmpty(tmpOrder.getMemberCode()))
		{
			// 有会员信息，查询会员信息，需要把临时订单中与会员有关的各项临时数据给生效，并且把会员锁解开
			logger.info("There is one Member info, code is " + tmpOrder.getMemberCode());
			
			Members queryParam = new Members();
			queryParam.setMemberCode(tmpOrder.getMemberCode());
			queryParam.setMerId(session.getMerId());
			
			Members member = this.memberMapper.selectByCode(queryParam);
				
			// 先检查会员token
			String memberPayToken = reqParam.getMemberPayToken();
			if (StringUtils.isEmpty(memberPayToken))
			{
				logger.error(member.getMemberCode() + " token for pay is null!");
				resp.setRetCode("2");
				return resp;
			}
			
			// 加载token
			MemberTokens memberToken = this.memberTokenMpper.selectByToken(memberPayToken);
			if (memberToken == null)
			{
				logger.error(member.getMemberCode() + " token for pay is not right!");
				resp.setRetCode("2");
				return resp;
			}
			
//			Date tokenCreateDate = memberToken.getCreateDate();
//			// 校验TOKEN，防止伪造攻击
//			// 重新生成会员支付用的token,对会员MK和orderId/会员token计算MD5
//			String yinzi = member.getMemberMk() + session.getMerUserToken() + Long.toString(tokenCreateDate.getTime());
//			logger.info(yinzi);
//			
//			String memberTokenV = MD5.encrypt(yinzi);
//			if (!memberPayToken.equalsIgnoreCase(memberTokenV))
//			{
//				logger.error(member.getMemberCode() + " token for pay is not right!");
//				resp.setRetCode("2");
//				return resp;
//			}
			
			// 会员信息
			order.setMemberId(member.getMemberId());
			order.setMemberCode(member.getMemberCode());
			
			// 会员账户支付金额
			order.setMemberBalancePay(tmpOrder.getBalancePay());
			
			// 会员用卡券支付的金额
			order.setCardCouponPay(tmpOrder.getCardPayPrice());
			
			// 检查是否有会员余额消费，有的话，使之生效
//			if (order.getMemberBalancePay() > 0 )
//			{
//				logger.info("Member balance pay " + order.getMemberBalancePay());
//				
//				// 从会员余额中减去支付的金额
//				int memberBalance = member.getMemberBalance();
//				memberBalance -= order.getMemberBalancePay();
//				member.setMemberBalance(memberBalance);
//				
//				// 解除会员TOKEN
//				member.setLocked(false);
//				member.setLockedToken(null);
//				
//				// 更新会员信息
//				this.memberMapper.updateByPrimaryKeySelective(member);
//			}
			
			// 设置回复给终端的信息
			resp.setMemberCode(member.getMemberCode());
			resp.setMemberName(member.getMemberName());
			resp.setMemberLevel(member.getLevelValue());
			resp.setMemberScore(member.getMemberScore());
			resp.setMenberBalance(member.getMemberBalance());
			
//			// 检查会员是否使用了卡券，需要将卡券余额生效，如果余额为0，则还需要将其标记未已使用
//			if (!StringUtils.isEmpty(tmpOrder.getCardCoupon()))
//			{
//				logger.info(tmpOrder.getCardCoupon());
//				
//				ArrayList<JSONObject> cardCouponItems = null;
//				cardCouponItems = JSON.parseObject(tmpOrder.getCardCoupon(), ArrayList.class);
//				
//				if (cardCouponItems != null && !cardCouponItems.isEmpty())
//				{
//					for (JSONObject obj : cardCouponItems)
//					{
//						CardCouponItem item = obj.toJavaObject(CardCouponItem.class);
//						
//						Long cardId = item.getCardCouponId();
//						
//						CardCoupon cardCoupon = this.cardCouponMapper.selectByPrimaryKey(cardId);
//						
//						// 计算余额
//						int balance = cardCoupon.getCouponBalance();
//						
//						// 减去卡券支付出去的金额
//						balance -= item.getPrice();
//
//						cardCoupon.setCouponBalance(balance);
//						
//						if (balance == 0)
//						{
//							// 表示卡券已使用完毕
//							cardCoupon.setCardValid(false);
//							cardCoupon.setFinalStatus(1);
//						}
//						
//						// 更新卡券信息到DB
//						this.cardCouponMapper.updateByPrimaryKeySelective(cardCoupon);
//					}
//				}
//			}
		}
		
		order.setOrderBillPrint(false);
		
		// 将临时订单删除
		this.tempPosOrderMapper.deleteByPrimaryKey(tmpOrder.getTmpOrderId());

		// 带有商品的订单
		if (tmpOrder.getOrderType() == 0)
		{
			// 更新库存，更新商品明细
			ArrayList<OrderDetails> orderDetails = this.orderDetailMapper.selectByOrderId(order.getOrderId());
			if (orderDetails != null && !orderDetails.isEmpty())
			{
				for (OrderDetails detail : orderDetails)
				{
					// 如果是捆绑销售的虚拟商品，直接跳过其库存计算
					if (detail.getGoodsBinding() != null && detail.getGoodsBinding() == 1)
					{
						logger.info(detail.getGoodsName() + ",捆绑销售的虚拟商品，直接跳过其库存计算");
						continue;
					}
					
					// 查询商品所在进货的批次
					GoodsStockDetail queryParam = new GoodsStockDetail();
					queryParam.setGoodsId(detail.getGoodsId());
					queryParam.setOwnerId(detail.getMerGroupId());
					
					int amount = detail.getGoodsAmount();
					int amountSize = 0;
					
					// 得到可用的商品库存批次
					ArrayList<GoodsStockDetail> goodsStockDetails = this.goodsStockDetailMapper.selectValidByGoodsId(queryParam);
					if (goodsStockDetails != null)
					{
						for (GoodsStockDetail stockDetail : goodsStockDetails)
						{
							// 库存够用
							if (amount <= stockDetail.getStockCount())
							{
								amountSize = amount;
							}
							else
							{
								// 库存批次不够用，把当前商品数量用光，并且寻下一个批次
								if (stockDetail.getStockCount() <= 0)
								{
									continue;
								}
								
								amountSize = stockDetail.getStockCount();
							}
							amount -= amountSize;
							
							// 更新库存,减去销售的商品库存
							queryParam.setGoodsStockDetailId(stockDetail.getGoodsStockDetailId());
							queryParam.setStockCount(amountSize);
							this.goodsStockDetailMapper.deCreaseStock(queryParam);
							
							if (amount == 0)
							{
								// 当前销量消化完
								break;
							}
						}
						
						if (amount > 0)
						{
							logger.info("出现没有消化干净当前销量情况，从最后一笔入库明细中继续给予扣减到负数, amount = " + amount);
							// 仍然可能出现没有消化干净当前销量情况，从最后一笔入库明细中继续给予扣减到负数
							// 更新库存,减去销售的商品库存
							queryParam.setGoodsStockDetailId(goodsStockDetails.get(goodsStockDetails.size() - 1).getGoodsStockDetailId());
							queryParam.setStockCount(amount);
							this.goodsStockDetailMapper.deCreaseStock(queryParam);
						}
						
						// 更新商品明细的库存批次，可追溯,通过该数据来计算每个订单的销售毛利润
						detail.setOrderStockDetailId(goodsStockDetails.get(0).getGoodsStockDetailId());
						
						// 记录交易时进货的商品成本
						detail.setGoodsCost(goodsStockDetails.get(0).getGoodsCost());
						
						this.orderDetailMapper.updateStockDetailId(detail);
					}
					
					// 刷新总库存
					GoodsStock goodsStockParam = new GoodsStock();
					goodsStockParam.setGoodsId(detail.getGoodsId());
					goodsStockParam.setOwnerId(detail.getMerGroupId());
					this.goodsStockMapper.statUpdateStoreStock(goodsStockParam);
				}
			}
			
			// 生成订单小票
			OrderBillUtils orderBillUtils = new OrderBillUtils(0);
			order.setGroupName(session.getGroupName());
			String printInfo = orderBillUtils.getOrderBillText(order, orderDetails);
			
			StringBuffer printInfos = new StringBuffer();
			printInfos.append(printInfo);
			printInfos.append("\n\n持卡人签名：\n\n\n\n");
			
			// 拷贝一份，作为商户联
			printInfos.append("========商户存根=========\n");
			printInfos.append(printInfo);
			printInfos.append("\n\n");
			
			order.setOrderBill(printInfos.toString());
			
			// 生成新订单
			System.out.println(order.toString());
			logger.info(order.toString());
			this.ordersMapper.insertSelective(order);
			
			// 计算当前订单的毛利润
			this.ordersMapper.updateOrderProfit(order.getOrderId());
			
			resp.setPrintInfo(printInfos.toString());
		}
		else
		{
			// 生成订单小票
			OrderBillUtils orderBillUtils = new OrderBillUtils(0);
			
			order.setGroupName(session.getGroupName());
			String printInfo = orderBillUtils.getOrderBillText(order);
			
			StringBuffer printInfos = new StringBuffer();
			printInfos.append(printInfo);
			
			
			// 拷贝一份，作为商户联
			printInfos.append("========商户存根=========\n");
			printInfos.append(printInfo);
//			printInfos.append("\n\n");
			printInfos.append("\n\n持卡人签名：\n\n\n\n\n\n\n\n\n\n");
			
			order.setOrderBill(printInfos.toString());
			
			// 生成新订单
			System.out.println(order.getTermSn());
			logger.info(order.toString());
			this.ordersMapper.insertSelective(order);
			
			resp.setPrintInfo(printInfos.toString());
		}
		
		return resp;
	}

	@Override
	public QueryOrdersResp posQueryOrders(QueryOrdersReq reqParam, String ipAddress) 
	{
		QueryOrdersResp resp = new QueryOrdersResp();
		
		// 先检验头部，判断软件版本可用性
		HeadResp headResp = this.checkLastedVersion(reqParam.getSoftId(), reqParam.getVersion());
		
		resp.setHeadResp(headResp);
		
		if (!(headResp.getRetCode().equals("0") || headResp.getRetCode().equals("3") || headResp.getRetCode().equals("4")))
		{
			// 头部校验异常，直接返回
			return resp;
		}
		
		// 检查会话是否过期
		MerchantUsersSession session = tokenExpired(reqParam.getToken());
		if (session == null || !session.getValid())
		{
			logger.warn("Token expired!");
			
			headResp.setRetMsg1(null);
			headResp.setRetMsg2(null);
			headResp.setRetCode("1");
			
			return resp;
		}
		
		// 判断查询日期是否完整
		String orderDateText = reqParam.getOrderDate();
		if (StringUtils.isEmpty(orderDateText))
		{
			logger.warn("order date is null!");
			resp.setRetCode("1");
			return resp;
		}
		
		// 检查日期格式
		try {
			Date orderDate = DateUtils.parseDate(orderDateText, new String[]{"yyyy-MM-dd"});
			orderDateText = DateFormatUtils.format(orderDate, "yyyy-MM-dd");
		} catch (ParseException e) 
		{
			logger.error(e.toString());
			resp.setRetCode("1");
			return resp;
		}
		
		resp.setRetCode("0");
		resp.setOrderDate(orderDateText);
		
		Orders param = new Orders();
		param.setCreateDateText(orderDateText);
		param.setMerGroupId(session.getMerGroupId());
		param.setMerId(session.getMerId());
		
		ArrayList<Orders> orders = this.ordersMapper.selectByDate(param);
		if (orders == null || orders.isEmpty())
		{
			logger.info("No orders.");
			resp.setOrderCount(0);
			resp.setOrderTotalAmount(0);
			resp.setOrderActToalAmount(0);
			resp.setDiscountAmount(0);
			
			return resp;
		}
		
		logger.info("There are " + orders.size() + " orders.");
		resp.setOrderCount(orders.size());
		
		long orderTotalAmount = 0;
		long orderActTotalAmount = 0;
		long discountAmount = 0;
		
		for (Orders order : orders)
		{
			orderTotalAmount += order.getTotalPrice();
			orderActTotalAmount += order.getFinalPrice();
			
			if (order.getDiscountAmount() != null)
			{
				discountAmount += order.getDiscountAmount();
			}
			
			OrdersInfoItem item = new OrdersInfoItem();
			item.setOrderCode(order.getOrderCode());
			item.setOrderDatetime(DateFormatUtils.format(order.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
			item.setTotalAmount(order.getTotalPrice());
			item.setActTotalAmount(order.getFinalPrice());
			
			resp.add(item);
		}
		
		resp.setOrderTotalAmount(orderTotalAmount);
		resp.setOrderActToalAmount(orderActTotalAmount);
		resp.setDiscountAmount(discountAmount);
		
		return resp;
	}

	@Override
	public QueryOrderDetailResp posGetOrderDetail(QueryOrderDetailReq reqParam, String ipAddress) 
	{
		QueryOrderDetailResp resp = new QueryOrderDetailResp();
		
		// 先检验头部，判断软件版本可用性
		HeadResp headResp = this.checkLastedVersion(reqParam.getSoftId(), reqParam.getVersion());
		
		resp.setHeadResp(headResp);
		
		if (!(headResp.getRetCode().equals("0") || headResp.getRetCode().equals("3") || headResp.getRetCode().equals("4")))
		{
			// 头部校验异常，直接返回
			return resp;
		}
		
		// 检查会话是否过期
		MerchantUsersSession session = tokenExpired(reqParam.getToken());
		if (session == null || !session.getValid())
		{
			logger.warn("Token expired!");
			
			headResp.setRetMsg1(null);
			headResp.setRetMsg2(null);
			headResp.setRetCode("1");
			
			return resp;
		}
		
		// 判断查询日期是否完整
		String orderCode = reqParam.getOrderCode();
		if (StringUtils.isEmpty(orderCode))
		{
			logger.warn("order code is null!");
			resp.setRetCode("1");
			return resp;
		}
		
		// 使用 MerId和orderCode查询
		Orders query = new Orders();
		query.setOrderCode(orderCode);
		query.setMerId(session.getMerId());
		
		logger.info("order code is:[" + orderCode+"]");
		logger.info("merId is:[" + session.getMerId()+"]");
		
		Orders order = this.ordersMapper.selectByOrderCode(query);
		
		logger.info("order code is:[" + orderCode+"]");
		logger.info("merId is:[" + session.getMerId()+"]");
		
		if (order == null)
		{
			logger.warn("Can not find the order.");
			resp.setRetCode("1");
			return resp;
		}
		
		String orderId = order.getOrderId();
		
		ArrayList<OrderDetails> orderDetails = this.orderDetailMapper.selectByOrderId(orderId);
		
		resp.setRetCode("0");
		resp.setOrderCode(orderCode);
		resp.setOrderDatetime(DateFormatUtils.format(order.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
		
		resp.setTotalAmount(order.getTotalPrice());
		resp.setActTotalAmount(order.getFinalPrice());
		resp.setDiscountAmount(order.getDiscountAmount());
		
		if (order.getMemberCode() != null)
		{
			resp.setMemberCode(order.getMemberCode());
		}
		
		int goodsCount = 0;
		
		if (orderDetails != null && !orderDetails.isEmpty())
		{
			for (OrderDetails detail : orderDetails)
			{
				// 输出商品详细信息
				GoodsInfoItem goods = new GoodsInfoItem();
				goods.setGoodsCode(detail.getGoodsCode());
				goods.setGoodsName(detail.getGoodsName());
				goods.setCount(detail.getGoodsAmount());
				goods.setPrice(detail.getGoodsSalePrice());
				goods.setAmount(detail.getGoodsTotalPrice());
				
				goodsCount += detail.getGoodsAmount();
				
				resp.add(goods);
			}
		}
		
		resp.setGoodsCount(goodsCount);
		
		return resp;
	}

	@Override
	public OrderPrintResp posGetOrderPrintInfo(QueryOrderDetailReq reqParam, String ipAddress) 
	{
		OrderPrintResp resp = new OrderPrintResp();
		
		// 先检验头部，判断软件版本可用性
		HeadResp headResp = this.checkLastedVersion(reqParam.getSoftId(), reqParam.getVersion());
		
		resp.setHeadResp(headResp);
		
		if (!(headResp.getRetCode().equals("0") || headResp.getRetCode().equals("3") || headResp.getRetCode().equals("4")))
		{
			// 头部校验异常，直接返回
			return resp;
		}
		
		// 检查会话是否过期
		MerchantUsersSession session = tokenExpired(reqParam.getToken());
		if (session == null || !session.getValid())
		{
			logger.warn("Token expired!");
			
			headResp.setRetMsg1(null);
			headResp.setRetMsg2(null);
			headResp.setRetCode("1");
			
			return resp;
		}
		
		// 判断查询日期是否完整
		String orderCode = reqParam.getOrderCode();
		if (StringUtils.isEmpty(orderCode))
		{
			logger.warn("order code is null!");
			resp.setRetCode("1");
			return resp;
		}
		
		// 使用 MerId和orderCode查询
		Orders query = new Orders();
		query.setOrderCode(orderCode);
		query.setMerId(session.getMerId());
		
		logger.info("order code is:[" + orderCode+"]");
		logger.info("merId is:[" + session.getMerId()+"]");
		
		Orders order = this.ordersMapper.selectPrintByOrderCode(query);
		
		logger.info("order code is:[" + orderCode+"]");
		logger.info("merId is:[" + session.getMerId()+"]");
		
		if (order == null)
		{
			logger.warn("Can not find the order.");
			resp.setRetCode("1");
			return resp;
		}
		
		// 查询小票的打印信息
		resp.setRetCode("0");
		resp.setOrderCode(orderCode);
		resp.setPrintInfo(order.getOrderBill());
		
		// 更新补打印标示
		this.ordersMapper.updateBillPrintFlag(order.getOrderId());
		
		return resp;
	}

	@Override
	public QueryMemberResp posQueryMember(QueryMemberReq reqParam, String ipAddress) {
		
		QueryMemberResp resp = new QueryMemberResp();
		
		// 先检验头部，判断软件版本可用性
		HeadResp headResp = this.checkLastedVersion(reqParam.getSoftId(), reqParam.getVersion());
		
		resp.setHeadResp(headResp);
		
		if (!(headResp.getRetCode().equals("0") || headResp.getRetCode().equals("3") || headResp.getRetCode().equals("4")))
		{
			// 头部校验异常，直接返回
			return resp;
		}
		
		// 检查会话是否过期
		MerchantUsersSession session = tokenExpired(reqParam.getToken());
		if (session == null || !session.getValid())
		{
			logger.warn("Token expired!");
			
			headResp.setRetMsg1(null);
			headResp.setRetMsg2(null);
			headResp.setRetCode("1");
			
			return resp;
		}
		
		// 获取会员号码
		if (StringUtils.isEmpty(reqParam.getMemberCode()))
		{
			logger.info("会员号码为空");
			resp.setRetCode("1");
			return resp;
		}
		
		logger.info(reqParam.getMemberCode());
		
		// 先判断会员号码是否合法
		if (!(reqParam.getMemberCode().length() == 11 && reqParam.getMemberCode().startsWith("1")))
		{
			logger.info("该会员号长度不合乎规范，长度必须为11，且以1开头");
			resp.setRetCode("1");
			return resp;
		}
		
		Members queryParam = new Members();
		queryParam.setMemberCode(reqParam.getMemberCode());
		queryParam.setMerId(session.getMerId());
		
		Members member = this.memberMapper.selectByCode(queryParam);
		if (member == null)
		{
			logger.info(reqParam.getMemberCode() + ",会员为空，初始建成会员");
			
			member = new Members();
			
			member.setMerId(session.getMerId());
			member.setMemberCode(reqParam.getMemberCode());
			member.setStoreId(session.getMerGroupId());
			member.setCreateDate(new Date());
			member.setAttentionDate(new Date());
			member.setMemberName(reqParam.getMemberCode());
			member.setMemberMk(MD5.encrypt(Long.toString(new Date().getTime())));
			
			// 寻找初级会员级别
			logger.info("merId is " + session.getMerId());
			ClientLevel clientLevelQuery = new ClientLevel();
			clientLevelQuery.setMerId(session.getMerId());
			ClientLevel lowestLevel = this.clientLevelMapper.selectLowestLevel(clientLevelQuery);
			if (lowestLevel != null)
			{
				logger.info("lowestLevel is " + lowestLevel.getClientLevelId());
				member.setClientLevelId(lowestLevel.getClientLevelId());
				member.setLevelName(lowestLevel.getLevelName());
			}
			else
			{
				logger.error("检索不到会员级别...");
			}
			logger.info("create member is " + member.getClientLevelId());
			this.memberMapper.insertSelective(member);
			
			// 查询出会员ID
			member = this.memberMapper.selectByCode(queryParam);
		}
		else
		{
			ClientLevel memberLevel = this.clientLevelMapper.selectByPrimaryKey(member.getClientLevelId());
			if (memberLevel != null)
			{
				member.setLevelName(memberLevel.getLevelName());
			}
		}
		
		// 生成会员支付用的token,对会员MK和orderId/会员token计算MD5
		Date now = new Date();
		String yinzi = member.getMemberMk() + session.getMerUserToken() + Long.toString(now.getTime());
		logger.info(yinzi);
		
		String memberToken = MD5.encrypt(yinzi);
		resp.setMemberPayToken(memberToken);
		
		MemberTokens memToken = new MemberTokens();
		memToken.setMemberId(member.getMemberId());
		memToken.setMenmberToken(memberToken);
		
		memToken.setCreateDate(now);
		//10 分钟后过期
		memToken.setExpiredDate(DateUtils.addMinutes(now, 10));
		
		logger.info("Create and save member token.");
		// 保存会员token
		this.memberTokenMpper.insertSelective(memToken);
		
		member.setLocked(true);
		member.setLockedToken(session.getMerUserToken());
		this.memberMapper.updateByPrimaryKeySelective(member);
		
		// 返回数据给终端
		resp.setRetCode("0");
		resp.setMemberCode(member.getMemberCode());
		resp.setMemberName(member.getMemberName());
		resp.setLevelName(member.getLevelName());
		resp.setMemberScore(member.getMemberScore());
		resp.setMemberBalance(member.getMemberBalance());
		
		return resp;
	}

	@Override
	public CreateOrderResp posCreateOrderWithoutGoods(CreateOrderReq reqParam, String ipAddress) 
	{
		CreateOrderResp resp = new CreateOrderResp();
		
		// 先检验头部，判断软件版本可用性
		HeadResp headResp = this.checkLastedVersion(reqParam.getSoftId(), reqParam.getVersion());
		
		resp.setHeadResp(headResp);
		
		if (!(headResp.getRetCode().equals("0") || headResp.getRetCode().equals("3") || headResp.getRetCode().equals("4")))
		{
			// 头部校验异常，直接返回
			return resp;
		}
		
		// 判断用户会员令牌和订单金额
		if (StringUtils.isEmpty(reqParam.getToken()) || reqParam.getOrderAmount() == null)
		{
			logger.warn("Token codes or orderAmount is null!");
			headResp.setRetCode("1");
			return resp;
		}
		
		logger.info("收款金额为 " + reqParam.getOrderAmount());
		
		// 检查会话是否过期
		MerchantUsersSession session = tokenExpired(reqParam.getToken());
		if (session == null || !session.getValid())
		{
			logger.warn("Token expired!");
			
			headResp.setRetMsg1(null);
			headResp.setRetMsg2(null);
			headResp.setRetCode("1");
			
			return resp;
		}
		
		logger.info("Create temp order info ...");
		// 创建临时订单
		TempPosOrders tempOrder = new TempPosOrders();
		
		// 生成订单号
		tempOrder.setTmpOrderId(UUID.randomUUID().toString());
		tempOrder.setOrderType(2);
		// 设置关联信息
		tempOrder.setMerGroupId(session.getMerGroupId());
		tempOrder.setMerId(session.getMerId());
		tempOrder.setMerUserId(session.getMerUserId());
		tempOrder.setCreateDate(new Date());
		tempOrder.setOperatorId(session.getMerUserId());
		tempOrder.setOperatorName(session.getUserName());
		
		// 保存到临时订单表中
		tempOrder.setTotalPrice(reqParam.getOrderAmount());
		tempOrder.setFinalPrice(reqParam.getOrderAmount());
		tempOrder.setDiscountPrice(0);
		
		this.tempPosOrderMapper.insertSelective(tempOrder);
		
		// 设置返回报文给终端
		resp.setRetCode("0");
		resp.setOrderId(tempOrder.getTmpOrderId());
		resp.setOrderAmount(tempOrder.getFinalPrice());
		
		return resp;
	}

	@Override
	public GoodsStockParam posGetGoodsStock(GoodsStockParam goodsStockParam, String ipAddress) {
		GoodsStockParam resp = new GoodsStockParam();
		
		// 先检验头部，判断软件版本可用性
		HeadResp headResp = this.checkLastedVersion(goodsStockParam.getSoftId(), goodsStockParam.getVersion());
		
		resp.setHeadResp(headResp);
		
		if (!(headResp.getRetCode().equals("0") || headResp.getRetCode().equals("3") || headResp.getRetCode().equals("4")))
		{
			// 头部校验异常，直接返回
			return resp;
		}
		
		// 判断用户会员令牌和订单金额
		if (StringUtils.isEmpty(goodsStockParam.getToken()) || goodsStockParam.getGoodsCode() == null)
		{
			logger.warn("Token codes or goodsCode is null!");
			headResp.setRetCode("1");
			return resp;
		}
		
		logger.info("商品条码为 " + goodsStockParam.getGoodsCode());
		
		// 检查会话是否过期
		MerchantUsersSession session = tokenExpired(goodsStockParam.getToken());
		if (session == null || !session.getValid())
		{
			logger.warn("Token expired!");
			
			headResp.setRetMsg1(null);
			headResp.setRetMsg2(null);
			headResp.setRetCode("1");
			
			return resp;
		}
		
		// 查询库存
		GoodsInfo goodsInfoQuery = new GoodsInfo();
		goodsInfoQuery.setMerId(session.getMerId());
		goodsInfoQuery.setGoodsCode(goodsStockParam.getGoodsCode());
		GoodsInfo goodsInfo = this.goodsInfoMapper.selectByGoodsCode(goodsInfoQuery);
		
		if (goodsInfo == null)
		{
			logger.info("找不到商品 " + goodsStockParam.getGoodsCode());
			resp.setRetCode("1");
			return resp;
		}
		
		logger.info("goodsId = " + goodsInfo.getGoodsId());
		logger.info("storeId = " + session.getMerGroupId());
		
		GoodsStock stockQuery = new GoodsStock();
		stockQuery.setOwnerId(session.getMerGroupId());
		stockQuery.setGoodsId(goodsInfo.getGoodsId());
		
		GoodsStock goodsStock = this.goodsStockMapper.selectStoreStockByGoodsId2(stockQuery);
		if (goodsStock == null)
		{
			logger.info("找不到商品库存 " + goodsStockParam.getGoodsCode());
			resp.setRetCode("1");
			return resp;
		}
		logger.info("goodsStock.getStockAmount() = " + goodsStock.getStockAmount());
		
		resp.setRetCode("0");
		resp.setGoodsCode(goodsStock.getGoodsCode());
		resp.setGoodsName(goodsInfo.getGoodsName());
		resp.setGoodsStock(goodsStock.getStockAmount());
		
		return resp;
	}

	@Override
	public GoodsStockParam posSetGoodsStock(GoodsStockParam goodsStockParam, String ipAddress) {
		
		GoodsStockParam resp = new GoodsStockParam();
		
		// 先检验头部，判断软件版本可用性
		HeadResp headResp = this.checkLastedVersion(goodsStockParam.getSoftId(), goodsStockParam.getVersion());
		
		resp.setHeadResp(headResp);
		
		if (!(headResp.getRetCode().equals("0") || headResp.getRetCode().equals("3") || headResp.getRetCode().equals("4")))
		{
			// 头部校验异常，直接返回
			return resp;
		}
		
		// 判断用户会员令牌和订单金额
		if (StringUtils.isEmpty(goodsStockParam.getToken()) || StringUtils.isEmpty(goodsStockParam.getGoodsCode()) 
				|| goodsStockParam.getGoodsStock() == null || goodsStockParam.getGoodsStock() < 0)
		{
			logger.warn("Token codes or goodsCode or goodsStock is null!");
			headResp.setRetCode("1");
			return resp;
		}
		
		logger.info("商品条码为 " + goodsStockParam.getGoodsCode());
		logger.info("商品库存为 " + goodsStockParam.getGoodsStock());
		
		// 检查会话是否过期
		MerchantUsersSession session = tokenExpired(goodsStockParam.getToken());
		if (session == null || !session.getValid())
		{
			logger.warn("Token expired!");
			
			headResp.setRetMsg1(null);
			headResp.setRetMsg2(null);
			headResp.setRetCode("1");
			
			return resp;
		}
		
		// 查询库存
		GoodsInfo goodsInfoQuery = new GoodsInfo();
		goodsInfoQuery.setMerId(session.getMerId());
		goodsInfoQuery.setGoodsCode(goodsStockParam.getGoodsCode());
		GoodsInfo goodsInfo = this.goodsInfoMapper.selectByGoodsCode(goodsInfoQuery);
		
		if (goodsInfo == null)
		{
			logger.info("找不到商品 " + goodsStockParam.getGoodsCode());
			resp.setRetCode("1");
			return resp;
		}
		
		GoodsStock stockQuery = new GoodsStock();
		stockQuery.setOwnerId(session.getMerGroupId());
		stockQuery.setGoodsId(goodsInfo.getGoodsId());
		
		GoodsStock goodsStock = this.goodsStockMapper.selectStoreStockByGoodsId2(stockQuery);
		if (goodsStock == null)
		{
			logger.info("找不到商品库存 " + goodsStockParam.getGoodsCode());
			resp.setRetCode("1");
			return resp;
		}
		
		int changeValue = goodsStockParam.getGoodsStock() - goodsStock.getStockAmount();
		if (changeValue > 0)
		{
			logger.info("实际库存比系统库存多 " + changeValue);
		}
		else
		{
			logger.info("实际库存比系统库存少 " + (-changeValue));
		}
		
		// 查询所有库存明细
		GoodsStockDetail query = new GoodsStockDetail();
		query.setGoodsId(goodsInfo.getGoodsId());
		query.setOwnerId(session.getMerGroupId());
		
		ArrayList<GoodsStockDetail> goodsStockDetails = this.goodsStockDetailMapper.selectByStoreGoods(query);
		if (goodsStockDetails != null && !goodsStockDetails.isEmpty())
		{
			if (changeValue > 0)
			{
				GoodsStockDetail detail = goodsStockDetails.get(0);
				detail.setStockCount(detail.getStockCount() + changeValue);
				
				this.goodsStockDetailMapper.updateByPrimaryKeySelective(detail);
			}
			else
			{
				// 库存调小
				changeValue = -1 * changeValue;
				
				for (GoodsStockDetail stockDetail : goodsStockDetails)
				{
					if (stockDetail.getStockCount() > 0)
					{
						if (stockDetail.getStockCount() >= changeValue)
						{
							stockDetail.setStockCount(stockDetail.getStockCount() - changeValue);
							this.goodsStockDetailMapper.updateByPrimaryKeySelective(stockDetail);
							changeValue = 0;
							break;
						}
						else
						{
							changeValue -= stockDetail.getStockCount();

							stockDetail.setStockCount(0);
							this.goodsStockDetailMapper.updateByPrimaryKeySelective(stockDetail);
						}
					}
				}
				
				// 库存仍然没有调完，允许为负数
				if (changeValue > 0)
				{
					GoodsStockDetail detail = goodsStockDetails.get(0);
					detail.setStockCount(-changeValue);
					
					this.goodsStockDetailMapper.updateByPrimaryKeySelective(detail);
				}
			}
		}
		
		// 刷新总库存
		logger.info("刷新总库存");
		goodsStock.setOwnerId(session.getMerGroupId());
		this.goodsStockMapper.statUpdateStoreStock(goodsStock);
		
		resp.setRetCode("0");
		resp.setGoodsCode(goodsStock.getGoodsCode());
		resp.setGoodsName(goodsInfo.getGoodsName());
		resp.setGoodsStock(goodsStockParam.getGoodsStock());
		
		return resp;
	}
	
}
