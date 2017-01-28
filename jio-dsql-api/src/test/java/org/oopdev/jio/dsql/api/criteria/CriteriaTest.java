package org.oopdev.jio.dsql.api.criteria;

import org.junit.Test;
import static org.junit.Assert.*;

import org.oopdev.jio.dsql.api.criteria.transform.Transformer;
import org.oopdev.jio.dsql.api.example.TransformerImplTest;
import org.oopdev.jio.dsql.api.example.dto.UserDto;
import org.oopdev.jio.dsql.api.example.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Created by kamilbukum on 28/01/2017.
 */
public class CriteriaTest {
    @Test
    public void getTransformClass() throws Exception {
        // Entity Class Test
        Class<User> entityClass = Criteria.forEntity(User.class).getTransformClass();
        assertNotNull(entityClass);
        assertEquals(entityClass, User.class);

        // DTO Class
        Class<UserDto> clazz = Criteria.forDto(User.class, UserDto.class).getTransformClass();
        assertNotNull(clazz);
        assertEquals(clazz, UserDto.class);

        // Map class
        Class<Map<String, Object>> mapClass = Criteria.forMap(User.class).getTransformClass();
        assertNotNull(mapClass);
        assertEquals(mapClass, Criteria.MAP_CLASS);

        // New Criteria For Entity
        entityClass = Criteria.newCriteria(User.class, new TransformerImplTest<>(User.class)).getTransformClass();
        assertNotNull(entityClass);
        assertEquals(entityClass, User.class);

        // New Criteria For DTO
        clazz  = Criteria.newCriteria(User.class, new TransformerImplTest<>(UserDto.class)).getTransformClass();
        assertNotNull(clazz);
        assertEquals(clazz, UserDto.class);

        // New Criteria For Map
        mapClass  = Criteria.newCriteria(User.class, new TransformerImplTest<>(Criteria.MAP_CLASS)).getTransformClass();
        assertNotNull(mapClass);
        assertEquals(mapClass, Criteria.MAP_CLASS);
    }


    @Test
    public void getEntityClass() throws Exception {
        // Entity Class Test
        Class<?> entityClass = Criteria.forEntity(User.class).getEntityClass();
        assertNotNull(entityClass);
        assertEquals(entityClass, User.class);

        // DTO Class
        Class<?> entityClassForDto = Criteria.forDto(User.class, UserDto.class).getEntityClass();
        assertNotNull(entityClassForDto);
        assertEquals(entityClassForDto, User.class);

        // Map class

        Class<?> entityClassForMap = Criteria.forMap(User.class).getEntityClass();
        assertNotNull(entityClassForMap);
        assertEquals(entityClassForMap, User.class);


        // New Criteria For Entity
        entityClass = Criteria.newCriteria(User.class, new TransformerImplTest<>(User.class)).getEntityClass();
        assertNotNull(entityClass);
        assertEquals(entityClass, User.class);

        // New Criteria For DTO
        entityClass  = Criteria.newCriteria(User.class, new TransformerImplTest<>(UserDto.class)).getEntityClass();
        assertNotNull(entityClass);
        assertEquals(entityClass, User.class);

        // New Criteria For Map
        entityClass  = Criteria.newCriteria(User.class, new TransformerImplTest<>(Criteria.MAP_CLASS)).getEntityClass();
        assertNotNull(entityClass);
        assertEquals(entityClass, User.class);
    }

    @Test
    public void restrict() throws Exception {

    }

