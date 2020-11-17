package main.CreditCard;

import java.util.Date;

abstract public class CreditCard {

    private String cardNumber;
    private Date expirationDate;
    private String nameOfCardHolder;
    private boolean isValid;
    private String type;

    public CreditCard (String cardNumber, Date expirationDate, String nameOfCardHolder, boolean isValid, String type){
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.nameOfCardHolder = nameOfCardHolder;
        this.isValid = isValid;
        this.type = type;
    }

    public String getCardNumber(){return this.cardNumber;}
    public Date getExpirationDate(){return this.expirationDate;}
    public String getNameOfCardHolder(){return this.nameOfCardHolder;}
    public String getType(){return this.type;}
    public boolean getValid(){return this.isValid;}

}
