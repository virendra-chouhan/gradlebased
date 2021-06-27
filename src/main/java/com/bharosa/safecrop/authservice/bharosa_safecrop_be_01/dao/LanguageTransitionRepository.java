package com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.model.LanguageTransition;

@Repository
public interface LanguageTransitionRepository extends JpaRepository<LanguageTransition, String> {

	@Query("SELECT p FROM LanguageTransition p ,Tehsil q WHERE  p.translated_object_id=q.tehsil_id and p.language_code=:lang group by p.translated_object_id")

	List<LanguageTransition> getTehsil(@Param("lang") String lang);

	@Query("SELECT p FROM LanguageTransition p WHERE  p.translated_value like :keyword% and p.language_code=:lang group by p.translated_object_id")
	List<LanguageTransition> getTehsilByKeyword(@Param("lang") String lang, @Param("keyword") String keyword);
}
