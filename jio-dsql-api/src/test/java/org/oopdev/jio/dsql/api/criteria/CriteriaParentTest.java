package org.oopdev.jio.dsql.api.criteria;

import org.hibernate.Session;
import org.junit.Test;
import org.oopdev.jio.dsql.api.criteria.transform.Transformer;
import org.oopdev.jio.dsql.api.test.tools.DataTestTools;
import org.oopdev.jio.dsql.api.test.tools.TransformerImplTest;
import org.oopdev.jio.dsql.api.test.tools.dto.UserDto;
import org.oopdev.jio.dsql.api.test.tools.entity.User;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by kamilbukum on 29/01/2017.
 */
public class CriteriaParentTest extends DataTestTools {
    @Test
    public void join() throws Exception {

    }

    @Test
    public void getTransformer() throws Exception {
        Session session = sessionFactory.openSession();
        Transformer<User> entityTransformer = new TransformerImplTest<>(User.class, session);
        Transformer<UserDto> dtoTransformer = new TransformerImplTest<>(UserDto.class, session);
        Transformer<Map<String, Object>> mapTransformer = new TransformerImplTest<>(Transformer.MAP_CLASS, session);

        // New Criteria For Entity
        Transformer<User> entityResultTransformer = Criteria.newCriteria(User.class, entityTransformer).getTransformer();
        assertNotNull(entityResultTransformer);
        assertEquals(entityResultTransformer, entityTransformer);

        // New Criteria For DTO
        Transformer<UserDto> dtoResultTransformer = Criteria.newCriteria(User.class, dtoTransformer).getTransformer();
        assertNotNull(dtoResultTransformer);
        assertEquals(dtoResultTransformer, dtoTransformer);

        // New Criteria For Map
        Transformer<Map<String, Object>> mapResultTransformer = Criteria.newCriteria(User.class, mapTransformer).getTransformer();
        assertNotNull(mapResultTransformer);
        assertEquals(mapResultTransformer, mapTransformer);
    }

    @Test
    public void getEntityClass() throws Exception {
        Session session = sessionFactory.openSession();
        Transformer<User> entityTransformer = new TransformerImplTest<>(User.class, session);
        Transformer<UserDto> dtoTransformer = new TransformerImplTest<>(UserDto.class, session);
        Transformer<Map<String, Object>> mapTransformer = new TransformerImplTest<>(Transformer.MAP_CLASS, session);

        // New Criteria For Entity
        Class<?> entityClass = Criteria.newCriteria(User.class, entityTransformer).getEntityClass();
        assertNotNull(entityClass);
        assertEquals(entityClass, User.class);

        // New Criteria For DTO
        entityClass  = Criteria.newCriteria(User.class, dtoTransformer).getEntityClass();
        assertNotNull(entityClass);
        assertEquals(entityClass, User.class);

        // New Criteria For Map
        entityClass  = Criteria.newCriteria(User.class, mapTransformer).getEntityClass();
        assertNotNull(entityClass);
        assertEquals(entityClass, User.class);
    }

    @Test
    public void restrict() throws Exception {

    }

}