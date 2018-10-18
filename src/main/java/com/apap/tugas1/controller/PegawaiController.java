package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
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
	private ProvinsiService provinsiService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@RequestMapping("/")
	private String index(Model model) {
		List<JabatanModel> archive = jabatanService.getListJabatan();
			
		model.addAttribute("listJabatan", archive);
		return "index";
	}
	
	@RequestMapping("/pegawai")
	private String viewPegawai(@RequestParam(value="nip", required = true) String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
		if (pegawai==null) {
			return "haha";
		}
		else {
			model.addAttribute("pegawai", pegawai);
			double gaji = 0;
			double gajiTerbesar = 0;
	        for (JabatanModel jabatan:pegawai.getJabatanList()) {
	            if (jabatan.getGajiPokok() > gajiTerbesar) {
	                gajiTerbesar = jabatan.getGajiPokok();
	            }
	        }
	        gaji = gajiTerbesar;
	        double tunjangan = pegawai.getInstansi().getProvinsi().getPresentaseTunjangan();
	        gaji += (gaji * tunjangan/100);
	        Integer gajiInt = (int) gaji;
	        model.addAttribute("gajiPegawai",gajiInt);
		}
		return "view-pegawai";
	}
	
}