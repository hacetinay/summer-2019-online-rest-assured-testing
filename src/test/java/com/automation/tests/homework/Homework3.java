package com.automation.tests.homework;
import com.automation.utilities.ConfigurationReader;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
public class Homework3 {
    @BeforeAll
    public static void setUp(){
        baseURI=ConfigurationReader.getProperty("harrypotter.uri");
    }
//    Verify sorting hat
//1. Send a get request to /sortingHat. Request includes :
//2. Verify status code 200, content type application/json; charset=utf-8
//3. Verify that response body contains one of the following houses:
//            "Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff"
    @Test
    public void test1(){
        Response response=     given().accept("application/json; charset=utf-8").
                queryParam("key","$2a$10$v6/4oH1IH59SgkmugnPjqOc1wYDIc/y.aPOEhp.DaoMfOobNAg62q").
                when().
                get("/sortingHat");
       String str=response.body().asString();
        System.out.println(str);
       
        if (str.contains("Gryffindor")||str.contains("Ravenclaw")||str.contains("Slytherin")||str.contains("Hufflepuff")){
            System.out.println("passed :)");
        }else {
            System.out.println("failed :(");
        }



    }
//    Verify bad key
//1. Send a get request to /characters. Request includes :
//• Header Accept with value application/json
//• Query param key with value invalid
//2. Verify status code 401, content type application/json; charset=utf-8
//3. Verify response status line include message Unauthorized
//4. Verify that response body says "error": "API Key Not Found"
    @Test
    public void test2(){
        Response response=given().accept("application/json").
                queryParam("key","invalid").
                when().
                get("/characters");
        response.then().assertThat().
                statusCode(401).statusLine(containsString("Unauthorized")).
                body("error",is("API Key Not Found")).log().all(true);

    }
//    Verify no key
//1. Send a get request to /characters. Request includes :
// • Header Accept with value application/json
//2. Verify status code 409, content type application/json; charset=utf-8
//            3. Verify response status line include message Conflict
//4. Verify that response body says "error": "Must pass API key for request"
    @Test
    public void test3(){
        Response response=given().accept("application/json").
                queryParam("api_key","7405e1fd70c6d3a643c8c2506698673d9bc29363").
                when().
                get("/characters");
        response.then().assertThat().
                statusCode(409).statusLine(containsString("Conflict")).
                contentType("application/json; charset=utf-8").
                body("error",is("Must pass API key for request")).log().all(true);
    }

//    Verify number of characters
//1. Send a get request to /characters. Request includes :
//• Header Accept with value application/json
//• Query param key with value {{apiKey}}
//2. Verify status code 200, content type application/json; charset=utf-8
//            3. Verify response contains 194 characters
    @Test
    public void test4(){
   Response response=given().accept("application/json").
                queryParam("key","$2a$10$v6/4oH1IH59SgkmugnPjqOc1wYDIc/y.aPOEhp.DaoMfOobNAg62q").
                when().
                get("/characters");
//                response.then().assertThat().
//                statusCode(200).
//                contentType("application/json; charset=utf-8");
        List<Map<String,?>> characters=response.jsonPath().get();
        for(Map listOfCharacters:characters ) {
            System.out.println(listOfCharacters);

        }
        System.out.println(characters.size());
        assertEquals(200,response.statusCode());
        assertEquals("application/json; charset=utf-8",response.contentType());
        assertEquals(195,characters.size());

    }
//    Verify number of character id and house
//1. Send a get request to /characters. Request includes :
//• Header Accept with value application/json
//• Query param key with value {{apiKey}}
//2. Verify status code 200, content type application/json; charset=utf-8
//3. Verify all characters in the response have id field which is not empty
//4. Verify that value type of the field dumbledoresArmy is a boolean in all characters in the response
//5. Verify value of the house in all characters in the response is one of the following:
//            "Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff"
    @Test
    public void test5(){
        Response response=given().accept("application/json").
                queryParam("key","$2a$10$v6/4oH1IH59SgkmugnPjqOc1wYDIc/y.aPOEhp.DaoMfOobNAg62q").
                when().
                get("/characters").prettyPeek();

                List<String> list=response.jsonPath().getList("_id");
                List<String> list2=response.jsonPath().getList("house");
        System.out.println(list2);
            int numberOfElements=list.size();
            for (int i=0;i<list.size();i++){
            String str=response.jsonPath().get("_id["+i+"]");
            boolean dumbledoresArmy=response.jsonPath().getBoolean("dumbledoresArmy["+i+"]");
            assertTrue(dumbledoresArmy==true||dumbledoresArmy==false);
            assertTrue(!str.isEmpty());
            }
            response.then().assertThat().
                    contentType("application/json; charset=utf-8").statusCode(200);
            assertTrue(list2.contains("Gryffindor"));
            assertTrue(list2.contains("Ravenclaw"));
            assertTrue(list2.contains("Slytherin"));
            assertTrue(list2.contains("Hufflepuff"));
    }
//    Verify all character information
//1. Send a get request to /characters. Request includes :
//• Header Accept with value application/json
//• Query param key with value {{apiKey}}
//2. Verify status code 200, content type application/json; charset=utf-8
//3. Select name of any random character
//4. Send a get request to /characters. Request includes :
//• Header Accept with value application/json
//• Query param key with value {{apiKey}}
//• Query param name with value from step 3
//5. Verify that response contains the same character information from step 3. Compare all fields.
    @Test
    public void test6(){
        Response response=given().accept("application/json").
                queryParam("key","$2a$10$v6/4oH1IH59SgkmugnPjqOc1wYDIc/y.aPOEhp.DaoMfOobNAg62q").
                queryParam("name","Bathsheda Babbling").
                when().
                get("/characters").prettyPeek();
        String name=response.jsonPath().getString("name");
        System.out.println("name = " + name);
        response.then().assertThat().statusCode(200).contentType("application/json; charset=utf-8").
                body("_id",is("[5a0fa54aae5bc100213c232f]")).
                body("name",equalTo("[Bathsheda Babbling]")).
                body("role",is("[Professor, Ancient Runes]")).
                body("school",is("[Hogwarts School of Witchcraft and Wizardry]")).
                body("ministryOfMagic",is("false")).
                body("orderOfThePhoenix",is("false")).log().all(true);
//        assertEquals("_id","[5a0fa54aae5bc100213c232f]");
//        assertEquals("name","[Bathsheda Babbling]");
//        assertEquals("role","[Professor, Ancient Runes]");
//        assertEquals("school","[Hogwarts School of Witchcraft and Wizardry]");
//        assertEquals("ministryOfMagic","false");
//        assertEquals("_id","[5a0fa54aae5bc100213c232f]");

    }
//    Verify name search
//1. Send a get request to /characters. Request includes :
//• Header Accept with value application/json
//• Query param key with value {{apiKey}}
//• Query param name with value Harry Potter
//2. Verify status code 200, content type application/json; charset=utf-8
//3. Verify name Harry Potter
//4. Send a get request to /characters. Request includes :
//• Header Accept with value application/json
//• Query param key with value {{apiKey}}
//• Query param name with value Marry Potter
//5. Verify status code 200, content type application/json; charset=utf-8
//6. Verify response body is empty

