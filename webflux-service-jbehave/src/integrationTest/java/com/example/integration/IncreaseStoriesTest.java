package com.example.integration;

import java.util.Arrays;
import java.util.List;

import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;

public class IncreaseStoriesTest extends AbstractStoriesTest {
	 
	public IncreaseStoriesTest() {
		Embedder embedder = configuredEmbedder();
        embedder.embedderControls().doGenerateViewAfterStories(true).doIgnoreFailureInStories(true)
                .doIgnoreFailureInView(true).doVerboseFiltering(true).useThreads(2).doFailOnStoryTimeout(false);
        embedder.useMetaFilters(Arrays.asList("+stephen"));
	}
	
    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new IncreaseSteps());
    }
 
    @Override
    protected List<String> storyPaths() {
        return Arrays.asList("increase.story"
        		, "increase2.story"
        		);
    }
 
}