package com.fuhuitong.applychain.service.impl;

import com.fuhuitong.applychain.FuHuiTongContext;
import com.fuhuitong.applychain.dao.*;
import com.fuhuitong.applychain.model.*;
import com.fuhuitong.applychain.service.IGoodsService;
import com.fuhuitong.applychain.utils.ExcelCellUtils;
import com.fuhuitong.applychain.utils.MyUUID;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service
public class GoodsServiceImpl extends BaseService implements IGoodsService {

    @Resource
    private GoodsTypeMapper goodsTypeMapper;

    @Resource
    private AreasMapper areaMapper;

    @Resource
    private GoodsInfoMapper goodsInfoMapper;

    @Resource
    private GoodsPriceMapper goodsPriceMapper;

    @Resource
    private SysDictionaryMapper sysDictMapper;

    @Resource
    private MerchantsGroupsMapper merGroupsMapper;

    @Resource
    private ClientLevelMapper clientLevelMapper;

    @Resource
    private GoodsMemberPriceMapper goodsMemeberPriceMapper;

    @Resource
    private GoodsStockMapper goodsStockMapper;

    @Resource
    private GoodsProvidersMapper goodsProviderMapper;

    @Resource
    private BindingGoodsMapper bindingGoodsMapper;

    @Resource
    private BindingGoodsDetailsMapper bindingGoodsDetailsMapper;

    @Override
    public String goodsTypes(HttpServletRequest request, HttpSession session) {

        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return "page/relogin";
        }

        ArrayList<GoodsType> allGoodsTypes = this.goodsTypeMapper.selectByMerId(this.merUser.getMerId());
        ArrayList<GoodsType> topGoodsTypes = new ArrayList<GoodsType>();

        // 按照递归方式，组成树形结构，首先取出顶级节点
        for (GoodsType goodsType : allGoodsTypes) {
            if (StringUtils.isEmpty(goodsType.getParentTypeId())) {
                topGoodsTypes.add(goodsType);
            }
        }

        // 从数据集合中去掉顶级节点
        for (GoodsType goodsType : topGoodsTypes) {
            allGoodsTypes.remove(goodsType);
        }

        // 利用递归开始将剩下的子节点装入到各个top节点的children中
        for (GoodsType goodsType : topGoodsTypes) {
            loadTreeNodes(goodsType, allGoodsTypes);
        }

        // 虚拟一个大的节点
        GoodsType topGoodsType = new GoodsType();
        topGoodsType.setGoodsTypeId("TOP");
        topGoodsType.setGoodsTypeName("商品分类");

        if (topGoodsTypes != null && !topGoodsTypes.isEmpty()) {
            for (GoodsType top : topGoodsTypes) {
                topGoodsType.addChild(top);
            }
        }

        logger.info(topGoodsType.toTreeString());

        session.setAttribute("GoodsTypeTree", topGoodsType.toTreeString());

