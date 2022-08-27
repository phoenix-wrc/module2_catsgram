package ru.yandex.practicum.catsgram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exceptions.PostNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.exceptions.UserNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {
	private final UserService userService;
	Integer globalId = 0;
	private final List<Post> posts = new ArrayList<>();

	@Autowired
	public PostService(UserService userService) {
		this.userService = userService;
	}

	private Integer getNextId() {
		return globalId++;
	}

	public List<Post> findAll(Integer size, String sort, Integer from) {
		return posts.stream().sorted((p0, p1) -> {
			int comp = p0.getCreationDate().compareTo(p1.getCreationDate());
			if(sort.equals("decs")){
				return -1 * comp;
			}
			return comp;
		}).skip(from).limit(size).collect(Collectors.toList());

	}

	public Post create(Post post) {
		if(userService.findUserByEmail(post.getAuthor()) != null) {
			post.setId(getNextId());
			posts.add(post);
		} else {
			throw new UserNotFoundException("Пользователь " + post.getAuthor() + " не найден.");
		}
		return post;
	}

	public Post findPostById(Integer id) {
		return posts.stream()
				.filter(post -> post.getId().equals(id))
				.findFirst()
				.orElseThrow(() -> new PostNotFoundException(String.format("Пост № %d не найден.", id)));
	}

	public List<Post> findAllByUserEmail(String friend, Integer size, String sort) {
		return posts.stream()
				.filter(post -> friend.equals(post.getAuthor()))
				.sorted((p0, p1) -> {
					int comp = p0.getCreationDate().compareTo(p1.getCreationDate());
					if(sort.equals("decs")){
						return -1 * comp;
					}
					return comp;
				}).limit(size).collect(Collectors.toList());
	}
}
