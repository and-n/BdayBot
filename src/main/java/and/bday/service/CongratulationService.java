package and.bday.service;

import and.bday.service.model.CongratulationMessage;

public interface CongratulationService {

    void addCongratulationMessage(CongratulationMessage congratulationMessage);

    CongratulationMessage getCongratulation();

}
