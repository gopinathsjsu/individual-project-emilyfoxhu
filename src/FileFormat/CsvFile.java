package FileFormat;

import CreditCard.*;
import Handler.*;

import java.io.*;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CsvFile extends FileFormat{

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

        System.out.println("Reading from csv file...");
        File csvFile = new File(inputPath + ".csv");

        String line;
        String csvSplitBy = ",";
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        int i = 1; //which line? the first line need no handler

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))){
            while ((line = br.readLine()) != null){
                if (i > 1){
                    String[] record = line.split(csvSplitBy);
                    String cardNumber = String.format("%.0f", Double.parseDouble(record[0]));
                    Date expirationDate = dateFormat.parse(record[1]);
                    String nameOfCardHolder = record[2];
                    //for each record, use chainOfResponsibility(start from MasterCCHandler) to determine card type
                    //if it's a valid card, handler will create a credit card object, then add to ArrayList
                    creditCards.add(masterCCHandler.checkCreditCard(cardNumber, expirationDate, nameOfCardHolder));
                }
                i++;
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return creditCards;
    }

    @Override
    public boolean writeToFile(ArrayList<CreditCard> creditCards, String outputPath) {

        System.out.println("Writing to csv file...");
        String COMMA_DELIMITER = ",";
        String NEW_LINE_SEPARATOR = "\n";
        File csvFile = new File(outputPath + ".csv");

        try (PrintWriter printWriter = new PrintWriter(csvFile)) {
            StringBuilder sb = new StringBuilder();
            //first line
            sb.append("CardNumber");
            sb.append(COMMA_DELIMITER);
            sb.append("Type");
            sb.append(",");
            sb.append("ERROR Message");
            sb.append(NEW_LINE_SEPARATOR);

//            for (CreditCard creditCard : creditCards) {
//                System.out.println(creditCard.getCardNumber() + " " + creditCard.getType() + " " + creditCard.getValid());
//            }

            //className objectName : arraylist
            for (CreditCard creditCard : creditCards) {
                sb.append(creditCard.getCardNumber());
                sb.append(COMMA_DELIMITER);
                sb.append(creditCard.getType());
                if (!creditCard.getValid()){
                    sb.append(COMMA_DELIMITER);
                    sb.append("ERROR:InvalidNumber");
                }
                sb.append(NEW_LINE_SEPARATOR);
            }
            printWriter.write(sb.toString());
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return true;
    }
}