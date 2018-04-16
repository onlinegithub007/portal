package com.fuhuitong.applychain.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fuhuitong.applychain.FuHuiTongContext;
import com.fuhuitong.applychain.dao.*;
import com.fuhuitong.applychain.model.*;
import com.fuhuitong.applychain.service.IOrdersService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

@Service
public class OrdersServiceImpl extends BaseService implements IOrdersService {

    @Resource
    private OrdersMapper ordersMapper;

    @Resource
    private OrderDetailsMapper orderDetailsMapper;

    @Resource
    private StockOrdersDetailMapper stockOrdersDetailMapper;

    @Resource
    private GoodsStockMapper goodsStockMapper;

    @Resource
    private StoreDailyClearingMapper storeDailyClearingMapper;

    @Resource
    private MerchantsGroupsMapper merGroupsMapper;

    @Resource
    private GoodsStockDetailMapper goodsStockDetailMapper;

    @Resource
    private StoreSalesStatMapper storeSalesStatMapper;

    @Resource
    private MerchantUsersMapper usersMapper;

    @Resource
    private StoreSalesReportsMapper storeSalesReportMapper;

    @Resource
    private GoodsPriceMapper goodsPriceMapper;

    @Resource
    private StockDispatchMapper stockDispatchMapper;
    @Resource
    private ChargeMapper chargeMapper;
    @Resource
    private BankCardBinMapper bankCardBinMapper;

    @Override
    public String storeOrderRecords(HttpServletRequest request, HttpSession session) {

        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return "page/relogin";
        }


