
package com.bitmakers.techmonster.model_class;


public class JobFavouriteList {

    private int row_id;
    private String id;
    private String summary;
    private String detail;
    private String expire_time;
    private String job_dist;
    private String job_exp;
    private String applicant_job_status;
    private String job_city;
    private String job_type;
    private String name;
    private String job_country;
    private String salary;
    private String job_cover_image;
    private String job_skill;

    public JobFavouriteList(int row_id, String id, String summary, String detail, String expire_time, String job_dist, String job_exp, String applicant_job_status, String job_city, String job_type, String name, String job_country, String salary, String job_cover_image, String job_skill) {
        this.row_id = row_id;
        this.id = id;
        this.summary = summary;
        this.detail = detail;
        this.expire_time = expire_time;
        this.job_dist = job_dist;
        this.job_exp = job_exp;
        this.applicant_job_status = applicant_job_status;
        this.job_city = job_city;
        this.job_type = job_type;
        this.name = name;
        this.job_country = job_country;
        this.salary = salary;
        this.job_cover_image = job_cover_image;
        this.job_skill = job_skill;
    }

    public String getSummary ()
    {
        return summary;
    }

    public void setSummary (String summary)
    {
        this.summary = summary;
    }

    public String getDetail ()
    {
        return detail;
    }

    public void setDetail (String detail)
    {
        this.detail = detail;
    }

    public String getExpire_time ()
    {
        return expire_time;
    }

    public void setExpire_time (String expire_time)
    {
        this.expire_time = expire_time;
    }

    public String getJob_dist ()
    {
        return job_dist;
    }

    public void setJob_dist (String job_dist)
    {
        this.job_dist = job_dist;
    }

    public String getJob_exp ()
    {
        return job_exp;
    }

    public void setJob_exp (String job_exp)
    {
        this.job_exp = job_exp;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getApplicant_job_status ()
    {
        return applicant_job_status;
    }

    public void setApplicant_job_status (String applicant_job_status)    {
        this.applicant_job_status = applicant_job_status;
    }

    public String getJob_city ()
    {
        return job_city;
    }

    public void setJob_city (String job_city)
    {
        this.job_city = job_city;
    }

    public String getJob_type ()
    {
        return job_type;
    }

    public void setJob_type (String job_type)
    {
        this.job_type = job_type;
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

    public void setJob_cover_image (String job_cover_image)    {
        this.job_cover_image = job_cover_image;
    }

    public String getJob_skill ()
    {
        return job_skill;
    }

    public void setJob_skill (String job_skill)
    {
        this.job_skill = job_skill;
    }

}
