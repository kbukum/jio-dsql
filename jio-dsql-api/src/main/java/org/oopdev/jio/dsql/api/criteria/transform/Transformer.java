package org.oopdev.jio.dsql.api.criteria.transform;

import org.oopdev.jio.dsql.api.criteria.Criteria;

import java.util.List;

/**
 * Created by kamilbukum on 28/01/2017.
 */
public interface Transformer<T> {
    List<T> list(Criteria<T> criteria);
    Long count(Criteria<T> criteria);
    Result<T> pairList(Criteria<T> criteria);
    T findOne(Criteria<T> criteria);
    Object uniqueResult(Criteria<T> criteria);
    Class<T> getTransformClass();
}
