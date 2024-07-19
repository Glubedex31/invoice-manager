package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = { "commons", "server" })
public class Main {
    /**
     * The main method that starts the server.
     *
     * @param args any arguments passed at runtime
     */
    public static void main(String[] args) {
        //System.out.println("Hello, World!");
        SpringApplication.run(Main.class, args);
    }
}