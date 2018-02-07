package com.epam.rd.model;

import com.epam.rd.model.entity.EntityPackage;
import com.mysql.jdbc.Statement;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ModelPackage extends Model {
    Integer languageId;

    public final String GET_PACKAGES = "SELECT P.ID_PACKAGE, P.PRICE, P.TYPE, P.IMAGE, PD.NAME, PD.DESCRIPTION " +
            "FROM PACKAGE P " +
            "LEFT JOIN PACKAGE_DESCRIPTION PD ON (PD.ID_PACKAGE = P.ID_PACKAGE)" +
            "WHERE  PD.ID_LANGUAGE = %d ";

    public final String GET_PACKAGE_BY_ID = GET_PACKAGES + "AND P.ID_PACKAGE = %d ";

    private final String UPDATE_PACKAGE = "UPDATE `PACKAGE` %s WHERE `ID_PACKAGE`='%d';";
    private final String UPDATE_PACKAGE_DESCRIPTION = "UPDATE `PACKAGE_DESCRIPTION` %s WHERE `ID_PACKAGE`='%d' AND ID_LANGUAGE = %d ;";

    private final String DELETE = "DELETE FROM `PACKAGE` WHERE `ID_PACKAGE`=%d;";
    private final String CREATE_PACKAGE = "INSERT INTO PACKAGE (PRICE, TYPE, IMAGE) VALUES ('%.2f', '%d', '%s');";

    private final String CREATE_PACKAGE_DESCRIPTION = "INSERT INTO PACKAGE_DESCRIPTION " +
            "(ID_PACKAGE, NAME, DESCRIPTION, ID_LANGUAGE) VALUES ('%d', '%s', '%s', '%d');";

    private Connection connection;

    public ModelPackage(Connection connection, Integer languageId) {
        this.connection = connection;
        this.languageId = languageId;
    }

    public ModelPackage(Integer langId) {
        languageId = langId;
    }

    public EntityPackage loadById(Long id) {
        List<Map> collection = query(String.format(GET_PACKAGE_BY_ID, languageId, id));

        final EntityPackage[] packages = new EntityPackage[1];
        if (collection != null) {
            ArrayList<Long> attachedId = new ArrayList<>();
            collection.forEach((Map e) -> packages[0] = EntityPackage.builder()
                    .id(Long.valueOf((Integer) e.get("id_package")))
                    .price(((BigDecimal) e.get("price")).doubleValue())
                    .type((Integer) e.get("type"))
                    .image((String) e.get("image"))
                    .name((String) e.get("name"))
                    .description((String) e.get("description"))
                    .languageId(languageId)
                    .build());
        }
        return packages[0];
    }

    public List<EntityPackage> load(String whereCause) {
        List<Map> collection = query(String.format(GET_PACKAGES + whereCause, languageId));
        List<EntityPackage> list = new ArrayList<>();
        collection.forEach((Map e) -> list.add(
                EntityPackage.builder()
                        .id(Long.valueOf((Integer) e.get("id_package")))
                        .price(((BigDecimal) e.get("price")).doubleValue())
                        .type((Integer) e.get("type"))
                        .image((String) e.get("image"))
                        .name((String) e.get("name"))
                        .description((String) e.get("description"))
                        .languageId(languageId)
                        .build()
        ));
        return list;
    }

    public List<EntityPackage> load() {
        return load("");
    }

    public void update(EntityPackage item) {
        if (item.getId() != null) {

            String genFieldEntityPackage = "SET" + String.join(",",
                    packSet("ID_PACKAGE", Double.valueOf(item.getId())),
                    packSet("PRICE", item.getPrice()),
                    packSet("TYPE", item.getType()),
                    packSet("IMAGE", item.getImage()));

            String genFieldEntityPackageDescription = "SET" +
                    String.join(",",
                            packSet("NAME", item.getName()),
                            packSet("ID_LANGUAGE", item.getLanguageId()),
                            packSet("DESCRIPTION", item.getDescription())
                    );

            update(String.format(UPDATE_PACKAGE, genFieldEntityPackage, item.getId()));
            update(String.format(UPDATE_PACKAGE_DESCRIPTION, genFieldEntityPackageDescription, item.getId(), languageId));
        } else {
            throw new UnsupportedOperationException("EntityPackage id is null");
        }
    }

    public void delete(EntityPackage item) {
        update(String.format(DELETE, item.getId()));
    }

    public void create(EntityPackage item) {
        update(String.format(
                CREATE_PACKAGE,
                item.getPrice(),
                item.getType(),
                item.getImage()
                )
        );
        update(String.format(
                CREATE_PACKAGE_DESCRIPTION,
                lastId, item.getName(),
                item.getDescription(),
                item.getLanguageId()));
    }

    public boolean addProduct(EntityPackage item) throws Exception {
        String query = "INSERT INTO PACKAGE (PRICE, TYPE, IMAGE) VALUES (?,?,?)";
        boolean rowInserted = false;

        PreparedStatement statement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS);

        statement.setDouble(1, item.getPrice());
        statement.setInt(2, item.getType());
        statement.setString(3, item.getImage());
        statement.executeUpdate();
        long itemId = 0;
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                item.setId((long) generatedKeys.getInt(1));
                itemId = generatedKeys.getInt(1);
            }
        }

        String query2 = "INSERT INTO PACKAGE_DESCRIPTION (ID_PACKAGE, NAME, DESCRIPTION, ID_LANGUAGE) VALUES (?,?,?,?)";
        PreparedStatement statement2 = connection.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);
        statement2.setLong(1, itemId);
        statement2.setString(2, item.getName());
        statement2.setString(3, item.getDescription());
        statement2.setInt(4, item.getLanguageId());
        statement2.executeUpdate();

        try (ResultSet generatedKeys = statement2.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                generatedKeys.getInt(1);
            }
        }

        return rowInserted;
    }
}

