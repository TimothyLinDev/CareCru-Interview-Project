package application;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        MutablePicoContainer pico = new DefaultPicoContainer();
        pico.addComponent(MainController.class);
        pico.addComponent(DatabaseHelper.class);

        DatabaseHelper dbh = pico.getComponent(DatabaseHelper.class);
        MainController mc = pico.getComponent(MainController.class);
        mc.Menu(dbh);
    }
}