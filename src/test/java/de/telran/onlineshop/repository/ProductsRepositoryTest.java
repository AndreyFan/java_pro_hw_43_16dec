package de.telran.onlineshop.repository;

import de.telran.onlineshop.entity.CategoriesEntity;
import de.telran.onlineshop.entity.ProductsEntity;
import de.telran.onlineshop.entity.UsersEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductsRepositoryTest {
    @Autowired
    private ProductsRepository productsRepositoryTest;

    private static final String NAME_TEST = "Test";
    private static final String Description_TEST = "descriptionOfProductTest";
    private static final Double PRICE_TEST = 1.00;
    private static final String IMAGE_URL_TEST = "ImageUrlTest";
    private static final Double DISCOUNT_PRICE_TEST = 0.11;

    private static ProductsEntity productEntityTest;

    @BeforeAll
    static void SetStart() {
        productEntityTest = new ProductsEntity((Long) null, NAME_TEST, Description_TEST, PRICE_TEST, IMAGE_URL_TEST, DISCOUNT_PRICE_TEST, null, null, null, null, null, null);
        System.out.println("Выполняется setStart(единоразово, перед запуском всех тестов)!");
    }

    @Test
    void findAllTest() {
        List<ProductsEntity> productsEntityListTest = productsRepositoryTest.findAll();
        assertNotNull(productsEntityListTest);
    }

    @Test
    void insertTest() {
        String nameExpected = NAME_TEST;
        String descriptionExpected = Description_TEST;
        Double priceExpected = PRICE_TEST;
        String imageExpected = IMAGE_URL_TEST;
        Double discountPriceExpected = DISCOUNT_PRICE_TEST;

        ProductsEntity productInsertTest = productEntityTest;

        ProductsEntity productActual = productsRepositoryTest.save(productInsertTest);

        assertNotNull(productActual);
        assertTrue(productActual.getProductId() != null);
        assertEquals(nameExpected, productActual.getName());
        assertEquals(descriptionExpected, productActual.getDescription());
        assertEquals(priceExpected, productActual.getPrice());
        assertEquals(imageExpected, productActual.getImageUrl());
        assertEquals(discountPriceExpected, productActual.getDiscountPrice());

    }

    @Test
    void updateTest() {
        // when
        ProductsEntity productData = generateTestData();

        // test
        String nameExpected = "NewTestProductName";
        String descriptionExpected = "NewTestProductDescription";
        Double priceExpected = 22.22;
        productData.setName(nameExpected);
        productData.setDescription(descriptionExpected);
        productData.setPrice(priceExpected);
        ProductsEntity actualProduct = productsRepositoryTest.save(productData);
        //assert
        assertNotNull(productData);
        assertEquals(nameExpected, actualProduct.getName());
        assertEquals(descriptionExpected, actualProduct.getDescription());
        assertEquals(priceExpected, actualProduct.getPrice());

    }

    @Test
    void findByNameTest() {
        //when
        ProductsEntity productExpected = generateTestData();

        // run
        String nameExpected = productExpected.getName();
        ProductsEntity productActual = productsRepositoryTest.findByName(nameExpected);

        //assert
        assertNotNull(productActual);
        assertEquals(nameExpected, productActual.getName());

    }

    @Test
    void findById() {
        //when
        ProductsEntity productExpected = generateTestData();

        Long productExpectedId = productExpected.getProductId();

        Optional<ProductsEntity> productActual = productsRepositoryTest.findById(productExpectedId);
        assertTrue(productActual.isPresent() && productActual.get().equals(productExpected));

    }

    @Test
    void deleteTest() {
        ProductsEntity productExpected = generateTestData();

        Long productExpectedId = productExpected.getProductId();

        productsRepositoryTest.deleteById(productExpectedId);

        Optional<ProductsEntity> productActualOptional = productsRepositoryTest.findById(productExpectedId);
        assertFalse(productActualOptional.isPresent());

    }

    private ProductsEntity generateTestData() {
        ProductsEntity productInsert = productsRepositoryTest.save(productEntityTest);
        assertNotNull(productInsert);
        assertTrue(productInsert.getProductId() != null);
        assertEquals(NAME_TEST, productInsert.getName());
        assertEquals(Description_TEST, productInsert.getDescription());
        assertEquals(PRICE_TEST, productInsert.getPrice());
        assertEquals(IMAGE_URL_TEST, productInsert.getImageUrl());
        assertEquals(DISCOUNT_PRICE_TEST, productInsert.getDiscountPrice());
        return productInsert;
    }
}