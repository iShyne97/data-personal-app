package com.personalapp.repository;

import com.personalapp.model.DataPersonal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DataPersonalRepository extends JpaRepository<DataPersonal, Long> {
    Optional<DataPersonal> findByNikAndFullnameContainingIgnoreCase(Long nik, String fullname);
    List<DataPersonal> findAllByOrderByFullnameAsc();
}
