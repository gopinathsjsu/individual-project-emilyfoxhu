package main.CreditCard;

import java.util.Date;

public class MasterCC extends CreditCard{
    public MasterCC(String cardNumber, Date expirationDate, String nameOfCardHolder, boolean isValid, String type) {
        super(cardNumber, expirationDate, nameOfCardHolder, isValid, type);
    }
}
