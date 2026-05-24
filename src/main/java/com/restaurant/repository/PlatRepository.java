package com.restaurant.repository;

import com.restaurant.model.Plat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlatRepository extends JpaRepository<Plat, Long> {
    List<Plat> findByNameContainingIgnoreCase(String name);
    List<Plat> findByCategoryId(Long categoryId);
    List<Plat> findByNameContainingIgnoreCaseOrCategoryNameContainingIgnoreCase(String name, String category);
}
