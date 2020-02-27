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
public class BadSSLTest {
    @BeforeAll
    public static void setUp(){
        baseURI="https://untrusted-root.badssl.com/";
    }
    @Test
    @DisplayName("Access web site with bad SSL")
    public void test1(){
        //unable to find valid certification path to requested target
        //no valid SSL - no handshake
        //if web site cannot provide valid certificate
        //secured connection is not possible
        //client will reject to exchange information by default
        Response response = get().prettyPeek();
        System.out.println(response.statusCode());
    }
    @Test
    @DisplayName("Access web site with bad SSL (solution)")
    public void test2(){
        //.relaxedHTTPSValidation() - ignores SSL issues
        // * Use relaxed HTTP validation with SSLContext protocol SSL.
        // This means that you'll trust all hosts regardless if the SSL certificate is invalid.
        Response response = given().relaxedHTTPSValidation().get().prettyPeek();
        System.out.println(response.statusCode());
        assertEquals(200, response.getStatusCode());
    }
}
