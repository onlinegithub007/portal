package com.fuhuitong.applychain;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.DecimalFormat;
import java.util.Date;

public class OrderID {
	
	private static OrderID instance = null;
	private int counter = 1;
	
	private OrderID()
	{
	}
	
	public synchronized static OrderID getInstance()
	{
		if (instance == null)
		{
			instance = new OrderID();
		}
		
		return instance;
	}
	
	public synchronized String getOrderCounter()
	{
		String orderCounter = null;
		
		if (counter <= 9999)
		{
			DecimalFormat df = new DecimalFormat("0000");
			orderCounter = df.format(counter);
		}
		else
		{
			orderCounter = Integer.toString(counter);
		}
		
		this.counter++;
		if (this.counter >= 10000)
		{
			this.counter = 1;
		}
		
		orderCounter = DateFormatUtils.format(new Date(), "yyMMddHHmmss") + "-" + orderCounter;
		
		return orderCounter;
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 100; i++)
		{
			System.out.println(OrderID.getInstance().getOrderCounter());
		}
	}
}
