package com.bitmakers.techmonster.app_data;

/**
 * Created by MAHBUB_MUKUL on 6/9/2016.
 */
public class AppUrl {

    //public static String base_Url = "http://192.168.5.220:8080/rest?";
    public static String base_Url = "http://52.77.47.117:8080/v1/rest?";



    public static String homJobUrl = base_Url+"access_key=90AMUJHalcpJcR0CohSNTXDirqX&scope=applicant&func=get_jobs";
    public static String cityUrl = base_Url+"access_key=90AMUJHalcpJcR0CohSNTXDirqX&scope=common&func=get_city_district";
    public static String countryUrl = base_Url+"access_key=90AMUJHalcpJcR0CohSNTXDirqX&scope=common&func=get_country";
    public static String keyUrl = base_Url+"access_key=90AMUJHalcpJcR0CohSNTXDirqX&scope=applicant&func=get_system_keywords";
    public static String sign_in = base_Url+"access_key=90AMUJHalcpJcR0CohSNTXDirqX&scope=oauth&func=login";
    public static String sign_up = base_Url+"access_key=90AMUJHalcpJcR0CohSNTXDirqX&scope=oauth&func=register";

    public static String dtailsJob = base_Url+"access_key=90AMUJHalcpJcR0CohSNTXDirqX&scope=applicant&func=get_job_detail";

    public static String searchJob = base_Url+"access_key=90AMUJHalcpJcR0CohSNTXDirqX&scope=applicant&func=search_keywords";





}
