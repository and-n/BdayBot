package and.bday.service;

import java.util.List;

public interface FileService<T> {

    List<T> loadListFromFile(String fileName);

    void saveListToFile(List<T> info, String fileName);

}
