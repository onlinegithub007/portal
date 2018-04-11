package com.fuhuitong.applychain.model;

import com.fuhuitong.applychain.utils.MoneyUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.ArrayList;
import java.util.Date;

public class StockOrders extends Pagable{
	
    private String stockOrderId;

    private Integer stockOrderSn;

    private String merId;

    private Date createDate;
    
    private String createDateText;
    
    private String endDateText;

    private String orderTitle;
    
    private String applyId;

    private String creator;

    private String linkMan;

    private String linkManPhone;

    private String applyAddress;

    private Integer goodsCount;

    private Integer goodsTotalPrice;
    
    private String goodsTotalPriceText;
    
    private Integer ackGoodsCount;

    private Integer ackGoodsTotalPrice;
    
    private String ackGoodsTotalPriceText;

    private Integer stockOrderStatus;

    private Integer activeStatus;

    private Date opAudit1Date;

    private Date opAudit2Date;

    private Date finalAckDate;

    private Date purchaseDate;
    
    private String purchaseDateText;
    
    private String stockOrderMemo;
    
    private Integer providerCount;
    
    private Integer storeCount;
    
    private String groupName;
    
    private Boolean auditMode;
    
    private Integer providerAckStatus;
    
    private ArrayList<PurchaseOrders> purchaseOrdersProvider = new ArrayList<PurchaseOrders>();
    
    private ArrayList<PurchaseOrders> purchaseOrdersStore = new ArrayList<PurchaseOrders>();

    public String getStockOrderId() {
        return stockOrderId;
    }

    public void setStockOrderId(String stockOrderId) {
        this.stockOrderId = stockOrderId == null ? null : stockOrderId.trim();
    }

    public Integer getStockOrderSn() {
        return stockOrderSn;
    }

    public void setStockOrderSn(Integer stockOrderSn) {
        this.stockOrderSn = stockOrderSn;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId == null ? null : merId.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan == null ? null : linkMan.trim();
    }

    public String getLinkManPhone() {
        return linkManPhone;
    }

    public void setLinkManPhone(String linkManPhone) {
        this.linkManPhone = linkManPhone == null ? null : linkManPhone.trim();
    }

    public String getApplyAddress() {
        return applyAddress;
    }

    public void setApplyAddress(String applyAddress) {
        this.applyAddress = applyAddress == null ? null : applyAddress.trim();
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public Integer getGoodsTotalPrice() {
        return goodsTotalPrice;
    }

    public void setGoodsTotalPrice(Integer goodsTotalPrice) {
        this.goodsTotalPrice = goodsTotalPrice;
    }

    public void setStockOrderMemo(String stockOrderMemo) {
		this.stockOrderMemo = stockOrderMemo;
	}
    
    public String getStockOrderMemo() {
		return stockOrderMemo;
	}

    public Integer getStockOrderStatus() {
        return stockOrderStatus;
    }

    public void setStockOrderStatus(Integer stockOrderStatus) {
        this.stockOrderStatus = stockOrderStatus;
    }

    public Integer getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Integer activeStatus) {
        this.activeStatus = activeStatus;
    }

    public Date getOpAudit1Date() {
        return opAudit1Date;
    }

    public void setOpAudit1Date(Date opAudit1Date) {
        this.opAudit1Date = opAudit1Date;
    }

    public Date getOpAudit2Date() {
        return opAudit2Date;
    }

    public void setOpAudit2Date(Date opAudit2Date) {
        this.opAudit2Date = opAudit2Date;
    }

    public Date getFinalAckDate() {
        return finalAckDate;
    }

