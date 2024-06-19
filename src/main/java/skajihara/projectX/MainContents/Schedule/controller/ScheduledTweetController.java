package skajihara.projectX.MainContents.Schedule.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import skajihara.projectX.MainContents.Schedule.entity.ScheduledTweet;
import skajihara.projectX.MainContents.Schedule.service.ScheduledTweetService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/schedule")
public class ScheduledTweetController {

    @Autowired
    private final ScheduledTweetService scheduledTweetService;

    public ScheduledTweetController(ScheduledTweetService scheduledTweetService) {
        this.scheduledTweetService = scheduledTweetService;
    }

    @GetMapping("/account/{account_id}")
    public List<ScheduledTweet> getScheduledTweetsByAccountId(@PathVariable String account_id) {
        return scheduledTweetService.selectScheduledTweets(account_id);
    }

    @GetMapping("/{schedule_id}")
    public ScheduledTweet getScheduledTweet(@PathVariable int schedule_id) {
        return scheduledTweetService.selectScheduledTweet(schedule_id);
    }

    @PostMapping
    public void createScheduledTweet(@RequestBody ScheduledTweet scheduledTweet) {
        scheduledTweetService.createScheduledTweet(scheduledTweet);
    }

    @PutMapping("/{schedule_id}")
    public void updateScheduledTweet(@PathVariable int schedule_id, @RequestBody ScheduledTweet scheduledTweet) {
        scheduledTweetService.updateScheduledTweet(schedule_id, scheduledTweet);
    }

    @DeleteMapping("/{schedule_id}")
    public void deleteScheduledTweet(@PathVariable("schedule_id") int schedule_id) {
        scheduledTweetService.deleteScheduledTweet(schedule_id);
    }
}
