package integration;

/** Helper class for tests to build URLs */
public final class RestTestHelper {

    private static final String API_CALCULATE = "/calculate?";

    public static String buildUrl(String number1, String number2, String operation) {
        return API_CALCULATE + "number1=" + number1 + "&" + "number2=" + number2 + "&" + "operation=" + operation;
    }
}
