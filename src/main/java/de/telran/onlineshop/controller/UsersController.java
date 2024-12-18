package de.telran.onlineshop.controller;

import de.telran.onlineshop.dto.UserDto;
import de.telran.onlineshop.service.UsersService;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    // GET - Получение всех пользователей
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtos = usersService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(userDtos);
    }

    // GET - Получение пользователя по ID
    @GetMapping(value = "/find/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto userDto = usersService.getUserByID(id);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    // GET - Получение пользователя по имени
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/get")
    public UserDto getUserByName(@RequestParam String name) {
        return usersService.getUserByName(name);
    }

    //  POST - Создание нового пользователя
    @PostMapping
    public ResponseEntity<Boolean> createUsers(@RequestBody UserDto newUserDto) {
        boolean isCreated = usersService.createUsers(newUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(isCreated);
    }

    // PUT - Обновление пользователя
    @PutMapping
    public ResponseEntity<UserDto> updateUsers(@RequestBody UserDto updUserDto) {
        UserDto updatedUserDto = usersService.updateUsers(updUserDto);
        return ResponseEntity.status(202).body(updatedUserDto);
    }

    // DELETE - Удаление пользователя
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUsers(@PathVariable Long id) {
        usersService.deleteUsers(id);
        return ResponseEntity.status(200).build();
    }

    // @PreDestroy - Завершение работы сервиса
    @PreDestroy
    public void destroyUser() {
        usersService.destroyUser();
    }

    // Обработка исключений
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.I_AM_A_TEAPOT)
                .body("Извините, что-то пошло не так. Попробуйте позже!");
    }
}

