package ru.yandex.practicum.catsgram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.model.User;
import ru.yandex.practicum.catsgram.exceptions.InvalidEmailException;
import ru.yandex.practicum.catsgram.exceptions.UserAlreadyExistException;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {

	private final Map<String, User> users = new HashMap<>();
	private final static Logger log = LoggerFactory.getLogger(UserController.class);

	@PostMapping
	public User createUser(@RequestBody User user) throws UserAlreadyExistException, InvalidEmailException {
		log.debug("Создается пользователь: {}", user);
		if (user.getEmail() == null){
			throw new InvalidEmailException("Пользователь не ввел почту. ");
		} else if(user.getEmail().isEmpty() || user.getEmail().isBlank()) {
			throw new InvalidEmailException("Пользователь ввел неправильную почту - " + user.getEmail() + ".");
		} if (users.containsKey(user.getEmail())) {
			throw new UserAlreadyExistException("Пользователь уже существует. ");
		}
		users.put(user.getEmail(), user);
		return user;
	}

	@PutMapping
	public User update(@RequestBody User user) throws InvalidEmailException {
		log.debug("Получен запрос GET /home.");
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

	@GetMapping
	public List<User> users() {
		log.debug("Количество пользователей перед добавлением: {}", users.size());
		return List.of(users.values().toArray(new User[0]));
	}
}
