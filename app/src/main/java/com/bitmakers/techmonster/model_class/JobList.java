package com.bitmakers.techmonster.model_class;

/**
 * Created by MAHBUB_MUKUL on 6/9/2016.
 */
public class JobList {
    private String id;
    private String name;
    private String salary;
    private String summary;
    private String job_cover_image;
    private String job_city;
    private String job_country;
    private String expire_time;
    private String company_id;
    private String company_name;


    public String getSummary ()
    {
        return summary;
    }
    public void setSummary (String summary)
    {
        this.summary = summary;
    }
    public String getExpire_time ()
    {
        return expire_time;
    }
    public void setExpire_time (String expire_time)
    {
        this.expire_time = expire_time;
    }
    public String getId ()
    {
        return id;
    }
    public void setId (String id)
    {
        this.id = id;
    }
    public String getJob_city ()
    {
        return job_city;
    }
    public void setJob_city (String job_city)
    {
        this.job_city = job_city;
    }
    public String getName ()
    {
        return name;
    }
    public void setName (String name)
    {
        this.name = name;
    }
    public String getJob_country ()
    {
        return job_country;
    }
    public void setJob_country (String job_country)
    {
        this.job_country = job_country;
    }
    public String getSalary ()
    {
        return salary;
    }
    public void setSalary (String salary)
    {
        this.salary = salary;
    }
    public String getJob_cover_image ()
    {
        return job_cover_image;
    }
    public void setJob_cover_image (String job_cover_image)
    {
        this.job_cover_image = job_cover_image;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }
}
