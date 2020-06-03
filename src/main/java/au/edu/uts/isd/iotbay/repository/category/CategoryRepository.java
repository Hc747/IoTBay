package au.edu.uts.isd.iotbay.repository.category;

import au.edu.uts.isd.iotbay.model.category.Category;
import au.edu.uts.isd.iotbay.persistence.jdbc.ConnectionProvider;
import au.edu.uts.isd.iotbay.repository.Repository;

import java.util.Optional;

public interface CategoryRepository extends Repository<Category> {
    Optional<Category> findByCategoryId(int id);

    Optional<Category> findByCategoryName(String name);

    static CategoryRepository create(ConnectionProvider datasource) {
        if (datasource == null) {
            return InMemoryCategoryRepository.concurrent();
        }
        return new PersistentCategoryRepository(datasource);
    }

}
