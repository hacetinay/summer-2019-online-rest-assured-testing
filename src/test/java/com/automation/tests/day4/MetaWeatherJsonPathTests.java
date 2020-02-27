package com.automation.tests.day4;

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
public class MetaWeatherJsonPathTests {
    @BeforeAll
    public static void setup(){
        baseURI=ConfigurationReader.getProperty("meta.weather.uri");
    }
/**
 * TASK
 * Given accept type is JSON
 * When users sends a GET request to "/search"
 * And query parameter is 'New'
 * Then user verifies that payload contains 5 objects
 */
@Test
public void teat1(){

}
/**
 *TASK
 * Given accept type is JSON
 * When users sends a GET request to "/search"
 * And query parameter is New
 * Then user verifies that 1st object has following info:
 *  |title   |location_type|woeid  |latt_long          |
 *  |New York|City         |2459115|40.71455,-74.007118|
 */
@Test
public void teat2(){

}
/**
 *TASK
 * Given accept type is JSON
 * When users sends a GET request to "/search"
 * And query parameter is 'Las'
 * Then user verifies that payload  contains following titles:
 *  |Glasgow  |
 *  |Dallas   |
 *  |Las Vegas|
 *
 */
@Test
public void teat3(){

}
/**
 *
 *TASK
 * Given accept type is JSON
 * When users sends a GET request to "/search"
 * And query parameter is 'Las'
 * Then verify that every item in payload has location_type City
 *
 */
@Test
public void teat4(){
        given().
                accept(ContentType.JSON).
                queryParam("query","Las").
                get("/search").
                then().
                assertThat().body("location_type",everyItem(is("City"))).
                log().all(true);




}
    /**
     *TASK
     * Given accept type is JSON
     * When users sends a GET request to "/location"
     * And path parameter is '44418'
     * Then verify following that payload contains weather forecast sources
     * |BBC                 |
     * |Forecast.io         |
     * |HAMweather          |
     * |Met Office          |
     * |OpenWeatherMap      |
     * |Weather Underground |
     * |World Weather Online|
     *
     *
     */
    @Test
    @DisplayName("Verify following that payload contains weather forecast sources")
    public void test5(){
        List<String> expected = Arrays.asList("BBC","Forecast.io","HAMweather","Met Office",
                "OpenWeatherMap","Weather Underground",
                "World Weather Online");
        Response response =   given().
                accept(ContentType.JSON).
                pathParam("woeid", 44418).
                when().
                get("/location/{woeid}");
        List<String> actual = response.jsonPath().getList("sources.title");
        assertEquals(expected, actual);
        System.out.println(expected);
        System.out.println(actual);
    }


}
