package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class DataHandler {
    static void writeData(String filename, ArrayList<Unit> list) {
        FileOutputStream file;
        ObjectOutputStream out;
        try {
            file = new FileOutputStream(filename);
            out = new ObjectOutputStream(file);
            out.writeInt(list.size());
            for (Unit ob: list) {
                out.writeObject(ob);
            }
            out.close();
            file.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    static ArrayList<Unit> readData(String filename) {
        ArrayList<Unit> list = new ArrayList<>();
        Object ob;
        FileInputStream file;
        ObjectInputStream in;
        try {
            file = new FileInputStream(filename);
            in = new ObjectInputStream(file);
            int sizeOfFile = in.readInt();
            for(int i=0; i < sizeOfFile; i++) {
                ob = in.readObject();
                list.add((Unit) ob);
            }
        } catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch(IOException e) {
            System.out.println(e.getMessage());
        } catch(ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    static ArrayList<Unit> filterArrayList(ArrayList<Unit> list, String filter) {
        ArrayList<Unit> filteredList = new ArrayList<>();
        try {
            Pattern pattern = Pattern.compile(filter.toUpperCase());
            for (Unit unit : list) {
                Matcher match = pattern.matcher(unit.getUnitName());
                if (match.find()) {
                    filteredList.add(unit);
                }
            }
        } catch (PatternSyntaxException e) {

        }

        return filteredList;
    }
}
