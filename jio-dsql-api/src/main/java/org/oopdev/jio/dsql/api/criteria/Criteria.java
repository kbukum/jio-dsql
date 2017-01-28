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
public class Criteria<T> {
    /**
     *
     */
    private final Class<?> entityClass;
    /**
     *
     */
    private final Class<T> transformClass;
    private Transformer<T> transformer;

    /**
     *
     * @param entityClass
     */
    private Criteria(Class<T> entityClass) {
        this.entityClass = entityClass;
        this.transformClass = entityClass;
    }

    /**
     *
     * @param entityClass
     * @param transformClass
     */
    private Criteria(Class<?> entityClass, Class<T> transformClass) {
        this.entityClass = entityClass;
        this.transformClass = transformClass;
    }

    private Criteria(Class<?> entityClass, Class<T> transformClass, Transformer<T> transformer) {
        this.entityClass = entityClass;
        this.transformClass = transformClass;
        this.transformer = transformer;
    }

    /**
     *
     * @return
     */
    public Class<T> getTransformClass() {
        return transformClass;
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
    public Criteria<T> restrict(Criterion criterion) {
        return this;
    }

    /**
     *
     * @param transformer
     * @return
     */
    public Criteria<T> setTransformer(Transformer<T> transformer) {
        this.transformer = transformer;
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


    public static <E> Criteria<E> forEntity(Class<E> entityClass) {
        return new Criteria<>(entityClass);
    }

    public static <E> Criteria<E> forDto(Class<?> entityClass, Class<E> transformClass) {
        return new Criteria<>(entityClass, transformClass);
    }

    public static final Class<Map<String, Object>> MAP_CLASS = new TypeReference<Map<String, Object>>() {}.getClazz();

    public static <E> Criteria<Map<String, Object>> forMap(Class<E> entityClass) {
        return new Criteria<>(entityClass, MAP_CLASS);
    }

    public static <E> Criteria<E> newCriteria(Class<?> entityClass, Transformer<E> transformer) {
        return new Criteria<>(entityClass, transformer.getTransformClass(), transformer);
    }

}
