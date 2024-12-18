package de.telran.onlineshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.telran.onlineshop.dto.CategoryDto;
import de.telran.onlineshop.dto.ProductDto;
import de.telran.onlineshop.service.CategoriesService;
import de.telran.onlineshop.service.ProductsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductsController.class)
class ProductsControllerTest {
    @Autowired
    private MockMvc mockMvc; // для имитации запросов пользователей

    @MockBean
    private ProductsService productsServiceMock;

    @Autowired
    private ObjectMapper objectMapper;


//    @Test
//    void getAllProducts() throws Exception {
//        when(productsServiceMock.getAllProducts()).thenReturn(List.of(new ProductDto(1L, "Test", "description_test", 11.11, "url_test", 1.11, null, null)));
//        this.mockMvc.perform(get("/products"))
//                .andDo(print()) //печать лога вызова
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$..productID").exists())
//                .andExpect(jsonPath("$..productID").value(1))
//                .andExpect(jsonPath("$..name").value("Test"))
//                .andExpect(jsonPath("$..description").value("description_test"))
//        ;
//    }

//    @Test
//    void getProductByName() throws Exception {
//        String nameTest = "Test";
//        when(productsServiceMock.getProductByName(nameTest)).thenReturn(new ProductDto(1L, nameTest, "description_test", 11.11, "url_test", 1.11, null, null));
//        this.mockMvc.perform(get("/products/get?name=Test", nameTest))
//                .andDo(print()) //печать лога вызова
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.productID").exists())
//                .andExpect(jsonPath("$.productID").value(1))
//                .andExpect(jsonPath("$.name").value(nameTest))
//        ;
//
//    }

    @Test
    void deleteProduct() throws Exception {
        Long inputId = 1L;
        this.mockMvc.perform(delete("/products/{id}", inputId)) ///categories/1
                .andDo(print()) //печать лога вызова
                .andExpect(status().isOk());

        //return void
        verify(productsServiceMock).deleteProduct(inputId);
    }
}