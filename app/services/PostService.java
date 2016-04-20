import models.Post;
import java.util.List;

public interface PostService{
	List<Post> findLatest(int numPosts);
	
	List<Post> findAllUnderTag(String tag);
	
	List<Post> searchWithKeywords(List<String> keywords);
	
}