package cl.duoc.notificacionEv.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cl.duoc.notificacionEv.model.Notificacion;
import cl.duoc.notificacionEv.repository.NotificacionRepository;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    public List<Notificacion> getAllNotificaciones() {
        return notificacionRepository.findAll();
    }

    public Notificacion getById(Integer id) {
        return notificacionRepository.findById(id).orElse(null);
    }

    public Notificacion guardarNotificacion(Notificacion notificacion) {
        notificacion.setLeida(false);

        if (notificacion.getFechaEnvio() == null) {
            notificacion.setFechaEnvio(LocalDateTime.now());
        }

        return notificacionRepository.save(notificacion);
    }

    public Notificacion updateNotificacion(Notificacion notificacion) {
        return notificacionRepository.save(notificacion);
    }

    public String deleteNotificacion(int id) {
        notificacionRepository.deleteById(id);
        return "Notificación eliminada del sistema!";
    }

    public int totalNotificaciones() {
        return notificacionRepository.totalNotificaciones();
    }

    public List<Notificacion> obtenerPorUsuario(int idUsuario) {
        return notificacionRepository.selectPorUsuario(idUsuario);
    }

    public List<Notificacion> obtenerPorTipo(String tipo) {
        return notificacionRepository.selectPorTipo(tipo);
    }

    public List<Notificacion> obtenerPorLeida(boolean leida) {
        return notificacionRepository.selectPorLeida(leida);
    }

    public Notificacion marcarComoLeida(int id) {
        Notificacion notificacion = notificacionRepository.findById(id).orElse(null);

        if (notificacion == null) {
            return null;
        }

        notificacion.setLeida(true);

        return notificacionRepository.save(notificacion);
    }

    public Notificacion marcarComoNoLeida(int id) {
        Notificacion notificacion = notificacionRepository.findById(id).orElse(null);

        if (notificacion == null) {
            return null;
        }

        notificacion.setLeida(false);

        return notificacionRepository.save(notificacion);
    }
}