package br.com.messagebuddy.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Membership {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long membershipId;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Conversation conversation;
	
	@Enumerated(EnumType.ORDINAL)
	private InvitationStatus invitationStatus;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date memberSince;

	public Membership() {
	}


	public Long getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(Long memberId) {
		this.membershipId = memberId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	public InvitationStatus getInvitationStatus() {
		return invitationStatus;
	}

	public void setInvitationStatus(InvitationStatus invitationStatus) {
		this.invitationStatus = invitationStatus;
	}

	public Date getMemberSince() {
		return memberSince;
	}

	public void setMemberSince(Date memberSince) {
		this.memberSince = memberSince;
	}

	

}
