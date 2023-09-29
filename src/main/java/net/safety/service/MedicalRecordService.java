package net.safety.service;


import net.safety.repository.MedicalRecordRepository;
import org.springframework.stereotype.Service;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }
}
