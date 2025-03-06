package com.personalapp.service;

import com.personalapp.dto.request.DataPersonalDTO;
import com.personalapp.model.DataPersonal;

import java.util.List;

public interface DataPersonalService {
    DataPersonal createData(DataPersonalDTO dataPersonalDTO);
    DataPersonal updateData(Long nik, DataPersonalDTO dataPersonalDTO);
    DataPersonal deleteData(Long nik);
    List<DataPersonalDTO> readData();
    DataPersonalDTO findByNikAndName(Long nik, String fullname);
}
