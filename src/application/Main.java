package application;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;

public class Main {
    public static void main(String[] args) {

        MutablePicoContainer pico = new DefaultPicoContainer();
        pico.addComponent(MainController.class);
        pico.addComponent(DatabaseHelper.class);

        DatabaseHelper dbh = pico.getComponent(DatabaseHelper.class);
        MainController mc = pico.getComponent(MainController.class);

        try {
            mc.Menu(dbh);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}