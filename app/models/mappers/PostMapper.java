package models.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import models.Post;
import models.Usager;

public class PostMapper implements RowMapper<Post> {

    @Override
    public Post mapRow(ResultSet rs, int row) throws SQLException {
	Post post = new Post();
	post.setNo(rs.getInt("zeNo"));
	post.setTimestmp(rs.getTimestamp("zeTime"));
	post.setTitle(rs.getString("title"));
	post.setText(rs.getString("zeText"));
	
	String email = rs.getString("userEmail");
	
	Usager user = new Usager();
	user.setEmailAddress(email);
	
	post.setUser(user);	
	
	return post;
    }
    
    

}
