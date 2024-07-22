package com.br.foliveira.recrutamento_interno.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "t_user")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    
    @ManyToMany(fetch = FetchType.LAZY,
        cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
        })
    @JoinTable(name = "t_user_job",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "job_id") })
    private Set<Job> jobs = new HashSet<>();

    public User(String name, String email, Set<Job> jobs){
        this.name = name;
        this.email = email;
        this.jobs = jobs;
    }

    public void addJob(Job job){
        this.jobs.add(job);
        job.getUsers().add(this);
    }

    public void removeJob(long jobId) {
        Job job = this.jobs.stream().filter(t -> t.getId() == jobId).findFirst().orElse(null);
        if (job != null) {
          this.jobs.remove(job);
          job.getUsers().remove(this);
        }
    }

}
