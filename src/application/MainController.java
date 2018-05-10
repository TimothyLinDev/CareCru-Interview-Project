package application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;



//JSON Arrays containing libraries to test Program

public class MainController {
	
	public MainController() throws IOException{
		//Can add Login Logic here
        MutablePicoContainer pico = new DefaultPicoContainer();
        pico.addComponent(DatabaseHelper.class);

        DatabaseHelper dbh = pico.getComponent(DatabaseHelper.class);

        Menu(dbh) ;

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

                /*System.out.println("file: JSON file")
                System.out.println("input: JSON input")
                scanner = new Scanner(System.in);
                input = scanner.nextLine();

                if("file".equals(input)){

                } else if("input".equals(input)){

                } else{
                    System.out.println("unknown command")
                }*/

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
