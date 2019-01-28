package com.vukat.pmtool.repositories;

import com.vukat.pmtool.domain.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project,Long> {


   Project findByProjectIdentifer(String projectIdentifier);

   @Override
   Iterable<Project> findAll();

   Iterable<Project> findAllByProjectLeader(String username);


}
