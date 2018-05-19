package and.bday.service;

import and.bday.service.model.Human;
import org.joda.time.DateTime;

import java.util.List;

public interface BdayService {

    List<Human> whosBdayToday();

    List<Human> whosBdayAtDay(DateTime date);

}
