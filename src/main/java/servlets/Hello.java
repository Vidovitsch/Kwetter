package servlets;


import javax.annotation.ManagedBean;
import javax.inject.Named;

@Named(value = "hellobean")
@ManagedBean
public class Hello {
    final String world = "Hello World!";

    public Hello() {
        System.out.println("HelloWorld started!");
    }
    public String getworld() {
        return world;
    }
}
