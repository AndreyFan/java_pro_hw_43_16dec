package de.telran.onlineshop.repository;

import de.telran.onlineshop.entity.CategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository // начиная со Спринг 3.0 можно не ставить
public interface CategoriesRepository extends JpaRepository<CategoriesEntity, Long> { // Long - это тип поля с автоинкрементом
    @Query("SELECT ce FROM CategoriesEntity ce WHERE ce.name=?1 ")
    CategoriesEntity findByName(String name);

    // Чистый SQL
    @Query(value = "SELECT * FROM Categories ce WHERE ce.Name=?1", nativeQuery = true)
    CategoriesEntity findByNameNative(String name);

}
