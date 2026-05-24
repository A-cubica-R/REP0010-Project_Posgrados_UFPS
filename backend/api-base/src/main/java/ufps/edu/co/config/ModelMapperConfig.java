package ufps.edu.co.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ufps.edu.co.persistence.entities.AdministrativoEntity;
import ufps.edu.co.persistence.entities.AspiranteEntity;
import ufps.edu.co.persistence.entities.CohorteEntity;
import ufps.edu.co.persistence.entities.EstadoEntity;
import ufps.edu.co.persistence.entities.PersonaEntity;
import ufps.edu.co.persistence.entities.SemestreEntity;
import ufps.edu.co.persistence.entities.UbicacionEntity;
import ufps.edu.co.rest.dto.AdministrativoDTO;
import ufps.edu.co.rest.dto.AspiranteDTO;
import ufps.edu.co.rest.dto.CohorteDTO;
import ufps.edu.co.rest.dto.EstadoDTO;
import ufps.edu.co.rest.dto.PersonaDTO;
import ufps.edu.co.rest.dto.SemestreDTO;
import ufps.edu.co.rest.dto.UbicacionDTO;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setAmbiguityIgnored(true);

        // Persona ↔ Aspirante / Administrativo / Usuario
        mapper.createTypeMap(PersonaEntity.class, PersonaDTO.class)
              .addMappings(m -> {
                  m.skip(PersonaDTO::setAspiranteList);
                  m.skip(PersonaDTO::setAdministrativoList);
                  m.skip(PersonaDTO::setUsuarioList);
              });

        // Persona DTO -> Entity (avoid ambiguous ubicacion and deep graphs)
            mapper.emptyTypeMap(PersonaDTO.class, PersonaEntity.class)
              .addMappings(m -> {
                  m.skip(PersonaEntity::setAdministrativoList);
                  m.skip(PersonaEntity::setAspiranteList);
                  m.skip(PersonaEntity::setUsuarioList);
                  m.skip(PersonaEntity::setGenero);
                  m.skip(PersonaEntity::setUbicacion);
                  m.skip(PersonaEntity::setGrupoetnico);
                  m.skip(PersonaEntity::setPoblacionindigena);
                  m.skip(PersonaEntity::setDiscapacidad);
                  m.skip(PersonaEntity::setDocumentopersona);
                  m.skip(PersonaEntity::setCapacidadexepcional);
                  m.skip(PersonaEntity::setEstadocivil);
                    })
                    .implicitMappings();

        // Cohorte ↔ Aspirante / ListaAdmitidos / Prueba / Criterio
        mapper.createTypeMap(CohorteEntity.class, CohorteDTO.class)
              .addMappings(m -> {
                  m.skip(CohorteDTO::setAspiranteList);
                  m.skip(CohorteDTO::setAdmitidoList);
                  m.skip(CohorteDTO::setCriterioevaluacionList);
                  m.skip(CohorteDTO::setPruebaList);
              });

        // Cohorte DTO -> Entity (avoid deep mapping of relations)
            mapper.emptyTypeMap(CohorteDTO.class, CohorteEntity.class)
              .addMappings(m -> {
                  m.skip(CohorteEntity::setAdmitidoList);
                  m.skip(CohorteEntity::setAspiranteList);
                  m.skip(CohorteEntity::setEstado);
                  m.skip(CohorteEntity::setSemestre);
                  m.skip(CohorteEntity::setModalidad);
                  m.skip(CohorteEntity::setPlazo);
                  m.skip(CohorteEntity::setPlazo2);
                  m.skip(CohorteEntity::setPlazo3);
                  m.skip(CohorteEntity::setPrograma);
                  m.skip(CohorteEntity::setCriterioevaluacionList);
                  m.skip(CohorteEntity::setPruebaList);
                    })
                    .implicitMappings();

        // Estado → listas bidireccionales (evita referencias circulares)
        mapper.createTypeMap(EstadoEntity.class, EstadoDTO.class)
              .addMappings(m -> {
                  m.skip(EstadoDTO::setAdministrativoList);
                  m.skip(EstadoDTO::setAspiranteList);
                  m.skip(EstadoDTO::setCohorteList);
                  m.skip(EstadoDTO::setEntrevistaList);
                  m.skip(EstadoDTO::setPagoList);
                  m.skip(EstadoDTO::setSemestreList);
              });

        // Aspirante → listas bidireccionales
        mapper.createTypeMap(AspiranteEntity.class, AspiranteDTO.class)
              .addMappings(m -> {
                  m.skip(AspiranteDTO::setCalificacioncriterioList);
                  m.skip(AspiranteDTO::setDocumentoList);
                  m.skip(AspiranteDTO::setEntrevistaList);
                  m.skip(AspiranteDTO::setAdmitidoList);
                  m.skip(AspiranteDTO::setPagoList);
              });

        // Administrativo ↔ Documento
        mapper.createTypeMap(AdministrativoEntity.class, AdministrativoDTO.class)
              .addMappings(m -> m.skip(AdministrativoDTO::setDocumentoList));

        // Administrativo DTO -> Entity (avoid deep persona mapping)
        mapper.emptyTypeMap(AdministrativoDTO.class, AdministrativoEntity.class)
              .addMappings(m -> {
                  m.skip(AdministrativoEntity::setDocumentoList);
                  m.skip(AdministrativoEntity::setCargo);
                  m.skip(AdministrativoEntity::setEstado);
                  m.skip(AdministrativoEntity::setPersona);
              })
              .implicitMappings();

        // Ubicacion ↔ Entrevista
        mapper.createTypeMap(UbicacionEntity.class, UbicacionDTO.class)
              .addMappings(m -> m.skip(UbicacionDTO::setEntrevistaList));

        // Ubicacion DTO -> Entity (avoid mapping list relations)
        mapper.emptyTypeMap(UbicacionDTO.class, UbicacionEntity.class)
              .addMappings(m -> m.skip(UbicacionEntity::setMunicipio))
              .implicitMappings();

        // Semestre DTO -> Entity (avoid mapping cohorte list)
        mapper.emptyTypeMap(SemestreDTO.class, SemestreEntity.class)
              .addMappings(m -> {
                  m.skip(SemestreEntity::setCohorteList);
                  m.skip(SemestreEntity::setEstado);
              })
              .implicitMappings();

        return mapper;
    }
}
