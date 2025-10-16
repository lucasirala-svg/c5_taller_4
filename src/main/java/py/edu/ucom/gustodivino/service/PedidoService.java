package py.edu.ucom.gustodivino.service;

import py.edu.ucom.gustodivino.domain.DetallePedido;
import py.edu.ucom.gustodivino.domain.Pedido;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.util.List;

@ApplicationScoped
public class PedidoService {

    public List<Pedido> listar() {
        return Pedido.listAll();
    }

    public Pedido obtenerPorId(Long id) {
        return (Pedido) Pedido.findByIdOptional(id).orElseThrow(() -> new NotFoundException("Pedido no encontrado"));
    }

    @Transactional
    public Pedido crear(Pedido pedido) {
        for (DetallePedido detalle : pedido.detalles) {
            detalle.pedido = pedido;
        }
        pedido.persist();
        return pedido;
    }

    public Pedido actualizar(Long id, Pedido pedidoActualizado) {
        Pedido pedidoExistente = obtenerPorId(id);

        pedidoExistente.estado = pedidoActualizado.estado;
        pedidoExistente.tipoPedido = pedidoActualizado.tipoPedido;
        pedidoExistente.total = pedidoActualizado.total;

        pedidoExistente.detalles.clear();

        for (DetallePedido nuevoDetalle : pedidoActualizado.detalles) {
            nuevoDetalle.pedido = pedidoExistente; // Volvemos a enlazar el detalle con su pedido padre
            pedidoExistente.detalles.add(nuevoDetalle);
        }

        pedidoExistente.persist();
        return pedidoExistente;
    }

    @Transactional
    public void eliminar(Long id) {
        Pedido pedidoExistente = obtenerPorId(id);
        pedidoExistente.delete();
    }
}