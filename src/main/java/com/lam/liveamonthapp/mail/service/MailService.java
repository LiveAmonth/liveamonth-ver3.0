package com.lam.liveamonthapp.mail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.lam.liveamonthapp.mail.constants.MailConstant;
import com.lam.liveamonthapp.mail.dto.TempPasswordSendMailInfo;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

import static com.lam.liveamonthapp.constants.AttrConstants.*;


@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
    private final MessageSource messageSource;

    public void sendMail(TempPasswordSendMailInfo info, String password) {
        // 필요한거 -> 회원 이름, 이메일, 임시 비번
        MimeMessage message = mailSender.createMimeMessage();
        String helloUser = transMessage(MailConstant.HELLO.getCode(), new String[]{info.getName()});
        String intro = transMessage(MailConstant.INTRO.getCode(), null);
        String temp = transMessage(MailConstant.TEMP.getCode(), new String[]{password});
        String login = transMessage(MailConstant.LOGIN.getCode(), null);
        String edit = transMessage(MailConstant.EDIT.getCode(), null);
        String ask = transMessage(MailConstant.ASK.getCode(), null);
        String end = transMessage(MailConstant.END.getCode(), null);
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, UTF_8);
            messageHelper.setSubject(transMessage(MailConstant.PW_SUBJECT.getPwMailCode(), null));
            String htmlContent =
//                    "<img src=\"img/logo.png\"><br><br>"
                    helloUser + "<br><br>"
                    + "<h4>" + intro + "<br>"
                    + temp + "<br>"
                    + "<a href=\"http://localhost:3000/" + "login\">" + login + "</a></h4><br>"
                    + "<p>" + edit + "</p><br>"
                    + "<p>" + ask + "</p><br>"
                    + "<strong>" + end + "</strong>";
            messageHelper.setText(htmlContent, true);
            messageHelper.setFrom(ADMIN_EMAIL, ADMIN_NAME);
            messageHelper.setTo(new InternetAddress(info.getEmail(), info.getName(), UTF_8));
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String transMessage(String code, String[] params) {
        return messageSource.getMessage(code, params, Locale.KOREA);
    }
//    public void sendOneToOneAskByMail(OneToOneAskVO oneToOneAskVO, UserVO userVO) {
//        MimeMessage message = mailSender.createMimeMessage();
//        try {
//            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
//            messageHelper.setSubject("[한달살기에서 알려드립니다]고객님의 1:1 문의 답변이 완료되었습니다.");
//            String htmlContent = "<img src=\"https://liveamonth-resources.s3.ap-northeast-2.amazonaws.com/img/logo/logo.png\"><br><br>"
//                    +"안녕하세요. "+userVO.getUserName()+" 고객님<br><br>"
//                    + "<h4>1:1 문의 답변이 완료되었습니다.<br><br>"
//                    + "문의 날짜 : " + oneToOneAskVO.getOneToOneAskDate()+"<br>"
//                    + "문의 내용 : " + oneToOneAskVO.getOneToOneAskDesc()+"<br><br>"
//                    + "답변 내용 : " + oneToOneAskVO.getOneToOneAskReply()+"<br></h4><br>"
//                    + "<p>다른 문의사항이 있으시면 한달살기(TP.liveamonth@gamil.com)으로 문의해 주시기 바랍니다.</p><br>"
//                    + "<strong>감사합니다.</strong>";
//            messageHelper.setText(htmlContent,true);
//            messageHelper.setFrom("TP.liveamonth@gmail.com","한달살기");
//            messageHelper.setTo(new InternetAddress(oneToOneAskVO.getOneToOneAskUserEmail(),userVO.getUserName(), "UTF-8"));
//            mailSender.send(message);
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
