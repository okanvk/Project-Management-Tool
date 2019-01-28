package com.vukat.pmtool.services;

import com.vukat.pmtool.domain.Backlog;
import com.vukat.pmtool.domain.Project;
import com.vukat.pmtool.domain.User;
import com.vukat.pmtool.exception.ProjectIdException;
import com.vukat.pmtool.exception.ProjectNotFoundException;
import com.vukat.pmtool.repositories.BacklogRepository;
import com.vukat.pmtool.repositories.ProjectRepository;
import com.vukat.pmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {


    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private UserRepository userRepository;

    public Project saveOrUpdateProject(Project project,String username) {

        if(project.getId() != null){

            Project existingProject = projectRepository.findByProjectIdentifer(project.getProjectIdentifer());

            if(existingProject != null && (!existingProject.getProjectLeader().equals(username))){
                throw new ProjectNotFoundException("Project not found in your account");
            }else if(existingProject == null){
                throw new ProjectNotFoundException("Project cannot be updated because it doesn't exist");
            }

        }

        try{

           User user = userRepository.findByUsername(username);

           project.setUser(user);
           project.setProjectLeader(user.getUsername());

            project.setProjectIdentifer(project.getProjectIdentifer().toUpperCase());

            if(project.getId() == null) {

                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifer(project.getProjectIdentifer().toUpperCase());

            }else{
                project.setBacklog(backlogRepository.findByprojectIdentifer(project.getProjectIdentifer().toUpperCase()));
            }


            return projectRepository.save(project);
        }
        catch (Exception ex){
            throw new ProjectIdException("Project ID'"+project.getProjectIdentifer().toUpperCase()+"' already exists");
        }



    }


    public Project findProjectByIdentifier(String projectId,String username) {

        Project project = projectRepository.findByProjectIdentifer(projectId.toUpperCase());

        if(project == null) {
            throw new ProjectIdException("Project ID'" + projectId + "' does not exist");
        }

        // In strings
        //  check for same reference ==
        // check for value .equals
        if(!project.getProjectLeader().equals(username)){
            throw new ProjectNotFoundException("Project not found in your account");
        }

        return project;


    }

    public Iterable<Project> findAllProjects(String username){
        return projectRepository.findAllByProjectLeader(username);
    }


    public void deleteProjectByIdentifier(String projectId,String username) {

        Project project = findProjectByIdentifier(projectId,username);

        projectRepository.delete(project);


    }





}
