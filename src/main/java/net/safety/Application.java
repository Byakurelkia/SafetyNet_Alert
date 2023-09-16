package net.safety;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.safety.model.FireStation;
import net.safety.repository.FireStationRepository;
import org.json.JSONObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.*;
import java.util.List;

@EnableWebMvc
@SpringBootApplication
public class Application implements CommandLineRunner {

	public Application() throws IOException {
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
/*
		FireStationRepository repository = new FireStationRepository();
		FireStation fs = new FireStation("DENEME ADRESIM", 287654);
		repository.liste.forEach(a->
				{
					System.out.println("ILK DEGERLER " + a);
				}
				);

		System.out.println(repository.addForeStation(fs));

		repository.liste.forEach(f->
				System.out.println("EKLEME POMPIER ======= " + f)
				);*/



		/*ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		InputStream inputStream = new FileInputStream(new File("C:\\Users\\sumey\\Downloads\\Projet5\\src\\main\\resources\\dataSafetyNet.json"));
		TypeReference<List<FireStation>> typeReference = new TypeReference<List<FireStation>>() {};
		List<FireStation> fireStations = mapper.readValue(inputStream, typeReference);
		for (FireStation f : fireStations)
			System.out.println(f.getAddress());

		FireStation fs = new FireStation("1509 Culver St", 2);
		mapper.writeValue(new FileWriter("C:\\Users\\sumey\\Downloads\\Projet5\\src\\main\\resources\\fireStation2.txt"), fs);
		inputStream.close();

		f.allFireStations();*/
		}

	}

