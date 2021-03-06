package and.bday.controller;

import and.bday.service.HumanService;
import and.bday.service.SlackIntegrationService;
import and.bday.service.model.CongratulationMessage;
import and.bday.service.model.Human;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
public class BdayController {

    private static final Logger log = Logger.getLogger(BdayController.class);


    private HumanService humanService;
    private SlackIntegrationService slackIntegrationService;

    @Autowired
    public void setHumanService(HumanService humanService) {
        this.humanService = humanService;
    }

    @Autowired
    public void setSlackIntegrationService(SlackIntegrationService slackIntegrationService) {
        this.slackIntegrationService = slackIntegrationService;
    }

    @RequestMapping(value = "/human", method = RequestMethod.GET)
    public List getHuman(@RequestParam(value = "days", required = false, defaultValue = "0") int days,
                         @RequestParam(value = "all", required = false, defaultValue = "false") boolean all) {
        log.info("Request bdays from " + new Date().toString() + " plus " + days);
        if (all) {
            return humanService.whosBdayAtDay(DateTime.now(), DateTime.now().plusYears(1));
        }
        List<Human> humans = new ArrayList<>();
        for (int i = 0; i <= days; i++) {
            humans.addAll(humanService.whosBdayAtDay(DateTime.now().plusDays(i)));
        }
        return humans;
    }

    @RequestMapping(value = "/human/add", method = RequestMethod.POST)
    public void addHuman(@RequestParam String name, @RequestParam String surname, @RequestParam String bday) {
        humanService.addHuman(new Human(name, surname, DateTime.parse(bday)));
    }

    @RequestMapping(value = "/human/remove", method = RequestMethod.POST)
    public void removeHuman(@RequestParam String name, @RequestParam String surname) {
        humanService.removeHumanByFullName(name + " " + surname);
    }

    @RequestMapping(value = "/testmessage", method = RequestMethod.GET)
    public void testMessage() {
        slackIntegrationService.sendCongratulation(new Human("Andrey", "Tonevitskiy", DateTime.now()), new CongratulationMessage("", ""));

    }

}
