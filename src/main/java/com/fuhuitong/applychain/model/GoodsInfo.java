package com.fuhuitong.applychain.model;

import com.fuhuitong.applychain.model.request.POSParam;
import com.fuhuitong.applychain.utils.MoneyUtils;

import java.util.ArrayList;
import java.util.Date;

public class GoodsInfo extends POSParam {
    private String id;
    private String goodsId;

    private ArrayList<String> goodsIds;

    private String goodsTypeId;

    private String parentTypeId;

    private String goodsTypeName;

    private Long goodsPriceId;

    private Long goodsStockId;

    private Integer stockOrderDetailId;
    private Integer goodsCount;
    private String stockOrderId;

    private Integer goodsMemberPrice;

    private Long goodsMembPriceId;

    private Integer pricePercent;

    private String clientLevelId;

    private String merId;

    private String goodsCode;

    private ArrayList<String> goodsCodes;

    private String goodsName;

    private String goodsDate;

    private String goodsThumbnail;

    private Integer goodsCost;

    private Integer goodsPrice;

    private Integer storeGoodsPrice;

    private String goodsCostText;

    private String goodsPriceText;

    private String storeGoodsPriceText;

    private Integer storeGoodsCost;

    private String storeGoodsCostText;

    private String merGroupId;

    private Integer goodsBulkPrice;

    private String spuCode;

    private String skuCode;

    private String measurUnit;

    private String providerUnit;

    private Integer providerUnitMultiple = 1;

    private Integer flagNum;

    private Long importSn;

    private String flag1;

    private String flag2;

    private String flag3;

    private String flag4;

    private String flag5;

    private String goodsMemo;

    private int page;

    private int limit;

    private int offset;

    private int seeAllGoods = 0;

    private String priceSetting = null;

    private Integer status;

    private boolean blank;

    private Integer ownerType;

    private String ownerId;

    private Date productDate;

    private Date expiredDate;

    private Integer expiredDay = 0;

    private Integer hasQualityPeriod;

    private String stockAmountParam;
    private Integer stockAmount;
    private Integer minStockAmount;
    private Integer maxStockAmount;

    private String gpId;
    private String merUserId;
    private Integer gpStatus;

    private int providerCount = 0;
    private String providerId = "";
    private String providerName = "";

    private Integer stockCheckId;
    private String stockCheckorId;
    private String stockCheckorName;
    private Date createDate;
    private Integer checkResult;
    private Integer checkDiffValue;
    private String createDateText;

    private boolean show;

    private Integer goodsBinding;

    // 是否已经享受过促销的标记
    private boolean catchedDiscount = false;

    public String getGoodsDate() {
        return goodsDate;
    }

