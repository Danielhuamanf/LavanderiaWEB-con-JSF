package dao;

import dto.UsuarioDTO;
import java.util.List;

/**
 * Interfaz DAO para operaciones de usuario
 */
public interface UsuarioDAO {
    
    boolean registrarUsuario(UsuarioDTO usuario);

    UsuarioDTO buscarPorCorreo(String correo);
 
    UsuarioDTO buscarPorId(int id);

    boolean existeCorreo(String correo);
    
    List<UsuarioDTO> obtenerUsuariosPorRol(String rol);
    
    boolean actualizarUsuario(UsuarioDTO usuario);
    
    boolean eliminarUsuario(int id);
    
}