        return "page/goods_types";
    }

    @Override
    public String goodsTypesTreeData(HttpServletRequest request, HttpSession session) {
        if (!this.merUserHasAccessRight(request, session)) {
            return "{[]}";
        }

        return "";
    }

    private void loadTreeNodes(GoodsType parentGoodsType, ArrayList<GoodsType> goodsTypeList) {
        ArrayList<GoodsType> subList = new ArrayList<GoodsType>();

        // 找出所有属于 parentGroup 的子节点
        for (GoodsType goodsType : goodsTypeList) {
            if (goodsType.getParentTypeId().equals(parentGoodsType.getGoodsTypeId())) {
                subList.add(goodsType);
            }
        }

        if (subList.size() > 0) {
            // 从当前groups去掉已经找到的子节点
            for (GoodsType group : subList) {
                goodsTypeList.remove(group);

                // 装入 parentGroup的children中
                parentGoodsType.addChild(group);
            }

            // 继续执行子节点
            for (GoodsType goodsType : subList) {
                loadTreeNodes(goodsType, goodsTypeList);
            }
        }
    }

    @Override
    public String saveGoodsType(HttpServletRequest request, HttpSession session, GoodsType goodsType) {
        if (!this.merUserHasAccessRight(request, session)) {
            return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
        }

        if (StringUtils.isEmpty(goodsType.getGoodsTypeName())) {
            return getRetCodeWithJson(1, getMessage("addGoodsType.goodsTypeName"));
        }

        if (StringUtils.isEmpty(goodsType.getGoodsTypeId())) {
            // 新增分类
            goodsType.setGoodsTypeId(MyUUID.randomUUID());
            goodsType.setMerId(this.merUser.getMerId());
            goodsType.setStatus(0);
            goodsType.setCreateDate(new Date());

            this.goodsTypeMapper.insertSelective(goodsType);
        } else {
            // 修改分类
            this.goodsTypeMapper.updateByPrimaryKeySelective(goodsType);
        }

        return getRetCodeWithJson(0);
    }

    @Override
    public String deleteGoodsType(HttpServletRequest request, HttpSession session, String goodsTypeId) {

        if (!this.merUserHasAccessRight(request, session)) {
            return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
        }

        if (StringUtils.isEmpty(goodsTypeId)) {
            return getRetCodeWithJson(1, getMessage("deleteGoodsType.goodsTypeIdNotNull"));
        }

        // 先检查是否有下级商品分类
        ArrayList<GoodsType> subTypes = this.goodsTypeMapper.selectByParentId(goodsTypeId);
        if (subTypes != null && !subTypes.isEmpty()) {
            return getRetCodeWithJson(1, getMessage("deleteGoodsType.hasSubGoodsType"));
        }

        this.goodsTypeMapper.deleteByPrimaryKey(goodsTypeId);

        return getRetCodeWithJson(0);
    }

    @Override
    public String goods(HttpServletRequest request, HttpSession session) {
        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return "page/relogin";
        }

        // 加载商品大类
        ArrayList<GoodsType> topGoodsType = this.goodsTypeMapper.selectTopTypeByMerId(this.merUser.getMerId());
        if (topGoodsType != null && !topGoodsType.isEmpty()) {
            session.setAttribute("TopGoodsTypes", topGoodsType);
        }

        return "page/goods";
    }

    @Override
    public String goodsSubTypes(HttpServletRequest request, HttpSession session, String parentTypeId) {

        if (!this.merUserHasAccessRight(request, session)) {
            return getTableData(null, null, 0);
        }

        // 加载商品子类
        GoodsType query = new GoodsType();
        query.setMerId(this.merUser.getMerId());
        query.setParentTypeId(parentTypeId);

        ArrayList<GoodsType> subGoodsType = this.goodsTypeMapper.selectSubTypeByMerId(query);
        if (subGoodsType != null && !subGoodsType.isEmpty()) {
            return getTableData(subGoodsType, "", subGoodsType.size());
        } else {
            return getTableData(null, null, 0);
        }
    }

    @Override
    public String goodsData(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo) {
        if (!this.merUserHasAccessRight(request, session)) {
            return getTableData(null, null, 0);
        }

        int page = goodsInfo.getPage();
        int limit = goodsInfo.getLimit();

        logger.info("page = " + page);
        logger.info("limit = " + limit);

        session.removeAttribute("SelectedGoodsTypeId");
        session.removeAttribute("SelectedParentGoodsTypeId");

        if (StringUtils.isEmpty(goodsInfo.getGoodsTypeId())) {
            goodsInfo.setGoodsTypeId(null);
        }

        if (StringUtils.isEmpty(goodsInfo.getParentTypeId())) {
            goodsInfo.setParentTypeId(null);
        }

        if (goodsInfo.getStatus() != null && goodsInfo.getStatus() == -1) {
            goodsInfo.setStatus(null);
        }
        if (StringUtils.isEmpty(goodsInfo.getGoodsCode())) {
            goodsInfo.setGoodsCode(null);
        } else {
            String booksCode = goodsInfo.getGoodsCode();
            goodsInfo.setGoodsCode("%" + booksCode.substring(0, booksCode.length() - 1) + "%");
        }

        goodsInfo.setMerId(this.merUser.getMerId());

        int totalCount = this.goodsInfoMapper.selectCountByGoodsType(goodsInfo);
        logger.info("totalCount = " + totalCount);

        if (!StringUtils.isEmpty(goodsInfo.getGoodsTypeId())) {
            session.setAttribute("SelectedGoodsTypeId", goodsInfo.getGoodsTypeId());
        }

        if (!StringUtils.isEmpty(goodsInfo.getParentTypeId())) {
            session.setAttribute("SelectedParentGoodsTypeId", goodsInfo.getParentTypeId());
        }

        ArrayList<GoodsInfo> goodsInfoList = this.goodsInfoMapper.selectByGoodsType(goodsInfo);
        if (goodsInfoList != null && !goodsInfoList.isEmpty()) {
            return getTableData(goodsInfoList, "", totalCount);
        } else {
            return getTableData(null, null, 0);
        }
    }

    @Override
    public String changeMerGoodsStatus(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo) {

        if (!this.merUserHasAccessRight(request, session)) {
            return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
        }

        if (!StringUtils.isEmpty(goodsInfo.getGoodsId())) {
            this.goodsInfoMapper.changeStatus(goodsInfo);
        }

        return getRetCodeWithJson(0);
    }

    @Override
    public String goodsInfo(HttpServletRequest request, HttpSession session, String goodsId) {
        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return "page/relogin";
        }

        session.removeAttribute("TopGoodsTypes");
        session.removeAttribute("SubGoodsTypes");
        session.removeAttribute("CurrentGoodsInfo");

        // 计量单位
        String unitParam = this.sysDictMapper.selectByPrimaryKey(FuHuiTongContext.PARAM_MEASURE_UNIT).getDicValues();
        String[] units = unitParam.split(",");
        session.setAttribute("MeasureUnits", units);

        String goodsTypeId = null;
        String parentTypeId = null;

        if (StringUtils.isEmpty(goodsId)) {
            // 新增
            parentTypeId = (String) session.getAttribute("SelectedParentGoodsTypeId");
            goodsTypeId = (String) session.getAttribute("SelectedGoodsTypeId");

            session.setAttribute("GoodsInfoTitle", getMessage("saveGoodsInfo.createTitle"));

            GoodsInfo goodsInfo = new GoodsInfo();
            goodsInfo.setHasQualityPeriod(0);
            session.setAttribute("CurrentGoodsInfo", goodsInfo);
        } else {
            session.setAttribute("GoodsInfoTitle", getMessage("saveGoodsInfo.modifyTitle"));

            // 修改
            GoodsInfo goodsInfo = this.goodsInfoMapper.selectByPrimaryKey(goodsId);
            session.setAttribute("CurrentGoodsInfo", goodsInfo);

            GoodsType goodsType = this.goodsTypeMapper.selectByPrimaryKey(goodsInfo.getGoodsTypeId());
            // 看该分类是二级分类还是一级分类
            if (goodsType.getParentTypeId() == null) {
                // 一级分类
                parentTypeId = goodsType.getGoodsTypeId();
            } else {
                // 属于二级分类
                goodsTypeId = goodsType.getGoodsTypeId();
                parentTypeId = goodsType.getParentTypeId();
            }
        }

        // 加载商品大类
        ArrayList<GoodsType> topGoodsType = this.goodsTypeMapper.selectTopTypeByMerId(this.merUser.getMerId());
        if (topGoodsType != null && !topGoodsType.isEmpty()) {
            if (parentTypeId != null) {
                for (GoodsType type : topGoodsType) {
                    if (type.getGoodsTypeId().equals(parentTypeId)) {
                        type.setSelected(" selected ");
                    }
                }
            }
            session.setAttribute("TopGoodsTypes", topGoodsType);
        }

        if (goodsTypeId != null) {
            // 加载商品小类
            GoodsType query = new GoodsType();
            query.setMerId(this.merUser.getMerId());
            query.setParentTypeId(parentTypeId);

            ArrayList<GoodsType> subTypes = this.goodsTypeMapper.selectSubTypeByMerId(query);
            if (!subTypes.isEmpty()) {
                for (GoodsType type : subTypes) {
                    if (type.getGoodsTypeId().equals(goodsTypeId)) {
                        type.setSelected(" selected ");
                    }
                }
            }

            session.setAttribute("SubGoodsTypes", subTypes);
        }

        return "page/goods_info";
    }

    @Override
    public String uploadGoodsPic(MultipartFile uploadFile, HttpServletRequest request, HttpSession session) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String saveGoodsInfo(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo) {

        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
        }

        if (StringUtils.isEmpty(goodsInfo.getGoodsCode())) {
            return getRetCodeWithJson(1, getMessage("saveGoodsInfo.goodsCodeNotNull"));
        }

        if (StringUtils.isEmpty(goodsInfo.getGoodsName())) {
            return getRetCodeWithJson(1, getMessage("saveGoodsInfo.goodsNameNotNull"));
        }

        if (goodsInfo.getGoodsPrice() == null) {
            return getRetCodeWithJson(1, getMessage("saveGoodsInfo.goodsPriceNotNull"));
        }

        if (StringUtils.isEmpty(goodsInfo.getParentTypeId())) {
            return getRetCodeWithJson(1, getMessage("saveGoodsInfo.goodsTypeNotNull"));
        }

        logger.info("measurUnit is " + goodsInfo.getMeasurUnit());
        logger.info("providerUnit is " + goodsInfo.getProviderUnit());

        if (StringUtils.isEmpty(goodsInfo.getMeasurUnit())) {
            return getRetCodeWithJson(1, getMessage("saveGoodsInfo.measurUnitNotNull"));
        }

        if (StringUtils.isEmpty(goodsInfo.getProviderUnit())) {
            return getRetCodeWithJson(1, getMessage("saveGoodsInfo.providerUnitNotNull"));
        }

        // 如果没有设定小类，则设定大类
        if (StringUtils.isEmpty(goodsInfo.getGoodsTypeId())) {
            goodsInfo.setGoodsTypeId(goodsInfo.getParentTypeId());
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(new Date());
        goodsInfo.setGoodsDate(date);

        if (StringUtils.isEmpty(goodsInfo.getGoodsId())) {
            // 新增
            GoodsInfo query = new GoodsInfo();
            query.setMerId(this.merUser.getMerId());

            // 判断条码是否重复
            if (this.goodsInfoMapper.selectByGoodsCode(query) != null) {
                return getRetCodeWithJson(1, getMessage("saveGoodsInfo.goodsCodeExists"));
            }

            goodsInfo.setMerId(this.merUser.getMerId());
            goodsInfo.setStatus(0);
            goodsInfo.setGoodsId(MyUUID.randomUUID());

            this.goodsInfoMapper.insertSelective(goodsInfo);

            return getRetCodeWithJson(0, getMessage("saveGoodsInfo.createSuccess"));
        } else {
            // 判断条码是否重复
            GoodsInfo query = new GoodsInfo();
            query.setMerId(this.merUser.getMerId());
            query.setGoodsCode(goodsInfo.getGoodsCode());
            GoodsInfo queried = this.goodsInfoMapper.selectByGoodsCode(query);
            if (queried != null) {
                // 存在相同条码
                if (!queried.getGoodsId().equals(goodsInfo.getGoodsId())) {
                    return getRetCodeWithJson(1, getMessage("saveGoodsInfo.goodsCodeExists"));
                }
            }

            this.goodsInfoMapper.updateByPrimaryKeySelective(goodsInfo);

            return getRetCodeWithJson(2, getMessage("saveGoodsInfo.modifySuccess"));
        }
    }

    @Override
    public String createSameGoodsInfo(HttpServletRequest request, HttpSession session, String goodsId) {
        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return "page/relogin";
        }

        session.removeAttribute("TopGoodsTypes");
        session.removeAttribute("SubGoodsTypes");
        session.removeAttribute("CurrentGoodsInfo");

        // 计量单位
        String unitParam = this.sysDictMapper.selectByPrimaryKey(FuHuiTongContext.PARAM_MEASURE_UNIT).getDicValues();
        String[] units = unitParam.split(",");
        session.setAttribute("MeasureUnits", units);

        String goodsTypeId = null;
        String parentTypeId = null;

        session.setAttribute("GoodsInfoTitle", getMessage("saveGoodsInfo.createTitle"));

        // 新建同类
        GoodsInfo goodsInfo = this.goodsInfoMapper.selectByPrimaryKey(goodsId);

        GoodsInfo same = new GoodsInfo();
        same.setGoodsTypeId(goodsInfo.getGoodsTypeId());
        same.setMeasurUnit(goodsInfo.getMeasurUnit());
        same.setHasQualityPeriod(goodsInfo.getHasQualityPeriod());

        session.setAttribute("CurrentGoodsInfo", same);

        GoodsType goodsType = this.goodsTypeMapper.selectByPrimaryKey(goodsInfo.getGoodsTypeId());
        // 看该分类是二级分类还是一级分类
        if (goodsType.getParentTypeId() == null) {
            // 一级分类
            parentTypeId = goodsType.getGoodsTypeId();
        } else {
            // 属于二级分类
            goodsTypeId = goodsType.getGoodsTypeId();
            parentTypeId = goodsType.getParentTypeId();
        }

        // 加载商品大类
        ArrayList<GoodsType> topGoodsType = this.goodsTypeMapper.selectTopTypeByMerId(this.merUser.getMerId());
        if (topGoodsType != null && !topGoodsType.isEmpty()) {
            if (parentTypeId != null) {
                for (GoodsType type : topGoodsType) {
                    if (type.getGoodsTypeId().equals(parentTypeId)) {
                        type.setSelected(" selected ");
                    }
                }
            }
            session.setAttribute("TopGoodsTypes", topGoodsType);
        }

        if (goodsTypeId != null) {
            // 加载商品小类
            GoodsType query = new GoodsType();
            query.setMerId(this.merUser.getMerId());
            query.setParentTypeId(parentTypeId);

            ArrayList<GoodsType> subTypes = this.goodsTypeMapper.selectSubTypeByMerId(query);
            if (!subTypes.isEmpty()) {
                for (GoodsType type : subTypes) {
                    if (type.getGoodsTypeId().equals(goodsTypeId)) {
                        type.setSelected(" selected ");
                    }
                }
            }

            session.setAttribute("SubGoodsTypes", subTypes);
        }

        return "page/goods_info";
    }

    @Override
    public String storeGoodsPrice(HttpServletRequest request, HttpSession session, String merGroupId) {

        if (!this.merUserHasAccessRight(request, session) || StringUtils.isEmpty(merGroupId)) {
            logger.info("Current user was not logged in.");
            return "page/relogin";
        }

        session.removeAttribute("TopGoodsTypes");
        session.removeAttribute("SubGoodsTypes");

        MerchantsGroups store = this.merGroupsMapper.selectByPrimaryKey(merGroupId);
        if (store != null) {
            session.setAttribute("CurrentStoreForPrice", store);
        }

        // 加载商品大类
        ArrayList<GoodsType> topGoodsType = this.goodsTypeMapper.selectTopTypeByMerId(this.merUser.getMerId());
        if (topGoodsType != null && !topGoodsType.isEmpty()) {
            session.setAttribute("TopGoodsTypes", topGoodsType);
        }

        return "page/stores_goods_price";
    }

    @Override
    public String storeGoodsPriceSelect(HttpServletRequest request, HttpSession session) {
        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return "page/relogin";
        }

        // 查询地区
        // 加载所有顶级地区
        ArrayList<Areas> areas = this.areaMapper.selectTopAreas();
        if (areas != null && !areas.isEmpty()) {
            session.setAttribute("Provinces", areas);
        }

        return "page/stores_goods_price_select";
    }

    /**
     * 该方法有两个入口，一个是获取门店商品价格时，需要从当前上下文获得门店信息
     * 第二个入口是店长查看自己的商品价格，当前店长的门店就是当前门店上下文
     */
    @Override
    public String storesGoodsPriceData(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo, String dispatch) {

        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return getTableData(null, null, 0);
        }

        logger.info("seeAllGoods : " + goodsInfo.getSeeAllGoods());
        logger.info("page : " + goodsInfo.getPage());
        logger.info("limit : " + goodsInfo.getLimit());

        MerchantsGroups store = (MerchantsGroups) session.getAttribute("CurrentStoreForPrice");
        if (store != null) {
            // 门店上下文是从先前门店选择中获取的,此时为设定门店价格
            goodsInfo.setMerId(this.merUser.getMerId());
            goodsInfo.setMerGroupId(store.getMerGroupId());
        } else {
            // 店长所处门店即为上下文，此时为订货管理
            // 订货管理有两个入口分支，一个是店长发起的订货，另外一个是总部直接派发的，此时门店需要从session中获取
            // 总部直接派发进货单时非空
            MerchantsGroups selectedStore = (MerchantsGroups) session.getAttribute("SelectedStore");
            String currStoreId = null;
            if (selectedStore == null) {
                // 一个是店长发起的订货
                currStoreId = this.merUser.getMerGroupId();
            } else {
                // 另外一个是总部直接派发的，此时门店需要从session中获取
                currStoreId = selectedStore.getMerGroupId();
            }
            goodsInfo.setMerId(this.merUser.getMerId());
            goodsInfo.setMerGroupId(currStoreId);
        }

        if (StringUtils.isEmpty(goodsInfo.getGoodsTypeId())) {
            goodsInfo.setGoodsTypeId(null);
        }

        if (StringUtils.isEmpty(goodsInfo.getParentTypeId())) {
            goodsInfo.setParentTypeId(null);
        }

        // 只看可售
        if (goodsInfo.getSeeAllGoods() == 0) {
            if (store == null && !goodsInfo.isShow()) {
                // 该部分逻辑用在店长创建订货单时，选择当前可用的商品，对话框没有显示出来时，缺省不返回数据
                return getTableData(null, null, 0);
            }

            int totalCount = this.goodsInfoMapper.selectStorePriceCount2(goodsInfo);
            logger.info("totalCount = " + totalCount);

            if (!StringUtils.isEmpty(dispatch)) {
                // 店长创建订货单时，查看全部商品
                logger.info("店长创建订货单时，查看全部商品, page size = " + totalCount);
                goodsInfo.setPage(1);
                goodsInfo.setLimit(totalCount);
            } else {
                logger.info("设定门店价格时，分页查看商品, page size = " + goodsInfo.getLimit());
            }

            ArrayList<GoodsInfo> goodsList = this.goodsInfoMapper.selectStorePrice2(goodsInfo);
            if (goodsList != null && !goodsList.isEmpty()) {
                return getTableData(goodsList, "", totalCount);
            } else {
                return getTableData(null, null, 0);
            }
        } else {
            // 看全部商品
            int totalCount = this.goodsInfoMapper.selectStorePriceCount(goodsInfo);
            logger.info("totalCount = " + totalCount);

            ArrayList<GoodsInfo> goodsList = this.goodsInfoMapper.selectStorePrice(goodsInfo);
            if (goodsList != null && !goodsList.isEmpty()) {
                return getTableData(goodsList, "", totalCount);
            } else {
                return getTableData(null, null, 0);
            }
        }
    }

    @Override
    public String saveStoresGoodsPrice(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo) {

        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return getRetCodeWithJson(1);
        }

        if (StringUtils.isEmpty(goodsInfo.getGoodsId()) || StringUtils.isEmpty(goodsInfo.getStoreGoodsPrice())) {
            logger.warn("goods id or price is null");
            return getRetCodeWithJson(1, getMessage("saveStoresGoodsPrice.goodsPriceNotNul"));
        }

        MerchantsGroups store = (MerchantsGroups) session.getAttribute("CurrentStoreForPrice");

        logger.info("goods id " + goodsInfo.getGoodsId());
        logger.info("goods store price " + goodsInfo.getStoreGoodsPrice());
        logger.info("store id is " + store.getMerGroupId());
        logger.info("priceSetting is " + goodsInfo.getPriceSetting());
        logger.info("goodsPriceId is " + goodsInfo.getGoodsPriceId());

        if (goodsInfo.getPriceSetting().equalsIgnoreCase("grantPrice")) {
            // 初次设置门店价格
            GoodsPrice goodsPrice = new GoodsPrice();
            goodsPrice.setGoodsId(goodsInfo.getGoodsId());
            goodsPrice.setGoodsPrice(goodsInfo.getStoreGoodsPrice());
            goodsPrice.setStoreGoodsCost(goodsInfo.getStoreGoodsCost());
            goodsPrice.setMerGroupId(store.getMerGroupId());

            logger.info("Grant goods price " + goodsInfo.getStoreGoodsPrice() + " of " + goodsInfo.getGoodsId());

            this.goodsPriceMapper.grantPrice(goodsPrice);
            return getRetCodeWithJson(0, getMessage("saveStoresGoodsPrice.grantSuccess"));
        } else {
            // 再次修改门店价格
            GoodsPrice goodsPrice = new GoodsPrice();
            goodsPrice.setGoodsPriceId(goodsInfo.getGoodsPriceId());
            goodsPrice.setGoodsPrice(goodsInfo.getStoreGoodsPrice());
            goodsPrice.setStoreGoodsCost(goodsInfo.getStoreGoodsCost());
            goodsPrice.setMerGroupId(store.getMerGroupId());

            logger.info("Save goods price " + goodsInfo.getStoreGoodsPrice() + " of " + goodsInfo.getGoodsId());

            this.goodsPriceMapper.savePrice(goodsPrice);
            return getRetCodeWithJson(0, getMessage("saveStoresGoodsPrice.saveSuccess"));
        }
    }

    @Override
    public ResponseEntity<byte[]> downloadGoodsInfoTemplate(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo) {

        logger.info("Download goods info excel template file.");

        if (!this.merUserHasAccessRight(request, session)) {
            logger.warn("Current user was not logged in.");
            return null;
        }

        if (StringUtils.isEmpty(goodsInfo.getParentTypeId())) {
            logger.warn("Parent type was null!");
            return null;
        }

        if (StringUtils.isEmpty(goodsInfo.getGoodsTypeId())) {
            goodsInfo.setGoodsTypeId(null);
        }

        if (goodsInfo.getStatus() != null && goodsInfo.getStatus() == -1) {
            goodsInfo.setStatus(null);
        }

        String goodsTypeId = goodsInfo.getGoodsTypeId();
        if (StringUtils.isEmpty(goodsTypeId)) {
            goodsTypeId = goodsInfo.getParentTypeId();
        }

        GoodsType goodsType = this.goodsTypeMapper.selectByPrimaryKey(goodsTypeId);

        // 先打开模板文件
        String createGoodsTemplate = FuHuiTongContext.getInstance().getExcelTempDir() + File.separator + "goods_info_template.xls";
        logger.info(createGoodsTemplate);

        FileInputStream fis;
        try {
            fis = new FileInputStream(new File(createGoodsTemplate));
            HSSFWorkbook wb = new HSSFWorkbook(fis);
            HSSFSheet sheet = wb.getSheetAt(0);

            ArrayList<GoodsInfo> goodsInfoList = null;
            int totalCount = 0;

            // 导出商品
            if (!goodsInfo.isBlank()) {
                goodsInfo.setMerId(this.merUser.getMerId());

                totalCount = this.goodsInfoMapper.selectCountByGoodsType(goodsInfo);
                logger.info("totalCount = " + totalCount);

                if (totalCount > 0) {
                    goodsInfo.setPage(1);
                    goodsInfo.setLimit(totalCount);
                    goodsInfo.setImportSn(1L);

                    // 全部查询出来
                    goodsInfoList = this.goodsInfoMapper.selectByGoodsType(goodsInfo);

                    for (int i = 0; i < goodsInfoList.size(); i++) {
                        GoodsInfo goods = goodsInfoList.get(i);

                        // 从第二行开始
                        HSSFRow row = sheet.createRow(i + 2);

                        // 商品分类ID
                        HSSFCell cell0 = row.createCell(0);
                        cell0.setCellValue(goods.getGoodsTypeId());

                        // 商品分类名称
                        HSSFCell cell1 = row.createCell(1);
                        cell1.setCellValue(goods.getGoodsTypeName());

                        // 商品ID
                        if (goods.getGoodsId() != null) {
                            HSSFCell cell2 = row.createCell(2);
                            cell2.setCellValue(goods.getGoodsId());
                        }

                        // 商品条码
                        if (goods.getGoodsCode() != null) {
                            HSSFCell cell3 = row.createCell(3);
                            cell3.setCellValue(goods.getGoodsCode());
                        }

                        // 商品名称
                        if (goods.getGoodsName() != null) {
                            HSSFCell cell4 = row.createCell(4);
                            cell4.setCellValue(goods.getGoodsName());
                        }

                        // 商品销售价
                        if (goods.getGoodsPrice() != null) {
                            HSSFCell cell5 = row.createCell(5);
                            cell5.setCellValue(goods.getGoodsPrice());
                        }

                        // 商品零售单位
                        if (goods.getMeasurUnit() != null) {
                            HSSFCell cell6 = row.createCell(6);
                            cell6.setCellValue(goods.getMeasurUnit());
                        }

                        // 商品进货单位
                        if (goods.getProviderUnit() != null) {
                            HSSFCell cell7 = row.createCell(7);
                            cell7.setCellValue(goods.getProviderUnit());
                        }

                        // 商品进货/零售单位换算比
                        if (goods.getProviderUnitMultiple() != null) {
                            HSSFCell cell8 = row.createCell(8);
                            cell8.setCellValue(goods.getProviderUnitMultiple());
                        }

                        // 是否有保质期
                        if (goods.getHasQualityPeriod() != null && goods.getHasQualityPeriod() == 1) {
                            HSSFCell cell9 = row.createCell(9);
                            cell9.setCellValue("Y");
                        }

                        // 保质期
                        if (goods.getExpiredDay() != null) {
                            HSSFCell cell10 = row.createCell(10);
                            cell10.setCellValue(goods.getExpiredDay());
                        }
                    }
                }
            }

            if (totalCount == 0) {
                // 导出空白的EXCEL，附带分类信息
                // 从第二行开始
                HSSFRow row = sheet.createRow(2);

                // 商品分类ID
                HSSFCell cell0 = row.createCell(0);
                cell0.setCellValue(goodsType.getGoodsTypeId());

                HSSFCell cell1 = row.createCell(1);
                cell1.setCellValue(goodsType.getGoodsTypeName());
            }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            wb.write(bos);
            wb.close();

            // 生成下载文件流
            String fileName = this.sysDictMapper.selectByPrimaryKey(FuHuiTongContext.PARAM_GOODS_INFO_TEMP).getDicValues();

            String extName = FilenameUtils.getExtension(fileName);
            String baseName = FilenameUtils.getBaseName(fileName);
            if (!goodsInfo.isBlank()) {
                fileName = baseName + "(" + goodsType.getGoodsTypeName() + ")_" + DateFormatUtils.format(new Date(), "yyyy-MM-dd") + "." + extName;
            } else {
                fileName = baseName + "(" + goodsType.getGoodsTypeName() + "_空白)_" + DateFormatUtils.format(new Date(), "yyyy-MM-dd") + "." + extName;
            }

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
    public String importGoodsInfo(MultipartFile uploadFile, HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo) {
        if (!this.merUserHasAccessRight(request, session)) {
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
            GoodsInfo preOne = parseGoodsInfo(row);
            if (preOne.getGoodsTypeId() == null || preOne.getGoodsCode() == null || preOne.getGoodsName() == null) {
                logger.info("Data format error.");
                IOUtils.closeQuietly(wb);

                return getRetCodeWithJson(1, getMessage("importGoodsInfo.formatError"));
            }

            ArrayList<GoodsInfo> newGoodsInfoList = new ArrayList<GoodsInfo>();
            ArrayList<GoodsInfo> goodsInfoList = new ArrayList<GoodsInfo>();

            if (preOne.getGoodsId() == null) {
                newGoodsInfoList.add(preOne);
            } else {
                goodsInfoList.add(preOne);
            }

            int rowNum = 3;
            while (true) {
                row = sheet.getRow(rowNum);
                GoodsInfo goods = parseGoodsInfo(row);

                if (goods == null || goods.getGoodsTypeId() == null || goods.getGoodsCode() == null || goods.getGoodsName() == null || goods.getGoodsPrice() == null) {
                    // 数据结束
                    break;
                }

                logger.info("rowNum is " + rowNum);
                logger.info("goodsId is " + goods.getGoodsId());
                logger.info("goodsCode is " + goods.getGoodsCode());

                if (goods.getGoodsId() == null) {
                    newGoodsInfoList.add(goods);
                } else {
                    goodsInfoList.add(goods);
                }

                rowNum++;
            }

            IOUtils.closeQuietly(wb);

            logger.info("There are " + goodsInfoList.size() + " data records in the excel file to update.");

            for (GoodsInfo goods : goodsInfoList) {
                goods.setMerId(this.merUser.getMerId());
                this.goodsInfoMapper.updateWhenImportExcel(goods);
            }

            logger.info("There are " + newGoodsInfoList.size() + " new data records in the excel file to import.");
            long importSn = 0;

            for (GoodsInfo goods : newGoodsInfoList) {
                goods.setMerId(this.merUser.getMerId());
                goods.setStatus(0);
                goods.setGoodsId(MyUUID.randomUUID());
                goods.setImportSn(new Date().getTime() + importSn);

                this.goodsInfoMapper.insertSelective(goods);
                importSn++;
            }

            int updateCount = goodsInfoList.size();
            int createCount = newGoodsInfoList.size();
            int total = updateCount + createCount;

            String retMsg = "总计导入" + total + "数据，更新" + updateCount + "条，新增" + createCount + "条商品数据";

            return getRetCodeWithJson(0, retMsg);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return getRetCodeWithJson(0);
    }

    private GoodsInfo parseGoodsInfo(HSSFRow row) {
        GoodsInfo goodsInfo = new GoodsInfo();
        if (row == null) {
            return goodsInfo;
        }

        if (row.getCell(0) != null) {
            goodsInfo.setGoodsTypeId(ExcelCellUtils.readStringValue(row.getCell(0)));
        }

        if (row.getCell(2) != null) {
            goodsInfo.setGoodsId(ExcelCellUtils.readStringValue(row.getCell(2)));
        }

        if (row.getCell(3) != null) {
            goodsInfo.setGoodsCode(ExcelCellUtils.readStringValue(row.getCell(3)));
        }

        if (row.getCell(4) != null) {
            goodsInfo.setGoodsName(ExcelCellUtils.readStringValue(row.getCell(4)));
        }

        if (row.getCell(5) != null) {
            Long value = ExcelCellUtils.readLongValue(row.getCell(5));
            if (value != null) {
                goodsInfo.setGoodsPrice(value.intValue());
            }
        }

        // 商品零售单位
        if (row.getCell(6) != null) {
            goodsInfo.setMeasurUnit(ExcelCellUtils.readStringValue(row.getCell(6)));
        }

        // 商品进货单位
        if (row.getCell(7) != null) {
            goodsInfo.setProviderUnit(ExcelCellUtils.readStringValue(row.getCell(7)));
        }

        // 商品进货单位/零售单位换算比
        if (row.getCell(8) != null) {
            Long value = ExcelCellUtils.readLongValue(row.getCell(8));
            if (value != null) {
                goodsInfo.setProviderUnitMultiple(value.intValue());
            }
        }

        goodsInfo.setHasQualityPeriod(0);
        if (row.getCell(9) != null) {
            String hasExpired = ExcelCellUtils.readStringValue(row.getCell(9));
            if (hasExpired != null) {
                if (!StringUtils.isEmpty(hasExpired) && hasExpired.equalsIgnoreCase("Y")) {
                    goodsInfo.setHasQualityPeriod(1);
                }
            }
        }

        if (row.getCell(10) != null) {
            Long value = ExcelCellUtils.readLongValue(row.getCell(10));
            if (value != null) {
                goodsInfo.setExpiredDay(value.intValue());
            }
        }

        // 采购价
        if (row.getCell(11) != null) {
            Long value = ExcelCellUtils.readLongValue(row.getCell(11));
            if (value != null) {
                goodsInfo.setGoodsCost(value.intValue());
            }
        }

        return goodsInfo;
    }

    private GoodsInfo parseStoreGoodsPrice(HSSFRow row) {
        GoodsInfo goodsInfo = new GoodsInfo();
        if (row == null) {
            return goodsInfo;
        }

        if (row.getCell(0) != null) {
            goodsInfo.setGoodsId(ExcelCellUtils.readStringValue(row.getCell(0)));
        }

        if (row.getCell(1) != null) {
            goodsInfo.setGoodsCode(ExcelCellUtils.readStringValue(row.getCell(1)));
        }

        if (row.getCell(2) != null) {
            goodsInfo.setGoodsName(ExcelCellUtils.readStringValue(row.getCell(2)));
        }

        if (row.getCell(3) != null) {
            Long value = ExcelCellUtils.readLongValue(row.getCell(3));
            if (value != null) {
                goodsInfo.setGoodsPriceId(value);
            }
        }

        if (row.getCell(4) != null) {
            Long value = ExcelCellUtils.readLongValue(row.getCell(4));
            if (value != null) {
                goodsInfo.setGoodsPrice(value.intValue());
            }
        }

        // 门店销售价
        if (row.getCell(5) != null) {
            Long value = ExcelCellUtils.readLongValue(row.getCell(5));
            if (value != null) {
                goodsInfo.setStoreGoodsPrice(value.intValue());
            }
        }

		/*
        // 门店进货价
		if (row.getCell(6) != null)
		{
			Long value = ExcelCellUtils.readLongValue(row.getCell(6));
			if (value != null)
			{
				goodsInfo.setStoreGoodsCost(value.intValue());
			}
		}*/

        return goodsInfo;
    }

    private GoodsInfo parseMemberGoodsPrice(HSSFRow row) {
        GoodsInfo goodsInfo = new GoodsInfo();
        if (row == null) {
            return goodsInfo;
        }

        if (row.getCell(0) != null) {
            goodsInfo.setGoodsId(ExcelCellUtils.readStringValue(row.getCell(0)));
        }

        if (row.getCell(1) != null) {
            goodsInfo.setGoodsCode(ExcelCellUtils.readStringValue(row.getCell(1)));
        }

        if (row.getCell(2) != null) {
            goodsInfo.setGoodsName(ExcelCellUtils.readStringValue(row.getCell(2)));
        }

        // 会员销售编码
        if (row.getCell(3) != null) {
            Long value = ExcelCellUtils.readLongValue(row.getCell(3));
            if (value != null) {
                goodsInfo.setGoodsMembPriceId(value);
            }
        }

        // 商品销售价
        if (row.getCell(4) != null) {
            Long value = ExcelCellUtils.readLongValue(row.getCell(4));
            if (value != null) {
                goodsInfo.setGoodsPrice(value.intValue());
            }
        }

        // 会员销售价
        if (row.getCell(5) != null) {
            Long value = ExcelCellUtils.readLongValue(row.getCell(5));
            if (value != null) {
                goodsInfo.setGoodsMemberPrice(value.intValue());
            }
        }

        return goodsInfo;
    }

    @Override
    public ResponseEntity<byte[]> downloadStorePriceTemplate(HttpServletRequest request, HttpSession session,
                                                             GoodsInfo goodsInfo) {

        logger.info("Download store price info excel template file.");

        if (!this.merUserHasAccessRight(request, session)) {
            logger.warn("Current user was not logged in.");
            return null;
        }

        MerchantsGroups store = (MerchantsGroups) session.getAttribute("CurrentStoreForPrice");

        goodsInfo.setGoodsTypeId(null);
        goodsInfo.setMerId(this.merUser.getMerId());
        goodsInfo.setMerGroupId(store.getMerGroupId());

        if (goodsInfo.getStatus() != null && goodsInfo.getStatus() == -1) {
            goodsInfo.setStatus(null);
        }

        String goodsTypeName = null;

        if (!StringUtils.isEmpty(goodsInfo.getParentTypeId())) {
            GoodsType goodsType = this.goodsTypeMapper.selectByPrimaryKey(goodsInfo.getParentTypeId());
            goodsTypeName = goodsType.getGoodsTypeName();
        } else {
            goodsInfo.setParentTypeId(null);
        }

        // 看全部商品
        int totalCount = this.goodsInfoMapper.selectStorePriceCount(goodsInfo);
        logger.info("totalCount = " + totalCount);
        goodsInfo.setPage(1);
        goodsInfo.setLimit(totalCount);
        goodsInfo.setMerId(this.merUser.getMerId());
        goodsInfo.setImportSn(1L);

        // 先打开模板文件
        String storesPriceTemplate = FuHuiTongContext.getInstance().getExcelTempDir() + File.separator + "store_goods_price_template.xls";
        logger.info(storesPriceTemplate);

        FileInputStream fis;
        try {
            fis = new FileInputStream(new File(storesPriceTemplate));
            HSSFWorkbook wb = new HSSFWorkbook(fis);
            HSSFSheet sheet = wb.getSheetAt(0);

            // 更新 门店名称
            HSSFRow row = sheet.getRow(1);
            HSSFCell cell = row.getCell(1);
            cell.setCellValue(store.getGroupName());

            // 更新分类名称
            if (!StringUtils.isEmpty(goodsTypeName)) {
                cell = row.getCell(3);
                cell.setCellValue(goodsTypeName);
            }

            // 导出商品
            if (totalCount > 0) {
                ArrayList<GoodsInfo> goodsList = this.goodsInfoMapper.selectStorePrice(goodsInfo);

                for (int i = 0; i < goodsList.size(); i++) {
                    GoodsInfo goods = goodsList.get(i);

                    // 从第二行开始
                    row = sheet.createRow(i + 3);

                    // 商品编码
                    HSSFCell cell0 = row.createCell(0);
                    cell0.setCellValue(goods.getGoodsId());

                    // 商品条码
                    if (goods.getGoodsCode() != null) {
                        HSSFCell cell1 = row.createCell(1);
                        cell1.setCellValue(goods.getGoodsCode());
                    }

                    // 商品名称
                    if (goods.getGoodsName() != null) {
                        HSSFCell cell2 = row.createCell(2);
                        cell2.setCellValue(goods.getGoodsName());
                    }

                    // 门店销售价编码
                    if (goods.getGoodsPriceId() != null) {
                        HSSFCell cell3 = row.createCell(3);
                        cell3.setCellValue(goods.getGoodsPriceId());
                    }

                    // 商品销售价
                    if (goods.getGoodsPrice() != null) {
                        HSSFCell cell4 = row.createCell(4);
                        cell4.setCellValue(goods.getGoodsPrice());
                    }

                    // 门店销售价价
                    if (goods.getStoreGoodsPrice() != null) {
                        HSSFCell cell5 = row.createCell(5);
                        cell5.setCellValue(goods.getStoreGoodsPrice());
                    }
					
					/*
					 * 门店成本价与总部进货价，无差价，无需导入  2017/11/10
					// 门店进货价
					if(goods.getStoreGoodsCost() != null)
					{
						HSSFCell cell6 = row.createCell(6);
						cell6.setCellValue(goods.getStoreGoodsCost());
					}
					*/
                }
            }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            wb.write(bos);
            wb.close();

            // 生成下载文件流
            String fileName = this.sysDictMapper.selectByPrimaryKey(FuHuiTongContext.PARAM_STORE_GOODS_INFO_TEMP).getDicValues();

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
    public String importStoreGoodsPrice(MultipartFile uploadFile, HttpServletRequest request, HttpSession session,
                                        GoodsInfo goodsInfo) {

        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return getRetCodeWithJson(1);
        }

        HSSFWorkbook wb;
        HSSFSheet sheet;

        MerchantsGroups store = (MerchantsGroups) session.getAttribute("CurrentStoreForPrice");

        try {
            wb = new HSSFWorkbook(uploadFile.getInputStream());
            sheet = wb.getSheetAt(0);

            // 从第3行开始读取数据，先预先读取一行
            HSSFRow row = sheet.getRow(3);
            GoodsInfo preOne = parseStoreGoodsPrice(row);
            if (preOne.getGoodsId() == null || preOne.getGoodsCode() == null || preOne.getGoodsName() == null) {
                logger.info("Data format error.");
                IOUtils.closeQuietly(wb);

                return getRetCodeWithJson(1, getMessage("importGoodsInfo.formatError"));
            }

            ArrayList<GoodsInfo> newGoodsInfoList = new ArrayList<GoodsInfo>();
            ArrayList<GoodsInfo> goodsInfoList = new ArrayList<GoodsInfo>();

            if (preOne.getGoodsPriceId() == null) {
                if (preOne.getStoreGoodsPrice() != null)
                    newGoodsInfoList.add(preOne);
            } else {
                if (preOne.getStoreGoodsPrice() != null)
                    goodsInfoList.add(preOne);
            }

            int rowNum = 4;
            while (true) {
                row = sheet.getRow(rowNum);
                GoodsInfo goods = parseStoreGoodsPrice(row);

                if (goods == null || goods.getGoodsId() == null || goods.getGoodsCode() == null || goods.getGoodsName() == null) {
                    // 数据结束
                    break;
                }

                logger.info("rowNum is " + rowNum);
                logger.info("goodsId is " + goods.getGoodsId());
                logger.info("goodsCode is " + goods.getGoodsCode());

                // 门店价格为空，则不进入新增或者修改的范围
                if (goods.getGoodsPriceId() == null) {
                    if (goods.getStoreGoodsPrice() != null)
                        newGoodsInfoList.add(goods);
                } else {
                    if (goods.getStoreGoodsPrice() != null)
                        goodsInfoList.add(goods);
                }

                rowNum++;
            }

            IOUtils.closeQuietly(wb);

            logger.info("There are " + goodsInfoList.size() + " data records in the excel file to update.");

            for (GoodsInfo goods : goodsInfoList) {
                // 再次修改门店价格
                GoodsPrice goodsPrice = new GoodsPrice();
                goodsPrice.setGoodsPriceId(goods.getGoodsPriceId());
                goodsPrice.setGoodsPrice(goods.getStoreGoodsPrice());
                goodsPrice.setMerGroupId(store.getMerGroupId());

                this.goodsPriceMapper.savePrice(goodsPrice);
            }

            logger.info("There are " + newGoodsInfoList.size() + " new data records in the excel file to import.");
            for (GoodsInfo goods : newGoodsInfoList) {
                // 初次设置门店价格
                GoodsPrice goodsPrice = new GoodsPrice();
                goodsPrice.setGoodsId(goods.getGoodsId());
                goodsPrice.setGoodsPrice(goods.getStoreGoodsPrice());
                goodsPrice.setMerGroupId(store.getMerGroupId());

                this.goodsPriceMapper.grantPrice(goodsPrice);
            }

            int updateCount = goodsInfoList.size();
            int createCount = newGoodsInfoList.size();
            int total = updateCount + createCount;

            String retMsg = "总计导入" + total + "数据，更新" + updateCount + "条，新增" + createCount + "条商品数据";

            return getRetCodeWithJson(0, retMsg);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return getRetCodeWithJson(0);
    }

    @Override
    public String membersGoodsPrice(HttpServletRequest request, HttpSession session, String clientLevelId) {

        if (!this.merUserHasAccessRight(request, session) || StringUtils.isEmpty(clientLevelId)) {
            logger.info("Current user was not logged in.");
            return "page/relogin";
        }

        session.removeAttribute("TopGoodsTypes");
        session.removeAttribute("SubGoodsTypes");

        ClientLevel memberLevel = this.clientLevelMapper.selectByPrimaryKey(clientLevelId);
        if (memberLevel != null) {
            session.setAttribute("CurrentClientLevel", memberLevel);
        }

        // 加载商品大类
        ArrayList<GoodsType> topGoodsType = this.goodsTypeMapper.selectTopTypeByMerId(this.merUser.getMerId());
        if (topGoodsType != null && !topGoodsType.isEmpty()) {
            session.setAttribute("TopGoodsTypes", topGoodsType);
        }

        return "page/members_goods_price";
    }

    @Override
    public String memberGoodsPriceData(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo) {

        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return getTableData(null, null, 0);
        }

        logger.info("seeAllGoods : " + goodsInfo.getSeeAllGoods());
        logger.info("page : " + goodsInfo.getPage());
        logger.info("limit : " + goodsInfo.getLimit());

        ClientLevel clientLevel = (ClientLevel) session.getAttribute("CurrentClientLevel");

        goodsInfo.setMerId(this.merUser.getMerId());
        goodsInfo.setClientLevelId(clientLevel.getClientLevelId());

        if (StringUtils.isEmpty(goodsInfo.getGoodsTypeId())) {
            goodsInfo.setGoodsTypeId(null);
        }

        if (StringUtils.isEmpty(goodsInfo.getParentTypeId())) {
            goodsInfo.setParentTypeId(null);
        }

        // 只看可售
        if (goodsInfo.getSeeAllGoods() == 0) {
            int totalCount = this.goodsInfoMapper.selectMemberPriceCount2(goodsInfo);
            logger.info("totalCount = " + totalCount);

            ArrayList<GoodsInfo> goodsList = this.goodsInfoMapper.selectMemberPrice2(goodsInfo);
            if (goodsList != null && !goodsList.isEmpty()) {
                return getTableData(goodsList, "", totalCount);
            } else {
                return getTableData(null, null, 0);
            }
        } else {
            // 看全部商品
            int totalCount = this.goodsInfoMapper.selectMemberPriceCount(goodsInfo);
            logger.info("totalCount = " + totalCount);

            ArrayList<GoodsInfo> goodsList = this.goodsInfoMapper.selectMemberPrice(goodsInfo);
            if (goodsList != null && !goodsList.isEmpty()) {
                return getTableData(goodsList, "", totalCount);
            } else {
                return getTableData(null, null, 0);
            }
        }
    }

    @Override
    public ResponseEntity<byte[]> downloadMemberPriceTemplate(HttpServletRequest request, HttpSession session,
                                                              GoodsInfo goodsInfo) {
        logger.info("Download member price info excel template file.");

        if (!this.merUserHasAccessRight(request, session)) {
            logger.warn("Current user was not logged in.");
            return null;
        }

        ClientLevel clientLevel = (ClientLevel) session.getAttribute("CurrentClientLevel");

        goodsInfo.setGoodsTypeId(null);
        goodsInfo.setMerId(this.merUser.getMerId());
        goodsInfo.setClientLevelId(clientLevel.getClientLevelId());

        if (goodsInfo.getStatus() != null && goodsInfo.getStatus() == -1) {
            goodsInfo.setStatus(null);
        }

        String goodsTypeName = null;

        if (!StringUtils.isEmpty(goodsInfo.getParentTypeId())) {
            GoodsType goodsType = this.goodsTypeMapper.selectByPrimaryKey(goodsInfo.getParentTypeId());
            goodsTypeName = goodsType.getGoodsTypeName();
        } else {
            goodsInfo.setParentTypeId(null);
        }

        // 看全部商品
        int totalCount = this.goodsInfoMapper.selectMemberPriceCount(goodsInfo);
        logger.info("totalCount = " + totalCount);
        goodsInfo.setPage(1);
        goodsInfo.setLimit(totalCount);
        goodsInfo.setMerId(this.merUser.getMerId());
        goodsInfo.setImportSn(1L);

        // 先打开模板文件
        String memberPriceTemplate = FuHuiTongContext.getInstance().getExcelTempDir() + File.separator + "member_goods_price_template.xls";
        logger.info(memberPriceTemplate);

        FileInputStream fis;
        try {
            fis = new FileInputStream(new File(memberPriceTemplate));
            HSSFWorkbook wb = new HSSFWorkbook(fis);
            HSSFSheet sheet = wb.getSheetAt(0);

            // 更新 会员级别名称
            HSSFRow row = sheet.getRow(1);
            HSSFCell cell = row.getCell(1);
            cell.setCellValue(clientLevel.getLevelName());

            // 更新分类名称
            if (!StringUtils.isEmpty(goodsTypeName)) {
                cell = row.getCell(3);
                cell.setCellValue(goodsTypeName);
            }

            // 导出商品
            if (totalCount > 0) {
                ArrayList<GoodsInfo> goodsList = this.goodsInfoMapper.selectMemberPrice(goodsInfo);

                for (int i = 0; i < goodsList.size(); i++) {
                    GoodsInfo goods = goodsList.get(i);

                    // 从第二行开始
                    row = sheet.createRow(i + 3);

                    // 商品编码
                    HSSFCell cell0 = row.createCell(0);
                    cell0.setCellValue(goods.getGoodsId());

                    // 商品条码
                    if (goods.getGoodsCode() != null) {
                        HSSFCell cell1 = row.createCell(1);
                        cell1.setCellValue(goods.getGoodsCode());
                    }

                    // 商品名称
                    if (goods.getGoodsName() != null) {
                        HSSFCell cell2 = row.createCell(2);
                        cell2.setCellValue(goods.getGoodsName());
                    }

                    // 会员销售价编码
                    if (goods.getGoodsPriceId() != null) {
                        HSSFCell cell3 = row.createCell(3);
                        cell3.setCellValue(goods.getGoodsMembPriceId());
                    }

                    // 商品销售价
                    if (goods.getGoodsPrice() != null) {
                        HSSFCell cell4 = row.createCell(4);
                        cell4.setCellValue(goods.getGoodsPrice());
                    }

                    // 会员销售价价
                    if (goods.getStoreGoodsPrice() != null) {
                        HSSFCell cell5 = row.createCell(5);
                        cell5.setCellValue(goods.getGoodsMemberPrice());
                    }
                }
            }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            wb.write(bos);
            wb.close();

            // 生成下载文件流
            String fileName = this.sysDictMapper.selectByPrimaryKey(FuHuiTongContext.PARAM_STORE_GOODS_INFO_TEMP).getDicValues();

            String extName = FilenameUtils.getExtension(fileName);
            String baseName = FilenameUtils.getBaseName(fileName);
            fileName = baseName + "_" + clientLevel.getLevelName() + "_" + DateFormatUtils.format(new Date(), "yyyy-MM-dd") + "." + extName;

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
    public String importMemberGoodsPrice(MultipartFile uploadFile, HttpServletRequest request, HttpSession session,
                                         GoodsInfo goodsInfo) {
        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return getRetCodeWithJson(1);
        }

        HSSFWorkbook wb;
        HSSFSheet sheet;

        ClientLevel clientLevel = (ClientLevel) session.getAttribute("CurrentClientLevel");

        try {
            wb = new HSSFWorkbook(uploadFile.getInputStream());
            sheet = wb.getSheetAt(0);

            // 从第3行开始读取数据，先预先读取一行
            HSSFRow row = sheet.getRow(3);
            GoodsInfo preOne = parseMemberGoodsPrice(row);
            if (preOne.getGoodsId() == null || preOne.getGoodsCode() == null || preOne.getGoodsName() == null) {
                logger.info("Data format error.");
                IOUtils.closeQuietly(wb);

                return getRetCodeWithJson(1, getMessage("importGoodsInfo.formatError"));
            }

            ArrayList<GoodsInfo> newGoodsInfoList = new ArrayList<GoodsInfo>();
            ArrayList<GoodsInfo> goodsInfoList = new ArrayList<GoodsInfo>();

            if (preOne.getGoodsMembPriceId() == null) {
                if (preOne.getGoodsMemberPrice() != null)
                    newGoodsInfoList.add(preOne);
            } else {
                if (preOne.getGoodsMemberPrice() != null)
                    goodsInfoList.add(preOne);
            }

            int rowNum = 4;
            while (true) {
                row = sheet.getRow(rowNum);
                GoodsInfo goods = parseMemberGoodsPrice(row);

                if (goods == null || goods.getGoodsId() == null || goods.getGoodsCode() == null || goods.getGoodsName() == null) {
                    // 数据结束
                    break;
                }

                logger.info("rowNum is " + rowNum);
                logger.info("goodsId is " + goods.getGoodsId());
                logger.info("goodsCode is " + goods.getGoodsCode());

                // 会员价格为空，则不进入新增或者修改的范围
                if (goods.getGoodsMembPriceId() == null) {
                    if (goods.getGoodsMemberPrice() != null)
                        newGoodsInfoList.add(goods);
                } else {
                    if (goods.getGoodsMemberPrice() != null)
                        goodsInfoList.add(goods);
                }

                rowNum++;
            }

            IOUtils.closeQuietly(wb);

            logger.info("There are " + goodsInfoList.size() + " data records in the excel file to update.");

            for (GoodsInfo goods : goodsInfoList) {
                // 再次修改会员价格
                GoodsMemberPrice memberPrice = new GoodsMemberPrice();

                memberPrice.setGoodsMembPriceId(goods.getGoodsMembPriceId());
                memberPrice.setGoodsPrice(goods.getGoodsMemberPrice());

                this.goodsMemeberPriceMapper.updateByPrimaryKeySelective(memberPrice);
            }

            logger.info("There are " + newGoodsInfoList.size() + " new data records in the excel file to import.");
            for (GoodsInfo goods : newGoodsInfoList) {
                // 初次设置会员价格
                GoodsMemberPrice memberPrice = new GoodsMemberPrice();

                memberPrice.setGoodsTypeId(goodsInfo.getGoodsTypeId());
                memberPrice.setClientLevelId(clientLevel.getClientLevelId());
                memberPrice.setMerId(this.merUser.getMerId());
                memberPrice.setGoodsId(goods.getGoodsId());
                memberPrice.setGoodsPrice(goods.getGoodsMemberPrice());
                memberPrice.setPricePercent((int) (goods.getGoodsMemberPrice() / goods.getGoodsPrice()));
                memberPrice.setCreateDate(new Date());
                memberPrice.setValid(true);

                this.goodsMemeberPriceMapper.insertSelective(memberPrice);
            }

            int updateCount = goodsInfoList.size();
            int createCount = newGoodsInfoList.size();
            int total = updateCount + createCount;

            String retMsg = "总计导入" + total + "数据，更新" + updateCount + "条，新增" + createCount + "条商品数据";

            return getRetCodeWithJson(0, retMsg);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return getRetCodeWithJson(0);
    }

    @Override
    public String importMerProviderGoods(MultipartFile uploadFile, HttpServletRequest request, HttpSession session) {

        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return getRetCodeWithJson(1);
        }

        MerchantUsers merUser = (MerchantUsers) session.getAttribute("CurrentGpsUser");

        HSSFWorkbook wb;
        HSSFSheet sheet;

        try {
            wb = new HSSFWorkbook(uploadFile.getInputStream());
            sheet = wb.getSheetAt(0);

            // 从第2行开始读取数据，先预先读取一行
            HSSFRow row = sheet.getRow(2);
            GoodsInfo preOne = parseGoodsInfo(row);
            if (preOne.getGoodsTypeId() == null || preOne.getGoodsCode() == null || preOne.getGoodsName() == null) {
                logger.info("Data format error.");
                IOUtils.closeQuietly(wb);

                return getRetCodeWithJson(1, getMessage("importGoodsInfo.formatError"));
            }

            ArrayList<GoodsInfo> goodsInfoList = new ArrayList<GoodsInfo>();

            goodsInfoList.add(preOne);

            int rowNum = 3;
            while (true) {
                row = sheet.getRow(rowNum);
                GoodsInfo goods = parseGoodsInfo(row);

                if (goods == null || goods.getGoodsTypeId() == null || goods.getGoodsCode() == null || goods.getGoodsName() == null || goods.getGoodsPrice() == null) {
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

            logger.info("There are " + goodsInfoList.size() + " data records in the excel file to provider.");

            for (GoodsInfo goods : goodsInfoList) {
                // 先删除已经存在的
                GoodsProviders query = new GoodsProviders();
                query.setGoodsId(goods.getGoodsId());
                query.setMerUserId(merUser.getMerUserId());
                this.goodsProviderMapper.deleteByProvider(query);

                // 再插入新的商品清单
                GoodsProviders gp = new GoodsProviders();
                gp.setGpId(MyUUID.randomUUID());
                gp.setGoodsId(goods.getGoodsId());
                gp.setGpStatus(0);
                if (goods.getGoodsCost() == null) {
                    gp.setGoodsCost(0);
                } else {
                    gp.setGoodsCost(goods.getGoodsCost());
                }

                gp.setMerUserId(merUser.getMerUserId());

                this.goodsProviderMapper.insertSelective(gp);
            }

            int updateCount = goodsInfoList.size();

            String retMsg = "总计导入" + updateCount + "条商品数据，归属到当前供应商名下";

            return getRetCodeWithJson(0, retMsg);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return getRetCodeWithJson(0);
    }

    @Override
    public String deleteGoodsInfo(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo) {

        if (!this.merUserHasAccessRight(request, session) || StringUtils.isEmpty(goodsInfo.getGoodsId())) {
            logger.info("Current user was not logged in.");
            return getRetCodeWithJson(1);
        }

        this.goodsInfoMapper.deleteByPrimaryKey(goodsInfo.getGoodsId());

        return getRetCodeWithJson(0);
    }

    @Override
    public String deleteMemberGoodsPrice(HttpServletRequest request, HttpSession session, String goodsMembPriceId) {

        if (!this.merUserHasAccessRight(request, session) || StringUtils.isEmpty(goodsMembPriceId)) {
            logger.info("Current user was not logged in.");
            return getRetCodeWithJson(1);
        }

        this.goodsMemeberPriceMapper.deleteByPrimaryKey(Long.parseLong(goodsMembPriceId));

        return getRetCodeWithJson(0);

    }

    @Override
    public String queryGoodsByIds(HttpServletRequest request, HttpSession session, String goodsIds) {
        if (!this.merUserHasAccessRight(request, session) || StringUtils.isEmpty(goodsIds)) {
            logger.info("Current user was not logged in.");
            return getTableData(null, null, 0);
        }

        ArrayList<String> dis = new ArrayList<>();
        String[] goodsIdStr = goodsIds.split(",");
        for (String str : goodsIdStr) {
            dis.add(str);
        }

        GoodsInfo query = new GoodsInfo();
        query.setGoodsIds(dis);

        ArrayList<GoodsInfo> goodsInfos = this.goodsInfoMapper.selectByGoodsIds(query);
        if (goodsInfos != null && !goodsInfos.isEmpty()) {
            return getTableData(goodsInfos, null, goodsInfos.size());
        }

        return getTableData(null, null, 0);
    }

    @Override
    public String bindingGoodsSelectStore(HttpServletRequest request, HttpSession session) {
        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return "page/relogin";
        }

        return "page/goods/binding_goods_select_stores";
    }

    @Override
    public String bindingGoods(HttpServletRequest request, HttpSession session, String merGroupId) {

        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return "page/relogin";
        }

        MerchantsGroups currStore = this.merGroupsMapper.selectByPrimaryKey(merGroupId);
        if (currStore != null) {
            session.setAttribute("CurrStore", currStore);
        }

        return "page/goods/binding_goods";
    }

    @Override
    public String bindingGoodsData(HttpServletRequest request, HttpSession session, String merGroupId) {
        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return getTableData(null, null, 0);
        }

        MerchantsGroups currStore = (MerchantsGroups) session.getAttribute("CurrStore");
        ArrayList<BindingGoods> bindingGoodsList = this.bindingGoodsMapper.selectByStore(currStore.getMerGroupId());

        if (bindingGoodsList != null && !bindingGoodsList.isEmpty()) {
            return getTableData(bindingGoodsList, null, bindingGoodsList.size());
        }

        return getTableData(null, null, 0);
    }

    @Override
    public String bindingGoodsSave(HttpServletRequest request, HttpSession session, BindingGoods bindingGoods) {

        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
        }

        if (StringUtils.isEmpty(bindingGoods.getGoodsName())) {
            return getRetCodeWithJson(1, getMessage("saveGoodsInfo.goodsNameNotNull"));
        }

        if (StringUtils.isEmpty(bindingGoods.getGoodsCode())) {
            return getRetCodeWithJson(1, getMessage("saveGoodsInfo.goodsCodeNotNull"));
        }

        if (bindingGoods.getGoodsPrice() == null) {
            return getRetCodeWithJson(1, getMessage("saveGoodsInfo.goodsPriceNotNull"));
        }

        MerchantsGroups currStore = (MerchantsGroups) session.getAttribute("CurrStore");

        // 新增捆绑商品
        if (StringUtils.isEmpty(bindingGoods.getBindGoodsId())) {
            // 检查条码是否存在
            BindingGoods query = new BindingGoods();
            query.setMerGroupId(currStore.getMerGroupId());
            query.setGoodsCode(bindingGoods.getGoodsCode());
            if (this.bindingGoodsMapper.selectByCode(query) != null) {
                logger.info("门店 " + currStore.getGroupName() + " 新建的捆绑商品的条码 " + bindingGoods.getGoodsCode() + "已经存在");
                return getRetCodeWithJson(1, getMessage("saveGoodsInfo.goodsCodeExists"));
            }

            bindingGoods.setBindGoodsId(MyUUID.randomUUID());
            bindingGoods.setMerId(this.merUser.getMerId());
            bindingGoods.setMerGroupId(currStore.getMerGroupId());
            bindingGoods.setBindActive(false);

            this.bindingGoodsMapper.insertSelective(bindingGoods);

            return getRetCodeWithJson(0, getMessage("saveGoodsInfo.createSuccess"));
        } else {
            // 修改捆绑商品
            BindingGoods query = new BindingGoods();
            query.setMerGroupId(currStore.getMerGroupId());
            query.setGoodsCode(bindingGoods.getGoodsCode());

            BindingGoods queried = this.bindingGoodsMapper.selectByCode(query);
            if (queried != null && !queried.getBindGoodsId().equalsIgnoreCase(bindingGoods.getBindGoodsId())) {
                logger.info("门店 " + currStore.getGroupName() + " 修改的捆绑商品的条码 " + bindingGoods.getGoodsCode() + "已经存在");
                return getRetCodeWithJson(1, getMessage("saveGoodsInfo.goodsCodeExists"));
            }

            bindingGoods.setMerId(this.merUser.getMerId());
            bindingGoods.setMerGroupId(currStore.getMerGroupId());

            this.bindingGoodsMapper.updateByPrimaryKeySelective(bindingGoods);

            return getRetCodeWithJson(0, getMessage("saveGoodsInfo.modifySuccess"));
        }
    }

    @Override
    public String bindingGoodDelete(HttpServletRequest request, HttpSession session, String bindGoodsId) {
        if (!this.merUserHasAccessRight(request, session) || StringUtils.isEmpty(bindGoodsId)) {
            logger.info("Current user was not logged in.");
            return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
        }

        // 先删除 BINDING_GOODS
        this.bindingGoodsMapper.deleteByPrimaryKey(bindGoodsId);

        // 再删除 BINDING_GOODS_DETAILS
        this.bindingGoodsDetailsMapper.deleteByBindGoodsId(bindGoodsId);

        return getRetCodeWithJson(0, "");
    }

    @Override
    public String bindingGoodActive(HttpServletRequest request, HttpSession session, BindingGoods bindingGoods, Boolean active) {
        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
        }

        logger.info("捆绑商品状态 " + bindingGoods.getBindActive());
        bindingGoods.setBindActive(active);

        this.bindingGoodsMapper.updateByPrimaryKeySelective(bindingGoods);

        return getRetCodeWithJson(0, "");
    }

    @Override
    public String bindingGoodDetailData(HttpServletRequest request, HttpSession session, String bindGoodsId) {
        if (!this.merUserHasAccessRight(request, session) || StringUtils.isEmpty(bindGoodsId)) {
            logger.info("Current user was not logged in.");
            return getTableData(null, null, 0);
        }

        ArrayList<BindingGoodsDetails> bindingGoodsDetails = bindingGoodsDetailsMapper.selectByBindGoodsId(bindGoodsId);
        if (bindingGoodsDetails != null && !bindingGoodsDetails.isEmpty()) {
            return getTableData(bindingGoodsDetails, null, bindingGoodsDetails.size());
        } else {
            return getTableData(null, null, 0);
        }
    }

    @Override
    public String bindingGoodDetailQueryAdd(HttpServletRequest request, HttpSession session,
                                            BindingGoodsDetails bindingDetail) {

        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
        }

        logger.info("goodsCode is " + bindingDetail.getGoodsCode());
        logger.info("bindGoodsId is " + bindingDetail.getBindGoodsId());

        if (StringUtils.isEmpty(bindingDetail.getBindGoodsId()) || StringUtils.isEmpty(bindingDetail.getGoodsCode())) {
            return getRetCodeWithJson(1, getMessage("bindingGoodDetailQueryAdd.goodsCodeNotNull"));
        }

        MerchantsGroups currStore = (MerchantsGroups) session.getAttribute("CurrStore");

        // 检索被绑定的商品是否被授权到当前门店
        GoodsInfo query = new GoodsInfo();
        query.setMerGroupId(currStore.getMerGroupId());
        query.setGoodsCode(bindingDetail.getGoodsCode());

        GoodsInfo goodsInfo = this.goodsInfoMapper.selectStoreGoodsPriceBy(query);
        if (goodsInfo == null) {
            logger.info("没有找到该商品");
            return getRetCodeWithJson(1, getMessage("bindingGoodDetailQueryAdd.cannotFindGoods"));
        }

        // 查询该商品是否已经绑定
        BindingGoodsDetails query2 = new BindingGoodsDetails();
        query2.setGoodsId(goodsInfo.getGoodsId());
        query2.setBindGoodsId(bindingDetail.getBindGoodsId());
        BindingGoodsDetails queied = this.bindingGoodsDetailsMapper.selectByBindGoods(query2);
        if (queied == null) {
            // 没有绑定，绑定之，数量默认为1
            bindingDetail.setGoodsId(goodsInfo.getGoodsId());
            bindingDetail.setBindGoodsCount(1);
            bindingDetail.setGoodsName(goodsInfo.getGoodsName());

            this.bindingGoodsDetailsMapper.insertSelective(bindingDetail);
        }

        return getRetCodeWithJson(0, getMessage("bindingGoodDetailQueryAdd.bindSuccess"));
    }

    @Override
    public String bindingGoodDetailDelete(HttpServletRequest request, HttpSession session, Integer bindDetailId) {
        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
        }

        this.bindingGoodsDetailsMapper.deleteByPrimaryKey(bindDetailId);

        return getRetCodeWithJson(0, "");
    }

    @Override
    public String bindingGoodDetailChange(HttpServletRequest request, HttpSession session,
                                          BindingGoodsDetails goodsDetail) {
        if (!this.merUserHasAccessRight(request, session) || goodsDetail.getBindDetailId() == null) {
            logger.info("Current user was not logged in.");
            return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
        }

        if (goodsDetail.getBindGoodsCount() == null || goodsDetail.getBindGoodsCount() == 0) {
            return getRetCodeWithJson(1, getMessage("bindingGoodDetailChange.bindGoodsCountNotZero"));
        }

        this.bindingGoodsDetailsMapper.updateByPrimaryKeySelective(goodsDetail);

        return getRetCodeWithJson(0, "");
    }

    @Override
    public String goodsDataNoPage(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo, Integer seeAllGoods) {
        if (!this.merUserHasAccessRight(request, session)) {
            return getTableData(null, null, 0);
        }

        session.removeAttribute("SelectedGoodsTypeId");
        session.removeAttribute("SelectedParentGoodsTypeId");

        if (seeAllGoods == null || seeAllGoods.intValue() == 0) {
            return getTableData(null, null, 0);
        }

        if (StringUtils.isEmpty(goodsInfo.getGoodsTypeId())) {
            goodsInfo.setGoodsTypeId(null);
        }

        if (StringUtils.isEmpty(goodsInfo.getParentTypeId())) {
            goodsInfo.setParentTypeId(null);
        }

        if (goodsInfo.getStatus() != null && goodsInfo.getStatus() == -1) {
            goodsInfo.setStatus(null);
        }

        goodsInfo.setMerId(this.merUser.getMerId());

        int totalCount = this.goodsInfoMapper.selectCountByGoodsType(goodsInfo);
        logger.info("totalCount = " + totalCount);

        goodsInfo.setPage(1);
        goodsInfo.setLimit(totalCount);

        ArrayList<GoodsInfo> goodsInfoList = this.goodsInfoMapper.selectByGoodsType(goodsInfo);
        if (goodsInfoList != null && !goodsInfoList.isEmpty()) {
            return getTableData(goodsInfoList, "", totalCount);
        } else {
            return getTableData(null, null, 0);
        }
    }

    @Override
    public String storeGoodsPriceCopy(HttpServletRequest request, HttpSession session, String merGroupId,
                                      String storeIds) {
        if (!this.merUserHasAccessRight(request, session)) {
            logger.info("Current user was not logged in.");
            return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
        }

        if (StringUtils.isEmpty(storeIds)) {
            logger.info("还没有选择门店");
            return getRetCodeWithJson(1, getMessage("storeGoodsPriceCopy.noStoresSelected"));
        }

        MerchantsGroups store = (MerchantsGroups) session.getAttribute("CurrentStoreForPrice");

        // 从选择的门店中删除当前门店
        storeIds = StringUtils.replace(storeIds, store.getMerGroupId(), "");
        logger.info("storeIds = " + storeIds);

        String[] storeIdArray = storeIds.split(",");
        if (storeIdArray == null || storeIdArray.length == 0) {
            logger.info("还没有选择门店");
            return getRetCodeWithJson(1, getMessage("storeGoodsPriceCopy.noStoresSelected"));
        }


        // 先查询当前门店的价格策略
        ArrayList<GoodsPrice> goodsPrices = this.goodsPriceMapper.selectByStore(store.getMerGroupId());
        for (String storeId : storeIdArray) {
            if (StringUtils.isEmpty(storeId)) {
                continue;
            }

            logger.info("复制到 " + storeId);
            for (GoodsPrice price : goodsPrices) {
                // 先检查该门店的该商品价格是否存在，如果不存在，则插入，如果存在，则跳过
                GoodsPrice query = new GoodsPrice();
                query.setGoodsId(price.getGoodsId());
                query.setMerGroupId(storeId);

                GoodsPrice priceQ = this.goodsPriceMapper.selectByStoreGoods(query);
                if (priceQ == null) {
                    logger.info("商品价格未授权，复制到该门店 " + storeId);

                    GoodsPrice newPrice = new GoodsPrice();
                    newPrice.setGoodsId(price.getGoodsId());
                    newPrice.setMerGroupId(storeId);
                    newPrice.setGoodsPrice(price.getGoodsPrice());
                    newPrice.setStoreGoodsCost(price.getStoreGoodsCost());
                    this.goodsPriceMapper.insertSelective(newPrice);
                }
            }
        }

        return getRetCodeWithJson(0, getMessage("storeGoodsPriceCopy.priceCopySuccess"));
    }

}
