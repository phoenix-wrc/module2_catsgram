package ru.yandex.practicum.catsgram.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exceptions.InvalidEmailException;
import ru.yandex.practicum.catsgram.exceptions.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
	private final Map<String, User> users = new HashMap<>();

	public User createUser(User user) throws UserAlreadyExistException, InvalidEmailException {
		if (user.getEmail() == null) {
			throw new InvalidEmailException("Пользователь не ввел почту. ");
		} else if(user.getEmail().isEmpty() || user.getEmail().isBlank()) {
			throw new InvalidEmailException("Пользователь ввел неправильную почту - " + user.getEmail() + ".");
		} if (users.containsKey(user.getEmail())) {
			throw new UserAlreadyExistException("Пользователь уже существует. ");
		}
		users.put(user.getEmail(), user);
		return user;
	}

	public User update(User user) throws InvalidEmailException {
		if (user.getEmail() == null){
			throw new InvalidEmailException("Пользователь не ввел почту. ");
		} else if(user.getEmail().isEmpty() || user.getEmail().isBlank()) {
			throw new InvalidEmailException("Пользователь ввел неправильную почту - " + user.getEmail() + ".");
		} if (users.containsKey(user.getEmail())) {
			users.put(user.getEmail(), user);
		} else {
			System.out.print("Не содержит. ");
			users.put(user.getEmail(), user);
		}
		return user;
	}

	public List<User> users() {
		return List.of(users.values().toArray(new User[0]));
	}

	public User findUserByEmail(String email) {
		if (email == null) {
			return null;
		}
		return users.get(email);
	}
}
