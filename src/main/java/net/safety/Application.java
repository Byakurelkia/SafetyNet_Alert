package net.safety;

import net.safety.dataLoad.DataLoadFireStation;
import net.safety.dataLoad.DataLoadMedicalRecord;
import net.safety.dataLoad.DataLoadPerson;
import net.safety.dto.PersonDto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		DataLoadMedicalRecord dataLoadMedicalRecord = new DataLoadMedicalRecord();
		DataLoadFireStation dataLoadFireStation =new DataLoadFireStation();

		DataLoadPerson dataLoadPerson = new DataLoadPerson(dataLoadMedicalRecord, dataLoadFireStation);
		dataLoadPerson.allPersons().stream().forEach(p->  System.out.println(new PersonDto(p)));
		//dataLoadPerson.allPersonsWithMedRecAndFireStation().stream().forEach(all -> System.out.println(all.toString()));

		/*dataLoadFireStation.allFireStations().forEach(f->
				System.out.println(f + " " + dataLoadFireStation.allFireStations().size())
				);*/
		//dataLoadMedicalRecord.allMedicalRecords().stream().forEach(m-> System.out.println(m.toString()));
		//System.out.println("SON SATIR INDEXLI ARAMA = " + dataLoadPerson.allPersonsWithMedicalRecord().toString());
		/*DataLoadFireStation dataLoadFireStation = new DataLoadFireStation();
		dataLoadFireStation.allFireStations().stream().forEach(f-> System.out.println(f.toString()));*/
	}
}
