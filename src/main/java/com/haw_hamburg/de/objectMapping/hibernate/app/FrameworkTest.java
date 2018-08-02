package com.haw_hamburg.de.objectMapping.hibernate.app;

import java.net.UnknownHostException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.haw_hamburg.de.objectMapping.utils.Result;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class FrameworkTest {

	// Result Object
	private Result resultWrite;
	private Result resultRead;

	// Node and Port Config
	private String node = "localhost";
	private Integer port = 27017;

	// Host
	private MongoClient mongoClient;

	// Collection
	private String collection_user_name = "User";
	private DBCollection collection_user = null;

	// Collection
	private String collection_post_name = "Post";
	private DBCollection collection_post = null;

	// Collection
	private String collection_comment_name = "Comment";
	private DBCollection collection_comment = null;

	// Collection
	private String collection_discussion_name = "Discussion";
	private DBCollection collection_discussion = null;

	// DB
	private String db_name = "UserPostsMongo";

	// Entity Manager Factory
	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("userPostsMongo");

	private DB db = null;

	// Testkonfig
	public Integer inserts;
	public Integer runs;

	StoreActivity storeActivity;
	ReadActivity readActivity;

	public FrameworkTest() {

	}

	public FrameworkTest(Integer inserts, Integer runs) {
		this.inserts = inserts;
		this.runs = runs;
		storeActivity = new StoreActivity(inserts, entityManagerFactory);
		readActivity = new ReadActivity(entityManagerFactory);
	}

	public Integer getInserts() {
		return inserts;
	}

	public void setInserts(Integer inserts) {
		this.inserts = inserts;
	}

	public Integer getRuns() {
		return runs;
	}

	public void setRuns(Integer runs) {
		this.runs = runs;
	}

	private ServerAddress initialise() throws UnknownHostException {
		ServerAddress addrs = new ServerAddress(this.node, this.port);
		return addrs;
	}

	public void performTests() throws Exception {

	}

	public Result performWriteTest() throws Exception {

		// Intialize Variables
		this.resultWrite = new Result();

		// Create Test Environment
		createTestEnvironment();

		int runsWrite = this.runs;

		// Execute Runs
		for (int i = 0; i < runsWrite; i++) {

			// Record Start Time
			long startTime = System.nanoTime();

			// Insert Documents
			storeActivity.persistEntities();

			// Record End Time and calculate Run Time
			long estimatedTime = System.nanoTime() - startTime;
			double seconds = (double) estimatedTime / 1000000000.0;

			printCount();

			resultWrite.addMeasureResult("Write Run" + (i), seconds, this.inserts, true);
			System.out.println("Write Run" + (i) + " finished");

		}

		this.storeActivity.closeConnection();

		// Print Result
		return this.resultWrite;

	}

	public Result performReadTest() throws Exception {
		// Intialize Variables
		this.resultRead = new Result();

			// Record Start Time
			long startTime = System.nanoTime();

			// Read Documents
			readActivity.readEntities();

			// Record End Time and calculate Run Time
			long estimatedTime = System.nanoTime() - startTime;
			double seconds = (double) estimatedTime / 1000000000.0;

			resultRead.addMeasureResult("Read All Entries", seconds, this.inserts * this.runs, false);
		this.readActivity.closeConnection();
		deleteTestEnvironment();

		// Print Result
		return this.resultRead;
	}

	@SuppressWarnings("deprecation")
	private void createTestEnvironment() throws Exception {

		// Get URI
		ServerAddress addrs = initialise();

		// Connect to MongoDB Server
		this.mongoClient = new MongoClient(addrs);

		// Connect to Database (Creates the DB if it does not exist)
		this.db = this.mongoClient.getDB(this.db_name);

		// Create and Connect to Collection
		this.db.createCollection(this.collection_user_name, null);
		this.db.createCollection(this.collection_post_name, null);
		this.db.createCollection(this.collection_comment_name, null);
		this.db.createCollection(this.collection_discussion_name, null);
		this.collection_user = this.db.getCollection(this.collection_user_name);
		this.collection_post = this.db.getCollection(this.collection_post_name);
		this.collection_comment = this.db.getCollection(this.collection_comment_name);
		this.collection_discussion = this.db.getCollection(this.collection_discussion_name);
	}

	private void deleteTestEnvironment() {

		// Delete Connection
		this.db.getCollection(this.collection_user_name).drop();
		this.db.getCollection(this.collection_post_name).drop();
		this.db.getCollection(this.collection_comment_name).drop();
		this.db.getCollection(this.collection_discussion_name).drop();
		entityManagerFactory.close();

	}

	private void printCount() {
		System.out.println("Count users " + this.collection_user.find().count());
		System.out.println("Count posts " + this.collection_post.find().count());
		System.out.println("Count comments " + this.collection_comment.find().count());
		System.out.println("Count discussions " + this.collection_discussion.find().count());
	}

}
