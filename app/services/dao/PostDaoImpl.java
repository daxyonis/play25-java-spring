package services.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import models.Post;
import models.mappers.PostMapper;

@Repository
public class PostDaoImpl implements PostDAO {
    
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    public PostDaoImpl(JdbcTemplate jdbc){
	this.jdbcTemplate = jdbc;
    }           

    @Override
    public List<Post> findLatest(int numPosts) {
	String SQL = "SELECT * FROM Post ORDER BY zeno DESC LIMIT ?";
	return jdbcTemplate.query(SQL, new Object[]{numPosts}, new PostMapper());
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
