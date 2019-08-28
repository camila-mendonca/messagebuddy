package br.com.messagebuddy.mail;

public interface EmailSender {
	
	public void sendMessage(String to, String subject, String text);

}
