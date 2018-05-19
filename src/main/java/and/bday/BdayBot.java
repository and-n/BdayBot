package and.bday;

import and.bday.service.model.Human;
import com.google.gson.Gson;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BdayBot {

    private static final Logger logger = Logger.getLogger(BdayBot.class);

    public static void main(String[] args) {

        System.out.println("Bot added!");


        logger.info("Start Bday BOOOT");
        final String secretKey = System.getProperty("slackApiKey");

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
        }

    }
}
