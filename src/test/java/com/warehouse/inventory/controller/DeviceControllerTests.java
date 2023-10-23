package com.warehouse.inventory.controller;

import com.warehouse.inventory.model.Device;
import com.warehouse.inventory.service.DeviceService;
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
    public void testCreateDevice() throws Exception {
        Device createdDevice = new Device(1234567, 5);
        Mockito.when(deviceService.createDevice(createdDevice)).thenReturn(createdDevice);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"pin\":\"1234567\",\"temperature\":5}"))
                .andExpect(status().isCreated())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("\"id\":1"));
        assertTrue(content.contains("\"pin\":1234567"));
        assertTrue(content.contains("\"temperature\":5"));
        assertTrue(content.contains("\"status\":ACTIVE"));
    }

    @Test
    public void testGetDeviceById() throws Exception {
        int deviceId = 1;
        Device device = new Device(1234567, 5);
        Mockito.when(deviceService.getDeviceById(deviceId)).thenReturn(device);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/devices/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("\"id\":1"));
        assertTrue(content.contains("\"pin\":1234567"));
        assertTrue(content.contains("\"temperature\":5"));
        assertTrue(content.contains("\"status\":ACTIVE"));
    }

    @Test
    public void testUpdateDevice() throws Exception {
        int deviceId = 1;
        Device updatedDevice = new Device(1234567, 10);
        Mockito.when(deviceService.updateDevice(deviceId, updatedDevice)).thenReturn(updatedDevice);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/devices/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"pin\":\"1234567\",\"temperature\":10}"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("\"temperature\":10"));
        assertTrue(content.contains("\"status\":ACTIVE"));
    }

    @Test
    public void testDeleteDevice() throws Exception {
        int deviceId = 1;
        Mockito.when(deviceService.deleteDevice(deviceId)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/devices/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}

