package ro.msg.learning.shop.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.entities.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JdbcProductJdbcRepository implements ProductJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public ProductJdbc save(ProductJdbc productJdbc) {
        if (productJdbc.getId() != 0) {
            update(productJdbc);
        } else {
            int id = insert(productJdbc);
            productJdbc.setId(id);
        }
        return productJdbc;
    }

    @Override
    public Optional<ProductJdbc> findById(int id) {
        List<ProductJdbc> jdbcProducts = jdbcTemplate
                .query("SELECT * FROM product WHERE id = ?", (resultSet, i) -> new ProductJdbc(resultSet
                        .getInt("id"), resultSet
                        .getString("name"), resultSet.getString("description"), resultSet
                        .getBigDecimal("price"), resultSet.getDouble("weight"), resultSet
                        .getInt("category"), resultSet.getInt("supplier"), resultSet
                        .getString("image_url")), id);
        return jdbcProducts.isEmpty() ? Optional.empty() : Optional.of(jdbcProducts.get(0));
    }

    @Override
    public void remove(ProductJdbc productJdbc) {
        jdbcTemplate.update("DELETE FROM product WHERE id =?", productJdbc.getId());
    }

    @Override
    public List<ProductJdbc> findAll() {
        return jdbcTemplate
                .query("SELECT * FROM product_jdbc", (resultSet, i) -> new ProductJdbc(resultSet.getInt("id"), resultSet
                        .getString("name"), resultSet.getString("description"), resultSet
                        .getBigDecimal("price"), resultSet.getDouble("weight"), resultSet
                        .getInt("category"), resultSet.getInt("supplier"), resultSet
                        .getString("image_url")));
    }

    private int insert(ProductJdbc productJdbc) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
        insert.setTableName("product");
        insert.setGeneratedKeyName("id");

        Map<String, Object> data = new HashMap<>();
        data.put("name", productJdbc.getName());
        data.put("description", productJdbc.getDescription());
        data.put("price", productJdbc.getPrice());
        data.put("weight", productJdbc.getWeight());
        data.put("category", productJdbc.getCategory());
        data.put("supplier", productJdbc.getSupplier());
        data.put("image_url", productJdbc.getImageUrl());
        return insert.executeAndReturnKey(data).intValue();
    }

    private void update(ProductJdbc productJdbc) {
        jdbcTemplate
                .update("UPDATE product SET name = ?,description= ?,price= ?,weight= ?, category= ?, supplier= ?, image_url= ? WHERE id = ?", productJdbc
                        .getName(), productJdbc.getDescription(), productJdbc.getPrice(), productJdbc
                        .getWeight(), productJdbc.getCategory(), productJdbc.getSupplier(), productJdbc.getImageUrl());
    }
}
