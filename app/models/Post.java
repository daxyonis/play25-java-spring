package models;

import java.sql.Timestamp;
import java.util.List;

public class Post{
	private int no;
	private Timestamp timestmp;
	private Usager user;
	private String title;
	private String text;
	private List<String> tags;
	
	public int getNo() {
	    return no;
	}
	public void setNo(int no) {
	    this.no = no;
	}
	public Timestamp getTimestmp() {
	    return timestmp;
	}
	public void setTimestmp(Timestamp timestmp) {
	    this.timestmp = timestmp;
	}
	public Usager getUser() {
	    return user;
	}
	public void setUser(Usager user) {
	    this.user = user;
	}
	public String getText() {
	    return text;
	}
	public void setText(String text) {
	    this.text = text;
	}
	public List<String> getTags() {
	    return tags;
	}
	public void setTags(List<String> tags) {
	    this.tags = tags;
	}
	public String getTitle() {
	    return title;
	}
	public void setTitle(String title) {
	    this.title = title;
	}
	
	
}