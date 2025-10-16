package py.edu.ucom.gustodivino.resource;

import py.edu.ucom.gustodivino.domain.Cliente;
import py.edu.ucom.gustodivino.repository.ClienteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class ClienteResource {

    @Inject
    ClienteRepository clienteRepository;

    @GET
    public List<Cliente> listarTodos() {
        return clienteRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Long id) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente != null) {
            return Response.ok(cliente).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Transactional
    public Response crear(Cliente cliente) {
        clienteRepository.persist(cliente);
        return Response.status(Response.Status.CREATED).entity(cliente).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response actualizar(@PathParam("id") Long id, Cliente clienteActualizado) {
        Cliente clienteExistente = clienteRepository.findById(id);
        if (clienteExistente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        clienteExistente.nombre = clienteActualizado.nombre;
        clienteExistente.apellido = clienteActualizado.apellido;
        clienteExistente.telefono = clienteActualizado.telefono;
        clienteExistente.direccion = clienteActualizado.direccion;

        return Response.ok(clienteExistente).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response eliminar(@PathParam("id") Long id) {
        boolean eliminado = clienteRepository.deleteById(id);
        if (eliminado) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
