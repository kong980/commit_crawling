package kr.co.acorn.commit.model;

import java.sql.Timestamp;

import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class Job {
	private String companyname, title, career, degree, location, finishDate_S, image;
	private Timestamp createDate, finishDate_D;
}
