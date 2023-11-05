package net.safety.mapper;

import net.safety.dto.MedicalRecordDto;
import net.safety.model.MedicalRecord;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MedicalRecordMapper {

    MedicalRecordMapper INSTANCE = Mappers.getMapper(MedicalRecordMapper.class);

    MedicalRecord medicalRecordDtoToMedicalRecord(MedicalRecordDto medicalRecordDto);
}
