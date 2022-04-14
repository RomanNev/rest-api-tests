package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ApiTests {
    @Test
    void successRegister() {
        /*
        request: https://reqres.in/api/register
        data:
        {
            "email": "eve.holt@reqres.in",
            "password": "pistol"
         }
        response:
       {
            "id": 4,
            "token": "QpwL5tke4Pnpja7X4"
         }
         */

        String registerData = "{\"email\": \"eve.holt@reqres.in\", " +
                "\"password\": \"pistol\"}";

        given()
                .body(registerData)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }


    @Test
    void unsuccessfulRegister(){
         /*
        request: https://reqres.in/api/register
        data:
        {
             "email": "sydney@fife"
        }
        response:
        {
            "error": "Missing password"
        }
         */

        String registerData = "{\"email\": \"sydney@fife\"}";

        given()
                .body(registerData)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));

    }

    @Test
    void successCreate(){
          /*
        request: https://reqres.in/api/users
        data:
        {
            "name": "morpheus",
            "job": "leader"
        }
        response:
        {
             "name": "morpheus",
                "job": "leader",
        }
         */

        String createData = "{\"name\": \"morpheus\",\n" +
                                " \"job\": \"leader\"}";

        given()
                .body(createData)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));

    }

    @Test
    void successPut(){
          /*
        request: https://reqres.in/api/users/2
        data:
       {
             "name": "morpheus",
             "job": "zion resident"
        }
        response:
        {
             "name": "morpheus",
               "job": "zion resident",
        }
         */

        String updateData = "{\"name\": \"morpheus\",\n" +
                " \"job\": \"zion resident\"}";

        given()
                .body(updateData)
                .contentType(JSON)
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));

    }

    @Test
    void resourceNotFound() {

          /*
        request: https://reqres.in/api/unknown/23
        data:
       {
             "name": "morpheus",
             "job": "zion resident"
        }
        response:
        {}
         */

        given()
                .when()
                .get("https://reqres.in/api/unknown/23")
                .then()
                .statusCode(404);

    }
}
