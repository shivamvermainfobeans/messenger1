package com.rest.jersey.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.rest.jersey.messenger.database.DatabaseClass;
import com.rest.jersey.messenger.model.Comment;
import com.rest.jersey.messenger.model.ErrorMessage;
import com.rest.jersey.messenger.model.Message;

public class CommentService {
	
	private Map<Long, Message> messages = DatabaseClass.getMessages();
	private Map<Long, Comment> comments = DatabaseClass.getComments();
 
	public CommentService() {
		comments.put(1L, new Comment(1, "Hello" ,"Abhi"));
		comments.put(2L, new Comment(2, "Hello World!" ,"Abha"));
	}

	public List<Comment> getAllComments(long messageId){
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return new ArrayList<Comment>(comments.values());
	}
	
	public Comment getComment(long messageId, long commentId){
		ErrorMessage errorMessage = new ErrorMessage("Not Found ", 404, "This is not Found");
		Response response = Response.status(Status.NOT_FOUND)
						.entity(errorMessage)
						.build();
		Message message = messages.get(messageId);
		if(message == null){
			throw new WebApplicationException(response); 
		}
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		Comment comment = comments.get(commentId);
		if(comment == null){
			throw new NotFoundException(response);
		}
		return comment;
	}
	
	public Comment addComment(long messageId, Comment comment){
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		comment.setId(comments.size() + 1);
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment updateComment(long messageId, Comment comment){
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		if(comment.getId() <= 0){
			return null;
		}
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment removeComment(long messageId, long commentId){
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return comments.remove(commentId);
	}
}

