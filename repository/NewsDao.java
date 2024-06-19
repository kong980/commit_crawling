package kr.co.acorn.commit.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.acorn.commit.mapper.DataMappingInterface;
import kr.co.acorn.commit.model.News;

@Repository
public class NewsDao {
	@Autowired
	private DataMappingInterface dataInterface;

	public List<News> getDataAll() {
		List<News> list = dataInterface.selectAllNews();
		return list;
	}

	public int insertDataNews(News dt) {
		dt.getTitle();
		dt.getSubTitle();
		dt.getContent();
		dt.getImage();
		dt.getOrigin();
		dt.getWriter();
		dt.getOriginDate();
		dt.getCreateDate();

		int result = dataInterface.insertDataNews(dt);
		return result;
	}
}
