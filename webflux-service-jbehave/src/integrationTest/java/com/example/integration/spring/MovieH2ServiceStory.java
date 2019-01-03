package com.example.integration.spring;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AcceptanceTest
public class MovieH2ServiceStory extends AbstractSpringJBehaveStory {

}
