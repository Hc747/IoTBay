package au.edu.uts.isd.iotbay.repository.category;

import au.edu.uts.isd.iotbay.model.category.Category;
import au.edu.uts.isd.iotbay.persistence.mongo.MongoDatabaseProvider;
import au.edu.uts.isd.iotbay.repository.MongoRepository;

import static com.mongodb.client.model.Filters.eq;

public class MongoCategoryRepository extends MongoRepository<Category> implements CategoryRepository {

    protected MongoCategoryRepository(MongoDatabaseProvider datasource) {
        super(datasource, Category.class);
    }

    @Override
    public Category findByName(String name) {
        return find(eq("name", name));
    }
}
