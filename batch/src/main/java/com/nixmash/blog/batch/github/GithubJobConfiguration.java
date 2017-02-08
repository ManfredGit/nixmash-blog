package com.nixmash.blog.batch.github;

import com.nixmash.blog.jpa.dto.GitHubDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * Created by daveburke on 11/30/16.
 */
@SuppressWarnings({"Convert2Lambda", "Anonymous2MethodRef"})
@Configuration
public class GithubJobConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(GithubJobConfiguration.class);

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final GithubJobListener githubJobListener;
    private final GithubJobUI githubJobUI;

    public GithubJobConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, GithubJobListener githubJobListener, GithubJobUI githubJobUI) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.githubJobListener = githubJobListener;
        this.githubJobUI = githubJobUI;
    }


    @Bean(name = "githubJob")
    public Job githubJob() throws Exception {
        return jobBuilderFactory.get("githubJob")
                .incrementer(new RunIdIncrementer())
                .listener(githubJobListener)
                .flow(githubStep1())
                .end()
                .build();
    }

    @Bean
    public Step githubStep1() throws Exception {
        return stepBuilderFactory.get("githubStep1")
                .tasklet(new Tasklet() {
                    public RepeatStatus execute(StepContribution contribution,
                                                ChunkContext chunkContext) {

//                        GitHubDTO gitHubDTO = githubJobUI.getDummyStats();

                        GitHubDTO gitHubDTO = githubJobUI.getGitHubStats();
                        long currentStatId = githubJobUI.getCurrentGithubId();
                        gitHubDTO.setStatId(currentStatId);
                        gitHubDTO.setStatDate(new Date());

                        githubJobUI.saveGithubStats(gitHubDTO);

                        chunkContext
                                .getStepContext()
                                .getStepExecution()
                                .getJobExecution()
                                .getExecutionContext()
                                .put("statId", currentStatId);

                        logger.info("Working with GitHubDTO: " + gitHubDTO.toString());
                        return RepeatStatus.FINISHED;
                    }
                })
                .listener(githubPromotionListener())
                .build();
    }

    @Bean
    public ExecutionContextPromotionListener githubPromotionListener()
    {
        ExecutionContextPromotionListener listener =
                new ExecutionContextPromotionListener();
        listener.setKeys( new String[] { "statId" } );
        return listener;
    }
}
