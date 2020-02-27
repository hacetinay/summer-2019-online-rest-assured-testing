package com.automation.tests.day9;

import com.automation.pojos.Spartan;
import com.automation.utilities.ExcelUtil;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import com.automation.pojos.Job;
import com.automation.pojos.Location;
import com.automation.utilities.ConfigurationReader;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

public class Test_Data {
    @Test
    public void test1(){

    String excelPath="C:\\Users\\hacet\\IdeaProjects\\summer-2019-online-rest-assured-testing\\src\\test\\resources\\test_data\\bookit_test_data.xlsx";
    ExcelUtil excelUtil=new ExcelUtil(excelPath,"students");
        System.out.println(excelUtil.getDataList());
        for (Map<String ,String> map:excelUtil.getDataList()){
            System.out.println(map);
        }
    }

}
