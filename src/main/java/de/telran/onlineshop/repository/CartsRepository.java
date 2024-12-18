package de.telran.onlineshop.repository;

import de.telran.onlineshop.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartsRepository extends JpaRepository<CartEntity, Long> {
}
