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
    public List<Post> findAll(
            @RequestParam(value = "sort", defaultValue = "asc", required = false) String sort,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size
    ) {
        if (!(sort.equals("asc") || sort.equals("desc"))) {
            throw  new IllegalArgumentException();
        }
        if(size < 0 || page <= 0) {
            throw new IllegalArgumentException();
        }
        Integer from = size * page;
        List<Post> out = postService.findAll(size, sort, from);
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