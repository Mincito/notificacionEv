package cl.duoc.notificacionEv.mapper;

import cl.duoc.notificacionEv.dto.CreateNotificacionRequest;
import cl.duoc.notificacionEv.dto.UpdateNotificacionRequest;
import cl.duoc.notificacionEv.model.Notificacion;


public class NotificacionMapper {

    public static Notificacion toModel(CreateNotificacionRequest request) {
        return new Notificacion(
                0, // ID temporal, será asignado por el service/repository
                request.idUsuario(),
                request.mensaje(),
                request.tipo(),
                null,
                false
        );
    }

    public static Notificacion toModel(int id, UpdateNotificacionRequest request) {
        return new Notificacion(
                id, // ID del path parameter
                request.idUsuario(),
                request.mensaje(),
                request.tipo(),
                request.fechaEnvio(),
                request.leida()
        );
    }
}