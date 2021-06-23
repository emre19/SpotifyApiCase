package Services;

import Specs.RequestSpec;
import Specs.ResponseSpec;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class User extends RequestSpec {

    public User() {
        super("/v1/me");
    }

    public String getUserId(int statusCode){

        Response response =
        given()
                .spec(super.getRequestSpecification())
                .get()
        .then()
                .spec(ResponseSpec.checkStatusCode(statusCode))
                .extract()
                .response();

        String userId = response.jsonPath().getString("id");
        System.out.println("Oluşturulan user id: "+ userId);
        return userId;
    }
}
