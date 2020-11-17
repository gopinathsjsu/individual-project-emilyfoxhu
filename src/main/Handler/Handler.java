package main.Handler;

import main.CreditCard.*;
import java.util.Date;

public interface Handler {

    public CreditCard checkCreditCard(String cardNumber, Date expirationDate, String nameOfCardHolder);

    public void setSuccessor(Handler next);

}
