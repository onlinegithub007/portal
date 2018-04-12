package com.fuhuitong.applychain.controller;

import com.fuhuitong.applychain.model.BindingGoods;
import com.fuhuitong.applychain.model.BindingGoodsDetails;
import com.fuhuitong.applychain.model.GoodsInfo;
import com.fuhuitong.applychain.model.GoodsType;
import com.fuhuitong.applychain.service.IGoodsService;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 商品控制类
 */
@Controller
public class GoodsController {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Resource
	private IGoodsService goodsService;
	
	@RequestMapping("/backstage/goods_types.html")
	public String goodsTypes(HttpServletRequest request, HttpSession session)
	{
		
		return goodsService.goodsTypes(request, session);
	}
	
	@RequestMapping("/backstage/goods_types_tree_data.html")
	public @ResponseBody String goodsTypesTreeData(HttpServletRequest request, HttpSession session)
	{
		return goodsService.goodsTypesTreeData(request, session);
	}
	
	@RequestMapping("/backstage/save_goods_types.html")
	public @ResponseBody String saveGoodsType(HttpServletRequest request, HttpSession session, GoodsType goodsType)
	{
		return goodsService.saveGoodsType(request, session, goodsType);
	}
	
	@RequestMapping("/backstage/delete_goods_types.html")
	public @ResponseBody String deleteGoodsType(HttpServletRequest request, HttpSession session, String goodsTypeId)
	{
		return goodsService.deleteGoodsType(request, session, goodsTypeId);
	}

	/**
	 * 商品信息
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backstage/goods.html")
	public String goods(HttpServletRequest request, HttpSession session)
	{
		return goodsService.goods(request, session);
	}
	
	@RequestMapping("/backstage/goods_sub_types.html")
	public @ResponseBody String goodsSubTypes(HttpServletRequest request, HttpSession session, String parentTypeId)
	{
		return goodsService.goodsSubTypes(request, session, parentTypeId);
	}
	
	@RequestMapping("/backstage/merchant_goods_data.html")
	public @ResponseBody String goodsData(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo)
	{
		return goodsService.goodsData(request, session, goodsInfo);
	}
	
	@RequestMapping("/backstage/merchant_goods_data_nopage.html")
	public @ResponseBody String goodsDataNoPage(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo, Integer seeAllGoods)
	{
		return goodsService.goodsDataNoPage(request, session, goodsInfo, seeAllGoods);
	}
	
	@RequestMapping("/backstage/change_mer_goods_status.html")
	public @ResponseBody String changeMerGoodsStatus(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo)
	{
		return goodsService.changeMerGoodsStatus(request, session, goodsInfo);
	}
	
	@RequestMapping("/backstage/goods_info.html")
	public String goodsInfo(HttpServletRequest request, HttpSession session, String goodsId)
	{
		return goodsService.goodsInfo(request, session, goodsId);
	}
	
	@RequestMapping("/backstage/create_same_goods_info.html")
	public String createSameGoodsInfo(HttpServletRequest request, HttpSession session, String goodsId)
	{
		return goodsService.createSameGoodsInfo(request, session, goodsId);
	}
	
	@RequestMapping("/backstage/upload_goods_pic.html")
	public @ResponseBody String uploadGoodsPic(@RequestParam("fileName") MultipartFile uploadFile, HttpServletRequest request, HttpSession session)
	{
		return goodsService.uploadGoodsPic(uploadFile, request, session);
	}
	
	@RequestMapping(value = "/backstage/save_goods_info.html", method = RequestMethod.POST)
	public @ResponseBody String saveGoodsInfo(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo)
	{
		return goodsService.saveGoodsInfo(request, session, goodsInfo);
	}
	
	@RequestMapping("/backstage/delete_goods_info.html")
	public @ResponseBody String deleteGoodsInfo(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo)
	{
		return goodsService.deleteGoodsInfo(request, session, goodsInfo);
	}
	
	@RequestMapping("/backstage/stores_goods_price.html")
	public String storeGoodsPrice(HttpServletRequest request, HttpSession session, String merGroupId)
	{
		return goodsService.storeGoodsPrice(request, session, merGroupId);
	}
	
	/**
	 * 将商品门店授权价格拷贝到其他门店
	 * @param request
	 * @param session
	 * @param merGroupId
	 * @return
	 */
	@RequestMapping("/backstage/stores_goods_price_copy.html")
	public @ResponseBody String storeGoodsPriceCopy(HttpServletRequest request, HttpSession session, String merGroupId, String storeIds)
	{
		return goodsService.storeGoodsPriceCopy(request, session, merGroupId, storeIds);
	}
	
	@RequestMapping("/backstage/stores_goods_price_select.html")
	public String storeGoodsPriceSelect(HttpServletRequest request, HttpSession session)
	{
		return goodsService.storeGoodsPriceSelect(request, session);
	}
	
	@RequestMapping("/backstage/stores_goods_price_data.html")
	public @ResponseBody String storesGoodsPriceData(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo, String dispatch)
	{
		return goodsService.storesGoodsPriceData(request, session, goodsInfo, dispatch);
	}
	
	@RequestMapping("/backstage/save_stores_goods_price.html")
	public @ResponseBody String saveStoresGoodsPrice(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo)
	{
		return goodsService.saveStoresGoodsPrice(request, session, goodsInfo);
	}
	
	@RequestMapping("/backstage/download_goodsinfo_temp.html")
	public ResponseEntity<byte[]> downloadGoodsInfoTemplate(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo)
	{
		return goodsService.downloadGoodsInfoTemplate(request, session, goodsInfo);
	}
	
