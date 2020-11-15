package FileFormat;

import CreditCard.CreditCard;

import java.util.ArrayList;

public class XmlFile extends FileFormat{
    @Override
    public ArrayList<CreditCard> readFromFile(String inputPath) {

        return null;
    }

    @Override
    public boolean writeToFile(ArrayList<CreditCard> creditCards, String outputPath) {

        return false;
    }
}
