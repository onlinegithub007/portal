package com.fuhuitong.applychain.utils;

/**
 *
 */
public class BankCardUtils {
	public static String getMarkedBankCard(String cardNo){
		
		try
		{
			int startIndex = cardNo.length() - 10;
			
			String markedCard = cardNo.substring(0, startIndex);
			markedCard += "******";
			markedCard += cardNo.substring(cardNo.length() - 4, cardNo.length());
			
			return markedCard;
		}
		catch(Exception e)
		{
			return cardNo;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(getMarkedBankCard("6222888899991234"));
		System.out.println(getMarkedBankCard("6250031234563908"));
	}
}
