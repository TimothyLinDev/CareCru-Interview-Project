package application;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Map<Integer, Library> database = new HashMap<>();

        Library library = new Library();

        Book book = new Book();

        book.setId(1);
        book.setAuthor("Stendhal");
        book.setTitle("The red and the black");

        library.setName("Great library");
        library.addBook(book);

        database.put(1, library);

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
                System.out.println("Bye!");
                break;
            } else if ("list".equals(input)) {
                System.out.println(objectMapper.writeValueAsString(database));
            } else if ("create".equals(input)) {

                System.out.println("Library (JSON):");

                scanner = new Scanner(System.in);
                input = scanner.nextLine();

                System.out.println(input);

                Library inputLibrary = objectMapper.readValue(input, Library.class);

                System.out.println(inputLibrary.getName());
                database.put(database.size() + 1, inputLibrary);
            } else if ("delete".equals(input)) {
                System.out.println("ID to delete:");

                scanner = new Scanner(System.in);
                input = scanner.nextLine();

                database.remove(Integer.parseInt(input));
            } else {
                System.out.println("unknown command");
            }
        }
    }
}
