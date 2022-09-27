package order;

import config.Config;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderAPI {

    public Response create(Order order) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(Config.BASE_URL)
                .body(order)
                .when()
                .post(Config.HANDLE_ORDER_CREATE);
    }

    public void clean(Response response) {
        // получаем track заказа дл€ последующего его удалени€ из базы
        OrderTrack orderTrack = response.body().as(OrderTrack.class);
        // отмен€ем заказ по track
        Response responseCancellation = given()
                .header("Content-type", "application/json")
                .baseUri(Config.BASE_URL)
                .body(orderTrack)
                .put(Config.HANDLE_ORDER_CANCELLATION);
        int statusCancellation = responseCancellation.then().extract().statusCode();
        if (statusCancellation != 200) {
            System.out.println("ѕримечание: созданный заказ " + orderTrack.getTrack() + " не отменилс€");
        }
    }
}