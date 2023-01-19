package com.doneasy.don.service.project.donationofproject.kakao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoAmount {

    private int total;
    private int tax_free;
    private int vat;
    private int point;
    private int discount;
}
