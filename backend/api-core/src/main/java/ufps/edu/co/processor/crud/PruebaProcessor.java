package ufps.edu.co.processor.crud;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufps.edu.co.domain.exceptions.DomainException;
import ufps.edu.co.domain.exceptions.errorcodes.PruebaErrorCode;
import ufps.edu.co.maps.specific.PruebaMap;
import ufps.edu.co.records.input.entity.PruebaInput.*;
import ufps.edu.co.records.output.entity.PruebaOutput;
import ufps.edu.co.rest.dto.AspiranteDTO;
import ufps.edu.co.rest.dto.PruebaDTO;
import ufps.edu.co.rest.dto.UbicacionDTO;
import ufps.edu.co.rest.services.AspiranteService;
import ufps.edu.co.rest.services.PruebaService;
import ufps.edu.co.rest.services.UbicacionService;
import ufps.edu.co.services.EmailService;
import ufps.edu.co.usecase.GlobalUseCase;

@Service
public class PruebaProcessor implements
        GlobalUseCase<PRUEBA_CREATE, PRUEBA_UPDATE, PRUEBA_DELETE, PRUEBA_PATCH, PRUEBA_FIND, PruebaOutput> {

    private static final Logger logger = LoggerFactory.getLogger(PruebaProcessor.class);

    @Autowired private PruebaService service;
    @Autowired private AspiranteService aspiranteService;
    @Autowired private UbicacionService ubicacionService;
    @Autowired private PruebaMap map;
    @Autowired private EmailService emailService;

    @Override
    public PruebaOutput create(PRUEBA_CREATE input) {
        UbicacionDTO ubicacion = ubicacionService.create(UbicacionDTO.builder().direccion(input.ubicacion()).zonaurbana(true).build());
        PruebaDTO dto = map.toDto(input);
        dto.setIdUbicacion(ubicacion.getId());
        PruebaDTO created = service.create(dto);
        PruebaOutput output = map.toOutput(created);
        notifyPruebaCreada(created.getId(), input.idAspirante());
        return output;
    }

    @Override
    public PruebaOutput update(PRUEBA_UPDATE input) {
        try {
            UbicacionDTO ubicacion = ubicacionService.create(UbicacionDTO.builder().direccion(input.ubicacion()).zonaurbana(true).build());
            PruebaDTO dto = map.toDto(input);
            dto.setIdUbicacion(ubicacion.getId());
            return map.toOutput(service.update(input.id(), dto));
        } catch (Exception e) {
            throw new DomainException(PruebaErrorCode.PRUEBA_NOT_FOUND, input.id());
        }
    }

    @Override
    public PruebaOutput patch(PRUEBA_PATCH input) {
        throw new UnsupportedOperationException("Patch operation is not supported for Prueba");
    }

    @Override
    public PruebaOutput findById(PRUEBA_FIND input) {
        try {
            return map.toOutput(service.findById(input.id()));
        } catch (Exception e) {
            throw new DomainException(PruebaErrorCode.PRUEBA_NOT_FOUND, input.id());
        }
    }

    @Override
    public List<PruebaOutput> findAll() {
        return service.findAll().stream().map(map::toOutput).toList();
    }

    @Override
    public void deleteById(PRUEBA_DELETE input) {
        PruebaDTO prueba;
        try {
            prueba = service.findById(input.id());
        } catch (Exception e) {
            throw new DomainException(PruebaErrorCode.PRUEBA_NOT_FOUND, input.id());
        }
        try {
            service.deleteById(input.id());
        } catch (Exception e) {
            throw new DomainException(PruebaErrorCode.PRUEBA_NOT_FOUND, input.id());
        }
        notifyPruebaEliminada(prueba);
    }

    private void notifyPruebaCreada(Integer idPrueba, Integer idAspirante) {
        try {
            PruebaDTO prueba = service.findById(idPrueba);
            AspiranteDTO aspirante = aspiranteService.findById(idAspirante);
            if (aspirante == null || aspirante.getPersona() == null) return;

            String nombre = aspirante.getPersona().getNombres();
            String correoAspirante = aspirante.getPersona().getCorreo();
            String nombrePrueba = prueba.getNombre() != null ? prueba.getNombre() : "No definido";
            String descripcion = prueba.getDescripcion() != null ? prueba.getDescripcion() : "Sin descripción";
            String lugar = prueba.getUbicacion() != null ? prueba.getUbicacion().getDireccion() : "No definido";

            String html = "<p>Hola <strong>" + nombre + "</strong>,</p>"
                    + "<p>Se ha programado una prueba para tu proceso de admisión en los Posgrados de la UFPS.</p>"
                    + "<table style=\"border-collapse:collapse;font-family:Arial,sans-serif;\">"
                    + "<tr><td style=\"padding:4px 12px 4px 0\"><strong>Prueba:</strong></td><td>" + nombrePrueba + "</td></tr>"
                    + "<tr><td style=\"padding:4px 12px 4px 0\"><strong>Descripción:</strong></td><td>" + descripcion + "</td></tr>"
                    + "<tr><td style=\"padding:4px 12px 4px 0\"><strong>Lugar:</strong></td><td>" + lugar + "</td></tr>"
                    + "</table>"
                    + "<p>Por favor asegúrate de asistir puntualmente.</p>";

            emailService.sendEmail(correoAspirante, "Prueba Programada - Posgrados UFPS", html);
        } catch (Exception e) {
            logger.warn("No se pudo notificar prueba creada al aspirante {}: {}", idAspirante, e.getMessage());
        }
    }

    private void notifyPruebaEliminada(PruebaDTO prueba) {
        try {
            AspiranteDTO aspirante = aspiranteService.findById(prueba.getIdAspirante());
            if (aspirante == null || aspirante.getPersona() == null) return;

            String nombre = aspirante.getPersona().getNombres();
            String correoAspirante = aspirante.getPersona().getCorreo();
            String nombrePrueba = prueba.getNombre() != null ? prueba.getNombre() : "No definido";

            String html = "<p>Hola <strong>" + nombre + "</strong>,</p>"
                    + "<p>Te informamos que la prueba <strong>" + nombrePrueba
                    + "</strong> programada para tu proceso de admisión ha sido cancelada.</p>"
                    + "<p>Pronto te contactaremos con más detalles sobre los próximos pasos en tu proceso de admisión.</p>";

            emailService.sendEmail(correoAspirante, "Prueba Cancelada - Posgrados UFPS", html);
        } catch (Exception e) {
            logger.warn("No se pudo notificar prueba eliminada para prueba {}: {}", prueba.getId(), e.getMessage());
        }
    }
}