    @Test
    public void test7(){
        Response response=given().accept("application/json").
                queryParam("key","$2a$10$v6/4oH1IH59SgkmugnPjqOc1wYDIc/y.aPOEhp.DaoMfOobNAg62q").
                queryParam("name","Harry Potter").
                when().
                get("/characters").prettyPeek();
        String name=response.jsonPath().getString("name");
        System.out.println("name = " + name);
        response.then().assertThat().contentType("application/json; charset=utf-8").statusCode(200);

                assertEquals("[Harry Potter]",name.toString());
                Response response2=given().accept("application/json").
                        queryParam("key","$2a$10$v6/4oH1IH59SgkmugnPjqOc1wYDIc/y.aPOEhp.DaoMfOobNAg62q").
                        queryParam("name","Marry Potter").
                        when().
                        get("/characters").prettyPeek();
        String name2=response.jsonPath().getString("name");
        System.out.println("name = " + name);

        response2.then().assertThat().contentType("application/json; charset=utf-8").statusCode(200).
                body("",is(empty()));
    }
// Verify house members
//1. Send a get request to /houses. Request includes :
//• Header Accept with value application/json
//• Query param key with value {{apiKey}}
//2. Verify status code 200, content type application/json; charset=utf-8
//3. Capture the id of the Gryffindor house
//4. Capture the ids of the all members of the Gryffindor house
//5. Send a get request to /houses/:id. Request includes :
//• Header Accept with value application/json
//• Query param key with value {{apiKey}}
//• Path param id with value from step 3
//6. Verify that response contains the same member ids as the step 4
    @Test
    public void test8(){
        Response response=given().accept("application/json").
                queryParam("key","$2a$10$v6/4oH1IH59SgkmugnPjqOc1wYDIc/y.aPOEhp.DaoMfOobNAg62q").
                queryParam("name","Harry Potter").
                when().
                get("/houses").prettyPeek();
        String id=response.jsonPath().get("find{it.name=='Gryffindor'}._id");
        System.out.println("id = " + id);
        List<String>allId=response.jsonPath().getList("members");
        System.out.println(allId);
        response.then().assertThat().contentType("application/json; charset=utf-8").statusCode(200);
        Assertions.assertEquals("5a05e2b252f721a3cf2ea33f",id);
        Assertions.assertEquals("[[5a0fa648ae5bc100213c2332, 5a0fa67dae5bc100213c2333, 5a0fa7dcae5bc100213c2338, 5a107e1ae0686c0021283b19, 5a10944f3dc2080021cd8755, 5a10947c3dc2080021cd8756, 5a1096b33dc2080021cd8762, 5a1097653dc2080021cd8763, 5a1098fd3dc2080021cd876e, 5a109af13dc2080021cd877a, 5a109bfc3dc2080021cd877f, 5a109c3d3dc2080021cd8780, 5a109cb83dc2080021cd8784, 5a109cd33dc2080021cd8785, 5a109e143dc2080021cd878d, 5a109e1e3dc2080021cd878e, 5a109e253dc2080021cd878f, 5a109e543dc2080021cd8790, 5a109f053dc2080021cd8793, 5a1226520f5ae10021650d76, 5a1226d70f5ae10021650d77, 5a12292a0f5ae10021650d7e, 5a12298d0f5ae10021650d7f, 5a1229e10f5ae10021650d80, 5a122cbe0f5ae10021650d89, 5a1233ff0f5ae10021650d98, 5a1234cb0f5ae10021650d9b, 5a1237480f5ae10021650da3, 5a1237c00f5ae10021650da5, 5a1238070f5ae10021650da6, 5a1238350f5ae10021650da7, 5a12387a0f5ae10021650da8, 5a1238b20f5ae10021650da9, 5a1239130f5ae10021650daa, 5a12393d0f5ae10021650dab, 5a12395f0f5ae10021650dac, 5a1239c80f5ae10021650dad, 5a1239f10f5ae10021650dae, 5a123b450f5ae10021650db7, 5a123f130f5ae10021650dcc], [5a0fa8a6ae5bc100213c233b, 5a107ffee0686c0021283b21, 5a108023e0686c0021283b22, 5a108047e0686c0021283b23, 5a1097d53dc2080021cd8769, 5a1099cf3dc2080021cd8772, 5a109a553dc2080021cd8777, 5a109de83dc2080021cd878c, 5a109e943dc2080021cd8791, 5a109ebd3dc2080021cd8792, 5a1225a20f5ae10021650d73, 5a12260f0f5ae10021650d75, 5a122a880f5ae10021650d82, 5a122ac40f5ae10021650d83, 5a122aef0f5ae10021650d84, 5a12368a0f5ae10021650da0, 5a123e450f5ae10021650dc8], [5a0fa6bbae5bc100213c2334, 5a0fa6e8ae5bc100213c2335, 5a0fa74eae5bc100213c2336, 5a0fa772ae5bc100213c2337, 5a107e4ae0686c0021283b1a, 5a1093eb3dc2080021cd8753, 5a10942a3dc2080021cd8754, 5a1099353dc2080021cd876f, 5a109aa03dc2080021cd8779, 5a109d893dc2080021cd8789, 5a109d9f3dc2080021cd878a, 5a109dab3dc2080021cd878b, 5a109f8b3dc2080021cd8795, 5a109fa83dc2080021cd8796, 5a109fde3dc2080021cd8797, 5a1225640f5ae10021650d72, 5a1225e30f5ae10021650d74, 5a122c200f5ae10021650d88, 5a1232600f5ae10021650d93, 5a12327c0f5ae10021650d94, 5a1233bc0f5ae10021650d97, 5a1235060f5ae10021650d9c, 5a1236eb0f5ae10021650da1, 5a123a710f5ae10021650db1, 5a123af40f5ae10021650db5], [5a0fa11a4d153d00212c47cc, 5a0fa360ae5bc100213c232c, 5a0fa365ae5bc100213c232d, 5a0fa4daae5bc100213c232e, 5a0fa842ae5bc100213c2339, 5a0fa86dae5bc100213c233a, 5a1096253dc2080021cd875f, 5a1098bd3dc2080021cd876d, 5a109c993dc2080021cd8783, 5a1223720f5ae10021650d6f, 5a1223ed0f5ae10021650d70, 5a122f3d0f5ae10021650d8d, 5a1232b10f5ae10021650d95, 5a12333f0f5ae10021650d96, 5a1234500f5ae10021650d99, 5a1235790f5ae10021650d9d, 5a123cb40f5ae10021650dbc]]",allId.toString());

    }
//Verify house members again
//1. Send a get request to /houses/:id. Request includes :
//• Header Accept with value application/json
//• Query param key with value {{apiKey}}
//• Path param id with value 5a05e2b252f721a3cf2ea33f
//2. Capture the ids of all members
//3. Send a get request to /characters. Request includes :
//• Header Accept with value application/json
//• Query param key with value {{apiKey}}
//• Query param house with value Gryffindor
//4. Verify that response contains the same member ids from step 2
    @Test
    public void test9() {
        Response response = given().accept("application/json").
                queryParam("key", "$2a$10$v6/4oH1IH59SgkmugnPjqOc1wYDIc/y.aPOEhp.DaoMfOobNAg62q").
                pathParam("id", "5a05e2b252f721a3cf2ea33f").
                when().
                get("/houses/{id}");
//        response.prettyPrint();
        List<String> list = response.jsonPath().getList("members[0]._id");
        System.out.println(list);


        Response response2 = given().accept("application/json").
                queryParam("key", "$2a$10$v6/4oH1IH59SgkmugnPjqOc1wYDIc/y.aPOEhp.DaoMfOobNAg62q").

                when().
                get("/characters");

        List<String> list2 = response2.jsonPath().getList("_id");
        System.out.println(list2);

        int count = 0;
        for (int i = 0; i < list.size(); i++) {

                if (list2.contains(list.get(0))) {
                    count++;
                }

          }
        System.out.println(count);
        System.out.println(list.size());
        assertEquals(count,list.size());


    }

