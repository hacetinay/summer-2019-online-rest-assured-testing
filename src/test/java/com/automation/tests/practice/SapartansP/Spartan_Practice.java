package com.automation.tests.practice.SapartansP;

import com.automation.utilities.ConfigurationReader;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.baseURI;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.response.Response;

import java.util.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
public class Spartan_Practice {

           @BeforeAll
        public static void setup(){
            baseURI = ConfigurationReader.getProperty("spartan.uri");
        }
@Test
    public void test1(){
        Response response=  given().accept("application/json;charset=UTF-8").
                pathParam("id",11).
                when().
                get("/spartans/{id}");
        assertEquals(200,response.statusCode());
        assertEquals("application/json;charset=UTF-8",response.contentType());
        JsonPath jsonPath=response.jsonPath();
        String name=jsonPath.getString("name");
        int id=jsonPath.getInt("id");
        String gender=jsonPath.getString("gender");
        long phone=jsonPath.getLong("phone");
        assertEquals("Nona",name);
        assertEquals(11L,id);
        assertEquals("Female",gender);
        assertEquals(7959094216L,phone);
        System.out.println("name = " + name);
        System.out.println("phone = " + phone);
        System.out.println("gender = " + gender);
        System.out.println("id = " + id);

    }
@Test
    public void test2(){
               JsonPath jsonPath=get("http://api.cybertektraining.com/student/2631").jsonPath();
               String lastName=jsonPath.getString("students.lastName");
    String batch=jsonPath.getString("students.batch");
               String phone=jsonPath.getString("students.contact.phone");
               String city=jsonPath.getString("students.company.address.city");
    System.out.println("city = " + city);
    System.out.println("batch = " + batch);
    System.out.println("phone = " + phone);
    System.out.println("lastName = " + lastName);

}
@Test
    public void test3() {
    JsonPath jsonPath = get("http://ec2-34-201-69-55.compute-1.amazonaws.com:1000/ords/hr/employees").jsonPath();
//    String first_name = jsonPath.getString("items[1].first_name");
//    System.out.println("first_name = " + first_name);
//      jsonPath.prettyPrint();
    List<String> firstNames=jsonPath.getList("items.first_name");
    for (String list:firstNames){
        System.out.println(list);
    }
    System.out.println("firstNames = " + firstNames);
}

//    {
//           "id": 15,
//            "name": "Meta",
//            "gender": "Female",
//            "phone": 1938695106
//    }
@Test
    public void test4(){
               given().accept(ContentType.JSON).
                       pathParam("id", 15).
                       when().
                       get("/spartans/{id}").
                       then().
                       assertThat().body("name",equalTo("Meta")).
                       assertThat().body("id",equalTo(15)).
                       assertThat().body("gender",equalTo("Female")).
                       assertThat().body("phone", equalTo(1938695106)).
                       and().
                       log().all(true);

}
//    {
//        "id": 33,
//            "name": "Wilek",
//            "gender": "Male",
//            "phone": 2877865902
//    }
    @Test
    public void test5(){
            Response response=given().accept(ContentType.JSON).
                    pathParam("id",33).
                    when().
                    get("/spartans/{id}");
//            JsonPath jsonPath=response.jsonPath();
            Map<String,Object> map=response.body().as(Map.class);

        System.out.println("map = " + map);
    }
    @Test
    public void test6(){

               Map<String,Object> newSpartan=new HashMap<>();
               newSpartan.put("name","Haci");
               newSpartan.put("gender","Male");
               newSpartan.put("phone", 48423615117L);
              given().
                       accept(ContentType.JSON).
                       and().contentType(ContentType.JSON).
                       body(newSpartan).
                       when().
                       post("/spartans").prettyPeek().
                      then().
                      assertThat().body("success",is("A Spartan is Born!")).
                      assertThat().body("data.id",is(127)).
                      assertThat().body("data.name", is("Haci")).
                      assertThat().body("data.gender",is("Male")).
                      assertThat().body("data.phone",is(48423615117L));


    }
}
