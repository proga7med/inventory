package com.warehouse.inventory.controller;

import com.warehouse.inventory.exception.DeviceConfigurationException;
import com.warehouse.inventory.exception.DeviceNotFoundException;
import com.warehouse.inventory.model.Device;
import com.warehouse.inventory.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DeviceController {
    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/devices")
    public ResponseEntity<List<Device>> getDevices() {
        List<Device> devices = deviceService.getDevicesOrderByPinAsc();
        return ResponseEntity.ok().body(devices);
    }

    @GetMapping("/devices/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable(value = "id") int deviceId) throws DeviceNotFoundException {
        Device device = deviceService.getDeviceById(deviceId);
        return ResponseEntity.ok().body(device);
    }

    @PostMapping("/devices")
    public ResponseEntity<Device> createDevice(@RequestBody Device device) {
        Device createdDevice = deviceService.createDevice(device);
        return new ResponseEntity<>(createdDevice, HttpStatus.CREATED);
    }

    @PutMapping("/devices/{id}")
    public ResponseEntity<Device> updateDevice(@PathVariable(value = "id") int deviceId, @RequestBody Device deviceDetails) throws DeviceNotFoundException {
        Device updatedDevice = deviceService.updateDevice(deviceId, deviceDetails);
        return new ResponseEntity<>(updatedDevice, HttpStatus.OK);
    }

    @DeleteMapping("/devices/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable(value = "id") int deviceId) throws DeviceNotFoundException {
        deviceService.deleteDevice(deviceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/devices/{id}/configure")
    public ResponseEntity<Device> configureDevice(@PathVariable(value = "id") int deviceId) throws DeviceNotFoundException, DeviceConfigurationException {
        Device device = deviceService.getDeviceById(deviceId);
        Device configuredDevice = deviceService.configure(device);
        return new ResponseEntity<>(configuredDevice, HttpStatus.OK);
    }
}
