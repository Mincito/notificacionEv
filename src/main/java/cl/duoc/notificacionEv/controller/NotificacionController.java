package cl.duoc.notificacionEv.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import jakarta.validation.Valid;
import cl.duoc.notificacionEv.dto.CreateNotificacionRequest;
import cl.duoc.notificacionEv.dto.UpdateNotificacionRequest;
import cl.duoc.notificacionEv.mapper.NotificacionMapper;
import cl.duoc.notificacionEv.model.Notificacion;
import cl.duoc.notificacionEv.service.NotificacionService;

@RestController
@RequestMapping("/api/v1/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @GetMapping
    public ResponseEntity<List<Notificacion>> getAllNotificaciones() {
        List<Notificacion> notificaciones = notificacionService.getAllNotificaciones();
        return ResponseEntity.ok(notificaciones);
    }

    @PostMapping
    public ResponseEntity<Notificacion> guardarNotificacion(
            @Valid @RequestBody CreateNotificacionRequest request) {

        Notificacion notificacion = NotificacionMapper.toModel(request);
        Notificacion notificacionGuardada = notificacionService.guardarNotificacion(notificacion);

        return ResponseEntity.status(HttpStatus.CREATED).body(notificacionGuardada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notificacion> getById(@PathVariable Integer id) {
        Notificacion notificacion = notificacionService.getById(id);

        if (notificacion == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(notificacion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notificacion> updateNotificacion(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateNotificacionRequest request) {

        Notificacion notificacionExistente = notificacionService.getById(id);

        if (notificacionExistente == null) {
            return ResponseEntity.notFound().build();
        }

        Notificacion notificacion = NotificacionMapper.toModel(id, request);
        Notificacion notificacionActualizada = notificacionService.updateNotificacion(notificacion);

        return ResponseEntity.ok(notificacionActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotificacion(@PathVariable int id) {
        Notificacion notificacion = notificacionService.getById(id);

        if (notificacion == null) {
            return ResponseEntity.notFound().build();
        }

        notificacionService.deleteNotificacion(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/total")
    public ResponseEntity<Integer> totalNotificaciones() {
        int total = notificacionService.totalNotificaciones();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Notificacion>> obtenerPorUsuario(@PathVariable int idUsuario) {
        List<Notificacion> notificaciones = notificacionService.obtenerPorUsuario(idUsuario);
        return ResponseEntity.ok(notificaciones);
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Notificacion>> obtenerPorTipo(@PathVariable String tipo) {
        List<Notificacion> notificaciones = notificacionService.obtenerPorTipo(tipo);
        return ResponseEntity.ok(notificaciones);
    }

    @GetMapping("/leida/{leida}")
    public ResponseEntity<List<Notificacion>> obtenerPorLeida(@PathVariable boolean leida) {
        List<Notificacion> notificaciones = notificacionService.obtenerPorLeida(leida);
        return ResponseEntity.ok(notificaciones);
    }

    @PutMapping("/{id}/leer")
    public ResponseEntity<Notificacion> marcarComoLeida(@PathVariable int id) {
        Notificacion notificacion = notificacionService.marcarComoLeida(id);

        if (notificacion == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(notificacion);
    }

    @PutMapping("/{id}/no-leida")
    public ResponseEntity<Notificacion> marcarComoNoLeida(@PathVariable int id) {
        Notificacion notificacion = notificacionService.marcarComoNoLeida(id);

        if (notificacion == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(notificacion);
    }
}