package org.oopdev.jio.dsql.api.criteria.transform;

import org.oopdev.jio.dsql.api.cache.Meta;
import org.oopdev.jio.dsql.api.cache.MetaFinder;
import org.oopdev.jio.dsql.api.criteria.Criteria;
import org.oopdev.jio.dsql.common.util.Strings;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kamilbukum on 10/01/2017.
 */
public abstract class Transformer<E> {
    private final MetaFinder finder;
    private final Class<? extends E> transformClass;
    private final TransformType transformType;
    private Meta meta;
    public Transformer(Class<? extends E> transformClass, MetaFinder finder){
        this.transformClass = transformClass;
        this.finder = finder;
        if(transformClass == null) {
            this.transformType = TransformType.ENTITY;
        } else if(transformClass.getName().equals(Map.class.getName())) {
            this.transformType = TransformType.MAP;
        } else {
            this.transformType = TransformType.DTO;
            this.meta = CachedEntity.getEntityMeta(transformClass, this.finder, true);
        }
    }

    public Class<? extends E> getTransformClass() {
        return transformClass;
    }

    public TransformType getTransformType() {
        return transformType;
    }

    public MetaFinder getFinder() {
        return this.finder;
    }

    public Meta getMeta(){
        return this.meta;
    }

    public Meta getMeta(Class<?> clazz){
        return CachedEntity.getEntityMeta(clazz, this.finder, false);
    }

    public abstract List<E> list(Criteria<E> criteria);
    public abstract Result<E> pairList(Criteria<E> criteria);
    public abstract Long count(Criteria<E> criteria);
    public abstract Object uniqueResult(Criteria<E> criteria);


    public enum TransformType {
        ENTITY , MAP , DTO
    }



    /**
     * Caches fields of Class
     */
    public static class CachedEntity {
        /**
         * Holds Cached Fields of Given Entity Class
         */
        private static final ConcurrentHashMap<String, Meta> entityMetaMap = new ConcurrentHashMap<>();
        private static final ConcurrentHashMap<String, MetaFinder> entityMetaFinder = new ConcurrentHashMap<>();

        private static Meta upgradeCache(Class<?> entityClass, MetaFinder finder){
            Meta meta = finder.getEntityMeta(entityClass);
            entityMetaMap.put(entityClass.getName(), meta);
            entityMetaFinder.put(entityClass.getName(), finder);
            return meta;
        }

        public static Meta getEntityMeta(Class<?> entityClass, MetaFinder metaFinder, boolean isDto) {
            Meta meta = entityMetaMap.get(entityClass.getName());
            MetaFinder finder = entityMetaFinder.get(entityClass.getName());
            if(meta == null || (finder != null && !finder.equals(metaFinder))) {
                meta = upgradeCache(entityClass, finder == null ? metaFinder: finder);
                if(meta == null) {
                    throw new RuntimeException("Entity Meta is null empty ! Please check the given finder that name is " + metaFinder.getClass().getName());
                }
                if(!isDto && Strings.isEmptyOrNull(meta.getIdentityName())) {
                    throw new RuntimeException("Identity name not found in Entity Meta ! Please check the given entity that's name is " + entityClass.getName());
                }
            }
            return meta;
        }
    }
}
