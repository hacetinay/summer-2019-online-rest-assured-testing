package com.automation.tests.day2;
import static io.restassured.RestAssured.given;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class akiyew {
    private String baseURI = "https://api.lyrics.ovh/v1/";

    @Test
    public void test1(){
        String artistName = "sami yusuf";
        String titleName = "sari gelin";

        Response response = given().
                pathParam("artist", artistName).
                pathParam("title", titleName).
                get(baseURI+"{artist}/{title}");

        String lyrics = response.asString().replace("\\n", "\n").replace("\\r","\r");
        System.out.println(lyrics);

    }
}
