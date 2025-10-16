package py.edu.ucom.gustodivino.resource;

import py.edu.ucom.gustodivino.domain.Producto;
import py.edu.ucom.gustodivino.service.ProductoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/productos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class ProductoResource {

    @Inject
    ProductoService productoService;

    @GET
    public Response listar() {
        return Response.ok(productoService.listar()).build();
    }

    @GET
    @Path("/{id}")
    public Response obtener(@PathParam("id") Long id) {
        return Response.ok(productoService.obtenerPorId(id)).build();
    }

    @POST
    public Response crear(@Valid Producto producto) {
        Producto nuevoProducto = productoService.crear(producto);
        return Response.status(Response.Status.CREATED).entity(nuevoProducto).build();
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") Long id, @Valid Producto producto) {
        return Response.ok(productoService.actualizar(id, producto)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Long id) {
        productoService.eliminar(id);
        return Response.noContent().build();
    }
}