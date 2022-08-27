package ru.yandex.practicum.catsgram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PostFeedController {

	private final PostService postService;

	public PostFeedController(PostService postService) {
		this.postService = postService;
	}

	@PostMapping("/feed/friends")
	public List<Post> findFriends(@RequestBody String param) {
		ObjectMapper om = new ObjectMapper();
		FriendsParam friendsParam;

		try {
			String paramsFromJSON = om.readValue(param, String.class);
			friendsParam = om.readValue(paramsFromJSON, FriendsParam.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		if (friendsParam != null) {
			List<Post> out = new ArrayList<>();
			for (String friend :friendsParam.getFriends()) {
				out.addAll(postService.findAllByUserEmail(friend, friendsParam.size, friendsParam.sort));
			}
			return out;
		} else {
			throw new RuntimeException("Неверное значение параметров");
		}
	}

	static class FriendsParam {
		private String sort;
		private Integer size;

		private List<String> friends;

		public String getSort() {
			return sort;
		}

		public void setSort(String sort) {
			this.sort = sort;
		}

		public Integer getSize() {
			return size;
		}

		public void setSize(Integer size) {
			this.size = size;
		}

		public List<String> getFriends() {
			return friends;
		}

		public void setFriends(List<String> friends) {
			this.friends = friends;
		}

	}
}
