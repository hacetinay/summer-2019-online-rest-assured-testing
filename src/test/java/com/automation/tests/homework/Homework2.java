package com.automation.tests.homework;
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
public class Homework2 {
    @BeforeAll
    public static void setUp(){
        baseURI=ConfigurationReader.getProperty("github.uri");
    }
//    Verify organization information
//1. Send a get request to /orgs/:org. Request includes :
//            • Path param org with value cucumber
//2. Verify status code 200, content type application/json; charset=utf-8
//            3. Verify value of the login field is cucumber
//4. Verify value of the name field is cucumber
//5. Verify value of the id field is 320565
    @Test
    public void test1(){
        given().accept(ContentType.JSON).
                pathParam("org","cucumber").
                when().
                get("/orgs/{org}").
                then().
                contentType("application/json; charset=utf-8").
                statusCode(200).
                assertThat().body("login",is("cucumber")).
                assertThat().body("name", is("Cucumber")).
                assertThat().body("id",is(320565)).
                log().all(true);
    }
//    Verify error message
//1. Send a get request to /orgs/:org. Request includes :
//            • Header Accept with value application/xml
//• Path param org with value cucumber
//2. Verify status code 415, content type application/json; charset=utf-8
//            3. Verify response status line include message Unsupported Media Type
    @Test
    public void test2(){

        given().accept("application/xml").
                pathParam("org","cucumber").
                when().
                get("/orgs/{org}").
                then().
                contentType("application/json; charset=utf-8").
                statusCode(415).
                statusLine(containsString("Unsupported Media Type")).
                log().all(true);
    }
//    Number of repositories
//1. Send a get request to /orgs/:org. Request includes :
//            • Path param org with value cucumber
//2. Grab the value of the field public_repos
//3. Send a get request to /orgs/:org/repos. Request includes :
//            • Path param org with value cucumber
//4. Verify that number of objects in the response is equal to value from step 2
    @Test
    public void test4(){
        Response response=given().accept(ContentType.JSON).
                pathParam("org","cucumber").
                when().
                get("/orgs/{org}/repos");
                response.then().
                contentType("application/json; charset=utf-8").
                statusCode(200);
                List<String> node_id=response.jsonPath().getList("node_id");
          System.out.println(node_id);
        System.out.println(node_id.size());
   }
//    Repository id information
//1. Send a get request to /orgs/:org/repos. Request includes :
//   Path param org with value cucumber
//2. Verify that id field is unique in every in every object in the response
//3. Verify that node_id field is unique in every in every object in the response
    @Test
    public void test3(){
        Response response=given().accept(ContentType.JSON).
                pathParam("org","cucumber").
                when().
                get("/orgs/{org}/repos");
        response.then().
                contentType("application/json; charset=utf-8").
                statusCode(200);
            List<String> public_repos=response.jsonPath().getList("public_repos");
        System.out.println(public_repos.size());
        List<Map<String,?>> numOfObject=response.body().as(List.class);
        System.out.println(numOfObject.size());
        assertEquals(public_repos.size(),numOfObject.size());

    }
//    Repository owner information
//1. Send a get request to /orgs/:org. Request includes :
//            • Path param org with value cucumber
//2. Grab the value of the field id
//3. Send a get request to /orgs/:org/repos. Request includes :
//            • Path param org with value cucumber
//4. Verify that value of the id inside the owner object in every response is equal to value from step 2
    @Test
    public void test5(){
    Response response1= given().accept(ContentType.JSON).
            pathParam("org","cucumber").
            when().
            get("/orgs/{org}");
        int idNum = response1.jsonPath().getInt("id");
        System.out.println(idNum);
//    List <Integer> id=response1.jsonPath().getList("response1.id");//incomplete
//        System.out.println(id);

        Response response2=given().accept(ContentType.JSON).
                pathParam("org","cucumber").
                when().
                get("/orgs/{org}/repos");
        List<Integer> ownerId=response2.jsonPath().getList("owner.id");
        System.out.println(ownerId.size());//30
    }
    @Test
    public void test6(){
        Response response= given().accept(ContentType.JSON).
                queryParam("sort","full_name").
                pathParam("org","cucumber").
                when().
                get("/orgs/{org}");
        List<String>listOfName=response.jsonPath().getList("name");
        System.out.println(listOfName);
    }
    @Test
    public void test7(){

        Response response = given().accept(ContentType.JSON).
                queryParam("sort", "full_name").
                queryParam("direction","desc").
                pathParam("org", "cucumber").
                when().
                get("/orgs/{org}");
        List<String> created_at = response.jsonPath().getList("created_at");
        System.out.println(created_at);
    }
    @Test
    public void test8(){

        Response response = given().accept(ContentType.JSON).
                queryParam("sort", "full_name").
                pathParam("org", "cucumber").
                when().
                get("/orgs/{org}");
        List<String> created_at = response.jsonPath().getList("created_at");

        System.out.println(created_at);
    }
}


