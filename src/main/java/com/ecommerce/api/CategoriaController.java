package com.ecommerce.api;

import com.ecommerce.model.Categoria;
import com.ecommerce.service.CategoriaService;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/categorias")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CategoriaController {

    @EJB
    private CategoriaService categoriaService;

    // Obtener todas las categorías
    @GET
    public Response todasLasCategorias() {
        List<Categoria> categorias = categoriaService.todasLasCategorias();
        return Response.ok(categorias).build();
    }

    // Crear una nueva categoría
    @POST
    public Response crearCategoria(Categoria categoria) {
        categoriaService.crearCategoria(categoria);
        return Response.status(Response.Status.CREATED).entity("Categoría creada exitosamente").build();
    }

    // Obtener una categoría por ID
    @GET
    @Path("/{id}")
    public Response obtenerCategoria(@PathParam("id") Long id) {
        Categoria categoria = categoriaService.obtenerCategoria(id);
        if (categoria != null) {
            return Response.ok(categoria).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Categoría no encontrada").build();
        }
    }

    // Verificar si una categoría existe
    @GET
    @Path("/existe/{id}")
    public Response existeCategoria(@PathParam("id") Long id) {
        boolean existe = categoriaService.existeCategoria(id);
        return Response.ok(existe).build();
    }

    // Actualizar una categoría
    @PUT
    @Path("/{id}")
    public Response actualizarCategoria(@PathParam("id") Long id, Categoria categoria) {
        if (categoriaService.existeCategoria(id)) {
            categoriaService.actualizarCategoria(id, categoria);
            return Response.ok("Categoría actualizada exitosamente").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Categoría no encontrada").build();
        }
    }

    // Eliminar una categoría
    @DELETE
    @Path("/{id}")
    public Response eliminarCategoria(@PathParam("id") Long id) {
        if (categoriaService.existeCategoria(id)) {
            categoriaService.eliminarCategoria(id);
            return Response.ok("Categoría eliminada exitosamente").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Categoría no encontrada").build();
        }
    }
}
