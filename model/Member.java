package kr.co.acorn.commit.model;

import lombok.Data;

@Data
public class Member {
	private int memberIdx;
	private String memberType;
	private String memberEmail;
	private String memberPassword;
	private String memberNickname;
}
