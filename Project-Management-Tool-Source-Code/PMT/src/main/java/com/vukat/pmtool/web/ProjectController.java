package com.vukat.pmtool.web;


import com.vukat.pmtool.domain.Project;
import com.vukat.pmtool.services.ProjectService;
import com.vukat.pmtool.services.ValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;


@RestController
@RequestMapping("/api/project")
@CrossOrigin()
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ValidationErrorService validationErrorService;

    //BindingResult(interface) checks object valid or not
    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result, Principal principal) {


        ResponseEntity<?> errorMap = validationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap;

        Project project1 = projectService.saveOrUpdateProject(project,principal.getName());
        return new ResponseEntity<Project>(project1, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(Principal principal,@PathVariable String projectId) {


        Project project = projectService.findProjectByIdentifier(projectId,principal.getName());

        return new ResponseEntity<Project>(project,HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects(Principal principal) {
        return projectService.findAllProjects(principal.getName());
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId,Principal principal) {

        projectService.deleteProjectByIdentifier(projectId.toUpperCase(),principal.getName());
        return new ResponseEntity<String>("Project with ID '" + projectId + "' was deleted",HttpStatus.OK);

    }

}
