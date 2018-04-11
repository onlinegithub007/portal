package com.fuhuitong.applychain;

import com.fuhuitong.applychain.utils.MyUUID;
import org.apache.commons.exec.environment.EnvironmentUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FuHuiTongContext {
	
	private Logger logger = Logger.getLogger(getClass());
	private static FuHuiTongContext instance = null;
	private Properties validateMsg = new Properties();
	private Properties sysProps = new Properties();
	
	private ExecutorService logProcessorPool;
	
	private String webAppDir;
	private String homeDir;
	private String tmpDir;
	private String toolsDir;
	private String processorsDir;
	private String systemTitle;
	private String serverNo;
	private String excelTempDir;
	
	private String agentResPath;
	
	public static final String[] SERVER_OS = {"CentOs", "Redhat", "Ubuntu"};
	
	public static final String USER_FOR_LOG_COLLECTER = "fuhuitong";
	public static final String USERHOME_FOR_LOG_COLLECTER = "/home/fuhuitong";
	
	public static final String PARAM_KAFKA_BOOTSTRAP_SERVER = "kafka.bootstrap.servers";
	public static final String PARAM_HADOOP_MASTER = "hadoop.master.servers";
	public static final String PARAM_SPARK_HOME = "spark.home";
	public static final String PARAM_DATA_COLLECT_TIME = "data_collecting_time";
	public static final String PARAM_BAIDU_API_KEY = "baidu.map.api.appkey";
	public static final String PARAM_COMPANY_NAME = "company.name";
	public static final String PARAM_DOWNLOAD_URL = "app_download_url";
	public static final String PARAM_APP_STORE_DIR = "app_store_dir";
	public static final String PARAM_SYSTEM_EMAIL = "sys_email";
	public static final String PARAM_DEFAULT_PASSWORD = "default_password";
	
	public static final String PARAM_CUSTOMER_SERVICE_PHONE = "customer.service.phone";
	public static final String PARAM_POS_DOWNLOAD_URL = "pos.download.url";
	public static final String PARAM_REGISTING_EMAIL_TEMP = "registing.email.temp";
	
	public static final String PARAM_CREATING_STORE_EMAIL_TEMP = "creating.store.email.temp";
	public static final String PARAM_CREATING_STORE_EMAIL_TITLE = "creating.store.email.title";
	
	public static final String PARAM_REGISTING_EMAIL_CC_TEMP = "registing.email.cc.temp";
	public static final String PARAM_REGISTING_EMAIL_TITLE = "registing.email.title";
	public static final String PARAM_REGISTING_SMS_TEMP = "registing.sms.temp";
	public static final String PARAM_SMS_URL = "sms.url";
	public static final String PARAM_SMTP_PASSWORD = "smtp.password";
	public static final String PARAM_SMTP_SENDER = "smtp.sender";
	public static final String PARAM_SMTP_SERVER = "smtp.server";
	public static final String PARAM_SMTP_SERVER_PORT = "smtp.server.port";
	public static final String PARAM_WEB_PORTAL_URL = "web.portal.url";
	public static final String PARAM_PRODUCT_LEGEND = "product.legend";
	public static final String PARAM_MEASURE_UNIT = "measure.units";
	public static final String PARAM_GOODS_INFO_TEMP = "goods_info_template";
	public static final String PARAM_STORE_GOODS_INFO_TEMP = "store_goods_info_template";
	public static final String PARAM_MER_STOCK_TEMP = "merchant_goods_stock_template";
	public static final String PARAM_STORE_STOCK_ORDER_GOODS_TEMP = "store_stock_order_goods_template";
	public static final String PARAM_COPYRIGHT = "copyright";
	
	public static final String VERIFY_CODE = "VerifyCode";
	
	// 分店
	public static final Integer CLIENT_LEVEL_STORE = 0;
	
	// 供货商
	public static final Integer CLIENT_LEVEL_PROVIDER = 1;
	
	// 经销商
	public static final Integer CLIENT_LEVEL_SALER = 2;
	
	// 会员
	public static final Integer CLIENT_LEVEL_MEMBER = 3;
	
	public static final String READONLY = "readonly";
	
	// 现金支付
	public static final Integer PAY_METHOD_CASH = 0;

	// 微信支付用户主扫
	public static final Integer PAY_METHOD_WEIXIN = 1;
	
	// 支付宝用户主扫
	public static final Integer PAY_METHOD_ALIPAY = 2;

	// 翼支付用户主扫
	public static final Integer PAY_METHOD_YIPAY = 3;
	
	private FuHuiTongContext()
	{
		try {
			Resource validateMsgResource = new ClassPathResource("validateMsg.properties");
			validateMsg.load(validateMsgResource.getInputStream());
			
			Resource systemResource = new ClassPathResource("system.properties");
			sysProps.load(systemResource.getInputStream());
			
			systemTitle = sysProps.getProperty("system.title");
			serverNo = sysProps.getProperty("serverNo");
			
			// 设置服务器的No,防止在集群环境下主键冲突
			MyUUID.serverNo = serverNo + "-";
			
			this.webAppDir = System.getProperty("web.root");
			this.tmpDir = this.webAppDir + "WEB-INF" + File.separator + "tmp";
			
			this.excelTempDir = this.webAppDir + "WEB-INF" + File.separator + "assets" + File.separator + "excels";
			
			this.logProcessorPool = Executors.newFixedThreadPool(50);
		} 
		catch (IOException e)
		{
			logger.error(e.toString());
		}
	}

	public String getCurrentHome()
	{
		String home = null;
		
		try {
			Map<String, String> environments = EnvironmentUtils.getProcEnvironment();
			
			if (environments != null)
			{
				home = environments.get("HOME");
			}
		} catch (IOException e) {
			logger.error(e.toString());
		}
		
		return home;
	}
	
	public String getflumeResFile()
	{
		StringBuffer flumeResFile = new StringBuffer();
		
		flumeResFile.append(getWebAppDir()).append(File.separator).append("WEB-INF")
				.append(File.separator).append("asset").append(File.separator).append("agent.zip");
		
		return flumeResFile.toString();
	}
	
	public synchronized String getHomeDir() {
		return homeDir;
	}
	
	public static synchronized FuHuiTongContext getInstance()
	{
		if (instance == null)
		{
			instance = new FuHuiTongContext();
		}
		
		return instance;
	}
	
	public synchronized String getWebAppDir() {
		return webAppDir;
	}
	
	public synchronized String getValidateText(String key)
	{
		return this.validateMsg.getProperty(key);
	}
	
	public synchronized String getSystemTitle() {
		return systemTitle;
	}
	
	public synchronized String getToolsDir() {
		return toolsDir;
	}
	
	public synchronized String getTmpDir() {
		return tmpDir;
	}
	
	public synchronized String getAgentResPath() {
		return agentResPath;
	}
	
	public synchronized String getProcessorsDir() {
		return processorsDir;
	}

	public synchronized ExecutorService getLogProcessorPool() {
		return logProcessorPool;
	}

	public synchronized void setLogProcessorPool(ExecutorService logProcessorPool) {
		this.logProcessorPool = logProcessorPool;
	}
	
	public synchronized String getExcelTempDir() {
		return excelTempDir;
	}
}