	@RequestMapping("/backstage/download_store_price_temp.html")
	public ResponseEntity<byte[]> downloadStorePriceTemplate(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo)
	{
		return goodsService.downloadStorePriceTemplate(request, session, goodsInfo);
	}
	
	@RequestMapping("/backstage/import_goods_info.html")
	public @ResponseBody String importGoodsInfo(@RequestParam("fileName") MultipartFile uploadFile, HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo)
	{
		return goodsService.importGoodsInfo(uploadFile, request, session, goodsInfo);
	}
	
	@RequestMapping("/backstage/import_store_goods_price.html")
	public @ResponseBody String importStoreGoodsPrice(@RequestParam("fileName") MultipartFile uploadFile, HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo)
	{
		return goodsService.importStoreGoodsPrice(uploadFile, request, session, goodsInfo);
	}
	
	@RequestMapping("/backstage/members_goods_price.html")
	public String membersGoodsPrice(HttpServletRequest request, HttpSession session, String clientLevelId)
	{
		return goodsService.membersGoodsPrice(request, session, clientLevelId);
	}
	
	@RequestMapping("/backstage/member_goods_price_data.html")
	public @ResponseBody String memberGoodsPriceData(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo)
	{
		return goodsService.memberGoodsPriceData(request, session, goodsInfo);
	}
	
	@RequestMapping("/backstage/delete_member_goods_price.html")
	public @ResponseBody String deleteMemberGoodsPrice(HttpServletRequest request, HttpSession session, String goodsMembPriceId)
	{
		return goodsService.deleteMemberGoodsPrice(request, session, goodsMembPriceId);
	}
	
	@RequestMapping("/backstage/download_member_price_temp.html")
	public ResponseEntity<byte[]> downloadMemberPriceTemplate(HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo)
	{
		return goodsService.downloadMemberPriceTemplate(request, session, goodsInfo);
	}
	
	@RequestMapping("/backstage/import_member_goods_price.html")
	public @ResponseBody String importMemberGoodsPrice(@RequestParam("fileName") MultipartFile uploadFile, HttpServletRequest request, HttpSession session, GoodsInfo goodsInfo)
	{
		return goodsService.importMemberGoodsPrice(uploadFile, request, session, goodsInfo);
	}
	
	@RequestMapping("/backstage/import_provider_goods.html")
	public @ResponseBody String importMerProviderGoods(@RequestParam("fileName") MultipartFile uploadFile, HttpServletRequest request, HttpSession session)
	{
		return goodsService.importMerProviderGoods(uploadFile, request, session);
	}
	
	@RequestMapping("/backstage/query_goods_by_ids.html")
	public @ResponseBody String queryGoodsByIds(HttpServletRequest request, HttpSession session, String goodsIds)
	{
		return goodsService.queryGoodsByIds(request, session, goodsIds);
	}
	
	@RequestMapping("/backstage/binding_goods_select_stores.html")
	public String bindingGoodsSelectStore(HttpServletRequest request, HttpSession session)
	{
		return goodsService.bindingGoodsSelectStore(request, session);
	}
	
	@RequestMapping("/backstage/binding_goods.html")
	public String bindingGoods(HttpServletRequest request, HttpSession session, String merGroupId)
	{
		return goodsService.bindingGoods(request, session, merGroupId);
	}
	
	@RequestMapping("/backstage/binding_goods_data.html")
	public @ResponseBody String bindingGoodsData(HttpServletRequest request, HttpSession session, String merGroupId)
	{
		return goodsService.bindingGoodsData(request, session, merGroupId);
	}
	
	@RequestMapping("/backstage/binding_goods_save.html")
	public @ResponseBody String bindingGoodsSave(HttpServletRequest request, HttpSession session, BindingGoods bindingGoods)
	{
		return goodsService.bindingGoodsSave(request, session, bindingGoods);
	}
	
	@RequestMapping("/backstage/binding_goods_delete.html")
	public @ResponseBody String bindingGoodDelete(HttpServletRequest request, HttpSession session, String bindGoodsId)
	{
		return goodsService.bindingGoodDelete(request, session, bindGoodsId);
	}
	
	@RequestMapping("/backstage/binding_goods_detail_delete.html")
	public @ResponseBody String bindingGoodDetailDelete(HttpServletRequest request, HttpSession session, Integer bindDetailId)
	{
		return goodsService.bindingGoodDetailDelete(request, session, bindDetailId);
	}
	
	@RequestMapping("/backstage/binding_goods_detail_change.html")
	public @ResponseBody String bindingGoodDetailChange(HttpServletRequest request, HttpSession session, BindingGoodsDetails goodsDetail)
	{
		return goodsService.bindingGoodDetailChange(request, session, goodsDetail);
	}
	
	@RequestMapping("/backstage/binding_goods_active.html")
	public @ResponseBody String bindingGoodActive(HttpServletRequest request, HttpSession session, BindingGoods bindingGoods, Boolean active)
	{
		return goodsService.bindingGoodActive(request, session, bindingGoods, active);
	}
	
	@RequestMapping("/backstage/binding_goods_detail_data.html")
	public @ResponseBody String bindingGoodDetailData(HttpServletRequest request, HttpSession session, String bindGoodsId)
	{
		return goodsService.bindingGoodDetailData(request, session, bindGoodsId);
	}
	
	@RequestMapping("/backstage/binding_goods_detail_queryAndBind.html")
	public @ResponseBody String bindingGoodDetailQueryAdd(HttpServletRequest request, HttpSession session, BindingGoodsDetails bindingDetail)
	{
		return goodsService.bindingGoodDetailQueryAdd(request, session, bindingDetail);
	}
}
