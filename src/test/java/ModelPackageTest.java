import com.epam.rd.model.ModelCustomer;
import com.epam.rd.model.ModelPackage;
import com.epam.rd.model.entity.EntityPackage;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;


public class ModelPackageTest {
    ModelPackage element = new ModelPackage(1);


    @Test
    public void createRecord() {
        EntityPackage s = EntityPackage.builder()
                .image("image")
                .name("LastName")
                .type(1)
                .languageId(1)
                .price(160.0)
                .description("LOOL").build();

        element.create(s);
    }

    @Test
    public void update() {
        element.update(
                EntityPackage.builder()
                        .id(15L)
                        .description("DESCRIPTIasdf as dfas dfON")
                        .name("name123123")
                        .image("immmmmg")
                        .type(1)
                        .languageId(1)
                        .build()
        );
    }

    @Test
    public void delete() {
        element.delete(
                EntityPackage.builder()
                        .id(15L)
                        .build()
        );
    }

    @Test
    public void getExistEntity() {
        Assert.assertNotNull(element.loadById(15L));
    }

    @Test
    public void getCollectionEntity() {
        Assert.assertNotNull(element.load());
    }

}
