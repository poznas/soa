package com.agh.soa.lab5;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Root;
import javax.transaction.*;
import java.util.List;
import java.util.function.BiConsumer;

@Setter
@NoArgsConstructor
public abstract class AbstractRepository<T> {

    @PersistenceContext(unitName = "Soa")
    private EntityManager entityManager;

    @Resource
    private UserTransaction userTransaction;

    protected abstract Class<T> getType();

    public T insert(T entity) {
        return commit(entity, EntityManager::persist);
    }

    public void merge(T entity) {
        commit(entity, EntityManager::merge);
    }

    public void delete(T entity) {
        commit(entity, (em, x) -> em.remove(em.contains(x) ? x : em.merge(x)));
    }

    private T commit(T entity, BiConsumer<EntityManager, T> action) {
        try {
            userTransaction.begin();
            action.accept(entityManager, entity);
            userTransaction.commit();
        } catch (NotSupportedException | SystemException
                | HeuristicMixedException | HeuristicRollbackException | RollbackException e) {
            e.printStackTrace();
        }
        return entity;
    }

    public T getEntity(Long id) {
        var builder = entityManager.getCriteriaBuilder();
        var query = builder.createQuery(getType());
        Root<T> root = query.from(getType());

        query.select(root).where(builder.equal(root.get("id"), id));

        try {
            return entityManager.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<T> getEntities() {
        return entityManager.createQuery("from " + getType().getName(), getType()).getResultList();
    }
}
