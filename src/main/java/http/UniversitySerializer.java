package http;

import java.io.*;
import java.util.List;

public class UniversitySerializer {
    private static final String DEFAULT_FILENAME = "university.txt";

    public void SerializeUniversity(List<University> university) {
        try (FileOutputStream fileOut = new FileOutputStream(DEFAULT_FILENAME);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

            out.writeObject(university);
            System.out.println("University serialized in file: " + DEFAULT_FILENAME);

            ProcessBuilder pd = new ProcessBuilder("notepad.exe", DEFAULT_FILENAME);
            pd.start();
        } catch (IOException e) {
            System.err.println("Error of serialization " + e);
        }
    }
    @SuppressWarnings("unchecked")
    public List<University> deserializationUniversity() {
        try (FileInputStream fileIn = new FileInputStream(DEFAULT_FILENAME);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {

            List<University> university = (List<University>) in.readObject();
            System.out.println("University deserializated from file: " + DEFAULT_FILENAME);
            return university;

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("error of deserialization " + e);
            return null;
        }
    }
}
