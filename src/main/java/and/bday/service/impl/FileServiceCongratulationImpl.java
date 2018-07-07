package and.bday.service.impl;

import and.bday.service.FileService;
import and.bday.service.model.CongratulationMessage;
import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceCongratulationImpl implements FileService<CongratulationMessage> {

    private static final Logger log = Logger.getLogger(FileService.class);
    private final Gson gson = Converters.registerDateTime(new GsonBuilder().setPrettyPrinting()).create();

    @Override
    public List<CongratulationMessage> loadListFromFile(String fileName) {

        Type listType = new TypeToken<List<CongratulationMessage>>() {
        }.getType();
        final List<CongratulationMessage> loadedDataList = new ArrayList<>();

        synchronized (gson) {
            final Path filePath = Paths.get(fileName);
            if (Files.exists(filePath)) {
                try (final Reader reader = new FileReader(fileName)) {
                    loadedDataList.addAll(gson.fromJson(reader, listType));
                } catch (Exception e) {
                    log.error("File " + fileName + " loading problem", e);
                }
            } else {
                saveListToFile(loadedDataList, fileName);
            }
        }
        return loadedDataList;
    }

    @Override
    public void saveListToFile(List<CongratulationMessage> info, String fileName) {
        synchronized (gson) {
            final Path filePath = Paths.get(fileName);
            try (final FileWriter fw = new FileWriter(filePath.toFile())) {
                // add save to temp file
                final String gs = gson.toJson(info);
                fw.write(gs);
                fw.flush();
                fw.close();
                log.info("file " + fileName + " created");
            } catch (IOException e) {
                log.error("Save to fail error! ", e);
                SlackIntegrationServiceImpl.sendError("save to file error " + e.getMessage());
            }
        }
    }

}
