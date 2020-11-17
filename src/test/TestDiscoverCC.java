package test;
import main.Handler.*;
import main.CreditCard.*;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Date;

public class TestDiscoverCC {

    @Test
    public void testDiscoverCC(){

        System.out.println("\nTesting Discover Card ... ");

        Date expirationDate = new Date();
        String nameOfCardHolder = "Richard";
        String cardNumber1 = "6010000000000000";
        String cardNumber2 = "6011000000123456";
        String cardNumber3 = "601100000000000012";
        String expectedCardType = "Discover Card";

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

        //Check that the first four digits of the Discover card can only be 6011
        assertFalse(creditCard1.getValid());

        //Check that card2 is a valid card
        assertTrue(creditCard2.getValid());

        //Check that card2 returns card type: "Discover Card"
        assertEquals(expectedCardType, creditCard2.getType());

        //Check that the length of Discover card can only be 16 digits
        assertFalse(creditCard3.getValid());

    }

}

