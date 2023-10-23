package com.warehouse.inventory.service;

import com.warehouse.inventory.exception.DeviceConfigurationException;
import com.warehouse.inventory.exception.DeviceNotFoundException;
import com.warehouse.inventory.model.Device;
import com.warehouse.inventory.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;

    private final Random random = new Random();

    @Override
    public Device createDevice(Device device) {
        return deviceRepository.save(device);
    }

    @Override
    public List<Device> getDevices() {
        return deviceRepository.findAll();
    }

    @Override
    public List<Device> getDevicesOrderByPinAsc() {
        return deviceRepository.findAllByOrderByPinAsc();
    }

    @Override
    public Device getDeviceById(int id) throws DeviceNotFoundException {
        return deviceRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException(String.format("Device %d Not found", id)));
    }

    @Override
    public Device updateDevice(int id, Device device) throws DeviceNotFoundException {
        Device existingDevice = deviceRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException(String.format("Device %d Not found", id)));

        existingDevice.setTemperature(device.getTemperature());
        existingDevice.setPin(device.getPin());
        return deviceRepository.save(existingDevice);
    }

    @Override
    public boolean deleteDevice(int id) {
        deviceRepository.deleteById(id);
        return true;
    }

    @Override
    public Device configure(Device device) throws DeviceConfigurationException {
        if (device.getStatus() == Device.Status.ACTIVE) {
            throw new DeviceConfigurationException(String.format("Device %d is already configured", device.getId()));
        }

        int temperature = random.nextInt(10) + 1;
        device.setTemperature(temperature);
        device.setStatus(Device.Status.ACTIVE);

        return device;
    }
}
