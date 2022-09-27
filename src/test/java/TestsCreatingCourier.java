import courier.CourierAPI;
import courier.CourierData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class TestsCreatingCourier {
    private CourierData courierData;
    private final CourierAPI courierAPI = new CourierAPI();
    private Response response;

    @Test
    @DisplayName("���� �������� ������� � ����������� �����������")
    @Description("����� � ������ ������� ���������� � ������� Couriers � ��. ������������ ��� 200. ����� �������� ���� �������� � �������� ���������, ������ �������")
    public void testCanCreateCourierWithCorrectParameters() {
        courierData  = CourierData.getCourierCorrect();
        response = courierAPI.create(courierData);
        response.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);
        // ����� ����� �������� �� �������� � �������� ���������
        courierAPI.clean(courierData);
    }

    @Test
    @DisplayName("���� ������ ������� ���� ���������� ��������")
    @Description("��� ��������� �������� ������� � ���� �� ����������� ������������ ��� 409 � ��������� \"���� ����� ��� ������������. ���������� ������.\". ����� �������� ���� �������� � �������� ���������, ������ �������")
    public void testCanNotCreateTwoIdenticalCouriers() {
        courierData  = CourierData.getCourierCorrect();
        response = courierAPI.create(courierData);
        response.then().statusCode(201);
        response = courierAPI.create(courierData);
        response.then().assertThat().body("message", equalTo("���� ����� ��� ������������. ���������� ������."))
                .and()
                .statusCode(409);
        // ����� ����� �������� �� �������� � �������� ���������
        courierAPI.clean(courierData);
    }

    @Test
    @DisplayName("���������� ���� �������� ������� ��� ������")
    @Description("������������ ��� 400 � ��������� �� ������ \"������������ ������ ��� �������� ������� ������\"")
    public void testCanNotCreateCourierWithoutLogin() {
        courierData  = CourierData.getCourierWithoutLogin();
        response = courierAPI.create(courierData);
        response.then().assertThat().body("message", equalTo("������������ ������ ��� �������� ������� ������"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("���������� ���� �������� ������� ��� ������")
    @Description("������������ ��� 400 � ��������� �� ������ \"������������ ������ ��� �������� ������� ������\"")
    public void testCanNotCreateCourierWithoutPassword() {
        courierData  = CourierData.getCourierWithoutPassword();
        response = courierAPI.create(courierData);
        response.then().assertThat().body("message", equalTo("������������ ������ ��� �������� ������� ������"))
                .and()
                .statusCode(400);
    }
}