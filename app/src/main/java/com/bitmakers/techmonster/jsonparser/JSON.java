package com.bitmakers.techmonster.jsonparser;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;

import com.bitmakers.techmonster.model_class.CityList;
import com.bitmakers.techmonster.model_class.DistList;
import com.bitmakers.techmonster.model_class.JobList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

/**
 * Created by MAHBUB_MUKUL on 6/13/2016.
 */
public class JSON {


    public ArrayList<JobList> parseHomeNews(String json){

        ArrayList<JobList> temp = new ArrayList<JobList>();

        System.out.println("FFFFFFFFFFFFFFFFFF _JSONNEWS  ..........  "+json);
        try {
            if(json.startsWith("Error"))
                return null;

            JSONObject jObj = new JSONObject(json);
            JSONArray contacts = jObj.getJSONArray("list_job");

            System.out.println("FFFFFFFFFFFFFFFFFF _JSONNEWSL.........  "+contacts.length());

            for(int i = 0; i < contacts.length(); i++){
                JSONObject c = contacts.getJSONObject(i);

                String id="",name="",salary="",summary="",job_cover_image ="",
                        job_city="",job_dist="",expire_time="",company_id="",company_name="", companyTxt="";

                try{id = c.getString("id");}catch (Exception e){}
                try{name = c.getString("name");}catch (Exception e){}
                try{salary = c.getString("salary");}catch (Exception e){}
                try{summary = c.getString("summary");}catch (Exception e){}
                try{job_cover_image = c.getString("job_cover_image");}catch (Exception e){}
                try{job_city = c.getString("job_city");}catch (Exception e){}
                try{job_dist = c.getString("job_dist");}catch (Exception e){}
                try{expire_time = c.getString("expire_time");}catch (Exception e){}

                try{companyTxt = c.getString("company_info");
                    JSONObject comObj = new JSONObject(companyTxt);
                    try{company_id = comObj.getString("id_company");}catch (Exception e){}
                    try{company_name = comObj.getString("com_name");}catch (Exception e){}

                }catch (Exception e){}

                JobList jj = new JobList();
                jj.setId(id);jj.setCompany_id(company_id);jj.setCompany_name(company_name);
                jj.setExpire_time(expire_time);jj.setJob_city(job_city);jj.setJob_country(job_dist);
                jj.setJob_cover_image(job_cover_image);jj.setName(name);jj.setSalary(salary);jj.setSummary(summary);

                temp.add(jj);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  temp;
    }

    public ArrayList<CityList> parseCity(String json){

        ArrayList<CityList> temp = new ArrayList<CityList>();
        try {
            if(json.startsWith("Error"))
                return null;

            JSONObject jObj = new JSONObject(json);
            JSONArray contacts = jObj.getJSONArray("data");



            for(int i = 0; i < contacts.length(); i++){
                JSONObject c = contacts.getJSONObject(i);

                String id="",name="",country_id="",dist_id="",city_id ="",
                        dist_name="",disttxt="";

                ArrayList<DistList> temp1 = new ArrayList<DistList>();

                try{id = c.getString("id_city");}catch (Exception e){}
                try{name = c.getString("city_name");}catch (Exception e){}
                try{country_id = c.getString("id_country");}catch (Exception e){}
                try{
                    disttxt = c.getString("districts");
                    JSONArray districts = new JSONArray(disttxt);

                    for(int j = 0; j < districts.length(); j++){
                        JSONObject c1 = districts.getJSONObject(j);

                        try{dist_id = c1.getString("id");}catch (Exception e){}
                        try{dist_name = c1.getString("name");}catch (Exception e){}
                        try{city_id = c1.getString("id_city");}catch (Exception e){}
                        DistList jj = new DistList();
                        jj.setId(dist_id);jj.setName(dist_name);jj.setCity_id(city_id);

                        temp1.add(jj);
                    }

                }catch (Exception e){}

                CityList jk = new CityList();
                jk.setId(id);jk.setName(name);jk.setCountry_id(country_id);
                jk.setDistricts(temp1);

                temp.add(jk);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  temp;
    }

    public String parseSignIn(String json, Context ctx){

        String status="";
        try {
            if(json.startsWith("Error"))
                return "Error";

            JSONObject c = new JSONObject(json);

            String id="",company_id="",name="",rid="",ttl ="",
                    email="",token ="",avatar ="",array_company ="";
            try{status = c.getString("message");}catch (Exception e){}

            if(status.equals("Success"))
            {
                try{id = c.getString("uid");}catch (Exception e){}
                try{name = c.getString("name");}catch (Exception e){}
                try{company_id = c.getString("company_id");}catch (Exception e){}
                try{rid = c.getString("rid");}catch (Exception e){}
                try{ttl = c.getString("ttl");}catch (Exception e){}
                try{email = c.getString("email");}catch (Exception e){}
                try{token = c.getString("token");}catch (Exception e){}
                try{
                    String disttxt = c.getString("info");
                    JSONObject c1 = new JSONObject(disttxt);
                    try{avatar = c1.getString("avatar");}catch (Exception e){}
                    try{array_company = c1.getString("array_company");}catch (Exception e){}
                }catch (Exception e){}

                SharedPreferences pref = ctx.getSharedPreferences("TechMonster_Login", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();

                editor.putString("user_id", id);
                editor.putString("user_name", name);
                editor.putString("user_company_id", company_id);
                editor.putString("user_rid", rid);
                editor.putString("user_ttl", ttl);
                editor.putString("user_email", email);
                editor.putString("user_token", token);
                editor.putString("user_avatar", avatar);
                editor.putString("user_company", array_company);

                editor.commit(); // commit changes

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  status;
    }

    public String parseSignUp(String json, Context ctx){

        String status="";
        try {
            if(json.startsWith("Error"))
                return "Error";

            JSONObject c = new JSONObject(json);

            String id="",company_id="",name="",rid="",ttl ="",
                    email="",token ="",avatar ="",array_company ="";
            try{status = c.getString("message");}catch (Exception e){}

            if(status.equals("Success"))
            {
//                try{id = c.getString("uid");}catch (Exception e){}
//                try{name = c.getString("name");}catch (Exception e){}
//                try{company_id = c.getString("company_id");}catch (Exception e){}
//                try{rid = c.getString("rid");}catch (Exception e){}
//                try{ttl = c.getString("ttl");}catch (Exception e){}
//                try{email = c.getString("email");}catch (Exception e){}
//                try{token = c.getString("token");}catch (Exception e){}
//                try{
//                    String disttxt = c.getString("info");
//                    JSONObject c1 = new JSONObject(disttxt);
//                    try{avatar = c1.getString("avatar");}catch (Exception e){}
//                    try{array_company = c1.getString("array_company");}catch (Exception e){}
//                }catch (Exception e){}
//
//                SharedPreferences pref = ctx.getSharedPreferences("TechMonster_Login", 0); // 0 - for private mode
//                SharedPreferences.Editor editor = pref.edit();
//
//                editor.putString("user_id", id);
//                editor.putString("user_name", name);
//                editor.putString("user_company_id", company_id);
//                editor.putString("user_rid", rid);
//                editor.putString("user_ttl", ttl);
//                editor.putString("user_email", email);
//                editor.putString("user_token", token);
//                editor.putString("user_avatar", avatar);
//                editor.putString("user_company", array_company);
//
//                editor.commit(); // commit changes

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  status;
    }
}
