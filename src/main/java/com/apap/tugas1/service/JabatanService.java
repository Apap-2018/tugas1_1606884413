package com.apap.tugas1.service;

import java.util.ArrayList;

import com.apap.tugas1.model.JabatanModel;

public interface JabatanService {
	void addJabatan(JabatanModel jabatan);
	ArrayList<JabatanModel> getListJabatan();
	JabatanModel getJabatanById(Long id);
}
