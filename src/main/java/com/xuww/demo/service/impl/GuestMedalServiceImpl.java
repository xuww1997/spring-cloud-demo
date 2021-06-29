package com.xuww.demo.service.impl;

import com.xuww.demo.service.IMedalService;
import org.springframework.stereotype.Service;

@Service("guest")
public class GuestMedalServiceImpl implements IMedalService {
    @Override
    public void showMedal() {
        System.out.println("展示嘉宾勋章");
    }
}
