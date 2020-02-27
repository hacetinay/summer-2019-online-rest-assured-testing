package com.automation.tests.practice;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrittyPrint {
    private String baseURI="http://api.openrates.io/";
    @Test
    public void test1(){
        Response response=given().get(baseURI+"latest");
        assertEquals(200,response.getStatusCode());
        System.out.println(response.prettyPrint());
    }
    @Test
public void test2(){
        Response response=given().get(baseURI+"latest");
        assertEquals(200, response.getStatusCode());
        assertEquals("application/json",response.getHeader("content-type"));

}
//GET https://api.exchangeratesapi.io/latest?base=USD HTTP/1.1
//    this is query parameter based on qp
@Test
    public void test3(){
//        Response response=given().get(baseURI+"latest?base=USD");
    Response response=given().baseUri(baseURI).
            basePath("latest").
            queryParam("base", "USD").get();
        assertEquals(200,response.getStatusCode());
    System.out.println(response.prettyPrint());
}

@Test
    public void test4(){
        Response response=given().
        baseUri(baseURI+"latest").
        queryParam("date","2020-01-23" ).get();
        assertEquals(200,response.getStatusCode());
    System.out.println(response.prettyPrint());

}

}
