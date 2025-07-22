package bean;

import dao.UsuarioDAO;
import dao.impl.UsuarioDAOImpl;
import dto.UsuarioDTO;
import enums.RolUsuario;
import util.NavigationController;


import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * Managed Bean para el manejo del registro de usuarios
 */
@Named
@ViewScoped
public class RegistroBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
   
    private String nombre;
    private String correo;
    private String telefono;
    private String direccion;
    private String contrasena;
    private String confirmarContrasena;
    

    private UsuarioDAO usuarioDAO;
    
    public RegistroBean() {
        this.usuarioDAO = new UsuarioDAOImpl();
    }
 
    public String registrar() {
        try {
                         
            if (usuarioDAO.existeCorreo(correo.trim())) {
                mostrarMensajeError("El correo electrónico ya está registrado");
                return null;
            }
            
            if (!contrasena.equals(confirmarContrasena)) {
                mostrarMensajeError("Las contraseñas no coinciden");
                return null;
            }

            UsuarioDTO nuevoUsuario = new UsuarioDTO(
                nombre.trim(),
                correo.trim().toLowerCase(),
                telefono.trim(),
                direccion.trim(),
                contrasena,
                RolUsuario.CLIENTE.getValor()
            );
            
            // Registrar usuario
            if (usuarioDAO.registrarUsuario(nuevoUsuario)) {
                
                limpiarCampos();
                return NavigationController.LOGIN_PAGE + "?faces-redirect=true";
            } else {
                mostrarMensajeError("Error al registrar el usuario. Intente nuevamente.");
                return null;
            }
            
        } catch (Exception e) {
            mostrarMensajeError("Error interno del sistema. Intente nuevamente.");
            e.printStackTrace();
            return null;
        }
    }
    
   
    public String irALogin() {
        return NavigationController.LOGIN_PAGE + "?faces-redirect=true";
    }
    
    /**
     * Limpia todos los campos del formulario
     */
    private void limpiarCampos() {
        this.nombre = null;
        this.correo = null;
        this.telefono = null;
        this.direccion = null;
        this.contrasena = null;
        this.confirmarContrasena = null;
    }
    
    /**
     * Muestra un mensaje de error
     * @param mensaje El mensaje a mostrar
     */
    private void mostrarMensajeError(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, null));
    }
    
    // Getters y Setters
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getCorreo() {
        return correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public String getContrasena() {
        return contrasena;
    }
    
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    public String getConfirmarContrasena() {
        return confirmarContrasena;
    }
    
    public void setConfirmarContrasena(String confirmarContrasena) {
        this.confirmarContrasena = confirmarContrasena;
    }
}
