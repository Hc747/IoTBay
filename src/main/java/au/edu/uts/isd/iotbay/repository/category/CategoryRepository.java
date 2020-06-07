package au.edu.uts.isd.iotbay.repository.category;

import au.edu.uts.isd.iotbay.model.category.Category;
import au.edu.uts.isd.iotbay.persistence.mongo.MongoDatabaseProvider;
import au.edu.uts.isd.iotbay.repository.Repository;
import org.bson.types.ObjectId;

public interface CategoryRepository extends Repository<Category> {

    Category findById(ObjectId id);

    Category findByName(String name);

    static CategoryRepository create(MongoDatabaseProvider datasource) {
        if (datasource == null) {
            return new InMemoryCategoryRepository();
        }
        return new MongoCategoryRepository(datasource);
    }
}
