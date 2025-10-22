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
            nuevoDetalle.pedido = pedidoExistente;
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

    @Transactional
    public Pedido agregarDetalle(Long pedidoId, DetallePedido nuevoDetalle) {
        Pedido pedidoExistente = obtenerPorId(pedidoId);

        nuevoDetalle.pedido = pedidoExistente;
        pedidoExistente.detalles.add(nuevoDetalle);

        pedidoExistente.persist();
        return pedidoExistente;
    }

    @Transactional
    public Pedido actualizarDetalle(Long pedidoId, Long detalleId, DetallePedido detalleActualizado) {
        Pedido pedidoExistente = obtenerPorId(pedidoId);

        DetallePedido detalleExistente = pedidoExistente.detalles.stream()
                .filter(d -> d.id.equals(detalleId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Detalle de pedido no encontrado con id " + detalleId));

        detalleExistente.cantidad = detalleActualizado.cantidad;
        detalleExistente.precioUnitario = detalleActualizado.precioUnitario;

        pedidoExistente.persist();
        return pedidoExistente;
    }

    @Transactional
    public Pedido eliminarDetalle(Long pedidoId, Long detalleId) {
        Pedido pedidoExistente = obtenerPorId(pedidoId);

        boolean eliminado = pedidoExistente.detalles.removeIf(d -> d.id.equals(detalleId));

        if (!eliminado) {
            throw new NotFoundException("Detalle de pedido no encontrado con id " + detalleId);
        }

        pedidoExistente.persist();
        return pedidoExistente;
    }
}