package org.oopdev.jio.dsql.api.test.tools;

import org.hibernate.Query;
import org.hibernate.Session;
import org.oopdev.jio.dsql.api.cache.FieldMeta;
import org.oopdev.jio.dsql.api.cache.MetaFinder;
import org.oopdev.jio.dsql.api.criteria.Criteria;
import org.oopdev.jio.dsql.api.criteria.transform.Result;
import org.oopdev.jio.dsql.api.criteria.transform.Transformer;
import org.oopdev.jio.dsql.api.test.tools.transformers.AliasToBeanResultTransformer;
import org.oopdev.jio.dsql.api.test.tools.transformers.AliasToEntityMapResultTransformer;
import org.oopdev.jio.dsql.common.lang.BooleanHolder;
import org.oopdev.jio.dsql.common.lang.Pair;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by kamilbukum on 10/01/2017.
 */
public class TransformerImplTest<E> extends Transformer<E> {
    private Session session;
    public TransformerImplTest(Session session, MetaFinder finder) {
        this(session, null, finder);
    }

    public TransformerImplTest(Session session, Class<? extends E> transformClass, MetaFinder finder) {
        super(transformClass, finder);
        this.session = session;
    }

    @Override
    public List<E> list(Criteria<E> criteria) {
        TransformerUtilTest.Elements elements = new TransformerUtilTest.Elements();
        Pair<String, Map<String, Object>> pair = TransformerUtilTest.query(criteria, elements);

        Query query = session.createQuery(pair.getLeft());
        if(criteria.getLimit() != null) {
            query.setMaxResults(criteria.getLimit());
        }
        if(criteria.getOffset() != null) {
            query.setFirstResult(criteria.getOffset());
        }
        for(Map.Entry<String, Object> parameter: pair.getRight().entrySet()) {
            setParameter(query, parameter.getKey(), parameter.getValue());
        }
        setResultTransformer(query);
        List<E> destinationList = query.list();

        setElementsToList(criteria, pair.getRight(), elements, destinationList);
        return destinationList;
    }

    public void setElementsToList(Criteria<E> criteria, Map<String, Object> variableMap, TransformerUtilTest.Elements elements, List<?> destinationList) {
        if(elements.elementsQuery == null || elements.elementsMap == null || elements.elementsMap.size() == 0) return;
        Query elementsQuery = session.createQuery(elements.elementsQuery);
        if(criteria.getLimit() != null) {
            elementsQuery.setMaxResults(criteria.getLimit());
        }
        if(criteria.getOffset() != null) {
            elementsQuery.setFirstResult(criteria.getOffset());
        }
        for(Map.Entry<String, Object> parameter: variableMap.entrySet()) {
            setParameter(elementsQuery, parameter.getKey(), parameter.getValue());
        }
        List<?> sourceList = elementsQuery.list();
        if(sourceList.size() > 0) {
            for(int i = 0 ; i < destinationList.size(); i++) {
                Object sourceObject =  sourceList.get(i);
                Object destinationObject = destinationList.get(i);
                for(Map.Entry<String, String> elementEntry: elements.elementsMap.entrySet()) {
                    FieldMeta srcField = criteria.getMeta().getFieldMap().get(elementEntry.getKey());
                    FieldMeta destinationField = criteria.getTransformer().getMeta().getFieldMap().get(elementEntry.getKey());
                    try {
                        if(!destinationField.getField().isAccessible()) {
                            destinationField.getField().setAccessible(true);
                        }
                        if(!srcField.getField().isAccessible()) {
                            srcField.getField().setAccessible(true);
                        }
                        destinationField.getField().set(destinationObject, srcField.getField().get(sourceObject));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }




    @Override
    public Result<E> pairList(Criteria<E> criteria) {
        Result<E> result  = new Result<>();
        BooleanHolder groupBy = new BooleanHolder(false);
        TransformerUtilTest.Elements elements = new TransformerUtilTest.Elements();
        Pair<String, Pair<String, Map<String, Object>>> pair = TransformerUtilTest.pairList(criteria, elements, groupBy);
        Query listQuery = session.createQuery(pair.getLeft());
        if(elements.elementsMap != null && elements.elementsMap.size() > 0) {

        }
        if(criteria.getLimit() != null) {
            listQuery.setMaxResults(criteria.getLimit());
        }
        if(criteria.getOffset() != null) {
            listQuery.setFirstResult(criteria.getOffset());
        }
        setResultTransformer(listQuery);
        Query countQuery = session.createQuery(pair.getRight().getLeft());
        for(Map.Entry<String, Object> parameter: pair.getRight().getRight().entrySet()) {
            setParameter(listQuery, parameter.getKey(), parameter.getValue());
            setParameter(countQuery, parameter.getKey(), parameter.getValue());
        }
        List<E> destinationList = listQuery.list();
        setElementsToList(criteria, pair.getRight().getRight(), elements, destinationList);
        result.setList(destinationList);
        result.setTotalCount(groupBy.is() ? countQuery.list().size(): (long)countQuery.uniqueResult());
        return result;
    }

    public void setParameter(Query query, String key, Object value) {
        if(value instanceof Collection) {
            query.setParameterList(key, (Collection) value);
        } else {
            query.setParameter(key, value);
        }
    }

    @Override
    public Long count(Criteria<E> criteria) {
        BooleanHolder groupBy = new BooleanHolder(false);
        Pair<String, Map<String, Object>> pair = TransformerUtilTest.count(criteria, groupBy);
        Query query = session.createQuery(pair.getLeft());
        for(Map.Entry<String, Object> parameter: pair.getRight().entrySet()) {
            setParameter(query, parameter.getKey(), parameter.getValue());
        }
        setResultTransformer(query);
        return groupBy.is() ? query.list().size(): (long)query.uniqueResult();
    }

    @Override
    public Object uniqueResult(Criteria<E> criteria) {
        TransformerUtilTest.Elements elements = new TransformerUtilTest.Elements();
        Pair<String, Map<String, Object>> pair = TransformerUtilTest.query(criteria, elements);
        Query query = session.createQuery(pair.getLeft());

        for(Map.Entry<String, Object> parameter: pair.getRight().entrySet()) {
            setParameter(query, parameter.getKey(), parameter.getValue());
        }
        setResultTransformer(query);
        return query.uniqueResult();
    }

    private void setResultTransformer(Query query){
        switch (this.getTransformType()) {
            case MAP:
                query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
                break;
            case DTO:
                query.setResultTransformer(new AliasToBeanResultTransformer(this.getTransformClass(), this.getMeta()));
                break;
        }

    }

}
