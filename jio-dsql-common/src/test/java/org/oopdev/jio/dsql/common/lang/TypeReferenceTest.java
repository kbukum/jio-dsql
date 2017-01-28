package org.oopdev.jio.dsql.common.lang;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by kamilbukum on 28/01/2017.
 */
public class TypeReferenceTest {
    @Test
    public void getClazz() throws Exception {
        TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String, Object>>() {};
        assertEquals(typeReference.getClazz().getName(), Map.class.getName());

    }

}