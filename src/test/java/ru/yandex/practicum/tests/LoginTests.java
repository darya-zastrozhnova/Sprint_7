package ru.yandex.practicum.tests;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.model.Courier;
import ru.yandex.practicum.model.DuplicateCourier;
import ru.yandex.practicum.steps.CourierSteps;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginTests extends BaseTest {
    private CourierSteps courierSteps = new CourierSteps();
    private Courier courier;
    private DuplicateCourier wrongCredentials;
    private DuplicateCourier nonExistingCourier;

    @Before
    public void setUp() {
        courier = new Courier();
        courier.withPassword(RandomStringUtils.randomAlphabetic(12))
                .withLogin(RandomStringUtils.randomAlphabetic(12));
        wrongCredentials = new DuplicateCourier();
        wrongCredentials.setLogin(courier.getLogin());
        wrongCredentials.setPassword("wrongPassword");
        nonExistingCourier = new DuplicateCourier();
        nonExistingCourier.setLogin("non_existing_login");
        nonExistingCourier.setPassword(RandomStringUtils.randomAlphabetic(12));
        courierSteps
                .createCourier(courier);
    }

    @Test
    public void shouldLoginCourierTest() {

        courierSteps
                .login(courier)
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    public void shouldNotLoginWithWrongPassword() {

         courierSteps
                .wrongCredentials(wrongCredentials)
                .statusCode(404)
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    public void shouldNotLoginWithNonExistingLogin() {

        courierSteps
                .nonExistingCourier(nonExistingCourier)
                .statusCode(404)
                .body("message", is("Учетная запись не найдена"));
    }

    @After
    public void tearDown() {
        Integer id = courierSteps.login(courier)
                .extract().body().path("id");
        courier.withId(id);
        courierSteps.deleteCourier(courier);
    }
}
