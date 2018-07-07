package and.bday.service;

import java.lang.reflect.Type;
import java.util.List;

public interface FileService<T> {

    List<T> loadListFromFile(String fileName, Type gsonLoadype);

    void saveListToFile(List<T> info, String fileName);

}
