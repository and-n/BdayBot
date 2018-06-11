package and.bday.service;

import and.bday.service.model.CongratulationMessage;
import and.bday.service.model.Human;

public interface SlackIntegrationService {

    void sendCongratulation(Human human, CongratulationMessage congratulationMessage);

}
