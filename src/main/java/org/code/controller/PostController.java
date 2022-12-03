package org.code.controller;

import org.code.data.Post;
import org.code.data.User;
import org.code.exception.NotOwnerException;
import org.code.exception.PostAlreadyExistsException;
import org.code.exception.PostDoesNotExistException;
import org.code.model.DeleteDto;
import org.code.model.PostDto;
import org.code.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequestMapping("/post")
@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class PostController {

    PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }



    @GetMapping("/all")
    public Collection<Post> getAllPosts(){
        return postService.getAll();
    }

    @PostMapping("/create")
    public Post createPost(@RequestBody PostDto postDto) throws PostAlreadyExistsException {
        return postService.create(postDto.username(),postDto.title(),postDto.content());
    }

    @PostMapping("/delete")
    public void deletePost(@RequestBody DeleteDto deleteDto) throws NotOwnerException, PostDoesNotExistException {
        postService.delete(deleteDto.username(), deleteDto.title());
    }

    @PutMapping("/update")
    public Post updatePost(@RequestBody PostDto postDto) throws NotOwnerException, PostDoesNotExistException {
        return postService.edit(postDto.username(), postDto.title(), postDto.oldTitle(), postDto.content());
    }


}
