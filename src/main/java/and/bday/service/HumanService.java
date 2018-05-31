package and.bday.service;

import and.bday.service.model.Human;
import org.joda.time.DateTime;

import java.util.List;

public interface HumanService {

    List<Human> whosBdayToday();

    List<Human> whosBdayAtDay(DateTime date);

    List<Human> whosBdayAtDay(DateTime from, DateTime to);

    void addHuman(Human human);

    void addHuman(String name, String surname, String bday);

    void removeHuman(Human human);

    void removeHumanByFullName(String name);

}
