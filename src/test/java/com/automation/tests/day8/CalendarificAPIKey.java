package com.automation.tests.day8;
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
public class CalendarificAPIKey {
    @BeforeAll
    public static void setUp(){
        baseURI=ConfigurationReader.getProperty("calendar.uri");
    }
    /**
     * API key is a secret that the API generates and gives to the developer.
     * API key looks like long string: ebe88078e981c84bedeb9e8a34ad927560e545f2
     * API key can go as query parameter or inside a header,
     * it depends on web service how you must pass API key (as header parameter or query parameter)
     * How it get's created? You go to web site, register, and service gives you API key
     * Then you have to pass API key alongside with every request
     * API key is easy to implement for developer and client
     * But, non-technical people have no idea about this
     * So it's mostly used by developers only
     */
    /**
     * Given accept content type as JSON
     * When user sends GET request to "/countries"
     * Then user verifies that status code is 401
     * And user verifies that status line contains "Unauthorized" message
     * And user verifies that meta.error_detail contains "Missing or invalid api credentials." message
     */
    @Test
    public void test1(){
        given().accept(ContentType.JSON).
//                queryParam("api_key","7405e1fd70c6d3a643c8c2506698673d9bc29363").
                when().
                get("/countries").prettyPeek().
                then().
                assertThat().statusCode(401).
                statusLine(containsString("Unauthorized")).
                contentType(ContentType.JSON).
                body("meta.error_detail",is("Missing or invalid api credentials. See https://calendarific.com/api for details.")).
                body("meta.error_type",is("auth failed")).
                log().all(true);


    }
    @Test
    public void test2(){
        given().accept(ContentType.JSON).
                queryParam("api_key","7405e1fd70c6d3a643c8c2506698673d9bc29363").
        when().
                get("/countries").prettyPeek().
                then().
                assertThat().statusCode(200).
                             contentType(ContentType.JSON).
                             body("meta.code",is(200)).
                             body("response.countries.country_name[0]",is("Afghanistan")).
                log().all(true);

    }
    @Test
    public void test3(){
        Response response=given().accept(ContentType.JSON).
                queryParam("api_key","7405e1fd70c6d3a643c8c2506698673d9bc29363").
                when().
                get("/countries").prettyPeek();
//                then().
//                assertThat().statusCode(200).
//                assertThat().contentType(ContentType.JSON).
//                assertThat().body("meta.code",is(200)).
//                assertThat().body("response.countries.country_name[0]",is("Afghanistan")).
//                log().all(true);
//        String firstCountry=response.
    }
    /**
     * Given accept content type as JSON
     * And query parameter api_key with valid API key
     * When user sends GET request to "/countries"
     * Then user verifies that status code is 200
     * And user verifies that status line contains "OK" message
     * And user verifies that countries array is not empty
     */
    @Test
    public void test4() {
        Response response = given().accept(ContentType.JSON).
                queryParam("api_key", "7405e1fd70c6d3a643c8c2506698673d9bc29363").
                when().
                get("/countries");
        assertEquals("application/json",response.contentType());
        assertEquals(200,response.statusCode());
        String statusLine=response.statusLine();
        assertEquals(true,statusLine.contains("OK"));
//        response.then().assertThat().body()
        List<String> listOfCountries=response.jsonPath().getList("response.countries.country_name");
        System.out.println("listOfCountries = " + listOfCountries);
    }
    /**
     *
     * Given accept content type as JSON
     * And query parameter api_key with valid API key
     * And query parameter country is equals to US
     * And query parameter type is equals to national
     * And query parameter year is equals to 2019
     * When user sends GET request to "/holidays"
     * Then user verifies that number of national holidays in US is equals to 11
     */
    @Test
    public void test5(){
       Response response= given().accept(ContentType.JSON).
                queryParam("api_key","7405e1fd70c6d3a643c8c2506698673d9bc29363").
                queryParam("country","US").
                queryParam("type","national").
                queryParam("year","2019").
                when().
                get("/holidays");
       response.then().assertThat().statusCode(200).body("response.holidays",hasSize(11));
       List<Map<String,?>> holidays=response.jsonPath().getList("response.holidays");
        System.out.println(holidays.size());
        assertEquals(11,holidays.size(),"Wrong numner of holiday");
//        assertEquals(200,response.getStatusCode());
//        assertEquals(ContentType.JSON,response.contentType());

    }
    /**
     *  ####### WARM-UP TASK ########
     * Given accept content type as JSON
     * And query parameter api_key with valid API key
     * When user sends GET request to "/countries"
     * Then user verifies that total number of holidays in United Kingdom is equals to 95
     *
     * website: https://calendarific.com/
     */
    @Test
    public void test6(){
        Response response=given().accept(ContentType.JSON).
                queryParam("api_key", "7405e1fd70c6d3a643c8c2506698673d9bc29363").
                when().
                get("/countries");
        response.
                then().
                contentType(ContentType.JSON).statusCode(200);


//                assertThat().body("response.countries.total_holidays",is(95)).

        List<String> countries=response.jsonPath().getList("response.countries.country_name");
        System.out.println(countries);
        System.out.println("Index of 'United Kingdom': "+countries.indexOf("United Kingdom"));
        int indexOfUK=countries.indexOf("United Kingdom");
//        for(int i=0;i<=countries.size();i++){
//           if( countries.get(i)=="United Kingdom"){
//               break;
//
//           }
//            System.out.println(countries.get(i));
        response.   then().assertThat().
        body("response.countries.country_name["+indexOfUK+"]",is("United Kingdom")).
        body("response.countries.total_holidays["+indexOfUK+"]",is(95));
        int numberHolidays = response.jsonPath().get("response.countries.find {it.country_name == 'United Kingdom'}.total_holidays");
        System.out.println(numberHolidays);
    }
    @Test
    @DisplayName("user verifies that total number of holidays in United Kingdom is equals to 95")
    public void TC1(){
        Response response=
                given().accept(ContentType.JSON).
                        queryParam("api_key","aaf312cd592b0c7b22d90dd82e805dd4cf182dd5")
                        .when().get("/countries").prettyPeek();
        response.then().assertThat().statusCode(200);
        //Gpath - it's like Xpath, stands for searching values in JSON file.
        //GPath: response.countries.find {it.country_name == 'United Kingdom'}.total_holidays
        //find - method to find some parameter
        //{it.parameter_name == value} find JSON object that is matching criteria
        //.parameter_that_you_want = return this parameter after filtering
        int numberHolidays = response.jsonPath().get("response.countries.find {it.country_name == 'United Kingdom'}.total_holidays");

        //get all countries where number supported_languages is = 4

        List<String> countries = response.jsonPath().get("response.countries.findAll {it.supported_languages == 4}.country_name");

        System.out.println(countries);

        assertEquals(95,numberHolidays);
        System.out.println("MY TESTS PASSED SUCCESSFULLY😍😍");
    }

