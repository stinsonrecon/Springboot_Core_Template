package vn.com.hust.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.hust.admin.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
