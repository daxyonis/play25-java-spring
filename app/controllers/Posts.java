package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import models.Post;
import play.mvc.Controller;
import play.mvc.Result;

import services.PostService;

@Component
public class Posts extends Controller {
    
    private static PostService postService;
    
    @Autowired
    public void setPostService(PostService ps){
	this.postService = ps;
    }

    public static Result show(){
	List<Post> posts = postService.findLatest(3);
	return ok(views.html.posts.list.render(posts));
    }
}
