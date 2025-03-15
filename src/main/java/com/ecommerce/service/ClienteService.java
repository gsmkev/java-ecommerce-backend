package com.ecommerce.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

import com.ecommerce.model.Cliente;

@Stateless
public class ClienteService {

    @PersistenceContext(unitName = "ecommercePU")
    private EntityManager em;

    // Obtener todos los clientes
    public List<Cliente> obtenerTodosLosClientes() {
        return em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
    }

    // Crear un nuevo cliente
    public void crearCliente(Cliente cliente) {
        em.persist(cliente);
    }

    // Obtener un cliente por ID
    public Cliente obtenerCliente(Long id) {
        return em.find(Cliente.class, id);
    }

    // Verificar si un cliente existe
    public boolean existeCliente(Long id) {
        return em.find(Cliente.class, id) != null;
    }

    // Actualizar un cliente existente
    public boolean actualizarCliente(Long id, Cliente nuevoCliente) {
        Cliente cliente = em.find(Cliente.class, id);
        if (cliente == null) {
            return false;
        }
        cliente.setNombre(nuevoCliente.getNombre());
        cliente.setApellido(nuevoCliente.getApellido());
        cliente.setCedula(nuevoCliente.getCedula());
        cliente.setEmail(nuevoCliente.getEmail());
        em.merge(cliente);
        return true;
    }

    // Eliminar un cliente
    public boolean eliminarCliente(Long id) {
        Cliente cliente = em.find(Cliente.class, id);
        if (cliente == null) {
            return false;
        }
        em.remove(cliente);
        return true;
    }

}
