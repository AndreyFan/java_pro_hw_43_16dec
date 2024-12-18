package de.telran.onlineshop.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cartItems")
public class CartItemsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CartItemId")
    private Long cartItemId;

    @ManyToOne
    @JoinColumn(name = "CardID")
    private CartEntity cartId;

    @ManyToOne
    @JoinColumn(name = "ProductID")
    private ProductsEntity productId;

    @Column(name = "Quantity")
    private Integer quantity;

    public CartItemsEntity(Long cartItemId, CartEntity cartId, ProductsEntity productId, Integer quantity) {
        this.cartItemId = cartItemId;
        this.cartId = cartId;
        this.productId = productId;
        this.quantity = quantity;
    }
}
