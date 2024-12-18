package de.telran.onlineshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.telran.onlineshop.dto.CategoryDto;
import de.telran.onlineshop.dto.UserDto;
import de.telran.onlineshop.service.CategoriesService;
import de.telran.onlineshop.service.UsersService;
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

@WebMvcTest(UsersController.class)
class UsersControllerTest {
    @Autowired
    private MockMvc mockMvc; // для имитации запросов пользователей

    @MockBean
    private UsersService usersServiceMock;

    @Autowired
    private ObjectMapper objectMapper;

    String test_name = "Test";
    String test_email = "UserMailTest";


    @Test
    void getAllUsers() throws Exception {
        when(usersServiceMock.getAllUsers()).thenReturn(List.of(new UserDto(1L, test_name, test_email, "55555", "password")));
        this.mockMvc.perform(get("/users"))
                .andDo(print()) //печать лога вызова
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..userID").exists())
                .andExpect(jsonPath("$..userID").value(1))
                .andExpect(jsonPath("$..name").value("Test"))
                .andExpect(jsonPath("$..email").value("UserMailTest"))
        ;
    }

    @Test
    void getUserByID() throws Exception {
        Long testId = 1L;
        when(usersServiceMock.getUserByID(testId)).thenReturn(new UserDto(testId, test_name, test_email, "55555", "password"));
        this.mockMvc.perform(get("/users/find/{id}", testId))
                .andDo(print()) //печать лога вызова
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..userID").exists())
                .andExpect(jsonPath("$..userID").value(1))
                .andExpect(jsonPath("$..name").value("Test"))
                .andExpect(jsonPath("$..email").value("UserMailTest"))
        ;
    }

    @Test
    void getUserByName() throws Exception {
        when(usersServiceMock.getUserByName(test_name)).thenReturn(new UserDto(1L, test_name, test_email, "55555", "password"));
        this.mockMvc.perform(get("/users/get?name=Test", test_name))
                .andDo(print()) //печать лога вызова
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userID").exists())
                .andExpect(jsonPath("$.userID").value(1))
                .andExpect(jsonPath("$.name").value(test_name))
        ;
    }

    @Test
    void deleteUsers() throws Exception {

        Long inputId = 1L;

        this.mockMvc.perform(delete("/users/{id}", inputId)) ///categories/1
                .andDo(print()) //печать лога вызова
                .andExpect(status().isOk());

        //return void
        verify(usersServiceMock).deleteUsers(inputId);
    }

}