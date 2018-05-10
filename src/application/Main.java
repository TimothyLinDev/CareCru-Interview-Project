package application;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;

public class Main {
    public static void main(String[] args) {

        MutablePicoContainer pico = new DefaultPicoContainer();
        pico.addComponent(MainController.class);

        MainController mc = pico.getComponent(MainController.class);

    }
}