package com.fuhuitong.applychain.utils;

import com.fuhuitong.applychain.model.OrderDetails;
import com.fuhuitong.applychain.model.Orders;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.ArrayList;

public class OrderBillUtils {
	
	private int deviceType;
	
	public OrderBillUtils(int deviceType) 
	{
		this.deviceType = deviceType;
	}
	
	private String getTitle(String title)
	{
		if (title.length() > 16)
		{
			title = title.substring(0, 16);
			
			return title;
		}
		
		int space = (16 - title.length()) / 2;
		
		String str = "";
		
		for (int i = 0; i < space; i++)
		{
			str = "　" + str;
		}
		
		str += title;
		
		return str;
	}
	
	private String getRightAlignLabel(String label, int width)
	{
		String str = "";
		
		int spaceNum = width - label.length();
		
		if (spaceNum < 0)
		{
			str = label.substring(0, width);
			return str;
		}
		
		for (int i = 0; i < spaceNum; i++)
		{
				str += " ";
		}
		str = str + label;
		
		return str;
	}
	
	private String fillWSpace(int width)
	{
		String str = "";
		
		for (int i = 0; i < width; i++)
		{
				str += "　";
		}
		
		return str;
	}
	
//	public static void main(String[] args) {
//		
//		System.out.println(getTitle("北京大学分店"));
//		System.out.println(getTitle("珞狮南路珞狮南路武汉武汉理工大学分店"));
//		
//		System.out.println(getRightAlignLabel("2", 3));
//		System.out.println(getRightAlignLabel("20.00", 10));
//		System.out.println(getRightAlignLabel("22200.00", 10));
//	}
	
	/**
	 * 智能果乐POS，一行打印16个汉字，32个半角字符
	 * @param order
	 * @param orderDetails
	 * @return
	 */
	public String getOrderBillText(Orders order, ArrayList<OrderDetails> orderDetails)
	{
		StringBuffer buffer = new StringBuffer();
		

		buffer.append(getTitle(order.getGroupName())).append("\n");
		buffer.append("交易号: " + order.getOrderCode() + "\n");
		buffer.append("交易时间:" + DateFormatUtils.format(order.getPayDate(), "yyyy-MM-dd HH:mm:ss")).append("\n");
		buffer.append("--------------------------------\n");
		buffer.append("序号  商品名称  数量  单价  金额\n");
		buffer.append("--------------------------------\n");
		
		int sn = 1;
		int totalCount = 0;
		for (OrderDetails detail : orderDetails)
		{
			// 被捆绑的商品，以捆绑另取名销售的商品打印，在小票上不显示
			if (detail.getGoodsBinding() != null && detail.getGoodsBinding().intValue() == 2)
			{
				continue;
			}
			
			// 序号 + 商品名 占一行
			if (sn < 10)
			{
				buffer.append("0").append(sn).append(". ");
			}
			else
			{
				buffer.append(sn).append(". ");
			}
			
			buffer.append(detail.getGoodsName()).append("\n");
			
			// 数量， 单价   金额
			String lineStr = getRightAlignLabel(detail.getGoodsAmount().toString(), 8);
			// 单价
			lineStr += getRightAlignLabel(MoneyUtils.getMoneyText(detail.getGoodsPrice()), 8);
			// 总金额
			lineStr += getRightAlignLabel(MoneyUtils.getMoneyText(detail.getGoodsTotalPrice()), 14);
			
			buffer.append(lineStr).append("\n");
			
			totalCount += detail.getGoodsAmount();
			
			sn++;
		}
		buffer.append("--------------------------------\n");
		
		// 输出总计
		String totalStr = "总件数： " + totalCount;
		String orderTotalStr = "　　应收： " + order.getTotalPriceText();
		
		buffer.append(totalStr).append(orderTotalStr).append("\n");
		
		String finalStr = "实收：" + order.getFinalPriceText();
		buffer.append(finalStr);
		
		if (order.getDiscountAmount() != null)
		{
			String discount = "折让: " + order.getDiscountAmountText();
			buffer.append(getRightAlignLabel(discount, 14));
		}
		buffer.append("\n");
		
		String payMethod = null;
		if (order.getPayMethod() == 0)
		{
			payMethod = "现金支付";
		}
		else if (order.getPayMethod() == 1)
		{
			payMethod = "微信支付";
		}
		else if (order.getPayMethod() == 2)
		{
			payMethod = "支付宝";
		}
		else if (order.getPayMethod() == 3)
		{
			payMethod = "翼支付";
		}
		else if (order.getPayMethod() == 4)
		{
			payMethod = "刷卡支付";
		}
		
		if (payMethod != null)
		{
			buffer.append("支付方式：").append(payMethod).append("\n");
			
			if (order.getPayMethod() == 4)
			{
				buffer.append("商户名称：").append(order.getMerName()).append("\n");
				buffer.append("终端号：").append(order.getTermCode()).append("\n");
				buffer.append("商户号：").append(order.getMerCode()).append("\n");
				buffer.append("卡号：").append(BankCardUtils.getMarkedBankCard(order.getBankCard())).append("\n");
				buffer.append("卡类型：").append(order.getBankCardType());
				buffer.append("批次号：").append(order.getTradeBatchCode()).append("\n");
				buffer.append("凭证号：").append(order.getPayBillNumber()).append("\n");
				buffer.append("授权号：").append(order.getAuthorCode()).append("\n");
				buffer.append("参考号：").append(order.getSecondPayNumber()).append("\n");
			}
			else if (order.getPayMethod() != 0)
			{
				buffer.append("综合支付流水号：\n").append(order.getPayBillNumber()).append("\n");
			}
		}
		
		buffer.append("--------------------------------\n\n");
		
		System.out.println(buffer.toString());
		
		return buffer.toString();
	}
	
