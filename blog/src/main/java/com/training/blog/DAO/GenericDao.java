package com.training.blog.DAO;

import java.util.List;

public interface GenericDao<E, T> {
    void save(E entity);
    void delete(List<T> ids);
    void update(E entity);
}
