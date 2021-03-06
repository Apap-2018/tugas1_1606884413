package com.apap.tugas1.service;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDB;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService {
	@Autowired
	private JabatanDB jabatanDB;

	@Override
	public void addJabatan(JabatanModel jabatan) {
		// TODO Auto-generated method stub
		jabatanDB.save(jabatan);
	}

	@Override
	public ArrayList<JabatanModel> getListJabatan() {
		// TODO Auto-generated method stub
		return (ArrayList<JabatanModel>) jabatanDB.findAll();
	}

	@Override
	public JabatanModel getJabatanById(Long id) {
		return jabatanDB.findById(id).get();
	}

	@Override
	public void deleteJabatan(JabatanModel jabatan) {
		// TODO Auto-generated method stub
		jabatanDB.delete(jabatan);
	}
	
	

}
