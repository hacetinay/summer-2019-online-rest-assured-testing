package com.automation.tests.day8;
import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;


public class OMDBTestsAPIKey {
    @BeforeAll
    public static void setUp(){
        baseURI=ConfigurationReader.getProperty("omdb.uri");
    }
    @Test
    public void test1(){
        given().accept(ContentType.JSON).
//                queryParam("apikey","9f94d4d0&t").
                queryParam("t","Home Alone").
                when().
                get().prettyPeek().
                then().
                assertThat().statusCode(401).
                body("Error",is("No API key provided."));


    }
    @Test
    public void test2() {
        Response response=given().accept(ContentType.JSON).
                                queryParam("apikey","9f94d4d0").
                        queryParam("t", "Home Alone").
                when().
                get().prettyPeek();
        response.
                then().
                assertThat().statusCode(200).
                body("Actors", is(containsString("Macaulay Culkin")));
        Map<String,?> payload=response.getBody().as(Map.class);
        System.out.println(payload);
        for(Map.Entry<String,?> entry:payload.entrySet()){
            System.out.println("Key: "+entry.getKey()+", Value: "+entry.getValue());
        }



    }
}
