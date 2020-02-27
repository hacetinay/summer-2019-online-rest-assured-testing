package com.automation.tests.day8;

import com.automation.pojos.Spartan;
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

public class BearerTokenTestsWithBookit {
    String oauth="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI1Mzc2IiwiYXVkIjoic3R1ZGVudC10ZWFtLWxlYWRlciJ9.DoFI744aMLxUaf0GcjVOEDkJ3Wh7RlKDx-TYp8_sJpU";
    @BeforeAll
    public static void setUp(){
        baseURI=ConfigurationReader.getProperty("bookit.qa1");

    }
    @Test
    public void test1(){
        Response response=given().auth().oauth2(getToken()).
                accept(ContentType.JSON).
                when().
                get("/api/rooms");
        response.then().assertThat().statusCode(200).contentType(ContentType.JSON);

        List<String> listOfRoom=response.jsonPath().getList("name");
        for (String rooms:listOfRoom){
            System.out.println(rooms);
        }
    }
    /**
     * Method that generates access token
     * @return bearer token
     */
    public String getToken(){
        //https://cybertek-reservation-api-qa.herokuapp.com/sign?email=vasyl@cybertekschool.com&password=cybertek2020
        Response response = given().
                queryParam("email", ConfigurationReader.getProperty("team.leader.email")).
                queryParam("password", ConfigurationReader.getProperty("team.leader.password")).
                when().
                get("/sign").prettyPeek();
        return  response.jsonPath().getString("accessToken");
    }
}
