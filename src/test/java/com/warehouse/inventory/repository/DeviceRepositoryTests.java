package com.warehouse.inventory.repository;

import com.warehouse.inventory.model.Device;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class DeviceRepositoryTests {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testSaveDevice() {
        Device device = new Device(1234567, 5);
        Device savedDevice = deviceRepository.save(device);
        entityManager.flush();

        assertNotNull(savedDevice.getId());
        assertEquals("1234567", savedDevice.getPin());
        assertEquals(5, savedDevice.getTemperature());
    }

    @Test
    public void testFindDeviceById() {
        // Arrange
        Device device = new Device(1234567, 5);
        Device savedDevice = entityManager.persistAndFlush(device);
        Device foundDevice = deviceRepository.findById(savedDevice.getId()).orElse(null);

        assertNotNull(foundDevice);
        assertEquals(savedDevice.getPin(), foundDevice.getPin());
        assertEquals(savedDevice.getTemperature(), foundDevice.getTemperature());
    }

    @Test
    public void testUpdateDevice() {
        Device device = new Device(1234567, 5);
        Device savedDevice = entityManager.persistAndFlush(device);

        savedDevice.setTemperature(10);
        Device updatedDevice = deviceRepository.save(savedDevice);
        entityManager.flush();

        assertEquals(savedDevice.getId(), updatedDevice.getId());
        assertEquals(10, updatedDevice.getTemperature());
    }

    @Test
    public void testDeleteDevice() {
        Device device = new Device(1234567, 5);
        Device savedDevice = entityManager.persistAndFlush(device);

        deviceRepository.deleteById(savedDevice.getId());
        entityManager.flush();
        Device deletedDevice = deviceRepository.findById(savedDevice.getId()).orElse(null);

        assertNull(deletedDevice);
    }
}

