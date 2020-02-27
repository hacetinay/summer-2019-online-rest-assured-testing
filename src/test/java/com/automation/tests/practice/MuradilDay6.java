package com.automation.tests.practice;

import org.junit.jupiter.api.*;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.*;

import static io.restassured.RestAssured.*;

public class MuradilDay6 {

//"employee_id": 105,
//        "first_name": "David",
//        "last_name": "Austin",
//        "email": "DAUSTIN",
//        "phone_number": "590.423.4569",
//        "hire_date": "2005-06-25T04:00:00Z",
//        "job_id": "IT_PROG",
//        "salary": 4800,
//        "commission_pct": null,
//        "manager_id": 103,
//        "department_id": 60,
    @Test
    public void test1(){
        Response response = get("ec2-3-95-7-16.compute-1.amazonaws.com:1000/ords/hr/employees/105");

        Map<String,?> employeeMap=response.body().as(Map.class);
        System.out.println(employeeMap.toString());
        String first_name=employeeMap.get("first_name").toString();
        System.out.println("first_name = " + first_name);
        double employee_id=(Double) employeeMap.get("employee_id");
        System.out.println("employee_id = " + employee_id);
        String last_name=employeeMap.get("last_name").toString();
        System.out.println("last_name = " + last_name);
        String phone_number=employeeMap.get("phone_number").toString();
        System.out.println("phone_number = " + phone_number);

    }
    @Test
    public void test2(){
        Response response=given().
                                accept(ContentType.JSON).
                          when().
                                get("ec2-3-95-7-16.compute-1.amazonaws.com:8000/api/spartans");
        List<Map<String,?>> allSpartans=response.body().as(List.class);
        System.out.println("allSpartans = " + allSpartans);
        System.out.println(allSpartans.get(0));

        Map<String,?> mapOfFirst=allSpartans.get(0);
        System.out.println(mapOfFirst);
        System.out.println(mapOfFirst.get("gender"));
        int count=1;
        for (Map<String,?>spartans:allSpartans){
            System.out.println(count+"-spartan "+ spartans);
            count++;
        }

    }
}

