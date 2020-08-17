package com.shorty.app.administration;

import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Integer> {
	Account findByUsername(String username);
}
