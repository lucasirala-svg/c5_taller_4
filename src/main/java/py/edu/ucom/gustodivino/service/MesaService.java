package py.edu.ucom.gustodivino.service;

import py.edu.ucom.gustodivino.domain.Mesa;
import py.edu.ucom.gustodivino.repository.MesaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.util.List;

@ApplicationScoped
public class MesaService {

    @Inject
    MesaRepository mesaRepository;

    public List<Mesa> listar() {
        return mesaRepository.listAll();
    }

    public Mesa obtenerPorId(Long id) {
        return mesaRepository.findByIdOptional(id).orElseThrow(() -> new NotFoundException("Mesa no encontrada"));
    }

    @Transactional
    public Mesa crear(Mesa mesa) {
        mesaRepository.persist(mesa);
        return mesa;
    }

    @Transactional
    public Mesa actualizar(Long id, Mesa mesaActualizada) {
        Mesa mesaExistente = obtenerPorId(id);
        mesaExistente.numeroMesa = mesaActualizada.numeroMesa;
        mesaExistente.capacidad = mesaActualizada.capacidad;
        mesaExistente.estado = mesaActualizada.estado;
        return mesaExistente;
    }

    @Transactional
    public void eliminar(Long id) {
        if (!mesaRepository.deleteById(id)) {
            throw new NotFoundException("Mesa no encontrada para eliminar");
        }
    }

    @Transactional
    public void actualizarEstado(Long id, String nuevoEstado) {
        Mesa mesa = obtenerPorId(id);
        mesa.estado = nuevoEstado;
    }
}
