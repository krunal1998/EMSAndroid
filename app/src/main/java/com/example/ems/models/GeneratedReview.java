package com.example.ems.models;

import java.io.Serializable;
import java.util.Date;

public class GeneratedReview implements Serializable {
    public int GenerateReviewId;
    public String EmployeeId ;
    public String StartDate;
    public String EndDate;
    public String DueDate;
    public String Status;
    public transient double AverageRating;

}
