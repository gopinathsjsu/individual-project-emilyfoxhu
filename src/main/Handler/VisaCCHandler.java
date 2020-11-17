package main.Handler;

import main.CreditCard.*;
import java.util.Date;

public class VisaCCHandler implements Handler{

    private Handler successor = null;

    @Override
    //If it is visa card, create new visa card object and return, otherwise forward to AmEx Handler
    public CreditCard checkCreditCard(String cardNumber, Date expirationDate, String nameOfCardHolder) {
        System.out.println("VisaCCHandler got the request...");
        int firstDigit = Character.getNumericValue(cardNumber.charAt(0));
        int length = cardNumber.length();
        if ((length==16 || length==13) && firstDigit==4) {
            System.out.println("It is a Visa Credit Card");
            return new VisaCC(cardNumber, expirationDate, nameOfCardHolder, true, "Visa Card");
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
