package and.bday.service.impl;

import and.bday.service.HumanService;
import and.bday.service.model.Human;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HumanServiceImpl implements HumanService {

    private final Logger log = Logger.getLogger(HumanServiceImpl.class);
    private String humanListFile = System.getProperty("humanListFile", "humans.json");
    private final Gson gson = new Gson();

    @Override
    public List<Human> whosBdayToday() {
        return whosBdayAtDay(DateTime.now());
    }

    @Override
    public List<Human> whosBdayAtDay(DateTime date) {
        final List<Human> humansBdayList = loadHumanListFromFile();
        final List<Human> listForToday = humansBdayList
                .stream()
                .filter(human -> DateTime.parse(human.getBdayDate()).dayOfYear().equals(date.dayOfYear()))
                .collect(Collectors.toList());
        log.info("There are " + listForToday.size() + " birthdays at " + date);
        return listForToday;
    }

    @Override
    public void addHuman(Human human) {
        if (human != null) {
            List<Human> allHumans = loadHumanListFromFile();
            allHumans.remove(human);
            allHumans.add(human);
            saveHumansToFile(allHumans);
        }
    }

    @Override
    public void addHuman(String name, String surname, String bday) {
        if (name == null || name.trim().isEmpty()) {
            log.error("Adding empty name");
            return;
        }
        if (surname == null || surname.trim().isEmpty()) {
            log.error("Adding empty surname");
            return;
        }
        if (bday == null || bday.trim().isEmpty()) {
            log.error("Adding empty bday");
            return;
        }
        DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("dd.MM.yyyy");

        try {
            DateTime bdayDate = DateTime.parse(bday, dateTimeFormat);

            addHuman(new Human(name, surname, bdayDate));
        } catch (final Exception e) {
            log.error("fail created human", e);
        }
    }

    @Override
    public List<Human> whosBdayAtDay(final DateTime from, final DateTime to) {
        final List<Human> humans = new ArrayList<>();
        for (DateTime day = from; day.isBefore(to) || day.isEqual(to); day = day.plusDays(1)) {
            humans.addAll(whosBdayAtDay(day));
        }
        return humans;
    }

    @Override
    public void removeHuman(final Human human) {
        log.info("remove human " + human);
        saveHumansToFile(
                loadHumanListFromFile().stream()
                        .filter(human1 -> !human1.equals(human))
                        .collect(Collectors.toList())
        );
    }

    public void removeHumanByFullName(final String fullName) {
        log.info("remove human by full name " + fullName);
        saveHumansToFile(
                loadHumanListFromFile().stream()
                        .filter(human1 -> !(human1.getName() + human1.getSurname()).toLowerCase().equals(fullName.toLowerCase()))
                        .collect(Collectors.toList())
        );
    }

    private List<Human> loadHumanListFromFile() {

        Type listType = new TypeToken<ArrayList<Human>>() {
        }.getType();
        final List<Human> humans = new ArrayList<>();
        synchronized (gson) {
            final Path filePath = Paths.get(humanListFile);
            if (Files.exists(filePath)) {
                try {
                    humans.addAll(gson.fromJson(new FileReader(humanListFile), listType));
                } catch (Exception e) {
                    log.error("File " + humanListFile + " loading problem", e);
                }
            } else {
                saveHumansToFile(humans);
            }
        }

        return humans;
    }

    private void saveHumansToFile(List<Human> humans) {
        synchronized (gson) {
            final Path filePath = Paths.get(humanListFile);
            try (final FileWriter fw = new FileWriter(filePath.toFile())) {
                // add save to temp file
                final String gs = gson.toJson(humans);
                fw.write(gs);
                fw.flush();
                fw.close();
                log.info("file " + humanListFile + " created");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
