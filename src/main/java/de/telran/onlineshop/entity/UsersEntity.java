package de.telran.onlineshop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Users")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UsersEntity {
    @Id
    @Column(name = "UserID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "Name")
    private String name;

    @Column(name = "Email")
    private String email;

    @Column(name = "PhoneNumber")
    private String phoneNumber;

    @Column(name = "PasswordHash")
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "Role")
    private Role role;

    @OneToOne(mappedBy = "user")
    private CartEntity cart;

    @OneToMany(mappedBy = "user")
    private Set<FavoritesEntity> favorites= new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<OrdersEntity> orders;


    @ManyToMany
    @JoinTable(name = "UsersAddresses",
            joinColumns = @JoinColumn(name = "UserID"),
            inverseJoinColumns = @JoinColumn(name = "AddressID"))
    private Set<AddressEntity> addresses =  new HashSet<>();

    public UsersEntity(Long userId, String name, String email, String phoneNumber, String passwordHash, Role role, CartEntity cart, Set<FavoritesEntity> favorites, Set<OrdersEntity> orders, Set<AddressEntity> addresses) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.passwordHash = passwordHash;
        this.role = role;
        this.cart = cart;
        this.favorites = favorites;
        this.orders = orders;
        this.addresses = addresses;
    }
}

