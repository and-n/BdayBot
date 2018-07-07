package and.bday;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan
@EnableAutoConfiguration
@PropertySource("file:bdaybot.properties")
public class BdayBot {

    private static final Logger logger = Logger.getLogger(BdayBot.class);

    public static void main(String[] args) {

        System.out.println("Bot added!");
        SpringApplication.run(BdayBot.class, args);

        logger.info("Start Bday BOOOT");
    }
}
