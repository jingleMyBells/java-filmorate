package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserStorage {
    private final Map<Integer, User> users = new HashMap<>();

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public void createNewUser(User user) {
        int id = getProperUserId();
        user.setId(id);
        users.put(id, user);
    }

    public User getUserById(int id) {
        return users.get(id);
    }

    public void updateUser(User user) {
        User userToEdit = users.get(user.getId());
        userToEdit.setName(user.getName());
        userToEdit.setLogin(user.getLogin());
        userToEdit.setEmail(user.getEmail());
        userToEdit.setBirthday(user.getBirthday());
    }

    private int getProperUserId() {
        if (users.isEmpty()) {
            return 1;
        }
        return Collections.max(users.keySet()) + 1;
    }

}
