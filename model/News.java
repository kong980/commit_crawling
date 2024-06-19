package kr.co.acorn.commit.model;

import java.sql.Timestamp;

import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class News {
	private String title, subTitle, content, image, origin, writer;
	private Timestamp originDate, createDate;
}
