package py.edu.ucom.gustodivino.resource;

import py.edu.ucom.gustodivino.domain.Mesa;
import py.edu.ucom.gustodivino.service.MesaService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/mesas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class MesaResource {

    @Inject
    MesaService mesaService;

    @GET
    public Response listar() {
        return Response.ok(mesaService.listar()).build();
    }

    @GET
    @Path("/{id}")
    public Response obtener(@PathParam("id") Long id) {
        return Response.ok(mesaService.obtenerPorId(id)).build();
    }

    @POST
    public Response crear(Mesa mesa) {
        Mesa nuevaMesa = mesaService.crear(mesa);
        return Response.status(Response.Status.CREATED).entity(nuevaMesa).build();
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") Long id, Mesa mesa) {
        Mesa mesaActualizada = mesaService.actualizar(id, mesa);
        return Response.ok(mesaActualizada).build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Long id) {
        mesaService.eliminar(id);
        return Response.noContent().build();
    }
}