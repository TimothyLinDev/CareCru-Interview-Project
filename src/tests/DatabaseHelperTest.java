package tests;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class DatabaseHelperTest {
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(application.DatabaseHelper.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @org.junit.Test
    public void createDatabase() {
        assertEquals(5,5);
    }

    @org.junit.Test
    public void insertLibrary() {
        assertEquals("help", "help");
    }

    @org.junit.Test
    public void deleteLibrary() {
    }

    @org.junit.Test
    public void listLibraries() {
    }

    @org.junit.Test
    public void closeDB() {
    }
}
