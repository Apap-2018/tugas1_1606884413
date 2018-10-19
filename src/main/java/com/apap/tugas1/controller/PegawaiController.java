package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.InstansiModel;
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
		List<InstansiModel> listInstansi = instansiService.getInstansiList();
			
		model.addAttribute("listJabatan", archive);
		model.addAttribute("listInstansi", listInstansi);
		return "index";
	}
	
	@RequestMapping("/pegawai/tambah")
	private String addPegawai(Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		pegawai.setJabatanList(new ArrayList<JabatanModel>());
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listProvinsi", provinsiService.getProvinsiList());
		model.addAttribute("listInstansi", instansiService.getInstansiList());
		model.addAttribute("listJabatan", jabatanService.getListJabatan());
		return "add-pegawai";
	}
	
	@RequestMapping("/pegawai/cari")
	private String cariPegawai(@RequestParam(value="idProvinsi",required=false) String idProvinsi,@RequestParam(value="idInstansi",required=false) String idInstansi,@RequestParam(value="idJabatan",required=false) String idJabatan,Model model) {
		model.addAttribute("listProvinsi", provinsiService.getProvinsiList());
		model.addAttribute("listInstansi", instansiService.getInstansiList());
		model.addAttribute("listJabatan", jabatanService.getListJabatan());
		List<PegawaiModel> pegawai = pegawaiService.getPegawaiList();
		
		if ((idProvinsi==null || idProvinsi.equals("")) && (idInstansi==null||idInstansi.equals("")) && (idJabatan==null||idJabatan.equals(""))) {
		}
		else {
			if (idProvinsi!=null && !idProvinsi.equals("")) {
				List<PegawaiModel> temp = new ArrayList<PegawaiModel>();
				for (PegawaiModel peg: pegawai) {
					if (((Long)peg.getInstansi().getProvinsi().getId()).toString().equals(idProvinsi)) {
						temp.add(peg);
					}
				}
				pegawai = temp;
				model.addAttribute("idProvinsi", Long.parseLong(idProvinsi));
			}
			else {
				model.addAttribute("idProvinsi", "");
			}
			if (idInstansi!=null&&!idInstansi.equals("")) {
				List<PegawaiModel> temp = new ArrayList<PegawaiModel>();
				for (PegawaiModel peg: pegawai) {
					if (((Long)peg.getInstansi().getId()).toString().equals(idInstansi)) {
						temp.add(peg);
					}
				}
				pegawai = temp;
				model.addAttribute("idInstansi", Long.parseLong(idInstansi));
			}
			else {
				model.addAttribute("idInstansi", "");
			}
			if (idJabatan!=null&&!idJabatan.equals("")) {
				List<PegawaiModel> temp = new ArrayList<PegawaiModel>();
				for (PegawaiModel peg: pegawai) {
					for (JabatanModel jabatan:peg.getJabatanList()) {
						if (((Long)jabatan.getId()).toString().equals(idJabatan)) {
							temp.add(peg);
							break;
						}
					}
					
				}
				pegawai = temp;
				model.addAttribute("idJabatan", Long.parseLong(idJabatan));
			}
			else {
				model.addAttribute("idJabatan", "");
			}
		}
		model.addAttribute("listPegawai",pegawai);
		return "cari-pegawai";
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