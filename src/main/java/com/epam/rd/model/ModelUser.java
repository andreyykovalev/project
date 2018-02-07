package com.epam.rd.model;

import com.epam.rd.model.entity.EntityUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ModelUser extends Model {
    private static final String CREATE_ADMIN = "INSERT INTO `USER` (`LOGIN`, `PASSWORD`, `LEVEL`) VALUES ('%s','%s',%d);";
    private static final String UPDATE_ADMIN = "UPDATE USER SET LOGIN = '%s', PASSWORD = '%S', LEVEL = %d WHERE ID_USER = %d";
    private static final String DELETE_ADMIN = "DELETE FROM USER WHERE ID_USER = %d";
    private static final String LOAD_BY_ID = "SELECT * FROM USER WHERE ID_USER = %d";
    private static final String LOAD_ALL = "SELECT ID_USER FROM USER";
    private static final String GET_USER_BY_MAIL = "SELECT * FROM USER WHERE LOGIN = '%s'";

    public void create(EntityUser user) {
        update(String.format(CREATE_ADMIN, user.getLogin(), user.getPassword(), user.getLevel()));
    }

    public void update(EntityUser user) {
        update(String.format(UPDATE_ADMIN, user.getLogin(), user.getPassword(), user.getLevel(), user.getId()));
    }

    public void delete(EntityUser user) {
        update(String.format(DELETE_ADMIN, user.getId()));
    }

    public EntityUser load(Long id) {
        List<Map> collection = query(String.format(LOAD_BY_ID, id));

        final EntityUser[] packages = new EntityUser[1];
        if (collection != null) {
            collection.forEach((Map e) -> packages[0] = EntityUser.builder()
                    .id(id)
                    .login(((String) e.get("login")))
                    .password((String) e.get("password"))
                    .level((Integer) e.get("level"))
                    .build());
        }
        return packages[0];
    }

    public EntityUser loadByLogin(String login) {
        List<Map> collection = query(String.format(GET_USER_BY_MAIL, login));

        final EntityUser[] packages = new EntityUser[1];
        if (collection != null) {
            collection.forEach((Map e) -> packages[0] = EntityUser.builder()
                    .id(Long.valueOf((Integer) e.get("id_user")))
                    .login(((String) e.get("login")))
                    .password((String) e.get("password"))
                    .level((Integer) e.get("level"))
                    .build());
        }
        return packages[0];
    }

    public List<EntityUser> load() {
        List<Map> collection = query(LOAD_ALL);
        List<EntityUser> list = new ArrayList<>();
        collection.forEach((Map e) -> list.add(load(Long.valueOf((Integer) e.get("id_user")))));
        return list;
    }
}
