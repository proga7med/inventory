package com.warehouse.inventory.model;

import com.warehouse.inventory.util.PinGenerator;
import jakarta.persistence.*;

@Entity
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int pin;
    private int temperature;
    @Enumerated(EnumType.STRING)
    private Status status;

    public Device() {
        this.pin = PinGenerator.generate();
        this.temperature = -1;
        this.status = Status.READY;
    }

    public Device(int pin, int temperature) {
        this.pin = pin;
        this.temperature = temperature;
        this.status = temperature < 0 ? Status.READY : Status.ACTIVE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        ACTIVE,
        READY
    }
}
