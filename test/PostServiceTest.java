import static org.junit.Assert.*;

import java.util.List;

import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;

import models.Post;
import services.PostService;

public class PostServiceTest extends AbstractIntegrationTest{
        
    private static PostService postService;
    
    @Autowired
    public void setPostService(PostService ps){
	PostServiceTest.postService = ps;
    }

    @Test
    public void findLatestTest(){
	List<Post> posts = postService.findLatest(3);
	assertTrue(posts.size() == 3);
    }
}
