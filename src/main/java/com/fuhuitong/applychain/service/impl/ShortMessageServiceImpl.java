package com.fuhuitong.applychain.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fuhuitong.applychain.dao.ShortMessageMapper;
import com.fuhuitong.applychain.model.ShortMessage;
import com.fuhuitong.applychain.service.IShortMessageService;
import com.fuhuitong.applychain.utils.MD5;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

@Service
public class ShortMessageServiceImpl implements IShortMessageService {

	private Logger logger = Logger.getLogger(getClass());
	
	private String smsUrl;
	private String smsApiAccount;
	private String smsApiKey;
	
	private String smsSignTempId;
	private String provicerRegTempId;
	private String provicerOrderTempId;
	private String stockOrderAuditTempId;
	private String storeMasterRegTempId;
	private String appId;
	
	@Resource
	private ShortMessageMapper shortMessageMapper;
	
	public ShortMessageServiceImpl() {
		init();
	}
	
	private void init()
	{
		try {
			org.springframework.core.io.Resource systemResource = new ClassPathResource("sms.properties");
			Properties sysProps = new Properties();
			sysProps.load(systemResource.getInputStream());
			
			this.smsUrl = sysProps.getProperty("smsUrl");
			this.smsApiAccount = sysProps.getProperty("smsApiAccount");
			this.smsApiKey = sysProps.getProperty("smsApiKey");
			this.smsSignTempId = sysProps.getProperty("smsSignTempId");
			this.provicerRegTempId = sysProps.getProperty("provicerRegTempId");
			this.provicerOrderTempId = sysProps.getProperty("provicerOrderTempId");
			this.stockOrderAuditTempId = sysProps.getProperty("stockOrderAuditTempId");
			this.appId = sysProps.getProperty("appId");
			this.storeMasterRegTempId = sysProps.getProperty("storeMasterRegTempId");
			
			logger.info("smsUrl = " + this.smsUrl);
			logger.info("smsApiAccount = " + this.smsApiAccount);
			logger.info("smsApiKey = " + this.smsApiKey);
			logger.info("smsSignTempId = " + this.smsSignTempId);
			logger.info("provicerRegTempId = " + this.provicerRegTempId);
			logger.info("provicerOrderTempId = " + this.provicerOrderTempId);
			logger.info("stockOrderAuditTempId = " + this.stockOrderAuditTempId);
		} catch (IOException e) {
			logger.error(e.toString());
		}
	}
	
	@Override
	public ArrayList<ShortMessage> selectByDate(String sendDateText) {
		
		return this.shortMessageMapper.selectByDate(sendDateText);
	}

	@Override
	public boolean sendSms(String phoneCode, String message) {
		
		ShortMessage msg = new ShortMessage();
		
		msg.setDestAddr(phoneCode);
		msg.setMessage(message);
		msg.setSendDate(new Date());
		
		// TODO 调用发短信接口
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(this.smsUrl);
		httppost.setEntity(EntityBuilder.create().setText(message).build());
		
		logger.info("Executing request: " + httppost.getRequestLine());
		
        try {
			CloseableHttpResponse response = httpclient.execute(httppost);
			
			logger.info(response.getStatusLine());
			logger.info(EntityUtils.toString(response.getEntity()));

			this.shortMessageMapper.insertSelective(msg);
		} catch (IOException e) {
			logger.error(e.toString());
			
			return false;
		}
        
        return true;
	}

	private JSONObject getBasicJSONObject(String phoneCode, String tempId)
	{
		JSONObject json = new JSONObject();
		
		json.put("apiAccount", this.smsApiAccount);
		json.put("appId", this.appId);
		
		String timeStamp = Long.toString(new Date().getTime());
		String sign = MD5.encrypt(this.smsApiAccount + this.smsApiKey + timeStamp).toLowerCase();
		json.put("sign", sign);
		json.put("timeStamp", timeStamp);
		
		// 短信模板
		json.put("templateId", tempId);
		
		// 短信签名
		json.put("singerId", this.smsSignTempId);
		
		// 手机号
		json.put("mobile", phoneCode);
		
		return json;
	}
	
	@Override
	public boolean sendProviderOrderMessage(String phoneCode, String provider, String userName, int orderCount, String userAccount,
			String url) {
		
		JSONObject json = getBasicJSONObject(phoneCode, this.provicerOrderTempId);
		
		StringBuffer params = new StringBuffer();
		
		// #provider#的#userName#您好，您在赋汇通翼超市系统有#orderCount#个采购单需要处理，请使用账号 #userAccount#，请在PC上登录 #url#，及时处理订单信息。
		params.append(provider).append(",");
		params.append(userName).append(",");
		params.append(orderCount).append(",");
		params.append(userAccount).append(",");
		params.append(url);
		
		// 数据
		json.put("param", params.toString());
		
		return sendSms(phoneCode, json.toJSONString());
	}

	@Override
	public boolean sendProviderRegMessage(String phoneCode, String provider, String userName, String userAccount, String userPwd,
			String url) {
		
		JSONObject json = getBasicJSONObject(phoneCode, this.provicerRegTempId);
		
		StringBuffer params = new StringBuffer();
		
		// #provider#的#userName#您好，您在赋汇通翼超市系统有#orderCount#个采购单需要处理，请使用账号 #userAccount#，请在PC上登录 #url#，及时处理订单信息。
		params.append(provider).append(",");
		params.append(userName).append(",");
		params.append(userAccount).append(",");
		params.append(userPwd).append(",");
		params.append(url);
		
		// 数据
		json.put("param", params.toString());
		
		return sendSms(phoneCode, json.toJSONString());
	}
	
	@Override
	public boolean sendStockOrderAuditMessage(String phoneCode, String userName, int count, String userAccount, String url) {
		
		JSONObject json = getBasicJSONObject(phoneCode, this.stockOrderAuditTempId);
		
		StringBuffer params = new StringBuffer();
		
		// #provider#的#userName#您好，您在赋汇通翼超市系统有#orderCount#个采购单需要处理，请使用账号 #userAccount#，请在PC上登录 #url#，及时处理订单信息。
		params.append(userName).append(",");
		params.append(count).append(",");
		params.append(userAccount).append(",");
		params.append(url);
		
		// 数据
		json.put("param", params.toString());
		
		return sendSms(phoneCode, json.toJSONString());
	}

	@Override
	public boolean sendStoreMasterRegMessage(String phoneCode, String userName, String userAccount, String userPwd, String url) {
		
		JSONObject json = getBasicJSONObject(phoneCode, this.storeMasterRegTempId);
		
		StringBuffer params = new StringBuffer();
		
		// #provider#的#userName#您好，您在赋汇通翼超市系统有#orderCount#个采购单需要处理，请使用账号 #userAccount#，请在PC上登录 #url#，及时处理订单信息。
		params.append(userName).append(",");
		params.append(userAccount).append(",");
		params.append(userPwd).append(",");
		params.append(url);
		
		// 数据
		json.put("param", params.toString());
		
		return sendSms(phoneCode, json.toJSONString());
	}
	
	
}
