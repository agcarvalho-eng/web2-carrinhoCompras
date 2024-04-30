package br.edu.ifto.projetoWeb2.model.repository;


import br.edu.ifto.projetoWeb2.model.entity.CarrinhoCompra;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarrinhoCompraRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(CarrinhoCompra carrinhoCompra) {
        em.persist(carrinhoCompra);
    }

    public List<CarrinhoCompra> carrinhoCompras() {
        Query query = em.createQuery("from CarrinhoCompra");
        return query.getResultList();
    }

    public CarrinhoCompra carrinhoCompra(Long id) {
        return em.find(CarrinhoCompra.class, id);
    }

    public void remove(Long id) {
        CarrinhoCompra car = em.find(CarrinhoCompra.class, id);
        em.remove(car);
    }

    public void update(CarrinhoCompra carrinhoCompra) {
        em.merge(carrinhoCompra);
    }
}
