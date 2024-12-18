package de.telran.onlineshop.service;

import de.telran.onlineshop.configure.MapperUtil;
import de.telran.onlineshop.dto.CategoryDto;
import de.telran.onlineshop.dto.UserDto;
import de.telran.onlineshop.entity.CategoriesEntity;
import de.telran.onlineshop.entity.ProductsEntity;
import de.telran.onlineshop.dto.ProductDto;
import de.telran.onlineshop.entity.UsersEntity;
import de.telran.onlineshop.mapper.Mappers;
import de.telran.onlineshop.repository.CategoriesRepository;
import de.telran.onlineshop.repository.ProductsRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductsService {
    private static final Logger log = LoggerFactory.getLogger(ProductsService.class);
    private final ProductsRepository productsRepository;
    private final CategoriesRepository categoriesRepository;
    private final Mappers mappers;
    private List<ProductDto> productDtoList;


    @PostConstruct
    void initProducts() {

        long currentMillis = System.currentTimeMillis();

        // Преобразование миллисекунд в Timestamp
        Timestamp timestamp = new Timestamp(currentMillis);

        Timestamp dateCr = timestamp;
        Timestamp dateUp = timestamp;
        CategoriesEntity category1 = categoriesRepository.findById(1L).orElse(null);
        ProductsEntity productsEntity1 = new ProductsEntity((Long) null, "Milk", "Fresh liter of milk", 1.2, "https://example.com/milk.jpg", 0.0, dateCr, dateUp, category1, null, null, null);
        productsRepository.save(productsEntity1);
        ProductsEntity productsEntity2 = new ProductsEntity(null, "Bread", "Fresh loaf of bread", 0.8, "https://example.com/bread.jpg", 10.0, dateCr, dateUp, category1, null, null, null);
        productsRepository.save(productsEntity2);

        CategoriesEntity category2 = categoriesRepository.findById(2L).orElse(null);
        ProductsEntity productsEntity3 = new ProductsEntity((Long) null, "Dishwashing Liquid", "For cleaning dishes", 2.5, "https://example.com/detergent.jpg", 5.0, dateCr, dateUp, category2, null, null, null);
        productsRepository.save(productsEntity3);
        ProductsEntity productsEntity4 = new ProductsEntity(null, "Glass Cleaner", "Effective glass cleaner", 3.0, "https://example.com/glass_cleaner.jpg", 0.0, dateCr, dateUp, category2, null, null, null);
        productsRepository.save(productsEntity4);

        CategoriesEntity category3 = categoriesRepository.findByName("Радиотехника");
        ProductsEntity productsEntity5 = new ProductsEntity((Long) null, "Television", "LED TV", 150.0, "https://example.com/tv.jpg", 3.5, dateCr, dateUp, category3, null, null, null);
        productsRepository.save(productsEntity5);
        ProductsEntity productsEntity6 = new ProductsEntity(null, "Radio", "FM radio receiver", 30.0, "https://example.com/radio.jpg", 3.5, dateCr, dateUp, category3, null, null, null);
        productsRepository.save(productsEntity6);

        CategoriesEntity category4 = categoriesRepository.findById(4L).orElse(null);
        ProductsEntity productsEntity7 = new ProductsEntity((Long) null, "Teddy Bear", "Plush teddy bear", 10.0, "https://example.com/teddy_bear.jpg", 0.0, dateCr, dateUp, category4, null, null, null);
        productsRepository.save(productsEntity7);
        ProductsEntity productsEntity8 = new ProductsEntity(null, "Building Blocks", "Kids' building blocks set", 15.0, "https://example.com/blocks.jpg", 0.0, dateCr, dateUp, category4, null, null, null);
        productsRepository.save(productsEntity8);

        CategoriesEntity category5 = categoriesRepository.findByName("Одежда");
        ProductsEntity productsEntity9 = new ProductsEntity((Long) null, "T-Shirt", "Graphic T-shirt", 12.0, "https://example.com/tshirt.jpg", 10.0, dateCr, dateUp, category5, null, null, null);
        productsRepository.save(productsEntity9);
        ProductsEntity productsEntity10 = new ProductsEntity(null, "Jeans", "Stylish jeans", 25.0, "https://example.com/jeans.jpg", 20.0, dateCr, dateUp, category5, null, null, null);
        productsRepository.save(productsEntity10);

        CategoriesEntity category6 = categoriesRepository.findById(6L).orElse(null);
        ProductsEntity productsEntity11 = new ProductsEntity((Long) null, "Notebook", "School notebook", 1.5, "https://example.com/notebook.jpg", 0.0, dateCr, dateUp, category6, null, null, null);
        productsRepository.save(productsEntity11);
        ProductsEntity productsEntity12 = new ProductsEntity(null, "Pen", "Gel pen", 0.5, "https://example.com/pen.jpg", 0.0, dateCr, dateUp, category6, null, null, null);
        productsRepository.save(productsEntity12);


        System.out.println("Выполняем логику при создании объекта " + this.getClass().getName());
    }

    public List<ProductDto> getAllProducts() {
        List<ProductsEntity> productsEntityList = productsRepository.findAll();
        List<ProductDto> productDtoList = MapperUtil.convertList(productsEntityList, mappers::convertToProductDto);
        return productDtoList;
    }

    public ProductDto getProductByID(Long id) {
        ProductsEntity productsEntity = productsRepository.findById(id).orElse(new ProductsEntity());
        ProductDto productDto = mappers.convertToProductDto(productsEntity);
        return productDto;
    }

    public ProductDto getProductByName(String name) { ///products/get?name=Other,k=2
        ProductsEntity productsEntity = productsRepository.findByName(name);
        ProductDto productDto = mappers.convertToProductDto(productsEntity);
        return productDto;
    }

    public boolean createProduct(ProductDto newProduct) { //insert
        ProductsEntity createProductEntity = mappers.converterToProductsEntity(newProduct);
        ProductsEntity returnProductEntity = productsRepository.save(createProductEntity);

        return returnProductEntity.getProductId() != null;
    }

    public ProductDto updateProduct(ProductDto updProductDto) {//update
        ProductsEntity updateProductEntity = mappers.converterToProductsEntity(updProductDto);
        ProductsEntity returnProductEntity = productsRepository.save(updateProductEntity);
        ProductDto productDto = mappers.convertToProductDto(returnProductEntity);
        return productDto;
    }

    public void deleteProduct(Long id) {
        ProductsEntity products = productsRepository.findById(id).orElse(null);
        if (products == null) {
            throw new RuntimeException("Нету такого объекта с Id = " + id);
        } else {
            productsRepository.delete(products);
        }
    }

    @PreDestroy
    void destroy() {
        productDtoList.clear();
        System.out.println("Выполняем логику при окончании работы с  объектом " + this.getClass().getName());
    }

}
