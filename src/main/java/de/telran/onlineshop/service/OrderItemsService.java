package de.telran.onlineshop.service;

import de.telran.onlineshop.entity.OrderItemsEntity;
import de.telran.onlineshop.entity.OrdersEntity;
import de.telran.onlineshop.entity.ProductsEntity;
import de.telran.onlineshop.repository.OrderItemsRepository;
import de.telran.onlineshop.repository.OrdersRepository;
import de.telran.onlineshop.repository.ProductsRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemsService {

    private final OrdersRepository ordersRepository;
    private final ProductsRepository productsRepository;
    private final OrderItemsRepository orderItemsRepository;

    @PostConstruct
    void init(){
        OrdersEntity order1 = ordersRepository.findById(1L).orElse(null);
        ProductsEntity product1 = productsRepository.findByName("Milk");
        OrderItemsEntity orderItem1 = new OrderItemsEntity(null, order1, product1,10,1.1);
        orderItem1 = orderItemsRepository.save(orderItem1);

        OrdersEntity order2 = ordersRepository.findById(2L).orElse(null);
        ProductsEntity product2 = productsRepository.findById(5L).orElse(null);
        OrderItemsEntity orderItem2 = new OrderItemsEntity(null, order2, product2,2,16.0);
        orderItem2 = orderItemsRepository.save(orderItem2);

        OrdersEntity order3 = ordersRepository.findById(3L).orElse(null);
        ProductsEntity product3 = productsRepository.findById(7L).orElse(null);
        OrderItemsEntity orderItem3 = new OrderItemsEntity(null, order3, product3,4,135.12);
        orderItem3 = orderItemsRepository.save(orderItem3);

    }
}
