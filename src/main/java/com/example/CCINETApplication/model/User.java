package com.example.CCINETApplication.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "appUsers", indexes = {
        @Index(name = "idx_username", columnList = "username")
		})
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username")
	private String username;
	@Column(name = "firstname")
	private String firstname;
	@Column(name = "surname")
	private String surname;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Task> tasks;

	public User() {
	}

	public User(String username, String firstname, String surname) {
		this.username = username;
		this.firstname = firstname;
		this.surname = surname;
	}

	// Getters and setters

	public User(Long id, String username, String firstname, String surname, List<Task> tasks) {
		super();
		this.id = id;
		this.username = username;
		this.firstname = firstname;
		this.surname = surname;
		this.tasks = tasks;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
}
