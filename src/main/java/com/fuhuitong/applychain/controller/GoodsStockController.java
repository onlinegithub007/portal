package com.fuhuitong.applychain.controller;

import com.fuhuitong.applychain.model.*;
import com.fuhuitong.applychain.service.IGoodsStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class GoodsStockController {
	
	@Resource
	private IGoodsStockService goodsStockService;
	
	@RequestMapping("/backstage/merchant_goods_stock.html")
	public String merchantGoodsStock(HttpServletRequest request, HttpSession session)
	{
		return goodsStockService.merchantGoodsStock(request, session);
	}
	
	@RequestMapping("/backstage/merchant_goods_stock_data.html")
	public @ResponseBody String merchantGoodsStockData(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo)
	{
		return goodsStockService.merchantGoodsStockData(request, session, goodsInfo);
	}
	
	@RequestMapping("/backstage/download_merchant_stock.html")
	public ResponseEntity<byte[]> downloadMerchantStockTemplate(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo)
	{
		return goodsStockService.downloadMerchantStockTemplate(request, session, goodsInfo);
	}
	
	@RequestMapping("/backstage/import_merchant_goods_stock.html")
	public @ResponseBody String importMerchantStock(@RequestParam("fileName") MultipartFile uploadFile, HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo)
	{
		return goodsStockService.importMerchantStock(uploadFile, request, session, goodsInfo);
	}
	
	/**
	 * 订货单管理，门店店长从这里进入
	 * 总部给门店派单，也是从这里进入
	 * @param request
	 * @param session
	 * @param stockOrderStatus
	 * @param storeId
	 * @return
	 */
	@RequestMapping("/backstage/store_stock_orders.html")
	public String storeStockOrders(HttpServletRequest request, HttpSession session, Integer stockOrderStatus, String storeId)
	{
		return goodsStockService.storeStockOrders(request, session, stockOrderStatus, storeId);
	}
	
	/**
	 * 获得门店当前所有的订单信息
	 * @param request
	 * @param session
	 * @param order
	 * @return
	 */
	@RequestMapping("/backstage/store_stock_orders_data.html")
	public @ResponseBody String storeStockOrdersData(HttpServletRequest request, HttpSession session, StockOrders order)
	{
		return goodsStockService.storeStockOrdersData(request, session, order);
	}
	
	/**
	 * 创建新的进货单
	 * @param request
	 * @param session
	 * @param order
	 * @return
	 */
	@RequestMapping("/backstage/store_stock_order_info.html")
	public String storeStockOrderInfo(HttpServletRequest request, HttpSession session, StockOrders order)
	{
		return goodsStockService.storeStockOrderInfo(request, session, order);
	}
	
	@RequestMapping("/backstage/save_store_stock_orders.html")
	public @ResponseBody String saveStoreStockOrders(HttpServletRequest request, HttpSession session, StockOrders order)
	{
		return goodsStockService.saveStoreStockOrders(request, session, order);
	}
	
	@RequestMapping("/backstage/delete_store_stock_orders.html")
	public @ResponseBody String deleteStoreStockOrders(HttpServletRequest request, HttpSession session, StockOrders order)
	{
		return goodsStockService.deleteStoreStockOrders(request, session, order);
	}
	
	@RequestMapping("/backstage/store_stock_order_goods.html")
	public String storeStockOrderGoods(HttpServletRequest request, HttpSession session, StockOrders order)
	{
		return goodsStockService.storeStockOrderGoods(request, session, order);
	}
	
	@RequestMapping("/backstage/store_stock_order_goods_data.html")
	public @ResponseBody String storeStockOrderGoodsData(HttpServletRequest request, HttpSession session, StockOrders order)
	{
		return goodsStockService.storeStockOrderGoodsData(request, session, order);
	}
	
	@RequestMapping("/backstage/download_store_goods.html")
	public ResponseEntity<byte[]> downloadStoreGoods(HttpServletRequest request, HttpSession session)
	{
		return goodsStockService.downloadStoreGoods(request, session);
	}
	
	@RequestMapping("/backstage/import_store_stock_order_goods.html")
	public @ResponseBody String importStoreStockOrderGoods(@RequestParam("fileName") MultipartFile uploadFile, HttpServletRequest request, HttpSession session)
	{
		return goodsStockService.importStoreStockOrderGoods(uploadFile, request, session);
	}
	
	/**
	 * 采购单审核处理
	 * @param request
	 * @param session
	 * @param order
	 * @return
	 */
	@RequestMapping("/backstage/store_stock_orders_audit.html")
	public String storeStockOrdersAudit(HttpServletRequest request, HttpSession session, StockOrders order)
	{
		return goodsStockService.storeStockOrdersAudit(request, session, order);
	}
	
	/**
	 * 提交进货单给总部审核
	 * @param request
	 * @param session
	 * @param order
	 * @return
	 */
	@RequestMapping("/backstage/store_stock_orders_submit.html")
	public @ResponseBody String storeStockOrdersSubmit(HttpServletRequest request, HttpSession session, StockOrders order, String stockOrderDetailIdStr, String goodsCountStr)
	{
		return goodsStockService.storeStockOrdersSubmit(request, session, order, stockOrderDetailIdStr, goodsCountStr);
	}
	
	/**
	 * 为运营审核进货单查询商品信息，这里面包含了供应以及价格等信息
	 * @param request
	 * @param session
	 * @param order
	 * @return
	 */
	@RequestMapping("/backstage/store_stock_order_goods_data_audit.html")
	public @ResponseBody String storeStockOrderGoodsDataAudit(HttpServletRequest request, HttpSession session, StockOrders order)
	{
		return goodsStockService.storeStockOrderGoodsDataAudit(request, session, order);
	}
	
	/**
	 * 检索商品的供应商，返回列表形式
	 * @param request
	 * @param session
	 * @param order
	 * @return
	 */
	@RequestMapping("/backstage/select_goods_provider.html")
	public @ResponseBody String selectGoodsProvider(HttpServletRequest request, HttpSession session, String goodsId)
	{
		return goodsStockService.selectGoodsProvider(request, session, goodsId);
	}
	
	/**
	 * 查询所有门店某种商品的库存
	 * @param request
	 * @param session
	 * @param goodsId
	 * @return
	 */
	@RequestMapping("/backstage/select_allstore_goods_stock.html")
	public @ResponseBody String selectAllStoreGoodsStock(HttpServletRequest request, HttpSession session, String goodsId)
	{
		return goodsStockService.selectAllStoreGoodsStock(request, session, goodsId);
	}
	
	/**
	 * 临时设置商品的供应商和成本价信息
	 * @param request
	 * @param session
	 * @param goodsId
	 * @return
	 */
	@RequestMapping("/backstage/set_goods_provider_info.html")
	public @ResponseBody String setGoodsProviderInfo(HttpServletRequest request, HttpSession session, StockOrdersDetail detail)
	{
		return goodsStockService.setGoodsProviderInfo(request, session, detail);
	}
	
	/**
	 * 修改采购单的商品数量
	 * @param request
	 * @param session
	 * @param detail
	 * @return
	 */
	@RequestMapping("/backstage/set_goods_count_info_instock_order.html")
	public @ResponseBody String setGoodsCountInfoInstockOrder(HttpServletRequest request, HttpSession session, StockOrdersDetail detail)
	{
		return goodsStockService.setGoodsCountInfoInstockOrder(request, session, detail);
	}
	
	/**
	 * 设置进货单处理方式，分为 调拨和采购两种
	 * @param request
	 * @param session
	 * @param detail
	 * @return
	 */
	@RequestMapping("/backstage/set_stock_order_dealtype.html")
	public @ResponseBody String setStockOrderDealType(HttpServletRequest request, HttpSession session, StockOrdersDetail detail)
	{
		return goodsStockService.setStockOrderDealType(request, session, detail);
	}
	
	/**
	 * 保存调拨的门店
	 * @param request
	 * @param session
	 * @param detail
	 * @return
	 */
	@RequestMapping("/backstage/set_stock_order_whichstore.html")
	public @ResponseBody String setStockOrderWhichStore(HttpServletRequest request, HttpSession session, StockOrdersDetail detail)
	{
		return goodsStockService.setStockOrderWhichStore(request, session, detail);
	}
	
	/**
	 * 保存进货单1审信息，进入到待2审状态
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backstage/saveStockOrderAudit1.html")
	public @ResponseBody String saveStockOrderAudit1(HttpServletRequest request, HttpSession session, boolean pass, String desc)
	{
		return goodsStockService.saveStockOrderAudit1(request, session, pass, desc);
	}
	
	/**
	 * 完成进货单的终审
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backstage/saveStockOrderAudit2.html")
	public @ResponseBody String saveStockOrderAudit2(HttpServletRequest request, HttpSession session, boolean pass, String desc)
	{
		return goodsStockService.saveStockOrderAudit2(request, session, pass, desc);
	}
	
	// 得到进货单中商品供应商的信息
	@RequestMapping("/backstage/store_stock_order_list_providers.html")
	public @ResponseBody String listStockOrderProviders(HttpServletRequest request, HttpSession session, PurchaseOrders queryParam)
	{
		return goodsStockService.listStockOrderProviders(request, session, queryParam);
	}
	
	@RequestMapping("/backstage/store_purchase_order_view.html")
	public String storePurchaseOrderView(HttpServletRequest request, HttpSession session, String purchaseOrderId)
	{
		return goodsStockService.storePurchaseOrderView(request, session, purchaseOrderId);
	}
	
	@RequestMapping("/backstage/store_purchase_order_view_goods.html")
	public @ResponseBody String storePurchaseOrderViewGoods(HttpServletRequest request, HttpSession session, String purchaseOrderId)
	{
		return goodsStockService.storePurchaseOrderViewGoods(request, session, purchaseOrderId);
	}
	
	/**
	 * 正式确定下大采购单
	 * @param request
	 * @param session
	 * @param purchaseOrderId
	 * @return
	 */
	@RequestMapping("/backstage/saveStockOrderAudit3.html")
	public @ResponseBody String saveStockOrderAudit3(HttpServletRequest request, HttpSession session)
	{
		return goodsStockService.saveStockOrderAudit3(request, session);
	}
	
	/**
	 * 查看进货单明细
	 * @param request
	 * @param session
	 * @param stockOrderId
	 * @param admin		管理模式
	 * @return
	 */
	@RequestMapping("/backstage/store_stock_orders_view.html")
	public String storeStockOrdersView(HttpServletRequest request, HttpSession session, String stockOrderId, String adminMode)
	{
		return goodsStockService.storeStockOrdersView(request, session, stockOrderId, adminMode);
	}
	
	/**
	 * 门店收货确认主界面
	 * @param request
	 * @param session
	 * @param stockOrderId
	 * @return
	 */
	@RequestMapping("/backstage/store_stock_orders_ack.html")
	public String storeStockOrdersAck(HttpServletRequest request, HttpSession session, String stockOrderId)
	{
		return goodsStockService.storeStockOrdersAck(request, session, stockOrderId);
	}
	
	/**
	 * 进入商品明细验收的界面
	 * @param request
	 * @param session
	 * @param purchaseOrderId
	 * @return
	 */
	@RequestMapping("/backstage/store_stock_orders_ack_goods.html")
	public String storeStockOrdersAckGoods(HttpServletRequest request, HttpSession session, String purchaseOrderId)
	{
		return goodsStockService.storeStockOrdersAckGoods(request, session, purchaseOrderId);
	}
	
	/**
	 * 商品最终验收，进入库存
	 * @param request
	 * @param session
	 * @param orderDetail
	 * @return
	 */
	@RequestMapping("/backstage/store_stock_orders_ack_goods2.html")
	public @ResponseBody String storeStockOrdersAckGoods2(HttpServletRequest request, HttpSession session, PurchaseOrdersDetail orderDetail)
	{
		return goodsStockService.storeStockOrdersAckGoods2(request, session, orderDetail);
	}
	
	@RequestMapping("/backstage/store_stock_orders_ackall_goods.html")
	public @ResponseBody String storeStockOrdersAckAllGoods2(HttpServletRequest request, HttpSession session, String purchaseDetailIdStr, String goodsAckAmountStr)
	{
		return goodsStockService.storeStockOrdersAckAllGoods2(request, session, purchaseDetailIdStr, goodsAckAmountStr);
	}
	
	/**
	 * 当前进货单全部验收完毕
	 * @param request
	 * @param session
	 * @param orderDetail
	 * @return
	 */
	@RequestMapping("/backstage/store_stock_orders_ack_success.html")
	public @ResponseBody String storeStockOrdersAckSuccess(HttpServletRequest request, HttpSession session)
	{
		return goodsStockService.storeStockOrdersAckSuccess(request, session);
	}
	
	/**
	 * 查看门店的库存
	 * @param request
	 * @param session
	 * @param purchaseOrderId
	 * @return
	 */
	@RequestMapping("/backstage/store_goods_stock.html")
	public String storeGoodsStock(HttpServletRequest request, HttpSession session, String merGroupId)
	{
		return goodsStockService.storeGoodsStock(request, session, merGroupId);
	}
	
	@RequestMapping("/backstage/store_goods_stock_change.html")
	public @ResponseBody String storeGoodsStockChange(HttpServletRequest request, HttpSession session, GoodsStock goodsStock)
	{
		return goodsStockService.storeGoodsStockChange(request, session, goodsStock);
	}
	
	/**
	 * 查看门店的临期库存
	 * @param request
	 * @param session
	 * @param purchaseOrderId
	 * @return
	 */
	@RequestMapping("/backstage/store_goods_expired_stock.html")
	public String storeGoodsExpiredStock(HttpServletRequest request, HttpSession session, String merGroupId)
	{
		return goodsStockService.storeGoodsExpiredStock(request, session, merGroupId);
	}
	
	/**
	 * 查看门店的临期库存
	 * @param request
	 * @param session
	 * @param purchaseOrderId
	 * @return
	 */
	@RequestMapping("/backstage/store_goods_expired_stock_data.html")
	public @ResponseBody String storeGoodsExpiredStockData(HttpServletRequest request, HttpSession session, Integer expiredType)
	{
		return goodsStockService.storeGoodsExpiredStockData(request, session, expiredType);
	}
	
	/**
	 * 查看门店的库存数据
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backstage/store_goods_stock_data.html")
	public @ResponseBody String storeGoodsStockData(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo)
	{
		return goodsStockService.storeGoodsStockData(request, session, goodsInfo);
	}
	
	/**
	 * 查看门店的库存数据
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backstage/store_goods_stock_check_data.html")
	public @ResponseBody String storeGoodsStockCheckData(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo)
	{
		return goodsStockService.storeGoodsStockCheckData(request, session, goodsInfo);
	}
	
	/**
	 * 进入库存盘点界面
	 * @param request
	 * @param session
	 * @param stockCheck
	 * @return
	 */
	@RequestMapping("/backstage/store_goods_stock_check.html")
	public @ResponseBody String storeGoodsStockCheck(HttpServletRequest request, HttpSession session, GoodsStockCheck stockCheck)
	{
		return goodsStockService.storeGoodsStockCheck(request, session, stockCheck);
	}
	
	/**
	 * 显示库存明细页面
	 * @param request
	 * @param session
	 * @param goodsId
	 * @return
	 */
	@RequestMapping("/backstage/store_goods_stock_detail.html")
	public String storeGoodsStockDetail(HttpServletRequest request, HttpSession session, String goodsId)
	{
		return goodsStockService.storeGoodsStockDetail(request, session, goodsId);
	}
	
	/**
	 * 查看门店的库存明细数据
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backstage/store_goods_stock_data_detail.html")
	public @ResponseBody String storeGoodsStockDataDetail(HttpServletRequest request, HttpSession session)
	{
		return goodsStockService.storeGoodsStockDataDetail(request, session);
	}
	
	/**
	 * 门店调拨出库确认主界面
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backstage/store_goods_stock_out.html")
	public String storeGoodsStockOut(HttpServletRequest request, HttpSession session)
	{
		return goodsStockService.storeGoodsStockOut(request, session);
	}
	
	@RequestMapping("/backstage/store_goods_stock_out_data.html")
	public @ResponseBody String storeGoodsStockOutData(HttpServletRequest request, HttpSession session)
	{
		return goodsStockService.storeGoodsStockOutData(request, session);
	}
	
	@RequestMapping("/backstage/store_goods_stock_out_goods.html")
	public String storeGoodsStockOutGoods(HttpServletRequest request, HttpSession session, String purchaseOrderId)
	{
		return goodsStockService.storeGoodsStockOutGoods(request, session, purchaseOrderId);
	}
	
	@RequestMapping("/backstage/store_goods_stock_out_goods_data.html")
	public @ResponseBody String storeGoodsStockOutGoodsData(HttpServletRequest request, HttpSession session)
	{
		return goodsStockService.storeGoodsStockOutGoodsData(request, session);
	}
	
	@RequestMapping("/backstage/store_goods_stock_out_ack.html")
	public @ResponseBody String storeGoodsStockOutAck(HttpServletRequest request, HttpSession session)
	{
		return goodsStockService.storeGoodsStockOutAck(request, session);
	}
	
	/**
	 * 总部门店库存盘点
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backstage/master_store_goods_stock_select.html")
	public String masterStoreGoodsStockSelect(HttpServletRequest request, HttpSession session)
	{
		return goodsStockService.masterStoreGoodsStockSelect(request, session);
	}
	
	/**
	 * 进销存报表
	 * @param request
	 * @param session
	 * @param merGroupId
	 * @return
	 */
	@RequestMapping("/backstage/store_stock_orders_reports.html")
	public String storeStockOrdersReports(HttpServletRequest request, HttpSession session, String merGroupId)
	{
		return goodsStockService.storeStockOrdersReports(request, session);
	}
	
	@RequestMapping("/backstage/store_stock_orders_reports_data.html")
	public @ResponseBody String storeStockOrdersReportsData(HttpServletRequest request, HttpSession session, StockOrders stockOrder)
	{
		return goodsStockService.storeStockOrdersReportsData(request, session, stockOrder);
	}
	
	/**
	 * 创建采购单时，初始化商品，数量为1
	 * @param request
	 * @param session
	 * @param goodsList
	 * @return
	 */
	@RequestMapping("/backstage/store_stock_init_order_detail.html")
	public @ResponseBody String storeStockInitOrdersDetail(HttpServletRequest request, HttpSession session, String goodsList)
	{
		return goodsStockService.storeStockInitOrdersDetail(request, session, goodsList);
	}
	
	@RequestMapping("/backstage/store_stock_delete_order_detail.html")
	public @ResponseBody String storeStockDeleteOrdersDetail(HttpServletRequest request, HttpSession session, Integer stockOrderDetailId)
	{
		return goodsStockService.storeStockDeleteOrdersDetail(request, session, stockOrderDetailId);
	}
	
	/**
	 * 修改采购单中的某项商品数量
	 * @param request
	 * @param session
	 * @param detail
	 * @return
	 */
	@RequestMapping("/backstage/store_stock_update_order_detail.html")
	public @ResponseBody String storeStockUpdateOrdersDetail(HttpServletRequest request, HttpSession session, StockOrdersDetail detail)
	{
		return goodsStockService.storeStockUpdateOrdersDetail(request, session, detail);
	}
	
	/**
	 * 批量修改采购单中商品的数量
	 * @param request
	 * @param session
	 * @param detail
	 * @return
	 */
	@RequestMapping("/backstage/store_stock_batch_update_count.html")
	public @ResponseBody String storeStockBatchUpdateCount(HttpServletRequest request, HttpSession session, String stockOrderDetailIdStr, String goodsCountStr)
	{
		return goodsStockService.storeStockBatchUpdateCount(request, session, stockOrderDetailIdStr, goodsCountStr);
	}
	
	/**
	 * 复审时修改采购单中的某项商品数量
	 * @param request
	 * @param session
	 * @param detail
	 * @return
	 */
	@RequestMapping("/backstage/store_stock_update2_order_detail.html")
	public @ResponseBody String storeStockUpdate2OrdersDetail(HttpServletRequest request, HttpSession session, StockOrdersDetail detail)
	{
		return goodsStockService.storeStockUpdate2OrdersDetail(request, session, detail);
	}
	
	/**
	 * 供应商查看自己的采购单
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backstage/provider_my_orders.html")
	public String providerMyOrders(HttpServletRequest request, HttpSession session)
	{
		return goodsStockService.providerMyOrders(request, session);
	}
	
	@RequestMapping("/backstage/provider_my_orders_data.html")
	public @ResponseBody String providerMyOrdersData(HttpServletRequest request, HttpSession session, PurchaseOrders param)
	{
		return goodsStockService.providerMyOrdersData(request, session, param);
	}
	
	/**
	 * 供应商订单处理界面
	 * @param request
	 * @param session
	 * @param purchaseOrderId
	 * @return
	 */
	@RequestMapping("/backstage/provider_my_orders_goods.html")
	public String providerMyOrdersGoods(HttpServletRequest request, HttpSession session, String purchaseOrderId, String action)
	{
		return goodsStockService.providerMyOrdersGoods(request, session, purchaseOrderId, action);
	}
	
	/**
	 * 供应商获取采购单商品界面
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backstage/provider_my_orders_goods_data.html")
	public @ResponseBody String providerMyOrdersGoodsData(HttpServletRequest request, HttpSession session)
	{
		return goodsStockService.providerMyOrdersGoodsData(request, session);
	}
	
	/**
	 * 供应商对采购进行确认，修改采购数量
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backstage/provider_my_orders_ack_goods.html")
	public @ResponseBody String providerMyOrdersAckGoods(HttpServletRequest request, HttpSession session, PurchaseOrdersDetail purchaseDetail)
	{
		return goodsStockService.providerMyOrdersAckGoods(request, session, purchaseDetail);
	}
	
	/**
	 * 供应商确定供货数量
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backstage/provider_my_orders_ack.html")
	public @ResponseBody String providerMyOrdersAck(HttpServletRequest request, HttpSession session)
	{
		return goodsStockService.providerMyOrdersAck(request, session);
	}
	
	/**
	 * 对供应商自己确认的且做出修改的订单进行再次审核
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backstage/provider_purchase_orders_audit.html")
	public String providerOrdersAudit(HttpServletRequest request, HttpSession session)
	{
		return goodsStockService.providerOrdersAudit(request, session);
	}
	
	/**
	 * 显示所有供应商确认且修改了的订单信息
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backstage/provider_orders_audit_data.html")
	public @ResponseBody String providerOrdersAuditData(HttpServletRequest request, HttpSession session, PurchaseOrders purchaseOrder)
	{
		return goodsStockService.providerOrdersAuditData(request, session, purchaseOrder);
	}
	
	@RequestMapping("/backstage/provider_purchase_orders_goods_audit.html")
	public String providerOrdersGoodsAudit(HttpServletRequest request, HttpSession session, String purchaseOrderId, String action)
	{
		return goodsStockService.providerOrdersGoodsAudit(request, session, purchaseOrderId, action);
	}
	
	/**
	 * 对供应商修改的商品信息作出的确认，需要更新原始采购单
	 * @param request
	 * @param session
	 * @param purchaseOrder
	 * @return
	 */
	@RequestMapping("/backstage/provider_orders_audit_ack.html")
	public @ResponseBody String providerOrdersAuditAck(HttpServletRequest request, HttpSession session)
	{
		return goodsStockService.providerOrdersAuditAck(request, session);
	}
	
	/**
	 * 总部为门店派发进货单，选择门店界面
	 * @param request
	 * @param session
	 * @param purchaseOrderId
	 * @param action
	 * @return
	 */
	@RequestMapping("/backstage/master_stock_order_select.html")
	public String masterStockOrderSelect(HttpServletRequest request, HttpSession session)
	{
		return goodsStockService.masterStockOrderSelect(request, session);
	}
	
	/**
	 * 总部为多家门店批量下达采购单
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backstage/master_batch_stock_order_select.html")
	public String masterBatchStockOrderSelect(HttpServletRequest request, HttpSession session)
	{
		return goodsStockService.masterBatchStockOrderSelect(request, session);
	}
	
	/**
	 * 总部为多家门店批量下单，主界面
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backstage/master_batch_stock_ordes.html")
	public String masterBatchStockOrders(HttpServletRequest request, HttpSession session)
	{
		return goodsStockService.masterBatchStockOrders(request, session);
	}
	
	@RequestMapping("/backstage/master_batch_stock_ordes_data.html")
	public @ResponseBody String masterBatchStockOrdersData(HttpServletRequest request, HttpSession session, BatchStockOrders param)
	{
		return goodsStockService.masterBatchStockOrdersData(request, session, param);
	}
	
	@RequestMapping("/backstage/master_batch_stock_ordes_create.html")
	public @ResponseBody String masterBatchStockOrdersCreate(HttpServletRequest request, HttpSession session)
	{
		return goodsStockService.masterBatchStockOrdersCreate(request, session);
	}
	
	@RequestMapping("/backstage/master_batch_stock_ordes_delete.html")
	public @ResponseBody String masterBatchStockOrdersDelete(HttpServletRequest request, HttpSession session, String batchStockOrderId)
	{
		return goodsStockService.masterBatchStockOrdersDelete(request, session, batchStockOrderId);
	}
	
	/**
	 * 总部为多家门店下单采购单，进入商品选择页面
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backstage/master_batch_stock_order_goods.html")
	public String masterBatchStockOrderGoods(HttpServletRequest request, HttpSession session, String batchStockOrderId)
	{
		return goodsStockService.masterBatchStockOrderGoods(request, session, batchStockOrderId);
	}
	
	@RequestMapping("/backstage/master_batch_stock_order_goods_del.html")
	public @ResponseBody String masterBatchStockOrderGoodsDel(HttpServletRequest request, HttpSession session, BatchStockGoodsDetails batchStockGoodsDetails)
	{
		return goodsStockService.masterBatchStockOrderGoodsDel(request, session, batchStockGoodsDetails);
	}
	
	@RequestMapping("/backstage/master_batch_stock_order_goods_count.html")
	public @ResponseBody String masterBatchStockOrderGoodsCount(HttpServletRequest request, HttpSession session, String batchStockGoodsIdStr, String goodsCountStr)
	{
		return goodsStockService.masterBatchStockOrderGoodsCount(request, session, batchStockGoodsIdStr, goodsCountStr);
	}
	
	@RequestMapping("/backstage/master_batch_stock_order_goods_data.html")
	public @ResponseBody String masterBatchStockOrderGoodsData(HttpServletRequest request, HttpSession session)
	{
		return goodsStockService.masterBatchStockOrderGoodsData(request, session);
	}
	
	@RequestMapping("/backstage/master_batch_stock_init_goods.html")
	public @ResponseBody String masterBatchStockInitGoods(HttpServletRequest request, HttpSession session, String goodsList)
	{
		return goodsStockService.masterBatchStockInitGoods(request, session, goodsList);
	}
	
	@RequestMapping("/backstage/master_batch_stock_set_goods_provider.html")
	public @ResponseBody String masterBatchStockSetGoodsProvider(HttpServletRequest request, HttpSession session, BatchStockGoodsDetails param)
	{
		return goodsStockService.masterBatchStockSetGoodsProvider(request, session, param);
	}
	
	/**
	 * 总部为多家门店下单采购单，进入门店选择页面
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backstage/master_batch_stock_orders_stores.html")
	public String masterBatchStockOrderStores(HttpServletRequest request, HttpSession session, String batchStockOrderId)
	{
		return goodsStockService.masterBatchStockOrderStores(request, session, batchStockOrderId);
	}
	
	@RequestMapping("/backstage/master_batch_stock_orders_stores_data.html")
	public @ResponseBody String masterBatchStockOrderStoresData(HttpServletRequest request, HttpSession session, String batchStockOrderId)
	{
		return goodsStockService.masterBatchStockOrderStoresData(request, session, batchStockOrderId);
	}
	
	@RequestMapping("/backstage/master_batch_stock_orders_stores_save.html")
	public @ResponseBody String masterBatchStockOrderStoresSave(HttpServletRequest request, HttpSession session, String storeIds)
	{
		return goodsStockService.masterBatchStockOrderStoresSave(request, session, storeIds);
	}
	
	@RequestMapping("/backstage/master_batch_stock_orders_stores_remove.html")
	public @ResponseBody String masterBatchStockOrderStoresRemove(HttpServletRequest request, HttpSession session, BatchStockStoreDetails storeDetails)
	{
		return goodsStockService.masterBatchStockOrderStoresRemove(request, session, storeDetails);
	}
	
	@RequestMapping("/backstage/master_batch_stock_orders_submit.html")
	public @ResponseBody String masterBatchStockOrderSubmit(HttpServletRequest request, HttpSession session, String batchStockOrderId)
	{
		return goodsStockService.masterBatchStockOrderSubmit(request, session, batchStockOrderId);
	}
	
	/**
	 * 门店库存调拨的初次操作
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backstage/store_stock_dispatch0.html")
	public @ResponseBody String storeStockDispatch0(HttpServletRequest request, HttpSession session, StockDispatch dispatch)
	{
		return goodsStockService.storeStockDispatch0(request, session, dispatch);
	}
	
	/**
	 * 进入门店调拨页面
	 * @param request
	 * @param session
	 * @param dispatch
	 * @return
	 */
	@RequestMapping("/backstage/master_stock_dispatch0.html")
	public String masterStockDispatch0(HttpServletRequest request, HttpSession session)
	{
		return goodsStockService.masterStockDispatch0(request, session);
	}
	
	/**
	 * 获取当前管理员用户所有的临时调拨数据
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backstage/master_stock_dispatch0_data.html")
	public @ResponseBody String masterStockDispatch0Data(HttpServletRequest request, HttpSession session)
	{
		return goodsStockService.masterStockDispatch0Data(request, session);
	}
	
	@RequestMapping("/backstage/master_stock_dispatch0_action.html")
	public @ResponseBody String masterStockDispatch0Action(HttpServletRequest request, HttpSession session, String dispatchIds)
	{
		return goodsStockService.masterStockDispatch0Action(request, session, dispatchIds);
	}
	
	/**
	 * 取消某个商品的库存调拨
	 * @param request
	 * @param session
	 * @param stockDispatchId
	 * @return
	 */
	@RequestMapping("/backstage/master_stock_dispatch0_cancel.html")
	public @ResponseBody String masterStockDispatch0Cancel(HttpServletRequest request, HttpSession session, Integer stockDispatchId)
	{
		return goodsStockService.masterStockDispatch0Cancel(request, session, stockDispatchId);
	}
	
	/**
	 * 进入门店调拨页面
	 * @param request
	 * @param session
	 * @param dispatch
	 * @return
	 */
	@RequestMapping("/backstage/master_stock_dispatch1.html")
	public String masterStockDispatch1(HttpServletRequest request, HttpSession session)
	{
		return goodsStockService.masterStockDispatch1(request, session);
	}
	
	/**
	 * 查询所有的门店调拨的PurchaseOrders
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backstage/master_stock_dispatch1_data.html")
	public @ResponseBody String masterStockDispatch1Data(HttpServletRequest request, HttpSession session, PurchaseOrders purchaseOrders)
	{
		return goodsStockService.masterStockDispatch1Data(request, session, purchaseOrders);
	}
	
	/**
	 * 进入查看库存调拨明细界面
	 * @param request
	 * @param session
	 * @param purchaseOrderId
	 * @return
	 */
	@RequestMapping("/backstage/master_stock_dispatch2.html")
	public String masterStockDispatch2(HttpServletRequest request, HttpSession session, String purchaseOrderId)
	{
		return goodsStockService.masterStockDispatch2(request, session, purchaseOrderId);
	}
	
	@RequestMapping("/backstage/master_stock_dispatch_details.html")
	public String masterStockDispatchDetails(HttpServletRequest request, HttpSession session, String purchaseOrderId, String url)
	{
		return goodsStockService.masterStockDispatchDetails(request, session, purchaseOrderId, url);
	}
	
	/**
	 * 查看库存调拨明细数据
	 * @param request
	 * @param session
	 * @param purchaseOrderId
	 * @return
	 */
	@RequestMapping("/backstage/master_stock_dispatch2_data.html")
	public @ResponseBody String masterStockDispatch2Data(HttpServletRequest request, HttpSession session)
	{
		return goodsStockService.masterStockDispatch2Data(request, session);
	}
	
	/**
	 * 总部终审通过，进入调出门店出库确认状态
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backstage/master_stock_dispatch2_action.html")
	public @ResponseBody String masterStockDispatch2Action(HttpServletRequest request, HttpSession session)
	{
		return goodsStockService.masterStockDispatch2Action(request, session);
	}
	
	/**
	 * 进入门店调拨店长出库确认页面
	 * @param request
	 * @param session
	 * @param dispatch
	 * @return
	 */
	@RequestMapping("/backstage/master_stock_dispatch3.html")
	public String masterStockDispatch3(HttpServletRequest request, HttpSession session)
	{
		return goodsStockService.masterStockDispatch3(request, session);
	}
	
	/**
	 * 总部终审通过，门店店长已确认出库
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backstage/master_stock_dispatch3_action.html")
	public @ResponseBody String masterStockDispatch3Action(HttpServletRequest request, HttpSession session)
	{
		return goodsStockService.masterStockDispatch3Action(request, session);
	}
	
	/**
	 * 进入门店调拨店长入库确认页面
	 * @param request
	 * @param session
	 * @param dispatch
	 * @return
	 */
	@RequestMapping("/backstage/master_stock_dispatch4.html")
	public String masterStockDispatch4(HttpServletRequest request, HttpSession session)
	{
		return goodsStockService.masterStockDispatch4(request, session);
	}
	
	/**
	 * 门店入库确认
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backstage/master_stock_dispatch4_action.html")
	public @ResponseBody String masterStockDispatch4Action(HttpServletRequest request, HttpSession session)
	{
		return goodsStockService.masterStockDispatch4Action(request, session);
	}
	
	/**
	 * 采购单报表界面
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backstage/purchase_orders_reports.html")
	public String purchaseOrdersReports(HttpServletRequest request, HttpSession session)
	{
		return goodsStockService.purchaseOrdersReports(request, session);
	}
	
	@RequestMapping("/backstage/purchase_orders_reports_data.html")
	public @ResponseBody String purchaseOrdersReportsData(HttpServletRequest request, HttpSession session, PurchaseOrders param)
	{
		return goodsStockService.purchaseOrdersReportsData(request, session, param);
	}
	
	/**
	 * 用来刷新修复Purchase中确认收货的数量和金额
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backstage/purchase_orders_reports_data_update.html")
	public @ResponseBody String purchaseOrdersReportsDataUpdate(HttpServletRequest request, HttpSession session)
	{
		return goodsStockService.purchaseOrdersReportsDataUpdate(request, session);
	}
	
	
}
