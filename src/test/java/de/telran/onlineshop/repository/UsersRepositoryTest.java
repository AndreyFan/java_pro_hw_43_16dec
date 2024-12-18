package de.telran.onlineshop.repository;

import de.telran.onlineshop.entity.CategoriesEntity;
import de.telran.onlineshop.entity.Role;
import de.telran.onlineshop.entity.UsersEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepositoryTest;

    private static final String NAME_TEST = "Test";
    private static final String Email_TEST = "test@repo.test";
    private static final String Phone_TEST = "+49 1512 7777777";

    private static final String PasswordHash_TEST = "Password_Test";
    private static UsersEntity usersEntityTest;

    @BeforeAll
    static void setStart() {
        usersEntityTest = new UsersEntity(null, NAME_TEST, Email_TEST, Phone_TEST, PasswordHash_TEST, Role.CLIENT, null, null, null, null);
        System.out.println("Выполняется setStart(единоразово, перед запуском всех тестов)!");
    }

    @Test
    void findAllTest() {
        List<UsersEntity> usersTest = usersRepositoryTest.findAll();
        assertNotNull(usersTest);
    }

    @Test
    void insertTest() {
        String nameExpected = NAME_TEST;
        String emailExpected = Email_TEST;
        String phoneExpected = Phone_TEST;
        String passwordExpected = PasswordHash_TEST;

        UsersEntity usersInsertTest = usersEntityTest;

        UsersEntity usersActual = usersRepositoryTest.save(usersInsertTest);

        assertNotNull(usersActual);
        assertTrue(usersActual.getUserId() != null);
        assertEquals(emailExpected, usersActual.getEmail());
        assertEquals(nameExpected, usersActual.getName());
        assertEquals(phoneExpected, usersActual.getPhoneNumber());
        assertEquals(passwordExpected, usersActual.getPasswordHash());

    }

    @Test
    void updateTest() {
        // when
        UsersEntity userData = generateTestData();

        // test
        String nameExpected = "NewTestUserName";
        String phoneExpected = "NewTestUserPhone";
        String emailExpected = "NewTestUserEmail";
        userData.setName(nameExpected);
        userData.setPhoneNumber(phoneExpected);
        userData.setEmail(emailExpected);
        UsersEntity userActual = usersRepositoryTest.save(userData);

        //assert
        assertNotNull(userData);
        assertEquals(emailExpected, userActual.getEmail());
        assertEquals(nameExpected, userActual.getName());
        assertEquals(phoneExpected, userActual.getPhoneNumber());

    }

    @Test
    void findByNameTest() {
        //when
        UsersEntity userExpected = generateTestData();

        // run
        String nameExpected = userExpected.getName();
        UsersEntity userActual = usersRepositoryTest.findByName(nameExpected);

        //assert
        assertNotNull(userActual);
        assertEquals(nameExpected, userActual.getName());

    }

    @Test
    void findById() {
        //when
        UsersEntity userExpected = generateTestData();

        Long userExpectedId= userExpected.getUserId();

        Optional<UsersEntity> userActual= usersRepositoryTest.findById(userExpectedId);
        assertTrue(userActual.isPresent() && userActual.get().equals(userExpected));

    }

    @Test
    void deleteTest() {
        //when
        UsersEntity userExpected = generateTestData();

        Long idTest = userExpected.getUserId();
        usersRepositoryTest.deleteById(idTest);

        //проверка
        Optional<UsersEntity> userActualOptional = usersRepositoryTest.findById(idTest);
        assertFalse(userActualOptional.isPresent());

    }

    private UsersEntity generateTestData() {
        UsersEntity userInsert = usersRepositoryTest.save(usersEntityTest);
        assertNotNull(userInsert);
        assertTrue(userInsert.getUserId() != null);
        assertEquals(NAME_TEST, userInsert.getName());
        assertEquals(Email_TEST, userInsert.getEmail());
        assertEquals(Phone_TEST, userInsert.getPhoneNumber());
        assertEquals(PasswordHash_TEST, userInsert.getPasswordHash());
        return userInsert;
    }
}