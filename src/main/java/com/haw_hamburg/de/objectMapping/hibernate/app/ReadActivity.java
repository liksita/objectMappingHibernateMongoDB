package com.haw_hamburg.de.objectMapping.hibernate.app;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.haw_hamburg.de.objectMapping.hibernate.entities.Comment;
import com.haw_hamburg.de.objectMapping.hibernate.entities.Discussion;
import com.haw_hamburg.de.objectMapping.hibernate.entities.Post;
import com.haw_hamburg.de.objectMapping.hibernate.entities.User;

public class ReadActivity {

	private List<User> users;
	private List<Post> posts;
	private List<Comment> comments;
	private List<Discussion> discussions;

	EntityManager entityManager;

	public ReadActivity(EntityManagerFactory entityManagerFactory) {
		entityManager = entityManagerFactory.createEntityManager();
	}

	public void readEntities() {
		entityManager.getTransaction().begin();
		users = entityManager.createQuery("SELECT u FROM User u ", User.class).getResultList();
		posts = entityManager.createQuery("SELECT p FROM Post p ", Post.class).getResultList();
		comments = entityManager.createQuery("SELECT c FROM Comment c ", Comment.class).getResultList();
		discussions = entityManager.createQuery("SELECT d FROM Discussion d ", Discussion.class).getResultList();
		entityManager.getTransaction().commit();

	}

	public void closeConnection() {
		entityManager.close();
	}

	public static void main(String[] args) {
		ReadActivity readActivity = new ReadActivity(Persistence.createEntityManagerFactory("userPostsMongo"));
		readActivity.readEntities();
		System.out.println("User count: " + readActivity.getUsers().size());
		System.out.println("Post count: " + readActivity.getPosts().size());
		System.out.println("Comment count: " + readActivity.getComments().size());
		System.out.println("Discussion count: " + readActivity.getDiscussions().size());
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Discussion> getDiscussions() {
		return discussions;
	}

	public void setDiscussions(List<Discussion> discussions) {
		this.discussions = discussions;
	}

}
