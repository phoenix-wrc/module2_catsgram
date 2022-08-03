package ru.yandex.practicum.catsgram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.List;

@RestController
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }
    private final static Logger log = LoggerFactory.getLogger(PostController.class);

    @GetMapping("/posts")
    public List<Post> findAll() {
        List<Post> out = postService.findAll();
        log.debug("Текущее количество постов: {}", out.size());
        return out;
    }

    @PostMapping(value = "/post")
    public Post create(@RequestBody Post post) {
        log.debug("Сохраняется объект: {}", post);
        return postService.create(post);
    }

    @GetMapping(value = "/post/{id}")
    public Post getById(@PathVariable int id) {
        Post out = postService.findPostById(id);
        log.debug("Находим по id объект: {}", out);
        return out;
    }
}