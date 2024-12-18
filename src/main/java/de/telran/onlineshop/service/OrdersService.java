package de.telran.onlineshop.service;

import de.telran.onlineshop.entity.OrdersEntity;
import de.telran.onlineshop.entity.Status;
import de.telran.onlineshop.entity.UsersEntity;
import de.telran.onlineshop.repository.OrdersRepository;
import de.telran.onlineshop.repository.UsersRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrdersService {
    private final UsersRepository usersRepository;
    private final OrdersRepository ordersRepository;

    @PostConstruct
    void init(){

        UsersEntity user3 = usersRepository.findById(3L).orElse(null);
        OrdersEntity order1 = new OrdersEntity(null, Timestamp.valueOf(LocalDateTime.now()), "Berlin, Dresden Str. 4", "6457647", "home", Status.ACCEPTED,Timestamp.valueOf(LocalDateTime.now()), user3,null );
        order1 = ordersRepository.save(order1);

        UsersEntity user4 = usersRepository.findById(4L).orElse(null);
        OrdersEntity order2 = new OrdersEntity(null, Timestamp.valueOf(LocalDateTime.now()), "Leipzig, Schmidt Str. 1", "3457647", "post", Status.ONTHEWAY,Timestamp.valueOf(LocalDateTime.now()), user4,null );
        order2 = ordersRepository.save(order2);

        UsersEntity user5 = usersRepository.findById(5L).orElse(null);
        OrdersEntity order3 = new OrdersEntity(null, Timestamp.valueOf(LocalDateTime.now()), "Leipzig, Sonnen Str. 7", "8657647", "home", Status.ONTHEWAY,Timestamp.valueOf(LocalDateTime.now()), user5,null );
        order3 = ordersRepository.save(order3);
    }

}
