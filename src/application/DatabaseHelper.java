package application;

import java.sql.*;
import java.io.File;
import java.util.Map;
import java.util.List;

public class DatabaseHelper {

    private Connection c;
    private int count = 0;

    public DatabaseHelper(){
        try{
            Class.forName("org.sqlite.JDBC");
            this.c = DriverManager.getConnection("jdbc:sqlite::memory:");

            File file = new File("libraries.db");
            //Check if a database exists

            if(file.exists()){
                //if it does load it into memory
                c.createStatement().executeUpdate("restore from libraries.db");
                //Load up last Library ID
                PreparedStatement ps = c.prepareStatement("SELECT MAX(LIB_ID) FROM LIBRARIES;");
                ResultSet rs = ps.executeQuery();
                rs.next();
                this.count = rs.getInt(1);
                ps.close();
                rs.close();
            } else{
                createDatabase(c);
            }
        } catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public void createDatabase(Connection c){
        try {
            /*Creates the three tables that make up the database
             *LIBRARIES: Holds information about Libraries
             *LIB_BOOKS_BRIDGE: Bridge table connecting Libraries and Books tables
             *BOOKS: Holds information about Books
             */
            Statement stmt = null;
            stmt = c.createStatement();

            String sql = "CREATE TABLE LIBRARIES " +
                    "(NAME TEXT PRIMARY KEY	NOT NULL," +
                    " LIB_ID 			INT 	NOT NULL)";
            stmt. executeUpdate(sql);

            sql = "CREATE TABLE LIB_BOOKS_BRIDGE " +
                    "(LIB_ID INT NOT NULL," +
                    " BOOK_ID INT NOT NULL," +
                    " UNIQUE (LIB_ID, BOOK_ID))";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE BOOKS " +
                    "(BOOK_ID INT PRIMARY KEY	NOT NULL," +
                    " TITLE				TEXT	NOT NULL, " +
                    " AUTHOR			TEXT 	NOT NULL)";
            stmt.executeUpdate(sql);
            stmt.close();

        }catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);

        }
    }

    public void insertLibrary(Library inputLib){
        //Three separate prepared statements to insert into the three tables
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;

        int libID = ++count;

        List<Book> listOfBooks = inputLib.getBooks();

        try {
            ps = c.prepareStatement("INSERT OR IGNORE INTO LIBRARIES(NAME, LIB_ID) VALUES(?, ?)");
            ps.setString(1, inputLib.getName());
            ps.setInt(2,libID);
            ps.executeUpdate();


            for(int i = 0; i <listOfBooks.size(); i++) {
                Book book = new Book();
                book = listOfBooks.get(i);

                ps2 = c.prepareStatement("INSERT OR IGNORE INTO BOOKS (BOOK_ID, TITLE, AUTHOR) VALUES(?, ?, ?)");
                ps2.setInt(1, book.getId());
                ps2.setString(2, book.getTitle());
                ps2.setString(3, book.getAuthor());
                ps2.executeUpdate();

                ps3 = c.prepareStatement("INSERT INTO LIB_BOOKS_BRIDGE (LIB_ID, BOOK_ID) VALUES(?, ?)");
                ps3.setInt(1, libID);
                ps3.setInt(2, book.getId());
                ps3.executeUpdate();

                ps.close();
                ps2.close();
                ps3.close();

            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void deleteLibrary(int libID){
        try {

            Statement stmt = c.createStatement();
            String sql = "DELETE from LIBRARIES where LIB_ID = " + Integer.toString(libID) + ";";
            stmt.executeUpdate(sql);

            sql = "DELETE from LIB_BOOKS_BRIDGE where LIB_ID = " + Integer.toString(libID) + ";";
            stmt.executeUpdate(sql);

            sql = "DELETE from BOOKS where BOOK_ID" +
                    " IN (SELECT BOOKS.BOOK_ID FROM BOOKS" +
                    " LEFT JOIN LIB_BOOKS_BRIDGE" +
                    " ON BOOKS.BOOK_ID = LIB_BOOKS_BRIDGE.BOOK_ID" +
                    " WHERE LIB_BOOKS_BRIDGE.BOOK_ID IS NULL);";

            stmt.executeUpdate(sql);
            stmt.close();

        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void listLibraries(Map<Integer, Library> database){
        try {

            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM LIBRARIES WHERE LIBRARIES.LIB_ID = 1;");

            Statement stmt2 = c.createStatement();
            ResultSet rs2 = stmt2.executeQuery("SELECT BOOKS.BOOK_ID, TITLE, AUTHOR from LIB_BOOKS_BRIDGE" +
                    " JOIN BOOKS ON LIB_BOOKS_BRIDGE.BOOK_ID = BOOKS.BOOK_ID" +
                    "  where LIB_BOOKS_BRIDGE.LIB_ID = 1;");


            while (rs.next()){
                Library lib = new Library();
                String name = rs.getString("name");
                lib.setName(rs.getString("name"));

                while (rs2.next()){
                    Book bk = new Book();
                    bk.setId(rs2.getInt("book_id"));
                    bk.setTitle(rs2.getString("title"));
                    bk.setAuthor(rs2.getString("author"));

                    lib.addBook(bk);
                }
                database.put(rs.getInt("lib_id"),lib);
            }

            stmt.close();
            stmt2.close();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void closeDB(){
        try{
            c.createStatement().executeUpdate("backup to libraries.db");
            c.close();
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

    }
}