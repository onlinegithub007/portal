/**
 * @author haoqingfeng
 * 该类完成了智能POS上所有的接口服务
 */

package com.fuhuitong.applychain.controller;

import com.alibaba.fastjson.JSON;
import com.fuhuitong.applychain.model.AppDownload;
import com.fuhuitong.applychain.model.GoodsInfo;
import com.fuhuitong.applychain.model.MerchantUsers;
import com.fuhuitong.applychain.model.request.*;
import com.fuhuitong.applychain.model.response.*;
import com.fuhuitong.applychain.service.IPOSAppService;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class POSAppController{
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Resource
	private IPOSAppService posAppService;
	
	/**
	 * 终端检测最新版本
	 */
	@RequestMapping(value="/checkNewVersion", method = RequestMethod.POST)
	public @ResponseBody String checkNewVersion(HttpServletRequest request, AppDownload appDownloadParam)
	{
		String respStr = null;
		
		logger.info(request.getRemoteHost() + " checkNewVersion.");
		
		CheckNewVersionResp resp = this.posAppService.checkNewVersion(appDownloadParam);
		
		// 根据softId查询最新的版本
		respStr = JSON.toJSONString(resp);
		logger.info(respStr);
		
		return respStr;
	}
	
	@RequestMapping("/download_app")
	public ResponseEntity<byte[]> doDownload(HttpServletRequest request, HttpServletResponse response, AppDownload appDownloadParam)
	{
		if (appDownloadParam.getSoftId() == null)
		{
			return null;
		}
		
		return this.posAppService.downloadApp(appDownloadParam, request.getRemoteAddr());
	}
	
	@RequestMapping(value="/posUserLogin", method = RequestMethod.POST)
	public @ResponseBody String posUserLogin(HttpServletRequest request, MerchantUsers user)
	{
		// POS用户登录
		PosUserLoginResp resp = this.posAppService.posUserLogin(user, request.getRemoteAddr());
		String respStr = JSON.toJSONString(resp);
		
		logger.info(respStr);
		
		return respStr;
	}
	
	@RequestMapping(value="/posQueryGoodsByCode", method = RequestMethod.POST)
	public @ResponseBody String posQueryGoodsByCode(HttpServletRequest request, GoodsInfo goodsInfo)
	{
		PosQueryGoodsResp resp = this.posAppService.posQueryGoodsInfo(goodsInfo, request.getRemoteAddr());
		
		String respStr = JSON.toJSONString(resp);
		logger.info(respStr);
		
		return respStr;
	}
	
	@RequestMapping(value="/posCreateOrder", method = RequestMethod.POST)
	public @ResponseBody String posCreateOrder(HttpServletRequest request, CreateOrderReq reqParam)
	{
		CreateOrderResp resp = this.posAppService.createOrder(reqParam, request.getRemoteAddr());
		
		String respStr = JSON.toJSONString(resp);
		logger.info(respStr);
		
		return respStr;
	}
	
	@RequestMapping(value="/posCreateOrderWithoutGoods", method = RequestMethod.POST)
	public @ResponseBody String posCreateOrderWithoutGoods(HttpServletRequest request, CreateOrderReq reqParam)
	{
		CreateOrderResp resp = this.posAppService.posCreateOrderWithoutGoods(reqParam, request.getRemoteAddr());
		
		String respStr = JSON.toJSONString(resp);
		logger.info(respStr);
		
		return respStr;
	}
	
	@RequestMapping(value="/posQueryVIPInfos", method = RequestMethod.POST)
	public @ResponseBody String posQueryVIPInfos(HttpServletRequest request, QueryVIPInfosReq reqParam)
	{
		QueryVIPInfosResp resp = this.posAppService.posQueryVIPInfos(reqParam, request.getRemoteAddr());
		
		String respStr = JSON.toJSONString(resp);
		logger.info(respStr);
		
		return respStr;
	}
	
	@RequestMapping(value="/posPayOrderSuccess", method = RequestMethod.POST)
	public @ResponseBody String posPayOrderSuccess(HttpServletRequest request, POSPaySuccessReq reqParam)
	{
		logger.info(reqParam);
		POSPaySuccessResp resp = this.posAppService.posPaySuccess(reqParam, request.getRemoteAddr());
		
		String respStr = JSON.toJSONString(resp);
		logger.info(respStr);
		
		return respStr;
	}


	@RequestMapping(value="/posRepayment")
	public @ResponseBody String posRepayment(HttpServletRequest request, POSPaySuccessReq reqParam)
	{
		PosRepaymentResp resp = this.posAppService.posRepayment(reqParam, request.getRemoteAddr());
		String respStr = JSON.toJSONString(resp);
		logger.info(respStr);

		return respStr;
	}

	@RequestMapping(value="/posQueryOrders", method = RequestMethod.POST)
	public @ResponseBody String posQueryOrders(HttpServletRequest request, QueryOrdersReq reqParam)
	{
		QueryOrdersResp resp = this.posAppService.posQueryOrders(reqParam, request.getRemoteAddr());
		
		String respStr = JSON.toJSONString(resp);
		logger.info(respStr);
		
		return respStr;
	}
	
	@RequestMapping(value="/posQueryOrderDetail", method = RequestMethod.POST)
	public @ResponseBody String posGetOrderDetail(HttpServletRequest request, QueryOrderDetailReq reqParam)
	{
		QueryOrderDetailResp resp = this.posAppService.posGetOrderDetail(reqParam, request.getRemoteAddr());
		
		String respStr = JSON.toJSONString(resp);
		logger.info(respStr);
		
		return respStr;
	}

/*
	@RequestMapping(value="/posQueryOrderDetailByBill", method = RequestMethod.POST)
	public @ResponseBody String posGetOrderDetailByBill(HttpServletRequest request, QueryOrderDetailReq reqParam)
	{
		QueryOrderDetailResp resp = this.posAppService.posGetOrderDetailByBill(reqParam, request.getRemoteAddr());

		String respStr = JSON.toJSONString(resp);
		logger.info(respStr);

		return respStr;
	}*/

	@RequestMapping(value="/posGetOrderPrintInfo", method = RequestMethod.POST)
	public @ResponseBody String posGetOrderPrintInfo(HttpServletRequest request, QueryOrderDetailReq reqParam)
	{
		OrderPrintResp resp = this.posAppService.posGetOrderPrintInfo(reqParam, request.getRemoteAddr());
		String respStr = JSON.toJSONString(resp);
		logger.info(respStr);
		
		return respStr;
	}

	@RequestMapping(value="/posQueryMember", method = RequestMethod.POST)
	public @ResponseBody String posQueryMember(HttpServletRequest request, QueryMemberReq reqParam)
	{
		QueryMemberResp resp = this.posAppService.posQueryMember(reqParam, request.getRemoteAddr());
		String respStr = JSON.toJSONString(resp);
		logger.info(respStr);
		
		return respStr;
	}
	
	@RequestMapping(value="/posGetGoodsStock", method = RequestMethod.POST)
	public @ResponseBody String posGetGoodsStock(HttpServletRequest request, GoodsStockParam goodsStockParam)
	{
		GoodsStockParam resp = this.posAppService.posGetGoodsStock(goodsStockParam, request.getRemoteAddr());
		String respStr = JSON.toJSONString(resp);
		logger.info(respStr);
		
		return respStr;
	}
	
	@RequestMapping(value="/posSetGoodsStock", method = RequestMethod.POST)
	public @ResponseBody String posSetGoodsStock(HttpServletRequest request, GoodsStockParam goodsStockParam)
	{
		GoodsStockParam resp = this.posAppService.posSetGoodsStock(goodsStockParam, request.getRemoteAddr());
		String respStr = JSON.toJSONString(resp);
		logger.info(respStr);
		
		return respStr;
	}
}
