package com.fpt.Dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MailerServiceImpl {
	@Autowired
	JavaMailSender sender;

	/*
	 * @Scheduled(fixedDelay = 5000) public void run() { while (!list.isEmpty()) {
	 * MailInfo mail = list.remove(0); try { this.send(mail); } catch (Exception e)
	 * { e.printStackTrace(); } } }
	 */

	public void sendEmail(String toEmail, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("longzu102@gmail.com");
		message.setTo(toEmail);
		message.setText(body);
		message.setSubject(subject);
		System.out.println("Success");
		sender.send(message);
	}

	/*
	 * @Override public void send(MailInfo mail) throws MessagingException {
	 * MimeMessage message = sender.createMimeMessage(); MimeMessageHelper helper =
	 * new MimeMessageHelper(message, true, "utf-8");
	 * helper.setFrom(mail.getFrom()); helper.setTo(mail.getTo());
	 * helper.setSubject(mail.getSubject()); helper.setText(mail.getBody(), true);
	 * helper.setReplyTo(mail.getFrom()); String[] cc = mail.getCc(); if (cc != null
	 * && cc.length > 0) { helper.setCc(cc); } String[] bcc = mail.getBcc(); if (bcc
	 * != null && bcc.length > 0) { helper.setBcc(bcc); } String[] attachments =
	 * mail.getAttachments(); if (attachments != null && attachments.length > 0) {
	 * for (String path : attachments) { File file = new File(path);
	 * helper.addAttachment(file.getName(), file); } } sender.send(message); }
	 */

}
