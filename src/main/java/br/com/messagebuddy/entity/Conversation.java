package br.com.messagebuddy.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Conversation implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long conversationId;
	@ManyToOne
	@JoinColumn
	private User user;
	@NotBlank(message = "The group must have a subject")
	private String subject;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date creationDate;
	private Boolean open;
	@OneToMany(mappedBy = "conversation")
	Set<Membership> members;
	@OneToOne
	private Message lastMessage;
	
	public Conversation() {
	}

	public Long getConversationId() {
		return conversationId;
	}

	public void setConversationId(Long groupId) {
		this.conversationId = groupId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public Set<Membership> getMembers() {
		return members;
	}

	public void setMembers(Set<Membership> members) {
		this.members = members;
	}

	public Message getLastMessage() {
		return lastMessage;
	}

	public void setLastMessage(Message lastMessage) {
		this.lastMessage = lastMessage;
	}	

}
