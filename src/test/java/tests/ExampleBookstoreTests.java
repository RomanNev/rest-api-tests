package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static listeners.CustomAllureListener.withCustomTemplates;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;

public class ExampleBookstoreTests {
    @Test
    void getBooksTest() {
        get("https://demoqa.com/BookStore/v1/Books")
                .then()
                .body("books", hasSize(greaterThan(0))); // проверка на внутри тела ненулевое кол-во элементов
    }

    @Test
    void getBooksWithAllLogsTest() {
        given()
                .log().all()
                .when()
                .get("https://demoqa.com/BookStore/v1/Books")
                .then()
                .log().all()
                .body("books", hasSize(greaterThan(0)));
    }

    @Test
    void getBooksWithSomeLogsTest() {
        given()
                .log().uri()
                .log().body()
                .when()
                .get("https://demoqa.com/BookStore/v1/Books")
                .then()
                .log().status()
                .log().body()
                .body("books", hasSize(greaterThan(0)));
    }

    @Test
    void generateTokenTest() {
        String data = "{ \"userName\": \"alex\", " +
                "\"password\": \"asdsad#frew_DFS2\" }";

        given()
                .contentType(JSON)
                .body(data)
                .log().uri()
                .log().body()
                .when()
                .post("https://demoqa.com/Account/v1/GenerateToken")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("status", is("Success"))
                .body("result", is("User authorized successfully."))
                .body("token.size()", (greaterThan(10)));// значение токена больше 10
    }

    @Test
    void generateTokenWithAllureListenerTest() {
        String data = "{ \"userName\": \"alex\", " +
                "\"password\": \"asdsad#frew_DFS2\" }";

//        RestAssured.filters(new AllureRestAssured()); move to @BeforeAll

        given()
                .filter(new AllureRestAssured()) // для вывода лога прохождения тестов в отчет алюра
                .contentType(JSON)
                .body(data)
                .log().uri()
                .log().body()
                .when()
                .post("https://demoqa.com/Account/v1/GenerateToken")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("status", is("Success"))
                .body("result", is("User authorized successfully."))
                .body("token.size()", (greaterThan(10)));
    }


    @Test
    void generateTokenWithCustomAllureListenerTest() {
        String data = "{ \"userName\": \"alex\", " +
                "\"password\": \"asdsad#frew_DFS2\" }";

//        RestAssured.filters(new AllureRestAssured()); move to @BeforeAll

        given()
                .filter(withCustomTemplates()) // кастомный allure listener для красивыого вывода
                .contentType(JSON)
                .body(data)
                .log().uri()
                .log().body()
                .when()
                .post("https://demoqa.com/Account/v1/GenerateToken")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("status", is("Success"))
                .body("result", is("User authorized successfully."))
                .body("token.size()", (greaterThan(10)));
    }


    @Test
    void getTokenTest() {
        String data = "{ \"userName\": \"alex\", " +
                "\"password\": \"asdsad#frew_DFS2\" }";

        String token =
                given()
                        .contentType(JSON)
                        .body(data)
                        .log().uri()
                        .log().body()
                        .when()
                        .post("https://demoqa.com/Account/v1/GenerateToken")
                        .then()
                        .log().status()
                        .log().body()
                        .statusCode(200)
                        .body("status", is("Success"))
                        .body("result", is("User authorized successfully."))
                        .extract().path("token");

        System.out.println("Token: " + token);
    }


    @Test
    void generateTokenJsonSchemeCheckTest() {
        String data = "{ \"userName\": \"alex\", " +
                "\"password\": \"asdsad#frew_DFS2\" }";

        given()
                .filter(withCustomTemplates())
                .contentType(JSON)
                .body(data)
                .log().uri()
                .log().body()
                .when()
                .post("https://demoqa.com/Account/v1/GenerateToken")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/GenerateToken_response_scheme.json"))
                .body("status", is("Success"))
                .body("result", is("User authorized successfully."))
                .body("token.size()", (greaterThan(10)));
    }


}
