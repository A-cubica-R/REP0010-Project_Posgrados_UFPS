package ufps.edu.co.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ufps.edu.co.persistence.entities.AdministrativoEntity;
import ufps.edu.co.persistence.entities.AspiranteEntity;
import ufps.edu.co.persistence.entities.CohorteEntity;
import ufps.edu.co.persistence.entities.PersonaEntity;
import ufps.edu.co.persistence.entities.PruebaEntity;
import ufps.edu.co.persistence.entities.ResultadopruebaEntity;
import ufps.edu.co.persistence.entities.UbicacionEntity;
import ufps.edu.co.rest.dto.AdministrativoDTO;
import ufps.edu.co.rest.dto.AspiranteDTO;
import ufps.edu.co.rest.dto.CohorteDTO;
import ufps.edu.co.rest.dto.PersonaDTO;
import ufps.edu.co.rest.dto.PruebaDTO;
import ufps.edu.co.rest.dto.ResultadopruebaDTO;
import ufps.edu.co.rest.dto.UbicacionDTO;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        // Persona ↔ Aspirante / Administrativo / Usuario
        mapper.createTypeMap(PersonaEntity.class, PersonaDTO.class)
              .addMappings(m -> {
                  m.skip(PersonaDTO::setAspiranteList);
                  m.skip(PersonaDTO::setAdministrativoList);
                  m.skip(PersonaDTO::setUsuarioList);
              });

        // Cohorte ↔ Aspirante / ListaAdmitidos / Prueba / Criterio
        mapper.createTypeMap(CohorteEntity.class, CohorteDTO.class)
              .addMappings(m -> {
                  m.skip(CohorteDTO::setAspiranteList);
                  m.skip(CohorteDTO::setListaadmitidosList);
                  m.skip(CohorteDTO::setCriterioaceptacionList);
                  m.skip(CohorteDTO::setPruebaList);
              });

        // Aspirante → listas bidireccionales
        mapper.createTypeMap(AspiranteEntity.class, AspiranteDTO.class)
              .addMappings(m -> {
                  m.skip(AspiranteDTO::setCalificacioncriterioList);
                  m.skip(AspiranteDTO::setDocumentoList);
                  m.skip(AspiranteDTO::setEntrevistaList);
                  m.skip(AspiranteDTO::setListaadmitidosList);
                  m.skip(AspiranteDTO::setPagoList);
                  m.skip(AspiranteDTO::setResultadopruebaList);
              });

        // Administrativo ↔ Documento
        mapper.createTypeMap(AdministrativoEntity.class, AdministrativoDTO.class)
              .addMappings(m -> m.skip(AdministrativoDTO::setDocumentoList));

        // Ubicacion ↔ Entrevista
        mapper.createTypeMap(UbicacionEntity.class, UbicacionDTO.class)
              .addMappings(m -> m.skip(UbicacionDTO::setEntrevistaList));

        // Prueba ↔ Resultadoprueba
        mapper.createTypeMap(PruebaEntity.class, PruebaDTO.class)
              .addMappings(m -> m.skip(PruebaDTO::setResultadopruebaList));
        mapper.createTypeMap(ResultadopruebaEntity.class, ResultadopruebaDTO.class)
              .addMappings(m -> m.skip(ResultadopruebaDTO::setPrueba));

        return mapper;
    }
}
