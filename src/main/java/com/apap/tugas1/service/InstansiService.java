package com.apap.tugas1.service;

import java.util.List;
import java.util.Map;

import com.apap.tugas1.model.InstansiModel;

public interface InstansiService {
	List<InstansiModel> getInstansiList();
	InstansiModel getInstansiById(long id);

}
