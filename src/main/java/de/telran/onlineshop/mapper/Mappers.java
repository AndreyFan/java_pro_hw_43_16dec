package de.telran.onlineshop.mapper;

import de.telran.onlineshop.configure.MapperUtil;
import de.telran.onlineshop.dto.*;
import de.telran.onlineshop.entity.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class Mappers {
    private final ModelMapper modelMapper;

    public UserDto convertToUserDto(UsersEntity usersEntity) {
        modelMapper.typeMap(UsersEntity.class, UserDto.class)
                .addMappings(mapper -> mapper.skip(UserDto::setEmail));
        UserDto userDto = modelMapper.map(usersEntity, UserDto.class);// автомат
        if (userDto.getPasswordHash() != null)
            userDto.setPasswordHash("***"); // замещение данных на звёздочки

        // связанный сет
        if (usersEntity.getFavorites() != null) {
            Set<FavoriteDto> favoriteDtoSet = MapperUtil.convertSet(usersEntity.getFavorites(), this::convertToFavoritesDto);
            userDto.setFavorites(favoriteDtoSet);
        }

        CartDto cartDto = convertToCartDto(usersEntity.getCart());
        userDto.setCart(cartDto);
        return userDto;
    }

    public FavoriteDto convertToFavoritesDto(FavoritesEntity favoritesEntity) {
        FavoriteDto favoriteDto = modelMapper.map(favoritesEntity, FavoriteDto.class);
        favoriteDto.setUser(null);
        return modelMapper.map(favoritesEntity, FavoriteDto.class);
    }

    public UsersEntity convertToUserEntity(UserDto userDto) {
        UsersEntity usersEntity = modelMapper.map(userDto, UsersEntity.class);
        return usersEntity;
    }

    public CartDto convertToCartDto(CartEntity cartEntity) {
        CartDto cartDto = null;
        if (cartEntity != null)
            cartDto = modelMapper.map(cartEntity, CartDto.class);
        return cartDto;

    }

    public ProductDto convertToProductDto(ProductsEntity productsEntity) {
        ProductDto productDto = modelMapper.map(productsEntity, ProductDto.class);

        CategoryDto categoryDto = converterToCategoryDto(productsEntity.getCategory());
        productDto.setCategoryDto(categoryDto);
        return productDto;
    }

    public ProductsEntity converterToProductsEntity(ProductDto productDto) {
        ProductsEntity productsEntity = modelMapper.map(productDto, ProductsEntity.class);

        CategoriesEntity categoriesEntity = converterToCategoriesEntity(productDto.getCategoryDto());
        productsEntity.setCategory(categoriesEntity);
        return productsEntity;
    }

    public CategoryDto converterToCategoryDto(CategoriesEntity categoriesEntity){
        CategoryDto categoryDto = modelMapper.map(categoriesEntity, CategoryDto.class);
        return categoryDto;
    }

    public CategoriesEntity converterToCategoriesEntity(CategoryDto categoryDto){
        CategoriesEntity categoriesEntity = modelMapper.map(categoryDto, CategoriesEntity.class);
        return categoriesEntity;
    }

}