package com.hi.waiting_api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class WaitingService {

    private int currentWaitingCount = 0; // 현재 대기 번호
    private final Set<String> registeredPhones = new HashSet<>(); // 중복 등록 방지용 셋

    public String registerWaiting(String name, String phone) {
        String maskedPhone = maskPhoneNumber(phone);

        log.info("[웨이팅 요청] 이름 : {}, 연락처 : {}", name, maskedPhone);

        if (registeredPhones.contains(phone)) {
            throw new IllegalArgumentException("이미 대기 등록된 연락처입니다.");
        }

        if (currentWaitingCount >= 45) {
        }

        registeredPhones.add(phone);
        currentWaitingCount++;

        return name + "님, 대기번호 " + currentWaitingCount + "번이 발급되었습니다.";
    }

    // 전화번호 마스킹 메서드 (예: 010-1234-5678 -> 010-****-5678)
    private String maskPhoneNumber(String phone) {
        if (phone == null || phone.length() < 13) return "****";
        return phone.substring(0, 4) + "****" + phone.substring(8);
    }

}