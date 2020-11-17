package test;
import main.Handler.*;
import main.CreditCard.*;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Date;

public class TestAmExCC {

    @Test
    public void testAmExCC() {

        System.out.println("\nTesting AmericanExpress Card ... ");

        Date expirationDate = new Date();
        String nameOfCardHolder = "Eve";
        String cardNumber1 = "341000000000000";
        String cardNumber2 = "741000000123400";
        String cardNumber3 = "371000001234500";
        String cardNumber4 = "301000000000000";
        String cardNumber5 = "34100000000000012";
        String expectedCardType = "AmericanExpress Card";

        //create concrete handlers
        MasterCCHandler masterCCHandler = new MasterCCHandler();
        VisaCCHandler visaCCHandler = new VisaCCHandler();
        AmExCCHandler amExCCHandler = new AmExCCHandler();
        DiscoverCCHandler discoverCCHandler = new DiscoverCCHandler();
        //set successor
        masterCCHandler.setSuccessor(visaCCHandler);
        visaCCHandler.setSuccessor(amExCCHandler);
        amExCCHandler.setSuccessor(discoverCCHandler);
        //create sample cards
        CreditCard creditCard1 = masterCCHandler.checkCreditCard(cardNumber1, expirationDate, nameOfCardHolder);
        CreditCard creditCard2 = masterCCHandler.checkCreditCard(cardNumber2, expirationDate, nameOfCardHolder);
        CreditCard creditCard3 = masterCCHandler.checkCreditCard(cardNumber3, expirationDate, nameOfCardHolder);
        CreditCard creditCard4 = masterCCHandler.checkCreditCard(cardNumber4, expirationDate, nameOfCardHolder);
        CreditCard creditCard5 = masterCCHandler.checkCreditCard(cardNumber5, expirationDate, nameOfCardHolder);

        //Check that card1 is a valid card
        assertTrue(creditCard1.getValid());

        //Check that card1 returns card type: "AmericanExpress Card"
        assertEquals(expectedCardType, creditCard1.getType());

        //Check that the first digit of AmericanExpress card can only be 3
        assertFalse(creditCard2.getValid());

        //Check that the second digit of AmericanExpress card can only be 3 or 7
        assertTrue(creditCard3.getValid());
        assertFalse(creditCard4.getValid());

        //Check that the length of AmericanExpress card can only be 15 digits
        assertFalse(creditCard5.getValid());

    }

}
