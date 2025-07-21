package dao;

import dto.ServicioDTO;
import java.util.List;

public interface ServicioDAO {
    
    List<ServicioDTO> obtenerServiciosActivos();
    
    ServicioDTO obtenerServicioPorId(int idServicio);

    List<ServicioDTO> obtenerTodosServicios();

    boolean crearServicio(ServicioDTO servicio);
 
    boolean actualizarServicio(ServicioDTO servicio);
 
    boolean desactivarServicio(int idServicio);
}
