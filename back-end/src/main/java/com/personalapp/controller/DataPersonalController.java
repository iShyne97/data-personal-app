package com.personalapp.controller;

import com.personalapp.dto.request.DataPersonalDTO;
import com.personalapp.model.DataPersonal;
import com.personalapp.service.DataPersonalService;
import com.personalapp.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/data")
@CrossOrigin(origins = "*")
public class DataPersonalController {

    @Autowired
    private final DataPersonalService dataPersonalService;

    private final Logger log = LoggerFactory.getLogger(DataPersonalController.class);

    @PostMapping
    public ResponseEntity<Object> createData(@RequestBody DataPersonalDTO dataPersonalDTO, HttpServletRequest request){
        DataPersonal data = dataPersonalService.createData(dataPersonalDTO);
        return ResponseEntity.ok(ResponseUtil.success(data, "Data berhasil ditambahkan!", request.getRequestURI()));
    }

    @PutMapping("/{nik}")
    public ResponseEntity<Object> updateData(@PathVariable Long nik, @RequestBody DataPersonalDTO dataPersonalDTO, HttpServletRequest request){
        DataPersonal data = dataPersonalService.updateData(nik, dataPersonalDTO);
        return ResponseEntity.ok(ResponseUtil.success(data, "Data berhasil diubah!", request.getRequestURI()));
    }

    @DeleteMapping(path = "/{nik}")
    public ResponseEntity<Object> deleteData(@PathVariable Long nik, HttpServletRequest request){
        DataPersonal data = dataPersonalService.deleteData(nik);
        return ResponseEntity.ok(ResponseUtil.success(data, "Data berhasil dihapus!", request.getRequestURI()));
    }

    @GetMapping("/find")
    public ResponseEntity<Object> findDataByNikAndName(
            @RequestParam Long nik,
            @RequestParam String fullname,
            HttpServletRequest request){
        DataPersonalDTO data = dataPersonalService.findByNikAndName(nik, fullname);
        return ResponseEntity.ok(ResponseUtil.success(data, "Data ditemukan!", request.getRequestURI()));
    }

    @GetMapping
    public ResponseEntity<Object> getAllData(HttpServletRequest request) {

        List<DataPersonalDTO> dataPersonalDTOList = dataPersonalService.readData();

        return ResponseEntity.ok(ResponseUtil.success(
                dataPersonalDTOList,
                "Received " + dataPersonalDTOList.size() + " Data",
                request.getRequestURI()
        ));
    }

}
