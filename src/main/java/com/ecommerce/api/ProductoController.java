package com.ecommerce.api;

import com.ecommerce.model.Categoria;
import com.ecommerce.model.Producto;
import com.ecommerce.dto.ProductoDTO;
import com.ecommerce.service.CategoriaService;
import com.ecommerce.service.ProductoService;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/productos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductoController {

    @EJB
    private ProductoService productoService;

    @EJB
    private CategoriaService categoriaService;

    // Obtener todos los productos
    @GET
    public Response obtenerTodosLosProductos() {
        List<Producto> productos = productoService.obtenerTodosLosProductos();
        return Response.ok(productos).build();
    }

    // Crear un nuevo producto usando ProductoDTO
    @POST
    public Response crearProducto(ProductoDTO productoDTO) {
        Categoria categoria = categoriaService.obtenerCategoria(productoDTO.getIdCategoria());
        if (categoria == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Categoría no encontrada").build();
        }

        Producto producto = new Producto(
                productoDTO.getNombre(),
                categoria,
                productoDTO.getPrecioVenta(),
                productoDTO.getCantidadExistente());

        productoService.crearProducto(producto);
        return Response.status(Response.Status.CREATED).entity("Producto creado exitosamente").build();
    }

    // Obtener un producto por ID
    @GET
    @Path("/{id}")
    public Response obtenerProducto(@PathParam("id") Long id) {
        Producto producto = productoService.obtenerProducto(id);
        if (producto == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Producto no encontrado").build();
        }
        return Response.ok(producto).build();
    }

    // Verificar si un producto existe
    @GET
    @Path("/existe/{id}")
    public Response existeProducto(@PathParam("id") Long id) {
        boolean existe = productoService.existeProducto(id);
        return Response.ok(existe).build();
    }

    // Actualizar un producto
    @PUT
    @Path("/{id}")
    public Response actualizarProducto(@PathParam("id") Long id, ProductoDTO productoDTO) {
        Categoria categoria = categoriaService.obtenerCategoria(productoDTO.getIdCategoria());
        if (categoria == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Categoría no encontrada").build();
        }

        Producto producto = new Producto(
                productoDTO.getNombre(),
                categoria,
                productoDTO.getPrecioVenta(),
                productoDTO.getCantidadExistente());

        boolean actualizado = productoService.actualizarProducto(id, producto);
        if (!actualizado) {
            return Response.status(Response.Status.NOT_FOUND).entity("Producto no encontrado").build();
        }
        return Response.ok("Producto actualizado exitosamente").build();
    }

    // Eliminar un producto
    @DELETE
    @Path("/{id}")
    public Response eliminarProducto(@PathParam("id") Long id) {
        boolean eliminado = productoService.eliminarProducto(id);
        if (!eliminado) {
            return Response.status(Response.Status.NOT_FOUND).entity("Producto no encontrado").build();
        }
        return Response.ok("Producto eliminado exitosamente").build();
    }

    // Obtener productos por nombre o categoría
    @POST
    @Path("/filtrar")
    public Response obtenerProductosFiltrados(ProductoDTO filtro) {
        List<Producto> productos = productoService.obtenerProductosFiltrados(filtro.getNombre(),
                filtro.getIdCategoria());
        return Response.ok(productos).build();
    }

}
