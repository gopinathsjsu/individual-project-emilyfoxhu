package main.FileFormat;

//This is a factory method, the only job is to create file object
public class FileFactory {

    public static FileFormat makeFile(String typeOfFile){

        if (typeOfFile.contains(".csv"))
            return new CsvFile();
        else if (typeOfFile.contains(".xml"))
            return new XmlFile();
        else if (typeOfFile.contains(".json"))
            return new JsonFile();
        else
            return null;

    }
}
