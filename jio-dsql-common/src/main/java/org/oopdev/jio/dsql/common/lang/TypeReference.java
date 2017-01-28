package org.oopdev.jio.dsql.common.lang;

import java.lang.reflect.*;

public abstract class TypeReference<T> {
    /**
     * holds given class
     */
    protected final Class<T> clazz;

    /**
     * checks Generic class and finds it's type.
     */
    protected TypeReference() {
        Type superClass = this.getClass().getGenericSuperclass();
        if(superClass instanceof Class) {
            throw new IllegalArgumentException("Internal error: TypeReference constructed without actual type information");
        }
        clazz = (Class<T>) ((ParameterizedType) ((ParameterizedType)superClass).getActualTypeArguments()[0]).getRawType();
    }

    /**
     * get given generic class type
     * @return
     */
    public Class<T> getClazz() {
        return clazz;
    }
}
