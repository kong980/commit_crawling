package kr.co.acorn.commit.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.co.acorn.commit.model.Job;
import kr.co.acorn.commit.model.News;

@Mapper
public interface DataMappingInterface {
	@Select("select * from job")
	List<Job> selectAll();

	@Select("select finishDate_S from job")
	List<Job> getDate();

	@Insert("insert into job(companyname, title, career, degree, location, image, createDate, finishDate_S, finishDate_D) values(#{companyname, jdbcType=VARCHAR}, #{title, jdbcType=VARCHAR}, #{career, jdbcType=VARCHAR}, #{degree, jdbcType=VARCHAR}, #{location, jdbcType=VARCHAR}, #{image, jdbcType=VARCHAR}, #{createDate, jdbcType=TIMESTAMP}, #{finishDate_S, jdbcType=VARCHAR}, #{finishDate_D, jdbcType=TIMESTAMP})")
	int insertData(Job data);

	@Select("select * from news")
	List<News> selectAllNews();

	@Insert("insert into news(title, subTitle, content, image, origin, writer, originDate, createDate) values(#{title, jdbcType=VARCHAR}, #{subTitle, jdbcType=VARCHAR}, #{content, jdbcType=VARCHAR}, #{image, jdbcType=VARCHAR}, #{origin, jdbcType=VARCHAR}, #{writer, jdbcType=VARCHAR}, #{originDate, jdbcType=TIMESTAMP}, #{createDate, jdbcType=TIMESTAMP})")
	int insertDataNews(News data);

}