    /**
     * Verify house with most members
     * 1.Send a get request to /houses.
     * Request includes :
     * •Header Accept with value application/json
     * •Query param key with value {{apiKey}}
     * 2.Verify status code 200, content type application/json; charset=utf-8
     * 3.Verify that Gryffindor house has the most members
     */
    @Test
    public void test10(){

        Response response = given().accept("application/json").
                queryParam("key", "$2a$10$v6/4oH1IH59SgkmugnPjqOc1wYDIc/y.aPOEhp.DaoMfOobNAg62q").

                when().
                get("/houses");

        List<String> Gryffindor=response.jsonPath().getList("findAll{it.name=='Gryffindor'}.members[0]");
        System.out.println(Gryffindor);

        List<String> Ravenclaw=response.jsonPath().getList("findAll{it.name=='Ravenclaw'}.members[0]");
        System.out.println(Ravenclaw);

        List<String> Slytherin=response.jsonPath().getList("findAll{it.name=='Slytherin'}.members[0]");
        System.out.println(Slytherin);

        List<String> Hufflepuff=response.jsonPath().getList("findAll{it.name=='Hufflepuff'}.members[0]");
        System.out.println(Hufflepuff);

        System.out.println(Gryffindor.size()+" "+Slytherin.size()+" "+Ravenclaw.size()+" "+Hufflepuff.size());
        if (Gryffindor.size()>Slytherin.size()&&Gryffindor.size()>Ravenclaw.size()&&Gryffindor.size()>Hufflepuff.size()){
            System.out.println("Gryffindor house has the most members");
        }else {
            System.out.println("failed :( " );
        }

    }
    }