    public void setFinalAckDate(Date finalAckDate) {
        this.finalAckDate = finalAckDate;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
    
    public String getCreateDateText() {
		
    	if (this.createDate != null)
    	{
    		createDateText = DateFormatUtils.format(createDate, "yyyy-MM-dd HH:mm:ss");
    	}
    	return createDateText;
	}
    
    public void setCreateDateText(String createDateText) {
		this.createDateText = createDateText;
	}
    
    public String getPurchaseDateText() {
    	if (this.purchaseDate != null)
    	{
    		purchaseDateText = DateFormatUtils.format(purchaseDate, "yyyy-MM-dd HH:mm:ss");
    	}
    	
    	return purchaseDateText;
	}
    
    public void setPurchaseDateText(String purchaseDateText) {
		this.purchaseDateText = purchaseDateText;
	}
    
    public String getOrderTitle() {
		return orderTitle;
	}
    
    public void setOrderTitle(String orderTitle) {
		this.orderTitle = orderTitle;
	}

	public Integer getProviderCount() {
		return providerCount;
	}

	public void setProviderCount(Integer providerCount) {
		this.providerCount = providerCount;
	}

	public Integer getStoreCount() {
		return storeCount;
	}

	public void setStoreCount(Integer storeCount) {
		this.storeCount = storeCount;
	}
	
	public ArrayList<PurchaseOrders> getPurchaseOrdersProvider() {
		return purchaseOrdersProvider;
	}
	
	public void addPurchaseOrdersProvider(PurchaseOrders purchaseOrder)
	{
		purchaseOrdersProvider.add(purchaseOrder);
	}
	
	public ArrayList<PurchaseOrders> getPurchaseOrdersStore() {
		return purchaseOrdersStore;
	}
	
	public void addPurchaseOrdersStore(PurchaseOrders purchaseOrder)
	{
		purchaseOrdersStore.add(purchaseOrder);
	}
	
	public void setAuditMode(Boolean auditMode) {
		this.auditMode = auditMode;
	}
	
	public Boolean getAuditMode() {
		return auditMode;
	}
	
	public void setEndDateText(String endDateText) {
		this.endDateText = endDateText;
	}
	
	public String getEndDateText() {
		return endDateText;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public String getGroupName() {
		return groupName;
	}
	
	public void setAckGoodsCount(Integer ackGoodsCount) {
		this.ackGoodsCount = ackGoodsCount;
	}
	
	public Integer getAckGoodsCount() {
		return ackGoodsCount;
	}
	
	public void setAckGoodsTotalPrice(Integer ackGoodsTotalPrice) {
		this.ackGoodsTotalPrice = ackGoodsTotalPrice;
	}
	
	public Integer getAckGoodsTotalPrice() {
		return ackGoodsTotalPrice;
	}
	
	
	public String getGoodsTotalPriceText() {
		if (goodsTotalPrice != null)
		{
			goodsTotalPriceText = MoneyUtils.getMoneyText(goodsTotalPrice);
		}
		else
		{
			goodsTotalPriceText = "";
		}
		return goodsTotalPriceText;
	}

	public void setGoodsTotalPriceText(String goodsTotalPriceText) {
		this.goodsTotalPriceText = goodsTotalPriceText;
	}

	public String getAckGoodsTotalPriceText() {
		if (ackGoodsTotalPrice != null)
		{
			ackGoodsTotalPriceText = MoneyUtils.getMoneyText(ackGoodsTotalPrice);
		}
		else
		{
			ackGoodsTotalPriceText = "";
		}
		return ackGoodsTotalPriceText;
	}

	public void setAckGoodsTotalPriceText(String ackGoodsTotalPriceText) {
		this.ackGoodsTotalPriceText = ackGoodsTotalPriceText;
	}

	public Integer getProviderAckStatus() {
		return providerAckStatus;
	}

	public void setProviderAckStatus(Integer providerAckStatus) {
		this.providerAckStatus = providerAckStatus;
	}

	public String getStepByStatus(int step)
    {
    	String stepStr = "";
    	
    	if (this.stockOrderStatus < 5)
    	{
    		if (this.stockOrderStatus >= step)
    		{
    			stepStr = " step-done ";
    		}
    		else
    		{
    			stepStr = " step-active ";
    		}
    	}
    	else
    	{
    		stepStr = " step-done ";
    	}
    	
    	return stepStr;
    }
}