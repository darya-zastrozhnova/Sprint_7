package ru.yandex.practicum.steps;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.practicum.model.Courier;
import ru.yandex.practicum.model.DuplicateCourier;
import ru.yandex.practicum.model.Order;
import static io.restassured.RestAssured.given;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourierSteps {

    public static final String COURIER = "/api/v1/courier";
    public static final String LOGIN = "/api/v1/courier/login";
    public static final String ORDER = "/api/v1/orders";
    public static final String ORDERLIST = "/api/v1/orders";

    @Step
    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .body(courier)
                .when()
                .post(COURIER)
                .then();
    }


@Step
    public ValidatableResponse login(Courier courier) {
        return given()
                .body(courier)
                .when()
                .post(LOGIN)
                .then();
    }

    @Step
    public ValidatableResponse createDuplicateCourier(DuplicateCourier courier) {
        return given()
                .body(courier)
                .when()
                .post(COURIER)
                .then();
    }

    @Step
    public ValidatableResponse createCourierWithoutRequiredFields() {
        return given()
                .when()
                .post(COURIER)
                .then();
    }

    @Step
    public ValidatableResponse wrongCredentials(DuplicateCourier duplicateCourier) {
        return given()
                .body(duplicateCourier)
                .when()
                .post(LOGIN)
                .then();
    }

    @Step
    public ValidatableResponse nonExistingCourier(DuplicateCourier duplicateCourier) {
        return given()
                .body(duplicateCourier)
                .when()
                .post(LOGIN)
                .then();
    }

        @Step
        public ValidatableResponse deleteCourier (Courier courier){
            return given()
                    .pathParams("id", courier.getId())
                    .when()
                    .delete("/api/v1/courier/{id}")
                    .then();
        }

        @Step
        public ValidatableResponse OrderCreation(Order order){
        return given()
                .body(order)
                .when()
                .post(ORDER)
                .then();
        }
        @Step
        public ValidatableResponse getOrdersList(){
        return given()
                .when()
                .get(ORDERLIST)
                .then();
    }


}
