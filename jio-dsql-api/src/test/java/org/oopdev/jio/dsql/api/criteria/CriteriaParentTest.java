package org.oopdev.jio.dsql.api.criteria;

import org.hibernate.Session;
import org.junit.Test;
import org.oopdev.jio.dsql.api.criteria.transform.Transformer;
import org.oopdev.jio.dsql.api.test.tools.DataTestTools;
import org.oopdev.jio.dsql.api.test.tools.MetaFinderJpaImplTest;
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
        Transformer<User> entityTransformer = new TransformerImplTest<>(session, MetaFinderJpaImplTest.finderJpa);
        Transformer<UserDto> dtoTransformer = new TransformerImplTest<>(session, MetaFinderJpaImplTest.finderJpa);
        Transformer<Map<String, Object>> mapTransformer = new TransformerImplTest<>(session, MetaFinderJpaImplTest.finderJpa);

        // New Criteria For Entity
        Transformer<User> entityResultTransformer = Criteria.createCriteria(User.class, entityTransformer).getTransformer();
        assertNotNull(entityResultTransformer);
        assertEquals(entityResultTransformer, entityTransformer);

        // New Criteria For DTO
        Transformer<UserDto> dtoResultTransformer = Criteria.createCriteria(User.class, dtoTransformer).getTransformer();
        assertNotNull(dtoResultTransformer);
        assertEquals(dtoResultTransformer, dtoTransformer);

        // New Criteria For Map
        Transformer<Map<String, Object>> mapResultTransformer = Criteria.createCriteria(User.class, mapTransformer).getTransformer();
        assertNotNull(mapResultTransformer);
        assertEquals(mapResultTransformer, mapTransformer);
    }

    @Test
    public void getEntityClass() throws Exception {
        Session session = sessionFactory.openSession();
        Transformer<User> entityTransformer = new TransformerImplTest<>(session, MetaFinderJpaImplTest.finderJpa);
        Transformer<UserDto> dtoTransformer = new TransformerImplTest<>(session, MetaFinderJpaImplTest.finderJpa);
        Transformer<Map<String, Object>> mapTransformer = new TransformerImplTest<>(session, MetaFinderJpaImplTest.finderJpa);

        // New Criteria For Entity
        Class<?> entityClass = Criteria.createCriteria(User.class, entityTransformer).getEntityClass();
        assertNotNull(entityClass);
        assertEquals(entityClass, User.class);

        // New Criteria For DTO
        entityClass  = Criteria.createCriteria(User.class, dtoTransformer).getEntityClass();
        assertNotNull(entityClass);
        assertEquals(entityClass, User.class);

        // New Criteria For Map
        entityClass  = Criteria.createCriteria(User.class, mapTransformer).getEntityClass();
        assertNotNull(entityClass);
        assertEquals(entityClass, User.class);
    }

    @Test
    public void restrict() throws Exception {

    }

}