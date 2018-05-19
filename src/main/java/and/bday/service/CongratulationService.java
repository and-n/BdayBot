package and.bday.service;

import and.bday.service.model.Human;
import javafx.util.Pair;

public interface CongratulationService {

    Pair<String, String> getCongratulation();

    String getWishes();

    void congratulate(Human human, final Pair<String, String> prepare, String wishes);

}
