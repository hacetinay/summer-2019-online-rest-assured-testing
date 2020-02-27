package com.automation.tests.practice.bookit_api;
import com.automation.utilities.ConfigurationReader;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.baseURI;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.response.Response;

import java.io.File;
import java.util.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.BeforeAll;

public class BookItApi {
    String accessToken="Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4NiIsImF1ZCI6InN0dWRlbnQtdGVhbS1sZWFkZXIifQ.lEfjcu6RpBfcZ4qWthzZU8uH8fX4FCJFfxBnPNgh4Mo";
    @BeforeAll
    public static void setUp(){
        baseURI= ConfigurationReader.getProperty("bookitapi.uri");

    }
    @Test
    public void test1(){
        Response response=given().header("Authorization",accessToken).
                accept(ContentType.JSON).
                when().
                get("/campuses");
        JsonPath jsonPath=response.jsonPath();
        String roomNames=jsonPath.getString("id");
        String roomLocations=jsonPath.getString("clusters.rooms.name");
        String locations=jsonPath.getString("location");
//        String roomLocations=jsonPath.getString("id");
//        String roomNames=jsonPath.getString("id");
        System.out.println("roomNames = " + roomNames);
        System.out.println("locations = " + locations);
        System.out.println("roomLocations = " + roomLocations);

    }
}
