package py.edu.ucom.gustodivino.service;

import py.edu.ucom.gustodivino.domain.Producto;
import py.edu.ucom.gustodivino.repository.ProductoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.util.List;

@ApplicationScoped
public class ProductoService {

    @Inject
    ProductoRepository productoRepository;

    public List<Producto> listar() {
        return productoRepository.listAll();
    }

    public Producto obtenerPorId(Long id) {
        return productoRepository.findByIdOptional(id).orElseThrow(() -> new NotFoundException("Producto no encontrado"));
    }

    @Transactional
    public Producto crear(Producto producto) {
        productoRepository.persist(producto);
        return producto;
    }

    @Transactional
    public Producto actualizar(Long id, Producto productoActualizado) {
        Producto productoExistente = obtenerPorId(id);
        productoExistente.nombre = productoActualizado.nombre;
        productoExistente.descripcion = productoActualizado.descripcion;
        productoExistente.precio = productoActualizado.precio;
        productoExistente.categoria = productoActualizado.categoria;
        productoExistente.disponible = productoActualizado.disponible;
        return productoExistente;
    }

    @Transactional
    public void eliminar(Long id) {
        if (!productoRepository.deleteById(id)) {
            throw new NotFoundException("Producto no encontrado para eliminar");
        }
    }
}