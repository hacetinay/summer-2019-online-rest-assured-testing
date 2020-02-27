package com.automation.tests.Day5;
import com.automation.utilities.ConfigurationReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.BeforeAll;

public class ODBSTestsDay5 {
    @BeforeAll
    public static void setup(){
        baseURI=ConfigurationReader.getProperty("ords.uri");
    }
    /**
     * WARMUP
     * given path parameter is "/employees"
     * when user makes get request
     * then user verifies that status code is 200
     * and user verifies that average salary is grater than $5000
     */
    @Test
    @DisplayName("dvsvs")
    public void test1(){
        Response response= given().
                accept(ContentType.JSON).
                when().
                get("/employees");

        JsonPath jsonPath=response.jsonPath();//to create json body
        List<Integer>salaries=jsonPath.getList("items.salary");



    }
}
