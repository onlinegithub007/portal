package com.fuhuitong.applychain.model;

import com.fuhuitong.applychain.utils.MoneyUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.ArrayList;
import java.util.Date;

public class PurchaseOrders extends Pagable{
    private String purchaseOrderId;

    private Integer orderSn;

    private String stockOrderId;
    
    private String merId;

    private Integer dealType;
    
    private Integer storeAck;
    
    private Date storeAckDate;
    
    private String fromStoreId;

    private String fromStoreName;
    
    private String storeAckDateText;

    private Date createDate;
    
    private String createDateText;
    
    private String endDateText;

    private String creator;

    private String providerId;
    
    private String providerLinkman;

    private String providerLinkmanPhone;

    private String providerLinkmanEmail;
    
    private String providerName;
    
    private Integer providerAckStatus;

    private Date providerAckDate;
    
    private String providerAckDateText;

    private Integer providerAckAudit;

    private Date providerAckAuditDate;
    
    private String providerAckAuditDateText;

    private String applyId;
    
    private String groupName;
    
    private String linkMan;

    private String linkManPhone;
    
    private String applyAddress;

    private Integer goodsAmount;

    private Integer goodsTotalPrice;
    
    private String goodsTotalPriceText;

    private Integer auditAmount;

    private Integer auditTotalPrice;
    
    private String auditTotalPriceText;

    private Date payPlaneDate;

    private Date paiedDate;

    private Integer applyCheckStatus;

    private String applyCheckor;

    private Integer auditStatus;

    private Integer payStatus;

    private String opAudit1Id;

    private Date opAudit1Date;

    private String opAudit2Id;

    private Date opAudit2Date;

    private String finalAuditId;

    private Date finalAuditDate;

    private Date finalAckDate;

    private String finalAckId;

    private String finalAckDemo;

    private Integer orderStatus;
    
    private ArrayList<PurchaseOrdersDetail> orderDetails;
    
    private ArrayList<StockDispatch> stockDispatches;
    
    private String startDateTextParam;
    private String endDateTextParam;
    
    public String getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(String purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId == null ? null : purchaseOrderId.trim();
    }

    public Integer getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(Integer orderSn) {
        this.orderSn = orderSn;
    }

    public String getStockOrderId() {
        return stockOrderId;
    }

    public void setStockOrderId(String stockOrderId) {
        this.stockOrderId = stockOrderId == null ? null : stockOrderId.trim();
    }

    public Integer getDealType() {
        return dealType;
    }

