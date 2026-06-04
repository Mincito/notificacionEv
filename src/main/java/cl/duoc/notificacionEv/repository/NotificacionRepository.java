package cl.duoc.notificacionEv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cl.duoc.notificacionEv.model.Notificacion;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {

    @Query(value = "SELECT * FROM tabla_notificaciones WHERE id_usuario = :idUsuario", nativeQuery = true)
    List<Notificacion> selectPorUsuario(@Param("idUsuario") int idUsuario);

    @Query(value = "SELECT * FROM tabla_notificaciones WHERE tipo = :tipo", nativeQuery = true)
    List<Notificacion> selectPorTipo(@Param("tipo") String tipo);

    @Query(value = "SELECT * FROM tabla_notificaciones WHERE leida = :leida", nativeQuery = true)
    List<Notificacion> selectPorLeida(@Param("leida") boolean leida);

    default int totalNotificaciones() {
        return (int) this.count();
    }
}