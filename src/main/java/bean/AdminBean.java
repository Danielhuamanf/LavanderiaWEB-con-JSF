/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;
import static util.NavigationController.*;

@Named
@SessionScoped
public class AdminBean implements Serializable {
     public String irDashboard() {
        return ADMIN_DASHBOARD + "?faces-redirect=true"; // Define ADMIN_DASHBOARD en tu NavigationController
    }

    public String irGestionClientes() {
        return CLIENTE_GESTION + "?faces-redirect=true"; // Define CLIENTE_GESTION en NavigationController
    }

    public String irGestionServicios() {
        return SERVICIO_GESTION + "?faces-redirect=true"; // Define SERVICIO_GESTION en NavigationController
    }
    public String cerrarSesion() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return LOGIN_PAGE+"?faces-redirect=true";
    }
    public String irGestionPedidos() {
        return PEDIDO_GESTION + "?faces-redirect=true"; // Define SERVICIO_GESTION en NavigationController
    }

    
}