        return "page/orders/store_orders_records";
    }

    @Override
    public String storeOrderRecordsData(HttpServletRequest request, HttpSession session, Orders query) {

        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return getTableData(null, null, 0);
        }

        if (StringUtils.isEmpty(query.getMerGroupId())) {
            query.setMerGroupId(this.merUser.getMerGroupId());
        } else {
            query.setMerGroupId(query.getMerGroupId());
        }

        if (StringUtils.isEmpty(query.getCreateDateText())) {
            query.setCreateDateText(null);
        }

        query.setMerId(this.merUser.getMerId());
        int totalCount = this.ordersMapper.selectCountByDate2(query);

        if (totalCount > 0) {
            ArrayList<Orders> orders = this.ordersMapper.selectByDate2(query);

            if (orders != null && !orders.isEmpty()) {
                return getTableData(orders, null, totalCount);
            }
        }

        return getTableData(null, null, 0);
    }

    @Override
    public String storeOrderGoodsData(HttpServletRequest request, HttpSession session, Orders query) {

        if (!this.merUserHasAccessRight(request, session) || StringUtils.isEmpty(query.getOrderId())) {
            logger.info("Current user was not logged in.");
            return getTableData(null, null, 0);
        }

        ArrayList<OrderDetails> orderDetails = this.orderDetailsMapper.selectByOrderId(query.getOrderId());
        if (orderDetails != null && !orderDetails.isEmpty()) {
            return getTableData(orderDetails, null, orderDetails.size());
        }

        return getTableData(null, null, 0);
    }

    @Override
    public String storeDailyClearing(HttpServletRequest request, HttpSession session) {
        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return "page/relogin";
        }

        return "page/orders/store_daily_clearing";
    }

    @Override
    public String storeDailyClearingData(HttpServletRequest request, HttpSession session, StoreDailyClearing param) {

        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return getTableData(null, null, 0);
        }

        if (StringUtils.isEmpty(param.getClearingDateText())) {
            param.setClearingDateText(null);
        }

        param.setMerId(this.merUser.getMerId());
        param.setMerGroupId(this.merUser.getMerGroupId());

        Integer totalCount = this.storeDailyClearingMapper.selectStoreDailyCount(param);
        if (totalCount != null && totalCount > 0) {
            ArrayList<StoreDailyClearing> dailyClearings = this.storeDailyClearingMapper.selectStoreDaily(param);
            if (dailyClearings != null && !dailyClearings.isEmpty()) {
                return getTableData(dailyClearings, null, totalCount);
            }
        }

        return getTableData(null, null, 0);
    }

    @Override
    public String storeDailyClearingDo(HttpServletRequest request, HttpSession session, String createDateText) {
        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
        }

        Orders query = new Orders();
        query.setMerGroupId(this.merUser.getMerGroupId());
        if (StringUtils.isEmpty(createDateText)) {
            query.setCreateDateText(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
        } else {
            query.setCreateDateText(createDateText);
        }

        StoreDailyClearing todayOrderStat = new StoreDailyClearing();
        todayOrderStat.setMerId(this.merUser.getMerId());
        todayOrderStat.setMerGroupId(this.merUser.getMerGroupId());
        try {
            todayOrderStat.setClearingDate(DateUtils.parseDate(query.getCreateDateText(), new String[]{"yyyy-MM-dd"}));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 先查询全部订单和金额
        Orders allResult = this.ordersMapper.statOnClearingByDate(query);
        if (allResult.getTotalCount() != null) {
            todayOrderStat.setOrderCount(allResult.getTotalCount());
            todayOrderStat.setOrderAmount(allResult.getFinalPrice());
            todayOrderStat.setOrderProfit(allResult.getOrderProfit());
        }

        // 查询现金交易
        query.setPayMethod(FuHuiTongContext.PAY_METHOD_CASH);
        allResult = this.ordersMapper.statOnClearingByDate(query);
        if (allResult.getTotalCount() != null) {
            todayOrderStat.setCashOrderCount(allResult.getTotalCount());
            todayOrderStat.setCashOrderAmount(allResult.getFinalPrice());
        }

        // 查询微信支付
        query.setPayMethod(FuHuiTongContext.PAY_METHOD_WEIXIN);
        allResult = this.ordersMapper.statOnClearingByDate(query);
        if (allResult.getTotalCount() != null) {
            todayOrderStat.setWeixinOrderCount(allResult.getTotalCount());
            todayOrderStat.setWeixinOrderAmount(allResult.getFinalPrice());
        }

        // 支付宝支付
        query.setPayMethod(FuHuiTongContext.PAY_METHOD_ALIPAY);
        allResult = this.ordersMapper.statOnClearingByDate(query);
        if (allResult.getTotalCount() != null) {
            todayOrderStat.setAlipayOrderCount(allResult.getTotalCount());
            todayOrderStat.setAlipayOrderAmount(allResult.getFinalPrice());
        }

        // 查询翼支付交易
        query.setPayMethod(FuHuiTongContext.PAY_METHOD_YIPAY);
        allResult = this.ordersMapper.statOnClearingByDate(query);
        if (allResult.getTotalCount() != null) {
            todayOrderStat.setYipayOrderCount(allResult.getTotalCount());
            todayOrderStat.setYipayOrderAmount(allResult.getFinalPrice());
        }

        // 删除已经存在的
        this.storeDailyClearingMapper.deleteByDate(todayOrderStat);

        this.storeDailyClearingMapper.insertSelective(todayOrderStat);

        // 统计当日销售商品以及库存情况，删除重复的
        StoreSalesStat stat = new StoreSalesStat();
        stat.setCreateDateText(createDateText);
        stat.setMerId(this.merUser.getMerId());
        stat.setMerGroupId(this.merUser.getMerGroupId());
        this.storeSalesStatMapper.deleteByDate(stat);

        // 提前把当前门店库存加载出来
        ArrayList<GoodsStock> currentStoreStocks = this.goodsStockMapper.selectByStore(this.merUser.getMerGroupId());

        // 存入到临时map中便于快速检索
        Hashtable<String, GoodsStock> currentStoreStocksMap = new Hashtable<>();
        for (GoodsStock stock : currentStoreStocks) {
            currentStoreStocksMap.put(stock.getGoodsId(), stock);
        }

        // 统计当日销售商品数量
        OrderDetails param = new OrderDetails();
        param.setMerGroupId(this.merUser.getMerGroupId());
        param.setCreateDateText(createDateText);
        ArrayList<OrderDetails> orderDetailsStatResult = this.orderDetailsMapper.statSalesByDate(param);

        if (orderDetailsStatResult != null && !orderDetailsStatResult.isEmpty()) {
            for (OrderDetails detail : orderDetailsStatResult) {
                StoreSalesStat newStat = new StoreSalesStat();
                Date now = null;
                try {
                    now = DateUtils.parseDate(createDateText, new String[]{"yyyy-MM-dd"});
                } catch (ParseException e) {
                    now = new Date();
                }
                newStat.setCreateDate(now);
                newStat.setGoodsId(detail.getGoodsId());
                newStat.setGoodsSaleCount(detail.getGoodsAmount());

                GoodsStock stock = currentStoreStocksMap.get(detail.getGoodsId());
                if (stock != null) {
                    newStat.setGoodsStockCount(stock.getStockAmount());
                } else {
                    newStat.setGoodsStockCount(0);
                }

                newStat.setMerId(this.merUser.getMerId());
                newStat.setMerGroupId(this.merUser.getMerGroupId());

                this.storeSalesStatMapper.insertSelective(newStat);
            }
        }

        return getRetCodeWithJson(0, getMessage("storeDailyClearingDo.success"));
    }

    @Override
    public String merchantStoresOrderRecords(HttpServletRequest request, HttpSession session) {
        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return "page/relogin";
        }

        // 查询所有门店信息
        ArrayList<MerchantsGroups> allStores = this.merGroupsMapper.selectAllStores(this.merUser.getMerId());
        if (allStores != null && !allStores.isEmpty()) {
            session.setAttribute("AllStoresForRecord", allStores);
        }

        return "page/orders/merchant_stores_orders_records";
    }

    @Override
    public String masterStoresOrderRecordsData(HttpServletRequest request, HttpSession session, Orders query) {
        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return getTableData(null, null, 0);
        }

        query.setMerId(this.merUser.getMerId());
        if (StringUtils.isEmpty(query.getMerGroupId())) {
            query.setMerGroupId(null);
        }

        if (StringUtils.isEmpty(query.getCreateDateText())) {
            query.setCreateDateText(null);
        }

        if (StringUtils.isEmpty(query.getEndDateText())) {
            query.setEndDateText(null);
        }

        if (StringUtils.isEmpty(query.getOrderCode())) {
            query.setOrderCode(null);
        }

        if (StringUtils.isEmpty(query.getTermCode())) {
            query.setTermCode(null);
        }
        if (StringUtils.isEmpty(query.getTermSn())) {
            query.setTermSn(null);
        }

        int totalCount = this.ordersMapper.selectCountByDate2(query);

        if (totalCount > 0) {
            ArrayList<Orders> orders = this.ordersMapper.selectByDate2(query);

            if (orders != null && !orders.isEmpty()) {
                return getTableData(orders, null, totalCount);
            }
        }

        return getTableData(null, null, 0);
    }

    @Override
    public String masterStoresOrderRecordsConfirm(HttpServletRequest request, HttpSession session, Orders query) {

        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
        }

        if (StringUtils.isEmpty(query.getOrderId())) {
            logger.info("Cant find order.");
            return getRetCodeWithJson(1, getMessage("masterStoresOrderRecordsConfirm.orderNotExists"));
        }

        query.setOrderConfirm(1);
        query.setOrderConfirmDate(new Date());
        query.setOrderConfirmPerson(this.merUser.getUserName());

        this.ordersMapper.updateOrderConfirm(query);

        return getRetCodeWithJson(0, getMessage("masterStoresOrderRecordsConfirm.confirmSuccess"));
    }

    @Override
    public String storeDailyClearingRereshProfit(HttpServletRequest request, HttpSession session) {
        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return getRetCodeWithJson(1, "刷新订单毛利润失败");
        }

        /**
         * 加载所有交易成功的订单
         * 遍历所有订单明细，根据其库存明细获取商品进货价，更新到订单明细中
         * 刷新订单的毛利润
         */

        ArrayList<Orders> orders = this.ordersMapper.selectAll();
        if (orders != null && !orders.isEmpty()) {
            for (Orders order : orders) {
                ArrayList<OrderDetails> orderDetails = this.orderDetailsMapper.selectByOrderId(order.getOrderId());

                for (OrderDetails detail : orderDetails) {
                    // 查询其进货批次信息
                    GoodsStockDetail goodsStockDetail = this.goodsStockDetailMapper.selectByPrimaryKey(detail.getOrderStockDetailId());
                    if (goodsStockDetail != null) {
                        detail.setGoodsCost(goodsStockDetail.getGoodsCost());

                        this.orderDetailsMapper.updateStockDetailId(detail);
                    }
                }

                // 刷新 Orders毛利
                this.ordersMapper.updateOrderProfit(order.getOrderId());
            }
        }

        return getRetCodeWithJson(0, "刷新订单毛利润成功");
    }

    @Override
    public String storeDailyClearingDetails(HttpServletRequest request, HttpSession session, String createDateText) {
        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return "page/relogin";
        }

        session.setAttribute("StoreDailyCreateDateText", createDateText);

        return "page/orders/store_daily_clearing_details";
    }

    @Override
    public String storeDailyClearingDetailsData(HttpServletRequest request, HttpSession session) {

        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return getTableData(null, null, 0);
        }

        String createDateText = (String) session.getAttribute("StoreDailyCreateDateText");

        StoreSalesStat record = new StoreSalesStat();
        record.setCreateDateText(createDateText);
        record.setMerId(this.merUser.getMerId());
        record.setMerGroupId(this.merUser.getMerGroupId());

        Integer totalCount = this.storeSalesStatMapper.selectCountByDate(record);
        if (totalCount != null && totalCount > 0) {
            ArrayList<StoreSalesStat> statData = this.storeSalesStatMapper.selectByDate(record);
            if (statData != null && !statData.isEmpty()) {
                return getTableData(statData, null, totalCount);
            }
        }

        return getTableData(null, null, 0);
    }

    @Override
    public String storeGoodsSaleSort(HttpServletRequest request, HttpSession session) {
        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return "page/relogin";
        }

        // 查询所有门店信息
        ArrayList<MerchantsGroups> allStores = this.merGroupsMapper.selectAllStores(this.merUser.getMerId());
        if (allStores != null && !allStores.isEmpty()) {
            session.setAttribute("AllStoresForRecord", allStores);
        }

        return "page/orders/stores_goods_sale_sort";
    }

    @Override
    public String storeGoodsSaleSortData(HttpServletRequest request, HttpSession session, OrderDetails param) {
        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return getTableData(null, null, 0);
        }

        if (StringUtils.isEmpty(param.getCreateDateText())) {
            return getTableData(null, null, 0);
        }

        if (StringUtils.isEmpty(param.getEndDateText())) {
            return getTableData(null, null, 0);
        }

        param.setMerId(this.merUser.getMerId());
        if (StringUtils.isEmpty(param.getMerGroupId())) {
            param.setMerGroupId(null);
        }

        ArrayList<OrderDetails> orderDetails = this.orderDetailsMapper.statGoodsSalesByDate(param);
        if (orderDetails != null && !orderDetails.isEmpty()) {
            return getTableData(orderDetails, "", orderDetails.size());
        }

        return getTableData(null, null, 0);
    }

    @Override
    public String storeSaleSort(HttpServletRequest request, HttpSession session) {
        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return "page/relogin";
        }

        return "page/orders/stores_sale_sort";
    }

    @Override
    public String storeSaleSortData(HttpServletRequest request, HttpSession session, Orders param) {
        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return getTableData(null, null, 0);
        }

        session.removeAttribute("StoreSaleSortChartData");

        if (StringUtils.isEmpty(param.getCreateDateText()) || StringUtils.isEmpty(param.getEndDateText())) {
            return getTableData(null, null, 0);
        }

        param.setMerId(this.merUser.getMerId());
        ArrayList<Orders> storeSaleData = this.ordersMapper.selectStoreSaleDataByDates(param);
        if (storeSaleData != null && !storeSaleData.isEmpty()) {
            session.setAttribute("StoreSaleSortChartData", getChartData("门店销售排行", storeSaleData));
            return getTableData(storeSaleData, null, storeSaleData.size());
        }

        return getTableData(null, null, 0);
    }

    private String getChartData(String title, ArrayList<Orders> chartsTableData) {
        JSONObject obj = new JSONObject();

        ArrayList<String> storeNames = new ArrayList<>();
        ArrayList<Integer> storeTotalPrices = new ArrayList<>();
        ArrayList<Integer> storeOrderProfits = new ArrayList<>();

        if (chartsTableData != null && !chartsTableData.isEmpty()) {
            for (Orders chart : chartsTableData) {
                storeNames.add(chart.getGroupName());
                storeTotalPrices.add(chart.getTotalPrice() / 100);
                storeOrderProfits.add(chart.getOrderProfit() / 100);
            }
        }

        obj.put("title", title);
        obj.put("storeName", storeNames);
        obj.put("stockTotalPrices", storeTotalPrices);
        obj.put("storeOrderProfits", storeOrderProfits);

        return obj.toJSONString();
    }

    @Override
    public String storeSaleSortChartData(HttpServletRequest request, HttpSession session) {
        String chartData = (String) session.getAttribute("StoreSaleSortChartData");
        if (chartData == null) {
            chartData = getChartData("门店销售排行", null);
        }

        logger.info(chartData);
        return chartData;
    }

    @Override
    public String providersSaleStatSelect(HttpServletRequest request, HttpSession session) {
        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return "page/relogin";
        }

        return "page/orders/providers_sale_stat_select";
    }

    @Override
    public String providersSaleStatReport(HttpServletRequest request, HttpSession session, String merUserId) {
        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return "page/relogin";
        }

        MerchantUsers currentProviderUser = this.usersMapper.selectByPrimaryKey(merUserId);
        session.setAttribute("CurrentProviderUser", currentProviderUser);

        return "page/orders/providers_sale_stat_report";
    }

    @Override
    public String providersSaleStatReportData(HttpServletRequest request, HttpSession session, OrderDetails orderDetail) {

        if (!this.merUserHasAccessRight(request, session) || StringUtils.isEmpty(orderDetail.getCreateDateText())
                || StringUtils.isEmpty(orderDetail.getEndDateText())) {
            return getTableData(null, null, 0);
        }

        MerchantUsers currentProviderUser = (MerchantUsers) session.getAttribute("CurrentProviderUser");

        orderDetail.setMerId(this.merUser.getMerId());
        orderDetail.setProviderId(currentProviderUser.getMerUserId());
        ArrayList<OrderDetails> providerSaleData = this.orderDetailsMapper.statGoodsSalesByProvider(orderDetail);

        if (providerSaleData != null && !providerSaleData.isEmpty()) {
            return getTableData(providerSaleData, null, providerSaleData.size());
        }

        return getTableData(null, null, 0);
    }

    @Override
    public String storeTimeSaleSort(HttpServletRequest request, HttpSession session) {

        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return "page/relogin";
        }

        // 查询所有门店信息
        ArrayList<MerchantsGroups> allStores = this.merGroupsMapper.selectAllStores(this.merUser.getMerId());
        if (allStores != null && !allStores.isEmpty()) {
            session.setAttribute("AllStoresForRecord", allStores);
        }

        return "page/orders/stores_time_sale_sort";
    }

    @Override
    public String storeTimeSaleData(HttpServletRequest request, HttpSession session, OrderDetails param) {

        if (!this.merUserHasAccessRight(request, session) || StringUtils.isEmpty(param.getCreateDateText())) {
            return getTableData(null, null, 0);
        }

        param.setMerId(this.merUser.getMerId());
        if (StringUtils.isEmpty(param.getMerGroupId())) {
            param.setMerGroupId(null);
        }

        // 先查询当日所有订单
        Orders orderParam = new Orders();
        orderParam.setMerId(this.merUser.getMerId());
        orderParam.setCreateDateText(param.getCreateDateText());
        ArrayList<Orders> currentOrders = this.ordersMapper.selectByDatetime(orderParam);

        Hashtable<String, Orders> orderTables = new Hashtable<>();
        for (Orders order : currentOrders) {
            orderTables.put(order.getOrderId(), order);
        }

        ArrayList<OrderDetails> orderDetailOneDay = this.orderDetailsMapper.statTimeSalesBy(param);
        if (orderDetailOneDay != null && !orderDetailOneDay.isEmpty()) {
            logger.info("orderDetailOneDay size : " + orderDetailOneDay.size());

            for (OrderDetails detail : orderDetailOneDay) {
                Orders order = orderTables.get(detail.getOrderId());
                order.setTotalCount(detail.getGoodsAmount());
                order.setTotalPrice(detail.getGoodsTotalPrice());
            }

            // 分组到小时内
            ArrayList<Orders> hourOrdersData = new ArrayList<>();
            Hashtable<String, Orders> orderHourTables = new Hashtable<>();

            for (Orders order : currentOrders) {
                String hour = order.getCreateDateText();  // yyyy-MM-dd HH:mm:ss
                Date date;
                try {
                    date = DateUtils.parseDate(hour, new String[]{"yyyy-MM-dd HH:mm:ss"});
                    hour = DateFormatUtils.format(date, "HH");

                    Orders hourOrder = orderHourTables.get(hour);
                    if (hourOrder == null) {
                        hourOrder = order;
                        orderHourTables.put(hour, hourOrder);
                        hourOrder.setHour(hour);
                        hourOrdersData.add(hourOrder);
                    } else {
                        // 合并数据
                        int totalCount = hourOrder.getTotalCount() + order.getTotalCount();
                        hourOrder.setTotalCount(totalCount);

                        int totalPrice = hourOrder.getTotalPrice() + order.getTotalPrice();
                        hourOrder.setTotalPrice(totalPrice);
                    }
                } catch (ParseException e) {
                    logger.error(e.toString());
                    e.printStackTrace();
                }
            }

            int totalPrice = 0;
            for (Orders order : hourOrdersData) {
                totalPrice += order.getTotalPrice();
            }

            // 计算资金占比
            for (Orders order : hourOrdersData) {
                order.setPercent((float) order.getTotalPrice() * 100 / totalPrice);
            }

            return getTableData(hourOrdersData, null, hourOrdersData.size());
        }

        return getTableData(null, null, 0);
    }

    @Override
    public String storesSaleReports(HttpServletRequest request, HttpSession session) {
        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return "page/relogin";
        }

        // 查询所有门店信息
        ArrayList<MerchantsGroups> allStores = this.merGroupsMapper.selectAllStores(this.merUser.getMerId());
        if (allStores != null && !allStores.isEmpty()) {
            session.setAttribute("AllStoresForRecord", allStores);
        }

        return "page/orders/stores_sale_reports";
    }

    @Override
    public String storesSaleReportsData(HttpServletRequest request, HttpSession session, StoreSalesReports param) {
        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return getTableData(null, null, 0);
        }

        if (StringUtils.isEmpty(param.getCreateDateText()) || StringUtils.isEmpty(param.getMerGroupId())) {
            logger.info("输入查询参数为空");
            return getTableData(null, null, 0);
        }

