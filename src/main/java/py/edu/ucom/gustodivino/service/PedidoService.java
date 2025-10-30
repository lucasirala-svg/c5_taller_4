package py.edu.ucom.gustodivino.service;

import py.edu.ucom.gustodivino.domain.DetallePedido;
import py.edu.ucom.gustodivino.domain.Mesa;
import py.edu.ucom.gustodivino.domain.Pedido;
import py.edu.ucom.gustodivino.domain.Producto;
import py.edu.ucom.gustodivino.repository.ClienteRepository;
import py.edu.ucom.gustodivino.repository.DetallePedidoRepository;
import py.edu.ucom.gustodivino.repository.PedidoRepository;
import py.edu.ucom.gustodivino.repository.ProductoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class PedidoService {

    @Inject
    PedidoRepository pedidoRepository;

    @Inject
    DetallePedidoRepository detallePedidoRepository;

    @Inject
    ProductoRepository productoRepository;

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    MesaService mesaService;

    public List<Pedido> listar() {
        return pedidoRepository.listAll();
    }

    public Pedido obtenerPorId(Long id) {
        return pedidoRepository.findByIdOptional(id).orElseThrow(() -> new NotFoundException("Pedido no encontrado"));
    }

    @Transactional
    public Pedido crear(Pedido pedido) {
        clienteRepository.findByIdOptional(pedido.cliente.id).orElseThrow(() -> new NotFoundException("El cliente especificado no existe."));
        Mesa mesa = mesaService.obtenerPorId(pedido.mesa.id);

        if (!"disponible".equalsIgnoreCase(mesa.estado)) {
            throw new BadRequestException("La mesa " + mesa.numeroMesa + " no está disponible.");
        }

        double totalCalculado = 0.0;
        if (pedido.detalles != null && !pedido.detalles.isEmpty()) {
            for (DetallePedido detalle : pedido.detalles) {
                Producto producto = productoRepository.findByIdOptional(detalle.producto.id)
                        .orElseThrow(() -> new NotFoundException("Producto con ID " + detalle.producto.id + " no encontrado."));
                detalle.precioUnitario = producto.precio;
                totalCalculado += detalle.precioUnitario * detalle.cantidad;
                detalle.pedido = pedido; // Enlazar detalle con el pedido
            }
        }

        pedido.total = totalCalculado;
        pedido.estado = "Pendiente";
        pedido.createdAt = LocalDateTime.now();

        pedidoRepository.persist(pedido);

        mesaService.actualizarEstado(mesa.id, "Ocupada");

        return pedido;
    }

    @Transactional
    public Pedido actualizar(Long id, Pedido pedidoActualizado) {
        Pedido pedidoExistente = obtenerPorId(id);
        pedidoExistente.estado = pedidoActualizado.estado;
        pedidoExistente.tipoPedido = pedidoActualizado.tipoPedido;
        return pedidoExistente;
    }

    @Transactional
    public void eliminar(Long id) {
        if (!pedidoRepository.deleteById(id)) {
            throw new NotFoundException("Pedido no encontrado para eliminar");
        }
    }

    @Transactional
    public Pedido agregarDetalle(Long pedidoId, DetallePedido nuevoDetalle) {
        Pedido pedido = obtenerPorId(pedidoId);

        Producto producto = productoRepository.findByIdOptional(nuevoDetalle.producto.id)
                .orElseThrow(() -> new NotFoundException("Producto con ID " + nuevoDetalle.producto.id + " no encontrado."));

        nuevoDetalle.producto = producto;
        nuevoDetalle.precioUnitario = producto.precio;
        nuevoDetalle.pedido = pedido; // Muy importante: enlazar con el pedido

        pedido.detalles.add(nuevoDetalle);

        recalcularTotalPedido(pedido);

        return pedido;
    }

    @Transactional
    public Pedido actualizarDetalle(Long pedidoId, Long detalleId, DetallePedido detalleActualizado) {
        Pedido pedido = obtenerPorId(pedidoId);

        DetallePedido detalleExistente = pedido.detalles.stream()
                .filter(d -> d.id.equals(detalleId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Detalle con ID " + detalleId + " no encontrado en el pedido " + pedidoId));

        detalleExistente.cantidad = detalleActualizado.cantidad;

        recalcularTotalPedido(pedido);

        return pedido;
    }

    @Transactional
    public Pedido eliminarDetalle(Long pedidoId, Long detalleId) {
        Pedido pedido = obtenerPorId(pedidoId);

        DetallePedido detalleParaEliminar = detallePedidoRepository.findByIdOptional(detalleId)
                .orElseThrow(() -> new NotFoundException("Detalle con ID " + detalleId + " no encontrado."));

        if (!pedido.detalles.remove(detalleParaEliminar)) {
            throw new NotFoundException("El detalle no pertenecía a este pedido.");
        }

        recalcularTotalPedido(pedido);

        return pedido;
    }

    private void recalcularTotalPedido(Pedido pedido) {
        pedido.total = pedido.detalles.stream()
                .mapToDouble(detalle -> detalle.precioUnitario * detalle.cantidad)
                .sum();
    }
}

