package skajihara.projectX.MainContents.Home.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import skajihara.projectX.MainContents.Home.entity.Tweet;
import skajihara.projectX.MainContents.Home.service.TweetService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/tweets")
public class TweetController {

    @Autowired
    private final TweetService tweetService;

    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping
    public List<Tweet> getAllTweets() {
        return tweetService.selectAllTweets();
    }

    @GetMapping("/recent")
    public List<Tweet> getRecentTweets(@RequestParam(defaultValue = "20") int num) {
        return tweetService.selectRecentTweets(num);
    }

    @GetMapping("/tweet/{id}")
    public Tweet getTweet(@PathVariable int id) {
        return tweetService.selectTweet(id);
    }

    @PostMapping
    public void createTweet(@RequestBody Tweet tweet) {
        tweetService.createTweet(tweet);
    }

    @PutMapping("/{id}")
    public void updateTweet(@PathVariable int id, @RequestBody Tweet tweet) {
        tweetService.updateTweet(id, tweet);
    }

    @DeleteMapping("/{id}")
    public void deleteTweet(@PathVariable int id) {
        tweetService.deleteTweet(id);
    }
}
