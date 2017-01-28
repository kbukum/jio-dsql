package org.oopdev.jio.dsql.api.criteria.transform;

import org.oopdev.jio.dsql.api.cache.EntityMeta;
import org.oopdev.jio.dsql.api.cache.EntityMetaFinder;
import org.oopdev.jio.dsql.api.criteria.Criteria;
import org.oopdev.jio.dsql.common.lang.TypeReference;

import java.util.List;
import java.util.Map;

/**
 * Created by kamilbukum on 28/01/2017.
 */
public interface Transformer<T> {
    Class<Map<String, Object>> MAP_CLASS = new TypeReference<Map<String, Object>>() {}.getClazz();
    List<T> list(Criteria<T> criteria);
    Long count(Criteria<T> criteria);
    Result<T> pairList(Criteria<T> criteria);
    T findOne(Criteria<T> criteria);
    Object uniqueResult(Criteria<T> criteria);
    Class<T> getTransformClass();
    EntityMetaFinder getFinder();
    EntityMeta getMeta();
}
