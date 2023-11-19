package net.safety.integration_Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.safety.dto.PersonDto;
import net.safety.dto.PersonInfoByLastNameDto;
import net.safety.model.Person;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonControllerTest{

    @Autowired
    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void get_all_persons_should_return_persons_list() throws Exception {

        MvcResult result = mockMvc.perform(get("/persons"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<Person> personList = mapper.readerForListOf(Person.class).readValue(result.getResponse().getContentAsString());

        Assert.assertTrue(!personList.isEmpty());
    }

    @Test
    public void get_person_by_first_and_last_name_should_return_person() throws Exception {
        Person expectedPerson = new Person("Allison","Boyd",
                "112 Steppes Pl", "Culver","aly@imail.com", "841-874-9888", "97451");

        MvcResult result = mockMvc.perform(get("/persons/find/Allison/Boyd"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Person resultPerson = mapper.readValue(result.getResponse().getContentAsString(), Person.class);

        Assert.assertEquals(expectedPerson,resultPerson);
    }

    @Test
    public void get_person_by_first_and_last_name_should_throw_exception() throws Exception {

        MvcResult result = mockMvc.perform(get("/persons/find/NoNameExist/NoLastNameExist"))
                .andExpect(status().isNotFound())
                .andReturn();

        Assert.assertEquals("Person doesn't exist with this first and last name !"
                , result.getResponse().getContentAsString());
    }

    @Test
    public void create_person_should_return_person() throws Exception {
        Person personToCreateAndExpected = new Person("PersonCreated FirstName ","PersonCreated LastName",
                "112 Steppes Pl", "Culver","aly@imail.com", "841-874-9888", "97451");

        MvcResult result = mockMvc.perform(post("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(personToCreateAndExpected)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();


        Person receivedPerson = mapper.readValue(result.getResponse().getContentAsString(), Person.class);

        Assert.assertEquals(personToCreateAndExpected, receivedPerson);

    }

    @Test
    public void create_person_should_throw_exception() throws Exception {
        Person personToCreate = new Person("Allison","Boyd",
                "112 Steppes Pl", "Culver","aly@imail.com", "841-874-9888", "97451");

        MvcResult result = mockMvc.perform(post("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(personToCreate)))
                .andExpect(status().isBadRequest())
                .andReturn();

        Assert.assertEquals("A person with same name and last name exists already !",
                result.getResponse().getContentAsString());
    }

    @Test
    public void update_person_should_return_updated_person() throws Exception {
        Person expectedPerson =
                new Person("Brian", "Stelzer", "Adress Modified",
                        "City Modified", "Modified@email.com", "00000000", "97451");

        PersonDto personDtoModified =
                new PersonDto("Modified@email.com", "00000000",
                        "Adress Modified","97451","City Modified");

        MvcResult result = mockMvc.perform(put("/persons/Brian/Stelzer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(personDtoModified)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Person resultPerson = mapper.readValue(result.getResponse().getContentAsString(), Person.class);

        Assert.assertEquals(expectedPerson,resultPerson);

    }

    @Test
    public void update_person_should_throw_exception() throws Exception {

        PersonDto personDtoModified =
                new PersonDto("Modified@email.com", "00000000",
                        "Adress Modified","97451","City Modified");

        MvcResult result = mockMvc.perform(put("/persons/NoNameExist/NoLastNameExist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(personDtoModified)))
                .andExpect(status().isNotFound())
                .andReturn();

        Assert.assertEquals("Person with first and last name : 'NoNameExist , NoLastNameExist doesnt exists !",
                result.getResponse().getContentAsString());

    }

    @Test
    public void delete_person_should_return_no_content() throws Exception {

        MvcResult result = mockMvc.perform(delete("/persons/Shawna/Stelzer"))
                .andExpect(status().isNoContent())
                .andReturn();

        Assert.assertEquals("", result.getResponse().getContentAsString());
    }

    @Test
    public void delete_person_should_throw_exception() throws Exception{
        MvcResult result = mockMvc.perform(delete("/persons/NoNamePresent/NoLastNamePresent"))
                .andExpect(status().isNotFound())
                .andReturn();

        Assert.assertEquals("Person with first and last name : 'NoNamePresent , NoLastNamePresent doesnt exists !"
                , result.getResponse().getContentAsString());
    }

    @Test
    public void get_mails_by_city_should_return_list() throws Exception {

        MvcResult result = mockMvc.perform(get("/communityEmail/Culver"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<String> mailsList = mapper.readerForListOf(String.class)
                .readValue(result.getResponse().getContentAsString());

        Assert.assertTrue(!mailsList.isEmpty());
    }

    @Test
    public void get_mails_by_city_should_thrown_exception() throws Exception{

        MvcResult result = mockMvc.perform(get("/communityEmail/NotExistCityName"))
                .andExpect(status().isNotFound())
                .andReturn();

        Assert.assertEquals("This city doesnt exist in our database ! ", result.getResponse().getContentAsString());
    }

    @Test
    public void get_persons_info_by_first_and_last_name_dto_should_return_list() throws Exception{


        List<PersonInfoByLastNameDto> personInfoByLastNameDtoList = new ArrayList<>();
        personInfoByLastNameDtoList.add(new PersonInfoByLastNameDto("Ferguson",
                        "748 Townings Dr",29,"clivfd@ymail.com", Arrays.asList(), Set.of()));


        MvcResult result = mockMvc.perform(get("/personInfo/firstName/Ferguson"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<PersonInfoByLastNameDto> resultReceived = mapper.readerForListOf(PersonInfoByLastNameDto.class)
                .readValue(result.getResponse().getContentAsString());

        Assert.assertEquals(personInfoByLastNameDtoList.toString(),resultReceived.toString());


    }

    @Test
    public void get_persons_info_by_first_and_last_name_dto_should_throw_exception() throws Exception {
        MvcResult result = mockMvc.perform(get("/personInfo/AnyFirstName/NoExistLastName"))
                .andExpect(status().isNotFound())
                .andReturn();

        Assert.assertEquals("Person doesn't exist with this lastName." , result.getResponse().getContentAsString());
    }
}