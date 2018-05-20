package and.bday.service.impl;

import and.bday.service.HumanService;
import and.bday.service.model.Human;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HumanServiceImpl implements HumanService {

    private final Logger log = Logger.getLogger(HumanServiceImpl.class);
    private String humanListFile = System.getProperty("HumanListFile", "humans.json");


    @Override
    public List<Human> whosBdayToday() {
        return whosBdayAtDay(DateTime.now());
    }

    @Override
    public List<Human> whosBdayAtDay(DateTime date) {
        try {
            final List<Human> humansBdayList = loadHumanListFromFile();
            List<Human> listForToday = humansBdayList
                    .stream()
                    .filter(human -> DateTime.parse(human.getBdayDate()).dayOfYear().equals(DateTime.now().dayOfYear()))
                    .collect(Collectors.toList());
            log.info("There are " + humanListFile.length() + " birthdays at " + date);
            return listForToday;

        } catch (final FileNotFoundException e) {
            log.error("No FILE! ", e);
        }

        return new ArrayList<>();
    }

    @Override
    public void addHuman(Human human) {

    }

    @Override
    public void removeHuman(Human human) {

    }

    private List<Human> loadHumanListFromFile() throws FileNotFoundException {
        final Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Human>>() {
        }.getType();

        List<Human> humans = gson.fromJson(new FileReader(humanListFile), listType);
        humans = humans != null ? humans : new ArrayList<>();

        return humans;
    }

    private void saveHumansToFile(List<Human> humans) {

    }

}