//		// TODO 测试
//		String[] merIds = {"726d75cf-29e6-45aa-89a8-67e124af0199"};
//		
//		String salesDateText = "2017-11-01";
//		try {
//			Date salesDate = DateUtils.parseDate(salesDateText, new String[]{"yyyy-MM-dd"});
//
//			while(true)
//			{
//				makeStoreSalesDailyReport(merIds, salesDateText);
//				
//				salesDate = DateUtils.addDays(salesDate, 1);
//				salesDateText = DateFormatUtils.format(salesDate, "yyyy-MM-dd");
//				
//				if (salesDateText.equalsIgnoreCase("2017-11-04"))
//				{
//					break;
//				}
//			}
//			
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}

        ArrayList<StoreSalesReports> storeSalesReportsList = this.storeSalesReportMapper.selectByDate(param);
        if (storeSalesReportsList != null && !storeSalesReportsList.isEmpty()) {
            return getTableData(storeSalesReportsList, "", storeSalesReportsList.size());
        }

        return getTableData(null, null, 0);
    }

    @Override
    public String addRateByMerId(HttpServletRequest request, HttpSession session) {
        List<Orders> ls = ordersMapper.selectAll();
        for (int i = 0; i < ls.size(); i++) {
            Orders orders = ls.get(i);

                Charge charge = chargeMapper.findByMerId(orders.getMerId());
                if (charge != null) {
                    if (orders.getPayMethod() == 1) {//1 微信
                        orders.setChargeRate("" + charge.getWechat());
                        orders.setChargeAmount((orders.getFinalPrice() * charge.getWechat()) + "");
                    } else if (orders.getPayMethod() == 2) {//2 支付宝
                        orders.setChargeRate("" + charge.getAlipay());
                        orders.setChargeAmount((orders.getFinalPrice() * charge.getAlipay()) + "");
                    } else if (orders.getPayMethod() == 3) {//2 翼支付
                        orders.setChargeRate("" + charge.getBestpay());
                        orders.setChargeAmount((orders.getFinalPrice() * charge.getBestpay()) + "");
                    } else if (orders.getPayMethod() == 4) {//2 银行卡
                        if (orders.getBankCardType() == null) {
                            BankCardBin cardBin = this.bankCardBinMapper.selectByCardNo(orders.getBankCard());
                            if (cardBin != null) {
                                if (cardBin.getCardType() == 0) {
                                    orders.setBankCardType("借记卡");
                                } else if (cardBin.getCardType() == 1) {
                                    orders.setBankCardType("贷记卡");
                                } else {
                                    orders.setBankCardType("其他");
                                }
                            } else {
                                orders.setBankCardType("其他");
                            }
                        }
                        if (orders.getBankCardType().equals("借记卡")) {
                            orders.setChargeRate("" + charge.getDebit());

                            Double payDebit = orders.getFinalPrice() * charge.getDebit();
                            if (payDebit > charge.getDebitMax()) {
                                orders.setChargeAmount(charge.getDebitMax() + "");
                            } else {
                                orders.setChargeAmount(payDebit + "");
                            }

                        } else if (orders.getBankCardType().equals("贷记卡")) {
                            orders.setChargeRate("" + charge.getCredit());

                            Double payCredit = orders.getFinalPrice() * charge.getCredit();

                            orders.setChargeAmount(payCredit + "");


                        }
                        if (orders.getBankCardType() == null) {
                            orders.setChargeRate("" + charge.getCredit());
                            orders.setChargeAmount(orders.getFinalPrice() * charge.getCredit() + "");

                        }

                    } else if (orders.getPayMethod() == 0) {
                        orders.setChargeRate("" + 0);
                        orders.setChargeAmount("" + 0);
                    }
                } else {
                    orders.setChargeRate("" + 0);
                    orders.setChargeAmount("" + 0);
                }


            ordersMapper.updateByPrimaryKeySelective(orders);
        }


        return null;
    }

    @Override
    public String makeStoreSalesDailyReport(String[] merIds, String saleDateText) {

        if (merIds == null || merIds.length == 0) {
            logger.info("没有要生成门店销售报表的商户...");
            return "";
        }

        logger.info("saleDateText = " + saleDateText);

        Date day = new Date();
        try {
            day = DateUtils.parseDate(saleDateText, new String[]{"yyyy-MM-dd"});
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String createDateText = saleDateText;

        for (String merId : merIds) {
            logger.info("商户ID ：" + merId);

            // 查询所有门店信息
            ArrayList<MerchantsGroups> allStores = this.merGroupsMapper.selectAllStores(merId);
            if (allStores != null && !allStores.isEmpty()) {
                for (MerchantsGroups store : allStores) {
                    logger.info("生成门店销售报表 " + store.getGroupName());

                    StoreSalesReports param = new StoreSalesReports();
                    param.setCreateDate(day);
                    param.setCreateDateText(createDateText);
                    param.setMerId(merId);
                    param.setMerGroupId(store.getMerGroupId());

                    // 先删除重复数据
                    this.storeSalesReportMapper.deleteByDay(param);

                    // 初始化数据，生成 goodsId, name ,code, cost
                    this.storeSalesReportMapper.initReport(param);

                    // 更新商品门店零售价
                    this.storeSalesReportMapper.updateGoodsPrice(param);

                    // 刷新采购数量和金额
                    ArrayList<StoreSalesReports> storeSalesReportsList = this.storeSalesReportMapper.selectByDate(param);
                    if (storeSalesReportsList != null && !storeSalesReportsList.isEmpty()) {
                        for (StoreSalesReports item : storeSalesReportsList) {
                            GoodsStockDetail detailQuery = new GoodsStockDetail();
                            detailQuery.setOwnerId(store.getMerGroupId());
                            detailQuery.setGoodsId(item.getGoodsId());
                            detailQuery.setDateText(createDateText);

                            GoodsStockDetail stockDetail = this.goodsStockDetailMapper.selectBuyAmount(detailQuery);

                            OrderDetails orderDetailQuery = new OrderDetails();
                            orderDetailQuery.setPayDateText(createDateText);
                            orderDetailQuery.setMerGroupId(store.getMerGroupId());
                            orderDetailQuery.setGoodsId(item.getGoodsId());
                            OrderDetails orderDetail = this.orderDetailsMapper.selectStoreGoodsSales(orderDetailQuery);

                            if (stockDetail != null) {
                                // 采购入库后的数量
                                item.setStockBuyCount(stockDetail.getStockCount0());
                                // 采购入库后的金额
                                item.setStockBuyAmount(stockDetail.getGoodsCost().intValue());
                            }

                            if (orderDetail != null) {
                                item.setGoodsSaleCount(orderDetail.getGoodsAmount());
                                item.setGoodsSaleAmount(orderDetail.getGoodsTotalPrice());

                                // 计算毛利
                                if (orderDetail.getGoodsAmount() != null && item.getGoodsSalePrice() != null) {
                                    int goodsProfit = (int) (orderDetail.getGoodsTotalPrice() - orderDetail.getGoodsAmount() * item.getGoodsCost());
                                    item.setGoodsProfit(goodsProfit);
                                }
                            }

                            // 更新
                            this.storeSalesReportMapper.updateByPrimaryKeySelective(item);
                        }
                    }
                }

            }

            // 统计门店库存调拨
            StockDispatch dispathQuery = new StockDispatch();
            dispathQuery.setMerId(merId);
            dispathQuery.setDateText(createDateText);
            ArrayList<StockDispatch> finishedStockDispatches = this.stockDispatchMapper.selectFinishedByDay(dispathQuery);
            if (finishedStockDispatches != null && !finishedStockDispatches.isEmpty()) {
                for (StockDispatch dispatch : finishedStockDispatches) {
                    dispatch.setDateText(createDateText);
                    this.storeSalesReportMapper.updateGoodsDispathIn(dispatch);
                    this.storeSalesReportMapper.updateGoodsDispathOut(dispatch);
                }
            }
        }

        return null;
    }

    @Override
    public ResponseEntity<byte[]> downloadStoreSalesReport(HttpServletRequest request, HttpSession session,
                                                           StoreSalesReports param) {

        logger.info("导出门店销售报表...");

        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return null;
        }

        if (StringUtils.isEmpty(param.getCreateDateText()) || StringUtils.isEmpty(param.getMerGroupId())) {
            logger.info("输入查询参数为空");
            return null;
        }

        MerchantsGroups store = this.merGroupsMapper.selectByPrimaryKey(param.getMerGroupId());

        // 先打开模板文件
        String storeSalesReportTemplate = FuHuiTongContext.getInstance().getExcelTempDir() + File.separator + "store_sales_report_template.xls";
        logger.info(storeSalesReportTemplate);

        int totalCount = 0;
        ArrayList<StoreSalesReports> storeSalesReportsList = this.storeSalesReportMapper.selectByDate(param);
        if (storeSalesReportsList != null && !storeSalesReportsList.isEmpty()) {
            totalCount = storeSalesReportsList.size();
        }

        FileInputStream fis;
        try {
            fis = new FileInputStream(new File(storeSalesReportTemplate));
            HSSFWorkbook wb = new HSSFWorkbook(fis);
            HSSFSheet sheet = wb.getSheetAt(0);

            // 导出商品库存
            if (totalCount > 0) {
                for (int i = 0; i < storeSalesReportsList.size(); i++) {
                    StoreSalesReports reportItem = storeSalesReportsList.get(i);

                    // 从第三行开始
                    HSSFRow row = sheet.createRow(i + 2);

                    // 商品名称
                    HSSFCell cell0 = row.createCell(0);
                    cell0.setCellValue(reportItem.getGoodsName());

                    // 商品条码
                    if (reportItem.getGoodsCode() != null) {
                        HSSFCell cell1 = row.createCell(1);
                        cell1.setCellValue(reportItem.getGoodsCode());
                    }

                    // 进价(元)
                    if (reportItem.getGoodsCostText() != null) {
                        HSSFCell cell2 = row.createCell(2);
                        cell2.setCellValue(reportItem.getGoodsCostText());
                    }

                    // 零售价(元)
                    if (reportItem.getGoodsSalePriceText() != null) {
                        HSSFCell cell3 = row.createCell(3);
                        cell3.setCellValue(reportItem.getGoodsSalePriceText());
                    }

                    // 进货数量
                    if (reportItem.getStockBuyCount() != null) {
                        HSSFCell cell4 = row.createCell(4);
                        cell4.setCellValue(reportItem.getStockBuyCount());
                    }

                    // 金额(元)
                    if (reportItem.getStockBuyAmountText() != null) {
                        HSSFCell cell5 = row.createCell(5);
                        cell5.setCellValue(reportItem.getStockBuyAmountText());
                    }

                    // 销量
                    if (reportItem.getGoodsSaleCount() != null) {
                        HSSFCell cell6 = row.createCell(6);
                        cell6.setCellValue(reportItem.getGoodsSaleCount());
                    }

                    // 销售额(元)
                    if (reportItem.getGoodsSaleAmountText() != null) {
                        HSSFCell cell7 = row.createCell(7);
                        cell7.setCellValue(reportItem.getGoodsSaleAmountText());
                    }

                    // 毛利润(元)
                    if (reportItem.getGoodsProfitText() != null) {
                        HSSFCell cell8 = row.createCell(8);
                        cell8.setCellValue(reportItem.getGoodsProfitText());
                    }

                    // 调入数量
                    if (reportItem.getDispatchInCount() != null) {
                        HSSFCell cell9 = row.createCell(9);
                        cell9.setCellValue(reportItem.getDispatchInCount());
                    }

                    // 金额(元)
                    if (reportItem.getDispatchInAmountText() != null) {
                        HSSFCell cell10 = row.createCell(10);
                        cell10.setCellValue(reportItem.getDispatchInAmountText());
                    }

                    // 调出数量
                    if (reportItem.getDispatchOutCount() != null) {
                        HSSFCell cell11 = row.createCell(11);
                        cell11.setCellValue(reportItem.getDispatchOutCount());
                    }

                    // 金额(元)
                    if (reportItem.getDispatchOutAmountText() != null) {
                        HSSFCell cell12 = row.createCell(12);
                        cell12.setCellValue(reportItem.getDispatchOutAmountText());
                    }

                    // 退货数量
                    if (reportItem.getReturnCount() != null) {
                        HSSFCell cell13 = row.createCell(13);
                        cell13.setCellValue(reportItem.getReturnCount());
                    }

                    // 金额(元)
                    if (reportItem.getReturnAmountText() != null) {
                        HSSFCell cell14 = row.createCell(14);
                        cell14.setCellValue(reportItem.getReturnAmountText());
                    }

                    // 结余库存
                    if (reportItem.getGoodsStockCount() != null) {
                        HSSFCell cell15 = row.createCell(15);
                        cell15.setCellValue(reportItem.getGoodsStockCount());
                    }
                }
            }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            wb.write(bos);
            wb.close();

            // 生成下载文件流
            String fileName = "门店销售报表.xls";

            String extName = FilenameUtils.getExtension(fileName);
            String baseName = FilenameUtils.getBaseName(fileName);

            fileName = baseName + "_" + store.getGroupName() + "_" + param.getCreateDateText() + "." + extName;

            logger.info("file name is " + fileName);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", fileName, Charset.defaultCharset());
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            ResponseEntity<byte[]> downloadData = new ResponseEntity<byte[]>(bos.toByteArray(), headers, HttpStatus.CREATED);

            IOUtils.closeQuietly(fis);
            IOUtils.closeQuietly(bos);

            return downloadData;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ResponseEntity<byte[]> exportAgentsAllOrders(HttpServletRequest request, HttpSession session, Orders query) {

        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return null;
        }

        if (StringUtils.isEmpty(query.getCreateDateText())) {
            query.setCreateDateText(null);
        }

        if (StringUtils.isEmpty(query.getEndDateText())) {
            query.setEndDateText(null);
        }

        if (StringUtils.isEmpty(query.getOrderCode())) {
            query.setOrderCode(null);
        }


        int totalCount = this.ordersMapper.selectCountByDate2(query);
        query.setPage(1);
        query.setLimit(totalCount);

        if (totalCount > 0) {
            ArrayList<Orders> orders = this.ordersMapper.selectByDate2(query);

            return getExcelData(orders);
        }

        return null;
    }

    private ResponseEntity<byte[]> getExcelData(ArrayList<Orders> orders) {
        // 先打开模板文件
        String ordersTemplate = FuHuiTongContext.getInstance().getExcelTempDir() + File.separator + "orders_template.xls";
        logger.info(ordersTemplate);

        if (orders != null && !orders.isEmpty()) {
            FileInputStream fis;
            try {
                fis = new FileInputStream(new File(ordersTemplate));
                HSSFWorkbook wb = new HSSFWorkbook(fis);
                HSSFSheet sheet = wb.getSheetAt(0);

                // 0商户名称	1门店名称	2收银员	3订单编号	4综合流水号	5订单日期	6支付时间	7交易金额	8交易手续费	9结算金额	10支付方式	11银行卡类型	12终端编号 13终端sn
                for (int i = 0; i < orders.size(); i++) {
                    Orders order = orders.get(i);
                    HSSFRow row = sheet.createRow(i + 1);

                    HSSFCell cell0 = row.createCell(0);
                    cell0.setCellValue(order.getMerName());

                    HSSFCell cell1 = row.createCell(1);
                    cell1.setCellValue(order.getGroupName());

                    HSSFCell cell2 = row.createCell(2);
                    cell2.setCellValue(order.getOperatorName());

                    HSSFCell cell3 = row.createCell(3);
                    cell3.setCellValue(order.getOrderCode());

                    HSSFCell cell4 = row.createCell(4);
                    cell4.setCellValue(order.getPayBillNumber());

                    HSSFCell cell5 = row.createCell(5);
                    cell5.setCellValue(order.getCreateDateText());

                    HSSFCell cell6 = row.createCell(6);
                    cell6.setCellValue(order.getPayDateText());

//					HSSFCell cell6 = row.createCell(6);
//					cell6.setCellValue(order.getTotalPriceText());

                    HSSFCell cell7 = row.createCell(7);
                    cell7.setCellValue(order.getFinalPriceText());
                    String bankcardtype = "借记卡";
                    if (order.getBankName() != null) {
                        String bankcardname = order.getBankName();

                        if (bankcardname.indexOf("贷记卡") > 0) {
                            bankcardtype = "贷记卡";
                        } else if (bankcardname.indexOf("借记卡") > 0) {
                            bankcardtype = "借记卡";
                        } else {


                            String[] cardtype = bankcardname.split("&");
                            if (cardtype.length > 1) {
                                if (cardtype[1].equals("02")) {
                                    bankcardtype = "借记卡";
                                } else if ("03".equals(cardtype[1])) {
                                    bankcardtype = "贷记卡";
                                }
                            }
                        }
                    }

                    String payMethodStr = "现金支付";
                    double rate = 0;
                    if (order.getPayMethod() == 1) {
                        payMethodStr = "微信支付";
                        rate = 0.0038;
                    } else if (order.getPayMethod() == 2) {
                        payMethodStr = "支付宝";
                        rate = 0.0038;
                    } else if (order.getPayMethod() == 3) {
                        payMethodStr = "电信翼支付";
                        rate = 0.0038;
                    } else if (order.getPayMethod() == 4) {
                        payMethodStr = "银行卡";
                        if ("借记卡".equals(bankcardtype)) {
                            rate = 0.0055;
                        } else {
                            rate = 0.0058;
                        }
                    }
                    double price = Double.parseDouble(order.getFinalPriceText());
                    double finalPrice = price * rate;
                    if ("借记卡".equals(bankcardtype)) {
                        if (finalPrice > 20) {
                            finalPrice = 20;
                        }
                    }

                   double amount =  Double.parseDouble(order.getChargeAmount())*0.01;

                    HSSFCell cell8 = row.createCell(8);
                    cell8.setCellValue(amount);
                    //结算金额
                    HSSFCell cell9 = row.createCell(9);
                    if (order.getChargeAmount() == null) {
                        cell9.setCellValue("");
                    } else {
                        cell9.setCellValue(price - amount);
                    }

                    HSSFCell cell10 = row.createCell(10);

                    cell10.setCellValue(payMethodStr);


                    HSSFCell cell11 = row.createCell(11);

                    cell11.setCellValue(bankcardtype);

                    HSSFCell cell12 = row.createCell(12);

                    cell12.setCellValue(order.getTermCode());

                    HSSFCell cell13 = row.createCell(13);

                    cell13.setCellValue(order.getTermSn());


                }

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                wb.write(bos);
                wb.close();

                // 生成下载文件流
                String fileName = "交易数据导出.xls";

                String extName = FilenameUtils.getExtension(fileName);
                String baseName = FilenameUtils.getBaseName(fileName);
                fileName = baseName + "_" + DateFormatUtils.format(new Date(), "yyyy-MM-dd") + "." + extName;

                logger.info("file name is " + fileName);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentDispositionFormData("attachment", fileName, Charset.defaultCharset());
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

                ResponseEntity<byte[]> downloadData = new ResponseEntity<byte[]>(bos.toByteArray(), headers, HttpStatus.CREATED);

                IOUtils.closeQuietly(fis);
                IOUtils.closeQuietly(bos);

                return downloadData;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public ResponseEntity<byte[]> exportAllOrders(HttpServletRequest request, HttpSession session, Orders query) {
        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return null;
        }

        if (StringUtils.isEmpty(query.getCreateDateText())) {
            query.setCreateDateText(null);
        }

        if (StringUtils.isEmpty(query.getEndDateText())) {
            query.setEndDateText(null);
        }

        if (StringUtils.isEmpty(query.getOrderCode())) {
            query.setOrderCode(null);
        }
        if (StringUtils.isEmpty(query.getTermCode())) {
            query.setTermCode(null);
        }
        if (StringUtils.isEmpty(query.getTermSn())) {
            query.setTermSn(null);
        }
        query.setMerId(this.merUser.getMerId());
        int totalCount = this.ordersMapper.selectCountByDate2(query);
        query.setPage(1);
        query.setLimit(totalCount);

        if (totalCount > 0) {
            ArrayList<Orders> orders = this.ordersMapper.selectByDate2EXCEL(query);

            return getExcelData(orders);
        }

        return null;
    }


}
