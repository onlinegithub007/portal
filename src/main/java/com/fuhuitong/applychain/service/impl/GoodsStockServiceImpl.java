package com.fuhuitong.applychain.service.impl;

import com.fuhuitong.applychain.FuHuiTongContext;
import com.fuhuitong.applychain.dao.*;
import com.fuhuitong.applychain.model.*;
import com.fuhuitong.applychain.service.IGoodsStockService;
import com.fuhuitong.applychain.utils.ExcelCellUtils;
import com.fuhuitong.applychain.utils.MyUUID;
import org.apache.commons.beanutils.BeanUtils;
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
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;

@Service
public class GoodsStockServiceImpl extends BaseService implements IGoodsStockService {

	@Resource
	private GoodsTypeMapper goodsTypeMapper;
	
	@Resource
	private GoodsStockMapper goodsStockMapper;
	
	@Resource
	private GoodsInfoMapper goodsInfoMapper;
	
	@Resource
	private GoodsStockCheckMapper goodsStockCheckMapper;
	
	@Resource
	private StockOrdersMapper stockOrderMapper;
	
	@Resource
	private StockOrdersDetailMapper stockOrderDetailMapper;
	
	@Resource
	private GoodsPriceMapper goodsPriceMapper;
	
	@Resource
	private MerchantUsersMapper merUserMapper;
	
	@Resource
	private MerchantsGroupsMapper merGroupMapper;
	
	@Resource
	private AreasMapper areaMapper;
	
	@Resource
	private StockOrdersSNMapper stockOrderSnMapper;
	
	@Resource
	private GoodsProvidersMapper goodsProviderMapper;
	
	@Resource
	private PurchaseOrdersMapper purchaseOrderMapper;
	
	@Resource
	private PurchaseOrdersDetailMapper purchaseOrderDetailMapper;
	
	@Resource
	private PurchaseOrdersSnMapper purchaseOrderSnMapper;
	
	@Resource
	private GoodsStockBillsMapper goodsStockBillMapper;
	
	@Resource
	private GoodsStockDetailMapper goodsStockDetailMapper;
	
	@Resource
	private StockDispatchMapper stockDispatchMapper;
	
	@Resource
	private BatchStockOrdersMapper batchStockOrdersMapper;
	
	@Resource
	private BatchStockGoodsDetailsMapper batchStockGoodsDetailsMapper;
	
	@Resource
	private BatchStockStoreDetailsMapper batchStockStoresDetailsMapper;
	
	@Override
	public String merchantGoodsStock(HttpServletRequest request, HttpSession session) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		// 加载商品大类
		ArrayList<GoodsType> topGoodsType = this.goodsTypeMapper.selectTopTypeByMerId(this.merUser.getMerId());
		if (topGoodsType != null && !topGoodsType.isEmpty())
		{
			session.setAttribute("TopGoodsTypes", topGoodsType);
		}
		
