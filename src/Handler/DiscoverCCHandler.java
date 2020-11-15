package Handler;

import CreditCard.*;
import java.util.Date;

public class DiscoverCCHandler implements Handler{

    private Handler successor = null;

    @Override
    //If it is Discover card, create new Discover card object and return, otherwise set isValid to false
    public CreditCard checkCreditCard(String cardNumber, Date expirationDate, String nameOfCardHolder) {
        System.out.println("DiscoverCCHandler got the request...");
        int length = cardNumber.length();
        if (length==16 && cardNumber.startsWith("6011")) {
            System.out.println("It is an Discover Credit Card");
            return new DiscoverCC(cardNumber, expirationDate, nameOfCardHolder, true, "Discover Card");
        }
        else {
            if (successor != null)
                return successor.checkCreditCard(cardNumber, expirationDate, nameOfCardHolder);
        }
        System.out.println("ERROR: It is an Invalid Card!");
        return new DiscoverCC(cardNumber, expirationDate, nameOfCardHolder, false, "invalid");
    }

    @Override
    public void setSuccessor(Handler next) {
        this.successor = next;
    }
}
