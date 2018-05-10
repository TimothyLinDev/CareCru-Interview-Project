package application;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainController {

    public MainController() {
        //Can add Login Logic here

    }

    public void Menu(DatabaseHelper dbh) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

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
                //Creates holder for database rows returned by DBHelper
                Map<Integer, Library> database = new HashMap<>();
                dbh.listLibraries(database);
                System.out.println(objectMapper.writeValueAsString(database));
            } else if ("create".equals(input)) {

                System.out.println("file: JSON file");
                System.out.println("input: JSON input");
                scanner = new Scanner(System.in);
                input = scanner.nextLine();

                if("file".equals(input)){
                    System.out.println("FileName (JSON):");
                    scanner = new Scanner(System.in);
                    input = scanner.nextLine();

                    BufferedReader br = new BufferedReader(new FileReader(new File(input)));
                    String lib;
                    while ((lib = br.readLine()) != null){
                        System.out.println(lib);
                        Library inputLibrary = objectMapper.readValue(lib, Library.class);
                        dbh.insertLibrary(inputLibrary);
                    }

                } else if("input".equals(input)){
                    System.out.println("Library (JSON):");
                    scanner = new Scanner(System.in);
                    input = scanner.nextLine();

                    Library inputLibrary = objectMapper.readValue(input, Library.class);

                    System.out.println(inputLibrary.getName() + " Inserted");
                    dbh.insertLibrary(inputLibrary);

                } else{
                    System.out.println("unknown command");
                }

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