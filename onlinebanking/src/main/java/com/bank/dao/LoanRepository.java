package com.bank.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bank.entities.Loan;

public interface LoanRepository extends JpaRepository<Loan,Integer> {
	@Query("from Loan as c where c.user.id=:userId ")
	public List<Loan> findLoansByUser(@Param("userId")int userId);

}
