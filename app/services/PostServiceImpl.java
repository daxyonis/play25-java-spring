package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import models.Post;
import services.dao.PostDAO;

@Service
public class PostServiceImpl implements PostService{
    
    private PostDAO postDAO;
    
    @Autowired
    public PostServiceImpl(PostDAO postDAO){
	this.postDAO = postDAO;
    }

    @Override
    public List<Post> findLatest(int numPosts) {
	return postDAO.findLatest(numPosts);
    }

    @Override
    public List<Post> findAllUnderTag(String tag) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Post> searchWithKeywords(List<String> keywords) {
	// TODO Auto-generated method stub
	return null;
    }

    
}
