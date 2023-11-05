package net.safety.dataLoad;

import com.jsoniter.any.Any;
import net.safety.exception.DataLoadErrorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class DataLoadInitTest {

    private DataLoadInit dataLoadInit;

    @BeforeEach
    void setUp() {
        dataLoadInit = new DataLoadInit();
    }

    @Test
    public void data_load_should_return_any_object() throws IOException {
        Any result = dataLoadInit.readerFileJSON();

        assertNotNull(result);
        assertTrue(result.toBoolean());
    }

    @Test
    public void data_load_should_throw_exception() {
        dataLoadInit.setStr("src/main/resources/dataSafetyNeterror.json"); //onsimule une exception sur cette ligne
        DataLoadErrorException exception = assertThrows( DataLoadErrorException.class , ()-> dataLoadInit.readerFileJSON());
        assertEquals( "Error when reading data from file..", exception.getMessage());
    }


}