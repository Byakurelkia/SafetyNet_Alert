package net.safety.service;

import com.jsoniter.any.Any;
import net.safety.dataLoad.DataLoadInit;
import net.safety.exception.DataLoadErrorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DataLoadInitTest {

    private DataLoadInit dataLoadInit;

    @BeforeEach
    void setUp() {
        dataLoadInit = new DataLoadInit();
    }

    @Test
    public void data_load_should_return_any_object()  {
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