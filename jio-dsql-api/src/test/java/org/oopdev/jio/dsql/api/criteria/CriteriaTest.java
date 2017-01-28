package org.oopdev.jio.dsql.api.criteria;

import org.hibernate.Session;
import org.junit.Test;
import static org.junit.Assert.*;

import org.oopdev.jio.dsql.api.criteria.transform.Result;
import org.oopdev.jio.dsql.api.test.tools.DataTestTools;
import org.oopdev.jio.dsql.api.criteria.transform.Transformer;
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
        Transformer<User> entityTransformer = new TransformerImplTest<>(User.class, session);
        Transformer<UserDto> dtoTransformer = new TransformerImplTest<>(UserDto.class, session);
        Transformer<Map<String, Object>> mapTransformer = new TransformerImplTest<>(Transformer.MAP_CLASS, session);

        // New Criteria For Entity
        List<User> userList = Criteria.newCriteria(User.class, entityTransformer).list();

        // New Criteria For DTO
        List<UserDto> dtoList = Criteria.newCriteria(User.class, dtoTransformer).list();

        // New Criteria For Map
        List<Map<String, Object>> mapList = Criteria.newCriteria(User.class, mapTransformer).list();
    }

    @Test
    public void pairList() throws Exception {
        Session session = sessionFactory.openSession();
        Transformer<User> entityTransformer = new TransformerImplTest<>(User.class, session);
        Transformer<UserDto> dtoTransformer = new TransformerImplTest<>(UserDto.class, session);
        Transformer<Map<String, Object>> mapTransformer = new TransformerImplTest<>(Transformer.MAP_CLASS, session);

        // New Criteria For Entity
        Result<User> userResult = Criteria.newCriteria(User.class, entityTransformer).pairList();

        // New Criteria For DTO
        Result<UserDto> dtoResult = Criteria.newCriteria(User.class, dtoTransformer).pairList();

        // New Criteria For Map
        Result<Map<String, Object>> mapResult = Criteria.newCriteria(User.class, mapTransformer).pairList();
    }


    @Test
    public void count() throws Exception {
        Session session = sessionFactory.openSession();
        Transformer<User> entityTransformer = new TransformerImplTest<>(User.class, session);
        Transformer<UserDto> dtoTransformer = new TransformerImplTest<>(UserDto.class, session);
        Transformer<Map<String, Object>> mapTransformer = new TransformerImplTest<>(Transformer.MAP_CLASS, session);


        // New Criteria For Entity
        Long userCount = Criteria.newCriteria(User.class, entityTransformer).count();

        // New Criteria For DTO
        Long dtoCount = Criteria.newCriteria(User.class, dtoTransformer).count();

        // New Criteria For Map
        Long mapCount = Criteria.newCriteria(User.class, mapTransformer).count();
    }

    @Test
    public void findOne() throws Exception {
        Session session = sessionFactory.openSession();
        Transformer<User> entityTransformer = new TransformerImplTest<>(User.class, session);
        Transformer<UserDto> dtoTransformer = new TransformerImplTest<>(UserDto.class, session);
        Transformer<Map<String, Object>> mapTransformer = new TransformerImplTest<>(Transformer.MAP_CLASS, session);

        // New Criteria For Entity
        User user = Criteria.newCriteria(User.class, entityTransformer).findOne();

        // New Criteria For DTO
        UserDto dto = Criteria.newCriteria(User.class, dtoTransformer).findOne();

        // New Criteria For Map
        Map<String, Object> map = Criteria.newCriteria(User.class, mapTransformer).findOne();
    }


    @Test
    public void uniqueResult() throws Exception {
        Session session = sessionFactory.openSession();
        Transformer<User> entityTransformer = new TransformerImplTest<>(User.class, session);
        Transformer<UserDto> dtoTransformer = new TransformerImplTest<>(UserDto.class, session);
        Transformer<Map<String, Object>> mapTransformer = new TransformerImplTest<>(Transformer.MAP_CLASS, session);

        // New Criteria For Entity
        User user = (User) Criteria.newCriteria(User.class, entityTransformer).uniqueResult();

        // New Criteria For DTO
        UserDto dto = (UserDto) Criteria.newCriteria(User.class, dtoTransformer).uniqueResult();

        // New Criteria For Map
        Map<String, Object> map = (Map<String, Object>) Criteria.newCriteria(User.class, mapTransformer).uniqueResult();
    }

    @Test
    public void newCriteria() throws Exception {
        Session session = sessionFactory.openSession();
        // New Criteria For Entity
        Transformer<User> userTransformer = new TransformerImplTest<>(User.class, session);
        List<User> userList = Criteria.newCriteria(User.class, userTransformer).list();

        // New Criteria For DTO
        Transformer<UserDto> dtoTransformer = new TransformerImplTest<>(UserDto.class, session);
        List<UserDto> dtoList = Criteria.newCriteria(User.class, dtoTransformer).list();

        // New Criteria For Map
        Transformer<Map<String, Object>> mapTransformer = new TransformerImplTest<>(Transformer.MAP_CLASS, session);
        List<Map<String, Object>> mapList = Criteria.newCriteria(User.class, mapTransformer).list();
    }
}