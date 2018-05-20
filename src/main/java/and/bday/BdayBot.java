package and.bday;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
public class BdayBot {

    private static final Logger logger = Logger.getLogger(BdayBot.class);

    public static void main(String[] args) {

        System.out.println("Bot added!");
        SpringApplication.run(BdayBot.class, args);

        logger.info("Start Bday BOOOT");
       /* final String secretKey = System.getProperty("slackApiKey");

        SlackSession slackSession = SlackSessionFactory.createWebSocketSlackSession(secretKey);

        File file = new File("test.json");

        Gson gson = new Gson();
        List<Human> humans = new ArrayList<>();
        humans.add(new Human("name1", "sname", new DateTime()));
        humans.add(new Human("name2", "sname", new DateTime()));
        humans.add(new Human("name3", "sname", new DateTime()));
        try {
            FileWriter fw = new FileWriter("f.json");
            String gs = gson.toJson(humans);
            fw.write(gs);
            fw.close();
            System.out.println(gson.toJson(humans));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }
}
