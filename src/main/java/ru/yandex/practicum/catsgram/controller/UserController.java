package ru.yandex.practicum.catsgram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.model.User;
import ru.yandex.practicum.catsgram.exceptions.InvalidEmailException;
import ru.yandex.practicum.catsgram.exceptions.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.service.UserService;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {
	private final UserService userService;
	private final static Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	public UserController(UserService service) {
		this.userService = service;
	}

	@PostMapping
	public User createUser(@RequestBody User user) throws UserAlreadyExistException, InvalidEmailException {
		log.debug("Создается пользователь: {}", user);
		userService.createUser(user);
		return user;
	}

	@PutMapping
	public User update(@RequestBody User user) throws InvalidEmailException {
		log.debug("Получен запрос GET /home.");
		return userService.update(user);
	}

	@GetMapping
	public List<User> users() {
		List<User> out = userService.users();
		log.debug("Количество пользователей перед добавлением: {}", out.size());
		return out;
	}

	@GetMapping("user/{mail}")
	public User userByEmail(@PathVariable String mail) {
		User out = userService.findUserByEmail(mail);
		log.debug("Находим по почте пользователя {}", out);
		return out;
	}
}
