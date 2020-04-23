package clinicalpath.delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class HyApplication {

    public static void main(String[] args) {
        SpringApplication.run(HyApplication.class, args);
    }

}
