package com.example.l2phellobookmarks;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class GlobalVariables {
	public static Boolean hasDeviceCode = false;
	public static Boolean hasRefreshCode = false;
	public static long tokenExpireAtUnixTimestamp = -1;
	public static String user_code = "";
	public static String device_code = "";
	public static String apiCallToken = "";

	public static String GetOAuthLoginUrl() {
		String url = "https://service.campus.rwth-aachen.de/oauth2/oauth2.svc/code";
		return url;
	}
	public static String GetManageDeviceCodeUrl(){
		String url = "https://service.campus.rwth-aachen.de/oauth2?q=verify&d=";
		return url;
	}
	public static String GetOAuthPollUrl(){
		String url = "https://service.campus.rwth-aachen.de/oauth2/oauth2.svc/token";
		return url;
	}

	public static void populateCodesFromSharedPreference(Context context){
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		String temp = settings.getString("DEVICE_CODE", "");
		if(temp.equalsIgnoreCase("null") || temp.equalsIgnoreCase("")){
			GlobalVariables.hasDeviceCode = false;
		}
		else
			GlobalVariables.hasDeviceCode = true;
		
		temp = settings.getString("REFRESH_CODE", "");
		if(temp.equalsIgnoreCase("null") || temp.equalsIgnoreCase("")){
			GlobalVariables.hasRefreshCode = false;
		}
		else
			GlobalVariables.hasRefreshCode = true;
		
		GlobalVariables.tokenExpireAtUnixTimestamp = settings.getLong("EXPIRES_IN", -1);
		GlobalVariables.user_code = settings.getString("USER_CODE", "");
		GlobalVariables.device_code = settings.getString("DEVICE_CODE", "");
	}
	
	
}
