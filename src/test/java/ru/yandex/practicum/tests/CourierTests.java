package ru.yandex.practicum.tests;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.model.Courier;
import ru.yandex.practicum.model.DuplicateCourier;
import ru.yandex.practicum.steps.CourierSteps;
import static org.hamcrest.CoreMatchers.is;

public class CourierTests extends BaseTest {
    private CourierSteps courierSteps = new CourierSteps();
    private Courier courier;
    private DuplicateCourier duplicateCourier;


    @Before
    public void setUp() {
        courier = new Courier();
        courier.withPassword(RandomStringUtils.randomAlphabetic(12))
                .withLogin(RandomStringUtils.randomAlphabetic(12));
        duplicateCourier = new DuplicateCourier();
        duplicateCourier.setPassword(courier.getPassword());
        duplicateCourier.setLogin(courier.getLogin());
        courierSteps
                .createCourier(courier);
    }

    @Test
    public void shouldCreateCourierTest() {
        courierSteps
                .createCourier(courier)
                .statusCode(201)
                .body("ok", is(true));
    }

    @Test
    public void shouldCreateCourierDuplicate() {

        courierSteps
                .createDuplicateCourier(duplicateCourier)
                .statusCode(409)
                .body("message", is("Этот логин уже используется"));
    }

    @Test
    public void createCourierWithoutRequiredFields() {
        courierSteps
                .createCourierWithoutRequiredFields()
                .statusCode(400)
                .body("message", is ("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void tearDown() {
        Integer id = courierSteps.login(courier)
                .extract().body().path("id");
        courier.withId(id);
        courierSteps.deleteCourier(courier);
    }
}

