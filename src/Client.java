import CreditCard.*;
import FileFormat.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        ArrayList<CreditCard> creditCards;
        //FileFactory fileFactory = new FileFactory();
        FileFormat newFile;

        System.out.println("Which type of Sample file? csv/xml/json (Enter c/x/j)");
        Scanner userInput = new Scanner(System.in);

        if (userInput.hasNextLine()) {
            String typeOfFile = userInput.nextLine();
            //user does not know exactly which type of file object is created.
            newFile = FileFactory.makeFile(typeOfFile);

            if (newFile != null){
                creditCards = newFile.readFromFile("C:/Users/alism/Desktop/202SW Sys Engr/IndividualProject/CreditCard/src/Sample");
                System.out.println("Successfully read!");
                if (newFile.writeToFile(creditCards, "C:/Users/alism/Desktop/202SW Sys Engr/IndividualProject/CreditCard/src/Sample_output")){
                    System.out.println("Successfully write!");
                }
            }
            else {
                System.out.println("Please enter c/x/j again!");
            }
        }

    }

}
