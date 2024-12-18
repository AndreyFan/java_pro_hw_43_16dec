package de.telran.onlineshop.service;

import de.telran.onlineshop.configure.MapperUtil;
import de.telran.onlineshop.dto.UserDto;
import de.telran.onlineshop.entity.CartEntity;
import de.telran.onlineshop.entity.Role;
import de.telran.onlineshop.entity.UsersEntity;
import de.telran.onlineshop.mapper.Mappers;
import de.telran.onlineshop.repository.CartsRepository;
import de.telran.onlineshop.repository.UsersRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final CartsRepository cartsRepository;
    private final Mappers mappers;
    private List<UserDto> userDtoList;

    @PostConstruct
    void initUsers() {

        CartEntity cart1 = new CartEntity();
        cart1 = cartsRepository.save(cart1);
        UsersEntity user1 = new UsersEntity(null, "Peter", "peter@gmail/com", "+4915121748923", "peter45", Role.ADMINISTRATOR, cart1, null, null, null);
        usersRepository.save(user1);

        CartEntity cart2 = new CartEntity();
        cart2 = cartsRepository.save(cart2);
        UsersEntity user2 = new UsersEntity(null, "Olga", "olga@gmail/com", "+4915121748977", "olga24", Role.CLIENT, cart2, null, null, null);
        usersRepository.save(user2);

        CartEntity cart3 = new CartEntity();
        cart3 = cartsRepository.save(cart3);
        UsersEntity user3 = new UsersEntity(null, "Anna", "Anna@gmail/com", "+4915121748753", "Anna41", Role.CLIENT, cart3, null, null, null);
        usersRepository.save(user3);

        CartEntity cart4 = new CartEntity();
        cart4 = cartsRepository.save(cart4);
        UsersEntity user4 = new UsersEntity(null, "Max", "max@gmail/com", "+4915735748923", "max27", Role.CLIENT, cart4, null, null, null);
        usersRepository.save(user4);

        CartEntity cart5 = new CartEntity();
        cart5 = cartsRepository.save(cart5);
        UsersEntity user5 = new UsersEntity(null, "Irina", "irina@gmail/com", "+4912115856727", "irina31", Role.CLIENT, cart5, null, null, null);
        usersRepository.save(user5);

        System.out.println("Выполняем логику при создании объекта " + this.getClass().getName());
    }

    public List<UserDto> getAllUsers() {
        List<UsersEntity> usersEntity = usersRepository.findAll();
        List<UserDto> userDtoList1 = MapperUtil.convertList(usersEntity, mappers::convertToUserDto);
        return userDtoList1;
    }

    public UserDto getUserByID(Long id) {
        UsersEntity usersEntity = usersRepository.findById(id).orElse(new UsersEntity());

        UserDto userDto = mappers.convertToUserDto(usersEntity);
        return userDto;
    }

    public UserDto getUserByName(String name) {
        UsersEntity usersEntity = usersRepository.findByName(name);
        return new UserDto(usersEntity.getUserId(), usersEntity.getName(), usersEntity.getEmail(), usersEntity.getPhoneNumber(), usersEntity.getPasswordHash());
    }

    public boolean createUsers(UserDto newUserDto) { //update
        UsersEntity usersEntity = mappers.convertToUserEntity(newUserDto);
        UsersEntity returnUserEntity = usersRepository.save(usersEntity);
        return returnUserEntity.getUserId() != 0;
    }

    public UserDto updateUsers(UserDto updUserDto) { //update
        UsersEntity usersEntity = mappers.convertToUserEntity(updUserDto);
        UsersEntity returnUserEntity = usersRepository.save(usersEntity);
        return mappers.convertToUserDto(returnUserEntity);
    }

    public void deleteUsers(Long id) { //delete
        UsersEntity users = usersRepository.findById(id).orElse(null);
        if (users == null) {
            throw new RuntimeException("Нету такого объекта с Id = " + id);
        } else {
            usersRepository.delete(users);
        }
    }

    public void destroyUser() {
        userDtoList.clear();
        System.out.println("Выполняем логику при окончании работы с  объектом " + this.getClass().getName());
    }

}
