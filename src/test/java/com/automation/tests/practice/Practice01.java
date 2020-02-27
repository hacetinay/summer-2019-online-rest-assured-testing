package com.automation.tests.practice;
import com.automation.utilities.ConfigurationReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class Practice01 {
    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("ords.uri");
    }

    //    String baseURI="http://ec2-34-201-69-55.compute-1.amazonaws.com:1000/ords/hr";
    @Test
    public void test1() {
        Response response = RestAssured.given().get(baseURI + "/employees");
        assertEquals(200, response.getStatusCode());
        System.out.println(response.getContentType());
        System.out.println(response.body().prettyPeek());

    }

    @Test
    public void test2() {
        given().
                accept("applicarion/json").
                when().
                get("/employees/").
                then().
                assertThat().statusCode(200);
    }

    @Test
    public void test3() {
        List<String> regions = given().
                accept(ContentType.JSON).
                when().
                get("/regions").
                thenReturn().jsonPath().getList("items.region_name");
        System.out.println(regions);

    }

    @Test
    public void test4() {
        given().
                accept(ContentType.JSON).
                get("/employees").
                then().
                assertThat().statusCode(200).
                and().
                assertThat().contentType("application/json").log().all(true);
    }

    @Test
    public void test5() {
        given().
                accept(ContentType.JSON).
                pathParam("id", 106).
                when().get("/employees/{id}").
                then().assertThat().statusCode(200).
                and().assertThat().body("first_name", is("Valli"),
                "employee_id", is(106),
                "email", is("VPATABAL"),
                "salary", is(4800)).log().all(true);
    }

    @Test
    public void test6() {
        given().accept(ContentType.JSON).
                pathParam("id", 103).
                when().get("/employees/{id}").
                then().assertThat().statusCode(200)
                .and().assertThat().body("manager_id", is(102),
                "job_id", is("IT_PROG"),
                "first_name", is("Alexander")).log().all(true);
    }

    @Test
    public void test7() {
        given().accept(ContentType.JSON).
                when().get("/employees/103").
                then().assertThat().statusCode(200)
                .and().assertThat().body("manager_id", is(102),
                "job_id", is("IT_PROG"),
                "first_name", is("Alexander")).log().all(true);
    }

    @Test
    public void test8() {
        given().accept(ContentType.JSON).
                when().get("/employees/900").
                then().assertThat().statusCode(404).
                and().contentType("text/html");
    }

    @Test
    public void test9() {
        given().
                accept(ContentType.JSON).
                when().
                get("/locations/1700").
                then().
                assertThat().body("location_id", is(1700)).
                assertThat().body("postal_code", is("98199")).
                assertThat().body("city", is("Seattle")).
                assertThat().body("state_province", is("Washington"));
    }
@Test
    public void test10() {
        List<String> phoneNumbers = given().
                accept("application/json").
                when().get("/employees").
                thenReturn().jsonPath().get("items.phone_number"); //it calls Gpath (GroovyPath), like Xpath(XMLpath),

//        Replaces each element of this list with the result of applying the operator to that element.
//        replace '.' with '-' in every value
        phoneNumbers.replaceAll(phone -> phone.replace(".", "-"));

        System.out.println(phoneNumbers);
    }
    @Test
    public void test11(){
        List<Integer> expectedSalaries = List.of(24000, 17000, 17000, 12008, 11000,
                9000, 9000, 8200, 8200, 8000,
                7900, 7800, 7700, 6900, 6500,
                6000, 5800, 4800, 4800, 4200,
                3100, 2900, 2800, 2600, 2500);
        List<Integer> actualSalaries=given().accept(ContentType.JSON).
                get("/employees").
                jsonPath().getList("items.salary");
        Collections.sort(actualSalaries);//Collection does not accept List<?>
        Collections.reverse(actualSalaries);
        assertEquals(expectedSalaries,actualSalaries);
        System.out.println(expectedSalaries);
        System.out.println(actualSalaries);
    }

    @Test
    public void test12(){
        List<String > emails=given().accept(ContentType.JSON).
                             and().get("/employees").
                             andReturn().jsonPath().get("items.email");
        System.out.println(emails);
    }

    @Test
    public void test13(){
        List<String> phoneNums=given().accept(ContentType.JSON).
                and().
                get("/employees").
                jsonPath().getList("items.phone_number");
        phoneNums.replaceAll(p->p.replace(".","$"));
        System.out.println("phoneNums = " + phoneNums);
    }
    @Test
    public void test14() {
        List<String> depIDs = given().accept(ContentType.JSON).
                when().get("/employees").jsonPath().getList("items.department_id");

        System.out.println(depIDs);
    }


}