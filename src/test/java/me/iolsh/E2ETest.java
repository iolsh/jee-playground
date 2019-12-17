package me.iolsh;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

@RunWith(Arquillian.class)
public class E2ETest {

    private static final String WEBAPP_SRC = "src/main/webapp";

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
            .addPackages(true, Filters.exclude(".*Test.*"), ApplicationServices.class.getPackage())
            .addAsWebInfResource(new File(WEBAPP_SRC, "WEB-INF/glassfish-resources.xml"))
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
            .addAsResource("META-INF/persistence.xml");
    }

    @Test
    public void test() {

    }
}
