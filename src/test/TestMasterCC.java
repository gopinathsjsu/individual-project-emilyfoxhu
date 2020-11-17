package test;
import main.Handler.*;
import main.CreditCard.*;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Date;

public class TestMasterCC {

    @Test
    public void testMasterCC(){

        System.out.println("\nTesting Master Card ... ");

        Date expirationDate = new Date();
        String nameOfCardHolder = "Alice";
        String cardNumber1 = "5410000000000000";
        String cardNumber2 = "6410000123400000";
        String cardNumber3 = "5010001234000000";
        String cardNumber4 = "54100123400000001";
        String expectedCardType = "Master Card";

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

        //Check that card1 is a valid card
        assertTrue(creditCard1.getValid());

        //Check that card1 returns card type: "Master Card"
        assertEquals(expectedCardType, creditCard1.getType());

        //Check that the first digit of master card can only be 5
        assertFalse(creditCard2.getValid());

        //Check that the second digit of master card can only range in 1 to 5 inclusive
        assertFalse(creditCard3.getValid());

        //Check that the length of master card is 16 digits
        assertFalse(creditCard4.getValid());

    }

}
