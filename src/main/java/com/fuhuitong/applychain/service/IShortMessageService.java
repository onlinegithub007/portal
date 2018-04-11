package com.fuhuitong.applychain.service;

import com.fuhuitong.applychain.model.ShortMessage;

import java.util.ArrayList;

public interface IShortMessageService 
{
	public ArrayList<ShortMessage> selectByDate(String sendDateText);
	
	public boolean sendSms(String phoneCode, String message);
	
	/**
	 * #provider#的#userName#您好，赋汇通翼超市系统为您分配供应商账号 #userAccount#，登录密码为 #userPwd#，请在PC上登录 #url#，及时处理订单信息。
	 * @param proverUserName
	 * @param providerUnit
	 * @return
	 */
	public boolean sendProviderRegMessage(String phoneCode, String provider, String userName, String userAccount, String userPwd, String url);
	
	/**
	 * #provider#的#userName#您好，您在赋汇通翼超市系统有#orderCount#个采购单需要处理，请使用账号 #userAccount#，请在PC上登录 #url#，及时处理订单信息。
	 * @return
	 */
	public boolean sendProviderOrderMessage(String phoneCode, String provider, String userName, int orderCount, String userAccount, String url);
	
	/**
	 * #userName#您好，您在赋汇通翼超市系统有#count#个采购单需要审核处理，请使用账号 #userAccount#，请在PC上登录 #url#，及时进行处理。
	 * @return
	 */
	public boolean sendStockOrderAuditMessage(String phoneCode, String userName, int count, String userAccount, String url);
	
	/**
	 * #userName#您好，赋汇通翼超市系统为您分配了店长账号 #userAccount#，登录密码为 #userPwd#，请在PC上登录赋汇通翼超市系统 #url#。
	 * @return
	 */
	public boolean sendStoreMasterRegMessage(String phoneCode, String userName, String userAccount, String userPwd, String url);
}
