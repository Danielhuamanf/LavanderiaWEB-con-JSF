package dao;

import dto.PedidoDTO;
import java.util.List;

public interface PedidoDAO {

    boolean eliminarPedido(int idPedido);
    
    boolean crearPedido(PedidoDTO pedido);
      
    List<PedidoDTO> obtenerPedidosPorUsuarioYEstados(int idUsuario, List<String> estados);
    
    List<PedidoDTO> obtenerTodosPedidos();
    
    boolean actualizarEstadoPedido(int idPedido, String nuevoEstado);
    
    boolean actualizarPedido(PedidoDTO pedido);
}