package com.training.blog.DAO;

import java.util.List;

/**
 *
 * @param <E> <b>entity</b>
 * @param <T> the <b>type</b> of entity's id
 */
public interface GenericDao<E, T> {
    E save(E entity);
    void delete(List<T> ids);
    void update(E entity);
}
