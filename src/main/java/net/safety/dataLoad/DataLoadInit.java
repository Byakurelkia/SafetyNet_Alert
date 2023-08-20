package net.safety.dataLoad;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


public class DataLoadInit {

    private final static String str = "src/main/resources/dataSafetyNet.json";

    public DataLoadInit() { }

    protected Any readerFileJSON() throws IOException {
        byte[] bytesFile = Files.readAllBytes(new File(str).toPath());
        Any any;
        try (JsonIterator iter = JsonIterator.parse(bytesFile)) {
            any = iter.readAny();
        }
        return any;
    }

}
