package com.ecommerce.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

import com.ecommerce.model.Producto;

@Stateless
public class ProductoService {

    @PersistenceContext(unitName = "ecommercePU")
    private EntityManager em;

    // Obtener todos los productos
    public List<Producto> obtenerTodosLosProductos() {
        return em.createQuery("SELECT p FROM Producto p", Producto.class).getResultList();
    }

    // Crear un nuevo producto
    public void crearProducto(Producto producto) {
        em.persist(producto);
    }

    // Obtener un producto por ID
    public Producto obtenerProducto(Long id) {
        return em.find(Producto.class, id);
    }

    // Verificar si un producto existe
    public boolean existeProducto(Long id) {
        return em.createQuery("SELECT COUNT(p) FROM Producto p WHERE p.idProducto = :id", Long.class)
                .setParameter("id", id)
                .getSingleResult() > 0;
    }

    // Actualizar un producto existente
    public boolean actualizarProducto(Long id, Producto nuevoProducto) {
        Producto producto = em.find(Producto.class, id);
        if (producto == null) {
            return false;
        }
        producto.setNombre(nuevoProducto.getNombre());
        producto.setCategoria(nuevoProducto.getCategoria());
        producto.setPrecioVenta(nuevoProducto.getPrecioVenta());
        producto.setCantidadExistente(nuevoProducto.getCantidadExistente());
        em.merge(producto);
        return true;
    }

    // Eliminar un producto
    public boolean eliminarProducto(Long id) {
        Producto producto = em.find(Producto.class, id);
        if (producto == null) {
            return false;
        }
        em.remove(producto);
        return true;
    }

    // Obtener productos por filtro (nombre o categoría)
    public List<Producto> obtenerProductosFiltrados(String nombre, Long idCategoria) {
        String jpql = "SELECT p FROM Producto p WHERE 1 = 1";
        if (nombre != null && !nombre.isEmpty()) {
            jpql += " AND LOWER(p.nombre) LIKE LOWER(:nombre)";
        }
        if (idCategoria != null) {
            jpql += " AND p.categoria.idCategoria = :idCategoria";
        }

        TypedQuery<Producto> query = em.createQuery(jpql, Producto.class);

        if (nombre != null && !nombre.isEmpty()) {
            query.setParameter("nombre", "%" + nombre + "%");
        }
        if (idCategoria != null) {
            query.setParameter("idCategoria", idCategoria);
        }

        return query.getResultList();
    }

}
