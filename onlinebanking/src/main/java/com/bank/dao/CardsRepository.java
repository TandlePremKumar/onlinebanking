package com.bank.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bank.entities.Cards;




public interface CardsRepository  extends JpaRepository <Cards,Integer>{
	
	@Query("from Cards as c where c.user.id=:userId ")
	public List<Cards> findCardsByUser(@Param("userId")int userId);
	

}
