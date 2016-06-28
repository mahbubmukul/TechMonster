package com.bitmakers.techmonster.adapter_pkg;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitmakers.techmonster.R;
import com.bitmakers.techmonster.app_data.AppData;
import com.bitmakers.techmonster.model_class.DistList;
import com.bitmakers.techmonster.model_class.JobFavouriteList;
import com.bitmakers.techmonster.model_class.JobList;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

public class FavouriteAdapter extends BaseAdapter {


	//ArrayList<Match> i;
	Context ctx;
	ArrayList<JobFavouriteList> data;
	DisplayImageOptions options;



	public FavouriteAdapter(Context ctx, ArrayList<JobFavouriteList> data)
	{
		super();
//		this.i = i;
		this.ctx = ctx;
		this.data= data;

		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.mipmap.ic_launcher)
				.showImageForEmptyUri(R.mipmap.ic_launcher)
				.showImageOnFail(R.mipmap.ic_launcher)
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.considerExifParams(true)
				//.displayer(new RoundedBitmapDisplayer(20))
				.build();
	}

	
	static class ViewHolder{
		TextView job_title;
		TextView companyName;
		TextView location;
		TextView salary;
		TextView timeRemaining;
		ImageView coverImage;
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
//		return i.size();
		return data.size();
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
		
		final ViewHolder Holder=new ViewHolder();
		JobFavouriteList mm = data.get(position);
//
//
		Holder.job_title =(TextView) convertView.findViewById(R.id.job_title);
		Holder.job_title.setText(mm.getName());
		Holder.companyName =(TextView) convertView.findViewById(R.id.job_company);
		//Holder.companyName.setText(mm.getCompany_name());
		Holder.salary =(TextView) convertView.findViewById(R.id.job_sign_in);
		Holder.salary.setText(mm.getSalary());

		long curTime= System.currentTimeMillis();
		long expTime= Long.parseLong(mm.getExpire_time());


		long difference = expTime - curTime;
		int days = (int) (expTime / (1000*60*60*24));

		Holder.timeRemaining =(TextView) convertView.findViewById(R.id.job_left);
		Holder.timeRemaining.setText(days+" days remain");

		String cityName="", distName="";
		for(int i=0;i< AppData.cityLists.size();i++)
		{
			String cityId = AppData.cityLists.get(i).getId();
			if(cityId.equals(mm.getJob_city())){
				cityName = AppData.cityLists.get(i).getName();
				ArrayList<DistList> temp = AppData.cityLists.get(i).getDistricts();
				System.out.println("WWWWW "+temp.size());
				for(int j=0;j< temp.size();j++)
				{
					String distId = temp.get(j).getId();

					//System.out.println("FFFFF _DIST_LEN R.........  "+distId+" "+mm.getJob_country());
					if(distId.equals(mm.getJob_country())){
						distName = temp.get(j).getName();
						break;
					}

				}
			}
		}
		Holder.location =(TextView) convertView.findViewById(R.id.job_location);
		Holder.location.setText(cityName+", "+distName);





		Holder.coverImage= (ImageView) convertView.findViewById(R.id.cover_image);

		ImageLoader.getInstance().displayImage(mm.getJob_cover_image(), Holder.coverImage, options, new ImageLoadingListener() {
			@Override
			public void onLoadingStarted(String imageUri, View view) {
			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				int width = loadedImage.getWidth();
				int height = loadedImage.getHeight();
				System.out.println("TestDDDD   " + width + "  " + height);
				if (width > height)
					Holder.coverImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
			}

			@Override
			public void onLoadingCancelled(String imageUri, View view) {
			}
		});
		return convertView;
	}
	
	
	

}
