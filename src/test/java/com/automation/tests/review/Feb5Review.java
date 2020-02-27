package com.automation.tests.review;

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

public class Feb5Review {
    @BeforeAll
    public static void setUp(){
        baseURI=ConfigurationReader.getProperty("omdb.uri");
    }
    @Test
    @DisplayName("")
    public void search_test(){
        Response response=given().accept(ContentType.JSON).
                queryParam("apikey","9f94d4d0").
                queryParam("s", "Terminator").
                when().
                get().prettyPeek();
        response.
                then().
                assertThat().statusCode(200);
//        .body("Actors", is(containsString("Macaulay Culkin")));
        int darkFate=response.jsonPath().getInt("Search.find{it.Title=='Terminator: Dark Fate'}.Year");
        System.out.println(darkFate);
    }

}
