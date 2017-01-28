package org.oopdev.jio.dsql.api.criteria;

import org.oopdev.jio.dsql.api.criteria.criterion.Criterion;
import org.oopdev.jio.dsql.api.criteria.transform.Result;
import org.oopdev.jio.dsql.api.criteria.transform.Transformer;
import org.oopdev.jio.dsql.common.lang.TypeReference;

import java.util.List;
import java.util.Map;

/**
 * Created by kamilbukum on 28/01/2017.
 */
public class Criteria<T> extends CriteriaParent<T> {

    private Criteria(String alias, Class<?> entityClass, Transformer<T> transformer) {
        super(alias, entityClass, transformer);
    }

    @Override
    public Criteria<T> restrict(Criterion criterion) {
        super.restrict(criterion);
        return this;
    }

    /**
     *
     * @return
     */
    public List<T> list() {
       assertTransformerIsExist();
       return transformer.list(this);
    }

    public Long count() {
        assertTransformerIsExist();
        return transformer.count(this);
    }
    /**
     *
     * @return
     */
    public Result<T> pairList() {
        assertTransformerIsExist();
        return transformer.pairList(this);
    }

    public T findOne(){
        assertTransformerIsExist();
        return transformer.findOne(this);
    }

    public Object uniqueResult() {
        assertTransformerIsExist();
        return transformer.uniqueResult(this);
    }

    public Transformer<T> getTransformer() {
        return transformer;
    }

    private void assertTransformerIsExist(){
        if(this.transformer == null) {
            throw new RuntimeException("Transformer not provided for Criteria !");
        }
    }

    public static <E> Criteria<E> newCriteria(Class<?> entityClass, Transformer<E> transformer) {
        return new Criteria<>(null, entityClass, transformer);
    }

    public static <E> Criteria<E> newCriteria(String alias, Class<?> entityClass, Transformer<E> transformer) {
        return new Criteria<>(alias, entityClass, transformer);
    }
}
