package and.bday.service;

import and.bday.service.model.Human;
import org.joda.time.DateTime;

import java.util.List;

public interface HumanService {

    List<Human> whosBdayToday();

    List<Human> whosBdayAtDay(DateTime date);

    void addHuman(Human human);

    void removeHuman(Human human);

}
