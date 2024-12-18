package de.telran.onlineshop.service;

import de.telran.onlineshop.entity.CategoriesEntity;
import de.telran.onlineshop.dto.CategoryDto;
import de.telran.onlineshop.repository.CategoriesRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor  // добавиться конструктор в который включатся все
public class CategoriesService {

    private final CategoriesRepository categoriesRepository;
    @Autowired
    private Random taskRandom1;

    @Autowired
    @Qualifier("taskRandom2")
    private Random otherRandom;
    private List<CategoryDto> categoryDtoList;

    @PostConstruct
    void init() {
        CategoriesEntity category1 = new CategoriesEntity(null, "Продукты");
        categoriesRepository.save(category1);
        CategoriesEntity category2 = new CategoriesEntity(null, "Быт.химия");
        categoriesRepository.save(category2);
        CategoriesEntity category3 = new CategoriesEntity(null, "Радиотехника");
        categoriesRepository.save(category3);
        CategoriesEntity category4 = new CategoriesEntity(null, "Игрушки");
        categoriesRepository.save(category4);
        CategoriesEntity category5 = new CategoriesEntity(null, "Одежда");
        categoriesRepository.save(category5);
        CategoriesEntity category6 = new CategoriesEntity(null, "Other");
        categoriesRepository.save(category6);
        category6.setName("Другие");

//        CategoriesEntity updateCategory = new CategoriesEntity(taskRandom1.nextLong(5), "Другие_2");
//        categoriesRepository.save(updateCategory);

        System.out.println("Выполняем логику при создании объекта " + this.getClass().getName());
    }

    public List<CategoryDto> getAllCategories() {
        List<CategoriesEntity> categoriesEntities = categoriesRepository.findAll();
        return categoriesEntities.stream()
                .map(entity -> new CategoryDto(entity.getCategoryId(), entity.getName()))
                .collect(Collectors.toList());
    }


    public CategoryDto getCategoryById(Long id) { ///categories/find/3

        CategoriesEntity categoriesEntity = categoriesRepository.findById(id).orElse(new CategoriesEntity());
        return new CategoryDto(categoriesEntity.getCategoryId(), categoriesEntity.getName());
    }

    public CategoryDto getCategoryByName(String name) { ///categories/get?name=Other,k=2
        CategoriesEntity categoriesEntity = categoriesRepository.findByName(name); // используем OQL
        //  CategoriesEntity categoriesEntity = categoriesRepository.findByNameNative(name); // используем native SQL

        return new CategoryDto(categoriesEntity.getCategoryId(), categoriesEntity.getName());
    }

    public boolean createCategories(CategoryDto newCategory) { //insert
        CategoriesEntity createCategoryEntity = new CategoriesEntity(null, newCategory.getName());
        CategoriesEntity returnCategoryEntity = categoriesRepository.save(createCategoryEntity);

        return returnCategoryEntity.getCategoryId() != null;
    }

    public CategoryDto updateCategories(CategoryDto updCategory) { //update
        CategoriesEntity createCategoryEntity = new CategoriesEntity(updCategory.getCategoryID(), updCategory.getName());
        CategoriesEntity returnCategoryEntity = categoriesRepository.save(createCategoryEntity);
        // трансформируем данные из Entity в Dto и возвращаем пользователю
        return new CategoryDto(returnCategoryEntity.getCategoryId(), returnCategoryEntity.getName());
    }

    public void deleteCategories(Long id) { //delete
       // categoriesRepository.deleteById(id); //первый вариант реализации Delete, менее информативно

        //второй вариант реализации Delete
        CategoriesEntity categories = categoriesRepository.findById(id).orElse(null);
        if (categories == null) {
            throw new RuntimeException("Нету такого объекта с Id = " + id);
        } else {
            categoriesRepository.delete(categories);
        }
    }

    @PreDestroy
    void destroy() {
        categoryDtoList.clear();
        System.out.println("Выполняем логику при окончании работы с  объектом " + this.getClass().getName());
    }
}
