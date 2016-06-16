package com.bitmakers.techmonster.app_data;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class XInternetServices {

	// private String baseUrlOfAsim = ZInternetServices.baseProtocol()
	// + XInternetServices.FullDomain();

	private String baseUrl = "http://youtube.com";

	public String getMyBaseUrl() {
		return baseUrl;
	}

	// method to check Internet connection
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

}