package com.lalala.music.listener;

import java.io.File;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StartUpListener {
    @EventListener(ApplicationReadyEvent.class)
    public void MakeTempDirectoryAfterStartUp() {
        new File("temp/").mkdir();
        log.info("임시 음원 파일을 받을 temp 폴더를 생성했습니다.");
    }
}
