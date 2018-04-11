package com.fuhuitong.applychain.service;

import com.fuhuitong.applychain.model.BindingGoods;
import com.fuhuitong.applychain.model.BindingGoodsDetails;
import com.fuhuitong.applychain.model.GoodsInfo;
import com.fuhuitong.applychain.model.GoodsType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface IGoodsService extends IBaseService{
	
	public String goodsTypes(HttpServletRequest request, HttpSession session);
	
	public String goodsTypesTreeData(HttpServletRequest request, HttpSession session);
	
	public String saveGoodsType(HttpServletRequest request, HttpSession session, GoodsType goodsType);
	
	public String deleteGoodsType(HttpServletRequest request, HttpSession session, String goodsTypeId);
	
	public String goods(HttpServletRequest request, HttpSession session);
	
	public String goodsSubTypes(HttpServletRequest request, HttpSession session, String parentTypeId);
	
	public String goodsData(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo);
	
	public String goodsDataNoPage(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo, Integer seeAllGoods);
	
	public String changeMerGoodsStatus(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo);
	
	public String goodsInfo(HttpServletRequest request, HttpSession session, String goodsId);
	
	public String createSameGoodsInfo(HttpServletRequest request, HttpSession session, String goodsId);
	
	public String uploadGoodsPic(MultipartFile uploadFile, HttpServletRequest request, HttpSession session);
	
	public String saveGoodsInfo(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo);
	
	public String deleteGoodsInfo(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo);
	
	public String storeGoodsPrice(HttpServletRequest request, HttpSession session, String merGroupId);
	
	public String storeGoodsPriceCopy(HttpServletRequest request, HttpSession session, String merGroupId, String storeIds);
	
	public String membersGoodsPrice(HttpServletRequest request, HttpSession session, String clientLevelId);
	
	public String storeGoodsPriceSelect(HttpServletRequest request, HttpSession session);
	
	public String storesGoodsPriceData(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo, String dispatch);
	
	public String saveStoresGoodsPrice(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo);
	
	public ResponseEntity<byte[]> downloadGoodsInfoTemplate(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo);
	
	public ResponseEntity<byte[]> downloadStorePriceTemplate(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo);
	
	public String importGoodsInfo(MultipartFile uploadFile, HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo);
	
	public String importStoreGoodsPrice(MultipartFile uploadFile, HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo);

	public String memberGoodsPriceData(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo);
	
	public String deleteMemberGoodsPrice(HttpServletRequest request, HttpSession session, String goodsMembPriceId);
	
	public ResponseEntity<byte[]> downloadMemberPriceTemplate(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo);
	
	public String importMemberGoodsPrice(MultipartFile uploadFile, HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo);
	
	public String importMerProviderGoods(MultipartFile uploadFile, HttpServletRequest request, HttpSession session);
	
	public String queryGoodsByIds(HttpServletRequest request, HttpSession session, String goodsIds);
	
	public String bindingGoodsSelectStore(HttpServletRequest request, HttpSession session);
	
	public String bindingGoods(HttpServletRequest request, HttpSession session, String merGroupId);
	
	public String bindingGoodsData(HttpServletRequest request, HttpSession session, String merGroupId);
	
	public String bindingGoodsSave(HttpServletRequest request, HttpSession session, BindingGoods bindingGoods);
	
	public String bindingGoodDelete(HttpServletRequest request, HttpSession session, String bindGoodsId);
	
	public String bindingGoodDetailDelete(HttpServletRequest request, HttpSession session, Integer bindDetailId);
	
	public String bindingGoodDetailChange(HttpServletRequest request, HttpSession session, BindingGoodsDetails goodsDetail);
	
	public String bindingGoodActive(HttpServletRequest request, HttpSession session, BindingGoods bindingGoods, Boolean active);
	
	public String bindingGoodDetailData(HttpServletRequest request, HttpSession session, String bindGoodsId);
	
	public String bindingGoodDetailQueryAdd(HttpServletRequest request, HttpSession session, BindingGoodsDetails bindingDetail);
}
