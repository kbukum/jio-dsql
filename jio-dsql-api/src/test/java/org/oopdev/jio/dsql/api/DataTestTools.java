package org.oopdev.jio.dsql.api;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.BeforeClass;
import org.oopdev.jio.dsql.api.example.entity.Role;
import org.oopdev.jio.dsql.api.example.entity.User;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kamilbukum on 28/01/2017.
 */
public class DataTestTools {
    protected static SessionFactory sessionFactory;
    private static List<Role> roles = new LinkedList<>();
    private static List<User> users = new LinkedList<>();

    @BeforeClass
    public static void beforeClass(){
        if(sessionFactory == null) {
            sessionFactory = HibernateUtil.getSessionFactory(DataTestTools.class, "org.oopdev.jio.dsql.api.example.entity");
            addRolesAndUsers();
            Session session = sessionFactory.openSession();
            session.getTransaction().begin();

            if(roles.size() > 0) {
                for(Role role: roles) {
                    session.persist(role);
                }
            }

            if(users.size() > 0 && roles.size() > 0) {
                for(User user: users) {
                    Role role = roles.get(0);
                    user.setRoleOid(role.getOid());
                    session.persist(user);
                }
            }
            session.getTransaction().commit();
        }
    }

    private static void addRolesAndUsers() {

        roles.add(new Role("Role1", "Example First Role"));
        roles.add(new Role("Role2", "Example Second Role"));
        roles.add(new Role("Role3", "Example Third Role"));
        roles.add(new Role("Role4", "Example Fourth Role"));
        roles.add(new Role("Role5", "Example Fifth Role"));
        roles.add(new Role("Role6", "Example Fifth Role"));

        users.add(new User(
                "admin@robe.io",
                "Kamil",
                "Bukum",
                "35345435345345",
                true,
                10,
                new Date(),
                new Date(),
                null,
                "Transient Value"
        ));

        users.add(new User(
                "seray@robe.io",
                "Seray",
                "Uzgur",
                "35345435345345",
                false,
                5,
                new Date(),
                new Date(),
                null,
                "Transient Value"
        ));

        users.add(new User(
                "hasan@robe.io",
                "Hasan",
                "Mumin",
                "35345435345345",
                true,
                7,
                new Date(),
                new Date(),
                null,
                "Transient Value"
        ));
    }
}
