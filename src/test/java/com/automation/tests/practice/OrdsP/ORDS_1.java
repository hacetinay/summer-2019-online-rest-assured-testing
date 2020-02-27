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
import org.junit.jupiter.api.BeforeAll;

public class ORDS_1 {
    @BeforeAll
    public static void setup(){
        baseURI=ConfigurationReader.getProperty("ords.uri");
    }
    @Test
    public void test1(){
      given().accept(ContentType.JSON).
               and().
                queryParam("q","{\"region_id\":2}").
                when().
                get("/countries").
                then().
                assertThat().statusCode(200).
                assertThat().contentType(ContentType.JSON).
                and().log().all(true);
    }
@Test
    public void test2(){
        given().accept(ContentType.JSON).
                pathParam("id",103).
                when().get("/employees/{id}").
                then().
                assertThat().statusCode(200).
                assertThat().contentType(ContentType.JSON).
                assertThat().body("first_name",is("Alexander"), "last_name",is("Hunold"),
                "email",is("AHUNOLD"), "phone_number",is("590.423.4567")).log().all(true);
}
        @Test
    public void test3(){
        Response response=given().
        queryParam("q","{\"region_id\":2}").
                when().get("/countries");

            String countryName=response.path("items.country_name[0]");
        String country_id=response.path("items.country_id[1]");

            List<String> country_id2=response.path("items.country_name");
            System.out.println(countryName);
            System.out.println(country_id);
            System.out.println(country_id2);
        }
    @Test
    public void test4() {
        given().accept(ContentType.JSON).
                pathParam("id", "US").
                when().
                get("/countries/{id}").then().
                statusCode(200).contentType(ContentType.JSON).
                assertThat().
                body("country_id", is("US"),
                        "country_name", is("United States of America"),
                        "region_id", is(2)).log().all(true);

    }
    @Test
    public void test5() {
                given().
                    accept(ContentType.JSON).
                    queryParam("q","{\"department_id\":80}").
                when().
                    get("/employees").
                then().
                    statusCode(200).
                    contentType(ContentType.JSON).
                and().
                    assertThat().body("items.first_name[0]",is(startsWith("Jo"))).
                    assertThat().body("items.department_id[0]",is(80)).
                        assertThat().body("count",is(25)).//count is sibling of item so i don't need to write item.count
                    log().all(true);

    }
    @Test
    public void test6() {
       Response response= given().
                accept(ContentType.JSON).
                queryParam("q", "{\"department_id\":80}").
                when().
                get("/employees");
        List<String> jobID=response.path("items.job_id");
        List<Integer> department_id=response.path("items.job_id");




    }
}
