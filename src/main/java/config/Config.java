package config;

public class Config {
    // URL ���������� ������.�������
    public static String BASE_URL = "https://qa-scooter.praktikum-services.ru/";
    // ����� ��� �������� �������
    public static String HANDLE_CREATING_COURIER = "/api/v1/courier";
    // ����� ��� �������� ID �������
    public static String HANDLE_LOGIN_COURIER = "/api/v1/courier/login";
    // ����� ��� �������� �������
    public static String HANDLE_DELETE_COURIER= "/api/v1/courier/{courierId}";
    // ����� ��� ������ � �������: ��������, ��������� ������ �������
    public static String HANDLE_ORDER_CREATE= "/api/v1/orders";
    // ����� ��� ������ ������
    public static String HANDLE_ORDER_CANCELLATION = "/api/v1/orders/cancel";

}