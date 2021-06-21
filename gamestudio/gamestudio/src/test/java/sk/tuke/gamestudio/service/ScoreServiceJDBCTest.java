package sk.tuke.gamestudio.service;

import org.junit.Test;
import sk.tuke.gamestudio.entity.Score;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class ScoreServiceJDBCTest {

    @Test
    public void addScore() {
        ScoreService service = new ScoreServiceJDBC();
        Score score1 = new Score("tentrix", "Juliana", 60, new Date());
        service.addScore(score1);
        List<Score> scores = service.getBestScores("tentrix");


        assertEquals(1, scores.size());
        assertEquals("Juliana", scores.get(0).getPlayer());
        assertEquals(60, scores.get(0).getPoints());
        assertEquals("tentrix", scores.get(0).getGame());
    }



}
