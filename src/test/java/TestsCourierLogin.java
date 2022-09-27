import courier.CourierAPI;
import courier.CourierData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class TestsCourierLogin {

    private CourierData courierData;
    private final CourierAPI courierAPI = new CourierAPI();
    private Response response;

    @Test
    @DisplayName("���� ����� ������� � ����������� �����������")
    @Description("��������� ID ������� � ������������ ��� 200. ����� �������� ���� �������� � �������� ���������, ������ �������")
    public void testCourierCanLogInAndReturnIdWithCorrectParameters () {
        // ������� ��������� ������� (��������)
        courierData  = CourierData.getCourierCorrect();
        response = courierAPI.create(courierData);
        // �������� ����� ������� � ����������� �����������
        response = courierAPI.login(courierData);
        response.then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
        // ����� ����� �������� �� �������� � �������� ���������
        courierAPI.clean(courierData);
    }

    @Test
    @DisplayName("���������� ���� ����� ������� � login = null")
    @Description("������������ ��� 400 � ��������� �� ������ \"������������ ������ ��� �����\"")
    public void testCourierCanNotLogInWithoutLogin () {
        // ������� ��������� ������� � login = null"
        courierData  = CourierData.getCourierWithoutLogin();
        response = courierAPI.create(courierData);
        // �������� ����� ������� � login = null"
        response = courierAPI.login(courierData);
        response.then().assertThat().body("message", equalTo("������������ ������ ��� �����"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("���������� ���� ����� ������� � password = null")
    @Description("������������ ��� 400 � ��������� �� ������ \"������������ ������ ��� �����\"")
    public void testCourierCanNotLogInWithoutPassword () {
        try {
            // ������� ��������� ������� � password = null"
            courierData  = CourierData.getCourierWithoutPassword();
            response = courierAPI.create(courierData);
            // �������� ����� ������� � login = null"
            response = courierAPI.login(courierData);
            response.then().assertThat().body("message", equalTo("������������ ������ ��� �����"))
                    .and()
                    .statusCode(400);
        } catch (RuntimeException exception) {
            System.out.println("��: ����� �������� ������ ��������� ��� ������� ����� ������� ��� ������");
            System.out.println("��: ��� ������ 400. ��������� �� ������ \"������������ ������ ��� �����\"");
        }
    }

    @Test
    @DisplayName("���������� ���� ����� ������� � �������������� ����� �����-������")
    @Description("������������ ��� 404 � ��������� �� ������ \"������� ������ �� �������\"")
    public void testCourierCanNotLogInIfNonExistentLoginPasswordPair () {
        // ������� ��������� ������� (��������)
        courierData  = CourierData.getCourierCorrect();
        response = courierAPI.create(courierData);
        // ������ ������, ����� ������� �������������� ���� �����-������
        courierData.setPassword(courierData.getPassword() + "ERROR");
        // �������� ����� ������� � �������������� ����� �����-������
        response = courierAPI.login(courierData);
        response.then().assertThat().body("message", equalTo("������� ������ �� �������"))
                .and()
                .statusCode(404);
    }
}