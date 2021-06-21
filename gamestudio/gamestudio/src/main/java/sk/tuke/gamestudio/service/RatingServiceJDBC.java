package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;

import java.sql.*;

public class RatingServiceJDBC implements  RatingService{
    public static final String URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String USER = "postgres";
    public static final String PASSWORD = "postgres";

    public static final String INSERT_RATING =
            "INSERT INTO rating (player, game, rating, ratedon) VALUES (?, ?, ?, ?)";

    public static final String SELECT_RATING =
            "SELECT player, game, rating, ratedon FROM rating WHERE game = ? AND player = ?;";

    public static final String SELECT_NUMBER_RATING =
            "SELECT rating FROM rating WHERE game = ? AND player = ?";


    public static final String UPDATE_RATING =
            "UPDATE rating SET rating = ?, ratedon = ? WHERE game = ? AND player = ?";

    @Override
    public void setRating(Rating rating) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try(PreparedStatement ps = connection.prepareStatement(SELECT_RATING)){
                ps.setString(1, rating.getGame());
                ps.setString(2, rating.getPlayer());
                try(ResultSet rs = ps.executeQuery()){
                    if(!rs.next()){
                        try(PreparedStatement pS = connection.prepareStatement(INSERT_RATING)){
                            pS.setString(1, rating.getPlayer());
                            pS.setString(2, rating.getGame());
                            pS.setInt(3, rating.getRating());
                            pS.setDate(4, new Date(rating.getRatedon().getTime()));
                            pS.executeUpdate();
                        }
                    }else{
                        try(PreparedStatement Ps = connection.prepareStatement(UPDATE_RATING)){
                            Ps.setInt(1, rating.getRating());
                            Ps.setDate(2, new Date(rating.getRatedon().getTime()));
                            Ps.setString(3, rating.getGame());
                            Ps.setString(4, rating.getPlayer());
                            Ps.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new ScoreException("Error saving rating", e);
        }

    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        int rating = 0;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try(PreparedStatement ps = connection.prepareStatement("SELECT AVG(rating) FROM rating WHERE game = ?;")){
                ps.setString(1, game);
                try(ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    rating = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new ScoreException("Error loading rating", e);
        }

        return rating;
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        int rating=0;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try(PreparedStatement ps = connection.prepareStatement(SELECT_NUMBER_RATING)){
                ps.setString(1, game);
                ps.setString(2, player);
                try(ResultSet rs = ps.executeQuery()) {
                    if(rs.next()) {
                        rating = rs.getInt(1);
                    }else {
                        System.out.println("Sorry, there is no record of your rating.");
                    }

                }
            }
        } catch (SQLException e) {
            throw new ScoreException("Error loading rating", e);
        }

        return rating;
    }
}
