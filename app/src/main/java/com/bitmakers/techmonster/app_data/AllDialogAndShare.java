package com.bitmakers.techmonster.app_data;


import android.content.Context;
import android.content.Intent;

import com.bitmakers.techmonster.R;

public class AllDialogAndShare {

	private Context context;

	public AllDialogAndShare(Context context) {

		this.context = context;

	}

	public void shareIt() {

		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");

		String APP_NAME = context.getString(R.string.app_name);
		String PACKAGE_NAME = context.getPackageName();
		String newline = System.getProperty("line.separator");

		String shareSub = APP_NAME + " is very uselful app";

		String shareBody = APP_NAME
				+ " is a decent looking uselful app you may love." + newline
				+ "Download " + APP_NAME + " from google play " + newline
				+ newline + "https://play.google.com/store/apps/details?id="
				+ PACKAGE_NAME;

		sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
		sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
		context.startActivity(Intent.createChooser(sharingIntent, "Share... "));

	}

}
