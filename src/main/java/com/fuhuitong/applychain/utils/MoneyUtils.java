package com.fuhuitong.applychain.utils;

public class MoneyUtils 
{
	public static String getMoneyText(int money)
	{
		int fen = money % 100;
		int yuan = (int)(money / 100);
		
		String text = Integer.toString(yuan) + ".";
		
		if (fen < 10)
		{
			text += "0" + fen;
		}
		else
		{
			text += fen;
		}
		
		return text;
	}

	public static String getMoneyText(double v) {

		double yuan = v/100;

		String text = Double.toString(yuan);

		return text;

	}
}
