package main.FileFormat;

import main.CreditCard.*;
import main.Handler.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.codehaus.jackson.map.ObjectMapper;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;
import java.util.ArrayList;

public class JsonFile extends FileFormat{
    @Override
    public ArrayList<CreditCard> readFromFile(String inputPath) {

        //create concrete handlers
        MasterCCHandler masterCCHandler = new MasterCCHandler();
        VisaCCHandler visaCCHandler = new VisaCCHandler();
        AmExCCHandler amExCCHandler = new AmExCCHandler();
        DiscoverCCHandler discoverCCHandler = new DiscoverCCHandler();

        //set successor
        masterCCHandler.setSuccessor(visaCCHandler);
        visaCCHandler.setSuccessor(amExCCHandler);
        amExCCHandler.setSuccessor(discoverCCHandler);

        ArrayList<CreditCard> creditCards = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        System.out.println("Reading from json file...");
        File jsonFile = new File(inputPath);

        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader(jsonFile));
            // A JSON array.
            JSONArray cardList = (JSONArray) obj;

            for (int i=0; i<cardList.size(); i++){
                JSONObject creditCard = (JSONObject) cardList.get(i);
                String cardNumber = String.valueOf(creditCard.get("CardNumber"));
                Date expirationDate = dateFormat.parse(String.valueOf(creditCard.get("ExpirationDate")));
                String nameOfCardHolder = (String) creditCard.get("NameOfCardholder");
                //System.out.println(i + ": " + cardNumber + expirationDate + nameOfCardHolder);

                //for each record, use chainOfResponsibility(start from MasterCCHandler) to determine card type
                //if it's a valid card, handler will create a credit card object, then add to ArrayList
                creditCards.add(masterCCHandler.checkCreditCard(cardNumber, expirationDate, nameOfCardHolder));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

//        System.out.println("Test read output: holderName, ExpireDate, CardNumber, Type, isValid:");
//        for (CreditCard creditCard : creditCards) {
//            System.out.println(creditCard.getNameOfCardHolder() + " " + creditCard.getExpirationDate() + " " + creditCard.getCardNumber() + " " + creditCard.getType() + " " + creditCard.getValid());
//        }

        return creditCards;
    }

    @Override
    public boolean writeToFile(ArrayList<CreditCard> creditCards, String outputPath) {

        System.out.println("Writing to json file...");

        try {
            FileWriter jsonFile = new FileWriter(outputPath + "output.json");
            JSONObject cardList = new JSONObject();
            ObjectMapper mapper = new ObjectMapper();

            //className objectName : arraylist
            for (CreditCard creditCard : creditCards) {
                cardList.put("CardNumber", creditCard.getCardNumber());
                cardList.put("Type", creditCard.getType());
                if (creditCard.getValid())
                    cardList.put("ERRORMessage", "");
                else
                    cardList.put("ERRORMessage", "ERROR:InvalidNumber");

                try {
                    //jsonFile.write(cardList.toJSONString());
                    jsonFile.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(cardList));
                    jsonFile.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

}
