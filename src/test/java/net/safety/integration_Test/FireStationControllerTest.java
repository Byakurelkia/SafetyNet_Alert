package net.safety.integration_Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.safety.dto.ChildAndFamilyByAddressDto;
import net.safety.dto.PersonInfoByStationNumberDto;
import net.safety.dto.PersonInfoDto;
import net.safety.dto.PersonsInfoByAddressOrStationNumberDto;
import net.safety.model.FireStation;
import net.safety.response.ChildAndFamilyByAddressResponse;
import net.safety.response.PersonInfoByStationNumberResponse;
import net.safety.response.PersonsByAddressResponse;
import net.safety.response.PersonsInfoByStationNumberListResponse;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FireStationControllerTest {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    void get_all_fire_station_should_return_list_of_fire_station() throws Exception {
        MvcResult result = mockMvc.perform(get("/firestations"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<FireStation> fireStationSet = mapper.readerForListOf(FireStation.class)
                .readValue(result.getResponse().getContentAsString());

        Assertions.assertTrue(fireStationSet.size()>10);
    }

    @Test
    void get_fire_station_by_number_should_return_list_of_fire_station() throws Exception {

        MvcResult result = mockMvc.perform(get("/firestations/find/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<FireStation> resultListReceived = mapper.readerForListOf(FireStation.class)
                .readValue(result.getResponse().getContentAsString());

        Assertions.assertEquals(3, resultListReceived.size());

    }

    @Test
    void get_fire_station_by_number_should_throw_exception() throws Exception{

        MvcResult result = mockMvc.perform(get("/firestations/find/9999"))
                .andExpect(status().isNotFound())
                .andReturn();

        Assertions.assertEquals("FireStation specified with this number : 9999 doesn't exist.", result.getResponse().getContentAsString());
    }

    @Test
    void create_fire_station_should_return_fire_station() throws Exception{

        FireStation fireStationToCreateAndExpected = new FireStation("test address",90);

        MvcResult result = mockMvc.perform(post("/firestations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(fireStationToCreateAndExpected)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        FireStation fireStationResultReceived = mapper.readValue(result.getResponse().getContentAsString(), FireStation.class);

        Assertions.assertEquals(fireStationToCreateAndExpected, fireStationResultReceived);
    }

    @Test
    void create_fire_station_should_throw_exception() throws Exception{
        FireStation fireStationToCreate = new FireStation("947 E. Rose Dr",1);

        MvcResult result = mockMvc.perform(post("/firestations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(fireStationToCreate)))
                .andExpect(status().isBadRequest())
                .andReturn();

        Assertions.assertEquals("Same Firestation exists", result.getResponse().getContentAsString());

    }

    @Test
    void update_fire_station_should_return_fire_station_updated() throws Exception {

        FireStation fireStationExpected = new FireStation("1509 Culver St", 15);
        int stationNumber = 15;

        MvcResult result = mockMvc.perform(put("/firestations/1509 Culver St")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(stationNumber)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        FireStation fireStationReceived = mapper.readValue(result.getResponse().getContentAsString(), FireStation.class);

        Assertions.assertEquals(fireStationExpected,fireStationReceived);

    }

    @Test
    void update_fire_station_should_throw_exception() throws Exception{
        int stationNumber = 15;

        MvcResult result = mockMvc.perform(put("/firestations/Not Exist Address Test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(stationNumber)))
                .andExpect(status().isNotFound())
                .andReturn();

        Assertions.assertEquals("L'adresse spécifié n'existe pas ! ", result.getResponse().getContentAsString());
    }

    @Test
    void delete_fire_station_should_return_no_content() throws Exception {

        mockMvc.perform(delete("/firestations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("address", "644 Gershwin Cir")
                        .param("stationNumber", String.valueOf(1)))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void delete_fire_station_should_throw_exception() throws Exception {
        MvcResult result = mockMvc.perform(delete("/firestations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("address", "NO ADDRESS EXIST")
                        .param("stationNumber", String.valueOf(30)))
                .andExpect(status().isNotFound())
                .andReturn();

        Assertions.assertEquals("This address and number specified doesn't correspond to a fire station ! "
                , result.getResponse().getContentAsString());
    }

    @Test
    void get_person_info_by_station_number_should_return_info_dto_response() throws Exception {
        List<PersonInfoByStationNumberDto> personInfoByStationNumberDtoList = Arrays.asList(
                new PersonInfoByStationNumberDto("Ron","Peters","841-874-8888","112 Steppes Pl"),
                new PersonInfoByStationNumberDto("Tony","Cooper","841-874-6874","112 Steppes Pl"),
                new PersonInfoByStationNumberDto("Lily","Cooper","841-874-9845","489 Manchester St"),
                new PersonInfoByStationNumberDto("Allison","Boyd","841-874-9888","112 Steppes Pl")
        );
        PersonInfoByStationNumberResponse expectedResponse =
                new PersonInfoByStationNumberResponse(personInfoByStationNumberDtoList,4,0);

        MvcResult result = mockMvc.perform(get("/firestations/{stationNumber}",4))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        PersonInfoByStationNumberResponse resultReceived = mapper.readValue(result.getResponse().getContentAsString()
                , PersonInfoByStationNumberResponse.class);

        Assertions.assertEquals(expectedResponse.toString(), resultReceived.toString());
    }

    @Test
    void get_person_info_by_station_number_should_throw_exception() throws Exception {
        MvcResult result = mockMvc.perform(get("/firestations/{stationNumber}",49))
                .andExpect(status().isNotFound())
                .andReturn();

        Assertions.assertEquals("FireStation specified with this number : " + 49 + " doesn't exist."
                ,result.getResponse().getContentAsString());
    }

    @Test
    void get_child_and_family_by_address_should_return_response() throws Exception {

        String address = "1509 Culver St";

        List<ChildAndFamilyByAddressDto> childAndFamilyByAddressDtoList = Arrays.asList(
                new ChildAndFamilyByAddressDto("Tenley","Boyd",11),
                new ChildAndFamilyByAddressDto("Roger","Boyd",6)
        );

        List<PersonInfoDto> personInfoDtoList = Arrays.asList(
                new PersonInfoDto("John","Boyd",address,"841-874-6512"),
                new PersonInfoDto("Felicia","Boyd",address,"841-874-6544"),
                new PersonInfoDto("Jacob","Boyd",address,"841-874-6513")
        );

        ChildAndFamilyByAddressResponse expectedResponse =
                new ChildAndFamilyByAddressResponse(childAndFamilyByAddressDtoList,personInfoDtoList);

        MvcResult result = mockMvc.perform(get("/firestations/childAlert/{address}", address))
                .andExpect(status().isOk())
                .andReturn();

        ChildAndFamilyByAddressResponse resultResponse =
                mapper.readValue(result.getResponse().getContentAsString(), ChildAndFamilyByAddressResponse.class);

        Assert.assertEquals(expectedResponse.toString(),resultResponse.toString());


    }

    @Test
    void get_child_and_family_by_address_should_throw_exception() throws Exception {

        String address = "NOT EXIST ADDRESS";

        MvcResult result = mockMvc.perform(get("/firestations/childAlert/{address}", address))
                .andExpect(status().isNotFound())
                .andReturn();

        Assert.assertEquals("Nobody exist in this address specified! ", result.getResponse().getContentAsString());
    }

    @Test
    void get_all_phone_numbers_by_station_number_should_return_number_list() throws Exception {
        int stationNumber = 2;

        List<String> expectedNumbersList = Arrays.asList(
                "841-874-7512","841-874-7512","841-874-7878","841-874-6513","841-874-7458"
        );

        MvcResult result = mockMvc.perform(get("/firestations/phoneAlert/{stationNumber}", stationNumber))
                .andExpect(status().isOk())
                .andReturn();

        List<String> receivedResult = mapper.readerForListOf(String.class).readValue(result.getResponse().getContentAsString());

        //Si la liste reçu contient une entrée non attendu, l'assert control retournera false
        boolean value = true;
        for (String s : expectedNumbersList) {
            if (!receivedResult.contains(s)) {
                value = false;
                break;
            }
        }

        Assert.assertTrue(value);

    }

    @Test
    void get_all_phone_numbers_by_station_number_should_throw_exception() throws Exception{
        int stationNumber = 100;

        MvcResult result = mockMvc.perform(get("/firestations/phoneAlert/{stationNumber}", stationNumber))
                .andExpect(status().isNotFound())
                .andReturn();

        Assert.assertEquals("FireStation specified with this number : " + stationNumber + " doesn't exist.",
                result.getResponse().getContentAsString());
    }

    @Test
    void get_persons_info_by_address_should_return_persons_by_address_list_response() throws Exception {

        String address = "112 Steppes Pl";

        List<PersonsInfoByAddressOrStationNumberDto> personsInfoByAddressOrStationNumberDtoList = Arrays.asList(

                new PersonsInfoByAddressOrStationNumberDto("Peters","841-874-8888",58,
                        Arrays.asList(),Set.of()),
                new PersonsInfoByAddressOrStationNumberDto("Cooper","841-874-6874",29,
                        Arrays.asList("hydrapermazol:300mg", "dodoxadin:30mg"), Set.of("shellfish")),
                new PersonsInfoByAddressOrStationNumberDto("Boyd","841-874-9888",58,
                        Arrays.asList("aznol:200mg"), Set.of("nillacilan"))
        );

        List<Integer> stationNumbersList = Arrays.asList(4,3);

        PersonsByAddressResponse expectedResponse =
                new PersonsByAddressResponse(personsInfoByAddressOrStationNumberDtoList, stationNumbersList);

        MvcResult result = mockMvc.perform(get("/firestations/fire/{address}", address))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        PersonsByAddressResponse receivedResult =
                mapper.readValue(result.getResponse().getContentAsString(), PersonsByAddressResponse.class);

        Assert.assertEquals(expectedResponse.toString(),receivedResult.toString());

    }

    @Test
    void get_persons_info_by_address_should_throw_exception() throws Exception{

        String address = "NOR ADDRESS EXISTS";

        MvcResult result = mockMvc.perform(get("/firestations/fire/{address}", address))
                .andExpect(status().isNotFound())
                .andReturn();

        Assert.assertEquals("Nobody exist in this address specified! ", result.getResponse().getContentAsString());

    }

    @Test
    void get_persons_info_by_station_number_list_should_return_response_list() throws Exception {

        String address1 = "29 15th St";
        String address2 = "892 Downing Ct";
        String address3 = "951 LoneTree Rd";
        String address4 = "644 Gershwin Cir";
        String address5 = "908 73rd St";
        String address6 = "947 E. Rose Dr";

        List<Integer> stationNumberList = new ArrayList<>();
        stationNumberList.add(2);
        stationNumberList.add(1);

        List<PersonsInfoByAddressOrStationNumberDto> personsInfoList1 =
                Arrays.asList(new PersonsInfoByAddressOrStationNumberDto
                        ("Marrack","841-874-6513",34,Arrays.asList(),Set.of()));

        List<PersonsInfoByAddressOrStationNumberDto> personsInfoList2 = Arrays.asList(
                new PersonsInfoByAddressOrStationNumberDto("Zemicks","841-874-7512",6,
                        Arrays.asList(),Set.of()),
                new PersonsInfoByAddressOrStationNumberDto("Zemicks","841-874-7512",38,
                        Arrays.asList(),Set.of()),
                new PersonsInfoByAddressOrStationNumberDto("Zemicks","841-874-7878",35,
                        Arrays.asList("aznol:60mg", "hydrapermazol:900mg", "pharmacol:5000mg", "terazine:500mg")
                        ,Set.of("peanut", "shellfish", "aznol"))
                );

        List<PersonsInfoByAddressOrStationNumberDto> personsInfoList3 = Arrays.asList(
                new PersonsInfoByAddressOrStationNumberDto("Cadigan","841-874-7458",78,
                        Arrays.asList("tradoxidine:400mg"),Set.of()));

        List<PersonsInfoByAddressOrStationNumberDto> personsInfoList4 = Arrays.asList(
                new PersonsInfoByAddressOrStationNumberDto("Duncan","841-874-6512",23,
                        Arrays.asList(),Set.of("shellfish")));

        List<PersonsInfoByAddressOrStationNumberDto> personsInfoList5 = Arrays.asList(
                new PersonsInfoByAddressOrStationNumberDto("Peters","841-874-7462",41,
                        Arrays.asList(),Set.of()),
                new PersonsInfoByAddressOrStationNumberDto("Walker","841-874-8547",44,
                        Arrays.asList("thradox:700mg"), Set.of("illisoxian"))
                );

        List<PersonsInfoByAddressOrStationNumberDto> personsInfoList6 = Arrays.asList(
                new PersonsInfoByAddressOrStationNumberDto("Stelzer","841-874-7784",47,
                        Arrays.asList("ibupurin:200mg", "hydrapermazol:400mg"), Set.of("nillacilan")),
                new PersonsInfoByAddressOrStationNumberDto("Stelzer","841-874-7784",9,
                        Arrays.asList("noxidian:100mg", "pharmacol:2500mg"), Set.of()),
                new PersonsInfoByAddressOrStationNumberDto("Stelzer","841-874-7784",43,
                        Arrays.asList(), Set.of())
                );

        List<PersonsInfoByStationNumberListResponse> expectedList = Arrays.asList(
                new PersonsInfoByStationNumberListResponse(address2,personsInfoList2),
                new PersonsInfoByStationNumberListResponse(address1,personsInfoList1),
                new PersonsInfoByStationNumberListResponse(address3,personsInfoList3),
                new PersonsInfoByStationNumberListResponse(address6,personsInfoList6),
                new PersonsInfoByStationNumberListResponse(address4,personsInfoList4),
                new PersonsInfoByStationNumberListResponse(address5,personsInfoList5)
        );

        MvcResult result = mockMvc.perform(get("/firestations/flood/2,1"))
                .andExpect(status().isOk())
                .andReturn();

        List<PersonsInfoByStationNumberListResponse> resultReceived =
                mapper.readerForListOf(PersonsInfoByStationNumberListResponse.class)
                        .readValue(result.getResponse().getContentAsString());

        Assert.assertTrue(expectedList.size() == resultReceived.size());


    }

    @Test
    void get_persons_info_by_station_number_list_should_throw_exception() throws Exception{

        MvcResult result = mockMvc.perform(get("/firestations/flood/199,180"))
                .andExpect(status().isNotFound())
                .andReturn();

        Assert.assertEquals("FireStation specified with this number : " + 199 + " doesn't exist."
                , result.getResponse().getContentAsString());
    }

}