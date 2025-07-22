package util;

import dto.UsuarioDTO;
import enums.RolUsuario;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;


import java.io.IOException;

/**
 * Controlador para manejar la navegación y sesiones del usuario
 */
public class NavigationController {
    
    public static final String LOGIN_PAGE = "/login.xhtml";
    public static final String REGISTRO_PAGE = "/registro.xhtml";
    public static final String CLIENTE_INICIO = "/inicio.xhtml";
    public static final String ADMIN_DASHBOARD = "/gestionPedidosAdmin.xhtml";
    public static final String ERROR_PAGE = "/error.xhtml";
    public static final String CLIENTE_GESTION = "/gestionClientes.xhtml";
    public static final String SERVICIO_GESTION = "/gestionServicios.xhtml";    
    public static final String PEDIDO_GESTION = "/gestionPedidosAdmin.xhtml"; 
    public static final String USUARIO_SESSION = "usuarioLogueado";
    public static final String ROL_SESSION = "rolUsuario";
    
    
    public static String redirigirSegunRol(UsuarioDTO usuario) {
        if (usuario == null) {
            return LOGIN_PAGE;
        }
        
      
        switch (usuario.getRol().toLowerCase()) {
            case "cliente":
                return CLIENTE_INICIO + "?faces-redirect=true";
            case "administrador":
                return ADMIN_DASHBOARD + "?faces-redirect=true";
            default:
                
                return LOGIN_PAGE;
        }
    }
   
    
    public static void cerrarSesion() {
        HttpSession session = obtenerSesion();
        session.invalidate();
    }
    
   
    public static String logout() {
        cerrarSesion();
        return LOGIN_PAGE + "?faces-redirect=true";
    }
    
    
    private static HttpSession obtenerSesion() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
    }
   
    
   
}
