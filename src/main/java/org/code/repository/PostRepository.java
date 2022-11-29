package org.code.repository;

import org.code.data.Post;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class PostRepository {

    private final Map<String, Post> posts = new HashMap<>();

    public Optional<Post> getByTitle(String title) {
        return Optional.ofNullable(posts.get(title));
    }

    public Optional<Post> save(Post post) {
        var existing = posts.put(post.getTitle(), post);

        return Optional.ofNullable(existing);
    }

    public Optional<Post> delete(Post post) {
        var existing = posts.remove(post.getTitle());

        return Optional.ofNullable(existing);
    }

    public Collection<Post> getAll() {
        return posts.values();
    }

}
