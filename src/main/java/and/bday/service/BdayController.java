package and.bday.service;

import and.bday.service.model.Human;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@RestController
public class BdayController {

    private static final Logger log = Logger.getLogger(BdayController.class);

    @Autowired
    private HumanService humanService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Human getHuman(@RequestParam(value = "days", required = false, defaultValue = "0") int days) {
        log.info("Request bdays from " + new Date().toString() + " plus " + days);
        humanService.whosBdayToday();

        return new Human("Name", "sname", DateTime.now());
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public Human addHuman(@RequestParam String name, @RequestParam String surname, @RequestParam String bday) {

        return new Human("Name", "sname", DateTime.now());
    }

}
