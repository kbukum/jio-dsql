package org.oopdev.jio.dsql.api.criteria;

import org.oopdev.jio.dsql.api.cache.Meta;
import org.oopdev.jio.dsql.api.criteria.criterion.Criterion;
import org.oopdev.jio.dsql.api.criteria.transform.Transformer;
import org.oopdev.jio.dsql.common.util.Strings;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by kamilbukum on 28/01/2017.
 */
public abstract class CriteriaParent<T> {
    protected String alias;
    protected final Class<?> entityClass;
    protected final Transformer<T> transformer;
    protected Meta meta;
    private final Map<Class<?>, CriteriaJoin<T>> joins = new LinkedHashMap<>();
    protected final List<Criterion> criterionList = new LinkedList<>();

    protected CriteriaParent(String alias, Class<?> entityClass, Transformer<T> transformer) {
        this.entityClass = entityClass;
        this.transformer = transformer;
        this.alias = alias == null ? Strings.unCapitalizeFirstChar(entityClass.getSimpleName()): alias;
        if(transformer.getTransformClass() != null && this.entityClass.getName().equals(transformer.getTransformClass().getName())) {
            this.meta = transformer.getMeta();
        } else {
            this.meta = transformer.getFinder().getEntityMeta(entityClass);
        }
    }

    public CriteriaJoin<T> join(Class<?> entityClass){
        CriteriaJoin<T> join = new CriteriaJoin<>(null, entityClass, this);
        joins.put(entityClass, join);
        return join;
    }

    public CriteriaJoin<T> join(String alias, Class<?> entityClass){
        CriteriaJoin<T> join = new CriteriaJoin<>(alias, entityClass, this);
        joins.put(entityClass, join);
        return join;
    }


    public Transformer<T> getTransformer() {
        return transformer;
    }


    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    /**
     *
     * @return
     */
    public Class<?> getEntityClass() {
        return entityClass;
    }

    /**
     *
     * @param criterion
     * @return
     */
    public CriteriaParent<T> restrict(Criterion criterion) {
        criterionList.add(criterion);
        return this;
    }
}
