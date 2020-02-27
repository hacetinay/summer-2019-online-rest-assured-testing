package com.automation.tests.practice.CybertekTraining;
import com.automation.pojos.Student;
import com.automation.tests.practice.POJOP.StudentP;
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
public class PreschoolP {
    @BeforeAll
    public static void setUp(){
        baseURI=ConfigurationReader.getProperty("school.uri");
    }
    @Test
    @DisplayName("Get student with id 2633 and convert payload into POJO")
    public void test1() {
        Response response = given().
                accept(ContentType.JSON).
                pathParam("id", 2633).
                when().
                get("/student/{id}").prettyPeek();
        //deserialization
        // from JSON to POJO
        //student - is a POJO
        Student student = response.jsonPath().getObject("students[0]", Student.class);

        System.out.println(student);

    }

}
