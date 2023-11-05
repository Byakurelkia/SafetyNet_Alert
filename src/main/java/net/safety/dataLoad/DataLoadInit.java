package net.safety.dataLoad;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import net.safety.exception.DataLoadErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Component
public class DataLoadInit {

    private String str = "src/main/resources/dataSafetyNet.json";
    private final Logger logger = LoggerFactory.getLogger(DataLoadInit.class);
    public DataLoadInit() {
    }

    public Any readerFileJSON() {
        try {
            byte[] bytesFile = Files.readAllBytes(new File(str).toPath());
            Any any;
            try (JsonIterator iter = JsonIterator.parse(bytesFile)) {
                any = iter.readAny();
                return any;
            }
        } catch (IOException e) {
            logger.error("Error when reading data from file ..");
            throw new DataLoadErrorException("Error when reading data from file..");

        }

    }

    public void setStr(String str) {
        this.str = str;
    }
    /*
    1) On va tester la methode readerFileJSON qui se trouve sur la classe DataLoadInit, donc test class name -> DataLoadInitTest
    2) Cette classe ne prend rien en paramètre pour être créé, donc pas de Mock
    3) Il use un path type String pour lire un fichier

    3.1) Si le path est correcte et que le fichier est bien lu, Il me retourne un objet any à la fin
    3.2) Si le path est erronée ou autre et que le fichier n'a pas pu être lu, Il me retourne une exception

    */

}
