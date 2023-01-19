package com.doneasy.don.service.project.donationofproject.kakao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Slf4j
@Component
public class KakaoSession {

    private static final HashMap<String, Object> kakaoSession = new HashMap<>();

    public void saveTid(String memberId, String tid) {
        kakaoSession.put(memberId, tid);
    }

    public void saveProjectId(String memberId, Long projectId) {
        kakaoSession.put(memberId + "ProjectId", projectId);
    }

    public void saveOrderId(String memberId, String orderId) {
        kakaoSession.put(memberId + "OrderId", orderId);
    }

    public void saveResult(String memberId, String status) {
        kakaoSession.put(memberId + "Result", status);
    }

    public void saveValue(String memberId, Integer value) {
        kakaoSession.put(memberId + "Value", value);
    }

    public void invalidData(String memberId) {
        kakaoSession.remove(memberId);
        kakaoSession.remove(memberId + "ProjectId");
        kakaoSession.remove(memberId + "OrderId");
        kakaoSession.remove(memberId + "Value");
        kakaoSession.remove(memberId + "Result");
    }

    public String getTid(String memberId) {
        return (String) kakaoSession.get(memberId);
    }

    public Long getProjectId(String memberId) {
        return (Long) kakaoSession.get(memberId + "ProjectId");
    }

    public String getOrderId(String memberId) {
        return (String) kakaoSession.get(memberId + "OrderId");
    }

    public String getResult(String memberId) {
        return (String) kakaoSession.get(memberId + "Result");
    }

    public Integer getValue(String memberId) {
        return (Integer) kakaoSession.get(memberId + "Value");
    }
}
