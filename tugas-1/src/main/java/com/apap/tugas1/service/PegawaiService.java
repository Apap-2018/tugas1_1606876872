package com.apap.tugas1.service;

import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {
	PegawaiModel getPegawaiDetailById(long id);
	PegawaiModel getPegawaiDetailByNip (String nip);
	void addPegawai(PegawaiModel pegawai);
}
