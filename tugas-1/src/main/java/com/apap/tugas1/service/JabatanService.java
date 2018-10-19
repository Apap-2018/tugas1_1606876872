package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.JabatanModel;



public interface JabatanService {
	JabatanModel getJabatanDetailById (long id);
	void addJabatan(JabatanModel jabatan);
	void updateJabatan(long id, JabatanModel jabatan);
	List<JabatanModel> getAllJabatan();
	void deleteJabatan (long id);
	Optional<JabatanModel> getJabatanById(Long id);
}
