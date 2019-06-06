package com.devworker.kms.fts;

import com.devworker.kms.dto.FtsDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName="posts", type="posts")
public class FTSDao {
	@Id
	private int id;	
	private String name;	
	private String user;
	private String time;	
	private int category;
	private String word;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public FtsDto toDto(){
		FtsDto dto = new FtsDto();
		dto.setCategory(category);
		dto.setId(id);
		dto.setName(name);
		dto.setTime(time);
		dto.setUser(user);
		dto.setWord(word);
		return dto;
	}
}