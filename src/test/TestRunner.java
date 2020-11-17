package test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {

    public static void main(String[] args) {

        Result result1 = JUnitCore.runClasses(TestMasterCC.class);
        Result result2 = JUnitCore.runClasses(TestVisaCC.class);
        Result result3 = JUnitCore.runClasses(TestAmExCC.class);
        Result result4 = JUnitCore.runClasses(TestDiscoverCC.class);

        System.out.println("\nMaster Card Test Result:");
        for (Failure failure : result1.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result1.wasSuccessful());

        System.out.println("\nVisa Card Test Result:");
        for (Failure failure : result2.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result2.wasSuccessful());

        System.out.println("\nAmericanExpress Card Test Result:");
        for (Failure failure : result3.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result3.wasSuccessful());

        System.out.println("\nDiscover Card Test Result:");
        for (Failure failure : result4.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result4.wasSuccessful());

    }
}