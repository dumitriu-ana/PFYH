package com.fyh.specialistservice.mapper;


import com.fyh.specialistservice.dto.SpecialistDto;
import com.fyh.specialistservice.entity.Specialist;

public class SpecialistMapper {

    public static SpecialistDto mapToSpecialistDto(Specialist specialist) {
        if (specialist == null) {
            return null;
        }

        SpecialistDto specialistDto = new SpecialistDto();
        specialistDto.setId(specialist.getId());
        specialistDto.setIdUtilizator(specialist.getIdUtilizator());
        specialistDto.setAtestat(specialist.getAtestat());
        specialistDto.setStatusValidare(specialist.getStatusValidare());
        specialistDto.setDescriere(specialist.getDescriere());
        specialistDto.setSoldAcumulat(specialist.getSoldAcumulat());
        specialistDto.setIdAdmin(specialist.getIdAdmin());
        specialistDto.setDataValidare(specialist.getDataValidare());

        return specialistDto;
    }

    public static Specialist mapToSpecialist(SpecialistDto specialistDto) {
        if (specialistDto == null) {
            return null;
        }

        Specialist specialist = new Specialist();
        specialist.setId(specialistDto.getId());
        specialist.setIdUtilizator(specialistDto.getIdUtilizator()); // Adaugă această linie
        specialist.setIdAdmin(specialistDto.getIdAdmin());       // Adaugă această linie
        specialist.setAtestat(specialistDto.getAtestat());
        specialist.setStatusValidare(specialistDto.getStatusValidare());
        specialist.setDescriere(specialistDto.getDescriere());
        specialist.setSoldAcumulat(specialistDto.getSoldAcumulat());
        specialist.setDataValidare(specialistDto.getDataValidare());

        return specialist;
    }
}
