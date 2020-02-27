package com.automation.tests.Day5;

import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class SchoolTests {
    @BeforeAll
    public static void setup(){
        baseURI=ConfigurationReader.getProperty("school.uri");
    }
    @Test
    public void test1(){
        Response response=given().accept(ContentType.JSON).
                when().
                delete("/student/delete{id}",56).prettyPeek();

    }

}
