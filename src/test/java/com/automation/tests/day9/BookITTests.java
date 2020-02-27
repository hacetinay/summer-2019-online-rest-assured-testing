package com.automation.tests.day9;
import com.automation.pojos.Room;
import com.automation.pojos.Spartan;
import com.automation.utilities.APIUtilities;
import com.automation.utilities.ExcelUtil;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import com.automation.pojos.Job;
import com.automation.pojos.Location;
import com.automation.utilities.ConfigurationReader;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
public class BookITTests {
    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("bookit.qa1");
    }
    /**
     * Given accept content type as JSON
     * When user sends get requests to /api/rooms
     * Then user should get 401 status code
     */
    @Test
    @DisplayName("Verify that user cannot access bookit API without providing credentials")
    public void test1() {
        given().
                accept(ContentType.JSON).
                when().
                get("/api/rooms").
                then().assertThat().statusCode(401).log().all(true);
        //this service doesn't return 401, it returns 422
        //is it correct or wrong? good time talk to developer and check business requirements
    }
    /**
     * Given accept content type as JSON
     * And user provides invalid token
     * When user sends get requests to /api/rooms
     * Then user should get 422 status code
     */
    @Test
    @DisplayName("Verify that system doesn't accept invalid token")
    public void test2() {
        //
        //same procedure: you need to provide token
        //since bearer token was originally created for oauth 2.0
        //it works in the same way
        //auth().oauth2()
        //500 Server Error - server is in trouble
        given().
                accept(ContentType.JSON).
                header("Authorization", "invalid token").
                when().
                get("/api/rooms").prettyPeek().
                then().assertThat().statusCode(422);
    }
    /**
     * given valid bearer token
     * when user performs GET request to "/api/rooms"
     * then user should get list of rooms in the payload
     * and status code 200
     */
    @Test
    public void test3() {
        given().auth().oauth2(APIUtilities.getTokenForBookit()).
                accept(ContentType.JSON).
                when().
                get("/api/rooms").prettyPeek();
    }
    @Test
    @DisplayName("Get all roms and deserialize it into collection of Rooms")
    public void test4() {
        //in real work environment, common practice is to authenticate with SSL certificate
        //you add SSL certificate on your side, with every request
        //and then you can work with web service
        Response response = given().auth().oauth2(APIUtilities.getTokenForBookit()).
                accept(ContentType.JSON).
                when().
                get("/api/rooms").prettyPeek();
        List<Room> rooms = response.jsonPath().getList("", Room.class);
        for (Room room : rooms) {
            System.out.println(room.getName());
        }
    }
    @Test
    @DisplayName("Create a new student")
    public void test5() {
    }
    @Test
    @DisplayName("Verify that B12 exists")
    public void test5_1() {
        ///api/batches/{batch-number}
        given().
                auth().oauth2(APIUtilities.getTokenForBookit()).
                when().
                get("/api/batches/{batch-number}", 12).
                then().assertThat().statusCode(200).log().body(true);
    }
