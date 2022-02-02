package ro.msg.learning.shop.repositories;

@FunctionalInterface
public interface ProductCategoryJdbcRepositoryFactory {
    ProductCategoryJdbcRepository createProductCategoryJdbcRepository();
}
