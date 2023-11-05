package net.safety.rest_Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonControllerTest {

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


}