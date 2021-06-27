package com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.service;

import java.util.ArrayList;
import java.util.List;

import com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.dao.LanguageTransitionRepository;
import com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.dto.TehsilDto;
import com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.model.LanguageTransition;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MasterService {

	@Autowired
	LanguageTransitionRepository languageTransition;

	public List<TehsilDto> getTehsil(String lang) {
		// TODO Auto-generated method stub
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		List<LanguageTransition> list = this.languageTransition.getTehsil(lang);
		List<TehsilDto> tehsil = new ArrayList<>();

		for (LanguageTransition l : list) {
			TehsilDto dto = new TehsilDto();
			dto.setTehsil_id(l.getTranslated_object_id());
			dto.setTehsil_name(l.getTranslated_value());
			tehsil.add(dto);

		}

		return tehsil;
	}

	public List<TehsilDto> getTehsilSearch(String lang, String keyword) {
		List<LanguageTransition> list = this.languageTransition.getTehsilByKeyword(lang, keyword);
		List<TehsilDto> tehsil = new ArrayList<>();

		for (LanguageTransition l : list) {
			TehsilDto dto = new TehsilDto();
			dto.setTehsil_id(l.getTranslated_object_id());
			dto.setTehsil_name(l.getTranslated_value());
			tehsil.add(dto);

		}

		return tehsil;
	}
}
