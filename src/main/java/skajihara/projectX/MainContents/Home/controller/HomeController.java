package skajihara.projectX.MainContents.Home.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import skajihara.projectX.MainContents.Home.entity.Tweet;
import skajihara.projectX.MainContents.Home.service.TimelineService;

import java.util.List;

@RestController
@RequestMapping("/api/tweets")
public class HomeController {

    @Autowired
    private final TimelineService timelineService;

    public HomeController(TimelineService timelineService) {
        this.timelineService = timelineService;
    }

    @GetMapping
    public List<Tweet> getAllTweets() {
        return timelineService.selectAllTweets();
    }

    @GetMapping("/recent")
    public List<Tweet> getRecentTweets(@RequestParam(defaultValue = "20") int num) {
        return timelineService.selectRecentTweets(num);
    }

    @PostMapping
    public void createTweet(@RequestBody Tweet tweet) {
        timelineService.createTweet(tweet);
    }

    @PutMapping("/{id}")
    public void updateTweet(@PathVariable int id, @RequestBody Tweet tweet) {
        timelineService.updateTweet(id, tweet);
    }

    @DeleteMapping("/{id}")
    public void deleteTweet(@PathVariable int id) {
        timelineService.deleteTweet(id);
    }
}