package de.telran.onlineshop.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Entity
@Table(name = "OrderItems")
public class OrderItemsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderItemID")
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "OrderId")
    private OrdersEntity orderId;

    @ManyToOne
    @JoinColumn(name = "ProductID")
    private ProductsEntity productId;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "PriceAtPurchase")
    private Double priceAtPurchase;

    public OrderItemsEntity() {
    }

    public OrderItemsEntity(Long orderItemId, OrdersEntity orderId, ProductsEntity productId, Integer quantity, Double priceAtPurchase) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
    }
}
