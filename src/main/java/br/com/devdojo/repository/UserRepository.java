package br.com.devdojo.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.devdojo.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long>{

	User findByUsername(String username);
	
}
