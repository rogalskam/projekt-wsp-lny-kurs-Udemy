package com.example.auth.services;

import com.example.auth.configuration.EmailConfiguration;
import com.example.auth.entity.User;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;

import java.io.IOException;


@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailConfiguration emailConfiguration;

    @Value("${front.url}")
    private String fontendUrl;

    @Value("classpath:static/mail-aktywuj.html")
    private Resource activeTemplate;
    @Value("classpath:static/resetuj-haslo.html")
    private Resource recoveryTemplate;


    public void sendActivation(User user){
        try{
            String html = Files.toString(activeTemplate.getFile(), Charsets.UTF_8);
            html = html.replace("https://google.com",fontendUrl+"/aktywuj/"+user.getUuid());
            emailConfiguration.sendMail(user.getEmail(), html,"Aktywacja konta",true);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public void sendPasswordRecovery(User user, String uuid) {
        try{
            String html = Files.toString(recoveryTemplate.getFile(), Charsets.UTF_8);
            html = html.replace("https://google.com",fontendUrl+"/odzyskaj-haslo/"+uuid);
            emailConfiguration.sendMail(user.getEmail(), html,"Odzyskanie hasła",true);
        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }
}
