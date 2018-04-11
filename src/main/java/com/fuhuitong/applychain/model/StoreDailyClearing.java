package com.fuhuitong.applychain.model;

import com.fuhuitong.applychain.utils.MoneyUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

public class StoreDailyClearing extends Pagable
{
    private Integer dailyClearId;

    private String merGroupId;

    private String merId;

    private Date clearingDate;

    private Integer orderCount = 0;

    private Integer orderAmount = 0;
    
    private String orderAmountText;

    private Integer orderProfit = 0;
    
    private String orderProfitText;

    private Integer cashOrderCount = 0;

    private Integer cashOrderAmount = 0;
    
    private String cashOrderAmountText;

    private Integer weixinOrderCount = 0;

    private Integer weixinOrderAmount = 0;
    
    private String weixinOrderAmountText;
    
    private Integer alipayOrderCount = 0;

    private Integer alipayOrderAmount = 0;
    
    private String alipayOrderAmountText;

    private Integer yipayOrderCount = 0;

    private Integer yipayOrderAmount = 0;
    
    private String yipayOrderAmountText;

    private Integer cardOrderCount = 0;
    
    private Integer cardOrderAmount = 0;
    
    private String cardOrderAmountText;

    private Boolean cashClear;

    private Date cashClearTime;
    
    private Integer cashClearAmount = 0;
    
    private String cashClearAmountText;

    private String cashClearPersonId;

    private String cashClearPersonName;
    
    private String clearingDateText;

    public Integer getDailyClearId() {
        return dailyClearId;
    }

    public void setDailyClearId(Integer dailyClearId) {
        this.dailyClearId = dailyClearId;
    }

    public String getMerGroupId() {
        return merGroupId;
    }

    public void setMerGroupId(String merGroupId) {
        this.merGroupId = merGroupId == null ? null : merGroupId.trim();
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId == null ? null : merId.trim();
    }

    public Date getClearingDate() {
        return clearingDate;
    }

    public void setClearingDate(Date clearingDate) {
        this.clearingDate = clearingDate;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Integer getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Integer orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getOrderProfit() {
        return orderProfit;
    }

    public void setOrderProfit(Integer orderProfit) {
        this.orderProfit = orderProfit;
    }

    public Integer getCashOrderCount() {
        return cashOrderCount;
    }

    public void setCashOrderCount(Integer cashOrderCount) {
        this.cashOrderCount = cashOrderCount;
    }

    public Integer getCashOrderAmount() {
        return cashOrderAmount;
    }

    public void setCashOrderAmount(Integer cashOrderAmount) {
        this.cashOrderAmount = cashOrderAmount;
    }


    public Integer getCardOrderCount() {
        return cardOrderCount;
    }

    public void setCardOrderCount(Integer cardOrderCount) {
        this.cardOrderCount = cardOrderCount;
    }

    public Boolean getCashClear() {
        return cashClear;
    }

    public void setCashClear(Boolean cashClear) {
        this.cashClear = cashClear;
    }

    public Date getCashClearTime() {
        return cashClearTime;
    }

    public void setCashClearTime(Date cashClearTime) {
        this.cashClearTime = cashClearTime;
    }

    public String getCashClearPersonId() {
        return cashClearPersonId;
    }

    public void setCashClearPersonId(String cashClearPersonId) {
        this.cashClearPersonId = cashClearPersonId == null ? null : cashClearPersonId.trim();
    }

    public String getCashClearPersonName() {
        return cashClearPersonName;
    }

    public void setCashClearPersonName(String cashClearPersonName) {
        this.cashClearPersonName = cashClearPersonName == null ? null : cashClearPersonName.trim();
    }
    
    public void setClearingDateText(String clearingDateText) {
			this.clearingDateText = clearingDateText;
		}
    
    public String getClearingDateText() {
	
    	if (clearingDate != null)
    	{
    		clearingDateText = DateFormatUtils.format(clearingDate, "yyyy-MM-dd");
    	}
    	
    	return clearingDateText;
	}

	public Integer getWeixinOrderCount() {
		
		return weixinOrderCount;
	}

	public Integer getWeixinOrderAmount() {
		
		return weixinOrderAmount;
	}

	public String getWeixinOrderAmountText() {
		
		weixinOrderAmountText = MoneyUtils.getMoneyText(getWeixinOrderAmount());
		
		return weixinOrderAmountText;
	}

	public Integer getAlipayOrderCount() {
		
		return alipayOrderCount;
	}

	public Integer getAlipayOrderAmount() 
	{
		return alipayOrderAmount;
	}

	public String getAlipayOrderAmountText() {
		
		alipayOrderAmountText = MoneyUtils.getMoneyText(alipayOrderAmount);
		
		return alipayOrderAmountText;
	}

	public Integer getYipayOrderCount() {
		
		return yipayOrderCount;
	}

	public Integer getYipayOrderAmount() {
		return yipayOrderAmount;
	}

	public String getYipayOrderAmountText() {
		
		if (yipayOrderAmount != null)
		{
			yipayOrderAmountText = MoneyUtils.getMoneyText(yipayOrderAmount);
		}
		
		return yipayOrderAmountText;
	}

	public String getOrderAmountText() {
		
		if (orderAmount != null)
		{
			orderAmountText = MoneyUtils.getMoneyText(orderAmount);
		}
		
		return orderAmountText;
	}

	public String getOrderProfitText() {
		
		if (orderProfit != null)
		{
			orderProfitText = MoneyUtils.getMoneyText(orderProfit);
		}
		
		return orderProfitText;
	}

	public String getCashOrderAmountText() {
		if (cashOrderAmount != null)
		{
			cashOrderAmountText = MoneyUtils.getMoneyText(cashOrderAmount);
		}
		
		return cashOrderAmountText;
	}

	public Integer getCashClearAmount() {
		return cashClearAmount;
	}

	public void setCashClearAmount(Integer cashClearAmount) {
		this.cashClearAmount = cashClearAmount;
	}

	public String getCashClearAmountText() {
		
		if (cashClearAmount != null)
		{
			cashClearAmountText = MoneyUtils.getMoneyText(cashClearAmount);
		}
		
		return cashClearAmountText;
	}
	
	public String getCardOrderAmountText() {
		
		if (cardOrderAmount != null)
		{
			cardOrderAmountText = MoneyUtils.getMoneyText(cardOrderAmount);
		}
		
		return cardOrderAmountText;
	}

	public Integer getCardOrderAmount() {
		return cardOrderAmount;
	}

	public void setCardOrderAmount(Integer cardOrderAmount) {
		this.cardOrderAmount = cardOrderAmount;
	}

	public void setWeixinOrderCount(Integer weixinOrderCount) {
		this.weixinOrderCount = weixinOrderCount;
	}

	public void setWeixinOrderAmount(Integer weixinOrderAmount) {
		this.weixinOrderAmount = weixinOrderAmount;
	}

	public void setAlipayOrderCount(Integer alipayOrderCount) {
		this.alipayOrderCount = alipayOrderCount;
	}

	public void setAlipayOrderAmount(Integer alipayOrderAmount) {
		this.alipayOrderAmount = alipayOrderAmount;
	}

	public void setYipayOrderCount(Integer yipayOrderCount) {
		this.yipayOrderCount = yipayOrderCount;
	}

	public void setYipayOrderAmount(Integer yipayOrderAmount) {
		this.yipayOrderAmount = yipayOrderAmount;
	}
    
}