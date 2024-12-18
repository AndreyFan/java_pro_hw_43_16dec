package de.telran.onlineshop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Orders")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class OrdersEntity {

    @Id
    @Column(name = "OrderID")
    @GeneratedValue()
    private Long orderId;

    @Column(name = "CreatedAt")
    private Timestamp createdAt;

    @Column(name = "DeliveryAddress")
    private String deliveryAddress;

    @Column(name = "ContactPhone")
    private String contactPhone;

    @Column(name = "DeliveryMethod")
    private String deliveryMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    private Status status;

    @Column(name = "UpdateAt")
    private Timestamp updateAt;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private UsersEntity user;

    @OneToMany(mappedBy = "orderId")
    private Set<OrderItemsEntity> orderItemsEntitySet= new HashSet<>();

    public OrdersEntity(Long orderId, Timestamp createdAt, String deliveryAddress, String contactPhone, String deliveryMethod, Status status, Timestamp updateAt, UsersEntity user, Set<OrderItemsEntity> orderItemsEntitySet) {
        this.orderId = orderId;
        this.createdAt = createdAt;
        this.deliveryAddress = deliveryAddress;
        this.contactPhone = contactPhone;
        this.deliveryMethod = deliveryMethod;
        this.status = status;
        this.updateAt = updateAt;
        this.user = user;
        this.orderItemsEntitySet = orderItemsEntitySet;
    }
}
