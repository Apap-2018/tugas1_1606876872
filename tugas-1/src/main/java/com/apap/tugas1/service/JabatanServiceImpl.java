package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.JabatanDb;


@Service
@Transactional
public class JabatanServiceImpl implements JabatanService{
	@Autowired
	private JabatanDb jabatanDb;

	@Override
	public JabatanModel getJabatanDetailById(long id) {
		// TODO Auto-generated method stub
		return jabatanDb.findById(id);
	}

	@Override
	public void addJabatan(JabatanModel jabatan) {
		// TODO Auto-generated method stub
		jabatanDb.save(jabatan);
	}

	@Override
	public void updateJabatan(long id, JabatanModel newJabatan) {
		// TODO Auto-generated method stub
		JabatanModel oldJabatan = this.getJabatanDetailById(id);
		oldJabatan.setNama(newJabatan.getNama());
		oldJabatan.setDeskripsi(newJabatan.getDeskripsi());
		oldJabatan.setGajiPokok(newJabatan.getGajiPokok());
	}

	@Override
	public List<JabatanModel> getAllJabatan() {
		// TODO Auto-generated method stub
		return jabatanDb.findAll();
	}

	@Override
	public void deleteJabatan(long id) {
		// TODO Auto-generated method stub
		jabatanDb.deleteById(id);
	}

	@Override
	public Optional<JabatanModel> getJabatanById(Long id) {
		// TODO Auto-generated method stub
		return jabatanDb.findById(id);
	}


	
	
	
	
	
	
}
