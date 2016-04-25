package services.dao;

import java.util.List;

import models.Post;

public interface PostDAO {

    List<Post> findLatest(int numPosts);

    List<Post> findAllUnderTag(String tag);

    List<Post> searchWithKeywords(List<String> keywords);

}
