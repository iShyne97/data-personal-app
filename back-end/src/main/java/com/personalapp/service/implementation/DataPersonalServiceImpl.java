package com.personalapp.service.implementation;

import com.personalapp.controller.DataPersonalController;
import com.personalapp.dto.request.DataPersonalDTO;
import com.personalapp.exception.DataNotFoundException;
import com.personalapp.exception.NikAlreadyExistException;
import com.personalapp.model.DataPersonal;
import com.personalapp.repository.DataPersonalRepository;
import com.personalapp.service.DataPersonalService;
import com.personalapp.util.DateUtil;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DataPersonalServiceImpl implements DataPersonalService {

    private final DataPersonalRepository dataPersonalRepository;

    private final Logger log = LoggerFactory.getLogger(DataPersonalController.class);

    @Override
    public DataPersonal createData(DataPersonalDTO dataPersonalDTO) {
        if (dataPersonalRepository.existsById(dataPersonalDTO.getNik())) {
            throw new NikAlreadyExistException("Data dengan NIK " + dataPersonalDTO.getNik() + " sudah ada!");
        }

        DataPersonal dataPersonal = DataPersonal.builder()
                .nik(dataPersonalDTO.getNik())
                .fullname(dataPersonalDTO.getFullname())
                .gender(dataPersonalDTO.getGender())
                .birth(dataPersonalDTO.getBirth())
                .address(dataPersonalDTO.getAddress())
                .country(dataPersonalDTO.getCountry())
                .build();

        return dataPersonalRepository.save(dataPersonal);
    }

    @Override
    public DataPersonal updateData(Long nik, DataPersonalDTO dataPersonalDTO) {
        DataPersonal existingData = dataPersonalRepository.findById(nik)
                .orElseThrow(() -> new DataNotFoundException("Data dengan NIK " + nik + " tidak ditemukan"));

        existingData.setFullname(dataPersonalDTO.getFullname());
        existingData.setGender(dataPersonalDTO.getGender());
        existingData.setBirth(dataPersonalDTO.getBirth());
        existingData.setAddress(dataPersonalDTO.getAddress());
        existingData.setCountry(dataPersonalDTO.getCountry());

        return dataPersonalRepository.save(existingData);
    }

    @Override
    public DataPersonal deleteData(Long nik) {
        DataPersonal data = dataPersonalRepository.findById(nik)
                .orElseThrow(() -> new DataNotFoundException("Data dengan id " + nik + " tidak ditemukan!"));
        dataPersonalRepository.deleteById(nik);
        return data;
    }

    @Override
    public List<DataPersonalDTO> readData() {

        List<DataPersonal> dataList = dataPersonalRepository.findAllByOrderByFullnameAsc();

        if (dataList.isEmpty()) {
            return Collections.emptyList();
        }
            List<DataPersonalDTO> result = dataList.stream()
                    .map(data -> {
                        String formattedDate = DateUtil.formatDate(data.getBirth());
                        return new DataPersonalDTO(
                                data.getNik(),
                                data.getFullname(),
                                data.getGender(),
                                data.getBirth(),
                                data.getAddress(),
                                data.getCountry(),
                                countAge(data.getBirth())
                        );
                    })
                    .collect(Collectors.toList());
            return result;
    }

    @Override
    public DataPersonalDTO findByNikAndName(Long nik, String fullname) {
        return dataPersonalRepository.findByNikAndFullnameContainingIgnoreCase(nik, fullname)
                .map(data -> new DataPersonalDTO(
                        data.getNik(),
                        data.getFullname(),
                        data.getGender(),
                        data.getBirth(),
                        data.getAddress(),
                        data.getCountry(),
                        countAge(data.getBirth())
                ))
                .orElseThrow(() -> new DataNotFoundException("Data NIK " + nik + " dengan Nama " + fullname + " tidak ditemukan!"));
    }

    public int countAge(Date birthDate) {
        if (birthDate == null) {
            return 0;
        }

        LocalDate birthLocalDate;

        if (birthDate instanceof java.sql.Date) {
            birthLocalDate = ((java.sql.Date) birthDate).toLocalDate();
        } else {
            birthLocalDate = birthDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }

        return Period.between(birthLocalDate, LocalDate.now()).getYears();
    }

}
