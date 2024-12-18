package de.telran.onlineshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FavoriteDto {
    private long favoriteID;
    private UserDto user;
    private ProductDto product;

}
