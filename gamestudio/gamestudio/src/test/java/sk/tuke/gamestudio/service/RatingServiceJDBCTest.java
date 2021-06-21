package sk.tuke.gamestudio.service;


import org.junit.Test;
import sk.tuke.gamestudio.entity.Rating;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class RatingServiceJDBCTest {
    private RatingService service = new RatingServiceJDBC();


    @Test
    public void setRating() throws RatingException {
        Rating rating1 = new Rating("Juliana","tentrix",3,new Date());
        service.setRating(rating1);

        assertEquals(3,service.getRating("tentrix","Juliana"));
    }


    @Test
    public void setNewRatingFromTheSamePlayerToTheSameGame() throws RatingException {
        Rating rating = new Rating("Juliana","tentrix",4,new Date());
        service.setRating(rating);
        Rating rating1 = new Rating("Juliana","tentrix",3,new Date());
        service.setRating(rating1);

        assertNotEquals(4,service.getRating("tentrix","Juliana"));
        assertEquals(3,service.getRating("tentrix","Juliana"));

    }



    @Test
    public void getAverageRating() throws RatingException {
        Rating rating1 = new Rating("Juliana","tentrix",3,new Date());
        Rating rating2 = new Rating("Julka","tentrix",5,new Date());
        service.setRating(rating1);
        service.setRating(rating2);

        assertEquals(4,service.getAverageRating("tentrix"));
    }
}
