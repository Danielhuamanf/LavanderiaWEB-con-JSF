package bean;

import dao.UsuarioDAO;
import dao.impl.UsuarioDAOImpl;
import dto.UsuarioDTO;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


@Named
@ViewScoped
public class ClienteBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private static final String ROL_CLIENTE = "cliente";
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$");
    
    // Servicios
    private UsuarioDAO usuarioDAO;
    
    // Propiedades para la tabla
    private List<UsuarioDTO> clientes;
    private List<UsuarioDTO> clientesFiltrados;
    private List<UsuarioDTO> clientesSeleccionados;
    
    // Propiedades para el formulario
    private UsuarioDTO clienteSeleccionado;
    private String confirmarContrasena;
    private boolean modoEdicion;
    private boolean dialogoVisible;
    
    // Constructor
    public ClienteBean() {
        this.usuarioDAO = new UsuarioDAOImpl();
        this.clientes = new ArrayList<>();
        this.clientesFiltrados = new ArrayList<>();
        this.clientesSeleccionados = new ArrayList<>();
        this.clienteSeleccionado = new UsuarioDTO();
        
    }
    
    @PostConstruct
    public void init() {
        cargarClientes();
    }
    
    // Métodos de carga de datos
    public void cargarClientes() {
        try {
            this.clientes = usuarioDAO.obtenerUsuariosPorRol(ROL_CLIENTE);
            this.clientesFiltrados = new ArrayList<>(this.clientes);
            mostrarMensaje("Clientes cargados correctamente", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            mostrarMensaje("Error al cargar clientes: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
            e.printStackTrace();
        }
    }
    
    // Métodos para el CRUD
    public void prepararNuevoCliente() {
        this.clienteSeleccionado = new UsuarioDTO();
        this.clienteSeleccionado.setRol(ROL_CLIENTE);
        this.confirmarContrasena = "";
        this.modoEdicion = false;
        this.dialogoVisible = true;
    }
    
    public void prepararEdicionCliente() {
        if (this.clienteSeleccionado != null) {
            // Crear una copia para evitar modificar el objeto original hasta guardar
            UsuarioDTO cliente = this.clienteSeleccionado;
            this.clienteSeleccionado = new UsuarioDTO();
            this.clienteSeleccionado.setIdUsuario(cliente.getIdUsuario());
            this.clienteSeleccionado.setNombre(cliente.getNombre());
            this.clienteSeleccionado.setCorreo(cliente.getCorreo());
            this.clienteSeleccionado.setTelefono(cliente.getTelefono());
            this.clienteSeleccionado.setDireccion(cliente.getDireccion());
            this.clienteSeleccionado.setRol(cliente.getRol());
            
            this.confirmarContrasena = "";
            this.modoEdicion = true;
            this.dialogoVisible = true;
        }
    }
    
    public void guardarCliente() {
        
        
        try {
            if (modoEdicion) {
                actualizarCliente();
            } else {
                crearNuevoCliente();
            }
        } catch (Exception e) {
            mostrarMensaje("Error inesperado: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
            e.printStackTrace();
        }
    }
    
    private void crearNuevoCliente() {
        try {
            // Verificar que el correo no exista
            if (usuarioDAO.existeCorreo(clienteSeleccionado.getCorreo())) {
                mostrarMensaje("El correo electrónico ya está registrado", FacesMessage.SEVERITY_WARN);
                return;
            }
            
            // Encriptar contraseña
            String contrasenaEncriptada = (clienteSeleccionado.getContrasena());
            clienteSeleccionado.setContrasena(contrasenaEncriptada);
            
            boolean resultado = usuarioDAO.registrarUsuario(clienteSeleccionado);
            
            if (resultado) {
                mostrarMensaje("Cliente creado exitosamente", FacesMessage.SEVERITY_INFO);
                cargarClientes();
                cerrarDialogo();
            } else {
                mostrarMensaje("Error al crear el cliente", FacesMessage.SEVERITY_ERROR);
            }
            
        } catch (Exception e) {
            mostrarMensaje("Error al crear cliente: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
            e.printStackTrace();
        }
    }
    
    private void actualizarCliente() {
        try {
            boolean resultado = usuarioDAO.actualizarUsuario(clienteSeleccionado);
            
            if (resultado) {
                mostrarMensaje("Cliente actualizado exitosamente", FacesMessage.SEVERITY_INFO);
                cargarClientes();
                cerrarDialogo();
            } else {
                mostrarMensaje("Error al actualizar el cliente", FacesMessage.SEVERITY_ERROR);
            }
            
        } catch (Exception e) {
            mostrarMensaje("Error al actualizar cliente: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
            e.printStackTrace();
        }
    }
    
    public void eliminarCliente() {
        if (clienteSeleccionado != null) {
            try {
                boolean resultado = usuarioDAO.eliminarUsuario(clienteSeleccionado.getIdUsuario());
                
                if (resultado) {
                    mostrarMensaje("Cliente eliminado exitosamente", FacesMessage.SEVERITY_INFO);
                    cargarClientes();
                } else {
                    mostrarMensaje("Error al eliminar el cliente", FacesMessage.SEVERITY_ERROR);
                }
                
            } catch (Exception e) {
                mostrarMensaje("Error al eliminar cliente: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
                e.printStackTrace();
            }
        }
    }
    
    public void cerrarDialogo() {
        this.dialogoVisible = false;
        this.clienteSeleccionado = new UsuarioDTO();
        this.confirmarContrasena = "";
    }
    
    private void mostrarMensaje(String mensaje, FacesMessage.Severity severidad) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage facesMessage = new FacesMessage(severidad, mensaje, null);
        context.addMessage(null, facesMessage);
    }
    
   
    // Getters y Setters
    public List<UsuarioDTO> getClientes() {
        return clientes;
    }
    
    public void setClientes(List<UsuarioDTO> clientes) {
        this.clientes = clientes;
    }
    
    public List<UsuarioDTO> getClientesFiltrados() {
        return clientesFiltrados;
    }
    
    public void setClientesFiltrados(List<UsuarioDTO> clientesFiltrados) {
        this.clientesFiltrados = clientesFiltrados;
    }
    
    public List<UsuarioDTO> getClientesSeleccionados() {
        return clientesSeleccionados;
    }
    
    public void setClientesSeleccionados(List<UsuarioDTO> clientesSeleccionados) {
        this.clientesSeleccionados = clientesSeleccionados;
    }
    
    public UsuarioDTO getClienteSeleccionado() {
        return clienteSeleccionado;
    }
    
    public void setClienteSeleccionado(UsuarioDTO clienteSeleccionado) {
        this.clienteSeleccionado = clienteSeleccionado;
    }
    
    public String getConfirmarContrasena() {
        return confirmarContrasena;
    }
    
    public void setConfirmarContrasena(String confirmarContrasena) {
        this.confirmarContrasena = confirmarContrasena;
    }
    
    public boolean isModoEdicion() {
        return modoEdicion;
    }
    
    public void setModoEdicion(boolean modoEdicion) {
        this.modoEdicion = modoEdicion;
    }
    
    public boolean isDialogoVisible() {
        return dialogoVisible;
    }
    
    public void setDialogoVisible(boolean dialogoVisible) {
        this.dialogoVisible = dialogoVisible;
    }
}