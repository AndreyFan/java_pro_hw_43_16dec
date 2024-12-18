package de.telran.onlineshop.service;

import de.telran.onlineshop.dto.UserDto;
import de.telran.onlineshop.entity.*;
import de.telran.onlineshop.mapper.Mappers;
import de.telran.onlineshop.repository.CartsRepository;
import de.telran.onlineshop.repository.UsersRepository;
import org.h2.command.dml.MergeUsing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsersServiceTest {
    @Mock
    private UsersRepository usersRepositoryMock;
    @Mock
    private CartsRepository cartsRepositoryMock;
    @Mock
    private Mappers mappersMock;
    @InjectMocks
    private UsersService usersServiceTest;  // unit объект, который тестируется

    private UsersEntity usersEntityTest1;
    private UserDto usersDtoTest1;

    @BeforeEach
    void setUp() {
        usersEntityTest1 = new UsersEntity(
                1L,
                "Васся Пупкин",
                "vasia@i.com",
                "+4912117558877",
                "vasia1555",
                Role.CLIENT,
                new CartEntity(),
                new HashSet<FavoritesEntity>(),
                new HashSet<OrdersEntity>(),
                new HashSet<AddressEntity>()
        );

        usersDtoTest1 = new UserDto(
                1L,
                "Васся Пупкин",
                "vasia@i.com",
                "+4912117558877",
                "vasia1555"
        );
    }

    @Test
    void getAllUsers() {

        UsersEntity usersEntityTest2 = new UsersEntity(
                2L,
                "PeterTest",
                "Peter@i.com",
                "+4912117557777",
                "peter1555",
                Role.CLIENT,
                new CartEntity(),
                new HashSet<FavoritesEntity>(),
                new HashSet<OrdersEntity>(),
                new HashSet<AddressEntity>()
        );

        UserDto usersDtoTest2 = new UserDto(
                2L,
                "PeterTest",
                "Peter@i.com",
                "+4912117557777",
                "peter1555"
        );

        when(usersRepositoryMock.findAll()).thenReturn(List.of(usersEntityTest1, usersEntityTest2));
        when(mappersMock.convertToUserDto(usersEntityTest1)).thenReturn(usersDtoTest1);
        when(mappersMock.convertToUserDto(usersEntityTest2)).thenReturn(usersDtoTest2);

        List<UserDto> actualUserDtoList = usersServiceTest.getAllUsers();

        assertNotNull(actualUserDtoList);
        assertTrue(actualUserDtoList.size() > 0);
        assertTrue(actualUserDtoList.size() == 2);
        assertEquals(1, actualUserDtoList.get(0).getUserID());
        assertEquals(usersDtoTest1, actualUserDtoList.get(0));
        verify(usersRepositoryMock).findAll(); //был ли запущен этот метод
        verify(mappersMock, times(2)).convertToUserDto(any(UsersEntity.class)); //был ли запущен этот метод и сколько раз

    }

    @Test
    void getUserByIDTest() {
        Long testId = 1L;
        when(usersRepositoryMock.findById(testId)).thenReturn(Optional.of(usersEntityTest1));

        when(mappersMock.convertToUserDto(usersEntityTest1)).thenReturn(usersDtoTest1);

        UserDto actualUserDto = usersServiceTest.getUserByID(testId);

        // проверка
        assertNotNull(actualUserDto);
        assertEquals(testId, actualUserDto.getUserID());
        assertEquals(usersDtoTest1, actualUserDto);
        verify(usersRepositoryMock).findById(testId); //был ли запущен этот метод
        verify(mappersMock, times(1)).convertToUserDto(any(UsersEntity.class)); //был ли запущен этот метод и сколько раз
    }

    @Test
    void getUserByNameTest() {
        String nameTest = "Васся Пупкин";
        when(usersRepositoryMock.findByName(nameTest)).thenReturn(usersEntityTest1);

        UserDto result = usersServiceTest.getUserByName(nameTest);

        assertNotNull(result);
        assertEquals(1L, result.getUserID());
        assertEquals("Васся Пупкин", result.getName());
        assertEquals("vasia@i.com", result.getEmail());
        verify(usersRepositoryMock, times(1)).findByName(nameTest);
    }

    @Test
    void updateUserTest() {
        when(mappersMock.convertToUserEntity(usersDtoTest1)).thenReturn(usersEntityTest1);
        when(usersRepositoryMock.save(usersEntityTest1)).thenReturn(usersEntityTest1);
        when(mappersMock.convertToUserDto(usersEntityTest1)).thenReturn(usersDtoTest1);


        UserDto actualUserDto = usersServiceTest.updateUsers(usersDtoTest1);

        // проверка
        assertNotNull(actualUserDto);
        assertEquals(usersDtoTest1.getUserID(), actualUserDto.getUserID());
        assertEquals(usersDtoTest1, actualUserDto);

        verify(mappersMock).convertToUserEntity(usersDtoTest1); //был ли запущен этот метод
        verify(usersRepositoryMock).save(usersEntityTest1); //был ли запущен этот метод
        verify(mappersMock).convertToUserDto(usersEntityTest1); //был ли запущен этот метод

    }

    @Test
    void createUsersTest() {

        UsersEntity userEntityTestInput = new UsersEntity(
                null,
                "PeterTest",
                "Peter@i.com",
                "+4912117557777",
                "peter1555",
                Role.CLIENT,
                new CartEntity(),
                new HashSet<FavoritesEntity>(),
                new HashSet<OrdersEntity>(),
                new HashSet<AddressEntity>()
        );

        UserDto userDtoTestInput = new UserDto(
                null,
                "PeterTest",
                "Peter@i.com",
                "+4912117557777",
                "peter1555"
        );

        when(mappersMock.convertToUserEntity(userDtoTestInput)).thenReturn(userEntityTestInput);
        when(usersRepositoryMock.save(userEntityTestInput)).thenReturn(usersEntityTest1);

        usersServiceTest.createUsers(userDtoTestInput); //запуск реального метода

        verify(mappersMock).convertToUserEntity(userDtoTestInput); //был ли запущен этот метод
        verify(usersRepositoryMock).save(userEntityTestInput); //был ли запущен этот метод
    }

    @Test
    void deleteUsersByIdTest() {
        Long testId = 1L;
        when(usersRepositoryMock.findById(testId)).thenReturn(Optional.of(usersEntityTest1));

        usersServiceTest.deleteUsers(testId); //запуск реального метода

        verify(usersRepositoryMock).delete(usersEntityTest1); //был ли запущен этот метод
    }

    // Exception
    @Test
    void deleteUsersByIdNotFoundTest() {
        Long testId = 1L;
        when(usersRepositoryMock.findById(testId)).thenReturn(Optional.ofNullable(null));

        assertThrows(Exception.class, () -> usersServiceTest.deleteUsers(testId));
    }


}