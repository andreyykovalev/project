import com.epam.rd.DataBaseUtility;
import com.epam.rd.model.ModelPackage;
import com.epam.rd.model.entity.EntityPackage;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;


public class ModelPackageTest {
    private static final double DELTA = 1e-15;
    private BasicDataSource dataSource = DataBaseUtility.getDataSource();
    ModelPackage sut = new ModelPackage(dataSource.getConnection(), 1);

    public ModelPackageTest() throws SQLException {
    }


    @Test
    public void createRecord() throws Exception {
        EntityPackage s = EntityPackage.builder()
                .image("image")
                .name("internet")
                .type(1)
                .languageId(1)
                .price(160.0)
                .description("internet 30mb/s").build();

        sut.addProduct(s);

        Assert.assertEquals("internet", sut.loadById(s.getId()).getName());
        Assert.assertEquals("internet 30mb/s", sut.loadById(s.getId()).getDescription());
        Assert.assertEquals("image", sut.loadById(s.getId()).getImage());
        Assert.assertEquals(160.00, sut.loadById(s.getId()).getPrice(), DELTA);
        Assert.assertEquals(1L, (long) sut.loadById(s.getId()).getLanguageId());
        sut.delete(s);
    }

    @Test
    public void update() throws Exception {
        EntityPackage s = EntityPackage.builder()
                .image("image")
                .name("internet")
                .type(1)
                .languageId(1)
                .price(160.0)
                .description("internet 30mb/s").build();

        sut.addProduct(s);

        sut.update(
                EntityPackage.builder()
                        .id(s.getId())
                        .description("internet extra plus")
                        .name("internet updated")
                        .image("picture")
                        .type(1)
                        .languageId(1)
                        .price(200.0)
                        .build()
        );
        Assert.assertEquals(200.0, sut.loadById(s.getId()).getPrice(), DELTA);
        Assert.assertEquals("internet extra plus", sut.loadById(s.getId()).getDescription());
        Assert.assertEquals("picture", sut.loadById(s.getId()).getImage());
        Assert.assertEquals("internet updated", sut.loadById(s.getId()).getName());
        sut.delete(s);
    }

    @Test
    public void delete() throws Exception {
        EntityPackage s = EntityPackage.builder()
                .image("image")
                .name("internet")
                .type(1)
                .languageId(1)
                .price(160.0)
                .description("internet 30mb/s").build();

        sut.addProduct(s);

        List<EntityPackage> packagesBeforeDelete = sut.load();

        sut.delete(
                EntityPackage.builder()
                        .id(s.getId())
                        .build()
        );
        List<EntityPackage> packagesAfterDelete = sut.load();

        Assert.assertEquals(packagesBeforeDelete.size(),
                            packagesAfterDelete.size() + 1);


    }

    @Test
    public void getExistEntity() throws Exception {
        EntityPackage s = EntityPackage.builder()
                .image("image")
                .name("internet")
                .type(1)
                .languageId(1)
                .price(160.0)
                .description("internet 30mb/s").build();

        sut.addProduct(s);

        Assert.assertNotNull(sut.loadById(s.getId()));

        sut.delete(s);
    }

    @Test
    public void getCollectionEntity() {
        List<EntityPackage> packages = sut.load();
        for (int i = 0; i < packages.size(); i++) {
            Assert.assertNotNull(packages.get(i));
        }
    }

}
