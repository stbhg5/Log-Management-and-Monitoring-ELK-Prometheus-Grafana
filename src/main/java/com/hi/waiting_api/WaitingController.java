package com.hi.waiting_api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/waiting")
@RequiredArgsConstructor
@Slf4j
public class WaitingController {

    private final WaitingService waitingService;

    @PostMapping
    public String register(@RequestBody WaitingRequest waitingRequest) {
        // String name = waitingRequest.getName();
        // String phone = waitingRequest.getPhone();

        try {
            log.info("[api 호출] 웨이팅 등록 요청 들어옴");

            // return waitingService.registerWaiting(name, phone);
            return waitingService.registerWaiting(waitingRequest.getName(), waitingRequest.getPhone());
        } catch (IllegalArgumentException e) {
            // log.error("[api 오류] 웨이팅 등록 중 문제 발생 : {}", e.getMessage());
            log.error("[api 오류] 웨이팅 등록 중 문제 발생 : {}", e);

            return "웨이팅 등록에 실패했습니다: " + e.getMessage();
        }
    }

}