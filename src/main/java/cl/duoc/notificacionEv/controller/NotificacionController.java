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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
    name = "Notificaciones",
    description = "Operaciones relacionadas con la gestión de notificaciones"
)
@RestController
@RequestMapping("/api/v1/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @Operation(
        summary = "Listar notificaciones",
        description = "Obtiene todas las notificaciones registradas en el sistema"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Lista de notificaciones obtenida correctamente"
    )
    @GetMapping
    public ResponseEntity<List<Notificacion>> getAllNotificaciones() {
        List<Notificacion> notificaciones = notificacionService.getAllNotificaciones();
        return ResponseEntity.ok(notificaciones);
    }

    @Operation(
        summary = "Crear notificación",
        description = "Registra una nueva notificación asociada a un usuario"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Notificación creada correctamente"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Los datos enviados no son válidos"
        )
    })
    @PostMapping
    public ResponseEntity<Notificacion> guardarNotificacion(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos necesarios para registrar una notificación",
                required = true,
                content = @Content(
                    schema = @Schema(implementation = CreateNotificacionRequest.class),
                    examples = @ExampleObject(
                        name = "Ejemplo de notificación",
                        value = """
                        {
                          "idUsuario": 2,
                          "mensaje": "Tu oferta fue registrada correctamente",
                          "tipo": "OFERTA"
                        }
                        """
                    )
                )
            )
            @Valid @RequestBody CreateNotificacionRequest request) {

        Notificacion notificacion = NotificacionMapper.toModel(request);
        Notificacion notificacionGuardada =
                notificacionService.guardarNotificacion(notificacion);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(notificacionGuardada);
    }

    @Operation(
        summary = "Buscar notificación por ID",
        description = "Obtiene una notificación según su identificador"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Notificación encontrada"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Notificación no encontrada"
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Notificacion> getById(@PathVariable Integer id) {
        Notificacion notificacion = notificacionService.getById(id);

        if (notificacion == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(notificacion);
    }

    @Operation(
        summary = "Actualizar notificación",
        description = "Modifica los datos de una notificación existente"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Notificación actualizada correctamente"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Los datos enviados no son válidos"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Notificación no encontrada"
        )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Notificacion> updateNotificacion(
            @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos actualizados de la notificación",
                required = true,
                content = @Content(
                    schema = @Schema(implementation = UpdateNotificacionRequest.class),
                    examples = @ExampleObject(
                        name = "Ejemplo de actualización",
                        value = """
                        {
                          "idUsuario": 2,
                          "mensaje": "Tu oferta fue superada",
                          "tipo": "OFERTA",
                          "fechaEnvio": "2026-07-03T13:00:00",
                          "leida": false
                        }
                        """
                    )
                )
            )
            @Valid @RequestBody UpdateNotificacionRequest request) {

        Notificacion notificacionExistente =
                notificacionService.getById(id);

        if (notificacionExistente == null) {
            return ResponseEntity.notFound().build();
        }

        Notificacion notificacion =
                NotificacionMapper.toModel(id, request);

        Notificacion notificacionActualizada =
                notificacionService.updateNotificacion(notificacion);

        return ResponseEntity.ok(notificacionActualizada);
    }

    @Operation(
        summary = "Eliminar notificación",
        description = "Elimina una notificación según su identificador"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Notificación eliminada correctamente"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Notificación no encontrada"
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotificacion(@PathVariable int id) {
        Notificacion notificacion = notificacionService.getById(id);

        if (notificacion == null) {
            return ResponseEntity.notFound().build();
        }

        notificacionService.deleteNotificacion(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Obtener total de notificaciones",
        description = "Devuelve la cantidad total de notificaciones registradas"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Total de notificaciones obtenido correctamente"
    )
    @GetMapping("/total")
    public ResponseEntity<Integer> totalNotificaciones() {
        int total = notificacionService.totalNotificaciones();
        return ResponseEntity.ok(total);
    }

    @Operation(
        summary = "Buscar notificaciones por usuario",
        description = "Obtiene todas las notificaciones asociadas a un usuario"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Notificaciones obtenidas correctamente"
    )
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Notificacion>> obtenerPorUsuario(
            @PathVariable int idUsuario) {

        List<Notificacion> notificaciones =
                notificacionService.obtenerPorUsuario(idUsuario);

        return ResponseEntity.ok(notificaciones);
    }

    @Operation(
        summary = "Buscar notificaciones por tipo",
        description = "Obtiene las notificaciones que tengan el tipo indicado"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Notificaciones obtenidas correctamente"
    )
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Notificacion>> obtenerPorTipo(
            @PathVariable String tipo) {

        List<Notificacion> notificaciones =
                notificacionService.obtenerPorTipo(tipo);

        return ResponseEntity.ok(notificaciones);
    }

    @Operation(
        summary = "Buscar notificaciones por estado de lectura",
        description = "Obtiene las notificaciones según estén leídas o no leídas"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Notificaciones obtenidas correctamente"
    )
    @GetMapping("/leida/{leida}")
    public ResponseEntity<List<Notificacion>> obtenerPorLeida(
            @PathVariable boolean leida) {

        List<Notificacion> notificaciones =
                notificacionService.obtenerPorLeida(leida);

        return ResponseEntity.ok(notificaciones);
    }

    @Operation(
        summary = "Marcar notificación como leída",
        description = "Cambia el estado de una notificación a leída"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Notificación marcada como leída"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Notificación no encontrada"
        )
    })
    @PutMapping("/{id}/leer")
    public ResponseEntity<Notificacion> marcarComoLeida(
            @PathVariable int id) {

        Notificacion notificacion =
                notificacionService.marcarComoLeida(id);

        if (notificacion == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(notificacion);
    }

    @Operation(
        summary = "Marcar notificación como no leída",
        description = "Cambia el estado de una notificación a no leída"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Notificación marcada como no leída"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Notificación no encontrada"
        )
    })
    @PutMapping("/{id}/no-leida")
    public ResponseEntity<Notificacion> marcarComoNoLeida(
            @PathVariable int id) {

        Notificacion notificacion =
                notificacionService.marcarComoNoLeida(id);

        if (notificacion == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(notificacion);
    }
}