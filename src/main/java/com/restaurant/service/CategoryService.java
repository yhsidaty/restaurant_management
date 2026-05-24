package com.restaurant.service;

import com.restaurant.model.Category;
import com.restaurant.model.Plat;
import com.restaurant.repository.CategoryRepository;
import com.restaurant.repository.PlatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final PlatRepository platRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));
    }

    @Transactional
    public Category createCategory(String name) {
        if (categoryRepository.existsByName(name)) {
            throw new RuntimeException("Cette catégorie existe déjà");
        }
        return categoryRepository.save(Category.builder().name(name).build());
    }

    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Transactional
    public Plat createPlat(Long categoryId, String name, String description, Double price) {
        Category category = findById(categoryId);
        Plat plat = Plat.builder()
                .name(name)
                .description(description)
                .price(price)
                .category(category)
                .build();
        return platRepository.save(plat);
    }

    @Transactional
    public void deletePlat(Long platId) {
        platRepository.deleteById(platId);
    }

    public List<Plat> searchPlats(String query) {
        if (query == null || query.isBlank()) {
            return platRepository.findAll();
        }
        return platRepository.findByNameContainingIgnoreCaseOrCategoryNameContainingIgnoreCase(query, query);
    }

    public List<Plat> getAllPlats() {
        return platRepository.findAll();
    }
}
