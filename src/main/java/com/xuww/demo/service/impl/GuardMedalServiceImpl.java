package com.xuww.demo.service.impl;

import com.xuww.demo.service.IMedalService;
import org.springframework.stereotype.Service;

@Service("guard")
public class GuardMedalServiceImpl implements IMedalService {
    @Override
    public void showMedal() {
        System.out.println("展示守护勋章");
    }
}
