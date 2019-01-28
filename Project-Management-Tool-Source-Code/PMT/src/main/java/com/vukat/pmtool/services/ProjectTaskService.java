package com.vukat.pmtool.services;

import com.vukat.pmtool.domain.Backlog;
import com.vukat.pmtool.domain.ProjectTask;
import com.vukat.pmtool.exception.ProjectNotFoundException;
import com.vukat.pmtool.repositories.BacklogRepository;
import com.vukat.pmtool.repositories.ProjectRepository;
import com.vukat.pmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProjectTaskService {


    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    public ProjectTask addProjectTask(String projectIdentifer,ProjectTask projectTask,String username){

        //Care for project not found!

            //BL should exists
            Backlog backlog = projectService.findProjectByIdentifier(projectIdentifer,username).getBacklog();

            // Set to Bl to Pt
            projectTask.setBacklog(backlog);

            // sequence should be IDPRO-1,IDPRO-2 ... 100,101
            Integer BacklogSequence = backlog.getPTSequence();

            //Update sequence
            BacklogSequence++;

            backlog.setPTSequence(BacklogSequence);

            //Add sequence to project tasks
            projectTask.setProjectSequence(projectIdentifer+"-"+BacklogSequence);

            projectTask.setProjectIdentifer(projectIdentifer);

            if(projectTask.getStatus() == "" || projectTask.getStatus() == null){
                projectTask.setStatus("TO_DO");
            }

            if(projectTask.getPriority() == null ||  projectTask.getPriority() == 0){
                projectTask.setPriority(3);
            }

            return projectTaskRepository.save(projectTask);









    }

    public Iterable<ProjectTask> findBacklogById(String backlog_id,String username) {

        projectService.findProjectByIdentifier(backlog_id,username);

        return this.projectTaskRepository.findByProjectIdentiferOrderByPriority(backlog_id);
    }

    public ProjectTask findProjectTaskByProjectSequence(String backlog_id,String pt_id,String username) {

        projectService.findProjectByIdentifier(backlog_id,username);

        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);

        if(projectTask == null){
            throw new ProjectNotFoundException("Project task not found");
        }

        if(!projectTask.getProjectIdentifer().equals(backlog_id)){
            throw new ProjectNotFoundException("Project task doesn't exist in this project");
        }

        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedProjectTask, String backlog_id,String pt_id,String username){

        ProjectTask projectTask = findProjectTaskByProjectSequence(backlog_id,pt_id,username);

        projectTask = updatedProjectTask;

        return projectTaskRepository.save(projectTask);

    }

    public void deleteProjectTask(String backlog_id,String pt_id,String username){

        ProjectTask projectTask = findProjectTaskByProjectSequence(backlog_id,pt_id,username);

        projectTaskRepository.delete(projectTask);

    }




}
