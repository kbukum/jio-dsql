package org.oopdev.jio.dsql.api.cache;

/**
 * Created by kamilbukum on 12/01/2017.
 */
@FunctionalInterface
public interface MetaFinder {
    Meta getEntityMeta(Class<?> entityClass);
}
