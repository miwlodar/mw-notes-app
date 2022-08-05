//ORM class with mapping given Note fields to DB columns
package io.github.miwlodar.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="notes")
public class Note {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name="title")
	private String title;

	@Column(name="content")
	private String content;
	
	@Column(name="owner")
	private String owner;
	
	@Column(name="created", nullable = false, updatable = false)
	@CreationTimestamp
	private Timestamp created;

	@Column(name="last_edited", nullable = false)
	@UpdateTimestamp
	private Timestamp lastEdited;

	public Note() {
	}

	public Note(int id, String content, String title, String owner, Timestamp created, Timestamp lastEdited) {
		this.id = id;
		this.content = content;
		this.title = title;
		this.owner = owner;
		this.created = created;
		this.lastEdited = lastEdited;
	}

	public Note(String content, String title, String owner, Timestamp created, Timestamp lastEdited) {
		this.content = content;
		this.title = title;
		this.owner = owner;
		this.created = created;
		this.lastEdited = lastEdited;
	}

	public Note(String content, String title, String owner) {
		this.content = content;
		this.title = title;
		this.owner = owner;
	}

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

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Timestamp getLastEdited() {
		return lastEdited;
	}

	public void setLastEdited(Timestamp lastEdited) {
		this.lastEdited = lastEdited;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "Note{" +
				"id=" + id +
				", content='" + content + '\'' +
				", title='" + title + '\'' +
				", owner='" + owner + '\'' +
				", created=" + created +
				", lastEdited=" + lastEdited +
				'}';
	}
}











