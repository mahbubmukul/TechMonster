package com.bitmakers.techmonster.adapter_pkg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitmakers.techmonster.R;

import java.util.ArrayList;

public class HomePageAdapter extends BaseAdapter {


	//ArrayList<Match> i;
	Context ctx;



	public HomePageAdapter(Context ctx)
	{
		super();
//		this.i = i;
		this.ctx = ctx;
	}

	
	static class ViewHolder{
		TextView matchNameTxt;
		TextView dateTime;
		TextView team1;
		TextView team2;
		ImageView team1_image;
		ImageView team2_image;
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
//		return i.size();
		return 10;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		convertView= LayoutInflater.from(ctx).inflate(R.layout.job_item, parent, false);
		
		ViewHolder Holder=new ViewHolder();
//		Match mm = i.get(position);
//
//
//		Holder.matchNameTxt =(TextView) convertView.findViewById(R.id.match_name);
//		Holder.matchNameTxt.setText(mm.getMatchName());
//		Holder.dateTime =(TextView) convertView.findViewById(R.id.match_date);
//		Holder.dateTime.setText(mm.getMatchDate()+", "+mm.getMatchTime());
//
//		Holder.team1 =(TextView) convertView.findViewById(R.id.team1_name);
//		Holder.team1.setText(mm.getTeamName1());
//		Holder.team2 =(TextView) convertView.findViewById(R.id.team2_name);
//		Holder.team2.setText(mm.getTeamName2());
//
//
//		Holder.team1_image= (ImageView) convertView.findViewById(R.id.team1_img);
//		Holder.team1_image.setImageResource(mm.getTeamImg1());
//		Holder.team2_image= (ImageView) convertView.findViewById(R.id.team2_img);
//		Holder.team2_image.setImageResource(mm.getTeamImg2());
		
		return convertView;
	}
	
	
	

}
