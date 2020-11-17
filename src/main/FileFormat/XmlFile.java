package main.FileFormat;

import main.CreditCard.*;
import main.Handler.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class XmlFile extends FileFormat{

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

        System.out.println("Reading from xml file...");
        File xmlFile = new File(inputPath);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        try {
            //make an instance of DocumentBuilderFactory
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xmlFile);
            doc.getDocumentElement();

            NodeList nodeList = doc.getElementsByTagName("row");

            for (int i = 0; i < nodeList.getLength(); i++) {

                Node nNode = nodeList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String cardNumber = String.format("%.0f", Double.parseDouble(eElement.getElementsByTagName("CardNumber").item(0).getTextContent()));
                    Date expirationDate = dateFormat.parse(eElement.getElementsByTagName("ExpirationDate").item(0).getTextContent());
                    String nameOfCardHolder = eElement.getElementsByTagName("NameOfCardholder").item(0).getTextContent();

                    //for each record, use chainOfResponsibility(start from MasterCCHandler) to determine card type
                    //if it's a valid card, handler will create a credit card object, then add to ArrayList
                    creditCards.add(masterCCHandler.checkCreditCard(cardNumber, expirationDate, nameOfCardHolder));
                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }

//        System.out.println("Test read output: holderName, ExpireDate, CardNumber, Type, isValid:");
//        for (CreditCard creditCard : creditCards) {
//            System.out.println(creditCard.getNameOfCardHolder() + " " + creditCard.getExpirationDate() + " " + creditCard.getCardNumber() + " " + creditCard.getType() + " " + creditCard.getValid());
//        }

        return creditCards;
    }

    @Override
    public boolean writeToFile(ArrayList<CreditCard> creditCards, String outputPath) {

        System.out.println("Writing to xml file...");
        File xmlFile = new File(outputPath + "output.xml");

        try {
            //make an instance of DocumentBuilderFactory
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db= dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            Element rootElement = doc.createElementNS("", "root");
            doc.appendChild(rootElement);

            //className objectName : arraylist
            for (CreditCard creditCard : creditCards) {
                Element rowElement = doc.createElement("row");
                Element nodeCardNumber = doc.createElement("CardNumber");
                nodeCardNumber.appendChild(doc.createTextNode(creditCard.getCardNumber()));
                rowElement.appendChild(nodeCardNumber);
                Element nodeType = doc.createElement("Type");
                nodeType.appendChild(doc.createTextNode(creditCard.getType()));
                rowElement.appendChild(nodeType);
                Element errorMessage = doc.createElement("ERRORMessage");

                if (creditCard.getValid()) {
                    errorMessage.appendChild(doc.createTextNode(""));
                } else {
                    errorMessage.appendChild(doc.createTextNode("ERROR:InvalidNumber"));
                }
                rowElement.appendChild(errorMessage);
                rootElement.appendChild(rowElement);
            }
            //create indent in xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource domSource = new DOMSource(doc);
            StreamResult outputFile = new StreamResult(xmlFile);
            transformer.transform(domSource, outputFile);

        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}