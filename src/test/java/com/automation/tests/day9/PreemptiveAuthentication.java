package com.automation.tests.day9;
import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
public class PreemptiveAuthentication {
    @BeforeAll
    public static void setup() {
        baseURI = "http://practice.cybertekschool.com";
        //https is not working because of SSL certificate issues
    }
    @Test
    @DisplayName("Normal basic authentication test")
    public void test1(){
        given().
                auth().basic("admin", "admin").
                when().
                get("/basic_auth").prettyPeek().
                then().assertThat().statusCode(200);
    }
    @Test
    @DisplayName("Preemptive authentication")
    public void test2(){
        given().
                auth().preemptive().basic("admin", "admin").
                when().
                get("/basic_auth").prettyPeek().
                then().assertThat().statusCode(200);
    }
    /**
     * Method that generates access token
     * @return bearer token
     */
    public static String getTokenForBookit(){
        Response response = given().
                queryParam("email", ConfigurationReader.getProperty("team.leader.email")).
                queryParam("password", ConfigurationReader.getProperty("team.leader.password")).
                when().
                get("/sign").prettyPeek();
        return  response.jsonPath().getString("accessToken");
    }
    /**
     * Method that generates access token
     * @param role - type of user. allowed types: student team leader, student team member and teacher
     * @return bearer token
     */
    public static String getTokenForBookit(String role) {
        String userName = "";
        String password = "";
        if (role.toLowerCase().contains("lead")) {
            userName = ConfigurationReader.getProperty("team.leader.email");
            password = ConfigurationReader.getProperty("team.leader.password");
        } else if (role.toLowerCase().contains("teacher")) {
            userName = ConfigurationReader.getProperty("teacher.email");
            password = ConfigurationReader.getProperty("teacher.password");
        } else if (role.toLowerCase().contains("member")) {
            userName = ConfigurationReader.getProperty("team.member.email");
            password = ConfigurationReader.getProperty("team.member.password");
        } else {
            throw new RuntimeException("Invalid user type!");
        }
        Response response = given().
                queryParam("email", userName).
                queryParam("password", password).
                when().
                get("/sign").prettyPeek();
        return response.jsonPath().getString("accessToken");
    }
}