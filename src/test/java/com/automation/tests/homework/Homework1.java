package com.automation.tests.homework;
import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
public class Homework1 {
    @BeforeAll
    public static void setUp() {

        baseURI = ConfigurationReader.getProperty("uinames.uri");

    }

    //    No params test
//1. Send a get request without providing any parameters
//2. Verify status code 200, content type application/json; charset=utf-8
//3. Verify that name, surname, gender, region fields have value
    @Test
    public void test1() {
        given().accept(ContentType.JSON).
                get().
        then().
                assertThat().statusCode(200).
                contentType("application/json; charset=utf-8").
                body("name",not(empty())).
                body("surname",not(empty())).
                body("gender",not(empty())).
                body("region",not(empty())).
                log().all(true);
    }

    //    Gender test
//1. Create a request by providing query parameter: gender, male or female
//2. Verify status code 200, content type application/json; charset=utf-8
//3. Verify that value of gender field is same from step 1
    @Test
    public void test2() {
        given().accept("application/json; charset=utf-8").
                when().
//                queryParam("gender","male").
        get("/?gender=male").
                then().
                contentType("application/json; charset=utf-8").
                statusCode(200).
                assertThat().body("gender", is("male")).
                log().all(true);
    }

    //2 params test
//1. Create a request by providing query parameters: a valid region and gender
//    NOTE: Available region values are given in the documentation
//2. Verify status code 200, content type application/json; charset=utf-8
//            3. Verify that value of gender field is same from step 1
//            4. Verify that value of region field is same from step 1
    @Test
    public void test3() {
        given().accept("application/json; charset=utf-8").
                when().
                get("/?gender=male&region=Turkey").
                then().
                statusCode(200).
                contentType("application/json; charset=utf-8").
                assertThat().body("gender", is("male")).
                assertThat().body("region", is("Turkey")).
                and().log().all(true);

    }

    //    Invalid gender test
//1. Create a request by providing query parameter: invalid gender
//2. Verify status code 400 and status line contains Bad Request
//3. Verify that value of error field is Invalid gender
    @Test
    public void test4() {
        given().accept("application/json; charset=utf-8").
                when().
                get("?gender=malel").
                then().
                statusCode(400).
                contentType("application/json; charset=utf-8").
                assertThat().body("error", is("Invalid gender")).
                and().log().all(true);
    }

    //    Invalid region test
//1. Create a request by providing query parameter: invalid region
//2. Verify status code 400 and status line contains Bad Request
//3. Verify that value of error field is Region or language not found
    @Test
    public void test5() {
        given().accept("application/json; charset=utf-8").
                when().
                get("?region=malel").
                then().
                statusCode(400).
                contentType("application/json; charset=utf-8").
                assertThat().body("error", is("Region or language not found")).
                and().log().all(true);
    }

    //    Amount and regions test
//1. Create request by providing query parameters: a valid region and amount (must be bigger than 1)
//2. Verify status code 200, content type application/json; charset=utf-8
//3. Verify that all objects have different name+surname combination
    @Test
    public boolean test6() {
        Response response = given().accept("application/json; charset=utf-8").
                when().
                get("/?amount=3&region=Turkey").prettyPeek();

//                then().
////                statusCode(200).
////                contentType("application/json; charset=utf-8").
////                assertThat().body("gender",is("male")).
////                assertThat().body("region", is("Turkey")).
////                and().log().all(true);
        List<Object> fullNames = response.jsonPath().getList("name");

        System.out.println("fullNames = " + fullNames);
      boolean hasDuplicates=false;
        for (int i = 0; i <= fullNames.size(); i++) {
            for (int j = 0; j < fullNames.size();j++ ) {
                if (fullNames.get(i).equals(fullNames.get(j))&& i!=j) {
                    hasDuplicates=true;
                    System.out.println("Duplicate :"+fullNames.get(i));
                break;
                }
            }
        }
        return hasDuplicates;
//        assertFalse(hasDuplicates,"List has some duplicates");
    }

    //    3 params test
//1. Create a request by providing query parameters: a valid region, gender and amount (must be bigger than 1)
//2. Verify status code 200, content type application/json; charset=utf-8
//3. Verify that all objects the response have the same region and gender passed in step 1
    @Test
    public void test7() {
        Response response = given().accept("application/json; charset=utf-8").
                when().
                get("/?gender=male&region=Turkey&amount=5");
        assertEquals(200, response.getStatusCode());
        assertEquals("application/json; charset=utf-8", response.contentType());

        List<String> listOfGender = response.jsonPath().getList("gender");


        for (int i = 0; i <= listOfGender.size() - 2; i++) {
            if (listOfGender.get(i) != listOfGender.get(i + 1)) {
                System.out.println("passed:)");
            } else {
                System.out.println("failed");
            }
        }
            List<String> listOfRegion = response.jsonPath().getList("region");
            for (int i = 0; i <= listOfRegion.size() - 2; i++) {
                if (listOfRegion.get(i) != listOfRegion.get(i + 1)) {
                    System.out.println("passed:)");
                } else {
                    System.out.println("failed");
                }
            }
            assertEquals(listOfRegion.get(0), listOfGender.get(1));
//            assertEquals(listOfRegion.get(1), listOfGender.get(2));

        }

//    Amount count test
//1. Create a request by providing query parameter: amount (must be bigger than 1)
//2. Verify status code 200, content type application/json; charset=utf-8
//3. Verify that number of objects returned in the response is same as the amount passed in step 1
    @Test
    public void test8(){
        Response response = given().accept("application/json; charset=utf-8").
                when().
                get("/?amount=15");
        assertEquals(200, response.getStatusCode());
        assertEquals("application/json; charset=utf-8", response.contentType());
        List<String>list=response.jsonPath().getList("name");
        System.out.println(list);
        assertEquals(15, list.size());
        System.out.println(list.size());


    }

}