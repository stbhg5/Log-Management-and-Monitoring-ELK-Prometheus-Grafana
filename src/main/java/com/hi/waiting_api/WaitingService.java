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

        // 1. [INFO] : 일반적인 비즈니스 흐름 기록(요청 수신)
        log.info("[웨이팅 요청] 이름 : {}, 연락처 : {}", name, maskedPhone);

        // 2. [ERROR] : 비즈니스 오류(중복 등록 시도)
        if (registeredPhones.contains(phone)) {
            log.error("[웨이팅 실패] 중복 등록 시도 발생! 연락처 : {}", maskedPhone);
            // log.warn("[웨이팅 실패] 중복 등록 시도 발생! 연락처 : {}", maskedPhone);
            // log.info("[웨이팅 실패] 중복 등록 시도 발생! 연락처 : {}", maskedPhone);
            throw new IllegalArgumentException("이미 대기 등록된 연락처입니다.");
        }

        // 3. [WARN] 시스템 경고 (대기열 마감 임박) => 50번, 100번, 1000번? 정하기 나름
        if (currentWaitingCount >= 45) {
        // if (currentWaitingCount >= 2) {
            log.warn("[웨이팅 경고] 대기열 마감 임박! 현재 대기 인원 : {}명", currentWaitingCount);
        }

        registeredPhones.add(phone);
        currentWaitingCount++;

        // 4. [INFO] 비즈니스 정상 처리 완료
        log.info("[웨이팅 완료] 대기번호 {}번 발급 완료 (고객명 : {})", currentWaitingCount, name);

        return name + "님, 대기번호 " + currentWaitingCount + "번이 발급되었습니다.";
    }

    // 전화번호 마스킹 메서드 (예: 010-1234-5678 -> 010-****-5678)
    private String maskPhoneNumber(String phone) {
        if (phone == null || phone.length() < 13) return "****";
        return phone.substring(0, 4) + "****" + phone.substring(8);
    }

}