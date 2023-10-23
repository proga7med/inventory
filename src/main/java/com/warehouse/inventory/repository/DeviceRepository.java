package com.warehouse.inventory.repository;

import com.warehouse.inventory.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {
    List<Device> findAllByOrderByPinAsc();
}

