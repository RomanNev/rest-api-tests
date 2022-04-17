package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;


public class DemoshopApiTest {

    static String URL = "http://demowebshop.tricentis.com/";

    @BeforeAll
    static void beforeAll() {
        Configuration.baseUrl = URL;
        Configuration.browserSize = "1920x1080";

    }

    @Test
    void LoginOldUserTest() {
        String authorizationCookie =
                given()
                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                        .formParam("Email", "golubtestuser@test.com")
                        .formParam("Password","golubtestuser1" )
                        .when()
                        .post(URL + "login")
                        .then()
                        .statusCode(302)
                        .extract()
                        .cookie("NOPCOMMERCE.AUTH");

        open("/wishlist");
        getWebDriver().manage().addCookie(new Cookie("NOPCOMMERCE.AUTH", authorizationCookie));
        open("");

    }
}
