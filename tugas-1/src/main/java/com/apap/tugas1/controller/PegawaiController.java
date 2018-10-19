package com.apap.tugas1.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;

@Controller
public class PegawaiController {
	
	@Autowired
	private PegawaiService pegawaiService;
	@Autowired
	private JabatanService jabatanService;
	@Autowired
	private ProvinsiService provinsiService;
	@Autowired
	private InstansiService instansiService;
	
	
	@RequestMapping("/")
	private String home(Model model) {
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		model.addAttribute("listJabatan", listJabatan);
		List<InstansiModel> listInstansi = instansiService.getAllInstansi();
		model.addAttribute("listInstansi", listInstansi);
		return "home";
	}
	
	@RequestMapping(value = "/pegawai", method = RequestMethod.GET)
	private String showDataPegawai(@RequestParam("nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
		
		double gajiPokokPegawai = pegawai.getListJabatan().get(0).getGajiPokok();
		if(pegawai.getListJabatan().size() > 1) {
			for(int i=1 ; i< pegawai.getListJabatan().size(); i++) {
				if(pegawai.getListJabatan().get(i).getGajiPokok() > gajiPokokPegawai) {
					gajiPokokPegawai = pegawai.getListJabatan().get(i).getGajiPokok();
				}
			}
		}
		
		double tunjangan = pegawai.getInstansi().getProvinsi().getPersentaseTunjangan() /100;
		gajiPokokPegawai = gajiPokokPegawai + (tunjangan * gajiPokokPegawai);
		String gaji = String.format ("%.0f", gajiPokokPegawai);
		
		model.addAttribute("gaji", gaji);
		model.addAttribute("pegawai", pegawai);
		return "show";
		
	}
	
	@RequestMapping("/pegawai/termuda-tertua")
	public String viewPegawai (@RequestParam long idInstansi, Model model) {
		List<PegawaiModel> listPegawai = pegawaiService.getTuaMudaInstansi(instansiService.getInstansiDetailById(idInstansi));
		model.addAttribute("pegawaiTertua", listPegawai.get(0));
		model.addAttribute("pegawaiTermuda", listPegawai.get(listPegawai.size()-1));
		return "tua-muda";
	}
	
/*	(1)kalo provinsi gak ada otomatis instansi gaada
	(2)kalo ketiga komponen gak ada yaudah gausah dibuat
	(3)kalo provinsi gak ada tp instansi sama jabatan ada ga guna balik ke (1)
	(4)kalo provinsi gak ada tp instansi ada sama jabatan gak ada balik ke (1)*/
/*	hashSet ada nilai double diitung satu*/
	@RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET)
	public String searchPegawai(@RequestParam(value = "idProvinsi") Optional<Long> idProvinsi,
								@RequestParam (value = "idInstansi")  Optional<Long> idInstansi,
								@RequestParam (value = "idJabatan")  Optional<Long> idJabatan, Model model) {
		
		ProvinsiModel provinsi = null;
		InstansiModel instansi = null;
		JabatanModel jabatan = null;
		
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		model.addAttribute("listJabatan", new HashSet(listJabatan));
		
		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		model.addAttribute("listProvinsi", listProvinsi);
		
		List<InstansiModel> listInstansi = instansiService.getAllInstansi();
		model.addAttribute("listInstansi", listInstansi);
		
		
		List<PegawaiModel> hasilPencarian = null;
		if (idProvinsi.isPresent()) {
			provinsi = provinsiService.getProvinsiDetailById(idProvinsi.get());
			if (idInstansi.isPresent()) {
				instansi = instansiService.getInstansiById(idInstansi.get()).get();	
				if (idJabatan.isPresent()) {
					jabatan = jabatanService.getJabatanDetailById(idJabatan.get());	
					hasilPencarian = pegawaiService.getPegawaiByInstansiAndJabatan(instansi, jabatan);
				}
				else {
					hasilPencarian = pegawaiService.getPegawaiByInstansi(instansi);
				}
			}
			else if (idJabatan.isPresent()) {
				jabatan = jabatanService.getJabatanById(idJabatan.get()).get();	
				hasilPencarian = pegawaiService.getPegawaiByProvinsiAndJabatan(idProvinsi.get(), jabatan);
			}
			else {
				hasilPencarian = pegawaiService.getPegawaiByProvinsi(idProvinsi.get());
			}
		}
		else {
			if (idJabatan.isPresent()) {
				jabatan = jabatanService.getJabatanById(idJabatan.get()).get();	
				hasilPencarian = pegawaiService.getPegawaiByJabatan(jabatan);
			}
		}
		model.addAttribute("provinsi", provinsi);
		model.addAttribute("instansi", instansi);
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("hasilPencarian", hasilPencarian);
		return "searchPegawai";
	}
	
	

	
	

	

}
