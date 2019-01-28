package com.vukat.pmtool.repositories;

import com.vukat.pmtool.domain.Backlog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BacklogRepository extends CrudRepository<Backlog, Long> {

    Backlog findByprojectIdentifer(String projectIdentifer);
}
