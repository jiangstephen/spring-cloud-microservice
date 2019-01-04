package com.example.integration.spring;

import java.util.Arrays;
import java.util.List;

import org.jbehave.core.annotations.UsingSteps;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.EmbedderControls;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.spring.SpringStepsFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.integration.AbstractStoriesTest;


@RunWith(SpringRunner.class)
@SpringBootTest
@AcceptanceTest
@UsingSteps
public class MovieServiceStoriesTest extends AbstractStoriesTest {
 
	@Autowired
	private ApplicationContext applicationContext;

	@SuppressWarnings("deprecation")
	private EmbedderControls embedderControls() {
		return new EmbedderControls()
				.doIgnoreFailureInView(true)
				.useThreads(1).useStoryTimeoutInSecs(10);
	}
	
	public MovieServiceStoriesTest() {
		Embedder embedder = new Embedder();
		embedder.useEmbedderControls(embedderControls());
		embedder.useMetaFilters(Arrays.asList("-skip"));
		useEmbedder(embedder);
	}
	
	@Override
	public InjectableStepsFactory stepsFactory(){
		return new SpringStepsFactory(configuration(), applicationContext);
	}
 
    @Override
    protected List<String> storyPaths() {
        return Arrays.asList("increase.story"
        		, "increase2.story"
        		, "com/example/integration/spring/movie_service_story.story"
        		, "com/example/integration/spring/movie_h2_service_story.story"
        		);
    }
 
}