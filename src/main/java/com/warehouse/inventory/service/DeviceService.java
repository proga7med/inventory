package com.warehouse.inventory.service;

import com.warehouse.inventory.exception.DeviceConfigurationException;
import com.warehouse.inventory.exception.DeviceNotFoundException;
import com.warehouse.inventory.model.Device;

import java.util.List;

public interface DeviceService {
    Device createDevice(Device device);

    List<Device> getDevices();

    List<Device> getDevicesOrderByPinAsc();

    Device getDeviceById(int id) throws DeviceNotFoundException;

    Device updateDevice(int id, Device device) throws DeviceNotFoundException;

    boolean deleteDevice(int id);

    Device configure(Device device) throws DeviceConfigurationException;
}
