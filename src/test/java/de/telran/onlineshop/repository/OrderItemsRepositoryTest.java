package de.telran.onlineshop.repository;

import de.telran.onlineshop.entity.OrderItemsEntity;
import de.telran.onlineshop.entity.UsersEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class OrderItemsRepositoryTest {

    @Autowired
    private  OrderItemsRepository orderItemsRepositoryTest;

    private   Integer QUANTITY_TEST = 111;
    private   Double PRICE_AT_PURCHASE = 11.11;



    @Test
    void findAll(){
        List<OrderItemsEntity> orderItemsEntityList = orderItemsRepositoryTest.findAll();
        assertNotNull(orderItemsEntityList);
    }

    @Test
    void insertTest(){
OrderItemsEntity orderItemInsertTest = new OrderItemsEntity(null,null,null, 222, 22.22);
        Integer quantityInsertExpected = 222;
        Double priceInsertExpected = 22.22;
        OrderItemsEntity orderItemInsertActual = orderItemsRepositoryTest.save(orderItemInsertTest);

        assertNotNull(orderItemInsertActual);
        assertTrue(orderItemInsertActual.getOrderItemId()!=0);
        assertEquals(quantityInsertExpected, orderItemInsertActual.getQuantity());
        assertEquals(priceInsertExpected, orderItemInsertActual.getPriceAtPurchase());
        orderItemsRepositoryTest.delete(orderItemInsertActual);
    }

    @Test
    void updateTest() {
        // when
        OrderItemsEntity orderItemData = generateTestData();

        // test
        Integer quantityExpected = 555;
        Double priceExpected = 55.11;
        orderItemData.setQuantity(quantityExpected);
        orderItemData.setPriceAtPurchase(priceExpected);
        OrderItemsEntity orderItemActual = orderItemsRepositoryTest.save(orderItemData);

        //assert
        assertNotNull(orderItemActual);
        assertEquals(quantityExpected, orderItemActual.getQuantity());
        assertEquals(priceExpected, orderItemActual.getPriceAtPurchase());
        orderItemsRepositoryTest.delete(orderItemActual);
    }

    @Test
    void findById() {
        //when
        OrderItemsEntity orderItemExpected = generateTestData();

        Long orderItemIdExpected= orderItemExpected.getOrderItemId();

        Optional<OrderItemsEntity> orderItemActual= orderItemsRepositoryTest.findById(orderItemIdExpected);
        assertTrue(orderItemActual.isPresent() && orderItemActual.get().equals(orderItemExpected));

    }

    @Test
    void deleteTest() {
        //when
        OrderItemsEntity orderItemTest = generateTestData();

        Long idTest = orderItemTest.getOrderItemId();
        orderItemsRepositoryTest.deleteById(idTest);

        //проверка
        Optional<OrderItemsEntity> orderItemActualOptional = orderItemsRepositoryTest.findById(idTest);
        assertFalse(orderItemActualOptional.isPresent());

    }

    private OrderItemsEntity generateTestData(){
        OrderItemsEntity orderItemsEntityTest= new OrderItemsEntity(null, null, null, QUANTITY_TEST, PRICE_AT_PURCHASE);
        OrderItemsEntity orderItemsInsert = orderItemsRepositoryTest.save(orderItemsEntityTest);
        assertNotNull(orderItemsInsert);
        assertTrue(orderItemsInsert.getOrderItemId() != null);
        assertEquals(QUANTITY_TEST, orderItemsInsert.getQuantity());
        assertEquals(PRICE_AT_PURCHASE, orderItemsInsert.getPriceAtPurchase());

        return orderItemsInsert;

    }

}