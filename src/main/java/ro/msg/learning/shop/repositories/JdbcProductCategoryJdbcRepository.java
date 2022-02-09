package ro.msg.learning.shop.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.entities.ProductCategoryJdbc;
import ro.msg.learning.shop.entities.ProductJdbc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JdbcProductCategoryJdbcRepository implements ProductCategoryJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public ProductCategoryJdbc save(ProductCategoryJdbc productCategoryJdbc) {
        if (productCategoryJdbc.getId() != 0) {
            update(productCategoryJdbc);
        } else {
            int id = insert(productCategoryJdbc);
            productCategoryJdbc.setId(id);
        }
        return productCategoryJdbc;
    }

    @Override
    public Optional<ProductCategoryJdbc> findById(int id) {
        List<ProductCategoryJdbc> jdbcProductCategories = jdbcTemplate
                .query("SELECT * FROM product_category_jdbc WHERE id = ?", (resultSet, i) -> new ProductCategoryJdbc(resultSet
                        .getInt("id"), resultSet.getString("name"), resultSet.getString("description")), id);
        return jdbcProductCategories.isEmpty() ? Optional.empty() : Optional.of(jdbcProductCategories.get(0));
    }

    @Override
    public void remove(ProductCategoryJdbc productCategoryJdbc) {
        jdbcTemplate.update("DELETE FROM product_category_jdbc WHERE id =?", productCategoryJdbc.getId());
    }

    @Override
    public List<ProductCategoryJdbc> findAll() {
        return jdbcTemplate
                .query("SELECT * FROM product_category_jdbc", (resultSet, i) -> new ProductCategoryJdbc(resultSet
                        .getInt("id"), resultSet
                        .getString("name"), resultSet.getString("description")));
    }

    private int insert(ProductCategoryJdbc productCategoryJdbc) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
        insert.setTableName("product_category_jdbc");
        insert.setGeneratedKeyName("id");

        Map<String, Object> data = new HashMap<>();
        data.put("name", productCategoryJdbc.getName());
        data.put("description", productCategoryJdbc.getDescription());
        return insert.executeAndReturnKey(data).intValue();
    }

    private void update(ProductCategoryJdbc productCategoryJdbc) {
        jdbcTemplate
                .update("UPDATE product_category_jdbc SET name = ?,description= ?,price= ?,weight= ?, category= ?, supplier= ?, image_url= ? WHERE id = ?", productCategoryJdbc
                        .getName(), productCategoryJdbc.getDescription());
    }
}
