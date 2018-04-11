/**
 * 
 */
package com.fuhuitong.applychain.task;

import com.fuhuitong.applychain.service.IOrdersService;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author haoqingfeng
 *
 */
@Component("storeSalesDailyReportTask")
@PropertySource("classpath:system.properties")
public class StoreSalesDailyReportsTask {

	private Logger logger = Logger.getLogger(getClass());
	
	@Value("${storeSalesDailyReportMerIds}")
	private String storeSalesDailyReportMerIds;
	
	@Resource
	private IOrdersService ordersService;
	
	/**
	 * 
	 */
	public StoreSalesDailyReportsTask() {
		
	}

	public void run()
	{
		logger.info("自动生成门店销售报表任务启动...");
		logger.info("storeSalesDailyReportMerIds is " + storeSalesDailyReportMerIds);
		
		String[] merIds = this.storeSalesDailyReportMerIds.split(",");
		
		// 定时任务在凌晨跑，统计头一天的
		Date now = DateUtils.addDays(new Date(), -1);
		String saleDateText = DateFormatUtils.format(now, "yyyy-MM-dd");
		ordersService.makeStoreSalesDailyReport(merIds, saleDateText);
		
		logger.info("自动生成门店销售报表任务完成 .");
	}
}
