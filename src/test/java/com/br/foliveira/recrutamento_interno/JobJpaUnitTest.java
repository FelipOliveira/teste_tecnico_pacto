package com.br.foliveira.recrutamento_interno;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.br.foliveira.recrutamento_interno.model.Job;
import com.br.foliveira.recrutamento_interno.repository.IJobRepository;

@DataJpaTest
public class JobJpaUnitTest {
    
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IJobRepository repository;

    @Test
    public void getAllJobs_returnsEmpty(){
        Iterable jobs = repository.findAll();
        assertThat(jobs).isEmpty();
    }

    @Test
    public void getAllJobs_returnsListWithThreeJobs(){
        List<Job> jobs = Stream.of(
            new Job("title 1","description 1"),
            new Job("title 2","description 2"),
            new Job("title 3","description 3")
        ).collect(Collectors.toList());

        jobs.forEach(entityManager::persist);
        Iterable<Job> jobs_data = repository.findAll();
        assertThat(jobs_data).hasSize(3).containsAll(jobs);
    }

    @Test
    public void getAllJobs_returnJobsWithTitleContainingString(){
        List<Job> jobs = Stream.of(
            new Job("Java","description 1"),
            new Job("Angular","description 2"),
            new Job("Javascript","description 3")
        ).collect(Collectors.toList());

        jobs.forEach(entityManager::persist);
        Iterable<Job> jobs_data = repository.findByTitleContaining("Java");
        assertThat(jobs_data).hasSize(2).contains(jobs.get(0), jobs.get(2));
    }

    @Test
    public void getJobById_returnJobDataById(){
        List<Job> jobs = Stream.of(
            new Job("title 1","description 1"),
            new Job("title 2","description 2"),
            new Job("title 3","description 3")
        ).collect(Collectors.toList());

        jobs.forEach(entityManager::persist);
        Job job_data = repository.findById(jobs.get(1).getId()).get();
        assertThat(job_data).isEqualTo(jobs.get(1));
    }

    @Test
    public void postJob_returnsSavedJobData(){
        Job job = new Job("Test", "Test description");

        assertThat(job).hasFieldOrPropertyWithValue("title", "Test");
        assertThat(job).hasFieldOrPropertyWithValue("description", "Test description");
    }

    @Test
    public void putJob_returnsUpdatedJobData(){
        List<Job> jobs = Stream.of(
            new Job("title 1","description 1"),
            new Job("title 2","description 2")
        ).collect(Collectors.toList());
        jobs.forEach(entityManager::persist);

        Job updatedJob = new Job("updated job", "updated description");
        Job job_data = repository.findById(jobs.get(1).getId()).get();
        job_data.setTitle(updatedJob.getTitle());
        job_data.setDescription(updatedJob.getDescription());
        repository.save(job_data);

        Job checkJob = repository.findById(jobs.get(1).getId()).get();
        assertThat(checkJob.getId()).isEqualTo(jobs.get(1).getId());
        assertThat(checkJob.getTitle()).isEqualTo(updatedJob.getTitle());
        assertThat(checkJob.getDescription()).isEqualTo(updatedJob.getDescription());
    }

    @Test
    public void deleteJobById_returnsListWithTwoJobs(){
        List<Job> jobs = Stream.of(
            new Job("title 1","description 1"),
            new Job("title 2","description 2"),
            new Job("title 3","description 3")
        ).collect(Collectors.toList());
        
        jobs.forEach(entityManager::persist);
        repository.deleteById(jobs.get(1).getId());

        Iterable<Job> job_data = repository.findAll();
        assertThat(job_data).hasSize(2).contains(jobs.get(0), jobs.get(2));
    }
}
