package org.code.service;

import org.code.data.Post;
import org.code.data.User;
import org.code.exception.NotOwnerException;
import org.code.exception.PostAlreadyExistsException;
import org.code.exception.PostDoesNotExistException;
import org.code.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

@Service
public class PostService {

    private final PostRepository repository;
    private final UserService userService;

    @Autowired
    public PostService(PostRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public Post create(String name, String title, String content)
            throws PostAlreadyExistsException
    {
        var existing = repository.getByTitle(title);
        if (existing.isPresent())
            throw new PostAlreadyExistsException();
       User user =  userService.getByUsername(name);
        var post = new Post(title, content, user);
        repository.save(post);

        return post;
    }

    public Post delete( String username, String title)
            throws PostDoesNotExistException, NotOwnerException
    {
        System.out.println(username + "<---name title --->"+ title);
        User user = userService.getByUsername(username);
        var post = repository
                .getByTitle(title)
                .orElseThrow(PostDoesNotExistException::new);

        if (!post.getCreator().equals(user))
            throw new NotOwnerException();

        repository.delete(post);

        return post;
    }

    public Post edit(String username, String title,String oldTitle, String updatedContent)
            throws PostDoesNotExistException, NotOwnerException
    {
        var user = userService.getByUsername(username);

        var post = repository
                .getByTitle(oldTitle)
                .orElseThrow(PostDoesNotExistException::new);

        if (!post.getCreator().equals(user))
            throw new NotOwnerException();

        repository.delete(post);
        post.setContent(updatedContent);
        post.setTitle(title);
        repository.save(post);

        return post;
    }

    public Collection<Post> getAll() {
        return repository.getAll();
    }
}
