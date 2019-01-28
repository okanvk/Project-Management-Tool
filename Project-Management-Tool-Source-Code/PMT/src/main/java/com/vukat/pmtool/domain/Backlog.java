package com.vukat.pmtool.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Backlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer PTSequence = 0;
    private String projectIdentifer;




    //OneToOne with Project (Each project has a specific backlog)

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id",nullable = false)
    @JsonIgnore
    private Project project;

    public List<ProjectTask> getProjectTasks() {
        return projectTasks;
    }

    public void setProjectTasks(List<ProjectTask> projectTasks) {
        this.projectTasks = projectTasks;
    }
//OneToMany with Tasks

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "backlog", orphanRemoval = true)// Task silinice sadece refresh atıyor kalanlar etkilenmiyor.
    private List<ProjectTask> projectTasks = new ArrayList<>();


    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public  Backlog(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPTSequence() {
        return PTSequence;
    }

    public void setPTSequence(Integer PTSequence) {
        this.PTSequence = PTSequence;
    }

    public String getProjectIdentifer() {
        return projectIdentifer;
    }

    public void setProjectIdentifer(String projectIdentifer) {
        this.projectIdentifer = projectIdentifer;
    }
}
