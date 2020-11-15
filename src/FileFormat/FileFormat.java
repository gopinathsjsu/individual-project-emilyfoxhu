package FileFormat;

import CreditCard.*;
import java.util.ArrayList;

abstract public class FileFormat {

    public abstract ArrayList<CreditCard> readFromFile(String inputPath);

    public abstract boolean writeToFile(ArrayList<CreditCard> creditCards, String outputPath);

}
