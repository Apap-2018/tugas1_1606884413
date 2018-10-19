package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.service.JabatanPegawaiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;

@Controller
public class JabatanController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@Autowired
	private JabatanPegawaiService jabatanPegawaiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@RequestMapping(value="/jabatan/tambah",method=RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("jabatan",new JabatanModel());
		return "addJabatan";
	}
	
	@RequestMapping(value="/jabatan/tambah",method=RequestMethod.POST)
	private String addPilotSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.addJabatan(jabatan);
		model.addAttribute("pesan", "Jabatan "+jabatan.getNama()+" berhasil ditambah");
		return "add";
	}
	
	@RequestMapping("/jabatan/view")
	private String viewJabatan(@RequestParam(value="idJabatan", required = true) Long id, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanById(id);
		if (jabatan==null) {
			return "haha";
		}
		else {
			model.addAttribute("jabatan", jabatan);
		}
		return "view-jabatan";
	}
	
	@RequestMapping("/jabatan/ubah")
	private String ubahJabatan(@RequestParam(value="idJabatan", required = true) Long id, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanById(id);
		if (jabatan==null) {
			return "haha";
		}
		else {
			model.addAttribute("jabatan", jabatan);
		}
		return "edit-jabatan";
	}
	
	@RequestMapping(value="/jabatan/ubah",method=RequestMethod.POST)
	private String ubahJabatanSubmit(@ModelAttribute JabatanModel jabatan,Model model) {
		jabatanService.addJabatan(jabatan);
		model.addAttribute("pesan", "Jabatan Berhasil Diubah");
		return "add";
	}
	
	@RequestMapping(value="/jabatan/hapus",method=RequestMethod.POST)
	private String hapusJabatan(@RequestParam(value="idJabatan", required = true) Long id, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanById(id);
		if (jabatan==null) {
			return "haha";
		}
		else {
			if (jabatan.getPegawaiList().size()==0) {
				jabatanService.deleteJabatan(jabatan);
				model.addAttribute("pesan", "Jabatan Berhasil Dihapus");
				return "add";
			}
			else {
				return "haha";
			}
		}
		
	}
	
	@RequestMapping("/jabatan/viewall")
	private String viewAllJabatan(Model model) {
		List<JabatanModel> jabatan = jabatanService.getListJabatan();
		model.addAttribute("jabatan",jabatan);
		return "viewall-jabatan";
	}

 

}