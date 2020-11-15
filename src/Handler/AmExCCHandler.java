package Handler;

import CreditCard.*;
import java.util.Date;

public class AmExCCHandler implements Handler{

    private Handler successor = null;

    @Override
    //If it is AmericanExpress card, create new AmEx card object and return, otherwise forward to Discover Handler
    public CreditCard checkCreditCard(String cardNumber, Date expirationDate, String nameOfCardHolder) {
        System.out.println("AmExCCHandler got the request...");
        int firstDigit = Character.getNumericValue(cardNumber.charAt(0));
        int secondDigit = Character.getNumericValue(cardNumber.charAt(1));
        int length = cardNumber.length();
        if (length==15 && firstDigit==3 && (secondDigit==4 || secondDigit==7)) {
            System.out.println("It is an AmericanExpress Credit Card");
            return new AmExCC(cardNumber, expirationDate, nameOfCardHolder, true, "AmericanExpress Card");
        }
        else {
            if (successor != null)
                return successor.checkCreditCard(cardNumber, expirationDate, nameOfCardHolder);
        }
        return null;
    }

    @Override
    public void setSuccessor(Handler next) {
        this.successor = next;
    }
}
