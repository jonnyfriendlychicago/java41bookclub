package com.jonfriend.java41bookclub.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jonfriend.java41bookclub.models.PublicationMdl;

@Repository
public interface PublicationRpo extends CrudRepository<PublicationMdl, Long> {
	List<PublicationMdl> findAll(); 
}
