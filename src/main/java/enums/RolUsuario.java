package enums;

public enum RolUsuario {
    CLIENTE("cliente"),
    ADMINISTRADOR("administrador");
    
    private final String valor;
    
    RolUsuario(String valor) {
        this.valor = valor;
    }
    
    public String getValor() {
        return valor;
    }
    
  
    public static RolUsuario fromString(String valor) {
        for (RolUsuario rol : RolUsuario.values()) {
            if (rol.valor.equalsIgnoreCase(valor)) {
                return rol;
            }
        }
        return null;
    }
    
    
    public static boolean esRolValido(String valor) {
        return fromString(valor) != null;
    }
    
    @Override
    public String toString() {
        return valor;
    }
}