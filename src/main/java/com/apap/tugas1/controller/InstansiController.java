package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.InstansiService;

@Controller
public class InstansiController {
	@Autowired
	InstansiService instansiService;
	
	@RequestMapping("/pegawai/termuda-tertua")
	private String pegawaiTertuaTermuda(@RequestParam(value="idInstansi", required = true) Long id, Model model) {
		InstansiModel instansi = instansiService.getInstansiById(id);
		if (instansi==null) {
			return "haha";
		}
		else {
			List<PegawaiModel> pegawais = instansi.getPegawaiInstansi();
			if (pegawais.isEmpty()) {
				return "haha";
			}
			PegawaiModel tertua = pegawais.get(0);
			PegawaiModel termuda = pegawais.get(0);
			for (PegawaiModel pegawai : pegawais) {
				if (pegawai.getTanggalLahir().compareTo(tertua.getTanggalLahir())<0) {
					tertua = pegawai;
				}
				if (pegawai.getTanggalLahir().compareTo(termuda.getTanggalLahir())>0) {
					termuda = pegawai;
				}
			}
			model.addAttribute("tertua", tertua);
			model.addAttribute("termuda",termuda);
			return "tertua-termuda";
		}
	}
	
 

}