    @Test
    public void setGetTransformer() throws Exception {
        // Entity Class Test
        Transformer<User> userTransformer = new TransformerImplTest<>(User.class);
        Transformer<User> transformer = Criteria.forEntity(User.class).setTransformer(userTransformer).getTransformer();
        assertNotNull(transformer);
        assertEquals(transformer, userTransformer);


        // DTO Class
        Transformer<UserDto> dtoTransformer = new TransformerImplTest<>(UserDto.class);
        Transformer<UserDto> dtoResultTransformer = Criteria.forDto(User.class, UserDto.class).setTransformer(dtoTransformer).getTransformer();
        assertNotNull(dtoResultTransformer);
        assertEquals(dtoResultTransformer, dtoTransformer);

        // Map class
        Transformer<Map<String, Object>> mapTransformer = new TransformerImplTest<>(Criteria.MAP_CLASS);
        Transformer<Map<String, Object>> mapResultTransformer = Criteria.forMap(User.class).setTransformer(mapTransformer).getTransformer();
        assertNotNull(mapResultTransformer);
        assertEquals(mapResultTransformer, mapTransformer);



        // New Criteria For Entity
        transformer = Criteria.newCriteria(User.class, userTransformer).getTransformer();
        assertNotNull(transformer);
        assertEquals(transformer, userTransformer);

        // New Criteria For DTO
        dtoResultTransformer = Criteria.newCriteria(User.class, dtoTransformer).getTransformer();
        assertNotNull(dtoResultTransformer);
        assertEquals(dtoResultTransformer, dtoTransformer);

        // New Criteria For Map
        mapResultTransformer = Criteria.newCriteria(User.class, mapTransformer).getTransformer();
        assertNotNull(mapResultTransformer);
        assertEquals(mapResultTransformer, mapTransformer);
    }

    @Test
    public void list() throws Exception {
        // Entity Class Test
        Transformer<User> userTransformer = new TransformerImplTest<>(User.class);
        List<User> userList = Criteria.forEntity(User.class).setTransformer(userTransformer).list();

        // DTO Class
        Transformer<UserDto> dtoTransformer = new TransformerImplTest<>(UserDto.class);
        List<UserDto> dtoList = Criteria.forDto(User.class, UserDto.class).setTransformer(dtoTransformer).list();

        // Map class
        Transformer<Map<String, Object>> mapTransformer = new TransformerImplTest<>(Criteria.MAP_CLASS);
        List<Map<String, Object>> mapList = Criteria.forMap(User.class).setTransformer(mapTransformer).list();



        // New Criteria For Entity
        userList = Criteria.newCriteria(User.class, userTransformer).list();

        // New Criteria For DTO
        dtoList = Criteria.newCriteria(User.class, dtoTransformer).list();

        // New Criteria For Map
        mapList = Criteria.newCriteria(User.class, mapTransformer).list();
    }

    @Test
    public void forEntity() throws Exception {
        // Entity Class Test
        Transformer<User> userTransformer = new TransformerImplTest<>(User.class);
        List<User> userList = Criteria.forEntity(User.class).setTransformer(userTransformer).list();
    }

    @Test
    public void forDto() throws Exception {

        // DTO Class
        Transformer<UserDto> dtoTransformer = new TransformerImplTest<>(UserDto.class);
        List<UserDto> dtoList = Criteria.forDto(User.class, UserDto.class).setTransformer(dtoTransformer).list();

    }

    @Test
    public void forMap() throws Exception {
        // Map class
        Transformer<Map<String, Object>> mapTransformer = new TransformerImplTest<>(Criteria.MAP_CLASS);
        List<Map<String, Object>> mapList = Criteria.forMap(User.class).setTransformer(mapTransformer).list();

    }

    @Test
    public void newCriteria() throws Exception {
        // New Criteria For Entity
        Transformer<User> userTransformer = new TransformerImplTest<>(User.class);
        List<User> userList = Criteria.newCriteria(User.class, userTransformer).list();

        // New Criteria For DTO
        Transformer<UserDto> dtoTransformer = new TransformerImplTest<>(UserDto.class);
        List<UserDto> dtoList = Criteria.newCriteria(User.class, dtoTransformer).list();

        // New Criteria For Map
        Transformer<Map<String, Object>> mapTransformer = new TransformerImplTest<>(Criteria.MAP_CLASS);
        List<Map<String, Object>> mapList = Criteria.newCriteria(User.class, mapTransformer).list();

    }


}