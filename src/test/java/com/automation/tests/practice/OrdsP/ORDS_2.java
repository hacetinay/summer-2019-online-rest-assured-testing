package com.automation.tests.practice.OrdsP;
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

public class ORDS_2 {

    @BeforeAll
    public static void setup(){
        baseURI=ConfigurationReader.getProperty("ords.uri");
    }
    @Test
    public void test1(){
                    Response response= given().
                    accept(ContentType.JSON).
                    when().
                    get("/regions");
        JsonPath jsonPath=response.jsonPath();
//        List<String>list=jsonPath.getList("items.region_name");

//        Map<String,?> map=response.body().as(Map.class);
//        System.out.println("map = " + map);
//        List<Map<String,?>> list=(Map<String,?>)map.get(0).g("items.region_name");
//        System.out.println(list.get(0));
        List<Map<String, ?>> map= jsonPath.getList("items");
        System.out.println(map);
    }
}
