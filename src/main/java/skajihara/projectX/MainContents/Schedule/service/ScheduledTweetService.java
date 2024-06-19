package skajihara.projectX.MainContents.Schedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skajihara.projectX.MainContents.Schedule.entity.ScheduledTweet;
import skajihara.projectX.MainContents.Exception.NotFoundException;
import skajihara.projectX.MainContents.Schedule.repository.ScheduledTweetRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduledTweetService {

    @Autowired
    private final ScheduledTweetRepository scheduledTweetRepository;

    public ScheduledTweetService(ScheduledTweetRepository scheduledTweetRepository) {
        this.scheduledTweetRepository = scheduledTweetRepository;
    }

    public List<ScheduledTweet> selectScheduledTweets(String account_id) {
        return scheduledTweetRepository.selectScheduledTweets(account_id);
    }

    public ScheduledTweet selectScheduledTweet(int schedule_id){

        ScheduledTweet scheduledTweet = scheduledTweetRepository.selectScheduledTweet(schedule_id);
        if (scheduledTweet == null) {
            throw new NotFoundException("Scheduled Tweet not found with id: " + schedule_id);
        }
        return scheduledTweet;
    }

    public void createScheduledTweet(ScheduledTweet scheduledTweet) {
        scheduledTweetRepository.save(scheduledTweet);
    }

    public void updateScheduledTweet(int schedule_id, ScheduledTweet scheduledTweet) {
        Optional<ScheduledTweet> existingScheduledTweetOptional = scheduledTweetRepository.findById(schedule_id);
        if (existingScheduledTweetOptional.isEmpty()) {
            throw new NotFoundException("Scheduled Tweet not found with id: " + schedule_id);
        }
        ScheduledTweet existingScheduledTweet = existingScheduledTweetOptional.get();
        scheduledTweet.setId(existingScheduledTweet.getId());
        scheduledTweetRepository.save(scheduledTweet);
    }

    public void deleteScheduledTweet(int schedule_id) {
        Optional<ScheduledTweet> existingScheduledTweetOptional = scheduledTweetRepository.findById(schedule_id);
        if (existingScheduledTweetOptional.isEmpty()) {
            throw new NotFoundException("Scheduled Tweet not found with id: " + schedule_id);
        }
        ScheduledTweet scheduledTweet = existingScheduledTweetOptional.get();
        scheduledTweetRepository.delete(scheduledTweet);
    }
}
