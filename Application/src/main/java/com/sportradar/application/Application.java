 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.sportradar.application;

import com.sportradar.scoreboard.Scoreboard;
import com.sportradar.scoreboard.entity.Match;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 *
 * @author enrodpre
 */
@SpringBootApplication(scanBasePackages = "com.sportradar.scoreboard")
public class Application {

    public static void main(String[] args) {
        //SpringApplication.run(Scoreboard.class, args);
        
        ConfigurableApplicationContext c = SpringApplication.run(Application.class, args);
        Scoreboard scoreboard = c.getBean(Scoreboard.class);
        
        scoreboard.startGame("Holanda", "Norway");
        scoreboard.getSummaryByTotalScore()
                .stream()
                .map(Match::toString)
                .forEach(System.out::println);
    }
}
