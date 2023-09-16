package net.safety.dataLoad;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Component
public class DataLoadInit {

    private String str = "src/main/resources/dataSafetyNet.json";

    public DataLoadInit() { }

    public Any readerFileJSON() throws IOException {
        byte[] bytesFile = Files.readAllBytes(new File(str).toPath());
        Any any;
        try (JsonIterator iter = JsonIterator.parse(bytesFile)) {
            any = iter.readAny();
        }
        return any;
    }

}
