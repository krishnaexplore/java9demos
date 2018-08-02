package org.books;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Book{
  private String title;
	private String author;
	private Long bookid;
  @JsonProperty("date")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern= "EEE MMM d HH:mm:ss Z yyyy", timezone = "GMT")
	private Timestamp date;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Long getBookid() {
		return bookid;
	}

	public void setBookid(Long bookid) {
		this.bookid = bookid;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}


}
