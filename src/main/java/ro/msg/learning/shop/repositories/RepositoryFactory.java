package ro.msg.learning.shop.repositories;

@FunctionalInterface
public interface RepositoryFactory {
    ProductJdbcRepository createProductJdbcRepository();
}
