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
    public DataLoadInit() { }

    public Any readerFileJSON() throws IOException {
        try {
            byte[] bytesFile = Files.readAllBytes(new File(str).toPath());
            Any any;
            try (JsonIterator iter = JsonIterator.parse(bytesFile)) {
                any = iter.readAny();
                return any;
            }
        }catch (DataLoadErrorException e){
            logger.error("Error when reading data from file ..");
            throw new DataLoadErrorException("Error when reading data from file..");
        }


    }

}
