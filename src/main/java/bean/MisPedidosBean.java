package bean;

import dao.PedidoDAO;
import dao.impl.PedidoDAOImpl;
import dto.PedidoDTO;
import dto.UsuarioDTO;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import static util.NavigationController.CLIENTE_INICIO;
import static util.NavigationController.LOGIN_PAGE;

@Named
@ViewScoped
public class MisPedidosBean implements Serializable {
    
    private List<PedidoDTO> pedidosActivos;
    private List<PedidoDTO> pedidosCancelados;
    private UsuarioDTO usuarioLogueado;

    public MisPedidosBean() {
        cargarPedidos();
    }
    
 
    private void cargarPedidos() {
        usuarioLogueado = (UsuarioDTO) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("usuario");
        PedidoDAO pedidoDAO = new PedidoDAOImpl();
 
        pedidosActivos = pedidoDAO.obtenerPedidosPorUsuarioYEstados(
            usuarioLogueado.getIdUsuario(), 
            Arrays.asList("Pendiente", "En proceso", "Listo para entrega", "Entregado")
        );

        pedidosCancelados = pedidoDAO.obtenerPedidosPorUsuarioYEstados(
            usuarioLogueado.getIdUsuario(), 
            Arrays.asList("Cancelado")
        );
    }
    

    public String obtenerClaseBadge(String estado) {
        if (estado == null) return "badge bg-secondary";
        
        switch (estado.toLowerCase()) {
            case "pendiente":
                return "badge bg-secondary";
            case "en proceso":
                return "badge bg-primary";
            case "listo para entrega":
                return "badge bg-warning";
            case "entregado":
                return "badge bg-success";
            case "cancelado":
                return "badge bg-danger";
            default:
                return "badge bg-secondary";
        }
    }
    
   
    public String obtenerTextoBadge(String estado) {
        if (estado == null) return "Sin estado";
        
        switch (estado.toLowerCase()) {
            case "pendiente":
                return "Pendiente";
            case "en proceso":
                return "En Proceso";
            case "listo para entrega":
                return "Listo para Entrega";
            case "entregado":
                return "Entregado";
            case "cancelado":
                return "Cancelado";
            default:
                return estado;
        }
    }
    
    
     public String irInicio() {
        return CLIENTE_INICIO+"?faces-redirect=true";
    }
    
    
    public String cerrarSesion() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return LOGIN_PAGE+"?faces-redirect=true";
    }
    
    
    public void refrescarPedidos() {
        cargarPedidos();
    }
    
    // Getters y Setters
    public List<PedidoDTO> getPedidosActivos() {
        return pedidosActivos;
    }
    
    public void setPedidosActivos(List<PedidoDTO> pedidosActivos) {
        this.pedidosActivos = pedidosActivos;
    }
    
    public List<PedidoDTO> getPedidosCancelados() {
        return pedidosCancelados;
    }
    
    public void setPedidosCancelados(List<PedidoDTO> pedidosCancelados) {
        this.pedidosCancelados = pedidosCancelados;
    }
    
    public UsuarioDTO getUsuarioLogueado() {
        return usuarioLogueado;
    }
    
    public void setUsuarioLogueado(UsuarioDTO usuarioLogueado) {
        this.usuarioLogueado = usuarioLogueado;
    }
}
