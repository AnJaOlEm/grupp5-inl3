package org.code.controller;

import lombok.AllArgsConstructor;
import org.code.data.Post;
import org.code.data.User;
import org.code.exception.NotOwnerException;
import org.code.exception.PostAlreadyExistsException;
import org.code.exception.PostDoesNotExistException;
import org.code.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequestMapping("/post")
@RestController
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
    public Post createPost(@RequestBody Post post) throws PostAlreadyExistsException {
        System.out.println(post.getContent() + " <---------------");
        return postService.create(post.getCreator(), post.getTitle(), post.getContent());
    }

    @DeleteMapping("/delete")
    public void deletePost(User user, String title) throws NotOwnerException, PostDoesNotExistException {
        postService.delete(user, title);
    }

    @PutMapping("/update")
    public Post updatePost(User user, String title, String content) throws NotOwnerException, PostDoesNotExistException {
        return postService.edit(user, title, content);
    }

}
