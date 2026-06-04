package cl.duoc.notificacionEv.dto;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record UpdateNotificacionRequest(

    @Positive(message = "El ID del usuario debe ser mayor a 0")
    int idUsuario,

    @NotBlank(message = "El mensaje no puede estar vacío")
    String mensaje,

    @NotBlank(message = "El tipo no puede estar vacío")
    String tipo,

    LocalDateTime fechaEnvio,

    boolean leida

) {
}