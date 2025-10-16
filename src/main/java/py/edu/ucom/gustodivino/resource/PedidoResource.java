package py.edu.ucom.gustodivino.resource;

import py.edu.ucom.gustodivino.domain.Pedido;
import py.edu.ucom.gustodivino.service.PedidoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PedidoResource {

    @Inject
    PedidoService pedidoService;

    @GET
    public Response listar() {
        return Response.ok(pedidoService.listar()).build();
    }

    @GET
    @Path("/{id}")
    public Response obtener(@PathParam("id") Long id) {
        return Response.ok(pedidoService.obtenerPorId(id)).build();
    }

    @POST
    public Response crear(Pedido pedido) {
        Pedido nuevoPedido = pedidoService.crear(pedido);
        return Response.status(Response.Status.CREATED).entity(nuevoPedido).build();
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") Long id, Pedido pedido) {
        Pedido pedidoActualizado = pedidoService.actualizar(id, pedido);
        return Response.ok(pedidoActualizado).build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Long id) {
        pedidoService.eliminar(id);
        return Response.noContent().build();
    }
}

