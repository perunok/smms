package com.act.smms.model;

import com.act.smms.enums.ISP;
import com.act.smms.enums.ISPConfigOption;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "configs")
public class ISPConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "option_name", unique = true, nullable = false)
    private ISPConfigOption option = ISPConfigOption.DEFAULT;

    @Column(name = "server_ip", nullable = false)
    private String serverIP;

    @Column(name = "server_port", nullable = false)
    private int serverPort;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "short_number", nullable = false)
    private int shortNumber;

    @Column(name = "delivery_port", nullable = false)
    private int deliveryPort;

    @Column(name = "delivery_ip", nullable = false)
    private String deliveryIP;

    @Column(name = "isp", nullable = false)
    private ISP isp;

    

    public ISPConfig() {
    }

    public ISPConfig(String serverIP, int serverPort, String username, String password, int shortNumber,
            int deliveryPort, String deliveryIP, ISP isp) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.username = username;
        this.password = password;
        this.shortNumber = shortNumber;
        this.deliveryPort = deliveryPort;
        this.deliveryIP = deliveryIP;
        this.isp = isp;
    }

    public ISPConfigOption getOption() {
        return option;
    }

    public void setOption(ISPConfigOption option) {
        this.option = option;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getShortNumber() {
        return shortNumber;
    }

    public void setShortNumber(int shortNumber) {
        this.shortNumber = shortNumber;
    }

    public int getDeliveryPort() {
        return deliveryPort;
    }

    public void setDeliveryPort(int deliveryPort) {
        this.deliveryPort = deliveryPort;
    }

    public String getDeliveryIP() {
        return deliveryIP;
    }

    public void setDeliveryIP(String deliveryIP) {
        this.deliveryIP = deliveryIP;
    }

    public ISP getIsp() {
        return isp;
    }

    public void setIsp(ISP isp) {
        this.isp = isp;
    }

}
