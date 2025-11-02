package com.bag.complaint_system.identity.domain.service;

public interface PasswordEncryptionService {

    String encryptPassword(String password);

    boolean checkPassword(String password, String encryptedPassword);
}
