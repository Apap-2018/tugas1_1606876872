package com.apap.tugas1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.PegawaiModel;
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
	private String home() {
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

}
