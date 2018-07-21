package com.haw_hamburg.de.objectMapping.hibernate.entities;

import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.ManyToOne;
import javax.persistence.InheritanceType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "TYPE")
public abstract class Activity {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	String id;

	Date date;

	@ManyToOne
	User author;

	// constructors, getters and setters...
	Activity() {

	}

	public Activity(Date date) {
		this.date = date;
	}

	public abstract String getId();

	public abstract void setId(String id);

	public abstract Date getDate();

	public abstract void setDate(Date date);

	public abstract User getAuthor();

	public abstract void setAuthor(User author);

}
