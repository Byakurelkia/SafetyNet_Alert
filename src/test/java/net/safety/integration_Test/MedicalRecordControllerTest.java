package net.safety.integration_Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.safety.dto.MedicalRecordDto;
import net.safety.model.MedicalRecord;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void test_get_all_medical_records_should_return_list() throws Exception {

        MvcResult result = this.mockMvc
                .perform(get("/medicalrecords")) // ça prepare la requete
                .andDo(print())//il execute la reuqtete
                .andExpect(status().isOk()) // on s'attend à ...
                .andReturn(); // retourne la réponse reçu de cette requete

        List<MedicalRecord> medicalRecordList = mapper.readerForListOf(MedicalRecord.class).readValue(result.getResponse().getContentAsString());
       // AddressNotFoundException exception = mapper.readValue(result.getResponse().getContentAsString(), AddressNotFoundException.class);
       // Assert.assertEquals("message expected ", exception.getMessage());

        Assert.assertTrue(medicalRecordList.size()>0);
    }

    @Test
    public void get_medical_record_by_name_and_last_name_should_return_medical_record() throws Exception {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        List<MedicalRecord> expectedMedicalRecord = Arrays.asList(new MedicalRecord("John", "Boyd", df.parse("03/06/1984")
                , Arrays.asList("aznol:350mg","hydrapermazol:100mg"), Set.of("nillacilan")));

        MvcResult result = mockMvc.perform(get("/medicalrecords/John/Boyd"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<MedicalRecord> resultMedicalRecord = mapper.readerForListOf(MedicalRecord.class).readValue(result.getResponse().getContentAsString());

        resultMedicalRecord.forEach(medicalRecord -> {
            try {
                medicalRecord.setBirthDate(df.parse("03/06/1984"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        Assert.assertEquals(expectedMedicalRecord, resultMedicalRecord);
    }

    @Test
    public void get_medical_record_by_name_and_last_name_should_throw_exception() throws Exception {

        MvcResult result = mockMvc.perform(get("/medicalrecords/NoName/NoLastName"))
                .andExpect(status().isNotFound())
                .andReturn();

        Assert.assertEquals("This person doesnt have a medical record saved!", result.getResponse().getContentAsString());

    }

    @Test
    public void create_medical_record_should_return_medical_record() throws Exception {
        MedicalRecord medicalRecordToCreate =
                new MedicalRecord("firstName", "lastName", new Date(),
                        Arrays.asList("medications1","medications2"), Set.of());

        MvcResult result = mockMvc.perform(post("/medicalrecords")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(medicalRecordToCreate)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MedicalRecord medicalRecordToReceived = mapper.readValue(result.getResponse().getContentAsString(), MedicalRecord.class);

        Assert.assertEquals(medicalRecordToCreate,medicalRecordToReceived);

    }

    @Test
    public void create_medical_record_should_return_error() throws Exception {
        MedicalRecord medicalRecordToCreate = new MedicalRecord("John","Boyd",new Date(),Arrays.asList("aznol:350mg", "hydrapermazol:100mg"), Set.of("nillacilan"));

        MvcResult result = mockMvc.perform(post("/medicalrecords")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(medicalRecordToCreate)))
                .andExpect(status().isBadRequest())
                .andReturn();

        Assert.assertEquals("A medical record already exist for this person", result.getResponse().getContentAsString());
    }

    @Test
    public void update_medical_record_should_return_medical_record_updated() throws Exception{
        MedicalRecord expectedMedicalRecord = new MedicalRecord("Tessa", "Carman", null
                , Arrays.asList(), Set.of());

        MedicalRecordDto medicalRecordDtoToUpdate = new MedicalRecordDto(null, Arrays.asList(), Set.of());

        MvcResult result = mockMvc.perform(put("/medicalrecords/Tessa/Carman")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(medicalRecordDtoToUpdate)))
                .andExpect(status().isOk())
                .andReturn();

        MedicalRecord medicalRecordReceived = mapper.readValue(result.getResponse().getContentAsString(), MedicalRecord.class);

        Assert.assertEquals(expectedMedicalRecord, medicalRecordReceived);

    }

    @Test
    public void update_medical_record_should_throw_exception() throws Exception {

        MedicalRecordDto medicalRecordDtoToUpdate = new MedicalRecordDto(null, Arrays.asList(), Set.of());

        MvcResult result = mockMvc.perform(put("/medicalrecords/NoName/NoLastName")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(medicalRecordDtoToUpdate)))
                .andExpect(status().isNotFound())
                .andReturn();

        Assert.assertEquals("Medical record doesnt exist with this first and last name"
                , result.getResponse().getContentAsString());
    }

    @Test
    public void delete_medical_record_should_return_no_content() throws Exception {

        MvcResult resultDelete = mockMvc.perform(delete("/medicalrecords/Reginold/Walker"))
                .andExpect(status().isNoContent())
                .andReturn();

    }

    @Test
    public void delete_medical_record_should_throw_exception() throws Exception {

        MvcResult resultDelete = mockMvc.perform(delete("/medicalrecords/NoNamePresent/NoLastNamePresent"))
                .andExpect(status().isNotFound())
                .andReturn();

        Assert.assertEquals("Medical record doesnt exist with this first and last name"
                , resultDelete.getResponse().getContentAsString());

    }
}