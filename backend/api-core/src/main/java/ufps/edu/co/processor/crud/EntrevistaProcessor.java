package ufps.edu.co.processor.crud;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ufps.edu.co.maps.specific.EntrevistaMap;
import ufps.edu.co.records.input.entity.AspiranteInput.ASPIRANTE_FIND;
import ufps.edu.co.records.input.entity.EntrevistaInput.*;
import ufps.edu.co.records.output.entity.EntrevistaOutput;
import ufps.edu.co.records.output.entity.EntrevistaResumenOutput;
import ufps.edu.co.rest.dto.AspiranteDTO;
import ufps.edu.co.rest.dto.EntrevistaDTO;
import ufps.edu.co.rest.dto.EstadoDTO;
import ufps.edu.co.rest.dto.TipoentrevistaDTO;
import ufps.edu.co.rest.dto.UbicacionDTO;
import ufps.edu.co.rest.services.AspiranteService;
import ufps.edu.co.rest.services.EntrevistaService;
import ufps.edu.co.rest.services.EstadoService;
import ufps.edu.co.rest.services.TipoentrevistaService;
import ufps.edu.co.rest.services.UbicacionService;
import ufps.edu.co.services.EmailService;
import ufps.edu.co.usecase.GlobalUseCase;

@Service
public class EntrevistaProcessor implements
        GlobalUseCase<ENTREVISTA_CREATE, ENTREVISTA_UPDATE, ENTREVISTA_DELETE, ENTREVISTA_PATCH, ENTREVISTA_FIND, EntrevistaOutput> {

    private static final Logger logger = LoggerFactory.getLogger(EntrevistaProcessor.class);

    @Autowired
    private EntrevistaService service;

    @Autowired
    private AspiranteService aspiranteService;

    @Autowired
    private UbicacionService ubicacionService;

    @Autowired
    private TipoentrevistaService tipoentrevistaService;

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private EntrevistaMap map;

    @Autowired
    private EmailService emailService;

    @Override
    public EntrevistaOutput create(ENTREVISTA_CREATE input) {
        try {
            TipoentrevistaDTO tipoentrevista = tipoentrevistaService.findById(input.idTipoentrevista());
            if (tipoentrevista == null) {
                throw new RuntimeException("Modalidad no encontrada: " + input.idTipoentrevista());
            }
            EstadoDTO estadoInicial = estadoService.findByTipoAndEntidad("PENDIENTE DE CONFIRMACION", "entrevista");
            if (estadoInicial == null) {
                throw new RuntimeException("Estado inicial 'PENDIENTE DE CONFIRMACION' no encontrado para entidad 'entrevista'");
            }
            UbicacionDTO ubicacion = ubicacionService.create(
                    UbicacionDTO.builder().direccion(input.ubicacion()).zonaurbana(true).build());
            EntrevistaDTO dto = map.toDto(input);
            dto.setIdTipoentrevista(tipoentrevista.getId());
            dto.setIdEstado(estadoInicial.getId());
            dto.setIdUbicacion(ubicacion.getId());
            EntrevistaDTO created = service.create(dto);
            EntrevistaOutput output = map.toOutput(created);
            notifyEntrevistaCreada(created.getId(), input.idAspirante());
            return output;
        } catch (Exception e) {
            throw new RuntimeException("Error creating Entrevista: " + e.getMessage(), e);
        }
    }

    @Override
    public EntrevistaOutput update(ENTREVISTA_UPDATE input) {
        try {
            UbicacionDTO ubicacion = ubicacionService.create(UbicacionDTO.builder().direccion(input.ubicacion()).zonaurbana(true).build());
            EntrevistaDTO dto = map.toDto(input);
            dto.setIdUbicacion(ubicacion.getId());
            EntrevistaDTO updated = service.update(input.id(), dto);
            return map.toOutput(updated);
        } catch (Exception e) {
            throw new RuntimeException("Error updating Entrevista: " + e.getMessage(), e);
        }
    }

    @Override
    public EntrevistaOutput patch(ENTREVISTA_PATCH input) {
        throw new UnsupportedOperationException("Patch operation is not supported for Entrevista");
    }

    @Override
    @Transactional(readOnly = true)
    public EntrevistaOutput findById(ENTREVISTA_FIND input) {
        try {
            EntrevistaDTO dto = service.findById(input.id());
            return map.toOutput(dto);
        } catch (Exception e) {
            throw new RuntimeException("Error finding Entrevista by ID: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<EntrevistaOutput> findAll() {
        try {
            return service.findAll().stream().map(map::toOutput).toList();
        } catch (Exception e) {
            throw new RuntimeException("Error finding all Entrevistas: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(ENTREVISTA_DELETE input) {
        try {
            EntrevistaDTO entrevista = service.findById(input.id());
            service.deleteById(input.id());
            notifyEntrevistaEliminada(entrevista);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting Entrevista by ID: " + e.getMessage(), e);
        }
    }

    public EntrevistaOutput completeInterview(ENTREVISTA_FIND input) {
        try {
            EstadoDTO estadoCompletada = estadoService.findByTipoAndEntidad("COMPLETADA", "entrevista");
            if (estadoCompletada == null) {
                throw new RuntimeException("Estado 'COMPLETADA' no encontrado para entidad 'entrevista'");
            }
            EntrevistaDTO updated = service.changeEstado(input.id(), estadoCompletada.getId(), "CONFIRMADA");

            EstadoDTO estadoEntrevistado = estadoService.findByTipoAndEntidad("ENTREVISTADO", "aspirante");
            if (estadoEntrevistado == null) {
                throw new RuntimeException("Estado 'ENTREVISTADO' no encontrado para entidad 'aspirante'");
            }
            aspiranteService.updateEstado(updated.getIdAspirante(), estadoEntrevistado.getId());

            return map.toOutput(updated);
        } catch (Exception e) {
            throw new RuntimeException("Error completing Entrevista: " + e.getMessage(), e);
        }
    }

    public EntrevistaOutput cancelInterview(ENTREVISTA_FIND input) {
        return cancelInterview(input.id(), null);
    }

    public EntrevistaOutput cancelInterview(Integer id, String motivocambio) {
        try {
            EstadoDTO estadoCancelada = estadoService.findByTipoAndEntidad("CANCELADA", "entrevista");
            if (estadoCancelada == null) {
                throw new RuntimeException("Estado 'CANCELADA' no encontrado para entidad 'entrevista'");
            }
            EntrevistaDTO updated = service.changeEstadoWithMotivo(id, estadoCancelada.getId(), "CONFIRMADA", motivocambio);
            return map.toOutput(updated);
        } catch (Exception e) {
            throw new RuntimeException("Error cancelling Entrevista: " + e.getMessage(), e);
        }
    }

    public EntrevistaOutput confirmInterview(ENTREVISTA_FIND input) {
        try {
            EstadoDTO estadoConfirmada = estadoService.findByTipoAndEntidad("CONFIRMADA", "entrevista");
            if (estadoConfirmada == null) {
                throw new RuntimeException("Estado 'CONFIRMADA' no encontrado para entidad 'entrevista'");
            }
            EntrevistaDTO updated = service.changeEstado(input.id(), estadoConfirmada.getId(), "PENDIENTE DE CONFIRMACION");
            return map.toOutput(updated);
        } catch (Exception e) {
            throw new RuntimeException("Error confirming Entrevista: " + e.getMessage(), e);
        }
    }

    public EntrevistaOutput requestChangeInterview(Integer id, String motivocambio) {
        try {
            EntrevistaDTO updated = service.requestChange(id, motivocambio);
            return map.toOutput(updated);
        } catch (Exception e) {
            throw new RuntimeException("Error requesting change for Entrevista: " + e.getMessage(), e);
        }
    }

    public EntrevistaOutput reschedule(ENTREVISTA_RESCHEDULE input) {
        try {
            TipoentrevistaDTO tipoentrevista = tipoentrevistaService.findById(input.idTipoentrevista());
            if (tipoentrevista == null) {
                throw new RuntimeException("Modalidad no encontrada: " + input.idTipoentrevista());
            }
            UbicacionDTO ubicacion = ubicacionService.create(
                    UbicacionDTO.builder().direccion(input.ubicacion()).zonaurbana(true).build());
            EntrevistaDTO updated = service.reschedule(
                    input.id(), input.fecha(), input.tiempo(),
                    tipoentrevista.getId(), ubicacion.getId(), input.motivocambio());
            return map.toOutput(updated);
        } catch (Exception e) {
            throw new RuntimeException("Error rescheduling Entrevista: " + e.getMessage(), e);
        }
    }

    public List<EntrevistaResumenOutput> findByIdAspirante(ASPIRANTE_FIND input) {
        try {
            return service.findByIdAspirante(input.id()).stream().map(dto -> {
                String direccion = dto.getUbicacion() != null ? dto.getUbicacion().getDireccion() : null;
                String estadoNombre = dto.getEstado() != null ? dto.getEstado().getTipo() : null;
                String tipoNombre = dto.getTipoentrevista() != null ? dto.getTipoentrevista().getTipo() : null;
                return EntrevistaResumenOutput.builder()
                        .id(dto.getId())
                        .fecha(dto.getFecha())
                        .tiempo(dto.getTiempo())
                        .idEstado(dto.getIdEstado())
                        .estado(estadoNombre)
                        .idTipoentrevista(dto.getIdTipoentrevista())
                        .tipoentrevista(tipoNombre)
                        .ubicacion(direccion)
                        .motivocambio(dto.getMotivocambio())
                        .build();
            }).toList();
        } catch (Exception e) {
            throw new RuntimeException("Error finding Entrevistas by Aspirante ID: " + e.getMessage(), e);
        }
    }

    public EntrevistaOutput rateInterview(ENTREVISTA_RATE input) {
        try {
            // TODO: Actualmente el método rateInterview no asigna la calificación a la entrevista. Habría que modificar el servicio para que lo haga, y luego este método funcionaría correctamente.
            EntrevistaDTO updated = service.rateInterview(input.id(), input.calificacion());
            return map.toOutput(updated);
        } catch (Exception e) {
            throw new RuntimeException("Error rating Entrevista: " + e.getMessage(), e);
        }
    }

    private void notifyEntrevistaCreada(Integer idEntrevista, Integer idAspirante) {
        try {
            EntrevistaDTO entrevista = service.findById(idEntrevista);
            AspiranteDTO aspirante = aspiranteService.findById(idAspirante);
            if (aspirante == null || aspirante.getPersona() == null) return;

            String nombre = aspirante.getPersona().getNombres();
            String correoAspirante = aspirante.getPersona().getCorreo();
            String fecha = entrevista.getFecha() != null ? entrevista.getFecha().toString() : "No definida";
            String hora = entrevista.getTiempo() != null ? entrevista.getTiempo().toString() : "No definida";
            String tipo = entrevista.getTipoentrevista() != null ? entrevista.getTipoentrevista().getTipo() : "No definido";
            String lugar = entrevista.getUbicacion() != null ? entrevista.getUbicacion().getDireccion() : "No definido";

            String html = "<p>Hola <strong>" + nombre + "</strong>,</p>"
                    + "<p>Se ha programado una entrevista para tu proceso de admisión en los Posgrados de la UFPS.</p>"
                    + "<table style=\"border-collapse:collapse;font-family:Arial,sans-serif;\">"
                    + "<tr><td style=\"padding:4px 12px 4px 0\"><strong>Fecha:</strong></td><td>" + fecha + "</td></tr>"
                    + "<tr><td style=\"padding:4px 12px 4px 0\"><strong>Hora:</strong></td><td>" + hora + "</td></tr>"
                    + "<tr><td style=\"padding:4px 12px 4px 0\"><strong>Tipo:</strong></td><td>" + tipo + "</td></tr>"
                    + "<tr><td style=\"padding:4px 12px 4px 0\"><strong>Lugar:</strong></td><td>" + lugar + "</td></tr>"
                    + "</table>"
                    + "<p>Por favor asegúrate de asistir puntualmente.</p>";

            emailService.sendEmail(correoAspirante, "Entrevista Programada - Posgrados UFPS", html);
        } catch (Exception e) {
            logger.warn("No se pudo notificar entrevista creada al aspirante {}: {}", idAspirante, e.getMessage());
        }
    }

    private void notifyEntrevistaEliminada(EntrevistaDTO entrevista) {
        try {
            AspiranteDTO aspirante = aspiranteService.findById(entrevista.getIdAspirante());
            if (aspirante == null || aspirante.getPersona() == null) return;

            String nombre = aspirante.getPersona().getNombres();
            String correoAspirante = aspirante.getPersona().getCorreo();
            String fecha = entrevista.getFecha() != null ? entrevista.getFecha().toString() : "No definida";
            String hora = entrevista.getTiempo() != null ? entrevista.getTiempo().toString() : "No definida";

            String html = "<p>Hola <strong>" + nombre + "</strong>,</p>"
                    + "<p>Te informamos que tu entrevista programada para el <strong>" + fecha
                    + "</strong> a las <strong>" + hora + "</strong> ha sido cancelada.</p>"
                    + "<p>Pronto te contactaremos con más detalles sobre los próximos pasos en tu proceso de admisión.</p>";

            emailService.sendEmail(correoAspirante, "Entrevista Cancelada - Posgrados UFPS", html);
        } catch (Exception e) {
            logger.warn("No se pudo notificar entrevista eliminada para entrevista {}: {}", entrevista.getId(),
                    e.getMessage());
        }
    }
}
