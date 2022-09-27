import config.Config;
import io.restassured.response.Response;
import order.ListOfOrder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import order.ListOfOrderAPI;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

public class TestsListOfOrders {
    ListOfOrderAPI listOfOrderAPI = new ListOfOrderAPI();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Before
    public void setUp() {
        RestAssured.baseURI = Config.BASE_URL;
    }

    @Test
    @DisplayName("Тест Получение списка заказов")
    @Description("В теле ответа содержится Список заказов")
    public void testGettingListOfOrders() {
        Response response = listOfOrderAPI.getListOfOrders();
        response.then().assertThat().body("orders", notNullValue())
                .and()
                .statusCode(200);
        ListOfOrder listOfOrder = response.then().extract().body().as(ListOfOrder.class);
        System.out.println("Список заказов:");
        String json = gson.toJson(listOfOrder);
        System.out.println(json);
    }
}