package com.luv2code.springbootlibrary.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ApiLog{
    private Long id;
    private String api;
    private String method;
    private int status_code;
    private String email;
    private Timestamp start_date;
    private Timestamp end_date;
    private Timestamp execution_time;
    private String result;
    private String error_message;
}
