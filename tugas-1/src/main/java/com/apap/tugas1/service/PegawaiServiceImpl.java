package com.apap.tugas1.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDb;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService{
	@Autowired
	private PegawaiDb pegawaiDb;

	@Override
	public PegawaiModel getPegawaiDetailById(long id) {
		// TODO Auto-generated method stub
		return pegawaiDb.findById(id);
	}

	@Override
	public void addPegawai(PegawaiModel pegawai) {
		// TODO Auto-generated method stub
		pegawaiDb.save(pegawai);
	}

	@Override
	public PegawaiModel getPegawaiDetailByNip(String nip) {
		// TODO Auto-generated method stub
		return pegawaiDb.findByNip(nip);
	}

	@Override
	public List<PegawaiModel> getTuaMudaInstansi(InstansiModel instansi) {
		// TODO Auto-generated method stub
		return pegawaiDb.findByInstansiOrderByTanggalLahirAsc(instansi);
	}

	@Override
	public List<PegawaiModel> getPegawaiByInstansiAndJabatan(InstansiModel instansi, JabatanModel jabatan) {
		// TODO Auto-generated method stub
		List<PegawaiModel> hasil = new ArrayList<>();
		
		for(PegawaiModel pegawai : pegawaiDb.findByInstansi(instansi)) {
			if (pegawai.getListJabatan().contains(jabatan)) {
				hasil.add(pegawai);
			}
		}
		
		return hasil;
	}

	@Override
	public List<PegawaiModel> getPegawaiByInstansi(InstansiModel instansi) {
		// TODO Auto-generated method stub
		return pegawaiDb.findByInstansi(instansi);
	}

	@Override
	public List<PegawaiModel> getPegawaiByProvinsiAndJabatan(Long provinsiId, JabatanModel jabatan) {
		// TODO Auto-generated method stub
		List<PegawaiModel> hasil = new ArrayList<>();
		
		for(PegawaiModel pegawai : this.getPegawaiByProvinsi(provinsiId)) {
			if (pegawai.getListJabatan().contains(jabatan)) {
				hasil.add(pegawai);
			}
		}
		
		return hasil;
	}

	@Override
	public List<PegawaiModel> getPegawaiByProvinsi(Long provinsiId) {
		// TODO Auto-generated method stub
		List<PegawaiModel> hasil = new ArrayList<>();
		
		for(PegawaiModel pegawai : pegawaiDb.findAll()) {
			if (pegawai.getInstansi().getProvinsi().getId() == provinsiId) {
				hasil.add(pegawai);
			}
		}
		
		return hasil;
	}

	@Override
	public List<PegawaiModel> getPegawaiByJabatan(JabatanModel jabatan) {
		// TODO Auto-generated method stub
		return pegawaiDb.findByListJabatan(jabatan);
	}

	@Override
	public List<PegawaiModel> getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi,
			Date tanggalLahir, String tahunMasuk) {
		// TODO Auto-generated method stub
		return pegawaiDb.findByInstansiAndTanggalLahirAndTahunMasuk(instansi, tanggalLahir, tahunMasuk);
	}

	@Override
	public void updatePegawai(String nip, PegawaiModel pegawai) {
		// TODO Auto-generated method stub
			PegawaiModel updatePegawai = pegawaiDb.findByNip(nip);
			updatePegawai.setNama(pegawai.getNama());
			updatePegawai.setNip(pegawai.getNip());
			updatePegawai.setTanggalLahir(pegawai.getTanggalLahir());
			updatePegawai.setTempatLahir(pegawai.getTempatLahir());
			updatePegawai.setTahunMasuk(pegawai.getTahunMasuk());
			updatePegawai.setInstansi(pegawai.getInstansi());
			updatePegawai.setListJabatan(pegawai.getListJabatan());
			pegawaiDb.save(updatePegawai);
		
	}

	@Override
	public void setNipPegawai(PegawaiModel pegawai) {
		// TODO Auto-generated method stub

		String nipTglLahir = "";
		
		Date tglLahir = pegawai.getTanggalLahir();
		String[] tanggalLahir = (String.valueOf(tglLahir).split("-"));
		for (int i = 0; i < tanggalLahir.length; i++) {
			nipTglLahir = tanggalLahir[i].substring(tanggalLahir[i].length()-2, tanggalLahir[i].length()) + nipTglLahir;
		}
		
		List<PegawaiModel> listPegawai = pegawaiDb.findByInstansiAndTanggalLahirAndTahunMasukOrderByNipAsc(pegawai.getInstansi(), pegawai.getTanggalLahir(), pegawai.getTahunMasuk());
		int nomorPegawaiTemp = 0;
		if (listPegawai.isEmpty()) {
			nomorPegawaiTemp = 1;
		} else {
			PegawaiModel lastPegawai = listPegawai.get(listPegawai.size()-1);
			nomorPegawaiTemp = Integer.valueOf(lastPegawai.getNip().substring(lastPegawai.getNip().length()-2)) + 1;
		}
		String nomorPegawai = (nomorPegawaiTemp < 10 ? "0" : "") + nomorPegawaiTemp;
		
		String nip = pegawai.getInstansi().getId() + nipTglLahir + pegawai.getTahunMasuk() + nomorPegawai;
		
		pegawai.setNip(nip);
	}
	
	
	
	
	
	
	
	
	
}
