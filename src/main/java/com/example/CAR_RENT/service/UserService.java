package com.example.CAR_RENT.service;

import com.example.CAR_RENT.entity.Role;
import com.example.CAR_RENT.entity.User;
import com.example.CAR_RENT.service.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    //Метод необходим для поиска и аутентификации пользователя при входе в аккаунт
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    public void save(User user) {
        userRepo.save(user);
    }

    public void saveNewUser(User user) {
        //Создаем идентификационный номер для активации аккаунта
        UUID activationCode = UUID.randomUUID();
        //Аккаунту присваиваем статус неактивен (user расширяет userdetails,
        //проверка на активность при входе происходит под капотом)
        user.setActive(false);
        user.setNumberOfRents(0);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setBalance(0);
        user.setActivationCode(activationCode.toString());
        save(user);
    }


    /**
     * Метод для активации пользователя. После регистрации пользователю на указанную почту
     * приходит сообщение с ссылкой, перейдя по которой он активирует аккаунт.
     *
     * @param user активируемый пользователь
     * @param code уникальный код для активации аккаунта
     */
    public void saveValidateUser(User user, String code) {

        //Если код пользователя равен коду в ссылке, то активируем пользователя и удаляем у него в бд код (на всякий случай)
        if (user.getActivationCode().equals(code)) {
            user.setActive(true);
            user.setActivationCode(null);
            save(user);
        }

    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public List<User> findAllByUsername(String findingName) {
        return userRepo.findAllByUsername(findingName);
    }

    /**
     * Метод, доступный админу, который меняет статус аккаунта пользователя
     *
     * @param user пользователь, которому необходимо включить или отключить аккаунт
     */
    public void changeActivity(User user) {
        user.setActive(!user.isActive());
        save(user);
    }

    public void saveEditUser(User user, String firstname, String lastname) {
        user.setFirstName(firstname);
        user.setLastName(lastname);
        save(user);
    }

    /**
     * Метод для добавления денег на счет
     *
     * @param currentUser текущий пользователь
     * @param balance     баланс, который необходимо добавить
     * @return редирект на профиль
     */
    public void updateBalance(User currentUser, Integer balance) {
        //Если баланс указан верно, то добавить пользователю на счет
        if (balance != null && balance > 0 && balance < 1000000) {
            currentUser.setBalance(currentUser.getBalance() + balance);
            save(currentUser);
        }

    }
}
