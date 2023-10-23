package com.warehouse.inventory.service;

import com.warehouse.inventory.exception.DeviceConfigurationException;
import com.warehouse.inventory.model.Device;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class DeviceServiceImplTests {
    private DeviceService deviceService;

    @BeforeEach
    public void setup() {
        deviceService = new DeviceServiceImpl();
    }

    @Test
    public void testConfigureDeviceWhenNotActive() throws DeviceConfigurationException {
        Device device = new Device(1, -1);
        deviceService.configure(device);
        assertEquals(device.getStatus(), Device.Status.ACTIVE);
    }

    @Test
    public void testConfigureDeviceWhenActive() throws DeviceConfigurationException {
        Device device = new Device(1234567, 5);
        assertThrows(DeviceConfigurationException.class, () -> deviceService.configure(device));
    }
}
