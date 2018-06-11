package and.bday.service.impl;

import and.bday.service.CongratulationService;
import and.bday.service.FileService;
import and.bday.service.model.CongratulationMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class CongratulationServiceImpl implements CongratulationService {

    private static final Logger log = Logger.getLogger(CongratulationServiceImpl.class);
    private static Random randomGenerator = new Random();
    private final String congratulationMessagesFile = System.getProperty("messageFileName", "messages.json");
    private FileService<CongratulationMessage> messageFileService;

    @Autowired
    public void setMessageFileService(FileService<CongratulationMessage> messageFileService) {
        this.messageFileService = messageFileService;
    }

    @Override
    public CongratulationMessage getCongratulation() {
        try {
            List<CongratulationMessage> preparationList = messageFileService.loadListFromFile(congratulationMessagesFile);

            if (preparationList.size() > 0) {
                return preparationList.get(randomGenerator.nextInt(preparationList.size()));
            }
        } catch (final Exception e) {
            log.error(" congratulation problem! ", e);
        }
        return new CongratulationMessage("Happy birthday ", "! ");
    }

    @Override
    public void addCongratulationMessage(CongratulationMessage congratulationMessage) {
        List<CongratulationMessage> list = messageFileService.loadListFromFile(congratulationMessagesFile);
        list.add(congratulationMessage);
        messageFileService.saveListToFile(
                list,
                congratulationMessagesFile
        );

    }

}
