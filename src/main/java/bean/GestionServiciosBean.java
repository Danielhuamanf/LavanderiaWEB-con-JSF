
package bean;

import dao.ServicioDAO;
import dao.impl.ServicioDAOImpl;
import dto.ServicioDTO;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Named
@ViewScoped
public class GestionServiciosBean implements Serializable {
    private ServicioDAO servicioDAO;
    private List<ServicioDTO> servicios;
    private List<ServicioDTO> serviciosFiltrados;
    private ServicioDTO servicioSeleccionado;
    private ServicioDTO nuevoServicio;
    private boolean mostrarFormulario;
    private String terminoBusqueda;

    @PostConstruct
    public void init() {
        servicioDAO = new ServicioDAOImpl();
        cargarServicios();
        nuevoServicio = new ServicioDTO();
        nuevoServicio.setActivo(true); // Por defecto activo al crear
    }

    public void cargarServicios() {
        servicios = servicioDAO.obtenerTodosServicios();
    }

    public void prepararNuevoServicio() {
        nuevoServicio = new ServicioDTO();
        nuevoServicio.setActivo(true);
        mostrarFormulario = true;
    }

    public void prepararEditarServicio(ServicioDTO servicioDTO) {
        servicioSeleccionado = servicioDAO.obtenerServicioPorId(servicioDTO.getIdServicio());
        mostrarFormulario = true;
    }

            // Métodos adicionales que necesitarás en tu bean
        public void prepararVerDetalles(ServicioDTO servicio) {
            this.servicioSeleccionado = servicioDAO.obtenerServicioPorId(servicio.getIdServicio());
        }

        public void prepararEliminarServicio(ServicioDTO servicio) {
            this.servicioSeleccionado = servicio;
        }

        public boolean activarServicio() {
            if (servicioSeleccionado != null) {
                servicioSeleccionado.setActivo(true);
                boolean resultado = servicioDAO.actualizarServicio(servicioSeleccionado);
                FacesMessage mensaje = new FacesMessage(
                    resultado ? "Servicio activado exitosamente" : "Error al activar servicio");
                FacesContext.getCurrentInstance().addMessage(null, mensaje);
                cargarServicios();
                return resultado;
            }
            return false;
        }

    public void guardarServicio() {
        boolean resultado;
        FacesMessage mensaje;

        if (servicioSeleccionado == null) {
            // Crear nuevo servicio
            resultado = servicioDAO.crearServicio(nuevoServicio);
            mensaje = new FacesMessage(resultado ? "Servicio creado exitosamente" : "Error al crear servicio");
        } else {
            // Actualizar servicio existente
            resultado = servicioDAO.actualizarServicio(servicioSeleccionado);
            mensaje = new FacesMessage(resultado ? "Servicio actualizado exitosamente" : "Error al actualizar servicio");
        }

        FacesContext.getCurrentInstance().addMessage(null, mensaje);
        
        if (resultado) {
            cargarServicios();
            mostrarFormulario = false;
            servicioSeleccionado = null;
        }
    }

    public void desactivarServicio() {
        if (servicioSeleccionado != null) {
            boolean resultado = servicioDAO.desactivarServicio(servicioSeleccionado.getIdServicio());
            FacesMessage mensaje = new FacesMessage(
                resultado ? "Servicio desactivado exitosamente" : "Error al desactivar servicio");
            
            FacesContext.getCurrentInstance().addMessage(null, mensaje);
            
            if (resultado) {
                cargarServicios();
                servicioSeleccionado = null;
            }
        }
    }

    public void buscarServicios() {
        if (terminoBusqueda == null || terminoBusqueda.trim().isEmpty()) {
            cargarServicios();
        } else {
            servicios = servicioDAO.obtenerTodosServicios().stream()
                .filter(s -> s.getNombreServicio().toLowerCase().contains(terminoBusqueda.toLowerCase()) ||
                            s.getDescripcion().toLowerCase().contains(terminoBusqueda.toLowerCase()))
                .toList();
        }
    }

    public void cancelarEdicion() {
        mostrarFormulario = false;
        servicioSeleccionado = null;
    }

    // Getters y Setters
    public List<ServicioDTO> getServicios() {
        return servicios;
    }

    public List<ServicioDTO> getServiciosFiltrados() {
        return serviciosFiltrados;
    }

    public void setServiciosFiltrados(List<ServicioDTO> serviciosFiltrados) {
        this.serviciosFiltrados = serviciosFiltrados;
    }

public ServicioDTO getServicioSeleccionado() {
    if (servicioSeleccionado == null) {
        servicioSeleccionado = new ServicioDTO();
    }
    return servicioSeleccionado;
}

    public void setServicioSeleccionado(ServicioDTO servicioSeleccionado) {
        this.servicioSeleccionado = servicioSeleccionado;
    }

public ServicioDTO getNuevoServicio() {
    if (nuevoServicio == null) {
        nuevoServicio = new ServicioDTO();
        nuevoServicio.setActivo(true);
    }
    return nuevoServicio;
}
    public void setNuevoServicio(ServicioDTO nuevoServicio) {
        this.nuevoServicio = nuevoServicio;
    }

    public boolean isMostrarFormulario() {
        return mostrarFormulario;
    }

    public String getTerminoBusqueda() {
        return terminoBusqueda;
    }

    public void setTerminoBusqueda(String terminoBusqueda) {
        this.terminoBusqueda = terminoBusqueda;
    }
}