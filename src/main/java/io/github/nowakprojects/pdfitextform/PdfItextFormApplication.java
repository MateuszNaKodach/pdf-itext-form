package io.github.nowakprojects.pdfitextform;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PdfItextFormApplication implements CommandLineRunner {

    public static void main(String[] args) {
        final ConfigurableApplicationContext springApplication = SpringApplication.run(PdfItextFormApplication.class, args);
        System.out.println("Application is closing...");
        springApplication.close();
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Application run!");
    }
}
