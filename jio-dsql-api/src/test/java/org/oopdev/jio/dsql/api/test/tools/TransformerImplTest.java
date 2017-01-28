package org.oopdev.jio.dsql.api.test.tools;

import org.hibernate.Query;
import org.hibernate.Session;
import org.oopdev.jio.dsql.api.cache.EntityMeta;
import org.oopdev.jio.dsql.api.cache.EntityMetaFinder;
import org.oopdev.jio.dsql.api.criteria.Criteria;
import org.oopdev.jio.dsql.api.criteria.transform.Result;
import org.oopdev.jio.dsql.api.criteria.transform.Transformer;
import org.oopdev.jio.dsql.common.lang.Pair;

import java.util.List;
import java.util.Map;

/**
 * Created by kamilbukum on 28/01/2017.
 */
public class TransformerImplTest<T> implements Transformer<T> {
    private EntityMeta meta;
    private static final EntityMetaFinder FINDER = new EntityMetaFinderImplTest();
    private final Class<T> transformClass;
    private final Session session;
    public TransformerImplTest(Class<T> transformClass, Session session) {
        this.transformClass = transformClass;
        this.session = session;
    }

    @Override
    public List<T> list(Criteria<T> criteria) {
        Pair<String, Map<String, Object>> resultPair = TransformerUtil.list(criteria);
        Query query = session.createQuery(resultPair.getLeft());
        if(resultPair.getRight() != null) {
            for(Map.Entry<String, Object> entry: resultPair.getRight().entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return query.list();
    }

    @Override
    public Long count(Criteria<T> criteria) {
        return null;
    }

    @Override
    public Result<T> pairList(Criteria<T> criteria) {
        return null;
    }

    @Override
    public T findOne(Criteria<T> criteria) {
        return null;
    }

    @Override
    public Object uniqueResult(Criteria<T> criteria) {
        return null;
    }

    @Override
    public Class<T> getTransformClass() {
        return transformClass;
    }

    @Override
    public EntityMetaFinder getFinder() {
        return FINDER;
    }

    @Override
    public EntityMeta getMeta() {
        if(this.meta == null) {
            this.meta = FINDER.getEntityMeta(this.transformClass);
        }
        return this.meta;
    }
}
