package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;

@Controller
public class InstansiController {

	@Autowired
	private PegawaiService pegawaiService;
	@Autowired
	private JabatanService jabatanService;
	@Autowired
	private ProvinsiService provinsiService;
	@Autowired
	private InstansiService instansiService;
	
	
	@RequestMapping(value = "/instansi/getInstansiByProvinsi", method = RequestMethod.GET)
	@ResponseBody
	public List<InstansiModel> getInstansi(@RequestParam (value = "idProvinsi", required = true) long idProvinsi) {
		ProvinsiModel provinsi = provinsiService.getProvinsiDetailById(idProvinsi);
		System.out.println("hahahahaha");
	    return instansiService.getInstansiFromProvinsi(provinsi);
	}
	
}
