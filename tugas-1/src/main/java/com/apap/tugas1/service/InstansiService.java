package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.InstansiModel;

public interface InstansiService {
	InstansiModel getInstansiDetailById (long id);
	List<InstansiModel> getAllInstansi();
	Optional<InstansiModel> getInstansiById(Long id);
}
