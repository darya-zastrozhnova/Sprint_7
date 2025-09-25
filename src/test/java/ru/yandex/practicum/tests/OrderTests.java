package ru.yandex.practicum.tests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.model.Order;
import ru.yandex.practicum.steps.CourierSteps;
import java.util.Arrays;
import java.util.Collection;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderTests extends BaseTest {
    private CourierSteps courierSteps = new CourierSteps();
    private Order order;
    private String[] color;


    @Parameterized.Parameters
    public static Collection<Object[]> getColorOptions() {
        return Arrays.asList(new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{}}
        });
    }
    public OrderTests(String[] colors) {
        this.color = colors;
}

    @Before
    public void setUp() {
        order = new Order();
        order.setColor(color);
    }

    @Test
    public void testOrderCreation() {
        courierSteps
                .OrderCreation(order)
                .statusCode(201)
                .body("track", notNullValue());
    }
}
