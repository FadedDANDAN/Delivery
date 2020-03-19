package clinicalpath.hy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class HyApplication {

    public static void main(String[] args) {
        SpringApplication.run(HyApplication.class, args);
    }

}
