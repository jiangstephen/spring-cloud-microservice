package com.example.integration;

import org.jbehave.core.embedder.Embedder;

public class IncreaseStoryMultiThreadTest extends IncreaseStoriesTest {
	 
	public IncreaseStoryMultiThreadTest() {
		Embedder embedder = configuredEmbedder();
        embedder.embedderControls().doGenerateViewAfterStories(true).doIgnoreFailureInStories(true)
                .doIgnoreFailureInView(true).doVerboseFiltering(true).useThreads(2).doFailOnStoryTimeout(false);
	}
	
}