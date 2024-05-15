package skajihara.projectX;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import skajihara.projectX.MainContents.Home.controller.TweetController;
import skajihara.projectX.MainContents.Home.repository.TweetRepository;
import skajihara.projectX.MainContents.Home.service.TweetService;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@SpringBootTest
class ProjectXApplicationTest {

	@Autowired
	private TweetController tweetController;

	@Autowired
	private TweetService tweetService;

	@Autowired
	private TweetRepository tweetRepository;

	// アプリケーションがSpringコンテキストを正常にロードできることを検証する
	@Test
	void contextLoads() {
		assertThat(tweetController).isNotNull();
		assertThat(tweetService).isNotNull();
		assertThat(tweetRepository).isNotNull();
	}
}
