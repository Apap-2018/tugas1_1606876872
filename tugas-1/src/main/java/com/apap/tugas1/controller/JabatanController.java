package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;


@Controller
public class JabatanController {
	
	@Autowired
	private PegawaiService pegawaiService;
	@Autowired
	private JabatanService jabatanService;
	@Autowired
	private ProvinsiService provinsiService;
	@Autowired
	private InstansiService instansiService;
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("jabatan", new JabatanModel());
		return "addJabatan";
	}
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	private String addJabatan (@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.addJabatan(jabatan);
		
		model.addAttribute("jabatan", jabatan);
		return "add";
	}
	
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.GET)
	private String update(@RequestParam("id") long id, Model model) {
		JabatanModel oldJabatan = jabatanService.getJabatanDetailById(id);
		model.addAttribute("jabatan", oldJabatan);
		return "update-Jabatan";
	}
	
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.POST)
	private String updateJabatan(Model model, @ModelAttribute JabatanModel jabatan) {
		jabatanService.updateJabatan(jabatan.getId(), jabatan);
		return "update";
	}
	
	@RequestMapping(value = "/jabatan/view", method = RequestMethod.GET)
	private String detailJabatan (@RequestParam("id") long id, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanDetailById(id);
		List<PegawaiModel> pegawai = pegawaiService.getPegawaiByJabatan(jabatan);
		int jlhPegawai = pegawai.size();
		
		model.addAttribute("jlhPegawai", jlhPegawai);
		model.addAttribute("jabatan", jabatan);
		return "viewJabatan";
	}
	
	
	@RequestMapping(value = "/jabatan/hapus", method = RequestMethod.POST) /*cuma bisa hapus pegawai yg ada di jabatan tapi tidak ada di pegawai , hint : cek jabatanPegawai*/
	private String hapusJabatan(@RequestParam(value ="id", required = true) long id, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanDetailById(id);
		try{
			jabatanService.deleteJabatan(id);
			return "deleteJabatan";
		}catch (Exception e) {
			return "failedDeleteJabatan";
		}
	}
	
	@RequestMapping(value = "/jabatan/viewall", method = RequestMethod.GET)
	private String viewallJabatan(Model model) {
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		
		model.addAttribute("listJabatan", listJabatan);
		return "viewall";
	}


}
