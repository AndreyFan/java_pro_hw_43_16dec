package de.telran.onlineshop.service;

import de.telran.onlineshop.configure.MapperUtil;
import de.telran.onlineshop.dto.CategoryDto;
import de.telran.onlineshop.dto.ProductDto;
import de.telran.onlineshop.dto.UserDto;
import de.telran.onlineshop.entity.*;
import de.telran.onlineshop.mapper.Mappers;
import de.telran.onlineshop.repository.CategoriesRepository;
import de.telran.onlineshop.repository.ProductsRepository;
import de.telran.onlineshop.repository.UsersRepository;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProductsServiceTest {

    @Mock
    private ProductsRepository productsRepositoryMock;
    @Mock
    private CategoriesRepository categoriesRepositoryMock;
    @Mock
    private Mappers mappersMock;

    @InjectMocks
    private ProductsService productsServiceTest; // unit объект, который тестируется

    private ProductsEntity productsEntityTest1;
    private ProductDto productDtoTest1;

    @BeforeEach
    void setUp() {
        Timestamp kurrentTime = new Timestamp(System.currentTimeMillis());
        productsEntityTest1 = new ProductsEntity(
                1L,
                "Seife",
                "For cleaning dishes",
                0.55,
                "https://example.com/detergent.jpg",
                5.0,
                kurrentTime,
                kurrentTime,
                new CategoriesEntity(),
                new HashSet<FavoritesEntity>(),
                new HashSet<OrderItemsEntity>(),
                new HashSet<CartItemsEntity>()
        );

        productDtoTest1 = new ProductDto(
                1L,
                "Seife",
                "For cleaning dishes",
                0.55,
                new CategoryDto(),
                "https://example.com/detergent.jpg",
                5.0,
                kurrentTime,
                kurrentTime
        );

    }

    @Test
    void getAllProducts() {
        Timestamp kurrentTime1 = new Timestamp(System.currentTimeMillis());
        ProductsEntity productsEntityTest2 = new ProductsEntity(
                2L,
                "Krot",
                "For cleaning dishes",
                0.55,
                "https://example.com/detergent.jpg",
                5.0,
                kurrentTime1,
                kurrentTime1,
                new CategoriesEntity(),
                new HashSet<FavoritesEntity>(),
                new HashSet<OrderItemsEntity>(),
                new HashSet<CartItemsEntity>()
        );

        ProductDto productDtoTest2 = new ProductDto(
                2L,
                "Krot",
                "For cleaning dishes",
                0.55,
                new CategoryDto(),
                "https://example.com/detergent.jpg",
                5.0,
                kurrentTime1,
                kurrentTime1
        );

        when(productsRepositoryMock.findAll()).thenReturn(List.of(productsEntityTest1, productsEntityTest2));
        when(mappersMock.convertToProductDto(productsEntityTest1)).thenReturn(productDtoTest1);
        when(mappersMock.convertToProductDto(productsEntityTest2)).thenReturn(productDtoTest2);

        List<ProductDto> actualProductsDtoList = productsServiceTest.getAllProducts();

        assertNotNull(actualProductsDtoList);
        assertTrue(actualProductsDtoList.size() > 0);
        assertEquals(2, actualProductsDtoList.size());
        assertEquals(1, actualProductsDtoList.get(0).getProductID());
        assertEquals(productDtoTest2, actualProductsDtoList.get(1));
        verify(productsRepositoryMock).findAll();
        verify(mappersMock, times(2)).convertToProductDto(any(ProductsEntity.class));
    }

    @Test
    void getProductByID() {
        Long testId = 1L;
        when(productsRepositoryMock.findById(testId)).thenReturn(Optional.of(productsEntityTest1));
        when(mappersMock.convertToProductDto(productsEntityTest1)).thenReturn(productDtoTest1);

        ProductDto actualProductDto = productsServiceTest.getProductByID(testId);

        assertNotNull(actualProductDto);
        assertEquals(testId, actualProductDto.getProductID());
        assertEquals(productDtoTest1, actualProductDto);
        verify(productsRepositoryMock).findById(testId);
        verify(mappersMock, times(1)).convertToProductDto(any(ProductsEntity.class));

    }

    @Test
    void getProductByName() {
        String nameTest = "Seife";
        when(productsRepositoryMock.findByName(nameTest)).thenReturn(productsEntityTest1);
        when(mappersMock.convertToProductDto(productsEntityTest1)).thenReturn(productDtoTest1);

        ProductDto actualProductDto = productsServiceTest.getProductByName(nameTest);

        assertNotNull(actualProductDto);
        assertEquals(1L, actualProductDto.getProductID());
        assertEquals("Seife", actualProductDto.getName());
        assertEquals("https://example.com/detergent.jpg", actualProductDto.getImageURL());
        verify(productsRepositoryMock, times(1)).findByName(nameTest);
    }

    @Test
    void createProduct() {
        Timestamp kurrentTime2 = new Timestamp(System.currentTimeMillis());
        ProductsEntity productsEntityTestInsert = new ProductsEntity(
                null,
                "Krot",
                "For cleaning dishes",
                0.55,
                "https://example.com/detergent.jpg",
                5.0,
                kurrentTime2,
                kurrentTime2,
                new CategoriesEntity(),
                new HashSet<FavoritesEntity>(),
                new HashSet<OrderItemsEntity>(),
                new HashSet<CartItemsEntity>()
        );

        ProductDto productDtoTestInsert = new ProductDto(
                null,
                "Krot",
                "For cleaning dishes",
                0.55,
                new CategoryDto(),
                "https://example.com/detergent.jpg",
                5.0,
                kurrentTime2,
                kurrentTime2
        );

        when(mappersMock.converterToProductsEntity(productDtoTestInsert)).thenReturn(productsEntityTestInsert);
        when(productsRepositoryMock.save(productsEntityTestInsert)).thenReturn(productsEntityTestInsert);

        productsServiceTest.createProduct(productDtoTestInsert);

        verify(mappersMock).converterToProductsEntity(productDtoTestInsert);
        verify(productsRepositoryMock).save(productsEntityTestInsert);
    }

    @Test
    void updateProduct() {
        when(mappersMock.converterToProductsEntity(productDtoTest1)).thenReturn(productsEntityTest1);
        when(productsRepositoryMock.save(productsEntityTest1)).thenReturn(productsEntityTest1);
        when(mappersMock.convertToProductDto(productsEntityTest1)).thenReturn(productDtoTest1);

        ProductDto actualProductDto = productsServiceTest.updateProduct(productDtoTest1);

        // проверка
        assertNotNull(actualProductDto);
        assertEquals(productDtoTest1.getProductID(), actualProductDto.getProductID());
        assertEquals(productDtoTest1, actualProductDto);
        verify(mappersMock).converterToProductsEntity(productDtoTest1);
        verify(productsRepositoryMock).save(productsEntityTest1);
        verify(mappersMock).convertToProductDto(productsEntityTest1);
    }

    @Test
    void deleteProduct() {
        Long testId = 1L;
        when(productsRepositoryMock.findById(testId)).thenReturn(Optional.of(productsEntityTest1));

        productsServiceTest.deleteProduct(testId);

        verify(productsRepositoryMock).delete(productsEntityTest1);
    }

    @Test
    void deleteProductByIdNotFoundTest() {
        Long testId = 1L;
        when(productsRepositoryMock.findById(testId)).thenReturn(Optional.ofNullable(null));

        assertThrows(Exception.class, () -> productsServiceTest.deleteProduct(testId));
    }
}