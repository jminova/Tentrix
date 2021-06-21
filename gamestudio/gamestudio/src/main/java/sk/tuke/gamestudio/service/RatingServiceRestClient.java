package sk.tuke.gamestudio.service;

import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Rating;


public class RatingServiceRestClient implements RatingService{
    private static final String URL = "http://localhost:8080/api/rating";

    private RestTemplate restTemplate = new RestTemplate();


    @Override
    public void setRating(Rating rating) throws RatingException {
        restTemplate.postForEntity(URL, rating, Rating.class);
    }

    @Override
    public int getAverageRating(String gameName) throws RatingException {
        int rating = 0;
        try {
            rating = (restTemplate.getForEntity(URL + "/" + gameName, Integer.class).getBody());
        }catch (HttpServerErrorException e){
            System.out.println("No rating available.");
        }
        return rating;

    }

    @Override
    public int getRating(String gameName, String player) throws RatingException {
        int rating = 0;
        try {
            rating = (restTemplate.getForEntity(URL + "/" + gameName + "/" + player, Integer.class).getBody());
        }
        catch (HttpServerErrorException e){
            System.out.println("No rating available.");
        }
        return rating;
    }
}