    public void setGoodsDate(String goodsDate) {
        this.goodsDate = goodsDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsTypeId() {
        return goodsTypeId;
    }

    public boolean isCatchedDiscount() {
        return catchedDiscount;
    }

    public void setCatchedDiscount(boolean catchedDiscount) {
        this.catchedDiscount = catchedDiscount;
    }

    public void setGoodsTypeId(String goodsTypeId) {
        this.goodsTypeId = goodsTypeId == null ? null : goodsTypeId.trim();
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId == null ? null : merId.trim();
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode == null ? null : goodsCode.trim();
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public String getGoodsThumbnail() {
        return goodsThumbnail;
    }

    public void setGoodsThumbnail(String goodsThumbnail) {
        this.goodsThumbnail = goodsThumbnail == null ? null : goodsThumbnail.trim();
    }

    public Integer getGoodsCost() {
        return goodsCost;
    }

    public void setGoodsCost(Integer goodsCost) {
        this.goodsCost = goodsCost;
    }

    public Integer getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Integer goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getGoodsBulkPrice() {
        return goodsBulkPrice;
    }

    public void setGoodsBulkPrice(Integer goodsBulkPrice) {
        this.goodsBulkPrice = goodsBulkPrice;
    }

    public String getSpuCode() {
        return spuCode;
    }

    public void setSpuCode(String spuCode) {
        this.spuCode = spuCode == null ? null : spuCode.trim();
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode == null ? null : skuCode.trim();
    }

    public String getMeasurUnit() {
        return measurUnit;
    }

    public void setMeasurUnit(String measurUnit) {
        this.measurUnit = measurUnit == null ? null : measurUnit.trim();
    }

    public Integer getFlagNum() {
        return flagNum;
    }

    public void setFlagNum(Integer flagNum) {
        this.flagNum = flagNum;
    }

    public String getFlag1() {
        return flag1;
    }

    public void setFlag1(String flag1) {
        this.flag1 = flag1 == null ? null : flag1.trim();
    }

    public String getFlag2() {
        return flag2;
    }

    public void setFlag2(String flag2) {
        this.flag2 = flag2 == null ? null : flag2.trim();
    }

    public String getFlag3() {
        return flag3;
    }

    public void setFlag3(String flag3) {
        this.flag3 = flag3 == null ? null : flag3.trim();
    }

    public String getFlag4() {
        return flag4;
    }

    public void setFlag4(String flag4) {
        this.flag4 = flag4 == null ? null : flag4.trim();
    }

    public String getFlag5() {
        return flag5;
    }

    public void setFlag5(String flag5) {
        this.flag5 = flag5 == null ? null : flag5.trim();
    }

    public String getGoodsMemo() {
        return goodsMemo;
    }

    public void setGoodsMemo(String goodsMemo) {
        this.goodsMemo = goodsMemo == null ? null : goodsMemo.trim();
    }

    public void setStoreGoodsPrice(Integer storeGoodsPrice) {
        this.storeGoodsPrice = storeGoodsPrice;
    }

    public Integer getStoreGoodsPrice() {
        return storeGoodsPrice;
    }

    public void setMerGroupId(String merGroupId) {
        this.merGroupId = merGroupId;
    }

    public String getMerGroupId() {
        return merGroupId;
    }

    public void setGoodsCodes(ArrayList<String> goodsCodes) {
        this.goodsCodes = goodsCodes;
    }

    public ArrayList<String> getGoodsCodes() {
        return goodsCodes;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {

        offset = (page - 1) * limit;

        return offset;
    }

    public String getParentTypeId() {
        return parentTypeId;
    }

    public void setParentTypeId(String parentTypeId) {
        this.parentTypeId = parentTypeId;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setSeeAllGoods(int seeAllGoods) {
        this.seeAllGoods = seeAllGoods;
    }

    public int getSeeAllGoods() {
        return seeAllGoods;
    }

    public void setPriceSetting(String priceSetting) {
        this.priceSetting = priceSetting;
    }

    public String getPriceSetting() {
        return priceSetting;
    }

    public void setGoodsPriceId(Long goodsPriceId) {
        this.goodsPriceId = goodsPriceId;
    }

    public Long getGoodsPriceId() {
        return goodsPriceId;
    }

    public boolean isBlank() {
        return blank;
    }

    public void setBlank(boolean blank) {
        this.blank = blank;
    }

    public Integer getGoodsMemberPrice() {
        return goodsMemberPrice;
    }

    public void setGoodsMemberPrice(Integer goodsMemberPrice) {
        this.goodsMemberPrice = goodsMemberPrice;
    }

    public Long getGoodsMembPriceId() {
        return goodsMembPriceId;
    }

    public void setGoodsMembPriceId(Long goodsMembPriceId) {
        this.goodsMembPriceId = goodsMembPriceId;
    }

    public void setPricePercent(Integer pricePercent) {
        this.pricePercent = pricePercent;
    }

    public Integer getPricePercent() {
        return pricePercent;
    }

    public void setClientLevelId(String clientLevelId) {
        this.clientLevelId = clientLevelId;
    }

    public String getClientLevelId() {
        return clientLevelId;
    }

    public Integer getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(Integer ownerType) {
        this.ownerType = ownerType;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getStockAmountParam() {
        return stockAmountParam;
    }

    public void setStockAmountParam(String stockAmountParam) {
        this.stockAmountParam = stockAmountParam == null ? null : stockAmountParam.trim();
    }

    public Integer getStockAmount() {
        return stockAmount;
    }

    public void setStockAmount(Integer stockAmount) {
        this.stockAmount = stockAmount;
    }

    public Integer getMinStockAmount() {
        return minStockAmount;
    }

    public void setMinStockAmount(Integer minStockAmount) {
        this.minStockAmount = minStockAmount;
    }

    public Integer getMaxStockAmount() {
        return maxStockAmount;
    }

    public void setMaxStockAmount(Integer maxStockAmount) {
        this.maxStockAmount = maxStockAmount;
    }

    public void setGoodsStockId(Long goodsStockId) {
        this.goodsStockId = goodsStockId;
    }

    public Long getGoodsStockId() {
        return goodsStockId;
    }

    public void setProductDate(Date productDate) {
        this.productDate = productDate;
    }

    public Date getProductDate() {
        return productDate;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getGpId() {
        return gpId;
    }

    public void setGpId(String gpId) {
        this.gpId = gpId;
    }

    public String getMerUserId() {
        return merUserId;
    }

    public void setMerUserId(String merUserId) {
        this.merUserId = merUserId;
    }

    public Integer getGpStatus() {
        return gpStatus;
    }

    public void setGpStatus(Integer gpStatus) {
        this.gpStatus = gpStatus;
    }

    public Integer getStockOrderDetailId() {
        return stockOrderDetailId;
    }

    public void setStockOrderDetailId(Integer stockOrderDetailId) {
        this.stockOrderDetailId = stockOrderDetailId;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public void setStockOrderId(String stockOrderId) {
        this.stockOrderId = stockOrderId;
    }

    public String getStockOrderId() {
        return stockOrderId;
    }

    public void setProviderCount(int providerCount) {
        this.providerCount = providerCount;
    }

    public int getProviderCount() {
        return providerCount;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setHasQualityPeriod(Integer hasQualityPeriod) {
        this.hasQualityPeriod = hasQualityPeriod;
    }

    public Integer getHasQualityPeriod() {
        return hasQualityPeriod;
    }

    public void setImportSn(Long importSn) {
        this.importSn = importSn;
    }

    public Long getImportSn() {
        return importSn;
    }

    public void setGoodsIds(ArrayList<String> goodsIds) {
        this.goodsIds = goodsIds;
    }

    public ArrayList<String> getGoodsIds() {
        return goodsIds;
    }

    public String getProviderUnit() {
        return providerUnit;
    }

    public void setProviderUnit(String providerUnit) {
        this.providerUnit = providerUnit == null ? null : providerUnit.trim();
    }

    public Integer getProviderUnitMultiple() {
        return providerUnitMultiple;
    }

    public void setProviderUnitMultiple(Integer providerUnitMultiple) {
        this.providerUnitMultiple = providerUnitMultiple;
    }

    public void setStoreGoodsCost(Integer storeGoodsCost) {
        this.storeGoodsCost = storeGoodsCost;
    }

    public Integer getStoreGoodsCost() {
        return storeGoodsCost;
    }

    public String getGoodsCostText() {
        if (goodsCost != null) {
            goodsCostText = MoneyUtils.getMoneyText(goodsCost);
        }

        return goodsCostText;
    }

    public String getGoodsPriceText() {
        if (goodsPrice != null) {
            goodsPriceText = MoneyUtils.getMoneyText(goodsPrice);
        }

        return goodsPriceText;
    }

    public String getStoreGoodsPriceText() {
        if (storeGoodsPrice != null) {
            storeGoodsPriceText = MoneyUtils.getMoneyText(storeGoodsPrice);
        }

        return storeGoodsPriceText;
    }

    public String getStockCheckorId() {
        return stockCheckorId;
    }

    public void setStockCheckorId(String stockCheckorId) {
        this.stockCheckorId = stockCheckorId;
    }

    public String getStockCheckorName() {
        return stockCheckorName;
    }

    public void setStockCheckorName(String stockCheckorName) {
        this.stockCheckorName = stockCheckorName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(Integer checkResult) {
        this.checkResult = checkResult;
    }

    public Integer getCheckDiffValue() {
        return checkDiffValue;
    }

    public void setCheckDiffValue(Integer checkDiffValue) {
        this.checkDiffValue = checkDiffValue;
    }

    public String getCreateDateText() {
        return createDateText;
    }

    public void setCreateDateText(String createDateText) {
        this.createDateText = createDateText;
    }

    public Integer getStockCheckId() {
        return stockCheckId;
    }

    public void setStockCheckId(Integer stockCheckId) {
        this.stockCheckId = stockCheckId;
    }

    public Integer getExpiredDay() {
        return expiredDay;
    }

    public void setExpiredDay(Integer expiredDay) {
        this.expiredDay = expiredDay;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public boolean isShow() {
        return show;
    }

    public String getStoreGoodsCostText() {
        if (storeGoodsCost != null) {
            storeGoodsCostText = MoneyUtils.getMoneyText(storeGoodsCost);
        }

        return storeGoodsCostText;
    }

    public void setGoodsBinding(Integer goodsBinding) {
        this.goodsBinding = goodsBinding;
    }

    public Integer getGoodsBinding() {
        return goodsBinding;
    }
}