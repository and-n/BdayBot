package and.bday.service.impl;

import and.bday.service.FileService;
import and.bday.service.HumanService;
import and.bday.service.model.Human;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HumanServiceImpl implements HumanService {

    private final Logger log = Logger.getLogger(HumanServiceImpl.class);
    private final String humanListFile = System.getProperty("humanListFile", "humans.json");
    private FileService<Human> humanFileService;

    @Autowired
    public void setHumanFileService(FileService<Human> humanFileService) {
        this.humanFileService = humanFileService;
    }

    @Override
    public List<Human> whosBdayToday() {
        return whosBdayAtDay(DateTime.now());
    }

    @Override
    public List<Human> whosBdayAtDay(DateTime date) {
        final List<Human> humansBdayList = humanFileService.loadListFromFile(humanListFile);
        final List<Human> listForToday = humansBdayList
                .stream()
                .filter(human -> human.getBdayDate().dayOfYear().equals(date.dayOfYear()))
                .collect(Collectors.toList());
        log.debug("There are " + listForToday.size() + " birthdays at " + date);
        return listForToday;
    }

    @Override
    public void addHuman(Human human) {
        if (human != null) {
            List<Human> allHumans = humanFileService.loadListFromFile(humanListFile);
            allHumans.remove(human);
            allHumans.add(human);
            humanFileService.saveListToFile(allHumans, humanListFile);
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
        humanFileService.saveListToFile(
                humanFileService.loadListFromFile(humanListFile).stream()
                        .filter(human1 -> !human1.equals(human))
                        .collect(Collectors.toList())
                , humanListFile);
    }

    public void removeHumanByFullName(final String fullName) {
        log.info("remove human by full name " + fullName);
        humanFileService.saveListToFile(
                humanFileService.loadListFromFile(humanListFile).stream()
                        .filter(human1 -> !(human1.getName() + " " + human1.getSurname()).toLowerCase().equals(fullName.toLowerCase()))
                        .collect(Collectors.toList())
                , humanListFile);
    }


}
