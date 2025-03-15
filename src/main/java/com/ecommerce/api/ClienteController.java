package com.ecommerce.api;

import com.ecommerce.model.Cliente;
import com.ecommerce.dto.ClienteDTO;
import com.ecommerce.service.ClienteService;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/clientes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteController {

    @EJB
    private ClienteService clienteService;

    // Obtener todos los clientes
    @GET
    public Response obtenerTodosLosClientes() {
        List<Cliente> clientes = clienteService.obtenerTodosLosClientes();
        return Response.ok(clientes).build();
    }

    // Crear un nuevo cliente usando ClienteDTO
    @POST
    public Response crearCliente(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente(
                clienteDTO.getNombre(),
                clienteDTO.getApellido(),
                clienteDTO.getCedula(),
                clienteDTO.getEmail());

        clienteService.crearCliente(cliente);
        return Response.status(Response.Status.CREATED).entity("Cliente creado exitosamente").build();
    }

    // Obtener un cliente por ID
    @GET
    @Path("/{id}")
    public Response obtenerCliente(@PathParam("id") Long id) {
        Cliente cliente = clienteService.obtenerCliente(id);
        if (cliente == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Cliente no encontrado").build();
        }
        return Response.ok(cliente).build();
    }

    // Verificar si un cliente existe
    @GET
    @Path("/existe/{id}")
    public Response verificarSiClienteExiste(@PathParam("id") Long id) {
        boolean existe = clienteService.existeCliente(id);
        if (!existe) {
            return Response.status(Response.Status.NOT_FOUND).entity("Cliente no encontrado").build();
        }
        return Response.ok("Cliente encontrado").build();
    }

    // Actualizar un cliente
    @PUT
    @Path("/{id}")
    public Response actualizarCliente(@PathParam("id") Long id, ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente(
                clienteDTO.getNombre(),
                clienteDTO.getApellido(),
                clienteDTO.getCedula(),
                clienteDTO.getEmail());

        boolean actualizado = clienteService.actualizarCliente(id, cliente);
        if (!actualizado) {
            return Response.status(Response.Status.NOT_FOUND).entity("Cliente no encontrado").build();
        }
        return Response.ok("Cliente actualizado exitosamente").build();
    }

    // Eliminar un cliente
    @DELETE
    @Path("/{id}")
    public Response eliminarCliente(@PathParam("id") Long id) {
        boolean eliminado = clienteService.eliminarCliente(id);
        if (!eliminado) {
            return Response.status(Response.Status.NOT_FOUND).entity("Cliente no encontrado").build();
        }
        return Response.ok("Cliente eliminado exitosamente").build();
    }
}
