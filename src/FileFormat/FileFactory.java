package FileFormat;

//This is a factory method, the only job is to create file object
public class FileFactory {

    public static FileFormat makeFile(String typeOfFile){
        //FileFormat newFile = null;
        switch (typeOfFile) {
            case "c":
                return new CsvFile();
            case "x":
                return new XmlFile();
            case "j":
                return new JasonFile();
            default:
                return null;
        }
    }
}
