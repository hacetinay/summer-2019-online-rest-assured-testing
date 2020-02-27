package com.automation.tests.practice.CalendarificApiP;
import com.automation.utilities.ConfigurationReader;
import com.automation.utilities.ExcelUtil;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
public class CalendarificP {
    @BeforeAll
    public static void setUp(){
        baseURI=ConfigurationReader.getProperty("calendar.uri");
    }
    @Test
    public void test1(){

        Response response=given().accept(ContentType.JSON).
                queryParam("api_key", "7405e1fd70c6d3a643c8c2506698673d9bc29363").
                when().
                get("/countries");
        response.
                then().
                contentType(ContentType.JSON).statusCode(200);

        List<String> countries=response.jsonPath().getList("response.countries.country_name");
        System.out.println(countries);
        System.out.println("Index of 'United Kingdom': "+countries.indexOf("United Kingdom"));
        int indexOfUK=countries.indexOf("United Kingdom");

        response.   then().assertThat().
                body("response.countries.country_name["+indexOfUK+"]",is("United Kingdom")).
                body("response.countries.total_holidays["+indexOfUK+"]",is(95));
        int numberHolidays = response.jsonPath().get("response.countries.find {it.country_name == 'United Kingdom'}.total_holidays");
        System.out.println(numberHolidays);
    }
    @Test
    public void test2() {

        Response response = given().accept(ContentType.JSON).
                queryParam("api_key", "7405e1fd70c6d3a643c8c2506698673d9bc29363").
                when().
                get("/countries");
        response.
                then().
                contentType(ContentType.JSON).statusCode(200);
        int numholiday=response.jsonPath().getInt("response.countries. find{it.uuid=='f0357a3f154bc2ffe2bff55055457068'}.supported_languages");
        System.out.println(numholiday);


    }
    @Test
    public void test3() {

        Response response = given().accept(ContentType.JSON).
                queryParam("api_key", "7405e1fd70c6d3a643c8c2506698673d9bc29363").
                when().
                get("/countries");
        response.
                then().
                contentType(ContentType.JSON).statusCode(200);

        List<String> listOfCountries=response.jsonPath().get("response.countries. findAll{it.supported_languages==3}.country_name");
        System.out.println(listOfCountries);
        int indexNumCuba=listOfCountries.indexOf("Cuba");
        System.out.println("indexNumCuba = " + indexNumCuba);


    }

    }
