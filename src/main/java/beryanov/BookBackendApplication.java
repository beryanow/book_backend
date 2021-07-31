package beryanov;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookBackendApplication {
    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(BookBackendApplication.class, args);
    }
}
