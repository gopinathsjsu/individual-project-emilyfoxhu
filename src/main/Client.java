package main;

import main.CreditCard.*;
import main.FileFormat.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        ArrayList<CreditCard> creditCards;
        FileFormat newFile;

        System.out.println("Please enter your input file name: (Sample.csv/Sample.xml/Sample.json)");
        Scanner userInput = new Scanner(System.in);

        if (userInput.hasNextLine()) {
            String typeOfFile = userInput.nextLine();
            //user does not know exactly which type of file object is created.
            newFile = FileFactory.makeFile(typeOfFile);

            if (newFile != null){
                creditCards = newFile.readFromFile("C:/Users/alism/Desktop/202SW Sys Engr/IndividualProject/CreditCard/src/" + typeOfFile);
                System.out.println("Successfully read!");
                if (newFile.writeToFile(creditCards, "C:/Users/alism/Desktop/202SW Sys Engr/IndividualProject/CreditCard/src/")){
                    System.out.println("Successfully write!");
                }
            }
            else {
                System.out.println("File Not Found! Please Enter your filename again, or check your file path.");
            }
        }

    }

}
