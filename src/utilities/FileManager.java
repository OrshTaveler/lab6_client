package utilities;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Класс для работы с файлами
 * @author Ubica228
 */
public class FileManager {
    public static Scanner getFileScanner(String path) throws FileNotFoundException {
         File file = new File(path);
         return new Scanner(file);
    }
    public static void save(String xmlFormat) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("xmlCollection.xml"));
        writer.write(xmlFormat);
        writer.close();
    }
    public static List<String> readCollection(){
        File file = new File("xmlCollection.xml");
        List<String> xmlStrings = new ArrayList<>();
        try {
            Scanner xmlScanner = new Scanner(file);
            String xmlString = xmlScanner.nextLine();
            while (!xmlString.equals("</root>")){
                xmlString = xmlScanner.nextLine();
                if (xmlString.equals("</root>")) break;
                xmlStrings.add(xmlString);
            }
        }
        catch (FileNotFoundException | NoSuchElementException e){
            return xmlStrings;
        }

        return xmlStrings;
    }
}
