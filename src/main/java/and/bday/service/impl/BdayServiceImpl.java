package and.bday.service.impl;

import and.bday.service.BdayService;
import and.bday.service.model.Human;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BdayServiceImpl implements BdayService {

    private final Logger log = Logger.getLogger(BdayServiceImpl.class);
    private String humanListFile = System.getProperty("HumanListFile", "humans.json");


    public List<Human> whosBdayToday() {
        return whosBdayAtDay(DateTime.now());
    }

    public List<Human> whosBdayAtDay(DateTime date) {
        try {
            final List<Human> humansBdayList = loadHumanListFromFile();
            List<Human> listForToday = humansBdayList
                    .stream()
                    .filter(human -> human.getBdayDate().dayOfYear().equals(DateTime.now().dayOfYear()))
                    .collect(Collectors.toList());
            log.info("There are " + humanListFile.length() + " birthdays at " + date);
            return listForToday;

        } catch (final FileNotFoundException e) {
            log.error("No FILE! ", e);
        }

        return new ArrayList<>();
    }

    private List<Human> loadHumanListFromFile() throws FileNotFoundException {
        final Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Human>>() {
        }.getType();

        List<Human> humans = gson.fromJson(new FileReader(humanListFile), listType);
        humans = humans != null ? humans : new ArrayList<>();

        return humans;
    }

}
