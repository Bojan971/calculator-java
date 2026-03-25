public class CalculatorTest {

    public static void main(String[] args) {

        testCalculateExpression();

    }

    static void testCalculateExpression() {
        String expected = "14.0";
        String actual = Calculator.Run("2+3*4");

        if (expected.equals(actual)) {
            System.out.println("TEST PASSED ");
        } else {
            System.out.println("TEST FAILED ");
            System.out.println("Expected: " + expected);
            System.out.println("Actual: " + actual);
        }
    }
}
