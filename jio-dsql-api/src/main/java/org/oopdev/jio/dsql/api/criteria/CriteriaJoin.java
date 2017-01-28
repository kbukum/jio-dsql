package org.oopdev.jio.dsql.api.criteria;

import org.oopdev.jio.dsql.api.criteria.criterion.Criterion;

/**
 * Created by kamilbukum on 28/01/2017.
 */
public class CriteriaJoin<T> extends CriteriaParent<T> {
    private final CriteriaParent<T> parent;
    CriteriaJoin(String alias, Class<?> entityClass, CriteriaParent<T> parent) {
        super(alias, entityClass, parent.getTransformer());
        this.parent = parent;
    }

    @Override
    public CriteriaParent<T> restrict(Criterion criterion) {
        super.restrict(criterion);
        return this;
    }

    public CriteriaParent<T> getParent() {
        return parent;
    }
}
