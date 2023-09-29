package net.safety.rest_Controller;

import net.safety.model.MedicalRecord;
import net.safety.repository.MedicalRecordRepository;
import net.safety.service.MedicalRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/medicalrecords")
public class MedicalRecordController {

    private final MedicalRecordRepository medicalRecordRepository;
    private final MedicalRecordService medicalRecordService;
    private final Logger logger = LoggerFactory.getLogger(MedicalRecordController.class);

    public MedicalRecordController(MedicalRecordRepository medicalRecordRepository, MedicalRecordService medicalRecordService){
        this.medicalRecordRepository = medicalRecordRepository;
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping
    public List<MedicalRecord> getALlMedRec(){
        return MedicalRecordRepository.listMedicalRecords;
    }


}