//    campus-location  required   name of the campus which team will be added to
//    batch-number required   number of the batch which team will be added to
//    team-name    required   name of the team, should be uniq per campus
    /**
     * given valid token is provided for student team member
     * and user provides following query parameters
     *  |campus-location|batch-number|team-name     |
     *  |     VA        |    12      |Online_Hackers|
     *  when user performs POST request to "/api/teams/team"
     *  then user should verify that status code is 403
     */
    @Test
    @DisplayName("Create a new team in B12 (negative)")
    public void test6(){
        //Online_Hackers
//        POST /api/teams/team
        given().
                auth().oauth2(APIUtilities.getTokenForBookit()).
                queryParam("campus-location", "VA").
                queryParam("batch-number", 12).
                queryParam("team-name", "Online_Hackers").
                when().
                post("/api/teams/team").
                then().assertThat().statusCode(403).log().all(true);
        //only teacher allowed to modify database. <---- authorization
        // 403 Forbidden - that means you are not authorized to do this
    }
    /**
     * given valid token is provided for teacher
     * and user provides following query parameters
     *  |campus-location|batch-number|team-name     |
     *  |     VA        |    12      |Online_Hackers|
     *  when user performs POST request to "/api/teams/team"
     *  then user should verify that status code is 201
     */
    @Test
    @DisplayName("Create a new team in B12 (positive)")
    public void test7(){
        //Online_Hackers
//        POST /api/teams/team
        given().
                auth().oauth2(APIUtilities.getTokenForBookit("teacher")).
                queryParam("campus-location", "VA").
                queryParam("batch-number", 12).
                queryParam("team-name", "Online_Hackers").
                when().
                post("/api/teams/team").
                then().log().all(true);
        //only teacher allowed to modify database. <---- authorization
    }
    //test: add yourself to the team
    /**
     *  {
     *             "id": 5443,
     *             "name": "Online_Hackers",
     *             "members": [
     *
     *             ]
     *         },
     */
    /**
     * POST /api/students/student
     *
     * Query Parameters
     * Parameter       Demand     Description
     * first-name      required   first name of the student
     * last-name       required   last name of the student
     * email           required   email of the student, will be used for an authentication
     * password            required   password of the account, will be used for an authentication
     * role            required   role of the student, [student-team-leader, student-team-member]
     * campus-location required   name of the campus which student will be added to
     * batch-number        required   number of the batch which student will be added to
     * team-name       required   name of the team which student will be added to
     *
     * given valid token is provided for student team leader
     * and user provides following query parameters
     *  |first-name  |last-name    |email         |password    |role               |campus-location|batch-number|team-name      |
     *  |    YourName|YourLastName |temp@email.com| anypassword|student-team-member|      VA       |    12      | Online_Hackers|
     *  when user performs POST request to "/api/students/student"
     *  then user should verify that status code is 403
     */
    @Test
    @DisplayName("Create a new student (positive test)")
    public void test8() {
        given().
                auth().oauth2(APIUtilities.getTokenForBookit("teacher")).
                queryParam("first_name","Haci").
                queryParam("last_name","Cetinay").
                queryParam("email","haci@gmai.com").
                queryParam("password","1234").
                queryParam("role","team-member").
                queryParam("campus-location","PA").
                queryParam("batch-numbe",12).
                queryParam("team-nam","Online_Hackers").
                when().
                post("/api/students/student").
                then().assertThat().statusCode(201).log().all(true);
    }
    @Test
    @DisplayName("Create a new student")
    public void test9() {
        given().
                auth().oauth2(APIUtilities.getTokenForBookit()).
                queryParam("first-name", "Vasyl").
                queryParam("last-name", "Fomiuk").
                queryParam("email", "baxex65713@hiwave.org").
                queryParam("password", "1234").
                queryParam("role", "student-team-leader").
                queryParam("campus-location", "VA").
                queryParam("batch-number", 12).
                queryParam("team-name", "Online_Hackers").
                when().
                post("/api/students/student").
                then().assertThat().statusCode(403).log().all(true);


    }
    @Test
    @DisplayName("Create a new student (positive test)")
    public void test10() {
        given().
                auth().oauth2(APIUtilities.getTokenForBookit("teacher")).
                queryParam("first-name", "namr").
                queryParam("last-name", "name").
                queryParam("email", "email@enayu.com").
                queryParam("password", "1234").
                queryParam("role", "student-team-leader").
                queryParam("campus-location", "VA").
                queryParam("batch-number", 12).
                queryParam("team-name", "Online_Hackers").
                when().
                post("/api/students/student").
                then().statusCode(201).log().all(true);
//        as a response, I've got this one
//        HTTP/1.1 201 Created
//        Connection: close
//        Date: Tue, 04 Feb 2020 20:22:03 GMT
//        Content-Type: application/json
//        Server: Jetty(9.4.8.v20171121)
//        Via: 1.1 vegur
//
//        user Vasyl Fomiuk has been added to database.
        // if you are getting 422, that means email already exists
    }
    @Test
    @DisplayName("")
    public void test11(){
        given().auth().oauth2(APIUtilities.getTokenForBookit("teacher")).
                when().
                delete("/api/students/{id}",5539).
                then().assertThat().statusCode(204).log().all(true);//after deletion it gives 204 as statuscode

    }

    @Test
    public void test12(){
        Response response=given().auth().oauth2(APIUtilities.getTokenForBookit("teacher")).
                when().
                get("/api/teams/{id}",5539);
//        response.
//                then().log().all(true);
        List<Integer>id=response.jsonPath().getList("members.id");
        System.out.println(id);
        for (int i=0; i<id.size(); i++){
            given().auth().oauth2(APIUtilities.getTokenForBookit("teacher")).
                    when().
                    delete("/api/teams/{id}",id.get(i)).
                    then().assertThat().statusCode(204).log().ifError();
            System.out.println(id.get(i));
        }
    }
    @Test
    @DisplayName("Add new students from excel file")
    public void test13() {
        String filePath = System.getProperty("user.dir") + "/src/test/resources/test_data/bookit_test_data.xlsx";
        ExcelUtil excelUtil = new ExcelUtil(filePath, "students");

        for (Map<String, String> map : excelUtil.getDataList()) {
            System.out.println(map);
        }
        //get the data from excel file
        List<Map<String, String>> students = excelUtil.getDataList();
        System.out.println(students);
        //generate token
        String token = APIUtilities.getTokenForBookit("teacher");
        //loop through the collection of students
        //and add them one by one
        //we can specify query parameters one by one
        //or put them as a map
        //make sure that key names in the map
        //matching query parameters
        for(Map<String, String> student: students) {
            given().
                    auth().oauth2(token).
                    queryParams(student).
                    when().
                    post("/api/students/student").
                    then().assertThat().statusCode(201).
                    log().body(true).log().ifError();
        }
        //log body of response
    }

}


