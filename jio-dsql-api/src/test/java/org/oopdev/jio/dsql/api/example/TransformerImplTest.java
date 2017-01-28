package org.oopdev.jio.dsql.api.example;

import org.oopdev.jio.dsql.api.criteria.Criteria;
import org.oopdev.jio.dsql.api.criteria.transform.Result;
import org.oopdev.jio.dsql.api.criteria.transform.Transformer;

import java.util.List;

/**
 * Created by kamilbukum on 28/01/2017.
 */
public class TransformerImplTest<T> implements Transformer<T> {
    private final Class<T> transformClass;
    public TransformerImplTest(Class<T> transformClass) {
        this.transformClass = transformClass;
    }

    @Override
    public List<T> list(Criteria<T> criteria) {
        return null;
    }

    @Override
    public Long count(Criteria<T> criteria) {
        return null;
    }

    @Override
    public Result<T> result(Criteria<T> criteria) {
        return null;
    }

    @Override
    public T findOne() {
        return null;
    }

    @Override
    public Object uniqueResult() {
        return null;
    }

    @Override
    public Class<T> getTransformClass() {
        return transformClass;
    }
}
