package sk.tuke.gamestudio.service;


import org.junit.Test;
import sk.tuke.gamestudio.entity.Comment;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CommentServiceJDBCTest {

    @Test
    public void addComment() throws CommentException {
        CommentService service = new CommentServiceJDBC();
        Comment comment1 = new Comment("Juliana", "tentrix", "Awesome game!", new Date());
        Comment comment2 = new Comment("Julka", "tentrix", "Could be better..", new Date());
        service.addComment(comment1);
        service.addComment(comment2);
        List<Comment> comments = service.getComments("tentrix");


        assertEquals(2, comments.size());
        assertEquals("Juliana", comments.get(0).getPlayer());
        assertEquals("Awesome game!", comments.get(0).getComment());
        assertEquals("tentrix", comments.get(0).getGame());
        assertEquals("Julka", comments.get(1).getPlayer());
        assertEquals("Could be better..", comments.get(1).getComment());
    }

}