	public String getOrderBillText(Orders order)
	{
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(getTitle("POS 签购单")+ "\n");
		buffer.append(getTitle("（消费单）")+ "\n");
		buffer.append("商户名称："+order.getGroupName()+"\n");
		if(order.getTermCode()!=null) {
		buffer.append("终端编号："+order.getTermCode()+"\n");
		}
		if(order.getTermSn()!=null) {
		buffer.append("终端sn号："+order.getTermSn()+"\n");	
		}
//		buffer.append(getTitle(order.getGroupName())).append("\n");
		buffer.append("交易号: " + order.getOrderCode() + "\n");
		buffer.append("交易时间:" + DateFormatUtils.format(order.getPayDate(), "yyyy-MM-dd HH:mm:ss")).append("\n");
		
		// 输出总计
		String orderTotalStr = "应收：" + order.getTotalPriceText();
		
		buffer.append(orderTotalStr).append("\n");
		
		String finalStr = "实收：" + order.getFinalPriceText();
		buffer.append(finalStr).append("\n");
		
		if (order.getDiscountAmount() != null)
		{
			String discount = "折让: " + order.getDiscountAmountText();
			buffer.append(discount);
		}
		buffer.append("\n");
		
		String payMethod = null;
		if (order.getPayMethod() == 0)
		{
			payMethod = "现金支付";
		}
		else if (order.getPayMethod() == 1)
		{
			payMethod = "微信支付";
		}
		else if (order.getPayMethod() == 2)
		{
			payMethod = "支付宝";
		}
		else if (order.getPayMethod() == 3)
		{
			payMethod = "翼支付";
		}
		else if (order.getPayMethod() == 4)
		{
			payMethod = "刷卡支付";
		}
		
		if (payMethod != null)
		{
			buffer.append("支付方式：").append(payMethod).append("\n");
			
			if (order.getPayMethod() == 4)
			{
				buffer.append("商户名称：").append(order.getMerName()).append("\n");
				buffer.append("终端号：").append(order.getTermCode()).append("\n");
				buffer.append("商户号：").append(order.getMerCode()).append("\n");
				buffer.append("卡号：").append(BankCardUtils.getMarkedBankCard(order.getBankCard())).append("\n");
				buffer.append("卡类型：").append(order.getBankCardType());
				buffer.append("批次号：").append(order.getTradeBatchCode()).append("\n");
				buffer.append("凭证号：").append(order.getPayBillNumber()).append("\n");
				buffer.append("授权号：").append(order.getAuthorCode()).append("\n");
				buffer.append("参考号：").append(order.getSecondPayNumber()).append("\n");
			}
			else if (order.getPayMethod() != 0)
			{
				buffer.append("综合支付流水号：\n").append(order.getPayBillNumber()).append("\n");
			}
		}
		if(order.getOrderBillPrint()) {
			buffer.append(getTitle("==重新打印==")+ "\n");
		}
		
		buffer.append("--------------------------------\n\n");
		
		System.out.println(buffer.toString());
		
		return buffer.toString();
	}
	
}
