package application;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


//JSON Arrays containing libraries to test Program

public class Main {

    public static void main(String[] args) throws IOException {

        //Creates holder for database rows returned by DBHelper
        Map<Integer, Library> database = new HashMap<>();

        ObjectMapper objectMapper = new ObjectMapper();

        DatabaseHelper dbh = new DatabaseHelper();

        while (true) {

            System.out.println("Menu:");
            System.out.println("list: displays all libraries");
            System.out.println("create: creates a library");
            System.out.println("delete: deletes  a library");
            System.out.println("quit: closes the app");
            System.out.print("Command: ");

            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            if ("quit".equals(input)) {
                dbh.closeDB();
                System.out.println("Bye!");
                break;
            } else if ("list".equals(input)) {
                dbh.listLibraries(database);
                System.out.println(objectMapper.writeValueAsString(database));
            } else if ("create".equals(input)) {

                System.out.println("Library (JSON):");

                scanner = new Scanner(System.in);
                input = scanner.nextLine();

                System.out.println(input);

                Library inputLibrary = objectMapper.readValue(input, Library.class);

                System.out.println(inputLibrary.getName() + " Inserted");
                dbh.insertLibrary(inputLibrary);
 
            } else if ("delete".equals(input)) {
                System.out.println("ID to delete:");

                scanner = new Scanner(System.in);
                input = scanner.nextLine();
                int key = Integer.parseInt(input);
                dbh.deleteLibrary(key);
                System.out.println("Library Deleted");

            } else {
                System.out.println("unknown command");
            }
        }
    }
}
