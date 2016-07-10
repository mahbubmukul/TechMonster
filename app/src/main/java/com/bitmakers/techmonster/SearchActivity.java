package com.bitmakers.techmonster;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ScrollingTabContainerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bitmakers.techmonster.app_data.AppData;
import com.bitmakers.techmonster.app_data.AppUrl;
import com.bitmakers.techmonster.app_data.XInternetServices;
import com.bitmakers.techmonster.jsonparser.JSON;
import com.bitmakers.techmonster.jsonparser.JSONParser;
import com.bitmakers.techmonster.model_class.DistList;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    Spinner city, district;

    ArrayList<String> cityList =new ArrayList<>();
    ArrayList<String> distList =new ArrayList<>();

    MultiAutoCompleteTextView textView=null;
    private ArrayAdapter<String> adapter;

    //These values show in autocomplete
    String item[];
    SharedPreferences pref;
    String token;
    String result;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setLogo(R.mipmap.jobportal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pref = getApplicationContext().getSharedPreferences("TechMonster_Login", 0);
        token = pref.getString("user_token", "");

        city = (Spinner) findViewById(R.id.spinCity);
        district = (Spinner) findViewById(R.id.spinArea);


        cityList.add("City");
        for(int i=0; i<AppData.cityLists.size();i++){
            cityList.add(AppData.cityLists.get(i).getName());
        }

        ArrayAdapter<String> karant_adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item,cityList);
        city.setAdapter(karant_adapter);

        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<DistList> temp = AppData.cityLists.get(position).getDistricts();
                System.out.println("WWWWW "+temp.size());
                distList = new ArrayList<String>();
                distList.add("District");
                for(int j=0;j< temp.size();j++)
                {
                        distList.add(temp.get(j).getName());
                }

                ArrayAdapter<String> kara = new ArrayAdapter<String>(SearchActivity.this,
                        R.layout.spinner_item,distList);
                district.setAdapter(kara);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        item = new String[AppData.newsKey.size()];

        for(int i=0; i<AppData.newsKey.size();i++){
            item[i]= AppData.newsKey.get(i).toString();
        }



        textView = (MultiAutoCompleteTextView) findViewById(R.id.keyWord);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item);
        textView.setThreshold(1);
        textView.setAdapter(adapter);
        textView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());


        ((FrameLayout)findViewById(R.id.search_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String cityName, distName, keyWord;

                cityName = city.getSelectedItem().toString().trim();
                distName = district.getSelectedItem().toString().trim();
                keyWord = textView.getText().toString().trim();

                String cityId="",distId="";

                if (cityName.equalsIgnoreCase("City")) {
                    cityName="";
                    cityId="";
                }
                else
                {
                    for(int i=0;i< AppData.cityLists.size();i++)
                    {
                        String nameCity = AppData.cityLists.get(i).getName();
                        if(nameCity.equals(cityName)){
                            cityId = AppData.cityLists.get(i).getId();
                        }

                    }
                }
                if (distName.equalsIgnoreCase("District")) {
                    distName="";
                }else{
                    for(int i=0;i< AppData.cityLists.size();i++)
                    {
                        String nameCity = AppData.cityLists.get(i).getName();
                        if(nameCity.equals(cityName)){
                            cityId = AppData.cityLists.get(i).getId();
                            ArrayList<DistList> temp = AppData.cityLists.get(i).getDistricts();
                            for(int j=0;j< temp.size();j++)
                            {
                                String nameDist = temp.get(j).getName();

                                //System.out.println("FFFFF _DIST_LEN R.........  "+distId+" "+mm.getJob_country());
                                if(nameDist.equals(distName)){
                                    distId = temp.get(j).getId();
                                    break;
                                }

                            }
                        }
                    }
                }



                if(cityName.equalsIgnoreCase("")&&distName.equalsIgnoreCase("")&&keyWord.equalsIgnoreCase("")) {
                    Toast.makeText(SearchActivity.this, "At Least 1 field mandatory!",Toast.LENGTH_LONG).show();
                }
                else if(cityName.equalsIgnoreCase(""))
                {
                    getDetailsWeb("&token="+token+"&keywords="+keyWord,true);
                }
                else if(distName.equalsIgnoreCase(""))
                {
                    getDetailsWeb("&token="+token+"&city="+cityId+"&keywords="+keyWord,true);
                }
                else if(distName.equalsIgnoreCase(""))
                {
                    getDetailsWeb("&token="+token+"&city="+cityId,true);
                }
                else {
                    getDetailsWeb("&token="+token+"&dist="+distId+"&city="+cityId+"&keywords="+keyWord,true);
                }

            }

        });

    }




    public void getDetailsWeb(final String data, final boolean showProgress) {

        if (XInternetServices.isNetworkAvailable(SearchActivity.this)) {
            class  LoadHomeData extends AsyncTask<Void,Void,Void> {
                ProgressDialog progressDialog;

                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        result = new JSONParser().thePostRequest(
                                AppUrl.searchJob+data, "");
                        System.out.println("WWWWWW >>>"+AppData.searchJobList.size());
                        AppData.searchJobList= new JSON().parseHomeNews(result );

                        System.out.println("WWWWWW >>>"+AppData.searchJobList.size());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                    if (showProgress == true) {
                        progressDialog = ProgressDialog.show(SearchActivity.this, "",
                                "Loading. Please Wait...");
                        progressDialog.setCancelable(false);
                    }
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    if (showProgress == true) {
                        progressDialog.dismiss();
                    }

                    // startActivity(new );
                    startActivity(new Intent(SearchActivity.this, SearchResultActivity.class));

                    super.onPostExecute(aVoid);
                }
            }
            new LoadHomeData().execute();
        } else {
            android.support.v7.app.AlertDialog.Builder builder1;
            builder1 = new android.support.v7.app.AlertDialog.Builder(SearchActivity.this);
            builder1.setMessage("Please check your internet connection");
            builder1.setCancelable(true);
            builder1.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            builder1.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ((Activity) SearchActivity.this).finish();
                        }
                    });
            android.support.v7.app.AlertDialog alert11 = builder1.create();
            alert11.show();

        }

    }

}