    @Test
    @DisplayName("user verifies that total number of holidays in United Kingdom is equals to 95")
    public void TC1_2(){
        Response response=
                given().accept(ContentType.JSON).
                        queryParam("api_key","aaf312cd592b0c7b22d90dd82e805dd4cf182dd5")
                        .queryParam("country","GB")
                        .queryParam("year",2019)
                        .when().get("/holidays").prettyPeek();
        response.then().assertThat().statusCode(200);
        List<Map<String,?>> holidays=response.jsonPath().get("response.holidays");
        assertEquals(95,holidays.size());
        System.out.println("MY TESTS PASSED SUCCESSFULLY😍😍");
    }
    @Test
    public void test7(){
        Response response=given().accept(ContentType.JSON).
                queryParam("api_key", "7405e1fd70c6d3a643c8c2506698673d9bc29363").
                when().
                get("/countries").prettyPeek();
        response.
                then().
                contentType(ContentType.JSON).statusCode(200);

        List<String> countries=response.jsonPath().getList("response.countries.country_name");
        System.out.println(countries);
        System.out.println("Index of 'United Kingdom': "+countries.indexOf("United Kingdom"));
        int indexOfUK=countries.indexOf("United Kingdom");

        response.   then().assertThat().
                body("response.countries.country_name["+indexOfUK+"]",is("United Kingdom")).
                body("response.countries.total_holidays["+indexOfUK+"]",is(95));
        int numberHolidays = response.jsonPath().get("response.countries.find {it.country_name == 'United Kingdom'}.total_holidays");
        System.out.println(numberHolidays);
        List<String> countries2 = response.jsonPath().get("response.countries.findAll {it.supported_languages == 4}.country_name");
        System.out.println("countries2 = " + countries2);
    }


}



