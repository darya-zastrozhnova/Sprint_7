package ru.yandex.practicum.tests;

import org.junit.Test;
import ru.yandex.practicum.steps.CourierSteps;

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderListTests extends BaseTest {
    private CourierSteps courierSteps = new CourierSteps();


    @Test
    public void testOrdersList() {
        courierSteps
                .getOrdersList()
                .statusCode(200)
                .body("orders", notNullValue());
    }
}
