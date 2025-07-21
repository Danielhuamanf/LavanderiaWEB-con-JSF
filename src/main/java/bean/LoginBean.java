package bean;

import dao.UsuarioDAO;
import dao.impl.UsuarioDAOImpl;
import dto.UsuarioDTO;
import enums.RolUsuario;
import util.NavigationController;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String correo;
    private String contrasena;
    private UsuarioDTO usuarioLogueado;
    private UsuarioDAO usuarioDAO;

    public LoginBean() {
        this.usuarioDAO = new UsuarioDAOImpl();
    }

    public String login() {
        try {
            
            // Buscar usuario por correo
            UsuarioDTO usuario = usuarioDAO.buscarPorCorreo(correo.trim());

            if (!contrasena.equals(usuario.getContrasena())) {
                mostrarMensajeError("Usuario o contraseña incorrectos");
                return null;
            }

            this.usuarioLogueado = usuario;

            // GUARDAR USUARIO EN SESIÓN
            FacesContext.getCurrentInstance()
                    .getExternalContext().getSessionMap()
                    .put("usuario", usuarioLogueado);
            NavigationController.mostrarMensajeExito("¡Bienvenido " + usuario.getNombre() + "!");

            limpiarCampos();

            return NavigationController.redirigirSegunRol(usuario);

        } catch (Exception e) {
            mostrarMensajeError("Error interno del sistema. Intente nuevamente.");
            e.printStackTrace();
            return null;
        }
    }

    public String logout() {
        try {
            this.usuarioLogueado = null;
            limpiarCampos();
            NavigationController.mostrarMensajeExito("Sesión cerrada exitosamente");
            return NavigationController.logout();
        } catch (Exception e) {
            mostrarMensajeError("Error al cerrar sesión");
            e.printStackTrace();
            return null;
        }
    }

    public boolean isLogueado() {
        return usuarioLogueado != null || NavigationController.hayUsuarioLogueado();
    }

    public UsuarioDTO getUsuarioLogueado() {
        if (usuarioLogueado == null) {
            usuarioLogueado = NavigationController.obtenerUsuarioDeSesion();
        }
        return usuarioLogueado;
    }

    public boolean isAdministrador() {
        return NavigationController.esAdministrador();
    }

    public boolean isCliente() {
        return NavigationController.esCliente();
    }

    public String getInfoSesion() {
        return NavigationController.getInfoSesion();
    }

    public String irARegistro() {
        return "/registro.xhtml?faces-redirect=true";
    }

    private void limpiarCampos() {
        this.correo = null;
        this.contrasena = null;
    }

    private void mostrarMensajeError(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,mensaje, null));
    }

    // Getters y Setters
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

}
