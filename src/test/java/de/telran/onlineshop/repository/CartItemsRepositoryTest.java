package de.telran.onlineshop.repository;

import de.telran.onlineshop.entity.CartItemsEntity;
import de.telran.onlineshop.entity.CategoriesEntity;
import de.telran.onlineshop.entity.UsersEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CartItemsRepositoryTest {
    @Autowired
    private CartItemsRepository cartItemsRepositoryTest;

    private static final Integer QUANTITY_TEST = 10;
    private static CartItemsEntity cartItemsEntityTest;

    @BeforeAll
    static void setStart() {
        cartItemsEntityTest = new CartItemsEntity(null, null, null, QUANTITY_TEST);

    }

    @Test
    void findAllTest() {
        List<CartItemsEntity> cartItemsEntityList = cartItemsRepositoryTest.findAll();
        assertNotNull(cartItemsEntityList);
    }

    @Test
    void insertTest() {
        Integer quantityExpected = QUANTITY_TEST;
        CartItemsEntity cartItemsTest = cartItemsEntityTest;

        CartItemsEntity cartItemsActual = cartItemsRepositoryTest.save(cartItemsTest);

        assertNotNull(cartItemsActual);
        assertTrue(cartItemsActual.getCartItemId() != null);
        assertEquals(quantityExpected, cartItemsActual.getQuantity());

    }


    @Test
    void updateTest() {
        CartItemsEntity cartItemsEntityDate = generateTestData();

        Integer quantityExpected = 55;
        cartItemsEntityDate.setQuantity(quantityExpected);

        CartItemsEntity cartItemsActual = cartItemsRepositoryTest.save(cartItemsEntityDate);
        assertNotNull(cartItemsActual);
        assertEquals(quantityExpected, cartItemsActual.getQuantity());
    }

    @Test
    void findById() {
        //when
        CartItemsEntity cartItemsEntityExpected = generateTestData();

        Long cartItemsIdExpected= cartItemsEntityExpected.getCartItemId();

        Optional<CartItemsEntity> cartItemsEntityActualoptional= cartItemsRepositoryTest.findById(cartItemsIdExpected);
        assertTrue(cartItemsEntityActualoptional.isPresent() && cartItemsEntityActualoptional.get().equals(cartItemsEntityExpected));

    }

    @Test
    void deleteTest(){
        CartItemsEntity cartItemsExpected = generateTestData();

        Long cartItemId = cartItemsExpected.getCartItemId();
        cartItemsRepositoryTest.deleteById(cartItemId);

        Optional<CartItemsEntity> cartItemsEntityActualOptional = cartItemsRepositoryTest.findById(cartItemId);
        assertFalse(cartItemsEntityActualOptional.isPresent());
    }

    private CartItemsEntity generateTestData() {
        CartItemsEntity cartItemsEntityInsert = cartItemsRepositoryTest.save(cartItemsEntityTest);
        assertNotNull(cartItemsEntityInsert);
        assertTrue(cartItemsEntityInsert.getCartItemId()!=null);
        assertEquals(QUANTITY_TEST, cartItemsEntityInsert.getQuantity());
        return cartItemsEntityInsert;
    }

}