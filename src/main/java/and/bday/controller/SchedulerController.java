package and.bday.controller;

import and.bday.service.CongratulationService;
import and.bday.service.HumanService;
import and.bday.service.SlackIntegrationService;
import and.bday.service.model.Human;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SchedulerController {

    private HumanService humanService;
    private CongratulationService congratulationService;
    private SlackIntegrationService slackIntegrationService;

    @Scheduled(cron = "0 10 10 * * ?")
    public void congratBdays() {
        List<Human> humans = humanService.whosBdayToday();
        for (Human human : humans) {
            slackIntegrationService.sendCongratulation(human, congratulationService.getCongratulation());
        }

    }

}
