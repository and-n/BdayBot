package and.bday.service.impl;

import and.bday.service.CongratulationService;
import and.bday.service.model.Human;
import javafx.util.Pair;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CongratulationServiceImpl implements CongratulationService {

    private static final Logger log = Logger.getLogger(CongratulationServiceImpl.class);

    @Override
    public Pair<String, String> getCongratulation() {
        try {

            List<String> preparationList = Arrays.asList("Сегодня поздравляем ",
                    " с прекрасным Днём Рождения!)");  // TODO load from file!

            if (preparationList.size() > 0) {
                final String wish = preparationList.get(randomGenerator.nextInt(preparationList.size()));
                final String[] res = wish.split("@");
                if (res.length != 2) {
                    return new Pair<>("", "");
                }
                return new Pair<>(res[0], res[1]);
            }
        } catch (final Exception e) {
            log.error(" congratulation problem! ", e);
        }
        return new Pair<>("Сегодня мы поздравляем ", " с Днём рождения!");
    }

    private Random randomGenerator;

    @Override
    public String getWishes() {
        List<String> wishesList = Arrays.asList("Счастья",
                "Здоровья");  // TODO load from file!

        return "";
    }

    @Override
    public void congratulate(final Human human, final Pair<String, String> prepare, final String wishes) {

    }
}
