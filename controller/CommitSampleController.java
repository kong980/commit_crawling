package kr.co.acorn.commit.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.acorn.commit.model.Job;
import kr.co.acorn.commit.model.News;
import kr.co.acorn.commit.repository.JobDao;
import kr.co.acorn.commit.repository.NewsDao;
import kr.co.acorn.commit.service.JobService;
import kr.co.acorn.commit.service.NewsService;

@RestController
public class CommitSampleController {

	@Autowired
	private JobService jobService;

	@Autowired
	private JobDao dao;

	@Autowired
	private NewsService newsService;

	@Autowired
	public NewsDao newsDao;

	@GetMapping("/jobs")
	public ArrayList<Job> list() {
		// 크롤링 한 데이터 DB에 집어넣기
//		jobService.getData();

		// DB에 있는 데이터 조회
		ArrayList<Job> list = (ArrayList<Job>) dao.getDataAll();
		return list;
	}

	@GetMapping("/news")
	public ArrayList<News> newsList() {
		// 크롤링 한 데이터 DB에 집어넣기
//		newsService.getData();

		// DB에 있는 데이터 조회
		ArrayList<News> list = (ArrayList<News>) newsDao.getDataAll();
		return list;
	}

}
