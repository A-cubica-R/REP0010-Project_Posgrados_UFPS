package ufps.edu.co.processor.crud;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufps.edu.co.domain.exceptions.DomainException;
import ufps.edu.co.domain.exceptions.errorcodes.PruebaErrorCode;
import ufps.edu.co.maps.specific.PruebaMap;
import ufps.edu.co.records.input.entity.AspiranteInput.ASPIRANTE_FIND;
import ufps.edu.co.records.input.entity.PruebaInput.*;
import ufps.edu.co.records.output.entity.PruebaOutput;
import ufps.edu.co.records.output.entity.PruebaResumenOutput;
import ufps.edu.co.records.output.entity.PruebaSimpleOutput;
import ufps.edu.co.rest.dto.AspiranteDTO;
import ufps.edu.co.rest.dto.EstadoDTO;
import ufps.edu.co.rest.dto.PruebaDTO;
import ufps.edu.co.rest.dto.TipopruebaDTO;
import ufps.edu.co.rest.dto.UbicacionDTO;
import ufps.edu.co.rest.services.AspiranteService;
import ufps.edu.co.rest.services.EstadoService;
import ufps.edu.co.rest.services.PruebaService;
import ufps.edu.co.rest.services.TipopruebaService;
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
    @Autowired private TipopruebaService tipopruebaService;
    @Autowired private EstadoService estadoService;
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

    public List<PruebaResumenOutput> findByIdAspirante(ASPIRANTE_FIND input) {
        try {
            return service.findByIdAspirante(input.id()).stream().map(dto -> {
                String estadoNombre = dto.getEstado() != null ? dto.getEstado().getTipo() : null;
                String tipoNombre = dto.getTipoprueba() != null ? dto.getTipoprueba().getTipo() : null;
                String direccion = dto.getUbicacion() != null ? dto.getUbicacion().getDireccion() : null;
                return PruebaResumenOutput.builder()
                        .id(dto.getId())
                        .nombre(dto.getNombre())
                        .descripcion(dto.getDescripcion())
                        .fecha(dto.getFecha())
                        .tiempo(dto.getTiempo())
                        .idEstado(dto.getIdEstado())
                        .estado(estadoNombre)
                        .idTipoprueba(dto.getIdTipoprueba())
                        .tipoprueba(tipoNombre)
                        .ubicacion(direccion)
                        .motivocambio(dto.getMotivocambio())
                        .build();
            }).toList();
        } catch (Exception e) {
            throw new RuntimeException("Error finding Pruebas by Aspirante ID: " + e.getMessage(), e);
        }
    }

    public PruebaResumenOutput crearPrueba(Integer idAspirante, PRUEBA_CREAR_REQUEST request) {
        try {
            TipopruebaDTO tipoprueba = tipopruebaService.findById(request.idTipoprueba());
            if (tipoprueba == null) {
                throw new RuntimeException("Tipo de prueba no encontrado: " + request.idTipoprueba());
            }
            EstadoDTO estadoInicial = estadoService.findByTipoAndEntidad("PENDIENTE DE CONFIRMACION", "prueba");
            if (estadoInicial == null) {
                throw new RuntimeException("Estado 'PENDIENTE DE CONFIRMACION' no encontrado para entidad 'prueba'");
            }
            AspiranteDTO aspirante = aspiranteService.findById(idAspirante);
            UbicacionDTO ubicacion = ubicacionService.create(
                    UbicacionDTO.builder().direccion(request.ubicacion()).zonaurbana(true).build());
            PruebaDTO dto = PruebaDTO.builder()
                    .nombre(request.nombre())
                    .descripcion(request.descripcion())
                    .fecha(request.fecha())
                    .tiempo(request.tiempo())
                    .idAspirante(idAspirante)
                    .idCohorte(aspirante.getIdCohorte())
                    .idUbicacion(ubicacion.getId())
                    .idEstado(estadoInicial.getId())
                    .idTipoprueba(tipoprueba.getId())
                    .build();
            PruebaDTO created = service.create(dto);
            notifyPruebaCreada(created.getId(), idAspirante);
            return toPruebaResumen(service.findById(created.getId()));
        } catch (Exception e) {
            throw new RuntimeException("Error creating Prueba: " + e.getMessage(), e);
        }
    }

    public PruebaResumenOutput reagendarPrueba(Integer idPrueba, PRUEBA_REAGENDAR_REQUEST request) {
        try {
            TipopruebaDTO tipoprueba = tipopruebaService.findById(request.idTipoprueba());
            if (tipoprueba == null) {
                throw new RuntimeException("Tipo de prueba no encontrado: " + request.idTipoprueba());
            }
            UbicacionDTO ubicacion = ubicacionService.create(
                    UbicacionDTO.builder().direccion(request.ubicacion()).zonaurbana(true).build());
            PruebaDTO updated = service.reschedule(idPrueba, request.fecha(), request.tiempo(),
                    tipoprueba.getId(), ubicacion.getId());
            return toPruebaResumen(service.findById(updated.getId()));
        } catch (IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error rescheduling Prueba: " + e.getMessage(), e);
        }
    }

    public PruebaResumenOutput editPrueba(Integer idPrueba, PRUEBA_EDITAR_REQUEST request) {
        try {
            Integer idTipoprueba = null;
            if (request.idTipoprueba() != null) {
                TipopruebaDTO tipoprueba = tipopruebaService.findById(request.idTipoprueba());
                if (tipoprueba == null) {
                    throw new RuntimeException("Tipo de prueba no encontrado: " + request.idTipoprueba());
                }
                idTipoprueba = tipoprueba.getId();
            }
            Integer idUbicacion = null;
            if (request.ubicacion() != null) {
                UbicacionDTO ubicacion = ubicacionService.create(
                        UbicacionDTO.builder().direccion(request.ubicacion()).zonaurbana(true).build());
                idUbicacion = ubicacion.getId();
            }
            PruebaDTO updated = service.edit(idPrueba, request.nombre(), request.descripcion(),
                    request.fecha(), request.tiempo(), idTipoprueba, idUbicacion);
            return toPruebaResumen(updated);
        } catch (Exception e) {
            throw new RuntimeException("Error editing Prueba: " + e.getMessage(), e);
        }
    }

    public PruebaSimpleOutput completarPrueba(Integer idPrueba) {
        try {
            EstadoDTO estadoCompletada = estadoService.findByTipoAndEntidad("COMPLETADA", "prueba");
            if (estadoCompletada == null) {
                throw new RuntimeException("Estado 'COMPLETADA' no encontrado para entidad 'prueba'");
            }
            PruebaDTO updated = service.changeEstado(idPrueba, estadoCompletada.getId(), "CONFIRMADA");
            return toPruebaSimple(service.findById(updated.getId()));
        } catch (Exception e) {
            throw new RuntimeException("Error completing Prueba: " + e.getMessage(), e);
        }
    }

    public PruebaSimpleOutput confirmarPrueba(Integer idPrueba) {
        try {
            EstadoDTO estadoConfirmada = estadoService.findByTipoAndEntidad("CONFIRMADA", "prueba");
            if (estadoConfirmada == null) {
                throw new RuntimeException("Estado 'CONFIRMADA' no encontrado para entidad 'prueba'");
            }
            PruebaDTO updated = service.changeEstado(idPrueba, estadoConfirmada.getId(), "PENDIENTE DE CONFIRMACION");
            return toPruebaSimple(service.findById(updated.getId()));
        } catch (Exception e) {
            throw new RuntimeException("Error confirming Prueba: " + e.getMessage(), e);
        }
    }

    public PruebaSimpleOutput solicitarCambioPrueba(Integer idPrueba, String motivocambio) {
        try {
            EstadoDTO estadoSolicitud = estadoService.findByTipoAndEntidad("SOLICITUD DE CAMBIO", "prueba");
            if (estadoSolicitud == null) {
                throw new RuntimeException("Estado 'SOLICITUD DE CAMBIO' no encontrado para entidad 'prueba'");
            }
            PruebaDTO updated = service.changeEstadoWithMotivo(idPrueba, estadoSolicitud.getId(), "PENDIENTE DE CONFIRMACION", motivocambio);
            return toPruebaSimple(service.findById(updated.getId()));
        } catch (Exception e) {
            throw new RuntimeException("Error requesting change for Prueba: " + e.getMessage(), e);
        }
    }

    public PruebaSimpleOutput cancelarPrueba(Integer idPrueba, String motivocambio) {
        try {
            EstadoDTO estadoCancelada = estadoService.findByTipoAndEntidad("CANCELADA", "prueba");
            if (estadoCancelada == null) {
                throw new RuntimeException("Estado 'CANCELADA' no encontrado para entidad 'prueba'");
            }
            PruebaDTO updated = service.changeEstadoWithMotivo(idPrueba, estadoCancelada.getId(), "CONFIRMADA", motivocambio);
            return toPruebaSimple(service.findById(updated.getId()));
        } catch (Exception e) {
            throw new RuntimeException("Error cancelling Prueba: " + e.getMessage(), e);
        }
    }

    private PruebaResumenOutput toPruebaResumen(PruebaDTO dto) {
        String estadoNombre = dto.getEstado() != null ? dto.getEstado().getTipo() : null;
        String tipoNombre = dto.getTipoprueba() != null ? dto.getTipoprueba().getTipo() : null;
        String direccion = dto.getUbicacion() != null ? dto.getUbicacion().getDireccion() : null;
        return PruebaResumenOutput.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .fecha(dto.getFecha())
                .tiempo(dto.getTiempo())
                .idEstado(dto.getIdEstado())
                .estado(estadoNombre)
                .idTipoprueba(dto.getIdTipoprueba())
                .tipoprueba(tipoNombre)
                .ubicacion(direccion)
                .motivocambio(dto.getMotivocambio())
                .build();
    }

    private PruebaSimpleOutput toPruebaSimple(PruebaDTO dto) {
        String estadoNombre = dto.getEstado() != null ? dto.getEstado().getTipo() : null;
        return PruebaSimpleOutput.builder()
                .idPrueba(dto.getId())
                .idAspirante(dto.getIdAspirante())
                .nombre(dto.getNombre())
                .estado(estadoNombre)
                .motivocambio(dto.getMotivocambio())
                .build();
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
