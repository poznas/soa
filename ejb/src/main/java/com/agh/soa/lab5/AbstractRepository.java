package com.agh.soa.lab5;

import static java.util.Optional.of;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;

@Log
@Setter
@NoArgsConstructor
public abstract class AbstractRepository<T> {

    @PersistenceContext(unitName = "Soa")
    protected EntityManager entityManager;

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

    public boolean delete(@NotNull Long id) {
        var entity = of(id).map(this::getEntity);

        if( entity.isPresent()) {
            delete(entity.get());
            return true;
        }
        return false;
    }

    private T commit(T entity, BiConsumer<EntityManager, T> action) {
        try {
            userTransaction.begin();
            action.accept(entityManager, entity);
            userTransaction.commit();
        } catch (NotSupportedException | SystemException
                | HeuristicMixedException | HeuristicRollbackException | RollbackException e) {
            log.warning(e.getMessage());
        }
        return entity;
    }

    public T getEntity(Long id) {
        return getEntity(Map.of("id", id));
    }

    protected T getEntity(@NotNull Map<String, Object> conditions) {
        var builder = entityManager.getCriteriaBuilder();
        var query = builder.createQuery(getType());
        Root<T> root = query.from(getType());

        var predicates = conditions.entrySet().stream()
          .map(e -> builder.equal(root.get(e.getKey()), e.getValue())).toArray(Predicate[]::new);

        query.select(root).where(predicates);

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
