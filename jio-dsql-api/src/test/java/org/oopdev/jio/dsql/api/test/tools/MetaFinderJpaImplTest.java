package org.oopdev.jio.dsql.api.test.tools;

import org.oopdev.jio.dsql.api.cache.MetaFinderImpl;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.util.function.Predicate;

/**
 * Created by kamilbukum on 29/01/2017.
 */
public class MetaFinderJpaImplTest extends MetaFinderImpl {
    public static MetaFinderJpaImplTest finderJpa = new MetaFinderJpaImplTest();
    @Override
    public Predicate<Field> predicateTransient() {
        return ENTITY_FIELD_IS_TRANSIENT;
    }

    private static final Predicate<Field> ENTITY_FIELD_IS_TRANSIENT = (field) -> {
        if(field.getAnnotation(Transient.class) != null) return true;
        return false;
    };

    @Override
    public Predicate<Field> predicateId() {
        return ENTITY_ID_FIELD_PREDICATE;
    }

    private static final Predicate<Field> ENTITY_ID_FIELD_PREDICATE = (field) -> {
        if(field.getAnnotation(Id.class) != null) return true;
        return false;
    };
}
