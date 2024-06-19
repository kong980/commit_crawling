package kr.co.acorn.commit.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.acorn.commit.mapper.DataMappingInterface;
import kr.co.acorn.commit.model.Job;

@Repository
public class JobDao {
	@Autowired
	private DataMappingInterface dataInterface;

	public List<Job> getDataAll() {
		List<Job> list = dataInterface.selectAll();
		return list;
	}

	public int insertData(Job dt) {
		dt.getCompanyname();
		dt.getTitle();
		dt.getCareer();
		dt.getDegree();
		dt.getLocation();
		dt.getCreateDate();
		dt.getFinishDate_S();
		dt.getFinishDate_D();
		dt.getImage();

		int result = dataInterface.insertData(dt);
		return result;
	}
	
	public List<Job> getDate(){
		List<Job> list = dataInterface.getDate();
		return list;
	}
	
}
