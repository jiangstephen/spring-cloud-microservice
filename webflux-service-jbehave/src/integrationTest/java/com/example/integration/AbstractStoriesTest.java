package com.example.integration;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;

public abstract class AbstractStoriesTest extends JUnitStories {
	 	
	@Override
    public Configuration configuration() {
        return new MostUsefulConfiguration()
          .useStoryLoader(new LoadFromClasspath(this.getClass()))
          .useStoryReporterBuilder(new StoryReporterBuilder()
            .withCodeLocation(CodeLocations.codeLocationFromClass(this.getClass()))
            .withDefaultFormats()
            .withFormats(Format.HTML, Format.TXT, Format.IDE_CONSOLE));
    } 
}