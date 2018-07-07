package and.bday.service.impl;

import and.bday.service.CongratulationService;
import and.bday.service.FileService;
import and.bday.service.model.CongratulationMessage;
import com.google.gson.reflect.TypeToken;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Random;

@Service
public class CongratulationServiceImpl implements CongratulationService {

    private static final Logger log = Logger.getLogger(CongratulationServiceImpl.class);
    private static Random randomGenerator = new Random();
    @Value("${messageFileName:messages.json}")
    private String congratulationMessagesFile;
    private FileService<CongratulationMessage> messageFileService;
    private final Type type = new TypeToken<List<CongratulationMessage>>() {
    }.getType();

    @Autowired
    public void setMessageFileService(FileService<CongratulationMessage> messageFileService) {
        this.messageFileService = messageFileService;
    }

    @Override
    public CongratulationMessage getCongratulation() {
        try {
            List<CongratulationMessage> preparationList = messageFileService.loadListFromFile(congratulationMessagesFile, type);

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
        List<CongratulationMessage> list = messageFileService.loadListFromFile(congratulationMessagesFile, type);
        list.add(congratulationMessage);
        messageFileService.saveListToFile(list, congratulationMessagesFile);

    }

}