		return "page/merchant_goods_stock";
	}

	@Override
	public String merchantGoodsStockData(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getTableData(null, null, 0);
		}
		
		logger.info("page : " + goodsInfo.getPage());
		logger.info("limit : " + goodsInfo.getLimit());
		
		goodsInfo = formatMerchantStockParam(goodsInfo);
		
		int totalCount =this.goodsInfoMapper.selectMerchantGoodsStockCount(goodsInfo);
		logger.info("totalCount = " + totalCount);
		
		if (totalCount > 0)
		{
			ArrayList<GoodsInfo> goodsList = this.goodsInfoMapper.selectMerchantGoodsStock(goodsInfo);
			return getTableData(goodsList, "", totalCount);
		}
		else
		{
			return getTableData(null, null, 0);
		}
	}

	private GoodsInfo formatMerchantStockParam(GoodsInfo goodsInfo)
	{
		if (StringUtils.isEmpty(goodsInfo.getGoodsTypeId()))
		{
			goodsInfo.setGoodsTypeId(null);
		}
		
		if (StringUtils.isEmpty(goodsInfo.getParentTypeId()))
		{
			goodsInfo.setParentTypeId(null);
		}
		
		String stockAmount = goodsInfo.getStockAmountParam();
		logger.info("stockAmount " + stockAmount);
		
		if (!StringUtils.isEmpty(stockAmount))
		{
			if (stockAmount.equals("0"))
			{
				goodsInfo.setMinStockAmount(0);
			}
			else if (!stockAmount.equals("-1"))
			{
				String[] args = stockAmount.split("-");
				
				if (args.length == 2)
				{
					goodsInfo.setMinStockAmount(Integer.parseInt(args[0]));
					
					if (args[1].equals("0"))
					{
						goodsInfo.setMaxStockAmount(9999999);
					}
					else
					{
						goodsInfo.setMaxStockAmount(Integer.parseInt(args[1]));
					}
				}
			}
		}
		
		/**
		 * <option value="-1">[库存状态不限]</option>
		<option value="0">0或负库存</option>
		<option value="0-100">100及以下</option>
		<option value="101-500">500及以下</option>
		<option value="501-1000">1000及以下</option>
		<option value="1001-2000">2000及以下</option>
		<option value="2001-5000">5000及以下</option>
		<option value="5000-0">5000以上</option>
		 */
		
		goodsInfo.setMerId(this.merUser.getMerId());
		goodsInfo.setMerGroupId(this.merUser.getMerGroupId());
		
		return goodsInfo;
	}
	
	@Override
	public ResponseEntity<byte[]> downloadMerchantStockTemplate(HttpServletRequest request, HttpSession session,
			GoodsInfo goodsInfo) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return null;
		}
		
		logger.info("page : " + goodsInfo.getPage());
		logger.info("limit : " + goodsInfo.getLimit());
		
		goodsInfo = formatMerchantStockParam(goodsInfo);
		
		int totalCount =this.goodsInfoMapper.selectMerchantGoodsStockCount(goodsInfo);
		logger.info("totalCount = " + totalCount);
		
		// 查询全部数据
		goodsInfo.setPage(1);
		goodsInfo.setLimit(totalCount);

		String typeName = "";
		if (!StringUtils.isEmpty(goodsInfo.getParentTypeId()))
		{
			GoodsType goodsType = this.goodsTypeMapper.selectByPrimaryKey(goodsInfo.getParentTypeId());
			typeName = goodsType.getGoodsTypeName() + "_";
		}
		
		// 先打开模板文件
		String merchantStockTemplate = FuHuiTongContext.getInstance().getExcelTempDir() + File.separator + "merchant_goods_stock_template.xls";
		logger.info(merchantStockTemplate);
		
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File(merchantStockTemplate));
			HSSFWorkbook wb = new HSSFWorkbook(fis);
			HSSFSheet sheet = wb.getSheetAt(0);
			
			// 导出商品库存
			if (totalCount > 0)
			{
				ArrayList<GoodsInfo> goodsList = this.goodsInfoMapper.selectMerchantGoodsStock(goodsInfo);
				
				for (int i = 0; i < goodsList.size(); i++)
				{
					GoodsInfo goods = goodsList.get(i);
						
					// 从第二行开始
					HSSFRow row = sheet.createRow(i + 2);
						
					// 商品分类
					HSSFCell cell0 = row.createCell(0);
					cell0.setCellValue(goods.getGoodsTypeName());
					
					// 商品编码
					if (goods.getGoodsId() != null)
					{
						HSSFCell cell1 = row.createCell(1);
						cell1.setCellValue(goods.getGoodsId());
					}
					
					// 库存编码
					if (goods.getGoodsStockId() != null)
					{
						HSSFCell cell2 = row.createCell(2);
						cell2.setCellValue(goods.getGoodsStockId());
					}
					
					// 商品条码
					if(goods.getGoodsCode() != null)
					{
						HSSFCell cell3 = row.createCell(3);
						cell3.setCellValue(goods.getGoodsCode());
					}
						
					// 商品名称
					if(goods.getGoodsName() != null)
					{
						HSSFCell cell4 = row.createCell(4);
						cell4.setCellValue(goods.getGoodsName());
					}
						
					// 商品库存
					if (goods.getStockAmount() != null)
					{
						HSSFCell cell5 = row.createCell(5);
						cell5.setCellValue(goods.getStockAmount());
					}
				}
			}
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			wb.write(bos);
			wb.close();
			
			// 生成下载文件流
			String fileName = this.sysDictMapper.selectByPrimaryKey(FuHuiTongContext.PARAM_MER_STOCK_TEMP).getDicValues();
			
			String extName = FilenameUtils.getExtension(fileName);
			String baseName = FilenameUtils.getBaseName(fileName);
			
			fileName = baseName + "_" + typeName + DateFormatUtils.format(new Date(), "yyyy-MM-dd") + "." + extName;
			
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
		
		// TODO Auto-generated method stub
		return null;
	}

	private GoodsInfo parseGoodsStock(HSSFRow row)
	{
		GoodsInfo goodsInfo = new GoodsInfo();
		if (row == null)
		{
			return goodsInfo;
		}
		
		if (row.getCell(0) != null)
		{
			goodsInfo.setGoodsName(ExcelCellUtils.readStringValue(row.getCell(0)));
		}
		
		if (row.getCell(1) != null)
		{
			goodsInfo.setGoodsId(ExcelCellUtils.readStringValue(row.getCell(1)));
		}
		
		if (row.getCell(2) != null)
		{
			
			goodsInfo.setGoodsStockId(ExcelCellUtils.readLongValue(row.getCell(2)));
		}
		
		if (row.getCell(3) != null)
		{
			goodsInfo.setGoodsCode(ExcelCellUtils.readStringValue(row.getCell(3)));
		}
		
		if (row.getCell(4) != null)
		{
			goodsInfo.setGoodsName(ExcelCellUtils.readStringValue(row.getCell(4)));
		}
		
		// 商品库存
		if (row.getCell(5) != null)
		{
			Long value = ExcelCellUtils.readLongValue(row.getCell(5));
			if (value != null)
			{
				goodsInfo.setStockAmount(value.intValue());
			}
		}
		
		return goodsInfo;
	}
	
	private GoodsInfo parseStockOrderDetail(HSSFRow row)
	{
		GoodsInfo goodsInfo = new GoodsInfo();
		if (row == null)
		{
			return goodsInfo;
		}
		
		if (row.getCell(0) != null)
		{
			goodsInfo.setGoodsTypeName(ExcelCellUtils.readStringValue(row.getCell(0)));
		}
		
		if (row.getCell(1) != null)
		{
			goodsInfo.setGoodsId(ExcelCellUtils.readStringValue(row.getCell(1)));
		}
		
		if (row.getCell(2) != null)
		{
			goodsInfo.setGoodsCode(ExcelCellUtils.readStringValue(row.getCell(2)));
		}
		
		if (row.getCell(3) != null)
		{
			goodsInfo.setGoodsName(ExcelCellUtils.readStringValue(row.getCell(3)));
		}
		
		if (row.getCell(4) != null)
		{
			goodsInfo.setProviderUnit(ExcelCellUtils.readStringValue(row.getCell(4)));
		}
		
		// 采购数量
		if (row.getCell(5) != null)
		{
			Long value = ExcelCellUtils.readLongValue(row.getCell(5));
			if (value != null)
			{
				goodsInfo.setGoodsCount(value.intValue());
			}
		}
		
		return goodsInfo;
	}
	
	@Override
	public String importMerchantStock(MultipartFile uploadFile, HttpServletRequest request, HttpSession session,
			GoodsInfo goodsInfo) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1);
		}
		
		HSSFWorkbook wb;
		HSSFSheet sheet;
		
		try {
			wb = new HSSFWorkbook(uploadFile.getInputStream());
			sheet = wb.getSheetAt(0);
			
			// 从第2行开始读取数据，先预先读取一行
			HSSFRow row = sheet.getRow(2);
			GoodsInfo preOne = parseGoodsStock(row);
			if (preOne.getGoodsId() == null || preOne.getGoodsCode() == null || preOne.getGoodsStockId() == null)
			{
				logger.info("Data format error.");
				IOUtils.closeQuietly(wb);
				
				return getRetCodeWithJson(1, getMessage("importGoodsInfo.formatError"));
			}
			
			ArrayList<GoodsInfo> goodsInfoList = new ArrayList<GoodsInfo>();
			goodsInfoList.add(preOne);
			
			int rowNum = 3;
			while(true)
			{
				row = sheet.getRow(rowNum);
				GoodsInfo goods = parseGoodsStock(row);
				
				if (goods == null || goods.getGoodsId() == null || goods.getGoodsCode() == null || goods.getGoodsStockId() == null)
				{
					// 数据结束
					break;
				}
				
				logger.info("rowNum is " + rowNum);
				logger.info("goodsId is " + goods.getGoodsId());
				logger.info("goodsCode is " + goods.getGoodsCode());
				
				goodsInfoList.add(goods);
				
				rowNum++;
			}
			
			IOUtils.closeQuietly(wb);
			
			logger.info("There are " + goodsInfoList.size() + " data records in the excel file to update.");
			
			for (GoodsInfo goods : goodsInfoList)
			{
				// 修改库存
				GoodsStock stock = new GoodsStock();
				stock.setGoodsStockId(goods.getGoodsStockId());
				stock.setGoodsId(goods.getGoodsId());
				stock.setOwnerId(this.merUser.getMerId());
				stock.setStockAmount(goods.getStockAmount());
				
				this.goodsStockMapper.updateMerchantStock(stock);
			}
			
			int updateCount = goodsInfoList.size();
			
			String retMsg = "总计导入" + updateCount + "库存数据，成功更新" + updateCount + "条";
			
			return getRetCodeWithJson(0, retMsg);
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		return getRetCodeWithJson(0);
	}

	@Override
	public String storeStockOrders(HttpServletRequest request, HttpSession session, Integer stockOrderStatus, String storeId) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		if (stockOrderStatus != null)
		{
			session.setAttribute("StockOrderStatus", stockOrderStatus);
			session.removeAttribute("AuditMode");
		}
		
		if (storeId != null)
		{
			// 此时运行在总部直接派发订单的模式
			logger.info("此时运行在总部直接派发订单的模式");
			
			MerchantsGroups selectedStore = this.merGroupMapper.selectByPrimaryKey(storeId);
			if (selectedStore != null)
			{
				logger.info("选定的门店是 " + selectedStore.getGroupName());
				session.setAttribute("SelectedStore", selectedStore);
			}
		}
		
		return "page/stock/store_stock_orders";
	}
	
	@Override
	public String storeStockOrdersData(HttpServletRequest request, HttpSession session, StockOrders order) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getTableData(null, "", 0);
		}
		
		logger.info("storeStockOrdersData .. ");
		
		if (StringUtils.isEmpty(order.getCreateDateText()))
		{
			order.setCreateDateText(null);
		}
		
		Integer stockOrderStatus = order.getStockOrderStatus();
		if (stockOrderStatus == null)
		{ 
			stockOrderStatus = (Integer) session.getAttribute("stockOrderStatus");
		}
		
		order.setStockOrderStatus(stockOrderStatus);
		
		logger.info("createDate = " + order.getCreateDateText());
		logger.info("stockOrderStatus = " + order.getStockOrderStatus());
		logger.info("page = " + order.getPage());
		logger.info("limit = " + order.getLimit());
		
		order.setMerId(this.merUser.getMerId());

		// 非审阅模式只能看本店的数据，审阅模式下可以看整个公司所有数据
		if (order.getAuditMode() == null || order.getAuditMode() == false)
		{
			logger.info("Now run as no-audit mode, merGroupId must set.");
			// 总部直接派单时，该变量不为空；门店创建订单时为空
			MerchantsGroups selectedStore = (MerchantsGroups) session.getAttribute("SelectedStore");
			if (selectedStore == null)
			{
				order.setApplyId(this.merUser.getMerGroupId());
			}
			else
			{
				// 查看选定的门店数据
				order.setApplyId(selectedStore.getMerGroupId());
			}
		}
		
		int totalCount = this.stockOrderMapper.selectStoreStockOrdersCount(order);
		logger.info("totalCount = " + totalCount);

		if (totalCount > 0)
		{
			ArrayList<StockOrders> stockOrders = this.stockOrderMapper.selectStoreStockOrders(order);
			if (stockOrders != null)
			{
				return getTableData(stockOrders, "", stockOrders.size());
			}
		}
		
		return getTableData(null, "", 0);
	}

	@Override
	public String storeStockOrderInfo(HttpServletRequest request, HttpSession session, StockOrders order) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		// 新增
		if (StringUtils.isEmpty(order.getStockOrderId()))
		{
			// 总部直接派发进货单时非空
			MerchantsGroups selectedStore = (MerchantsGroups) session.getAttribute("SelectedStore");
			String currStoreId = null;
			if (selectedStore == null)
			{
				currStoreId = this.merUser.getMerGroupId();
			}
			else
			{
				currStoreId = selectedStore.getMerGroupId();
			}
			
			StockOrders currentStockOrder = new StockOrders();
			
			// 查询当前分店的店长
			MerchantUsers query = new MerchantUsers();
			query.setMerId(this.merUser.getMerId());
			query.setMerGroupId(currStoreId);
			
			ArrayList<MerchantUsers> users = this.merUserMapper.selectStoreMaster(query);
			if (users != null && users.size() > 0)
			{
				currentStockOrder.setLinkMan(users.get(0).getUserName());
				currentStockOrder.setLinkManPhone(users.get(0).getUserPhone());
			}
			
			// 查询当前分店信息，得到其地址
			MerchantsGroups store = this.merGroupMapper.selectByPrimaryKey(currStoreId);
			if (store != null)
			{
				String applyAddress = store.getDetailAddress();
				
				// 查询地区
				Areas area = this.areaMapper.selectByPrimaryKey(store.getAreaId());
				if (area != null)
				{
					applyAddress = area.getFullName() + ", " + applyAddress;
				}
				currentStockOrder.setApplyAddress(applyAddress);
			}
			session.setAttribute("CurrentStockOrderInfo", currentStockOrder);
		}
		else
		{
			StockOrders currentStockOrder = this.stockOrderMapper.selectByPrimaryKey(order.getStockOrderId());
			session.setAttribute("CurrentStockOrderInfo", currentStockOrder);
		}
		
		return "page/stock/store_stock_order_info";
	}

	@Override
	public String saveStoreStockOrders(HttpServletRequest request, HttpSession session, StockOrders order) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		if (StringUtils.isEmpty(order.getLinkMan()))
		{
			return getRetCodeWithJson(1, getMessage("saveStoreStockOrders.linkManNotNull"));
		}
		
		if (StringUtils.isEmpty(order.getLinkManPhone()))
		{
			return getRetCodeWithJson(1, getMessage("saveStoreStockOrders.linkManPhoneNotNull"));
		}
		
		if (StringUtils.isEmpty(order.getApplyAddress()))
		{
			return getRetCodeWithJson(1, getMessage("saveStoreStockOrders.AddressNotNull"));
		}
		
		// 新增
		if (StringUtils.isEmpty(order.getStockOrderId()))
		{
			// 总部直接派发进货单时非空
			MerchantsGroups selectedStore = (MerchantsGroups) session.getAttribute("SelectedStore");
			String currStoreId = null;
			if (selectedStore == null)
			{
				currStoreId = this.merUser.getMerGroupId();
			}
			else
			{
				currStoreId = selectedStore.getMerGroupId();
			}
						
			order.setStockOrderId(MyUUID.randomUUID());
			order.setStockOrderSn(getStockOrderSN(order.getStockOrderId()));
			order.setMerId(this.merUser.getMerId());
			
			order.setApplyId(currStoreId);
			
			order.setCreateDate(new Date());
			order.setCreator(this.merUser.getUserName());
			order.setStockOrderStatus(0);
			order.setStockOrderMemo(IOUtils.LINE_SEPARATOR + this.merUser.getUserName() + " 于 " + DateFormatUtils.format(order.getCreateDate(), "yyyy-MM-dd HH:mm:ss") + " 创建了进货申请单");
			
			this.stockOrderMapper.insertSelective(order);
		}
		// 修改
		else
		{
			this.stockOrderMapper.updateBriefInfo(order);
		}
		
		return getRetCodeWithJson(0);
	}
	
	private int getStockOrderSN(String stockOrderId)
	{
		StockOrdersSN orderSn = new StockOrdersSN();
		orderSn.setStockOrderId(stockOrderId);
		
		this.stockOrderSnMapper.insert(orderSn);
		
		StockOrdersSN query = this.stockOrderSnMapper.selectByOrderId(stockOrderId);
		
		return query.getStockOrderSn();
	}

	@Override
	public String deleteStoreStockOrders(HttpServletRequest request, HttpSession session, StockOrders order) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		if (!StringUtils.isEmpty(order.getStockOrderId()))
		{
			// 先判断状态，安全考虑
			StockOrders query = this.stockOrderMapper.selectByPrimaryKey(order.getStockOrderId());
			if (query != null)
			{
				if (query.getStockOrderStatus() != 0){
					return getRetCodeWithJson(1, getMessage("deleteStoreStockOrders.StockOrderInProcessing"));
				}
				
				// 先删除配置的商品明细
				stockOrderDetailMapper.deleteByOrderId(order.getStockOrderId());
				
				// 再删除订单表
				stockOrderMapper.deleteByPrimaryKey(order.getStockOrderId());
				
				return getRetCodeWithJson(0);
			}
		}
		
		return getRetCodeWithJson(1);
	}

	@Override
	public String storeStockOrderGoods(HttpServletRequest request, HttpSession session, StockOrders order) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		if (!StringUtils.isEmpty(order.getStockOrderId()))
		{
			StockOrders cuurentOrder = this.stockOrderMapper.selectByPrimaryKey(order.getStockOrderId());
			if (cuurentOrder != null)
			{
				session.setAttribute("CurrentStockOrder", cuurentOrder);
			}
		}
		
		// 加载商品大类
		ArrayList<GoodsType> topGoodsType = this.goodsTypeMapper.selectTopTypeByMerId(this.merUser.getMerId());
		if (topGoodsType != null && !topGoodsType.isEmpty())
		{
			session.setAttribute("TopGoodsTypes", topGoodsType);
		}
		
		return "page/stock/store_stock_order_goods";
	}

	@Override
	public ResponseEntity<byte[]> downloadStoreGoods(HttpServletRequest request, HttpSession session) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return null;
		}
		
		GoodsInfo goodsInfo = new GoodsInfo();
		goodsInfo.setMerId(this.merUser.getMerId());
		goodsInfo.setMerGroupId(this.merUser.getMerGroupId());
		
		int totalCount =this.goodsInfoMapper.selectStorePriceCount(goodsInfo);
		logger.info("totalCount = " + totalCount);
		
		MerchantsGroups store = this.merGroupMapper.selectByPrimaryKey(this.merUser.getMerGroupId());
		
		// 先打开模板文件
		String storeGoodsTemplate = FuHuiTongContext.getInstance().getExcelTempDir() + File.separator + "store_stock_order_goods_template.xls";
		logger.info(storeGoodsTemplate);
		
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File(storeGoodsTemplate));
			HSSFWorkbook wb = new HSSFWorkbook(fis);
			HSSFSheet sheet = wb.getSheetAt(0);
			
			// 导出商品库存
			if (totalCount > 0)
			{
				goodsInfo.setPage(1);
				goodsInfo.setLimit(totalCount);
				ArrayList<GoodsInfo> goodsList = this.goodsInfoMapper.selectStorePrice(goodsInfo);
				
				for (int i = 0; i < goodsList.size(); i++)
				{
					GoodsInfo goods = goodsList.get(i);
						
					// 从第三行开始
					HSSFRow row = sheet.createRow(i + 3);
						
					// 商品分类
					HSSFCell cell0 = row.createCell(0);
					cell0.setCellValue(goods.getGoodsTypeName());
					
					// 商品编码
					if (goods.getGoodsId() != null)
					{
						HSSFCell cell1 = row.createCell(1);
						cell1.setCellValue(goods.getGoodsId());
					}
					
					// 商品条码
					if(goods.getGoodsCode() != null)
					{
						HSSFCell cell2 = row.createCell(2);
						cell2.setCellValue(goods.getGoodsCode());
					}
						
					// 商品名称
					if(goods.getGoodsName() != null)
					{
						HSSFCell cell3 = row.createCell(3);
						cell3.setCellValue(goods.getGoodsName());
					}
					
					// 商品单位
					if(goods.getGoodsName() != null)
					{
						HSSFCell cell4 = row.createCell(4);
						cell4.setCellValue(goods.getProviderUnit());
					}
				}
			}
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			wb.write(bos);
			wb.close();
			
			// 生成下载文件流
			String fileName = this.sysDictMapper.selectByPrimaryKey(FuHuiTongContext.PARAM_STORE_STOCK_ORDER_GOODS_TEMP).getDicValues();
			
			String extName = FilenameUtils.getExtension(fileName);
			String baseName = FilenameUtils.getBaseName(fileName);
			
			fileName = baseName + "_" + store.getGroupName() + "_" + DateFormatUtils.format(new Date(), "yyyy-MM-dd") + "." + extName;
			
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
	public String storeStockOrderGoodsData(HttpServletRequest request, HttpSession session, StockOrders order) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getTableData(null, null, 0);
		}
		
		StockOrders currentOrder = (StockOrders) session.getAttribute("CurrentStockOrder");
		
		StockOrdersDetail query = new StockOrdersDetail();
		query.setMerId(this.merUser.getMerId());
		query.setStockOrderId(currentOrder.getStockOrderId());
		
		ArrayList<StockOrdersDetail> sotckOrderDetails = this.stockOrderDetailMapper.selectStoreStockOrderDetail(query);
		if (sotckOrderDetails != null)
		{
			return getTableData(sotckOrderDetails, "", sotckOrderDetails.size());
		}
		
		return getTableData(null, null, 0);
	}

	@Override
	public String importStoreStockOrderGoods(MultipartFile uploadFile, HttpServletRequest request,
			HttpSession session) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1);
		}
		
		HSSFWorkbook wb;
		HSSFSheet sheet;
		
		StockOrders currentOrder = (StockOrders) session.getAttribute("CurrentStockOrder");
		
		this.stockOrderDetailMapper.deleteByOrderId(currentOrder.getStockOrderId());
		
		try {
			wb = new HSSFWorkbook(uploadFile.getInputStream());
			sheet = wb.getSheetAt(0);
			
			// 从第3行开始读取数据，先预先读取一行
			HSSFRow row = sheet.getRow(3);
			GoodsInfo preOne = parseStockOrderDetail(row);
			if (preOne.getGoodsId() == null || preOne.getGoodsCode() == null)
			{
				logger.info("Data format error.");
				IOUtils.closeQuietly(wb);
				
				return getRetCodeWithJson(1, getMessage("importGoodsInfo.formatError"));
			}
			
			ArrayList<GoodsInfo> goodsInfoList = new ArrayList<GoodsInfo>();
			
			if (preOne.getGoodsCount() != null)
			{
				goodsInfoList.add(preOne);
			}
			
			int totalGoodsCount = 0;
			
			int rowNum = 4;
			while(true)
			{
				row = sheet.getRow(rowNum);
				GoodsInfo goods = parseStockOrderDetail(row);
				
				if (goods == null || goods.getGoodsId() == null || goods.getGoodsCode() == null)
				{
					// 数据结束
					break;
				}
				
				logger.info("rowNum is " + rowNum);
				logger.info("goodsId is " + goods.getGoodsId());
				logger.info("goodsCode is " + goods.getGoodsCode());
				
				if (goods.getGoodsCount() != null)
				{
					goodsInfoList.add(goods);
				}
				
				rowNum++;
			}
			
			IOUtils.closeQuietly(wb);
			
			logger.info("There are " + goodsInfoList.size() + " data records in the excel file to update.");
			
			int totalPrice = 0;
			for (GoodsInfo goods : goodsInfoList)
			{
				// 保存进货单明细
				StockOrdersDetail orderDetail = new StockOrdersDetail();
				orderDetail.setGoodsId(goods.getGoodsId());
				orderDetail.setGoodsCount(goods.getGoodsCount());
				orderDetail.setGoodsTypeId(goods.getGoodsTypeId());
				orderDetail.setStockOrderId(currentOrder.getStockOrderId());
				orderDetail.setProviderUnit(goods.getProviderUnit());
				orderDetail.setApplyId(this.merUser.getMerGroupId());
				
				// 查询商品的进货单价
				GoodsPrice query = new GoodsPrice();
				query.setMerGroupId(this.merUser.getMerGroupId());
				query.setGoodsId(goods.getGoodsId());
				GoodsPrice goodsPrice = this.goodsPriceMapper.selectByStoreGoods(query);
				if (goodsPrice != null)
				{
					int goodsTotalPrice = goodsPrice.getProviderUnitMultiple() * goodsPrice.getStoreGoodsCost() * goods.getGoodsCount();
					orderDetail.setGoodsTotalPrice(goodsTotalPrice);
					
					totalPrice += goodsTotalPrice;
				}
				
				totalGoodsCount += orderDetail.getGoodsCount();
				
				this.stockOrderDetailMapper.insertSelective(orderDetail);
			}
			logger.info("totalPrice = " + totalPrice);
			currentOrder.setGoodsCount(totalGoodsCount);
			currentOrder.setGoodsTotalPrice(totalPrice);
			
			this.stockOrderMapper.updateGoodsCount(currentOrder);
			
			int updateCount = goodsInfoList.size();
			String retMsg = "总计导入" + updateCount + "进货单数据，成功更新" + updateCount + "条";

			currentOrder.setStockOrderMemo(IOUtils.LINE_SEPARATOR + this.merUser.getUserName() + " 于 " + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + " 配置了" + updateCount + "条商品");
			this.stockOrderMapper.appendOrderProcess(currentOrder);
			
			return getRetCodeWithJson(0, retMsg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return getRetCodeWithJson(0);
	}

	@Override
	public String storeStockOrdersAudit(HttpServletRequest request, HttpSession session, StockOrders order) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		if (StringUtils.isEmpty(order.getStockOrderId()))
		{
			// 返回审核进货单的首页
			session.removeAttribute("CurrentStockOrder");
			session.removeAttribute("CurrentStockOrderDetails");
			
			session.setAttribute("stockOrderStatus", order.getStockOrderStatus());
			session.setAttribute("AuditMode", true);
			
			return "page/stock/store_stock_orders_audit";
		}
		else
		{
			session.removeAttribute("StockOrderAuditOp");
			
			StockOrders cuurentStockOrder = this.stockOrderMapper.selectByPrimaryKey(order.getStockOrderId());
			session.setAttribute("CurrentStockOrder", cuurentStockOrder);
			
			MerchantsGroups store = this.merGroupMapper.selectByPrimaryKey(cuurentStockOrder.getApplyId());
			if (store != null)
			{
				session.setAttribute("ApplyStore", store);
			}
			
			// 进入进货单的初步审核内页
			if (order.getStockOrderStatus() == 1)
			{
				logger.info("进入进货单的初步审核内页, store_stock_orders_audit1");
				
				return "page/stock/store_stock_orders_audit1";
			}
			// 进货单的复审核内页
			else if (order.getStockOrderStatus() == 2)
			{
				logger.info("进入进货单的复审核内页, store_stock_orders_audit2");
				
				return "page/stock/store_stock_orders_audit2";
			}
			// 进货单的处理内页，包括采购单，门店调度
			else if (order.getStockOrderStatus() == 3)
			{
				logger.info("进货单的处理内页，包括采购单，门店调度, store_stock_orders_audit3");
				
				return "page/stock/store_stock_orders_audit3";
			}
			else
			{
				// 非法请求，直接推出
				return "page/relogin";
			}
		}
	}

	@Override
	public String storeStockOrdersAuditData(HttpServletRequest request, HttpSession session) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getTableData(null, null, 0);
		}
		
		return getTableData(null, null, 0);
	}

	@Override
	public String storeStockOrdersSubmit(HttpServletRequest request, HttpSession session, StockOrders order, String stockOrderDetailIdStr, String goodsCountStr) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		String stockOrderId = order.getStockOrderId();
		if (StringUtils.isEmpty(stockOrderId))
		{
			StockOrders cuurentOrder = (StockOrders) session.getAttribute("CurrentStockOrder");
			stockOrderId = cuurentOrder.getStockOrderId();
			order.setStockOrderId(stockOrderId);
		}
		
		if (!StringUtils.isEmpty(stockOrderDetailIdStr) && !StringUtils.isEmpty(goodsCountStr))
		{
			storeStockBatchUpdateCount(stockOrderDetailIdStr, goodsCountStr, order);
		}
		
		StockOrders orderQuery = this.stockOrderMapper.selectByPrimaryKey(order.getStockOrderId());
		if (orderQuery != null)
		{
			if (orderQuery.getGoodsCount() == 0)
			{
				// 还没有配置商品
				return getRetCodeWithJson(1, getMessage("storeStockOrdersSubmit.NoGoodsInTheOrder"));
			}
			else
			{
				// 修改状态 到 一审中 ： 1
				String memo = IOUtils.LINE_SEPARATOR + this.merUser.getUserName() + " 于 " + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + " 提交进货单。";
				orderQuery.setStockOrderMemo(memo);
				orderQuery.setStockOrderStatus(1);
				
				this.stockOrderMapper.changeOrderStatus(orderQuery);
				
				return getRetCodeWithJson(0, getMessage("storeStockOrdersSubmit.submitSuccess"));
			}
		}
		
		return getRetCodeWithJson(0);
	}

	@Override
	public String storeStockOrderGoodsDataAudit(HttpServletRequest request, HttpSession session, StockOrders order) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getTableData(null, null, 0);
		}
		
		ArrayList<StockOrdersDetail> sotckOrderDetails = null;
		StockOrders currentOrder = (StockOrders) session.getAttribute("CurrentStockOrder");
		
		sotckOrderDetails = (ArrayList<StockOrdersDetail>) session.getAttribute("CurrentStockOrderDetails");
		if (sotckOrderDetails == null)
		{
			StockOrdersDetail query = new StockOrdersDetail();
			query.setMerId(this.merUser.getMerId());
			query.setStockOrderId(currentOrder.getStockOrderId());
			logger.info("currentOrder.getStockOrderId() = " + currentOrder.getStockOrderId());
			sotckOrderDetails = this.stockOrderDetailMapper.selectStoreStockOrderDetail(query);
			
			// 只有初审时才会重新匹配
			if (sotckOrderDetails != null && currentOrder.getStockOrderStatus() == 1)
			{
				// 缺省按照供应商进行匹配
				for (StockOrdersDetail detail : sotckOrderDetails)
				{
					if (!(StringUtils.isEmpty(detail.getToProviderId()) && StringUtils.isEmpty(detail.getFromStoreId())))
					{
						continue;
					}
					
					ArrayList<GoodsProviders> providers = this.goodsProviderMapper.selectGoodsWithProvider(detail.getGoodsId());
					
					if (providers != null && !providers.isEmpty())
					{
						detail.setProviderCount(providers.size());
						if (providers.size() == 1)
						{
							GoodsProviders provider = providers.get(0);
							
							detail.setToProviderId(provider.getMerUserId());
							detail.setToProviderName(provider.getUserName() + "[" +provider.getMerName() + "]");
							detail.setProviderCost(provider.getGoodsCost());
							detail.setFromStoreId("");
							detail.setFromStoreName("");
						}
						else
						{
							detail.setToProviderId("");
							detail.setToProviderName("");
							detail.setProviderCost(0);
							detail.setFromStoreId("");
							detail.setFromStoreName("");
						}
					}
					else
					{
						detail.setToProviderId("");
						detail.setToProviderName("");
						detail.setProviderCost(0);
						detail.setFromStoreId("");
						detail.setFromStoreName("");
					}
				}
				
				// 保存在会话中，后面还会进一步修改调整
				session.setAttribute("CurrentStockOrderDetails", sotckOrderDetails);
				
				return getTableData(sotckOrderDetails, "", sotckOrderDetails.size());
			}
		}
		
		return getTableData(sotckOrderDetails, "", sotckOrderDetails.size());
	}

	@Override
	public String selectGoodsProvider(HttpServletRequest request, HttpSession session, String goodsId) {
		
		if (!this.merUserHasAccessRight(request, session) || StringUtils.isEmpty(goodsId))
		{
			logger.info("Current user was not logged in.");
			return getTableData(null, null, 0);
		}
		
		ArrayList<GoodsProviders> providers = this.goodsProviderMapper.selectGoodsWithProvider(goodsId);
		if (providers != null && !providers.isEmpty())
		{
			return getTableData(providers, "", providers.size());
		}
		
		return getTableData(null, null, 0);
	}

	@Override
	public String selectAllStoreGoodsStock(HttpServletRequest request, HttpSession session, String goodsId) {
		if (!this.merUserHasAccessRight(request, session) || StringUtils.isEmpty(goodsId))
		{
			logger.info("Current user was not logged in.");
			return getTableData(null, null, 0);
		}
		
		StockOrders currentOrder = (StockOrders) session.getAttribute("CurrentStockOrder");
		
		ArrayList<GoodsStock> allStoreStock = this.goodsStockMapper.selectStoreStockByGoodsId(goodsId);
		if (allStoreStock != null && !allStoreStock.isEmpty())
		{
			// 剔除发起进货单的门店
			for (GoodsStock stock : allStoreStock)
			{
				if (stock.getOwnerId().equalsIgnoreCase(currentOrder.getApplyId()))
				{
					allStoreStock.remove(stock);
					break;
				}
			}
			
			return getTableData(allStoreStock, "", allStoreStock.size());
		}
		
		return getTableData(null, null, 0);
	}

	/**
	 * 修改
	 */
	@Override
	public String setGoodsProviderInfo(HttpServletRequest request, HttpSession session, StockOrdersDetail detail) {
		
		// 得到 stockOrderDetailId, toProviderId, providerCost, dealType
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1);
		}
		
		logger.info("StockOrderDetailId = " + detail.getStockOrderDetailId());
		logger.info("ToProviderId = " + detail.getToProviderId());
		logger.info("ProviderCost = " + detail.getProviderCost());
		logger.info("dealType = " + detail.getDealType());
		
		if (StringUtils.isEmpty(detail.getToProviderId()))
		{
			return getRetCodeWithJson(1, getMessage("setGoodsProviderInfo.ToProviderIdisNull"));
		}
		
		// 临时保存在内存中，不写入数据库，等审核完毕后再写入数据库
		ArrayList<StockOrdersDetail> sotckOrderDetails  = (ArrayList<StockOrdersDetail>) session.getAttribute("CurrentStockOrderDetails");
		for (StockOrdersDetail d : sotckOrderDetails)
		{
			logger.info("d.stockOrderDetailId = " + d.getStockOrderDetailId());
			logger.info("detail.stockOrderDetailId = " + detail.getStockOrderDetailId());
			
			if (d.getStockOrderDetailId().intValue() == detail.getStockOrderDetailId().intValue())
			{
				if (!StringUtils.isEmpty(detail.getToProviderName()))
				{
					d.setToProviderId(detail.getToProviderId());
					d.setDealType(detail.getDealType());
					d.setToProviderName(detail.getToProviderName());
				}
				d.setProviderCost(detail.getProviderCost());
			}
		}
		
		session.setAttribute("CurrentStockOrderDetails", sotckOrderDetails);
		
		return getRetCodeWithJson(0);
	}

	@Override
	public String setStockOrderDealType(HttpServletRequest request, HttpSession session, StockOrdersDetail detail) {
		
		// 得到 stockOrderDetailId, toProviderId, providerCost, dealType
		if (!this.merUserHasAccessRight(request, session) || StringUtils.isEmpty(detail.getStockOrderDetailId()))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1);
		}
		
		logger.info("dealType : " + detail.getDealType());
		logger.info("stockOrderDetailId : " + detail.getStockOrderDetailId());
		
		// 临时保存在内存中，不写入数据库，等审核完毕后再写入数据库
		ArrayList<StockOrdersDetail> sotckOrderDetails  = (ArrayList<StockOrdersDetail>) session.getAttribute("CurrentStockOrderDetails");
		for (StockOrdersDetail d : sotckOrderDetails)
		{
			if (d.getStockOrderDetailId().intValue() == detail.getStockOrderDetailId().intValue())
			{
				d.setDealType(detail.getDealType());
			}
		}
		
		return getRetCodeWithJson(0);
	}

	@Override
	public String setStockOrderWhichStore(HttpServletRequest request, HttpSession session, StockOrdersDetail detail) {
		// 得到 stockOrderDetailId, toProviderId, providerCost, dealType
		if (!this.merUserHasAccessRight(request, session) || StringUtils.isEmpty(detail.getStockOrderDetailId()))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1);
		}
		
		logger.info("设置从哪个门店调拨");
		logger.info("dealType : " + detail.getDealType());
		logger.info("stockOrderDetailId : " + detail.getStockOrderDetailId());
		
		// 临时保存在内存中，不写入数据库，等审核完毕后再写入数据库
		ArrayList<StockOrdersDetail> sotckOrderDetails  = (ArrayList<StockOrdersDetail>) session.getAttribute("CurrentStockOrderDetails");
		for (StockOrdersDetail d : sotckOrderDetails)
		{
			if (d.getStockOrderDetailId().intValue() == detail.getStockOrderDetailId().intValue())
			{
				d.setDealType(detail.getDealType());
				d.setFromStoreId(detail.getFromStoreId());
				d.setFromStoreName(detail.getFromStoreName());
			}
		}
		
		return getRetCodeWithJson(0);
	}

	@Override
	public String saveStockOrderAudit1(HttpServletRequest request, HttpSession session, boolean pass, String desc) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1);
		}
		
		// 从当前上下文获取进货单对象
		StockOrders currentOrder = (StockOrders) session.getAttribute("CurrentStockOrder");

		if (!pass)
		{
			// TODO StockOrders 状态回到未提交之前，STOCK_ORDER_STATUS = 0
			currentOrder.setStockOrderMemo(IOUtils.LINE_SEPARATOR + this.merUser.getUserName() + " 于 " + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + " 退回采购单，理由是 : " + desc);
			currentOrder.setStockOrderStatus(0);
			this.stockOrderMapper.changeOrderStatus(currentOrder);
			
			return getRetCodeWithJson(0, getMessage("saveStockOrderAudit1.backsuccess"));
		}
		
		ArrayList<StockOrdersDetail> sotckOrderDetails  = (ArrayList<StockOrdersDetail>) session.getAttribute("CurrentStockOrderDetails");
		
		boolean valid = true;
		String errMsg = null;
		
		// 检查一遍是否都设置完整，先检查是否有设置了门店调拨方式，但没有设置门店的
		for (StockOrdersDetail d : sotckOrderDetails)
		{
			logger.info("dealType = " + d.getDealType());
			
			// 门店调拨
			if (d.getDealType() == 1 && StringUtils.isEmpty(d.getFromStoreId()))
			{
				logger.info(d.getGoodsName() + " has no store selected to get stock.");
				valid = false;
				errMsg = d.getGoodsName() + "->" + getMessage("saveStockOrderAudit1.NoStoreSelected");
				break;
			}
			
			// 采购单
			if (d.getDealType() == 0)
			{
				if (StringUtils.isEmpty(d.getToProviderId()))
				{
					valid = false;
					errMsg = d.getGoodsName() + "->" + getMessage("saveStockOrderAudit1.NoProviderSelected");
					break;
				}
				
				if (d.getProviderCost() == 0 || d.getProviderCost() == null)
				{
					valid = false;
					errMsg = d.getGoodsName() + "->" + getMessage("saveStockOrderAudit1.NoProviderCost");
					break;
				}
			}
		}
		
		if (!valid)
		{
			return getRetCodeWithJson(1, errMsg);
		}
		else
		{
			// 统计采购商的数量 和 门店调拨的数量
			int providerCount = 0;
			int storeCount = 0;
						
			// 更新进货单商品明细
			for (StockOrdersDetail d : sotckOrderDetails)
			{
				d.setGoodsTotalPrice(d.getGoodsCount() * d.getProviderCost());
				this.stockOrderDetailMapper.updateByPrimaryKeySelective(d);
				
				if (d.getDealType() == 0)
				{
					providerCount++;
				}
				else{
					storeCount++;
				}
			}
			
			// 更新采购商和门店数量，便于下一步订单处理时分组集中处理
			currentOrder.setProviderCount(providerCount);
			currentOrder.setStoreCount(storeCount);
			this.stockOrderMapper.updateByPrimaryKeySelective(currentOrder);
			
			// 更新采购单商品总数量和总金额
			StockOrdersDetail stockDetailStat = this.stockOrderDetailMapper.statDetailOrder(currentOrder.getStockOrderId());
			StockOrders record = new StockOrders();
			record.setStockOrderId(currentOrder.getStockOrderId());
			record.setGoodsCount(stockDetailStat.getGoodsCount());
			record.setGoodsTotalPrice(stockDetailStat.getGoodsTotalPrice());
			this.stockOrderMapper.updateGoodsCount(record);
			
			// 更新订单审核状态
			StringBuffer opBuffer = (StringBuffer) session.getAttribute("StockOrderAuditOp");
			if (opBuffer == null)
			{
				opBuffer = new StringBuffer();
			}
			
			opBuffer.append(IOUtils.LINE_SEPARATOR + this.merUser.getUserName() + " 于 " + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + " 完成进货申请单的初审。");
			currentOrder.setStockOrderMemo(opBuffer.toString());
			currentOrder.setStockOrderStatus(2);
			this.stockOrderMapper.changeOrderStatus(currentOrder);
			
			session.removeAttribute("CurrentStockOrder");
			session.removeAttribute("CurrentStockOrderDetails");
			session.removeAttribute("StockOrderAuditOp");
			
			return getRetCodeWithJson(0, getMessage("saveStockOrderAudit1.success"));
		}
	}

	@Override
	public String saveStockOrderAudit2(HttpServletRequest request, HttpSession session, boolean pass, String desc) {

		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1);
		}
		
		// 从当前上下文获取进货单对象
		StockOrders currentOrder = (StockOrders) session.getAttribute("CurrentStockOrder");
		
		StockOrdersDetail query = new StockOrdersDetail();
		query.setMerId(this.merUser.getMerId());
		query.setStockOrderId(currentOrder.getStockOrderId());
		ArrayList<StockOrdersDetail> sotckOrderDetails = this.stockOrderDetailMapper.selectStoreStockOrderDetail(query);
		
		session.removeAttribute("CurrentStockOrder");
		session.removeAttribute("CurrentStockOrderDetails");

		if (pass)
		{
			// 先删除同订单号的重复数据
			this.purchaseOrderDetailMapper.deleteByStockOrderId(currentOrder.getStockOrderId());
			this.purchaseOrderMapper.deleteByStockOrderId(currentOrder.getStockOrderId());
			
			MerchantsGroups store = this.merGroupMapper.selectByPrimaryKey(currentOrder.getApplyId());
			
			// 更新订单审核状态
			currentOrder.setStockOrderMemo(IOUtils.LINE_SEPARATOR + this.merUser.getUserName() + " 于 " + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + " 完成进货申请单的终审。");
			currentOrder.setStockOrderStatus(3);
			this.stockOrderMapper.changeOrderStatus(currentOrder);
			
			// TODO 生成采购单和门店库存调拨单
			// 便于后面遍历到同一个供应商的商品时，能快速定位到PurchaseOrders，生成明细
			Hashtable<String, PurchaseOrders> purchaseOrdersTableProvider = new Hashtable<String, PurchaseOrders>();
			
			// 将多个订单汇集到一个供应商
			for (StockOrdersDetail stockOrderDetail : sotckOrderDetails)
			{
				PurchaseOrders purchaseOrderProvider = null;
				
				String providerId = null;
				
				// 供应商采购，计算采购价和商品总数
				providerId = stockOrderDetail.getToProviderId();
				purchaseOrderProvider = purchaseOrdersTableProvider.get(providerId);
				
				if (purchaseOrderProvider == null)
				{
					purchaseOrderProvider = new PurchaseOrders();
					purchaseOrderProvider.setDealType(stockOrderDetail.getDealType());
					
					// 设置供应商的公司名，查询的是用户的名字
					purchaseOrderProvider.setProviderId(stockOrderDetail.getToProviderId());

					MerchantUsers provider = this.merUserMapper.selectByPrimaryKey(stockOrderDetail.getToProviderId());
					
					purchaseOrderProvider.setProviderLinkman(provider.getUserName());
					purchaseOrderProvider.setProviderLinkmanEmail(provider.getUserEmail());
					String providerLinkmanPhone = provider.getUserPhone();
					logger.info("providerLinkmanPhone = " + providerLinkmanPhone);
					purchaseOrderProvider.setProviderLinkmanPhone(providerLinkmanPhone);
					purchaseOrderProvider.setProviderName(provider.getMerName());
					
					// 下单的门店相关信息
					purchaseOrderProvider.setLinkMan(currentOrder.getLinkMan());
					purchaseOrderProvider.setLinkManPhone(currentOrder.getLinkManPhone());
					purchaseOrderProvider.setApplyAddress(currentOrder.getApplyAddress() + ", " + store.getGroupName());
					
					// 放到索引表中
					purchaseOrdersTableProvider.put(providerId, purchaseOrderProvider);
					currentOrder.addPurchaseOrdersProvider(purchaseOrderProvider);
				}
				
				// 增加商品明细
				PurchaseOrdersDetail purchaseOrdersDetail = new PurchaseOrdersDetail();
				
				purchaseOrdersDetail.setStockOrderDetailId(stockOrderDetail.getStockOrderDetailId());
				purchaseOrdersDetail.setGoodsId(stockOrderDetail.getGoodsId());
				purchaseOrdersDetail.setGoodsAmount(stockOrderDetail.getGoodsCount());
				purchaseOrdersDetail.setProviderUnit(stockOrderDetail.getProviderUnit());
				
				// 累加商品和采购价总额
				Integer goodsAmount = 0;
				if (purchaseOrderProvider.getGoodsAmount() != null)
				{
					goodsAmount = purchaseOrderProvider.getGoodsAmount();
				}
				purchaseOrderProvider.setGoodsAmount(goodsAmount + stockOrderDetail.getGoodsCount());

				// 供应商采购，计算采购价和商品总数
				if (stockOrderDetail.getDealType() == 0)
				{
					purchaseOrdersDetail.setGoodsCost(stockOrderDetail.getProviderCost());
					
					Integer goodsTotalPrice = 0;
					if (purchaseOrderProvider.getGoodsTotalPrice() != null)
					{
						goodsTotalPrice = purchaseOrderProvider.getGoodsTotalPrice();
					}
					
					// 累加商品采购总价值
					goodsTotalPrice += stockOrderDetail.getGoodsCount() * stockOrderDetail.getProviderCost();
					
					purchaseOrderProvider.setGoodsTotalPrice(goodsTotalPrice);
				}
				
				purchaseOrderProvider.addOrderDetail(purchaseOrdersDetail);
			}
			
			// 保存到数据库中
			logger.info("Create " + purchaseOrdersTableProvider.size() + " purchase orders.");
			
			ArrayList<PurchaseOrders> purchaseOrders = currentOrder.getPurchaseOrdersProvider();
			for (PurchaseOrders purcOrder : purchaseOrders)
			{
				// 生成供应商采购单
				purcOrder.setPurchaseOrderId(MyUUID.randomUUID());
				purcOrder.setCreateDate(new Date());
				purcOrder.setCreator(this.merUser.getUserName());
				purcOrder.setStockOrderId(currentOrder.getStockOrderId());
				
				// 获取SN号码
				PurchaseOrdersSn sn = new PurchaseOrdersSn();
				sn.setPurchaseOrderId(purcOrder.getPurchaseOrderId());
				this.purchaseOrderSnMapper.insert(sn);
				
				// 得到SN号
				PurchaseOrdersSn querySn = this.purchaseOrderSnMapper.selectByPurchaseOrderId(purcOrder.getPurchaseOrderId());
				purcOrder.setOrderSn(querySn.getPurchaseOrderSn());
				purcOrder.setApplyId(currentOrder.getApplyId());
				logger.info("生成供应商采购单, " + querySn.getPurchaseOrderSn());
				
				this.purchaseOrderMapper.insertSelective(purcOrder);
				
				ArrayList<PurchaseOrdersDetail> purchaseDetail = purcOrder.getOrderDetails();
				// 保存所有的商品明细
				for (PurchaseOrdersDetail detail : purchaseDetail)
				{
					detail.setPurchaseOrderId(purcOrder.getPurchaseOrderId());
					detail.setStockOrderId(currentOrder.getStockOrderId());
					// 缺省情况下，供应商确认的数量与商品采购数量相同
					detail.setProviderAckAmount(detail.getGoodsAmount());
					purchaseOrderDetailMapper.insertSelective(detail);
				}
			}
			
			/*purchaseOrders = currentOrder.getPurchaseOrdersStore();
			for (PurchaseOrders purcOrder : purchaseOrders)
			{
				purcOrder.setPurchaseOrderId(MyUUID.randomUUID());
				purcOrder.setCreateDate(new Date());
				purcOrder.setCreator(this.merUser.getUserName());
				logger.info("currentOrder.getStockOrderId() = " + currentOrder.getStockOrderId());
				purcOrder.setStockOrderId(currentOrder.getStockOrderId());
				
				// 获取SN号码
				PurchaseOrdersSn sn = new PurchaseOrdersSn();
				sn.setPurchaseOrderId(purcOrder.getPurchaseOrderId());
				this.purchaseOrderSnMapper.insert(sn);
				
				// 得到SN号
				PurchaseOrdersSn querySn = this.purchaseOrderSnMapper.selectByPurchaseOrderId(purcOrder.getPurchaseOrderId());
				purcOrder.setOrderSn(querySn.getPurchaseOrderSn());
				purcOrder.setApplyId(currentOrder.getApplyId());

				this.purchaseOrderMapper.insertSelective(purcOrder);
				
				logger.info("生成门店调拨单, " + querySn.getPurchaseOrderSn());
				
				ArrayList<PurchaseOrdersDetail> purchaseDetail = purcOrder.getOrderDetails();
				// 保存所有的商品明细
				for (PurchaseOrdersDetail detail : purchaseDetail)
				{
					detail.setPurchaseOrderId(purcOrder.getPurchaseOrderId());
					purchaseOrderDetailMapper.insertSelective(detail);
				}
			}*/
			
			return getRetCodeWithJson(0, getMessage("saveStockOrderAudit2.success"));
		}
		else
		{
			// 驳回
			StringBuffer buffer = new StringBuffer();
			buffer.append(IOUtils.LINE_SEPARATOR + this.merUser.getUserName() + " 于 " + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + " 进行进货申请单的终审，被驳回。");
			buffer.append(IOUtils.LINE_SEPARATOR + desc);
			currentOrder.setStockOrderMemo(buffer.toString());
			currentOrder.setStockOrderStatus(1);
			this.stockOrderMapper.changeOrderStatus(currentOrder);
			
			return getRetCodeWithJson(0, getMessage("saveStockOrderAudit2.backSuccess"));
		}
		
		
	}

	@Override
	public String listStockOrderProviders(HttpServletRequest request, HttpSession session, PurchaseOrders queryParam) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getTableData(null, null, 0);
		}
		
		// 从当前上下文获取进货单对象
		StockOrders currentOrder = (StockOrders) session.getAttribute("CurrentStockOrder");
		
		logger.info("Loading all purchase orders of Stock Order " + currentOrder.getStockOrderId() + ", dealType " + queryParam.getDealType());
		logger.info("OrderStatus = " + queryParam.getOrderStatus());
		
		if (StringUtils.isEmpty(queryParam.getOrderStatus()))
		{
			queryParam.setOrderStatus(null);
		}
		
		// 返回数据
		PurchaseOrders query = new PurchaseOrders();
		query.setDealType(queryParam.getDealType());
		query.setStockOrderId(currentOrder.getStockOrderId());
		query.setOrderStatus(queryParam.getOrderStatus());
		
		if (queryParam.getStoreAck() != null)
		{
			// 门店调拨，需要首先出库门店一方确认出库，收货一方才能看到
			query.setStoreAck(queryParam.getStoreAck());
		}
		
		ArrayList<PurchaseOrders> purchaseOrders = this.purchaseOrderMapper.selectByStockOrder(query);
		
		return getTableData(purchaseOrders, "", purchaseOrders.size());
	}

	@Override
	public String storePurchaseOrderView(HttpServletRequest request, HttpSession session, String purchaseOrderId) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		if (!StringUtils.isEmpty(purchaseOrderId))
		{
			PurchaseOrders currentPurchaseOrders = this.purchaseOrderMapper.selectByPrimaryKey(purchaseOrderId);
			
			if (currentPurchaseOrders != null)
			{
				session.setAttribute("CurrentPurchaseOrders", currentPurchaseOrders);
				
				if (currentPurchaseOrders.getDealType() == 0)
				{
					return "page/stock/store_purchase_order_view";
				}
				else
				{
					// 库存调拨单
					MerchantsGroups providerStore = this.merGroupMapper.selectByPrimaryKey(currentPurchaseOrders.getProviderId());
					if (providerStore != null)
					{
						session.setAttribute("ProviderStore", providerStore);
					}
					
					return "page/stock/store_purchase_order_view2";
				}
			}
		}
		
		return "page/relogin";
	}

	@Override
	public String storePurchaseOrderViewGoods(HttpServletRequest request, HttpSession session, String purchaseOrderId) {
		
		if (!this.merUserHasAccessRight(request, session) || StringUtils.isEmpty(purchaseOrderId))
		{
			logger.info("Current user was not logged in.");
			return getTableData(null, null, 0);
		}
		
		ArrayList<PurchaseOrdersDetail> purchaseOrderDetail = this.purchaseOrderDetailMapper.selectByPurchaseOrderId(purchaseOrderId);
		if (purchaseOrderDetail != null && purchaseOrderDetail.size() > 0)
		{
			return getTableData(purchaseOrderDetail, "", purchaseOrderDetail.size());
		}
		
		return getTableData(null, null, 0);
	}

	@Override
	public String saveStockOrderAudit3(HttpServletRequest request, HttpSession session) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1);
		}		
		
		// 采购单生效，变为1，进货单状态变为4，等待收货确认
		StockOrders currStockOrder = (StockOrders) session.getAttribute("CurrentStockOrder");
		
		PurchaseOrders queryPur = new PurchaseOrders();
		queryPur.setStockOrderId(currStockOrder.getStockOrderId());
		queryPur.setOrderStatus(0);
		
		ArrayList<PurchaseOrders> purchaseOrders = this.purchaseOrderMapper.selectByStockOrder(queryPur);
		if (purchaseOrders != null && !purchaseOrders.isEmpty())
		{
			for (PurchaseOrders purOrder : purchaseOrders)
			{
				// 修改采购单状态到 1
				purOrder.setOrderSn(null);
				purOrder.setOrderStatus(1);
				this.purchaseOrderMapper.changeOrderStatus(purOrder);
			}
		}
		
		currStockOrder.setStockOrderMemo(IOUtils.LINE_SEPARATOR + this.merUser.getUserName() + " 于 " + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + " 完成了采购单的下单");
		currStockOrder.setStockOrderStatus(4);
		this.stockOrderMapper.changeOrderStatus(currStockOrder);
		
		return getRetCodeWithJson(0, getMessage("saveStockOrderAudit3.Success"));
	}

	@Override
	public String storeStockOrdersView(HttpServletRequest request, HttpSession session, String stockOrderId, String adminMode) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		if (!StringUtils.isEmpty(stockOrderId))
		{
			StockOrders currentStockOrder = this.stockOrderMapper.selectByPrimaryKey(stockOrderId);
			if (currentStockOrder != null)
			{
				String applyId = currentStockOrder.getApplyId();
				MerchantsGroups applyStore = this.merGroupMapper.selectByPrimaryKey(applyId);
				if (applyStore != null)
				{
					session.setAttribute("ApplyStore", applyStore);
				}
				
				session.setAttribute("CurrentStockOrder", currentStockOrder);
			}
		}
		
		if (StringUtils.isEmpty(adminMode))
		{
			return "page/stock/store_stock_orders_view";
		}
		else
		{
			return "page/stock/store_stock_orders_view2";
		}
	}

	@Override
	public String storeStockOrdersAck(HttpServletRequest request, HttpSession session, String stockOrderId) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		if (StringUtils.isEmpty(stockOrderId))
		{
			// 没有传参数时，接入到收货确认的列表主界面
			
			return "page/stock/store_stock_orders_ack";
		}
		else
		{
			logger.info("Deliver goods ack : " + stockOrderId);
			
			StockOrders currentStockOrder = this.stockOrderMapper.selectByPrimaryKey(stockOrderId);
			if (currentStockOrder != null)
			{
				MerchantsGroups store = this.merGroupMapper.selectByPrimaryKey(currentStockOrder.getApplyId());
				session.setAttribute("ApplyStore", store);
				session.setAttribute("CurrentStockOrder", currentStockOrder);
			}
			
			// 进入收货确认的具体页面
			return "page/stock/store_stock_orders_ack1";
		}
	}

	@Override
	public String storeStockOrdersAckGoods(HttpServletRequest request, HttpSession session, String purchaseOrderId) {
		
		if (!this.merUserHasAccessRight(request, session) || StringUtils.isEmpty(purchaseOrderId))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		PurchaseOrders purchaseOrder = this.purchaseOrderMapper.selectByPrimaryKey(purchaseOrderId);
		session.setAttribute("CurrPurchaseOrder", purchaseOrder);
		
		return "page/stock/store_stock_orders_ack2";
	}

	private String storeStockOrdersAckGoods20(HttpServletRequest request, HttpSession session)
	{
		PurchaseOrders purchaseOrder = (PurchaseOrders) session.getAttribute("CurrPurchaseOrder");
		
		// 更新进货单中所有商品签收数量，和实际的进货总价值
		StockOrdersDetail stockDetailQuery = this.stockOrderDetailMapper.selectTotalAckPriceBy(purchaseOrder.getStockOrderId());
		if (stockDetailQuery != null)
		{
			StockOrders orderParam = new StockOrders();
			orderParam.setStockOrderId(purchaseOrder.getStockOrderId());
			orderParam.setAckGoodsCount(stockDetailQuery.getAckGoodsCount());
			orderParam.setAckGoodsTotalPrice(stockDetailQuery.getAckGoodsTotalPrice());
			
			this.stockOrderMapper.updateAckGoodsCount(orderParam);
		}
		
		// TODO 更新purchaseOrder已验货总数和总价
		PurchaseOrdersDetail statAckedValue = this.purchaseOrderDetailMapper.statAckedTotalValue(purchaseOrder.getPurchaseOrderId());
		purchaseOrder.setAuditAmount(statAckedValue.getGoodsAckAmount());
		purchaseOrder.setAuditTotalPrice(statAckedValue.getGoodsCost());
		this.purchaseOrderMapper.updateAckAmount(purchaseOrder);
		
		// 检查是否全部验收完成
		int unAckedDetail = this.purchaseOrderDetailMapper.selectByUnAckedCount(purchaseOrder.getPurchaseOrderId());
		if (unAckedDetail == 0)
		{
			// 所有商品都确认完毕，自动把当前供应商的采购单确认完毕
			PurchaseOrders queryp = new PurchaseOrders();
			queryp.setPurchaseOrderId(purchaseOrder.getPurchaseOrderId());
			queryp.setOrderStatus(2);
			
			this.purchaseOrderMapper.changeOrderStatus(queryp);
			
			// TODO 生成库存批次
			this.createGoodsStock(purchaseOrder);
			
			return getRetCodeWithJson(0, getMessage("storeStockOrdersAckGoods2.CurrentPurchaseOrderAckSuccess"));
		}
		
		return getRetCodeWithJson(0, getMessage("storeStockOrdersAckGoods2.Success"));
	}
	
	@Override
	public String storeStockOrdersAckGoods2(HttpServletRequest request, HttpSession session, PurchaseOrdersDetail orderDetail) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			return getRetCodeWithJson(1);
		}
		
		if (orderDetail.getGoodsAckAmount() == null)
		{
			return getRetCodeWithJson(1, getMessage("storeStockOrdersAckGoods2.AckAmountNotNull"));
		}
		
		int goodsAckAmount = orderDetail.getGoodsAckAmount().intValue();
		logger.info("goodsAckAmount = " + goodsAckAmount);
		
		if (goodsAckAmount < 0)
		{
			return getRetCodeWithJson(1, getMessage("storeStockOrdersAckGoods2.AckAmountNotNull"));
		}
		
		// 查询当前采购单明细
		PurchaseOrdersDetail purchaseOrderDetail = this.purchaseOrderDetailMapper.selectByPrimaryKey(orderDetail.getPurchaseDetailId());
		if (orderDetail.getGoodsAckAmount() > purchaseOrderDetail.getGoodsAmount())
		{
			// 确认的金额大于订单中采购数量
			logger.error("确认的数量不能大于订单中采购数量");
			
			return getRetCodeWithJson(1, getMessage("storeStockOrdersAckGoods2.AckAmountLessThanProviderAckAmount"));
		}
		
		// 设置生产日期和有效期
		if (!StringUtils.isEmpty(orderDetail.getProductDateText()))
		{
			logger.info("ProductDateText is " + orderDetail.getProductDateText());
			// 查询该商品的保质期
			GoodsInfo goodsInfo = this.goodsInfoMapper.selectByPrimaryKey(orderDetail.getGoodsId());
			if (goodsInfo != null)
			{
				logger.info("Expired day is " + goodsInfo.getExpiredDay());
				// 自动计算保质期日期
				Date expiredDate = DateUtils.addDays(orderDetail.getProductDate(), goodsInfo.getExpiredDay());
				orderDetail.setExpiredDateText(DateFormatUtils.format(expiredDate, "yyyy-MM-dd"));
			}
		}
		
		// 更新商品采购明细的确认状态，验收标志，0未验收，1已验收
		orderDetail.setAckStatus(1);
		this.purchaseOrderDetailMapper.updateByPrimaryKeySelective(orderDetail);
		
		PurchaseOrders purchaseOrder = (PurchaseOrders) session.getAttribute("CurrPurchaseOrder");
		
		// 设定原始采购单的实际验收数量
		StockOrdersDetail stockDetail = new StockOrdersDetail();
		stockDetail.setStockOrderId(purchaseOrder.getStockOrderId());
		stockDetail.setGoodsId(orderDetail.getGoodsId());
		stockDetail.setAckGoodsCount(orderDetail.getGoodsAckAmount());
		this.stockOrderDetailMapper.updateOnAck(stockDetail);
		
		return storeStockOrdersAckGoods20(request, session);
	}
	
	/**
	 * 全部验收
	 */
	@Override
	public String storeStockOrdersAckAllGoods2(HttpServletRequest request, HttpSession session,
			String purchaseDetailIdStr, String goodsAckAmountStr) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		if (StringUtils.isEmpty(purchaseDetailIdStr))
		{
			return getRetCodeWithJson(1, getMessage("storeStockOrdersAckGoods2.NoGoodsToAck"));
		}
		
		if (StringUtils.isEmpty(goodsAckAmountStr))
		{
			return getRetCodeWithJson(1, getMessage("storeStockOrdersAckGoods2.AckAmountNotNull"));
		}
		
		String[] purchaseDetailIdArr = purchaseDetailIdStr.split(",");
		if (purchaseDetailIdArr == null || purchaseDetailIdArr.length == 0)
		{
			return getRetCodeWithJson(1, getMessage("storeStockOrdersAckGoods2.NoGoodsToAck"));
		}
		
		String[] goodsAckAmountArr = goodsAckAmountStr.split(",");
		if (goodsAckAmountArr == null || goodsAckAmountArr.length == 0)
		{
			return getRetCodeWithJson(1, getMessage("storeStockOrdersAckGoods2.AckAmountNotNull"));
		}
		
		// 检查收货确认的数量,不能小于0，不能大于商品原数量
		boolean checkGoodsAck = true;
		String checkGoodsStr = "";
		for (int i = 0; i < goodsAckAmountArr.length; i++)
		{
			Integer ackAmount = Integer.parseInt(goodsAckAmountArr[i]);
			Integer purchaseDetailId = Integer.parseInt(purchaseDetailIdArr[i]);
			
			if (ackAmount < 0)
			{
				checkGoodsAck = false;
				checkGoodsStr += "确认的数量不能小于0";
				break;
			}
			
			// 查询当前采购单明细
			PurchaseOrdersDetail purchaseOrderDetail = this.purchaseOrderDetailMapper.selectByPrimaryKey(purchaseDetailId);
			if (ackAmount > purchaseOrderDetail.getGoodsAmount())
			{
				GoodsInfo goodsInfo = this.goodsInfoMapper.selectByPrimaryKey(purchaseOrderDetail.getGoodsId());
				logger.error("确认的数量不能大于订单中采购数量," + goodsInfo.getGoodsName());
				checkGoodsStr += "确认的数量不能大于订单中采购数量," + goodsInfo.getGoodsName() + ",采购数量:" + purchaseOrderDetail.getGoodsAmount() + ", 确认数量：" + ackAmount;
				logger.error(checkGoodsStr);
				checkGoodsAck = false;
				break;
			}
		}
		if (!checkGoodsAck)
		{
			return getRetCodeWithJson(1, checkGoodsStr);
		}
		
		PurchaseOrders purchaseOrder = (PurchaseOrders) session.getAttribute("CurrPurchaseOrder");
		
		// 开始做批量验收
		for (int i = 0; i < goodsAckAmountArr.length; i++)
		{
			String purchaseDetailIdS = purchaseDetailIdArr[i];
			Integer purchaseDetailId = Integer.parseInt(purchaseDetailIdS);
			Integer ackAmount = Integer.parseInt(goodsAckAmountArr[i]);
			
			// 查询当前采购单明细
			PurchaseOrdersDetail purchaseOrderDetail = this.purchaseOrderDetailMapper.selectByPrimaryKey(purchaseDetailId);
			purchaseOrderDetail.setGoodsAckAmount(ackAmount);
			// 更新商品采购明细的确认状态，验收标志，0未验收，1已验收
			purchaseOrderDetail.setAckStatus(1);
			this.purchaseOrderDetailMapper.updateByPrimaryKeySelective(purchaseOrderDetail);
			
			// 设定原始采购单的实际验收数量
			StockOrdersDetail stockDetail = new StockOrdersDetail();
			stockDetail.setStockOrderId(purchaseOrder.getStockOrderId());
			stockDetail.setGoodsId(purchaseOrderDetail.getGoodsId());
			stockDetail.setAckGoodsCount(purchaseOrderDetail.getGoodsAckAmount());
			this.stockOrderDetailMapper.updateOnAck(stockDetail);
		}
		
		return storeStockOrdersAckGoods20(request, session);
	}
	
	/**
	 * 生成库存批次，并更新门店的库存总数
	 * @param purchaseOrder
	 */
	private void createGoodsStock(PurchaseOrders purchaseOrder)
	{
		logger.info("Create goods stock data.");
		
		// 每一个进货单可能会对应多个供货商，每一个供货单对应一个入库批次
		GoodsStockBills stockBill = new GoodsStockBills();
		stockBill.setCreateDate(new Date());
		
		// 库存属于当前门店
		stockBill.setOwnerId(this.merUser.getMerGroupId());
		stockBill.setOwnerType(purchaseOrder.getDealType());
		stockBill.setPurchaseOrderId(purchaseOrder.getPurchaseOrderId());
		stockBill.setStockOrderId(purchaseOrder.getStockOrderId());
		
		// 入库
		stockBill.setStockBillSource(0);
		stockBill.setStockBillId(MyUUID.randomUUID());
		stockBill.setGoodsAmount(purchaseOrder.getGoodsAmount());
		
		this.goodsStockBillMapper.insertSelective(stockBill);
		
		// 新增库存每种商品的明细
		ArrayList<PurchaseOrdersDetail> purchaseOrderDetails = this.purchaseOrderDetailMapper.selectByPurchaseOrderId(purchaseOrder.getPurchaseOrderId());
		logger.info("purchaseOrderDetails = " + purchaseOrderDetails.size());
		
		for (PurchaseOrdersDetail detail : purchaseOrderDetails)
		{
			GoodsStockDetail goodsStockDetail = new GoodsStockDetail();
			
			goodsStockDetail.setExpiredDate(goodsStockDetail.getExpiredDate());
			
			goodsStockDetail.setGoodsId(detail.getGoodsId());
			goodsStockDetail.setOwnerId(stockBill.getOwnerId());
			goodsStockDetail.setProductDate(detail.getProductDate());
			goodsStockDetail.setExpiredDate(detail.getExpiredDate());
			goodsStockDetail.setStockBillId(stockBill.getStockBillId());
			
			Integer providerUnitMultiple = detail.getProviderUnitMultiple();
			if (providerUnitMultiple == null)
			{
				providerUnitMultiple = 1;
			}
			
			// TODO 进货的计量单位和零售不同，需要换算
			int goodsRealCount = detail.getGoodsAckAmount() * providerUnitMultiple;
			goodsStockDetail.setStockCount(goodsRealCount);
			goodsStockDetail.setStockCount0(goodsRealCount);
			
			// 商品单件成本价
			goodsStockDetail.setGoodsCost((float)detail.getGoodsCost() / providerUnitMultiple);
			
			goodsStockDetail.setCreateDate(new Date());
			goodsStockDetail.setStockBillType(0);
			
			this.goodsStockDetailMapper.insertSelective(goodsStockDetail);
			
			updateGoodsStock(goodsStockDetail);
		}
	}

	/**
	 * 更新商户单种商品库存
	 * @param goodsStockDetail
	 */
	private void updateGoodsStock(GoodsStockDetail goodsStockDetail)
	{
		// 先判断是否有当前商品的库存，如果没有，则直接插入，如果有，则更新之
		
		GoodsStock query = new GoodsStock();
		query.setGoodsId(goodsStockDetail.getGoodsId());
		query.setOwnerId(goodsStockDetail.getOwnerId());
		
		GoodsStock storeStock = this.goodsStockMapper.selectStoreStockByGoodsId2(query);
		
		if (storeStock == null)
		{
			// 插入库存数据
			GoodsStock stock = new GoodsStock();
			stock.setGoodsId(goodsStockDetail.getGoodsId());
			stock.setOwnerType(3);
			stock.setOwnerId(goodsStockDetail.getOwnerId());
			stock.setStockAmount(goodsStockDetail.getStockCount());
			
			this.goodsStockMapper.insertSelective(stock);
		}
		else
		{
			// 更新库存,设置新的库存
			storeStock.setStockAmount(goodsStockDetail.getStockCount());
			
			//this.goodsStockMapper.updateMerchantStock2(storeStock);
			GoodsStock goodsStock = new GoodsStock();
			goodsStock.setGoodsId(goodsStockDetail.getGoodsId());
			goodsStock.setOwnerId(goodsStockDetail.getOwnerId());
			
			this.goodsStockMapper.statUpdateStoreStock(goodsStock);
		}
		
	}
	
	@Override
	public String storeStockOrdersAckSuccess(HttpServletRequest request, HttpSession session) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		StockOrders stockOrder = (StockOrders) session.getAttribute("CurrentStockOrder");
		
		// 先检查是否还有未签收完的送货单
		PurchaseOrders query = new PurchaseOrders();
		query.setStockOrderId(stockOrder.getStockOrderId());
		query.setOrderStatus(1);
		
		Integer unAckedPurchases =  this.purchaseOrderMapper.selectByUnAcked(query);
		logger.info("unAckedPurchases = " + unAckedPurchases);
		if (unAckedPurchases == null || unAckedPurchases > 0)
		{
			return getRetCodeWithJson(1, getMessage("storeStockOrdersAckSuccess.NotAllPurchaseOrderAcked"));
		}
		
		String memo = IOUtils.LINE_SEPARATOR + this.merUser.getUserName() + " 于 " + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + " 完成了进货单所有商品签收。";
		
		StockOrders orderQuery = new StockOrders();
		orderQuery.setStockOrderId(stockOrder.getStockOrderId());
		orderQuery.setStockOrderMemo(memo);
		orderQuery.setStockOrderStatus(5);
		
		this.stockOrderMapper.changeOrderStatus(orderQuery);
		
		// 更新进货单中所有商品签收数量，和实际的进货总价值
		StockOrdersDetail stockDetail = this.stockOrderDetailMapper.selectTotalAckPriceBy(stockOrder.getStockOrderId());
		if (stockDetail != null)
		{
			StockOrders orderParam = new StockOrders();
			orderParam.setStockOrderId(stockOrder.getStockOrderId());
			orderParam.setAckGoodsCount(stockDetail.getAckGoodsCount());
			orderParam.setAckGoodsTotalPrice(stockDetail.getAckGoodsTotalPrice());
			
			this.stockOrderMapper.updateAckGoodsCount(orderParam);
		}
		
		return getRetCodeWithJson(0, getMessage("storeStockOrdersAckSuccess.OrderAckedSuccess"));
	}

	@Override
	public String storeGoodsStock(HttpServletRequest request, HttpSession session, String merGroupId) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		// 需要加载商品大类
		// 加载商品大类
		ArrayList<GoodsType> topGoodsType = this.goodsTypeMapper.selectTopTypeByMerId(this.merUser.getMerId());

		if (StringUtils.isEmpty(merGroupId)){
			session.setAttribute("CurrentMerGroupId", this.merUser.getMerGroupId());
		}
		else
		{
			session.setAttribute("CurrentMerGroupId", merGroupId);
			MerchantsGroups currStore = this.merGroupMapper.selectByPrimaryKey(merGroupId);
			session.setAttribute("CurrentStore", currStore);
		}
		
		if (topGoodsType != null && !topGoodsType.isEmpty())
		{
			session.setAttribute("TopGoodsTypes", topGoodsType);
		}
		
		if (StringUtils.isEmpty(merGroupId)){
			return "page/stock/store_goods_stock";
		}
		else{
			// 该分支是总部查看门店的库存入口点
			return "page/stock/store_goods_stock2";
		}
	}

	@Override
	public String storeGoodsStockData(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getTableData(null, null, 0);
		}
		
		logger.info("page : " + goodsInfo.getPage());
		logger.info("limit : " + goodsInfo.getLimit());
		
		
		goodsInfo = formatMerchantStockParam(goodsInfo);

		String merGroupId = (String) session.getAttribute("CurrentMerGroupId");
		goodsInfo.setMerGroupId(merGroupId);
		
		int totalCount =this.goodsInfoMapper.selectStoreGoodsStockCount(goodsInfo);
		logger.info("totalCount = " + totalCount);
		
		if (totalCount > 0)
		{
			ArrayList<GoodsInfo> goodsList = this.goodsInfoMapper.selectStoreGoodsStock(goodsInfo);
			return getTableData(goodsList, "", totalCount);
		}
		else
		{
			return getTableData(null, null, 0);
		}
	}
	
	@Override
	public String storeGoodsStockCheckData(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getTableData(null, null, 0);
		}
		
		logger.info("page : " + goodsInfo.getPage());
		logger.info("limit : " + goodsInfo.getLimit());
		
		if (StringUtils.isEmpty(goodsInfo.getGoodsCode()))
		{
			goodsInfo.setGoodsCode(null);
		}
		
		goodsInfo = formatMerchantStockParam(goodsInfo);
		goodsInfo.setCreateDateText(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));

		String merGroupId = (String) session.getAttribute("CurrentMerGroupId");
		goodsInfo.setMerGroupId(merGroupId);
		
		int totalCount =this.goodsInfoMapper.selectStoreGoodsCheckStockCount(goodsInfo);
		logger.info("totalCount = " + totalCount);
		
		if (totalCount > 0)
		{
			ArrayList<GoodsInfo> goodsList = this.goodsInfoMapper.selectStoreGoodsCheckStock(goodsInfo);
			return getTableData(goodsList, "", totalCount);
		}
		else
		{
			return getTableData(null, null, 0);
		}
	}

	@Override
	public String storeGoodsStockDataDetail(HttpServletRequest request, HttpSession session) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getTableData(null, null, 0);
		}
		
		GoodsInfo currGoodsInfo = (GoodsInfo) session.getAttribute("CurrGoodsInfo");
		String merGroupId = (String) session.getAttribute("CurrentMerGroupId");
		
		GoodsStockDetail query = new GoodsStockDetail();
		query.setOwnerId(merGroupId);
		query.setGoodsId(currGoodsInfo.getGoodsId());
		
		ArrayList<GoodsStockDetail> goodsStockDetails = this.goodsStockDetailMapper.selectByGoodsId(query);
		if (goodsStockDetails != null && !goodsStockDetails.isEmpty())
		{
			return getTableData(goodsStockDetails, "", goodsStockDetails.size());
		}
		
		return getTableData(null, null, 0);
	}

	@Override
	public String storeGoodsStockDetail(HttpServletRequest request, HttpSession session, String goodsId) {
		
		if (!this.merUserHasAccessRight(request, session) || StringUtils.isEmpty(goodsId))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		GoodsInfo goodsInfo = this.goodsInfoMapper.selectByPrimaryKey(goodsId);
		if (goodsInfo != null)
		{
			session.setAttribute("CurrGoodsInfo", goodsInfo);
		}
		
		String merGroupId = (String) session.getAttribute("CurrentMerGroupId");
		
		// 查询所有门店
		ArrayList<MerchantsGroups> stores = this.merGroupMapper.selectAllStores(this.merUser.getMerId());
		MerchantsGroups currStore = null;
		
		// 过滤掉选择的门店
		for (MerchantsGroups store : stores)
		{
			if (store.getMerGroupId().equalsIgnoreCase(merGroupId))
			{
				currStore = store;
				break;
			}
		}
		if (currStore != null)
		{
			stores.remove(currStore);
		}
		
		session.setAttribute("AllStoreToDispatch", stores);
		
		return "page/stock/store_goods_stock_detail";
	}

	@Override
	public String storeGoodsStockOut(HttpServletRequest request, HttpSession session) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		return "page/stock/store_goods_stock_out";
	}

	@Override
	public String storeGoodsStockOutData(HttpServletRequest request, HttpSession session) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getTableData(null, "", 0);
		}
		
		PurchaseOrders query = new PurchaseOrders();
		query.setProviderId(this.merUser.getMerGroupId());
		query.setStoreAck(0);
		
		ArrayList<PurchaseOrders> purchaseOrders = this.purchaseOrderMapper.selectStoreOutStockOrders(query);
		if (purchaseOrders != null && !purchaseOrders.isEmpty())
		{
			return getTableData(purchaseOrders, "", purchaseOrders.size());
		}
		
		return getTableData(null, "", 0);
	}

	@Override
	public String storeGoodsStockOutGoods(HttpServletRequest request, HttpSession session, String purchaseOrderId) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		PurchaseOrders purchaseOrder = this.purchaseOrderMapper.selectByPrimaryKey(purchaseOrderId);
		session.setAttribute("CurrentPurchaseOrdersForOut", purchaseOrder);
		
		return "page/stock/store_goods_stock_out_goods";
	}

	@Override
	public String storeGoodsStockOutGoodsData(HttpServletRequest request, HttpSession session) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getTableData(null, null, 0);
		}
		
		PurchaseOrders purchaseOrder = (PurchaseOrders) session.getAttribute("CurrentPurchaseOrdersForOut");
		
		PurchaseOrdersDetail query = new PurchaseOrdersDetail();
		query.setPurchaseOrderId(purchaseOrder.getPurchaseOrderId());
		query.setOwnerId(this.merUser.getMerGroupId());
		
		ArrayList<PurchaseOrdersDetail> purchaseOrderDetail = this.purchaseOrderDetailMapper.selectByPurchOrderIdWithStock(query);
		
		if (purchaseOrderDetail != null && purchaseOrderDetail.size() > 0)
		{
			return getTableData(purchaseOrderDetail, "", purchaseOrderDetail.size());
		}
		
		return getTableData(null, null, 0);
	}

	@Override
	public String storeGoodsStockOutAck(HttpServletRequest request, HttpSession session) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		PurchaseOrders purchaseOrder = (PurchaseOrders) session.getAttribute("CurrentPurchaseOrdersForOut");
		
		// 库存调出的门店
		String fromStoreId = purchaseOrder.getProviderId();
		logger.info("从" + purchaseOrder.getProviderName() + " 调出库存...");
		
		PurchaseOrdersDetail query = new PurchaseOrdersDetail();
		query.setPurchaseOrderId(purchaseOrder.getPurchaseOrderId());
		query.setOwnerId(this.merUser.getMerGroupId());
		
		ArrayList<PurchaseOrdersDetail> purchaseOrderDetails = this.purchaseOrderDetailMapper.selectByPurchOrderIdWithStock(query);
		
		if (purchaseOrderDetails != null && purchaseOrderDetails.size() > 0)
		{
			// 对该商户新建一个出库库存批次
			GoodsStockBills stockBill = new GoodsStockBills();
			stockBill.setCreateDate(new Date());
			stockBill.setGoodsAmount(purchaseOrder.getGoodsAmount());
			stockBill.setOwnerId(fromStoreId);
			stockBill.setOwnerType(3);
			stockBill.setPurchaseOrderId(purchaseOrder.getPurchaseOrderId());
			stockBill.setStockOrderId(purchaseOrder.getStockOrderId());
			stockBill.setStockBillId(MyUUID.randomUUID());
			stockBill.setStockBillSource(1);
			stockBill.setStockBillType(1);
			stockBill.setStockBillStatus(-1);
			
			// 新增临时库存批次
			this.goodsStockBillMapper.insertSelective(stockBill);
			
			// 可能会对应到多个数量较小的批次
			ArrayList<GoodsStockDetail> outStockDetails = new ArrayList<>();
			
			// goodsId, goodsAmount
			for (PurchaseOrdersDetail detail : purchaseOrderDetails)
			{
				String goodsId = detail.getGoodsId();
				Integer goodsAmount = detail.getGoodsAmount();
				
				logger.info("goodsId = " + goodsId + ", goodsCode = " + detail.getGoodsCode());
				
				// 查询该门店 当前商品的库存批次
				GoodsStockDetail queryParam = new GoodsStockDetail();
				queryParam.setOwnerId(fromStoreId);
				queryParam.setGoodsId(goodsId);
				
				ArrayList<GoodsStockDetail> goodsStockDetails = this.goodsStockDetailMapper.selectValidByGoodsId(queryParam);
				
				for (GoodsStockDetail stockDetail : goodsStockDetails)
				{
					int outStockSize = 0;
					if (stockDetail.getStockCount() >= goodsAmount)
					{
						outStockSize = goodsAmount;
					}
					else
					{
						outStockSize = stockDetail.getStockCount();
					}
					
					// 库存够用，新建一个临时库存批次
					try {
						GoodsStockDetail newOut = (GoodsStockDetail) BeanUtils.cloneBean(stockDetail);
						newOut.setGoodsStockDetailId(null);
						newOut.setStockCount0(outStockSize);
						newOut.setStockCount(0);
						newOut.setStockBillType(1);
						newOut.setStockDetailStatus(-1);
						newOut.setCreateDate(new Date());
						newOut.setReferStockDetailId(stockDetail.getGoodsStockDetailId());
						outStockDetails.add(newOut);
					} catch (Exception e) {
						e.printStackTrace();
					}
					goodsAmount -= outStockSize;
					
					if (goodsAmount <= 0)
					{
						break;
					}
				}
			}
			
			logger.info("总共" + outStockDetails.size() + "个商品库存明细");
			for (GoodsStockDetail outDetail : outStockDetails)
			{
				outDetail.setStockBillId(stockBill.getStockBillId());
				this.goodsStockDetailMapper.insertSelective(outDetail);
			}
		}
		
		// 修改PurchaseOrders的门店确认状态为 storeAck = 1，记录确认时间 STORE_ACK_DATE
		purchaseOrder.setStoreAck(1);
		purchaseOrder.setStoreAckDate(new Date());
		this.purchaseOrderMapper.updateStoreOutAck(purchaseOrder);
		
		// 出货单确认成功，等对方门店接收货物并确认后，出库数据方可生效
		return getRetCodeWithJson(0, getMessage("storeGoodsStockOutAck.StockOutAckedSuccess"));
	}

	@Override
	public String storeGoodsExpiredStock(HttpServletRequest request, HttpSession session, String merGroupId) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		if (!StringUtils.isEmpty(merGroupId))
		{
			session.setAttribute("ExpiredStockCurrentMerGroupId", merGroupId);
			MerchantsGroups currentStore = this.merGroupMapper.selectByPrimaryKey(merGroupId);
			session.setAttribute("ExpiredStockCurrentStore", currentStore);
		}
		else
		{
			session.setAttribute("ExpiredStockCurrentMerGroupId", this.merUser.getMerGroupId());
		}
		
		return "page/stock/store_goods_expired_stock";
	}

	@Override
	public String storeGoodsExpiredStockData(HttpServletRequest request, HttpSession session, Integer expiredType) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getTableData(null, "", 0);
		}
		
		String merGroupId = (String) session.getAttribute("ExpiredStockCurrentMerGroupId");
		
		logger.info("查询临期货物");
		GoodsStockDetail query = new GoodsStockDetail();
		query.setOwnerId(merGroupId);
		query.setExpiredType(expiredType);
				
		ArrayList<GoodsStockDetail> expiredGoodsStock = this.goodsStockDetailMapper.selectExpiredStock(query);
		if (expiredGoodsStock != null && expiredGoodsStock.size() > 0)
		{
			return getTableData(expiredGoodsStock, "", expiredGoodsStock.size());
		}
		
		return getTableData(null, "", 0);
	}

	@Override
	public String masterStoreGoodsStockSelect(HttpServletRequest request, HttpSession session) {
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
		
		return "page/stock/master_stores_goods_stock_select";
	}

	@Override
	public String storeStockOrdersReports(HttpServletRequest request, HttpSession session) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		return "page/reports/store_stock_orders_reports";
	}

	@Override
	public String storeStockOrdersReportsData(HttpServletRequest request, HttpSession session, StockOrders stockOrder) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getTableData(null, null, 0);
		}
		
		stockOrder.setMerId(this.merUser.getMerId());
		if (StringUtils.isEmpty(stockOrder.getCreateDateText()))
		{
			stockOrder.setCreateDateText(null);
		}
		if (StringUtils.isEmpty(stockOrder.getEndDateText()))
		{
			stockOrder.setEndDateText(null);
		}
		
		int totalCount = this.stockOrderMapper.selectStoreStockOrdersReportsCount(stockOrder);
		if (totalCount > 0)
		{
			ArrayList<StockOrders> reports = this.stockOrderMapper.selectStoreStockOrdersReports(stockOrder);
			if (reports != null && !reports.isEmpty())
			{
				return getTableData(reports, null, totalCount);
			}
		}
		
		return getTableData(null, null, 0);
	}

	@Override
	public String storeGoodsStockCheck(HttpServletRequest request, HttpSession session, GoodsStockCheck stockCheck) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		logger.info("goodsStockId = " + stockCheck.getGoodsStockId());
		logger.info("checkResult = " + stockCheck.getCheckResult());
		logger.info("checkDiffValue = " + stockCheck.getCheckDiffValue());
		
		String nowText = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
		
		// 先删除当日重复盘点记录
		stockCheck.setCreateDateText(nowText);
		this.goodsStockCheckMapper.deleteByDay(stockCheck);
		
		// 插入盘点记录
		stockCheck.setCreateDate(new Date());
		stockCheck.setStockCheckorId(this.merUser.getMerUserId());
		stockCheck.setStockCheckorName(this.merUser.getUserName());
		stockCheck.setOwnerId(this.merUser.getMerGroupId());
		
		this.goodsStockCheckMapper.insertSelective(stockCheck);
		
		return getRetCodeWithJson(0, getMessage("storeGoodsStockCheck.checkGoodsStockSuccess"));
	}

	@Override
	public String storeStockInitOrdersDetail(HttpServletRequest request, HttpSession session, String goodsList) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		if (StringUtils.isEmpty(goodsList))
		{
			return getRetCodeWithJson(1, getMessage("storeStockInitOrdersDetail.goodsListNotNull"));
		}
		
		StockOrders cuurentOrder = (StockOrders) session.getAttribute("CurrentStockOrder");
		String[] goodsIdArr = goodsList.split(",");
		
		// 先查出当前进货单已经设置了的商品，避免重复设置
		StockOrdersDetail query = new StockOrdersDetail();
		query.setMerId(this.merUser.getMerId());
		query.setStockOrderId(cuurentOrder.getStockOrderId());
		ArrayList<StockOrdersDetail> stockOrderDetails = this.stockOrderDetailMapper.selectStoreStockOrderDetail(query);
		
		Hashtable<String, StockOrdersDetail> details = new Hashtable<>();

		if (stockOrderDetails != null && !stockOrderDetails.isEmpty())
		{
			for (StockOrdersDetail detail : stockOrderDetails)
			{
				details.put(detail.getGoodsId(), detail);
			}
		}
		
		// 订货管理有两个入口分支，一个是店长发起的订货，另外一个是总部直接派发的，此时门店需要从session中获取
		// 总部直接派发进货单时非空
		MerchantsGroups selectedStore = (MerchantsGroups) session.getAttribute("SelectedStore");
		String currStoreId = null;
		if (selectedStore == null)
		{
			// 一个是店长发起的订货
			currStoreId = this.merUser.getMerGroupId();
		}
		else
		{
			// 另外一个是总部直接派发的，此时门店需要从session中获取
			currStoreId = selectedStore.getMerGroupId();
		}
		
		// 过滤重复的商品
		int count = 0;
		for (String goodsId : goodsIdArr)
		{
			if (details.get(goodsId) == null)
			{
				count++;
				GoodsInfo goodsInfo = this.goodsInfoMapper.selectByPrimaryKey(goodsId);
				
				// 保存进货单明细
				StockOrdersDetail orderDetail = new StockOrdersDetail();
				orderDetail.setGoodsId(goodsId);
				orderDetail.setGoodsCount(1);
				orderDetail.setGoodsTypeId(goodsInfo.getGoodsTypeId());
				orderDetail.setStockOrderId(cuurentOrder.getStockOrderId());
				orderDetail.setProviderUnit(goodsInfo.getProviderUnit());
				orderDetail.setApplyId(currStoreId);
				
				this.stockOrderDetailMapper.insertSelective(orderDetail);
			}
		}
		
		if (count > 0)
		{
			StockOrdersDetail orderDetailStat = this.stockOrderDetailMapper.statDetailOrder(cuurentOrder.getStockOrderId());
			if (orderDetailStat != null)
			{
				cuurentOrder.setGoodsCount(orderDetailStat.getGoodsCount());
				cuurentOrder.setGoodsTotalPrice(orderDetailStat.getGoodsTotalPrice());
			}
			this.stockOrderMapper.updateGoodsCount(cuurentOrder);
		}
		
		return getRetCodeWithJson(0);
	}

	@Override
	public String storeStockDeleteOrdersDetail(HttpServletRequest request, HttpSession session,
			Integer stockOrderDetailId) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		if (stockOrderDetailId != null)
		{
			this.stockOrderDetailMapper.deleteByPrimaryKey(stockOrderDetailId);
		}
		
		StockOrders cuurentOrder = (StockOrders) session.getAttribute("CurrentStockOrder");
		
		// 刷新采购单总数量和成本
		StockOrdersDetail orderDetailStat = this.stockOrderDetailMapper.statDetailOrder(cuurentOrder.getStockOrderId());
		if (orderDetailStat != null)
		{
			cuurentOrder.setGoodsCount(orderDetailStat.getGoodsCount());
			cuurentOrder.setGoodsTotalPrice(orderDetailStat.getGoodsTotalPrice());
		}
		else
		{
			cuurentOrder.setGoodsCount(0);
			cuurentOrder.setGoodsTotalPrice(0);
		}
		this.stockOrderMapper.updateGoodsCount(cuurentOrder);
		
		return getRetCodeWithJson(0, getMessage("storeStockDeleteOrdersDetail.deleteSuccess"));
	}

	

	@Override
	public String setGoodsCountInfoInstockOrder(HttpServletRequest request, HttpSession session,
			StockOrdersDetail detail) {
		
		// 得到 stockOrderDetailId, goodsId, goodsCount
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1);
		}
		
		logger.info("StockOrderDetailId = " + detail.getStockOrderDetailId());
		logger.info("goodsId = " + detail.getGoodsId());
		logger.info("goodsCount = " + detail.getGoodsCount());
		logger.info("goodsCode = " + detail.getGoodsCode());
		logger.info("goodsName = " + detail.getGoodsName());
		
		StringBuffer auditBuffer = (StringBuffer) session.getAttribute("StockOrderAuditOp");
		if (auditBuffer == null)
		{
			auditBuffer = new StringBuffer();
		}
		
		auditBuffer.append(IOUtils.LINE_SEPARATOR).append(this.merUser.getUserName())
			.append(" 于 ").append(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"))
			.append(" 修改 【").append(detail.getGoodsName()).append("】 采购数量为 ")
			.append(detail.getGoodsCount()).append("。");
		
		session.setAttribute("StockOrderAuditOp", auditBuffer);
		
		// 临时保存在内存中，不写入数据库，等审核完毕后再写入数据库
		ArrayList<StockOrdersDetail> sotckOrderDetails  = (ArrayList<StockOrdersDetail>) session.getAttribute("CurrentStockOrderDetails");
		for (StockOrdersDetail d : sotckOrderDetails)
		{
			if (d.getStockOrderDetailId().intValue() == detail.getStockOrderDetailId().intValue())
			{
				logger.info("d.stockOrderDetailId = " + d.getStockOrderDetailId());
				logger.info("detail.stockOrderDetailId = " + detail.getStockOrderDetailId());
				
				if (!StringUtils.isEmpty(detail.getGoodsId()))
				{
					d.setGoodsCount(detail.getGoodsCount());
					
					// TODO 需要修改商品的总采购价
					// 查询商品的进货单价
					/*GoodsPrice queryPrice = new GoodsPrice();
					queryPrice.setMerGroupId(d.getApplyId());
					queryPrice.setGoodsId(detail.getGoodsId());
					GoodsPrice goodsPrice = this.goodsPriceMapper.selectByStoreGoods(queryPrice);
					if (goodsPrice != null)
					{
						int goodsTotalPrice = goodsPrice.getProviderUnitMultiple() * goodsPrice.getStoreGoodsCost() * detail.getGoodsCount();
						d.setGoodsTotalPrice(goodsTotalPrice);
					}*/
				}
			}
		}
		
		StockOrders cuurentOrder = (StockOrders) session.getAttribute("CurrentStockOrder");
		// 刷新采购单总数量和成本
		StockOrdersDetail orderDetailStat = this.stockOrderDetailMapper.statDetailOrder(cuurentOrder.getStockOrderId());
		if (orderDetailStat != null)
		{
			cuurentOrder.setGoodsCount(orderDetailStat.getGoodsCount());
			cuurentOrder.setGoodsTotalPrice(orderDetailStat.getGoodsTotalPrice());
		}
		else
		{
			cuurentOrder.setGoodsCount(0);
			cuurentOrder.setGoodsTotalPrice(0);
		}
		this.stockOrderMapper.updateGoodsCount(cuurentOrder);
		
		session.setAttribute("CurrentStockOrderDetails", sotckOrderDetails);
		
		return getRetCodeWithJson(0);
	}

	private String storeStockUpdateOrdersDetail(HttpSession session, StockOrdersDetail detail, String storeId)
	{
		if (detail.getStockOrderDetailId() != null)
		{
			StockOrdersDetail param = this.stockOrderDetailMapper.selectByPrimaryKey(detail.getStockOrderDetailId());
			if (param.getProviderCost() == null)
			{
				// 新增采购数量时，还未设置供应商，此时为null
				this.stockOrderDetailMapper.updateByPrimaryKeySelective(detail);
			}
			else
			{
				detail.setGoodsTotalPrice(detail.getGoodsCount() * param.getProviderCost());
				// 重新修改进货单明细的商品数量和总价值
				this.stockOrderDetailMapper.updateOnAuditAfterProviderAck(detail);
			}
		}
		
		StockOrders cuurentOrder = (StockOrders) session.getAttribute("CurrentStockOrder");
		
		// 刷新采购单总数量和成本
		StockOrdersDetail orderDetailStat = this.stockOrderDetailMapper.statDetailOrder(cuurentOrder.getStockOrderId());
		if (orderDetailStat != null)
		{
			cuurentOrder.setGoodsCount(orderDetailStat.getGoodsCount());
			cuurentOrder.setGoodsTotalPrice(orderDetailStat.getGoodsTotalPrice());
		}
		else
		{
			cuurentOrder.setGoodsCount(0);
			cuurentOrder.setGoodsTotalPrice(0);
		}
		this.stockOrderMapper.updateGoodsCount(cuurentOrder);
		
		return getRetCodeWithJson(0, getMessage("storeStockDeleteOrdersDetail.updateSuccess"));
	}
	
	@Override
	public String storeStockUpdateOrdersDetail(HttpServletRequest request, HttpSession session,
			StockOrdersDetail detail) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}

		return storeStockUpdateOrdersDetail(session, detail, this.merUser.getMerGroupId());
	}
	
	@Override
	public String storeStockUpdate2OrdersDetail(HttpServletRequest request, HttpSession session,
			StockOrdersDetail detail) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}

		StockOrders cuurentOrder = (StockOrders) session.getAttribute("CurrentStockOrder");
		
		String retMsg = storeStockUpdateOrdersDetail(session, detail, cuurentOrder.getApplyId());
		
		// TODO 增加处理记录
		StringBuffer auditBuffer = new StringBuffer();
		
		auditBuffer.append(IOUtils.LINE_SEPARATOR).append(this.merUser.getUserName())
			.append(" 于 ").append(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"))
			.append(" 修改 【").append(detail.getGoodsName()).append("】 采购数量为 ")
			.append(detail.getGoodsCount()).append("。");
		
		cuurentOrder.setStockOrderMemo(auditBuffer.toString());
		this.stockOrderMapper.appendOrderProcess(cuurentOrder);
		
		return retMsg;
	}

	@Override
	public String providerMyOrders(HttpServletRequest request, HttpSession session) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		return "page/stock/provider_my_orders";
	}

	@Override
	public String providerMyOrdersData(HttpServletRequest request, HttpSession session, PurchaseOrders param) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getTableData(null, null, 0);
		}
		
		logger.info("ProviderId = " + this.merUser.getMerUserId());
		logger.info("DealType = " + param.getDealType());
		logger.info("providerAckStatus = " + param.getProviderAckStatus());
		
		param.setProviderId(this.merUser.getMerUserId());
		Integer totalCount = this.purchaseOrderMapper.selectByProviderCount(param);
		if (totalCount != null && totalCount > 0)
		{
			ArrayList<PurchaseOrders> myOrders = this.purchaseOrderMapper.selectByProvider(param);
			if (myOrders != null && !myOrders.isEmpty())
			{
				return getTableData(myOrders, null, totalCount);
			}
		}
		
		return getTableData(null, null, 0);
	}

	@Override
	public String providerMyOrdersGoods(HttpServletRequest request, HttpSession session, String purchaseOrderId, String action) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		if (!StringUtils.isEmpty(purchaseOrderId))
		{
			PurchaseOrders currentPurchaseOrders = this.purchaseOrderMapper.selectByPrimaryKey(purchaseOrderId);
			if (currentPurchaseOrders != null)
			{
				session.setAttribute("CurrentPurchaseOrders", currentPurchaseOrders);

				// 查询采购单信息
				StockOrders currentStockOrder = this.stockOrderMapper.selectByPrimaryKey(currentPurchaseOrders.getStockOrderId());
				if (currentStockOrder != null)
				{
					session.setAttribute("CurrentStockOrder", currentStockOrder);
				}
				
				// 查询进货的门店信息
				MerchantsGroups store = this.merGroupMapper.selectByPrimaryKey(currentStockOrder.getApplyId());
				if (store != null)
				{
					session.setAttribute("ApplyStore", store);
				}
			}
		}
		
		if (action.equals("ack"))
		{
			return "page/stock/provider_my_orders_goods";
		}
		else
		{
			return "page/stock/provider_my_orders_goods_view";
		}
	}

	@Override
	public String providerMyOrdersGoodsData(HttpServletRequest request, HttpSession session) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getTableData(null, null, 0);
		}
		
		PurchaseOrders currentPurchaseOrders = (PurchaseOrders) session.getAttribute("CurrentPurchaseOrders");
		
		ArrayList<PurchaseOrdersDetail> purchaseOrderDetails = this.purchaseOrderDetailMapper.selectByPurchaseOrderId(currentPurchaseOrders.getPurchaseOrderId());
		if (purchaseOrderDetails != null && !purchaseOrderDetails.isEmpty())
		{
			return getTableData(purchaseOrderDetails, "", purchaseOrderDetails.size());
		}
		
		return getTableData(null, null, 0);
	}

	@Override
	public String providerMyOrdersAckGoods(HttpServletRequest request, HttpSession session,
			PurchaseOrdersDetail purchaseDetail) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		logger.info("供应商确认商品采购数量，" + purchaseDetail.getPurchaseDetailId() + ", 采购数量为：" + purchaseDetail.getProviderAckAmount());
		
		// 先查询当前明细
		PurchaseOrdersDetail detail = this.purchaseOrderDetailMapper.selectByPrimaryKey(purchaseDetail.getPurchaseDetailId());
		
		// 如果供应商确认的数量，和实际采购数量一致，则直接返回成功
		if (purchaseDetail.getProviderAckAmount() == detail.getGoodsAmount())
		{
			// 状态恢复成【已确认无修改】
			purchaseDetail.setProviderAckStatus(1);
			this.purchaseOrderDetailMapper.updateProviderAck(purchaseDetail);
			
			// 更新总采购单 PurchaseOrders 数量和价值
			PurchaseOrdersDetail statValue = this.purchaseOrderDetailMapper.statProviderAckTotalValue(detail.getPurchaseOrderId());
			PurchaseOrders param = new PurchaseOrders();
			param.setPurchaseOrderId(detail.getPurchaseOrderId());
			param.setGoodsAmount(statValue.getProviderAckAmount());
			param.setGoodsTotalPrice(statValue.getGoodsCost());
			this.purchaseOrderMapper.updateTotalProviderValue(param);
			
			logger.info("供应商确认的数量，和实际采购数量一致，则直接返回成功");
			return getRetCodeWithJson(0, getMessage("providerMyOrdersAckGoods.ackSuccess"));
		}
		
		// 确认的数量和实际采购量不一致
		if (purchaseDetail.getProviderAckAmount() > detail.getGoodsAmount())
		{
			logger.info("供应商确认的数量，不能大于原始采购量");
			return getRetCodeWithJson(1, getMessage("providerMyOrdersAckGoods.ackAmountCanntBigThan"));
		}
		
		// 修改采购量
		// 状态变成【已确认有修改】
		purchaseDetail.setProviderAckStatus(2);
		this.purchaseOrderDetailMapper.updateProviderAck(purchaseDetail);
		
		// 更新总采购单 PurchaseOrders 数量和价值
		PurchaseOrdersDetail statValue = this.purchaseOrderDetailMapper.statProviderAckTotalValue(detail.getPurchaseOrderId());
		PurchaseOrders param = new PurchaseOrders();
		param.setPurchaseOrderId(detail.getPurchaseOrderId());
		param.setGoodsAmount(statValue.getProviderAckAmount());
		param.setGoodsTotalPrice(statValue.getGoodsCost());
		this.purchaseOrderMapper.updateTotalProviderValue(param);
		
		return getRetCodeWithJson(0, getMessage("providerMyOrdersAckGoods.ackSuccess"));
	}

	@Override
	public String providerMyOrdersAck(HttpServletRequest request, HttpSession session) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		/**
		 * 总体需求：如果供应商没有改动商品采购数量，则直接表示接受，总部也无需再审核确认
		 * 如果供应商改动了商品采购数量，总部需要审核。
		 * 首先遍历所有采购单明细，检查确认数量和原始采购数量是否有不同的，有不同则改变purchaseDetail状态PROVIDER_ACK_STATUS。
		 * 只要有一个不同，则整个PurchaseOrder的PROVIDER_ACK_STATUS要进行修改，交由总部审核。
		 * 总部审核通过之后，原始进货单中的数量会进行同步。
		 */
		PurchaseOrders currentPurchaseOrders = (PurchaseOrders) session.getAttribute("CurrentPurchaseOrders");
		ArrayList<PurchaseOrdersDetail> purchaseOrderDetails = this.purchaseOrderDetailMapper.selectByPurchaseOrderId(currentPurchaseOrders.getPurchaseOrderId());
		if (purchaseOrderDetails == null || purchaseOrderDetails.isEmpty())
		{
			return getRetCodeWithJson(1, getMessage("providerMyOrdersAckGoods.ackFailure"));
		}
		
		// 原始采购数量与供应商确认数量相同者，PROVIDER_ACK_STATUS为1
		this.purchaseOrderDetailMapper.updateAckStatus1(currentPurchaseOrders.getPurchaseOrderId());
		
		// 原始采购数量与供应商确认数量不同者，PROVIDER_ACK_STATUS为2
		this.purchaseOrderDetailMapper.updateAckStatus2(currentPurchaseOrders.getPurchaseOrderId());
		
		// 确定是否有修改
		boolean modified = false;
		for (PurchaseOrdersDetail detail : purchaseOrderDetails)
		{
			if (detail.getGoodsAmount() != detail.getProviderAckAmount())
			{
				modified = true;
				break;
			}
		}
		
		PurchaseOrders param = new PurchaseOrders();
		param.setPurchaseOrderId(currentPurchaseOrders.getPurchaseOrderId());
		param.setProviderAckDate(new Date());
		
		if (modified)
		{
			// 供应商有修改，整体采购单 PurchaseOrders的状态改为 PROVIDER_ACK_STATUS = 2
			logger.info("当前供应商修改了采购单数量，PROVIDER_ACK_STATUS = 2");
			param.setProviderAckStatus(2);
			
			this.purchaseOrderMapper.updateProviderAck(param);
			
			// 在StockOrders记录中打标记，便于被总部审核时提取出来
			StockOrders stockParam = new StockOrders();
			stockParam.setStockOrderId(currentPurchaseOrders.getStockOrderId());
			stockParam.setProviderAckStatus(2);
			
			this.stockOrderMapper.updateProviderAck(stockParam);
		}
		else
		{
			// 没有修改，PROVIDER_ACK_STATUS = 1
			logger.info("当前供应商修改了采购单数量，PROVIDER_ACK_STATUS = 1");
			param.setProviderAckStatus(1);
			
			this.purchaseOrderMapper.updateProviderAck(param);
			
			// 当前进货单STOCK_ORDERS 对应的状态进入 STOCK_ORDER_STATUS = 4
			StockOrders currStockOrder = this.stockOrderMapper.selectByPrimaryKey(currentPurchaseOrders.getStockOrderId());
			currStockOrder.setStockOrderMemo(IOUtils.LINE_SEPARATOR + this.merUser.getUserName() + " 于 " + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + " 完成了采购单的下单确认");
			currStockOrder.setStockOrderStatus(4);
			this.stockOrderMapper.changeOrderStatus(currStockOrder);
		}
		
		return getRetCodeWithJson(0, getMessage("providerMyOrdersAck.providerAckSuccess"));
	}

	@Override
	public String providerOrdersAudit(HttpServletRequest request, HttpSession session) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		return "page/stock/provider_purchase_orders_audit";
	}

	@Override
	public String providerOrdersAuditData(HttpServletRequest request, HttpSession session, PurchaseOrders purchaseOrder) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getTableData(null, null, 0);
		}
		
		purchaseOrder.setMerId(this.merUser.getMerId());
		Integer totalCount = this.purchaseOrderMapper.selectByProviderToAuditCount(purchaseOrder);
		if (totalCount != null && totalCount > 0)
		{
			ArrayList<PurchaseOrders> purchaseOrderList = this.purchaseOrderMapper.selectByProviderToAudit(purchaseOrder);
			if (purchaseOrderList != null && !purchaseOrderList.isEmpty())
			{
				return getTableData(purchaseOrderList, "", totalCount);
			}
		}
		
		return getTableData(null, null, 0);
	}

	@Override
	public String providerOrdersGoodsAudit(HttpServletRequest request, HttpSession session, String purchaseOrderId, String action) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		// 先查询当前明细
		PurchaseOrders currentPurchaseOrders = this.purchaseOrderMapper.selectByPrimaryKey(purchaseOrderId);
		if (currentPurchaseOrders != null)
		{
			session.setAttribute("CurrentPurchaseOrders", currentPurchaseOrders);
			
			// 查询采购单信息
			StockOrders currentStockOrder = this.stockOrderMapper.selectByPrimaryKey(currentPurchaseOrders.getStockOrderId());
			if (currentStockOrder != null)
			{
				session.setAttribute("CurrentStockOrder", currentStockOrder);
			}
			
			// 查询进货的门店信息
			MerchantsGroups store = this.merGroupMapper.selectByPrimaryKey(currentStockOrder.getApplyId());
			if (store != null)
			{
				session.setAttribute("ApplyStore", store);
			}
		}
		
		return "page/stock/provider_purchase_orders_goods_audit";
	}

	@Override
	public String providerOrdersAuditAck(HttpServletRequest request, HttpSession session) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		PurchaseOrders currentPurchaseOrders = (PurchaseOrders) session.getAttribute("CurrentPurchaseOrders");
		
		/*
		 *  这里同意该供应商的采购单数量之后，需要更新原始采购单、以及采购明细中的总体采购数量
		 */
		ArrayList<PurchaseOrdersDetail> purchaseOrderDetails = this.purchaseOrderDetailMapper.selectByPurchaseOrderId(currentPurchaseOrders.getPurchaseOrderId());
		if (purchaseOrderDetails == null || purchaseOrderDetails.isEmpty())
		{
			return getRetCodeWithJson(1, getMessage("providerOrdersAuditAck.AckFailure"));
		}
		
		//更新原始采购单明细
		for (PurchaseOrdersDetail purchaseDetail : purchaseOrderDetails)
		{
			StockOrdersDetail stockDetail = new StockOrdersDetail();
			stockDetail.setStockOrderDetailId(purchaseDetail.getStockOrderDetailId());
			stockDetail.setGoodsCount(purchaseDetail.getProviderAckAmount());
			stockDetail.setGoodsTotalPrice(purchaseDetail.getProviderAckAmount() * purchaseDetail.getGoodsCost());
			
			this.stockOrderDetailMapper.updateOnAuditAfterProviderAck(stockDetail);
		}
		
		StockOrders currentStockOrder = (StockOrders) session.getAttribute("CurrentStockOrder");
		
		// 更新原始采购总单的商品商品数量和总价，注意，这里的 PurchaseOrders 可能为采购总单的部分，所以需要从库中直接统计，不能从这里直接累计
		StockOrdersDetail statValue = this.stockOrderDetailMapper.statDetailOrder(currentStockOrder.getStockOrderId());
		
		StockOrders uparam = new StockOrders();
		uparam.setStockOrderId(currentStockOrder.getStockOrderId());
		uparam.setGoodsCount(statValue.getGoodsCount());
		uparam.setGoodsTotalPrice(statValue.getGoodsTotalPrice());
		this.stockOrderMapper.updateGoodsCount(uparam);
		
		// 更新PurchaseOrders的审核状态  PROVIDER_ACK_AUDIT = 1 '供应商修改后，总部审核状态，0未审核，1已审核',
		currentPurchaseOrders.setProviderAckAudit(1);
		currentPurchaseOrders.setProviderAckAuditDate(new Date());
		this.purchaseOrderMapper.updateAuditAfterProviderAck(currentPurchaseOrders);
		
		// 更新当前采购单的状态到4
		currentStockOrder.setStockOrderMemo(IOUtils.LINE_SEPARATOR + this.merUser.getUserName() + " 于 " + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + " 完成了供应商采购单修改后的复核确认");
		currentStockOrder.setStockOrderStatus(4);
		this.stockOrderMapper.changeOrderStatus(currentStockOrder);
		
		return getRetCodeWithJson(0, getMessage("providerOrdersAuditAck.AckSuccess"));
	}

	@Override
	public String masterStockOrderSelect(HttpServletRequest request, HttpSession session) {
		
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
		
		return "page/stock/master_stock_order_select";
	}

	@Override
	public String storeStockDispatch0(HttpServletRequest request, HttpSession session, StockDispatch dispatch) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1, getMessage("storeStockDispatch0.dispatchFailure"));
		}
		
		String merGroupId = (String) session.getAttribute("CurrentMerGroupId");
		MerchantsGroups fromStore = this.merGroupMapper.selectByPrimaryKey(merGroupId);
		logger.info("from store : " + merGroupId);
		logger.info("to store : " + dispatch.getToStoreId());
		logger.info("goods detail id : " + dispatch.getGoodsStockDetailId());
		logger.info("dispatch count : " + dispatch.getDispatchStockCount());
		
		// 锁定原库存明细
		GoodsStockDetail query = new GoodsStockDetail();
		query.setGoodsStockDetailId(dispatch.getGoodsStockDetailId());
		query.setLocked(true);
		this.goodsStockDetailMapper.lock(query);
		
		// 找到商品库存明细信息
		GoodsStockDetail stockDetail = this.goodsStockDetailMapper.selectByPrimaryKey(dispatch.getGoodsStockDetailId());
		// 找到当初入库单的信息
		GoodsStockBills stockBill = this.goodsStockBillMapper.selectByPrimaryKey(stockDetail.getStockBillId());
		// 找到当初采购入库的信息，便于找到供应商上门调货
		PurchaseOrders purchaseOrder = this.purchaseOrderMapper.selectByPrimaryKey(stockBill.getPurchaseOrderId());
		
		// 生成新的库存调拨草稿
		StockDispatch newDispatch = new StockDispatch();
		
		// 设置关联信息
		newDispatch.setMerId(this.merUser.getMerId());
		newDispatch.setCreateDate(new Date());
		newDispatch.setProductDate(stockDetail.getProductDate());
		newDispatch.setExpiredDate(stockDetail.getExpiredDate());
		newDispatch.setGoodsStockDetailId(dispatch.getGoodsStockDetailId());
		newDispatch.setOperator(this.merUser.getMerUserId());
		newDispatch.setOperatorName(this.merUser.getUserName());
		
		// 供货商信息
		newDispatch.setProviderId(purchaseOrder.getProviderId());
		newDispatch.setProviderName(purchaseOrder.getProviderName());
		newDispatch.setProviderLinkman(purchaseOrder.getProviderLinkman());
		
		// 调拨出的门店
		newDispatch.setFromStoreId(merGroupId);
		newDispatch.setFromStoreName(fromStore.getGroupName());
		
		// 调拨进的门店
		newDispatch.setToStoreId(dispatch.getToStoreId());
		MerchantsGroups toStore = this.merGroupMapper.selectByPrimaryKey(dispatch.getToStoreId());
		newDispatch.setToStoreName(toStore.getGroupName());
		
		// 调拨数量
		newDispatch.setCurrentStockCount(stockDetail.getStockCount());
		newDispatch.setDispatchStockCount(dispatch.getDispatchStockCount());
		
		// 商品信息
		newDispatch.setGoodsCost(stockDetail.getGoodsCost());
		newDispatch.setGoodsId(stockDetail.getGoodsId());
		GoodsInfo goodsInfo = this.goodsInfoMapper.selectByPrimaryKey(stockDetail.getGoodsId());
		newDispatch.setGoodsName(goodsInfo.getGoodsName());
		
		this.stockDispatchMapper.insertSelective(newDispatch);
		
		return getRetCodeWithJson(0, getMessage("storeStockDispatch0.dispatchSuccess"));
	}

	@Override
	public String masterStockDispatch0(HttpServletRequest request, HttpSession session) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		return "page/stock/master_stock_dispatch0";
	}

	@Override
	public String masterStockDispatch0Data(HttpServletRequest request, HttpSession session) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getTableData(null, "", 0);
		}
		
		ArrayList<StockDispatch> myStockDispatchs = this.stockDispatchMapper.selectMyTempDispatch(this.merUser.getMerId());
		if (myStockDispatchs != null && !myStockDispatchs.isEmpty())
		{
			return getTableData(myStockDispatchs, "", myStockDispatchs.size());
		}
		
		return getTableData(null, "", 0);
	}

	private PurchaseOrders createPurchaseOrders(StockDispatch dispatch)
	{
		PurchaseOrders purchaseOrder = new PurchaseOrders();
		purchaseOrder.setPurchaseOrderId(MyUUID.randomUUID());
		purchaseOrder.setDealType(1);
		purchaseOrder.setMerId(this.merUser.getMerId());
		purchaseOrder.setStoreAck(0);
		purchaseOrder.setOrderStatus(0);
		purchaseOrder.setCreateDate(new Date());
		purchaseOrder.setCreator(this.merUser.getUserName());
		purchaseOrder.setFromStoreId(dispatch.getFromStoreId());
		purchaseOrder.setFromStoreName(dispatch.getFromStoreName());
		
		purchaseOrder.setProviderId(dispatch.getProviderId());
		purchaseOrder.setProviderName(dispatch.getProviderName());
		purchaseOrder.setProviderLinkman(dispatch.getProviderLinkman());
		
		MerchantUsers provider = this.merUserMapper.selectByPrimaryKey(dispatch.getProviderId());
		purchaseOrder.setProviderLinkmanPhone(provider.getUserPhone());
		purchaseOrder.setProviderLinkmanEmail(provider.getUserEmail());
		
		purchaseOrder.setApplyId(dispatch.getToStoreId());
		
		// 查询目标门店店长联系信息
		MerchantUsers query2 = new MerchantUsers();
		query2.setMerId(this.merUser.getMerId());
		query2.setMerGroupId(dispatch.getToStoreId());
		
		ArrayList<MerchantUsers> toStoreMasters = this.merUserMapper.selectStoreMaster(query2);
		if (toStoreMasters != null && !toStoreMasters.isEmpty())
		{
			purchaseOrder.setLinkMan(toStoreMasters.get(0).getUserName());
			purchaseOrder.setLinkManPhone(toStoreMasters.get(0).getUserPhone());
			purchaseOrder.setProviderLinkmanEmail(toStoreMasters.get(0).getUserEmail());
		}
		
		// 查询调拨目标门店信息
		MerchantsGroups toStore = this.merGroupMapper.selectByPrimaryKey(dispatch.getToStoreId());
		purchaseOrder.setApplyAddress(toStore.getDetailAddress());
		
		// 商品总件数
		purchaseOrder.setGoodsAmount(0);
		// 商品总金额
		purchaseOrder.setGoodsTotalPrice(0);
		
		dispatch.setPurchaseOrderId(purchaseOrder.getPurchaseOrderId());
		purchaseOrder.addStockDispatch(dispatch);
		
		return purchaseOrder;
	}
	
	@Override
	public String masterStockDispatch0Action(HttpServletRequest request, HttpSession session, String dispatchIds) {
		
		if (!this.merUserHasAccessRight(request, session) || StringUtils.isEmpty(dispatchIds))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(0, getMessage("masterStockDispatch0Action.auditFailure"));
		}
		
		String[] dispatchIdArr = dispatchIds.split(",");
		Integer[] dispatchIdList = new Integer[dispatchIdArr.length];
		
		for (int i = 0; i < dispatchIdArr.length; i++)
		{
			dispatchIdList[i] = Integer.parseInt(dispatchIdArr[i]);
		}
		
		StockDispatch query = new StockDispatch();
		query.setStockDispatchIds(dispatchIdList);
		ArrayList<StockDispatch> myStockDispatchs = this.stockDispatchMapper.selectByIds(query);
		if (myStockDispatchs == null || myStockDispatchs.size() == 0)
		{
			return getRetCodeWithJson(0, getMessage("masterStockDispatch0Action.auditFailure"));
		}
		
		/**
		 * 将选中的调拨单按照供应商和目的门店进行分组汇总，生成 PurchaseOrder, dealType为1:门店调拨
		 * 
		 * 1) 将相同供应商的分为一个组
		 * 2) 将同一个供应商下面从不同门店调拨到不同分店的再细分为一个组
		 */
		logger.info("当前有个调拨单 " + myStockDispatchs.size());
		ArrayList<PurchaseOrders> purchaseOrders = new ArrayList<>();
		
		Hashtable<String, Hashtable<String, PurchaseOrders>> purchaseOrdersGroups = new Hashtable<>();
		
		for (StockDispatch dispatch : myStockDispatchs)
		{
			// 以供应商ID为Key，获得该供应商所有分组的Purchases
			Hashtable<String, PurchaseOrders> providerDispatchTable = purchaseOrdersGroups.get(dispatch.getProviderId());
			
			if (providerDispatchTable == null)
			{
				providerDispatchTable = new Hashtable<>();
				
				PurchaseOrders purchaseOrder = createPurchaseOrders(dispatch);
				purchaseOrders.add(purchaseOrder);
				
				String key = dispatch.getFromStoreId() + dispatch.getToStoreId();
				providerDispatchTable.put(key, purchaseOrder);
				purchaseOrdersGroups.put(dispatch.getProviderId(), providerDispatchTable);
			}
			else
			{
				String key = dispatch.getFromStoreId() + dispatch.getToStoreId();
				PurchaseOrders purchaseOrder = providerDispatchTable.get(key);
				if (purchaseOrder == null)
				{
					purchaseOrder = createPurchaseOrders(dispatch);
					purchaseOrders.add(purchaseOrder);
					
					providerDispatchTable.put(key, purchaseOrder);
				}
				else
				{
					purchaseOrder.addStockDispatch(dispatch);
				}
			}
		}
		
		logger.info("最后形成 " + purchaseOrders.size() + " 个PurchaseOrders.");
		
		for (PurchaseOrders purcOrder : purchaseOrders)
		{
			// 获取SN号码
			PurchaseOrdersSn sn = new PurchaseOrdersSn();
			sn.setPurchaseOrderId(purcOrder.getPurchaseOrderId());
			this.purchaseOrderSnMapper.insert(sn);
			
			// 得到SN号
			PurchaseOrdersSn querySn = this.purchaseOrderSnMapper.selectByPurchaseOrderId(purcOrder.getPurchaseOrderId());
			purcOrder.setOrderSn(querySn.getPurchaseOrderSn());
			logger.info("生成供应商采购单, " + querySn.getPurchaseOrderSn());
			
			this.purchaseOrderMapper.insertSelective(purcOrder);
			
			// 更新 门店库存调拨清单，更新 状态 和 PurchaseOrdersId
			ArrayList<StockDispatch> stockDispatchs = purcOrder.getStockDispatches();
			for (StockDispatch dispatch : stockDispatchs)
			{
				dispatch.setDispatchStatus(1);
				dispatch.setPurchaseOrderId(purcOrder.getPurchaseOrderId());
				this.stockDispatchMapper.updateStatus(dispatch);
			}
		}
		
		return getRetCodeWithJson(0, getMessage("masterStockDispatch0Action.auditSuccess"));
	}

	@Override
	public String masterStockDispatch1(HttpServletRequest request, HttpSession session) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		return "page/stock/master_stock_dispatch1";
	}

	@Override
	public String masterStockDispatch1Data(HttpServletRequest request, HttpSession session, PurchaseOrders purchaseOrders) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getTableData(null, "", 0);
		}

		logger.info("OrderStatus is " + purchaseOrders.getOrderStatus());
		
		PurchaseOrders param = new PurchaseOrders();
		param.setMerId(this.merUser.getMerId());
		if (purchaseOrders.getOrderStatus() == -1)
		{
			param.setOrderStatus(null);
		}
		else
		{
			param.setOrderStatus(purchaseOrders.getOrderStatus());
		}
		
		if (!StringUtils.isEmpty(purchaseOrders.getFromStoreId()))
		{
			// 当前门店店长查看从本店调出的单子
			param.setFromStoreId(this.merUser.getMerGroupId());
		}
		
		if (!StringUtils.isEmpty(purchaseOrders.getApplyId()))
		{
			// 当前门店店长查看调入本店的单子
			param.setApplyId(this.merUser.getMerGroupId());
		}
		
		Integer totalCount = this.purchaseOrderMapper.selectStoreDispatchCount(param);
		if (totalCount != null && totalCount > 0)
		{
			ArrayList<PurchaseOrders> storeDispatchOrders = this.purchaseOrderMapper.selectStoreDispatch(param);
			if (storeDispatchOrders != null && !storeDispatchOrders.isEmpty())
			{
				return getTableData(storeDispatchOrders, "", totalCount);
			}
		}
		
		return getTableData(null, "", 0);
	}

	@Override
	public String masterStockDispatch0Cancel(HttpServletRequest request, HttpSession session, Integer stockDispatchId) {
		
		if (!this.merUserHasAccessRight(request, session) || stockDispatchId == null)
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1, getMessage("masterStockDispatch0Cancel.CancelFailure"));
		}
		
		// 先找到原先库存明细，取消锁定
		StockDispatch dispatch = this.stockDispatchMapper.selectByPrimaryKey(stockDispatchId);
		
		GoodsStockDetail param = new GoodsStockDetail();
		param.setGoodsStockDetailId(dispatch.getGoodsStockDetailId());
		param.setLocked(false);
		
		this.goodsStockDetailMapper.lock(param);
		
		// 再删除该调拨计划
		this.stockDispatchMapper.deleteByPrimaryKey(stockDispatchId);
		
		return getRetCodeWithJson(0, getMessage("masterStockDispatch0Cancel.CancelSuccess"));
	}

	@Override
	public String masterStockDispatch2(HttpServletRequest request, HttpSession session, String purchaseOrderId) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		PurchaseOrders currentPurchaseOrders = this.purchaseOrderMapper.selectByPrimaryKey(purchaseOrderId);
		session.setAttribute("CurrentPurchaseOrders", currentPurchaseOrders);
		
		// 查询调出门店
		MerchantsGroups fromStore = this.merGroupMapper.selectByPrimaryKey(currentPurchaseOrders.getFromStoreId());
		session.setAttribute("FromStore", fromStore);
		
		// 查询调出门店店长
		MerchantUsers param = new MerchantUsers();
		param.setMerId(this.merUser.getMerId());
		param.setMerGroupId(currentPurchaseOrders.getFromStoreId());
		
		ArrayList<MerchantUsers> fromStoreMaster = this.merUserMapper.selectStoreMaster(param);
		if (fromStoreMaster != null && !fromStoreMaster.isEmpty())
		{
			session.setAttribute("FromStoreMaster", fromStoreMaster.get(0));
		}
		
		// 查询调入门店
		MerchantsGroups toStore = this.merGroupMapper.selectByPrimaryKey(currentPurchaseOrders.getApplyId());
		session.setAttribute("ToStore", toStore);
		
		// 查询调入门店店长
		param.setMerGroupId(currentPurchaseOrders.getApplyId());
		
		ArrayList<MerchantUsers> toStoreMaster = this.merUserMapper.selectStoreMaster(param);
		if (toStoreMaster != null && !toStoreMaster.isEmpty())
		{
			session.setAttribute("ToStoreMaster", toStoreMaster.get(0));
		}
		
		return "page/stock/master_stock_dispatch2";
	}

	@Override
	public String masterStockDispatchDetails(HttpServletRequest request, HttpSession session, String purchaseOrderId, String url) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		PurchaseOrders currentPurchaseOrders = this.purchaseOrderMapper.selectByPrimaryKey(purchaseOrderId);
		session.setAttribute("CurrentPurchaseOrders", currentPurchaseOrders);
		
		// 查询调出门店
		MerchantsGroups fromStore = this.merGroupMapper.selectByPrimaryKey(currentPurchaseOrders.getFromStoreId());
		session.setAttribute("FromStore", fromStore);
		
		// 查询调出门店店长
		MerchantUsers param = new MerchantUsers();
		param.setMerId(this.merUser.getMerId());
		param.setMerGroupId(currentPurchaseOrders.getFromStoreId());
		
		ArrayList<MerchantUsers> fromStoreMaster = this.merUserMapper.selectStoreMaster(param);
		if (fromStoreMaster != null && !fromStoreMaster.isEmpty())
		{
			session.setAttribute("FromStoreMaster", fromStoreMaster.get(0));
		}
		
		// 查询调入门店
		MerchantsGroups toStore = this.merGroupMapper.selectByPrimaryKey(currentPurchaseOrders.getApplyId());
		session.setAttribute("ToStore", toStore);
		
		// 查询调入门店店长
		param.setMerGroupId(currentPurchaseOrders.getApplyId());
		
		ArrayList<MerchantUsers> toStoreMaster = this.merUserMapper.selectStoreMaster(param);
		if (toStoreMaster != null && !toStoreMaster.isEmpty())
		{
			session.setAttribute("ToStoreMaster", toStoreMaster.get(0));
		}
		
		return "page/stock/" + url;
	}
	
	@Override
	public String masterStockDispatch2Data(HttpServletRequest request, HttpSession session) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getTableData(null, "", 0);
		}
		
		PurchaseOrders currentPurchaseOrders = (PurchaseOrders) session.getAttribute("CurrentPurchaseOrders");
		ArrayList<StockDispatch> dispatchList = this.stockDispatchMapper.selectByPurchaseOrder(currentPurchaseOrders.getPurchaseOrderId());
		
		return getTableData(dispatchList, "", dispatchList.size());
	}

	private boolean storeStockBatchUpdateCount(String stockOrderDetailIdStr, String goodsCountStr, StockOrders cuurentOrder)
	{
		String[] idArray = stockOrderDetailIdStr.split(",");
		String[] countArray = goodsCountStr.split(",");
		
		for (int i = 0; i < idArray.length; i++)
		{
			StockOrdersDetail detail = new StockOrdersDetail();
			detail.setStockOrderDetailId(Integer.parseInt(idArray[i]));
			detail.setGoodsCount(Integer.parseInt(countArray[i]));
			
			this.stockOrderDetailMapper.updateByPrimaryKeySelective(detail);
		}
		
		// 刷新采购单总数量和成本
		StockOrdersDetail orderDetailStat = this.stockOrderDetailMapper.statDetailOrder(cuurentOrder.getStockOrderId());
		if (orderDetailStat != null)
		{
			cuurentOrder.setGoodsCount(orderDetailStat.getGoodsCount());
			cuurentOrder.setGoodsTotalPrice(orderDetailStat.getGoodsTotalPrice());
		}
		else
		{
			cuurentOrder.setGoodsCount(0);
			cuurentOrder.setGoodsTotalPrice(0);
		}
		this.stockOrderMapper.updateGoodsCount(cuurentOrder);
		
		return true;
	}
	
	@Override
	public String storeStockBatchUpdateCount(HttpServletRequest request, HttpSession session,
			String stockOrderDetailIdStr, String goodsCountStr) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1, getMessage("storeStockBatchUpdateCount.BatchUpdateFailure"));
		}
		
		StockOrders cuurentOrder = (StockOrders) session.getAttribute("CurrentStockOrder");
		storeStockBatchUpdateCount(stockOrderDetailIdStr, goodsCountStr, cuurentOrder);
		
		return getRetCodeWithJson(0, getMessage("storeStockBatchUpdateCount.BatchUpdateSuccess"));
	}

	@Override
	public String masterStockDispatch2Action(HttpServletRequest request, HttpSession session) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1, getMessage("masterStockDispatch2Action.auditFailure"));
		}
		
		PurchaseOrders currentPurchaseOrders = (PurchaseOrders) session.getAttribute("CurrentPurchaseOrders");
		
		// 改变状态到 1，1：表示通过了门店调拨的终审，已经通知了供应商发货；并且货物调出门店店长可以看到该数据；
		currentPurchaseOrders.setOrderStatus(1);
		this.purchaseOrderMapper.changeOrderStatus(currentPurchaseOrders);
		
		return getRetCodeWithJson(0, getMessage("masterStockDispatch2Action.auditSuccess"));
	}

	@Override
	public String masterStockDispatch3(HttpServletRequest request, HttpSession session) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		return "page/stock/master_stock_dispatch3";
	}

	@Override
	public String masterStockDispatch3Action(HttpServletRequest request, HttpSession session) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1, getMessage("masterStockDispatch3Action.auditFailure"));
		}
		
		PurchaseOrders currentPurchaseOrders = (PurchaseOrders) session.getAttribute("CurrentPurchaseOrders");
		
		// 改变状态到 2，2：调出门店店长在供应商上门拿货时，在系统中做同意确认，状态变为2；
		currentPurchaseOrders.setOrderStatus(2);
		this.purchaseOrderMapper.changeOrderStatus(currentPurchaseOrders);
		
		return getRetCodeWithJson(0, getMessage("masterStockDispatch3Action.auditSuccess"));
	}

	@Override
	public String masterStockDispatch4(HttpServletRequest request, HttpSession session) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		return "page/stock/master_stock_dispatch4";
	}

	@Override
	public String masterStockDispatch4Action(HttpServletRequest request, HttpSession session) {

		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1, getMessage("masterStockDispatch4Action.auditFailure"));
		}
		
		PurchaseOrders currentPurchaseOrders = (PurchaseOrders) session.getAttribute("CurrentPurchaseOrders");

		/**
		 * 除了改变PurchaseOrder状态之外，还需要把 StockDispatch列表对应的GoodsStockDetail解锁，改变库存状态，刷新两个门店的库存数量
		 */
		// 创建一个GoodsStockBills入库批次
		logger.info("创建一个GoodsStockBills入库批次");
		GoodsStockBills stockBill = new GoodsStockBills();
		stockBill.setCreateDate(new Date());
		stockBill.setOwnerId(this.merUser.getMerGroupId());
		stockBill.setOwnerType(3);
		stockBill.setStockBillSource(1);
		stockBill.setStockBillType(0);
		stockBill.setStockBillStatus(0);
		stockBill.setStockBillId(MyUUID.randomUUID());
		stockBill.setPurchaseOrderId(currentPurchaseOrders.getPurchaseOrderId());
		
		int goodsTotalCount = 0;
		
		// 查询所有清单
		ArrayList<StockDispatch> dispatchList = this.stockDispatchMapper.selectByPurchaseOrder(currentPurchaseOrders.getPurchaseOrderId());
		for (StockDispatch dispatch : dispatchList)
		{
			goodsTotalCount += dispatch.getDispatchStockCount();
			
			// 查询出出库的门店库存明细
			GoodsStockDetail detail0 = this.goodsStockDetailMapper.selectByPrimaryKey(dispatch.getGoodsStockDetailId());
			
			// 1）修改库存数量, 2) 解锁
			detail0.setStockCount(detail0.getStockCount() - dispatch.getDispatchStockCount());
			detail0.setLocked(false);
			logger.info("修改调出门店的库存数量: " + detail0.getStockCount());
			this.goodsStockDetailMapper.updateByPrimaryKeySelective(detail0);
			
			// 刷新调出门店的总库存
			GoodsStock stock0 = new GoodsStock();
			stock0.setOwnerId(currentPurchaseOrders.getFromStoreId());
			stock0.setGoodsId(dispatch.getGoodsId());
			logger.info("刷新调出门店的总库存，" + currentPurchaseOrders.getFromStoreId());
			this.goodsStockMapper.statUpdateStoreStock(stock0);
			
			// 为调入门店新增入库记录
			GoodsStockDetail detail1 = new GoodsStockDetail();
			detail1.setStockBillId(stockBill.getStockBillId());
			detail1.setGoodsId(dispatch.getGoodsId());
			detail1.setOwnerId(this.merUser.getMerGroupId());
			detail1.setStockCount0(dispatch.getDispatchStockCount());
			detail1.setStockCount(dispatch.getDispatchStockCount());
			detail1.setGoodsCost(dispatch.getGoodsCost());
			detail1.setProductDate(dispatch.getProductDate());
			detail1.setExpiredDate(dispatch.getExpiredDate());
			detail1.setStockBillType(0);
			detail1.setStockDetailStatus(0);
			detail1.setLocked(false);
			detail1.setCreateDate(new Date());
			logger.info("为调入门店新增入库记录, count = " + detail1.getStockCount0());
			this.goodsStockDetailMapper.insertSelective(detail1);
			
			// 插入库存到调入门店中
			GoodsStock query = new GoodsStock();
			query.setGoodsId(dispatch.getGoodsId());
			query.setOwnerId(this.merUser.getMerGroupId());
			GoodsStock storeStock = this.goodsStockMapper.selectStoreStockByGoodsId2(query);
			if (storeStock == null)
			{
				// 插入库存数据
				GoodsStock stock = new GoodsStock();
				stock.setGoodsId(dispatch.getGoodsId());
				stock.setOwnerType(3);
				stock.setOwnerId(this.merUser.getMerGroupId());
				stock.setStockAmount(detail1.getStockCount0());
				
				this.goodsStockMapper.insertSelective(stock);
			}
			
			// 刷新调入门店的总库存
			GoodsStock stock1 = new GoodsStock();
			stock1.setOwnerId(currentPurchaseOrders.getApplyId());
			stock1.setGoodsId(dispatch.getGoodsId());
			logger.info("刷新调入门店的总库存, " + currentPurchaseOrders.getApplyId());
			this.goodsStockMapper.statUpdateStoreStock(stock1);
			
			// TODO 检查门店调入库存对应的商品是否在当前门店做了授权，如果没有做，则自动授权
			// 检查当前门店有没有做该商品的授权，如果没有，自动给予授权
			GoodsPrice priceQuery = new GoodsPrice();
			priceQuery.setGoodsId(dispatch.getGoodsId());
			priceQuery.setMerGroupId(this.merUser.getMerGroupId());
			GoodsPrice priceQuried = this.goodsPriceMapper.selectByStoreGoods(priceQuery);
			if (priceQuried == null)
			{
				// 未做授权，自动授权，门店销售价格采用商品零售价格
				GoodsInfo goodsInfo = this.goodsInfoMapper.selectByPrimaryKey(dispatch.getGoodsId());
				logger.info(dispatch.getGoodsName() + " 未做门店授权, " + this.merUser.getGroupName() + ",自动授权");
				priceQuery.setGoodsPrice(goodsInfo.getGoodsPrice());
				this.goodsPriceMapper.insertSelective(priceQuery);
			}
			
		}
		
		stockBill.setGoodsAmount(goodsTotalCount);
		this.goodsStockBillMapper.insertSelective(stockBill);
		
		// 改变状态到 3，3：调入门店店长签收后，状态变为3，调出门店原始库存发生变化，调入门店库存新增。状态3为最后一个状态。
		currentPurchaseOrders.setOrderStatus(3);
		this.purchaseOrderMapper.changeOrderStatus(currentPurchaseOrders);
		
		return getRetCodeWithJson(0, getMessage("masterStockDispatch4Action.auditSuccess"));
	}

	@Override
	public String purchaseOrdersReports(HttpServletRequest request, HttpSession session) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		return "page/reports/purchase_orders_reports";
	}

	@Override
	public String purchaseOrdersReportsData(HttpServletRequest request, HttpSession session, PurchaseOrders param) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getTableData(null, null, 0);
		}
		
		if (StringUtils.isEmpty(param.getStartDateTextParam()))
		{
			param.setStartDateTextParam(null);
		}
		if (StringUtils.isEmpty(param.getEndDateTextParam()))
		{
			param.setEndDateTextParam(null);
		}
		
		logger.info("createDate : " + param.getStartDateTextParam());
		logger.info("endDate : " + param.getEndDateTextParam());
		
		logger.info("page : " + param.getPage());
		logger.info("limit : " + param.getLimit());
		
		param.setMerId(this.merUser.getMerId());
		Integer totalCount = this.purchaseOrderMapper.selectAckedPurchaseOrdersCount(param);
		logger.info("totalCount = " + totalCount);
		
		if (totalCount != null && totalCount > 0)
		{
			ArrayList<PurchaseOrders> ackedPurchaseOrders = this.purchaseOrderMapper.selectAckedPurchaseOrders(param);
			if (ackedPurchaseOrders != null && !ackedPurchaseOrders.isEmpty())
			{
				return getTableData(ackedPurchaseOrders, null, totalCount);
			}
		}
		
		return getTableData(null, null, 0);
	}

	@Override
	public String purchaseOrdersReportsDataUpdate(HttpServletRequest request, HttpSession session) {
		
		PurchaseOrders param = new PurchaseOrders();
		param.setMerId(this.merUser.getMerId());
		Integer totalCount = this.purchaseOrderMapper.selectAckedPurchaseOrdersCount(param);
		
		if (totalCount != null && totalCount > 0)
		{
			ArrayList<PurchaseOrders> ackedPurchaseOrders = this.purchaseOrderMapper.selectAckedPurchaseOrders(param);
			if (ackedPurchaseOrders != null && !ackedPurchaseOrders.isEmpty())
			{
				for (PurchaseOrders purchaseOrder : ackedPurchaseOrders)
				{
					PurchaseOrdersDetail statAckedValue = this.purchaseOrderDetailMapper.statAckedTotalValue(purchaseOrder.getPurchaseOrderId());
					purchaseOrder.setAuditAmount(statAckedValue.getGoodsAckAmount());
					purchaseOrder.setAuditTotalPrice(statAckedValue.getGoodsCost());
					this.purchaseOrderMapper.updateAckAmount(purchaseOrder);
				}
			}
		}
		
		return getRetCodeWithJson(0, "数据刷新成功！");
	}

	@Override
	public String masterBatchStockOrderSelect(HttpServletRequest request, HttpSession session) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		return "page/stock/master_batch_stock_order_select";
	}

	@Override
	public String masterBatchStockOrderGoods(HttpServletRequest request, HttpSession session, String batchStockOrderId) {
		if (!this.merUserHasAccessRight(request, session) || StringUtils.isEmpty(batchStockOrderId))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		logger.info("batchStockOrderId = " + batchStockOrderId);
		BatchStockOrders currBatchStockOrders = this.batchStockOrdersMapper.selectByPrimaryKey(batchStockOrderId);
		if (currBatchStockOrders != null)
		{
			session.setAttribute("CurrBatchStockOrders", currBatchStockOrders);
		}
		
		// 加载商品大类
		ArrayList<GoodsType> topGoodsType = this.goodsTypeMapper.selectTopTypeByMerId(this.merUser.getMerId());
		if (topGoodsType != null && !topGoodsType.isEmpty())
		{
			session.setAttribute("TopGoodsTypes", topGoodsType);
		}
		
		return "page/stock/master_batch_stock_orders_goods";
	}

	@Override
	public String masterBatchStockOrders(HttpServletRequest request, HttpSession session) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		return "page/stock/master_batch_stock_orders";
	}

	@Override
	public String masterBatchStockOrdersData(HttpServletRequest request, HttpSession session, BatchStockOrders param) {
		if (!this.merUserHasAccessRight(request, session))
		{
			return getTableData(null, null, 0);
		}
		
		Integer dataCount = this.batchStockOrdersMapper.selectCountByMerId(this.merUser.getMerId());
		if (dataCount == null || dataCount == 0)
		{
			return getTableData(null, null, 0);
		}
		
		param.setMerId(this.merUser.getMerId());
		ArrayList<BatchStockOrders> batchStockOrders = this.batchStockOrdersMapper.selectAllByMerId(param);
		if (batchStockOrders != null && !batchStockOrders.isEmpty())
		{
			return getTableData(batchStockOrders, null, dataCount);
		}
		
		return getTableData(null, null, 0);
	}

	@Override
	public String masterBatchStockOrdersCreate(HttpServletRequest request, HttpSession session) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		BatchStockOrders batchStockOrders = new BatchStockOrders();
		
		batchStockOrders.setBatchStockOrderId(MyUUID.randomUUID());
		batchStockOrders.setCreateDate(new Date());
		batchStockOrders.setCreator(this.merUser.getUserName());
		batchStockOrders.setBatchOrderStatus(0);
		batchStockOrders.setMerId(this.merUser.getMerId());
		batchStockOrders.setGoodsCount(0);
		batchStockOrders.setStoresCount(0);
		
		this.batchStockOrdersMapper.insertSelective(batchStockOrders);
		
		return getRetCodeWithJson(0, "");
	}

	@Override
	public String masterBatchStockOrdersDelete(HttpServletRequest request, HttpSession session,
			String batchStockOrderId) {
		
		if (!this.merUserHasAccessRight(request, session) || StringUtils.isEmpty(batchStockOrderId))
		{
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		this.batchStockOrdersMapper.deleteByPrimaryKey(batchStockOrderId);
		this.batchStockStoresDetailsMapper.deleteByStockId(batchStockOrderId);
		this.batchStockGoodsDetailsMapper.deleteByStockId(batchStockOrderId);
		
		return getRetCodeWithJson(0, "");
	}

	@Override
	public String masterBatchStockOrderGoodsData(HttpServletRequest request, HttpSession session) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			return getTableData(null, null, 0);
		}
		
		BatchStockOrders currBatchStockOrders = (BatchStockOrders) session.getAttribute("CurrBatchStockOrders");
		
		ArrayList<BatchStockGoodsDetails> batchStockGoodsDetails = this.batchStockGoodsDetailsMapper.selectByStockId(currBatchStockOrders.getBatchStockOrderId());
		if (batchStockGoodsDetails != null && !batchStockGoodsDetails.isEmpty())
		{
			return getTableData(batchStockGoodsDetails, null, batchStockGoodsDetails.size());
		}
		
		return getTableData(null, null, 0);
	}

	@Override
	public String masterBatchStockInitGoods(HttpServletRequest request, HttpSession session, String goodsList) {
		
		if (!this.merUserHasAccessRight(request, session) || StringUtils.isEmpty(goodsList))
		{
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		String[] goodsArray = goodsList.split(",");
		if (goodsArray == null || goodsArray.length == 0)
		{
			return getRetCodeWithJson(1, "");
		}
		
		BatchStockOrders currBatchStockOrders = (BatchStockOrders) session.getAttribute("CurrBatchStockOrders");
		
		BatchStockGoodsDetails query = new BatchStockGoodsDetails();
		query.setBatchStockOrderId(currBatchStockOrders.getBatchStockOrderId());

		// 先删除存在的
		for (String goodsId : goodsArray)
		{
			query.setGoodsId(goodsId);
			this.batchStockGoodsDetailsMapper.deleteBy(query);
			query.setProviderCount(0);
			// 查找供应商
			ArrayList<GoodsProviders> goodsProviders = this.goodsProviderMapper.selectGoodsWithProvider(goodsId);
			if (goodsProviders != null && !goodsProviders.isEmpty())
			{
				query.setProviderCount(goodsProviders.size());
				if (goodsProviders.size() == 1)
				{
					query.setProviderId(goodsProviders.get(0).getMerUserId());
					
					String providerName = goodsProviders.get(0).getUserName();
					providerName += "[" + goodsProviders.get(0).getMerName() + "]";
					query.setProviderName(providerName);
					query.setProviderCost(goodsProviders.get(0).getGoodsCost());
					query.setProviderUnit(goodsProviders.get(0).getProviderUnit());
				}
				else
				{
					query.setProviderId("");
					query.setProviderName("");
				}
			}
			
			// 新增商品，默认数量为1
			this.batchStockGoodsDetailsMapper.insertGoods(query);
			
			// 更新批量采购单商品数量
			this.batchStockOrdersMapper.updateGoodsCount(currBatchStockOrders.getBatchStockOrderId());
		}
		
		return getRetCodeWithJson(0, "");
	}

	@Override
	public String masterBatchStockOrderGoodsDel(HttpServletRequest request, HttpSession session,
			BatchStockGoodsDetails batchStockGoodsDetails) {
		
		if (!this.merUserHasAccessRight(request, session) || batchStockGoodsDetails.getBatchStockGoodsId() == null)
		{
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		this.batchStockGoodsDetailsMapper.deleteByPrimaryKey(batchStockGoodsDetails.getBatchStockGoodsId());
		
		// 更新批量采购单商品数量
		this.batchStockOrdersMapper.updateGoodsCount(batchStockGoodsDetails.getBatchStockOrderId());
		
		return getRetCodeWithJson(0, "");
	}

	@Override
	public String masterBatchStockOrderGoodsCount(HttpServletRequest request, HttpSession session,
			String batchStockGoodsIdStr, String goodsCountStr) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		if (StringUtils.isEmpty(batchStockGoodsIdStr) || StringUtils.isEmpty(goodsCountStr))
		{
			return getRetCodeWithJson(1, getMessage("masterBatchStockOrderGoodsCount.goodsCountNull"));
		}
		
		String[] ids = batchStockGoodsIdStr.split(",");
		String[] counts = goodsCountStr.split(",");
				
		for (int i = 0; i < ids.length; i++)
		{
			Integer batchStockGoodsId = Integer.parseInt(ids[i]);
			Integer goodsCount = Integer.parseInt(counts[i]);
			
			BatchStockGoodsDetails query = new BatchStockGoodsDetails();
			query.setBatchStockGoodsId(batchStockGoodsId);
			query.setGoodsCount(goodsCount);
			
			this.batchStockGoodsDetailsMapper.updateCount(query);
		}
		
		BatchStockOrders currBatchStockOrders = (BatchStockOrders) session.getAttribute("CurrBatchStockOrders");
		
		// 更新批量采购单商品数量
		this.batchStockOrdersMapper.updateGoodsCount(currBatchStockOrders.getBatchStockOrderId());
		
		return getRetCodeWithJson(0, getMessage("masterBatchStockOrderGoodsCount.changeGoodsCountSuccess"));
	}

	@Override
	public String masterBatchStockSetGoodsProvider(HttpServletRequest request, HttpSession session, BatchStockGoodsDetails param) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		logger.info("providerId = " + param.getProviderId());
		logger.info("providerName = " + param.getProviderName());
		
		BatchStockGoodsDetails detail = new BatchStockGoodsDetails();
		detail.setBatchStockGoodsId(param.getBatchStockGoodsId());
		detail.setProviderId(param.getProviderId());
		detail.setProviderName(param.getProviderName());
		detail.setProviderCost(param.getProviderCost());
		detail.setProviderUnit(param.getProviderUnit());
		
		this.batchStockGoodsDetailsMapper.updateProvider(detail);
		
		// 更新批量采购单商品数量
		BatchStockOrders currBatchStockOrders = (BatchStockOrders) session.getAttribute("CurrBatchStockOrders");
		this.batchStockOrdersMapper.updateGoodsCount(currBatchStockOrders.getBatchStockOrderId());
		
		return getRetCodeWithJson(0, "");
	}

	@Override
	public String masterBatchStockOrderStores(HttpServletRequest request, HttpSession session,
			String batchStockOrderId) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		logger.info("batchStockOrderId = " + batchStockOrderId);
		BatchStockOrders currBatchStockOrders = this.batchStockOrdersMapper.selectByPrimaryKey(batchStockOrderId);
		if (currBatchStockOrders != null)
		{
			session.setAttribute("CurrBatchStockOrders", currBatchStockOrders);
		}
		
		// 查询地区
		// 加载所有顶级地区
		ArrayList<Areas> areas = this.areaMapper.selectTopAreas();
		if (areas != null && !areas.isEmpty())
		{
			session.setAttribute("Provinces", areas);
		}
		
		return "page/stock/master_batch_stock_orders_stores";
	}

	@Override
	public String masterBatchStockOrderStoresData(HttpServletRequest request, HttpSession session,
			String batchStockOrderId) {
		if (!this.merUserHasAccessRight(request, session))
		{
			return getTableData(null, null, 0);
		}
		
		BatchStockOrders currBatchStockOrders = (BatchStockOrders) session.getAttribute("CurrBatchStockOrders");
		
		ArrayList<BatchStockStoreDetails> stores = this.batchStockStoresDetailsMapper.selectByStockOrderId(currBatchStockOrders.getBatchStockOrderId());
		if (stores != null && !stores.isEmpty())
		{
			return getTableData(stores, null, stores.size());
		}
		
		return getTableData(null, null, 0);
	}

	@Override
	public String masterBatchStockOrderStoresSave(HttpServletRequest request, HttpSession session, String storeIds) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		if (StringUtils.isEmpty(storeIds))
		{
			return getRetCodeWithJson(1, getMessage("masterBatchStockOrderStoresSave.storesIsNull"));
		}
		
		String[] groupIds = storeIds.split(",");
		if (groupIds == null || groupIds.length == 0)
		{
			return getRetCodeWithJson(1, getMessage("masterBatchStockOrderStoresSave.storesIsNull"));
		}
		
		BatchStockOrders currBatchStockOrders = (BatchStockOrders) session.getAttribute("CurrBatchStockOrders");
		
		BatchStockStoreDetails storeParam = new BatchStockStoreDetails();
		storeParam.setBatchStockOrderId(currBatchStockOrders.getBatchStockOrderId());

		// 先删除相同的
		for (String groupId : groupIds)
		{
			storeParam.setMerGroupId(groupId);
			this.batchStockStoresDetailsMapper.deleteByStore(storeParam);
			
			MerchantsGroups store = this.merGroupMapper.selectByPrimaryKey(groupId);
			// 再新增进去
			storeParam.setGroupName(store.getGroupName());
			
			this.batchStockStoresDetailsMapper.insertSelective(storeParam);
		}
		
		// 统计门店参与的个数
		this.batchStockOrdersMapper.updateStoresCount(currBatchStockOrders.getBatchStockOrderId());
		
		return getRetCodeWithJson(0, "");
	}

	@Override
	public String masterBatchStockOrderStoresRemove(HttpServletRequest request, HttpSession session,
			BatchStockStoreDetails storeDetails) {
		if (!this.merUserHasAccessRight(request, session))
		{
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		BatchStockOrders currBatchStockOrders = (BatchStockOrders) session.getAttribute("CurrBatchStockOrders");
		
		this.batchStockStoresDetailsMapper.deleteByPrimaryKey(storeDetails.getBatchStockStoreId());
		
		// 统计门店参与的个数
		this.batchStockOrdersMapper.updateStoresCount(currBatchStockOrders.getBatchStockOrderId());
				
		return  getRetCodeWithJson(0, "");
	}

	@Override
	public String masterBatchStockOrderSubmit(HttpServletRequest request, HttpSession session,
			String batchStockOrderId) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		// 派发完成后，进入门店收货确认状态
		logger.info("批量派发采购单... " + batchStockOrderId );
		BatchStockOrders currBatchStockOrders = this.batchStockOrdersMapper.selectByPrimaryKey(batchStockOrderId);
		if (currBatchStockOrders == null)
		{
			return getRetCodeWithJson(1, getMessage("masterBatchStockOrderSubmit.submitNoSuchOrder"));
		}
		
		if (currBatchStockOrders.getBatchOrderStatus() == 1)
		{
			return getRetCodeWithJson(1, getMessage("masterBatchStockOrderSubmit.batchOrderSubmitted"));
		}
		
		// 先对商品按照供应商进行分组
		ArrayList<BatchStockGoodsDetails> goodsDetails = this.batchStockGoodsDetailsMapper.selectByStockId(batchStockOrderId);
		if (goodsDetails == null || goodsDetails.isEmpty())
		{
			return getRetCodeWithJson(1, getMessage("masterBatchStockOrderGoodsCount.goodsCountNull"));
		}
		
		boolean providerIsReady = true;
		// 遍历是否都设置了供应商
		for (BatchStockGoodsDetails goodsDetail : goodsDetails)
		{
			if (StringUtils.isEmpty(goodsDetail.getProviderId()))
			{
				providerIsReady = false;
				break;
			}
		}
		
		if (!providerIsReady)
		{
			return getRetCodeWithJson(1, getMessage("masterBatchStockOrderSubmit.providerNotNull"));
		}
		
		ArrayList<BatchStockStoreDetails> storeDetails = this.batchStockStoresDetailsMapper.selectByStockOrderId(batchStockOrderId);
		if (storeDetails == null || storeDetails.isEmpty())
		{
			return getRetCodeWithJson(1, getMessage("masterBatchStockOrderStoresSave.storesIsNull"));
		}
		
		// 先锁定
		currBatchStockOrders.setBatchOrderStatus(1);
		this.batchStockOrdersMapper.changeBatchOrderStatus(currBatchStockOrders);
		
		Hashtable<String, ArrayList<BatchStockGoodsDetails>> goodsDetailsTable = new Hashtable<>();
		
		for (BatchStockGoodsDetails goodsDetail : goodsDetails)
		{
			ArrayList<BatchStockGoodsDetails> goodsGroups = goodsDetailsTable.get(goodsDetail.getProviderId());
			
			if (goodsGroups == null)
			{
				goodsGroups = new ArrayList<>();
				goodsGroups.add(goodsDetail);
				goodsDetailsTable.put(goodsDetail.getProviderId(), goodsGroups);
			}
			else
			{
				goodsGroups.add(goodsDetail);
			}
		}
		
		// 生成 采购单 STOCK_ORDERS
		for (BatchStockStoreDetails storeDetail : storeDetails)
		{
			logger.info("为门店派发采购单 " + storeDetail.getGroupName());
			
			StockOrders stockOrder = new StockOrders();
			
			stockOrder.setStockOrderId(MyUUID.randomUUID());
			stockOrder.setStockOrderSn(getStockOrderSN(stockOrder.getStockOrderId()));
			stockOrder.setMerId(this.merUser.getMerId());
			stockOrder.setApplyId(storeDetail.getMerGroupId());
			
			MerchantsGroups store = this.merGroupMapper.selectByPrimaryKey(storeDetail.getMerGroupId());
			Areas area = this.areaMapper.selectByPrimaryKey(store.getAreaId());
			
			// 查询门店详细地址
			stockOrder.setApplyAddress(area.getFullName() + "," + store.getDetailAddress());
			
			stockOrder.setCreateDate(new Date());
			stockOrder.setCreator(this.merUser.getUserName());
			stockOrder.setProviderCount(1);
			
			// 这里的状态直接跳到 3，完成确认，给供货商下单
			stockOrder.setStockOrderStatus(3);
			stockOrder.setStockOrderMemo(IOUtils.LINE_SEPARATOR + this.merUser.getUserName() + " 于 " + DateFormatUtils.format(stockOrder.getCreateDate(), "yyyy-MM-dd HH:mm:ss") + " 创建了进货申请单");
			
			// 查询门店店长信息
			MerchantUsers storeMasterQuery = new MerchantUsers();
			storeMasterQuery.setMerId(this.merUser.getMerId());
			storeMasterQuery.setMerGroupId(storeDetail.getMerGroupId());
			
			ArrayList<MerchantUsers> storeMasters = this.merUserMapper.selectStoreMaster(storeMasterQuery);
			if (storeMasters == null || storeMasters.isEmpty())
			{
				stockOrder.setLinkMan(this.merUser.getUserName());
				stockOrder.setLinkManPhone(this.merUser.getUserPhone());
			}
			else
			{
				stockOrder.setLinkMan(storeMasters.get(0).getUserName());
				stockOrder.setLinkManPhone(storeMasters.get(0).getUserPhone());
			}
			
			// 商品采购数量
			stockOrder.setGoodsCount(currBatchStockOrders.getGoodsCount());
			stockOrder.setGoodsTotalPrice(currBatchStockOrders.getGoodsTotalPrice());
			
			// 保存到数据库中
			this.stockOrderMapper.insertSelective(stockOrder);
			
			Hashtable<String, StockOrdersDetail> stockOrderDetailsList = new Hashtable<>();
			
			// 生成采购单明细
			logger.info("生成采购单明细...");
			for (BatchStockGoodsDetails goodsDetail : goodsDetails)
			{
				GoodsInfo goodsInfo = this.goodsInfoMapper.selectByPrimaryKey(goodsDetail.getGoodsId());
				
				// 检查当前门店有没有做该商品的授权，如果没有，自动给予授权
				GoodsPrice priceQuery = new GoodsPrice();
				priceQuery.setGoodsId(goodsDetail.getGoodsId());
				priceQuery.setMerGroupId(storeDetail.getMerGroupId());
				GoodsPrice priceQuried = this.goodsPriceMapper.selectByStoreGoods(priceQuery);
				if (priceQuried == null)
				{
					// 未做授权，自动授权，门店销售价格采用商品零售价格
					logger.info(goodsInfo.getGoodsName() + " 未做门店授权, " + storeDetail.getGroupName() + ",自动授权");
					priceQuery.setGoodsPrice(goodsInfo.getGoodsPrice());
					this.goodsPriceMapper.insertSelective(priceQuery);
				}
				
				StockOrdersDetail stockOrdersDetail = new StockOrdersDetail();
				
				stockOrdersDetail.setGoodsTypeId(goodsInfo.getGoodsTypeId());
				stockOrdersDetail.setGoodsId(goodsDetail.getGoodsId());
				stockOrdersDetail.setStockOrderId(stockOrder.getStockOrderId());
				stockOrdersDetail.setGoodsCount(goodsDetail.getGoodsCount());
				stockOrdersDetail.setDealType(0);
				stockOrdersDetail.setToProviderId(goodsDetail.getProviderId());
				stockOrdersDetail.setToProviderName(goodsDetail.getProviderName());
				stockOrdersDetail.setProviderCost(goodsDetail.getProviderCost());
				stockOrdersDetail.setProviderCount(goodsDetail.getProviderCount());
				stockOrdersDetail.setProviderUnit(goodsInfo.getProviderUnit());
				stockOrdersDetail.setApplyId(storeDetail.getMerGroupId());
				stockOrdersDetail.setGoodsTotalPrice(goodsDetail.getGoodsCount() * goodsDetail.getProviderCost());
				
				this.stockOrderDetailMapper.insertSelective(stockOrdersDetail);
				
				StockOrdersDetail queried = this.stockOrderDetailMapper.findByGoodsId(stockOrdersDetail);
				if (queried != null)
				{
					stockOrdersDetail.setStockOrderDetailId(queried.getStockOrderDetailId());
				}
				stockOrderDetailsList.put(goodsDetail.getGoodsId(), stockOrdersDetail);
			}
			
			// 生成供应商的配送单 PURCHASE_ORDERS
			logger.info("生成供应商的配送单 PURCHASE_ORDERS...");
			Iterator<String> iter = goodsDetailsTable.keySet().iterator();
			while(iter.hasNext())
			{
				String providerId = iter.next();
				ArrayList<BatchStockGoodsDetails> goodsGroups = goodsDetailsTable.get(providerId);
				
				PurchaseOrders purchaseOrder = new PurchaseOrders();
				
				purchaseOrder.setPurchaseOrderId(MyUUID.randomUUID());
				// 获取SN号码
				PurchaseOrdersSn sn = new PurchaseOrdersSn();
				sn.setPurchaseOrderId(purchaseOrder.getPurchaseOrderId());
				this.purchaseOrderSnMapper.insert(sn);
				
				purchaseOrder.setMerId(this.merUser.getMerId());
				
				// 得到SN号
				PurchaseOrdersSn querySn = this.purchaseOrderSnMapper.selectByPurchaseOrderId(purchaseOrder.getPurchaseOrderId());
				purchaseOrder.setOrderSn(querySn.getPurchaseOrderSn());
				
				purchaseOrder.setStockOrderId(stockOrder.getStockOrderId());
				purchaseOrder.setDealType(0);
				purchaseOrder.setCreateDate(new Date());
				purchaseOrder.setCreator(this.merUser.getUserName());
				
				// 供应商信息
				MerchantUsers provider = this.merUserMapper.selectByPrimaryKey(providerId);
				purchaseOrder.setProviderId(providerId);
				purchaseOrder.setProviderLinkman(provider.getUserName());
				purchaseOrder.setProviderLinkmanPhone(provider.getUserPhone());
				purchaseOrder.setProviderLinkmanEmail(provider.getUserEmail());
				purchaseOrder.setProviderName(provider.getMerName());
				
				// 订货门店信息
				purchaseOrder.setApplyId(storeDetail.getMerGroupId());
				purchaseOrder.setLinkMan(stockOrder.getLinkMan()); 
				purchaseOrder.setLinkManPhone(stockOrder.getLinkManPhone());
				purchaseOrder.setApplyAddress(stockOrder.getApplyAddress());
				
				int goodsAmount = 0;
				int goodsTotalPrice = 0;
				for (BatchStockGoodsDetails goodsDetail : goodsGroups)
				{
					goodsAmount += goodsDetail.getGoodsCount();
					goodsTotalPrice += goodsDetail.getGoodsCount() * goodsDetail.getProviderCost();
				}
				purchaseOrder.setGoodsAmount(goodsAmount);
				purchaseOrder.setGoodsTotalPrice(goodsTotalPrice);
				
				// 已生效
				purchaseOrder.setOrderStatus(0);
				
				this.purchaseOrderMapper.insertSelective(purchaseOrder);
				
				logger.info("生成采购单明细, PurchaseOrdersDetail");
				// 生成采购单明细
				for (BatchStockGoodsDetails goodsDetail : goodsGroups)
				{
					PurchaseOrdersDetail purchaseDetail = new PurchaseOrdersDetail();
					purchaseDetail.setPurchaseOrderId(purchaseOrder.getPurchaseOrderId());
					purchaseDetail.setGoodsId(goodsDetail.getGoodsId());
					purchaseDetail.setGoodsCost(goodsDetail.getProviderCost());
					purchaseDetail.setGoodsAmount(goodsDetail.getGoodsCount());
					purchaseDetail.setProviderAckAmount(goodsDetail.getGoodsCount());
					purchaseDetail.setAckStatus(0);
					
					StockOrdersDetail stockOrdersDetail = stockOrderDetailsList.get(goodsDetail.getGoodsId());
					if (stockOrdersDetail != null)
					{
						purchaseDetail.setProviderUnit(stockOrdersDetail.getProviderUnit());
						purchaseDetail.setStockOrderDetailId(stockOrdersDetail.getStockOrderDetailId());
					}
					
					purchaseDetail.setStockOrderId(stockOrder.getStockOrderId());
					
					this.purchaseOrderDetailMapper.insertSelective(purchaseDetail);
				}
				
			}
			
		}
		
		return getRetCodeWithJson(0, getMessage("masterBatchStockOrderSubmit.submitSuccess"));
	}

	@Override
	public String storeGoodsStockChange(HttpServletRequest request, HttpSession session, GoodsStock goodsStock) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		if (goodsStock.getStockAmount() == null || goodsStock.getStockAmount() < 0)
		{
			return getRetCodeWithJson(1, getMessage("storeGoodsStockChange.stockCountIsNull"));
		}
		
		logger.info("GoodsStockId = " + goodsStock.getGoodsStockId());
		logger.info("StockAmount = " + goodsStock.getStockAmount());
		logger.info("StockAmount0 = " + goodsStock.getStockAmount0());
		logger.info("goodsId = " + goodsStock.getGoodsId());
		
		int changeValue = goodsStock.getStockAmount() - goodsStock.getStockAmount0();
		if (changeValue > 0)
		{
			logger.info("实际库存比系统库存多 " + changeValue);
		}
		else
		{
			logger.info("实际库存比系统库存少 " + (-changeValue));
		}
		
		MerchantsGroups currStore = (MerchantsGroups) session.getAttribute("CurrentStore");
		
		// 查询所有库存明细
		GoodsStockDetail query = new GoodsStockDetail();
		query.setGoodsId(goodsStock.getGoodsId());
		query.setOwnerId(currStore.getMerGroupId());
		
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
		goodsStock.setOwnerId(currStore.getMerGroupId());
		this.goodsStockMapper.statUpdateStoreStock(goodsStock);
		
		return getRetCodeWithJson(0, getMessage("storeGoodsStockChange.changeSuccess"));
	}
	
}
