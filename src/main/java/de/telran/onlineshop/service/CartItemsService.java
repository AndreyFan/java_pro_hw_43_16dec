package de.telran.onlineshop.service;

import de.telran.onlineshop.entity.CartEntity;
import de.telran.onlineshop.entity.CartItemsEntity;
import de.telran.onlineshop.entity.CategoriesEntity;
import de.telran.onlineshop.entity.ProductsEntity;
import de.telran.onlineshop.repository.CartItemsRepository;
import de.telran.onlineshop.repository.CartsRepository;
import de.telran.onlineshop.repository.CategoriesRepository;
import de.telran.onlineshop.repository.ProductsRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemsService {
    private static final Logger log = LoggerFactory.getLogger(CartItemsService.class);
    private final CartsRepository cartsRepository;
    private final ProductsRepository productsRepository;
    private final CartItemsRepository cartItemsRepository;




    @PostConstruct
    void init(){

        CartEntity cart1= cartsRepository.findById(1L).orElse(null);
        ProductsEntity product1= productsRepository.findByName("Teddy Bear");
        CartItemsEntity cartItem1 = new CartItemsEntity(null,cart1, product1, 2);
        cartItemsRepository.save(cartItem1);

        CartEntity cart2= cartsRepository.findById(2L).orElse(null);
        ProductsEntity product2= productsRepository.findByName("Jeans");
        CartItemsEntity cartItem2 = new CartItemsEntity(null,cart2, product2, 3);
        cartItemsRepository.save(cartItem2);

        CartEntity cart3= cartsRepository.findById(3L).orElse(null);
        ProductsEntity product3= productsRepository.findById(11L).orElse(null);
        CartItemsEntity cartItem3 = new CartItemsEntity(null,cart3, product3, 4);
        cartItemsRepository.save(cartItem3);

    }
}
