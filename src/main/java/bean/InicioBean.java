/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bean;

import dto.UsuarioDTO;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.Serializable;


@Named
@ViewScoped
public class InicioBean implements Serializable {

    private UsuarioDTO usuarioLogueado;

    @PostConstruct
    public void init() {
        usuarioLogueado = (UsuarioDTO) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("usuario");

        if (usuarioLogueado == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext()
                        .redirect("login.xhtml");
            } catch (IOException e) {
                System.err.println("Error al redireccionar al login: " + e.getMessage());
            }
        }
    }

    public UsuarioDTO getUsuarioLogueado() {
        return usuarioLogueado;
    }
}