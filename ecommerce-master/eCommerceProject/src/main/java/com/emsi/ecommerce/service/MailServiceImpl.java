package com.emsi.ecommerce.service;

import java.io.File;

import org.springframework.stereotype.Service;

import com.emsi.ecommerce.domaine.UserVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

@Service
public class MailServiceImpl implements IMailService {

	@Autowired
    private JavaMailSender javaMailSender;
	
	@Autowired
	private IAchatService achatService;
	
	public static final String EXCEL_PATH= "C:\\ecommerceApp\\ExcelFiles\\";

	
	@Override
	public void sendEmailWithExcelAttachement(UserVo client) throws Exception {
		
		MimeMessage msg = javaMailSender.createMimeMessage();
		// true = multipart message
		MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		helper.setTo(client.getEmail());
		helper.setSubject("IMAF-Prod Panier");
		// default = text/plain
		// helper.setText("Check attachment for image!");
		// true = text/html
		helper.setText("<html><h1>Merci de trouver ci-joint la liste de vos achats! </h1></html>", true);
		helper.addAttachment(client.getUsername()+".xlsx", new File(EXCEL_PATH+client.getUsername()+".xlsx"));
		
		try {
			
			javaMailSender.send(msg);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void sendValidationAccountEmail(UserVo client) {
		SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(client.getEmail());
        msg.setSubject("Vos identifiants pour acceder à IMAF-Prod");
        msg.setText("Merci de trouver ci joint vos identifiants \nUsername : "+client.getUsername()+"\nPassword : "+client.getPassword());
        
        new Thread(new Runnable() {

            @Override
            public void run() {
            	javaMailSender.send(msg);
            	
            }
        }).start();
            	
           
		
        
		
	}


}