    public void setDealType(Integer dealType) {
        this.dealType = dealType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId == null ? null : providerId.trim();
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

    public Integer getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(Integer goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public Integer getGoodsTotalPrice() {
        return goodsTotalPrice;
    }

    public void setGoodsTotalPrice(Integer goodsTotalPrice) {
        this.goodsTotalPrice = goodsTotalPrice;
    }

    public Integer getAuditAmount() {
        return auditAmount;
    }

    public void setAuditAmount(Integer auditAmount) {
        this.auditAmount = auditAmount;
    }

    public Integer getAuditTotalPrice() {
        return auditTotalPrice;
    }

    public void setAuditTotalPrice(Integer auditTotalPrice) {
        this.auditTotalPrice = auditTotalPrice;
    }
    
    public String getAuditTotalPriceText() {
		if (this.auditTotalPrice != null)
		{
			auditTotalPriceText = MoneyUtils.getMoneyText(auditTotalPrice);
		}
    	
    	return auditTotalPriceText;
	}

    public Date getPayPlaneDate() {
        return payPlaneDate;
    }

    public void setPayPlaneDate(Date payPlaneDate) {
        this.payPlaneDate = payPlaneDate;
    }

    public Date getPaiedDate() {
        return paiedDate;
    }

    public void setPaiedDate(Date paiedDate) {
        this.paiedDate = paiedDate;
    }

    public Integer getApplyCheckStatus() {
        return applyCheckStatus;
    }

    public void setApplyCheckStatus(Integer applyCheckStatus) {
        this.applyCheckStatus = applyCheckStatus;
    }

    public String getApplyCheckor() {
        return applyCheckor;
    }

    public void setApplyCheckor(String applyCheckor) {
        this.applyCheckor = applyCheckor == null ? null : applyCheckor.trim();
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getOpAudit1Id() {
        return opAudit1Id;
    }

    public void setOpAudit1Id(String opAudit1Id) {
        this.opAudit1Id = opAudit1Id == null ? null : opAudit1Id.trim();
    }

    public Date getOpAudit1Date() {
        return opAudit1Date;
    }

    public void setOpAudit1Date(Date opAudit1Date) {
        this.opAudit1Date = opAudit1Date;
    }

    public String getOpAudit2Id() {
        return opAudit2Id;
    }

    public void setOpAudit2Id(String opAudit2Id) {
        this.opAudit2Id = opAudit2Id == null ? null : opAudit2Id.trim();
    }

    public Date getOpAudit2Date() {
        return opAudit2Date;
    }

    public void setOpAudit2Date(Date opAudit2Date) {
        this.opAudit2Date = opAudit2Date;
    }

    public String getFinalAuditId() {
        return finalAuditId;
    }

    public void setFinalAuditId(String finalAuditId) {
        this.finalAuditId = finalAuditId == null ? null : finalAuditId.trim();
    }

    public Date getFinalAuditDate() {
        return finalAuditDate;
    }

    public void setFinalAuditDate(Date finalAuditDate) {
        this.finalAuditDate = finalAuditDate;
    }

    public Date getFinalAckDate() {
        return finalAckDate;
    }

    public void setFinalAckDate(Date finalAckDate) {
        this.finalAckDate = finalAckDate;
    }

    public String getFinalAckId() {
        return finalAckId;
    }

    public void setFinalAckId(String finalAckId) {
        this.finalAckId = finalAckId == null ? null : finalAckId.trim();
    }

    public String getFinalAckDemo() {
        return finalAckDemo;
    }

    public void setFinalAckDemo(String finalAckDemo) {
        this.finalAckDemo = finalAckDemo == null ? null : finalAckDemo.trim();
    }
    
    public ArrayList<PurchaseOrdersDetail> getOrderDetails() {
		return orderDetails;
	}
    
    public void addOrderDetail(PurchaseOrdersDetail orderDetail)
    {
    	if (orderDetails == null)
    	{
    		orderDetails = new ArrayList<>();
    	}
    	
    	orderDetails.add(orderDetail);
    }
    
    public void addStockDispatch(StockDispatch dispatch)
    {
    	if (this.stockDispatches == null)
    	{
    		this.stockDispatches = new ArrayList<>();
    	}
    	
    	this.stockDispatches.add(dispatch);
    }
    
    public String getProviderLinkman() {
        return providerLinkman;
    }

    public void setProviderLinkman(String providerLinkman) {
        this.providerLinkman = providerLinkman == null ? null : providerLinkman.trim();
    }

    public String getProviderLinkmanPhone() {
        return providerLinkmanPhone;
    }

    public void setProviderLinkmanPhone(String providerLinkmanPhone) {
        this.providerLinkmanPhone = providerLinkmanPhone == null ? null : providerLinkmanPhone.trim();
    }

    public String getProviderLinkmanEmail() {
        return providerLinkmanEmail;
    }

    public void setProviderLinkmanEmail(String providerLinkmanEmail) {
        this.providerLinkmanEmail = providerLinkmanEmail == null ? null : providerLinkmanEmail.trim();
    }
    
    public void setApplyAddress(String applyAddress) {
		this.applyAddress = applyAddress;
	}
    
    public String getApplyAddress() {
		return applyAddress;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
    
	public String getProviderName() {
		return providerName;
	}
	
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	
    public Integer getStoreAck() {
		return storeAck;
	}

	public void setStoreAck(Integer storeAck) {
		this.storeAck = storeAck;
	}

	public Date getStoreAckDate() {
		return storeAckDate;
	}

	public void setStoreAckDate(Date storeAckDate) {
		this.storeAckDate = storeAckDate;
	}
	
	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public String getGoodsTotalPriceText() {
		if (goodsTotalPrice != null)
		{
			goodsTotalPriceText = MoneyUtils.getMoneyText(goodsTotalPrice);
		}
		
		return goodsTotalPriceText;
	}

	public String getStoreAckDateText() {
		
		if (this.storeAckDate != null)
    	{
			storeAckDateText = DateFormatUtils.format(storeAckDate, "yyyy-MM-dd HH:mm:ss");
    	}
    	else
    	{
    		storeAckDateText  = "";
    	}
		
		return storeAckDateText;
	}

	public String getCreateDateText() {
    	if (this.createDate != null)
    	{
    		createDateText = DateFormatUtils.format(createDate, "yyyy-MM-dd HH:mm:ss");
    	}
    	else
    	{
    		createDateText  = "";
    	}
    	
    	return createDateText;
	}
	
	public void setCreateDateText(String createDateText) {
		this.createDateText = createDateText;
	}

	public String getFromStoreId() {
		return fromStoreId;
	}

	public void setFromStoreId(String fromStoreId) {
		this.fromStoreId = fromStoreId;
	}

	public String getFromStoreName() {
		return fromStoreName;
	}

	public void setFromStoreName(String fromStoreName) {
		this.fromStoreName = fromStoreName;
	}

	public Integer getProviderAckStatus() {
		return providerAckStatus;
	}

	public void setProviderAckStatus(Integer providerAckStatus) {
		this.providerAckStatus = providerAckStatus;
	}

	public Date getProviderAckDate() {
		return providerAckDate;
	}

	public void setProviderAckDate(Date providerAckDate) {
		this.providerAckDate = providerAckDate;
	}

	public Integer getProviderAckAudit() {
		return providerAckAudit;
	}

	public void setProviderAckAudit(Integer providerAckAudit) {
		this.providerAckAudit = providerAckAudit;
	}

	public Date getProviderAckAuditDate() {
		return providerAckAuditDate;
	}

	public void setProviderAckAuditDate(Date providerAckAuditDate) {
		this.providerAckAuditDate = providerAckAuditDate;
	}

	public String getProviderAckAuditDateText() {
		if (providerAckAuditDate != null)
		{
			providerAckAuditDateText = DateFormatUtils.format(providerAckAuditDate, "yyyy-MM-dd HH:mm:ss");
		}
		return providerAckAuditDateText;
	}
	
	public String getProviderAckDateText() {
		if (providerAckDate != null)
		{
			providerAckDateText = DateFormatUtils.format(providerAckDate, "yyyy-MM-dd HH:mm:ss");
		}
		
		return providerAckDateText;
	}
	
	public void setProviderAckAuditDateText(String providerAckAuditDateText) {
		this.providerAckAuditDateText = providerAckAuditDateText;
	}
	
	public void setProviderAckDateText(String providerAckDateText) {
		this.providerAckDateText = providerAckDateText;
	}
	
	public void setMerId(String merId) {
		this.merId = merId;
	}
	
	public String getMerId() {
		return merId;
	}
	
	public ArrayList<StockDispatch> getStockDispatches() {
		return stockDispatches;
	}
	
	public void setEndDateText(String endDateText) {
		this.endDateText = endDateText == null ? null : endDateText.trim();
	}
	
	public String getEndDateText() {
		return endDateText;
	}

	public String getStartDateTextParam() {
		return startDateTextParam;
	}

	public void setStartDateTextParam(String startDateTextParam) {
		this.startDateTextParam = startDateTextParam;
	}

	public String getEndDateTextParam() {
		return endDateTextParam;
	}

	public void setEndDateTextParam(String endDateTextParam) {
		this.endDateTextParam = endDateTextParam;
	}
	
	
}