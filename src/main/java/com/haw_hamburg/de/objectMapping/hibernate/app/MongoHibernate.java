package com.haw_hamburg.de.objectMapping.hibernate.app;

import java.util.Arrays;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import com.haw_hamburg.de.objectMapping.hibernate.entities.Comment;
import com.haw_hamburg.de.objectMapping.hibernate.entities.Discussion;
import com.haw_hamburg.de.objectMapping.hibernate.entities.LoginData;
import com.haw_hamburg.de.objectMapping.hibernate.entities.Post;
import com.haw_hamburg.de.objectMapping.hibernate.entities.User;

public class MongoHibernate {

	// Testkonfig
	public Integer inserts;

	private static int runCount = 0;

	private static EntityManagerFactory entityManagerFactory;
	EntityManager entityManager;

	public MongoHibernate(Integer inserts) {
		this.inserts = inserts;
		entityManagerFactory = Persistence.createEntityManagerFactory("userPostsMongo");

		entityManager = entityManagerFactory.createEntityManager();
	}

	public void persistEntities() {

		runCount++;

		for (int j = 0; j < this.inserts; j++) {
			entityManager.getTransaction().begin();

			// create a User
			User user1 = new User("user1", "user1" + runCount + j);
			User user2 = new User("user2", "user2" + runCount + j);

			LoginData loginData1 = new LoginData("user1", "password1");
			LoginData loginData2 = new LoginData("user2", "password2");

			user1.setLoginData(loginData1);
			user2.setLoginData(loginData2);

			Discussion discussion1 = new Discussion("discussion1" + runCount + j);
			Discussion discussion2 = new Discussion("discussion2" + runCount + j);

			discussion1.setUsers(Arrays.asList(user1, user2));
			discussion2.setUsers(Arrays.asList(user1, user2));

			// and three posts
			Post post1 = new Post("Title1 " + runCount + j, new Date());
			Post post2 = new Post("Title2 " + runCount + j, new Date());
			Post post3 = new Post("Title3 " + runCount + j, new Date());

			// and two Comments
			Comment comment1 = new Comment(new Date());
			Comment comment2 = new Comment(new Date());

			// let Bob post two posts
			post1.setAuthor(user1);
			user1.getUserPosts().add(post1);

			comment1.setAuthor(user1);
			user1.getUserComments().add(comment1);

			comment2.setAuthor(user1);
			user1.getUserComments().add(comment2);

			comment1.setPost(post1);
			post1.getUserComments().add(comment1);

			comment2.setPost(post2);
			post2.getUserComments().add(comment2);

			post2.setAuthor(user1);
			user1.getUserPosts().add(post2);

			post3.setAuthor(user2);
			user2.getUserPosts().add(post3);

			// persist author (will be cascaded to posts)
			entityManager.persist(user1);
			entityManager.persist(user2);

			entityManager.persist(discussion1);
			entityManager.persist(discussion2);

			entityManager.getTransaction().commit();

		}

	}

	public Integer getInserts() {
		return inserts;
	}

	public void setInserts(Integer inserts) {
		this.inserts = inserts;
	}

	public void closeConnection() {
		entityManager.close();
		entityManagerFactory.close();
	}

}
