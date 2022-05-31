package com.finance.dbclmrestapp.repository;

import com.finance.dbclmrestapp.model.Nace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DbClmRepo extends JpaRepository<Nace, String> {
}
