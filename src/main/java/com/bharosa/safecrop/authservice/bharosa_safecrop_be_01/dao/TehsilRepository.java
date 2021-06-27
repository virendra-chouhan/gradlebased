package com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.dao;

import com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.model.Tehsil;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TehsilRepository extends JpaRepository<Tehsil, Integer> {

}
