package io.github.mwlodar.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name="notes")
public class Note {

	// define fields
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="content")
	private String content;
	
	@Column(name="owner")
	private String owner;
	
	@Column(name="created")
	@CreatedDate
	Date created;

	@Column(name="last_edited")
	@CreatedDate
	@LastModifiedDate
	Date lastEdited;
		
	// define constructors
	
	public Note() {
		
	}

	public Note(int id, String content, String owner, Date created, Date lastEdited) {
		this.id = id;
		this.content = content;
		this.owner = owner;
		this.created = created;
		this.lastEdited = lastEdited;
	}

	public Note(String content, String owner, Date created, Date lastEdited) {
		this.content = content;
		this.owner = owner;
		this.created = created;
		this.lastEdited = lastEdited;
	}

	public Note(String content, String owner) {
		this.content = content;
		this.owner = owner;
	}

// define getter/setter
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastEdited() {
		return lastEdited;
	}

	public void setLastEdited(Date lastEdited) {
		this.lastEdited = lastEdited;
	}

	// define tostring

	@Override
	public String toString() {
		return "Note{" +
				"id=" + id +
				", content='" + content + '\'' +
				", owner='" + owner + '\'' +
				", created=" + created +
				", lastEdited=" + lastEdited +
				'}';
	}

}











