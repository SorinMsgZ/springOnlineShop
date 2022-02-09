package ro.msg.learning.shop.repositories;

@FunctionalInterface
public interface ProductJdbcRepositoryFactory {
    ProductJdbcRepository createProductJdbcRepository();
}
