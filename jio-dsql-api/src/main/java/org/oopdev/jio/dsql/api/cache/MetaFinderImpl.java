package org.oopdev.jio.dsql.api.cache;

import org.oopdev.jio.dsql.api.criteria.annotations.Ignore;
import org.oopdev.jio.dsql.api.criteria.annotations.Join;
import org.oopdev.jio.dsql.api.criteria.annotations.Relation;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Created by kamilbukum on 29/01/2017.
 */
public abstract class MetaFinderImpl implements MetaFinder {
    /**
     *
     * @param entityClass
     * @return
     */
    @Override
    public Meta getEntityMeta(Class<?> entityClass) {
        Map<String, FieldMeta> fieldMetaMap = new LinkedHashMap<>();
        Map<String, String> relationMap = new LinkedHashMap<>();
        String identityName = fillFieldMetaMap(entityClass, fieldMetaMap, relationMap);
        return new Meta(identityName, fieldMetaMap, relationMap);
    }

    public abstract Predicate<Field> predicateTransient();
    public abstract Predicate<Field> predicateId();
    public Predicate<Field> predicateField() {
        return ENTITY_FIELD_PREDICATE;
    }
    /**
     *
     * @param type
     * @param fieldMetaMap
     * @return
     */
    public String fillFieldMetaMap(Class<?> type, Map<String, FieldMeta> fieldMetaMap, Map<String, String> relationMap) {
        String identityName = null;

        for(Field field: type.getDeclaredFields()) {
            if(!predicateField().test(field)) continue;
            field.setAccessible(true);
            Relation hasRelation = field.getAnnotation(Relation.class);
            boolean isTransient = predicateTransient().test(field);
            Ignore ignore = field.getAnnotation(Ignore.class);
            Join searchFrom = field.getAnnotation(Join.class);
            boolean collection = Collection.class.isAssignableFrom(field.getType());
            FieldMeta meta;
            if(searchFrom == null) { // this field hasn't any target
                meta = new FieldMeta(field, isTransient,ignore != null, hasRelation != null, collection);
            }  else {
                FieldReference reference = new FieldReference(
                        searchFrom.target(),
                        searchFrom.filter(),
                        searchFrom.id(),
                        field.getName()
                );
                meta = new FieldMeta(field, reference, isTransient,ignore != null, hasRelation != null, collection);
            }

            fieldMetaMap.put(field.getName(), meta);
            if(hasRelation != null) {
                meta.setRelationName(hasRelation.value());
                relationMap.put(hasRelation.value(), field.getName());
            }
            if(predicateId().test(field)) {
                identityName = field.getName();
            }
        }

        if(!type.getSuperclass().getName().equals(Object.class.getName())) {
            String superIdentityName = fillFieldMetaMap(type.getSuperclass(), fieldMetaMap, relationMap);
            if(identityName == null) {
                identityName = superIdentityName;
            }
        }
        return identityName;
    }

    private static final Predicate<Field> ENTITY_FIELD_PREDICATE = (field) -> {
        if(field.isSynthetic()) return false;
        if((field.getModifiers() & Modifier.STATIC) == Modifier.STATIC) return false;
        if((field.getModifiers() & Modifier.FINAL) == Modifier.FINAL) return false;
        return true;
    };
}
