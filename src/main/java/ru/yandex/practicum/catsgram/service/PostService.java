package ru.yandex.practicum.catsgram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.exceptions.UserNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostService {
	private final UserService userService;
	private final Map<Integer, Post> posts = new HashMap<>();

	@Autowired
	public PostService(UserService userService) {
		this.userService = userService;
	}

	public List<Post> findAll() {
		return List.of(posts.values().toArray(new Post[0]));
	}

	public Post create(Post post) {
		if(userService.findUserByEmail(post.getAuthor()) != null) {
			posts.put(post.getId(), post);
		} else {
			throw new UserNotFoundException("Пользователь " + post.getAuthor() + " не найден.");
		}
		return post;
	}

	public Post findPostById(Integer id) {
		Post out = posts.get(id);
//		if (out == null) {
//
//		}
		return out;
	}
}
