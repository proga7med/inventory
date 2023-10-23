package com.warehouse.inventory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.warehouse.inventory.model.Device;
import com.warehouse.inventory.service.DeviceService;
import com.warehouse.inventory.util.PinGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(DeviceController.class)
public class DeviceControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeviceService deviceService;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void testGetDevices() throws Exception {
        // Arrange
        List<Device> devices = Arrays.asList(
                new Device(PinGenerator.generate(), -1),
                new Device(PinGenerator.generate(), 5)
        );
        Mockito.when(deviceService.getDevicesOrderByPinAsc()).thenReturn(devices);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/devices")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("\"pin\":"));
        assertTrue(content.contains("\"temperature\":"));
    }

    @Test
    public void testGetDeviceById() throws Exception {
        int deviceId = 1;
        Device device = new Device(1234567, -1);
        device.setId(deviceId);

        Mockito.when(deviceService.getDeviceById(deviceId)).thenReturn(device);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/devices/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("\"id\":1"));
        assertTrue(content.contains("\"pin\":1234567"));
        assertTrue(content.contains("\"temperature\":-1"));
    }

    @Test
    public void testCreateDevice() throws Exception {
        int deviceId = 1;
        Device deviceToCreate = new Device(1234567, 5);
        deviceToCreate.setId(deviceId);

        Device createdDevice = new Device(deviceToCreate.getPin(), 5);
        createdDevice.setId(deviceId);

        Mockito.when(deviceService.createDevice(deviceToCreate)).thenReturn(createdDevice);

        ObjectMapper objectMapper = new ObjectMapper();
        String deviceJson = objectMapper.writeValueAsString(deviceToCreate);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(deviceJson))
                .andExpect(status().isCreated())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("\"id\":1"));
        assertTrue(content.contains("\"pin\":1234567"));
        assertTrue(content.contains("\"temperature\":5"));
    }

    @Test
    public void testUpdateDevice() throws Exception {
        int deviceId = 1;
        Device updatedDevice = new Device(2345678, 10);
        updatedDevice.setId(deviceId);

        Device deviceBeforeUpdate = new Device(1234567, 5);
        deviceBeforeUpdate.setId(deviceId);

        Mockito.when(deviceService.getDeviceById(deviceId)).thenReturn(deviceBeforeUpdate);
        Mockito.when(deviceService.updateDevice(deviceId, updatedDevice)).thenReturn(updatedDevice);

        ObjectMapper objectMapper = new ObjectMapper();
        String deviceJson = objectMapper.writeValueAsString(updatedDevice);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/devices/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(deviceJson))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("\"id\":1"));
        assertTrue(content.contains("\"temperature\":10"));
    }

    @Test
    public void testDeleteDevice() throws Exception {
        int deviceId = 1;
        Mockito.when(deviceService.deleteDevice(deviceId)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/devices/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}


