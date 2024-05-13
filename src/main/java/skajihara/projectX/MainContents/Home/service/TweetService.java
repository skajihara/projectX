package skajihara.projectX.MainContents.Home.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skajihara.projectX.MainContents.Home.entity.Tweet;
import skajihara.projectX.MainContents.Home.exception.TweetException;
import skajihara.projectX.MainContents.Home.repository.TweetRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TweetService {

    @Autowired
    private final TweetRepository tweetRepository;

    public TweetService(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    public List<Tweet> selectAllTweets() {
        return tweetRepository.selectAll();
    }

    public List<Tweet> selectRecentTweets(int num){
        return tweetRepository.selectRecentN(num);
    }

    public void createTweet(Tweet tweet) {
        tweetRepository.save(tweet);
    }

    public void updateTweet(int id, Tweet tweet) {
        Optional<Tweet> existingTweetOptional = tweetRepository.findById(id);
        if (existingTweetOptional.isEmpty()) {
            throw new TweetException("Tweet not found with id: " + id);
        }
        Tweet existingTweet = existingTweetOptional.get();
        tweet.setId(existingTweet.getId());
        tweetRepository.save(tweet);
    }

    public void deleteTweet(int id) {
        Optional<Tweet> existingTweetOptional = tweetRepository.findById(id);
        if (existingTweetOptional.isEmpty()) {
            throw new TweetException("Tweet not found with id: " + id);
        }
        Tweet tweet = existingTweetOptional.get();
        tweetRepository.delete(tweet);
    }
}
