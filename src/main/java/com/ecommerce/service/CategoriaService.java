package com.ecommerce.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

import com.ecommerce.model.Categoria;

@Stateless
public class CategoriaService {

    @PersistenceContext(unitName = "ecommercePU")
    private EntityManager em;

    // Obtener todas las categorías
    public List<Categoria> todasLasCategorias() {
        return em.createQuery("SELECT c FROM Categoria c", Categoria.class).getResultList();
    }

    // Insertar una nueva categoría
    public void crearCategoria(Categoria categoria) {
        em.persist(categoria);
    }

    // Obtener una categoría por ID
    public Categoria obtenerCategoria(Long id) {
        return em.find(Categoria.class, id);
    }

    // Verificar si una categoría existe
    public boolean existeCategoria(Long id) {
        return em.createQuery("SELECT COUNT(c) FROM Categoria c WHERE c.id = :id", Long.class)
                .setParameter("id", id)
                .getSingleResult() > 0;
    }

    // Actualizar una categoría
    public void actualizarCategoria(Long id, Categoria categoria) {
        Categoria c = em.find(Categoria.class, id);
        c.setNombre(categoria.getNombre());
        em.merge(c);
    }

    // Eliminar una categoría
    public void eliminarCategoria(Long id) {
        Categoria c = em.find(Categoria.class, id);
        em.remove(c);
    }

}
