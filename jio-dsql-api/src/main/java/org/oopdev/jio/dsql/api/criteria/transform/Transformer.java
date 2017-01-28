package org.oopdev.jio.dsql.api.criteria.transform;

import org.oopdev.jio.dsql.api.criteria.Criteria;

import java.util.List;

/**
 * Created by kamilbukum on 28/01/2017.
 */
public interface Transformer<T> {
    List<T> list(Criteria<T> criteria);
    Long count(Criteria<T> criteria);
    Result<T> result(Criteria<T> criteria);
    T findOne();
    Object uniqueResult();
    Class<T> getTransformClass();
}
