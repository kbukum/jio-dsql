package org.oopdev.jio.dsql.api.test.tools;

import org.hibernate.Query;
import org.hibernate.Session;
import org.oopdev.jio.dsql.api.cache.Meta;
import org.oopdev.jio.dsql.api.cache.MetaFinder;
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
    private Meta meta;
    private static final MetaFinder FINDER = new MetaFinderImplTest();
    private final Class<T> transformClass;
    private final Session session;
    public TransformerImplTest(Class<T> transformClass, Session session) {
        this.transformClass = transformClass;
        this.session = session;
    }

    @Override
    public List<T> list(Criteria<T> criteria) {
        Pair<String, Map<String, Object>> resultPair = TransformerUtil.query(criteria);
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
        Pair<String, Map<String, Object>> resultPair = TransformerUtil.count(criteria);
        Query query = session.createQuery(resultPair.getLeft());
        if(resultPair.getRight() != null) {
            for(Map.Entry<String, Object> entry: resultPair.getRight().entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return (long)query.uniqueResult();
    }

    @Override
    public Result<T> pairList(Criteria<T> criteria) {
        Pair<String,Pair<String, Map<String, Object>>> resultPair = TransformerUtil.pairList(criteria);
        Query listQuery = session.createQuery(resultPair.getLeft());
        Query countQuery = session.createQuery(resultPair.getRight().getLeft());
        if(resultPair.getRight().getRight() != null) {
            for(Map.Entry<String, Object> entry: resultPair.getRight().getRight().entrySet()) {
                listQuery.setParameter(entry.getKey(), entry.getValue());
                countQuery.setParameter(entry.getKey(), entry.getValue());
            }
        }
        List<T> list = listQuery.list();
        Long count = (long)countQuery.uniqueResult();
        return new Result<>(list, count);
    }

    @Override
    public Object uniqueResult(Criteria<T> criteria) {
        Pair<String, Map<String, Object>> resultPair = TransformerUtil.query(criteria);
        Query query = session.createQuery(resultPair.getLeft());
        if(resultPair.getRight() != null) {
            for(Map.Entry<String, Object> entry: resultPair.getRight().entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return query.uniqueResult();
    }

    @Override
    public T findOne(Criteria<T> criteria) {
        return null;
    }

    @Override
    public Class<T> getTransformClass() {
        return transformClass;
    }

    @Override
    public MetaFinder getFinder() {
        return FINDER;
    }

    @Override
    public Meta getMeta() {
        if(this.meta == null) {
            this.meta = FINDER.getEntityMeta(this.transformClass);
        }
        return this.meta;
    }
}
