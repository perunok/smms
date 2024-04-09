package com.act.smms.service;

import org.springframework.stereotype.Service;

import com.act.smms.enums.ISP;

@Service
public class PhoneVerifier {

    public ISP getISP(String phone) {
        String _phone;

        // trim the prefix
        switch (phone.length()) {
            case 13:
                // if it starts with +251
                _phone = phone.substring(4);
                break;
            case 12:
                // if it starts with 251
                _phone = phone.substring(3);
                break;
            case 10:
                // if it starts with 0
                _phone = phone.substring(1);
                break;
            default:
                _phone = "";
                break;
        }
        // identify the isp
        if (_phone.startsWith("9")) {
            return ISP.ETHIOTEL;
        } else if (_phone.startsWith("7")) {
            return ISP.SAFARICOM;
        } else {
            return null;
        }
    }

    public boolean isValid(String phone) {
        // verify if the country code is valid

        if (!phone.startsWith("251") && !phone.startsWith("+251") && !phone.startsWith("0")) {
            return false;
        }

        switch (phone.length()) {
            case 13:
                // if it starts with +251
                if (phone.startsWith("+251")) {
                    return phone.substring(4).length() == 9;
                }
                return false;
            case 12:
                // if it starts with 251
                if (phone.startsWith("251")) {
                    return phone.substring(3).length() == 9;
                }
            case 10:
                // if it starts with 0
                if (phone.startsWith("0")) {
                    return phone.substring(1).length() == 9;
                }
            default:
                return false;
        }
    }
}
