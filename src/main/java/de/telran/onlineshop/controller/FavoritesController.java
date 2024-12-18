package de.telran.onlineshop.controller;

import de.telran.onlineshop.dto.FavoriteDto;
import de.telran.onlineshop.service.FavoritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/favorites")
public class FavoritesController {
    private FavoritesService favoritesService;
    @Autowired
    public FavoritesController(FavoritesService favoritesService) {
        this.favoritesService = favoritesService;
    }
//    @GetMapping
//    public List<FavoriteDto> getAllFavorites() {
//        return favoritesService.getAllFavorites();
//    }

//
//    @GetMapping(value = "/find/{id}")
//    FavoriteDto getFavoriteById(@PathVariable Long id) { ///categories/find/3
//        return favoritesService.getFavoriteById(id);
//    }
//
//    @PostMapping()
//    public boolean addFavorite(@RequestBody FavoriteDto favoriteDto) {
//        return favoritesService.addFavorite(favoriteDto);
//    }
//
//    @PutMapping()
//    public FavoriteDto updateFavorite(@RequestBody FavoriteDto updFavoriteDto) {
//        return favoritesService.updateFavorite(updFavoriteDto);
//    }
//
//    @DeleteMapping(value = "/{id}")
//    public void deleteFavorite(@PathVariable Long id) {
//     favoritesService.deleteFavorite(id);
//    }
    // альтернативная обработка ошибочной ситуации Exception
    @ExceptionHandler({IllegalArgumentException.class, FileNotFoundException.class})
    public ResponseEntity handleTwoException(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.I_AM_A_TEAPOT)
                .body("Извините, что-то пошло не так. Попробуйте позже!");
    }

}