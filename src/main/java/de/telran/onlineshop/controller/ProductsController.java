package de.telran.onlineshop.controller;

import de.telran.onlineshop.dto.ProductDto;
import de.telran.onlineshop.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductsController {

    private ProductsService productsService;

    @Autowired
    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping  //select
    public  List<ProductDto> getAllProducts() {
        return productsService.getAllProducts();
    }

    @GetMapping(value = "/find/{id}")
    public ProductDto getProductByID(@PathVariable Long id) {
        return productsService.getProductByID(id);
    }

    @GetMapping(value = "/get")
    public ProductDto getProductByName(@RequestParam String name) {
        return productsService.getProductByName(name);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public boolean createProduct(@RequestBody ProductDto newProductDto) {
        return productsService.createProduct(newProductDto);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping()
    public ProductDto updateProduct(@RequestBody ProductDto updProductDto) {
        return productsService.updateProduct(updProductDto);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteProduct(@PathVariable Long id) {
       productsService.deleteProduct(id);
    }

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
