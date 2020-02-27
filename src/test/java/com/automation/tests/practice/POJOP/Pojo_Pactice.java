package com.automation.tests.practice.POJOP;
import com.automation.pojos.Spartan;
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
import com.automation.pojos.Job;
import com.automation.pojos.Location;
import com.automation.utilities.ConfigurationReader;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.*;
public class Pojo_Pactice {
    @BeforeAll
    public static void setup(){
        baseURI=ConfigurationReader.getProperty("spartan.uri");

    }
    @Test
    public void test1(){
        Response response=given().accept(ContentType.JSON).
                get("/spartans/15");

        Spartan spartan=response.body().as(Spartan.class);
        System.out.println(spartan.getGender());
        System.out.println(spartan.getName());
        System.out.println(spartan.getPhone());
    }
    @Test
    public void test2(){
        Spartan spartan=new Spartan("Haci","Male",4847584398L);
        Gson gson=new Gson();
        String json=gson.toJson(spartan);
        System.out.println(json);

    }
    @Test
    public void test3(){
        Response response=given().accept(ContentType.JSON).
                get("/spartans");
        List<String >list=response.body().as(List.class);

        System.out.println(list);
    }
    @Test
    public void test4(){
//        for (int i=1;i<=100; i++) { if you want to create more than one spartan at the same time
            Spartan spartan = new Spartan();
            spartan.setName("Maximus");
            spartan.setGender("Male");
            spartan.setPhone(2324567890L);
            given().accept(ContentType.JSON).
                    contentType(ContentType.JSON).
                    body(spartan).
                    when().
                    post("/spartans").prettyPeek();
//        }
    }
    @Test
    public void test5(){
       Map<String,Object> update=new HashMap<>();
       update.put("name","Minimus");
       update.put("gender","Male");
       update.put("phone", 2324456790L);

        given().accept(ContentType.JSON).
                contentType(ContentType.JSON).
                and().
                pathParam("id",7).
                body(update).
                when().
                put("/spartans/{id}").
                then().
                assertThat().statusCode(204);
    }
    @Test
    public void test6(){
        Random random=new Random();
        int randomId=random.nextInt(100); //when we need random number to test delete functionality
        System.out.println("randomId = " + randomId);
        given().accept(ContentType.JSON).
                and().
                pathParam("id",randomId).
                when().
                delete("/spartans/{id}").prettyPeek().
                then().
                assertThat().statusCode(204);
        expect().that().statusCode(404).
                given().
                pathParam("id", randomId).
                get("/spartans/{id}");
    }

}
