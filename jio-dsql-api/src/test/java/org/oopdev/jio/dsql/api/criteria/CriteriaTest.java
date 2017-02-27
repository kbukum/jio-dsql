package org.oopdev.jio.dsql.api.criteria;

import org.hibernate.Session;
import org.junit.Test;

import org.oopdev.jio.dsql.api.criteria.criterion.Operator;
import org.oopdev.jio.dsql.api.criteria.criterion.Restrictions;
import org.oopdev.jio.dsql.api.criteria.transform.Result;
import org.oopdev.jio.dsql.api.test.tools.DataTestTools;
import org.oopdev.jio.dsql.api.criteria.transform.Transformer;
import org.oopdev.jio.dsql.api.test.tools.MetaFinderJpaImplTest;
import org.oopdev.jio.dsql.api.test.tools.TransformerImplTest;
import org.oopdev.jio.dsql.api.test.tools.dto.UserDto;
import org.oopdev.jio.dsql.api.test.tools.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Created by kamilbukum on 28/01/2017.
 */
public class CriteriaTest extends DataTestTools {

    @Test
    public void list() throws Exception {
        Session session = sessionFactory.openSession();
        Transformer<User> entityTransformer = new TransformerImplTest<>(session, MetaFinderJpaImplTest.finderJpa);
        Transformer<UserDto> dtoTransformer = new TransformerImplTest<>(session, MetaFinderJpaImplTest.finderJpa);
        Transformer<Map<String, Object>> mapTransformer = new TransformerImplTest<>(session, MetaFinderJpaImplTest.finderJpa);

        // New Criteria For Entity
        List<User> userList = Criteria.createCriteria(User.class, entityTransformer).list();
        System.out.println(userList);
        // New Criteria For DTO
        List<UserDto> dtoList = Criteria.createCriteria(User.class, dtoTransformer).list();
        System.out.println(dtoList);
        // New Criteria For Map
        List<Map<String, Object>> mapList = Criteria.createCriteria(User.class, mapTransformer).list();
        System.out.println(mapList);
    }

    @Test
    public void pairList() throws Exception {
        Session session = sessionFactory.openSession();
        Transformer<User> entityTransformer = new TransformerImplTest<>(session, MetaFinderJpaImplTest.finderJpa);
        Transformer<UserDto> dtoTransformer = new TransformerImplTest<>(session, MetaFinderJpaImplTest.finderJpa);
        Transformer<Map<String, Object>> mapTransformer = new TransformerImplTest<>(session, MetaFinderJpaImplTest.finderJpa);

        // New Criteria For Entity
        Result<User> userResult = Criteria.createCriteria(User.class, entityTransformer).pairList();

        // New Criteria For DTO
        Result<UserDto> dtoResult = Criteria.createCriteria(User.class, dtoTransformer).pairList();

        // New Criteria For Map
        Result<Map<String, Object>> mapResult = Criteria.createCriteria(User.class, mapTransformer).pairList();
    }


    @Test
    public void count() throws Exception {
        Session session = sessionFactory.openSession();
        Transformer<User> entityTransformer = new TransformerImplTest<>(session, MetaFinderJpaImplTest.finderJpa);
        Transformer<UserDto> dtoTransformer = new TransformerImplTest<>(session, MetaFinderJpaImplTest.finderJpa);
        Transformer<Map<String, Object>> mapTransformer = new TransformerImplTest<>(session, MetaFinderJpaImplTest.finderJpa);


        // New Criteria For Entity
        Long userCount = Criteria.createCriteria(User.class, entityTransformer).count();

        // New Criteria For DTO
        Long dtoCount = Criteria.createCriteria(User.class, dtoTransformer).count();

        // New Criteria For Map
        Long mapCount = Criteria.createCriteria(User.class, mapTransformer).count();
    }


    @Test
    public void uniqueResult() throws Exception {
        Session session = sessionFactory.openSession();
        Transformer<User> entityTransformer = new TransformerImplTest<>(session, MetaFinderJpaImplTest.finderJpa);
        Transformer<UserDto> dtoTransformer = new TransformerImplTest<>(session, MetaFinderJpaImplTest.finderJpa);
        Transformer<Map<String, Object>> mapTransformer = new TransformerImplTest<>(session, MetaFinderJpaImplTest.finderJpa);

        // New Criteria For Entity
        User user = (User) Criteria.createCriteria(User.class, entityTransformer).add(Restrictions.filter("name", Operator.EQUALS, "Kamil")).uniqueResult();

        // New Criteria For DTO
        // UserDto dto = (UserDto) Criteria.createCriteria(User.class, dtoTransformer).add(Restrictions.filter("name", Operator.EQUALS, "Kamil")).uniqueResult();

        // New Criteria For Map
        // Map<String, Object> map = (Map<String, Object>) Criteria.createCriteria(User.class, mapTransformer).add(Restrictions.filter("name", Operator.EQUALS, "Kamil")).uniqueResult();
    }

    @Test
    public void createCriteria() throws Exception {
        Session session = sessionFactory.openSession();
        // New Criteria For Entity
        Transformer<User> userTransformer = new TransformerImplTest<User>(session, MetaFinderJpaImplTest.finderJpa);
        List<User> userList = Criteria.createCriteria(User.class, userTransformer).list();

        // New Criteria For DTO
        Transformer<UserDto> dtoTransformer = new TransformerImplTest<>(session, MetaFinderJpaImplTest.finderJpa);
        List<UserDto> dtoList = Criteria.createCriteria(User.class, dtoTransformer).list();

        // New Criteria For Map
        Transformer<Map<String, Object>> mapTransformer = new TransformerImplTest<>(session, MetaFinderJpaImplTest.finderJpa);
        List<Map<String, Object>> mapList = Criteria.createCriteria(User.class, mapTransformer).list();
    }
}