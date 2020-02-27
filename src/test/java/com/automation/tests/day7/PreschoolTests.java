package com.automation.tests.day7;
import com.automation.pojos.Student;
import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

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

import static io.restassured.RestAssured.*;
public class PreschoolTests {

    @BeforeAll
    public static void setup(){
        baseURI=ConfigurationReader.getProperty("school.uri");
    }
    @Test
    public void test(){
        Response response=given().accept(ContentType.JSON).
                pathParam("id",2633).
                when().
                get("/student/{id}").prettyPeek();
        Student student=response.jsonPath().getObject("students[0]", Student.class);
        System.out.println("student = " + student);

        assertEquals(2633, student.getStudentId());
        assertEquals(11, student.getBatch());
        assertEquals("123456" ,student.getAdmissionNo());
        assertEquals("7925 Jones Branch Dr #3300", student.getContact().getPermanentAddress());
    }


}
