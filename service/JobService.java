package kr.co.acorn.commit.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.acorn.commit.model.Job;
import kr.co.acorn.commit.repository.JobDao;

@Service
public class JobService {
	@Autowired
	private JobDao dao;

	@Autowired
	private Job dt;

	public int getData() {
		for (int i = 1; i < 3; i++) {
			// 인크루트 주소 가져오기
			String url = "https://job.incruit.com/jobdb_list/searchjob.asp?rgn2=14&rgn2=18&rgn2=11&occ1=150&page=" + i;

			try {
				Document doc = Jsoup.connect(url).get(); // 주소에 있는 html 파일 가져다 넣기
				Elements goSiteUrlElements = doc.getElementsByClass("c_col "); // 채용공고 세부 페이지 url

				Elements companyElements = doc.getElementsByClass("cell_first"); // 기업명 div
				Elements titleElements = doc.getElementsByClass("cell_mid"); // 공고 제목, 조건 div
				Elements dateElements = doc.getElementsByClass("cell_last"); // 마감 날짜 div
				Elements linkElements = titleElements.select("a"); // 채용 공고 상세 페이지

				for (int j = 0; j < titleElements.size(); j++) {

					// 회사명
					String companyname = companyElements.get(j).getElementsByClass("cl_top").select("a").text();

					// 공고 제목
					String title = titleElements.get(j).getElementsByClass("cl_top").select("a").text();

					// 조건
					String career = titleElements.get(j).getElementsByClass("cl_md").select("span").get(0).text(); // 경력
					String degree = titleElements.get(j).getElementsByClass("cl_md").select("span").get(1).text(); // 학력
					String location = titleElements.get(j).getElementsByClass("cl_md").select("span").get(2).text(); // 지역

					// 채용공고 세부 페이지 url
					final String image = linkElements.get(j).attr("abs:href");

					// 마감 날짜(String 형식)
					String finishDate_S = dateElements.get(j).getElementsByClass("cl_btm").select("span").get(0).text();

					if (finishDate_S.endsWith("마감")) {
						continue;
					} else {
						if (finishDate_S.equals("채용시") || finishDate_S.equals("상시")) {
							dt.setFinishDate_D(null);
							// 채용시 & 상시 채용은 insert하는 순간의 시간으로 createDate 설정
							dt.setCreateDate(new Timestamp(System.currentTimeMillis()));
							// String 형식의 finishDate
							dt.setFinishDate_S(finishDate_S);
						} else {
							dt.setFinishDate_S(null);
							String stringToDate = finishDate_S.substring(1); // '~' 문자 제거

							SimpleDateFormat originalDateFormat = new SimpleDateFormat("MM.dd");
							try {
								// "yyyy-MM-dd" 형식으로 날짜 변환
								Date parsedDate = originalDateFormat.parse(stringToDate);
								Calendar cal = Calendar.getInstance();
								cal.setTime(parsedDate);
								// 년도 설정
								cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
								Timestamp finishDate_D = new Timestamp(cal.getTimeInMillis());

								// createDate는 finishDate-7일로 설정
								cal.setTimeInMillis(finishDate_D.getTime());
								cal.add(Calendar.DATE, -7);

								Timestamp createDate = new Timestamp(cal.getTime().getTime());

								// 데이터들을 DB에 insert
								dt.setCreateDate(createDate);
								dt.setFinishDate_D(finishDate_D);
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
						// 공통으로 DB에 들어갈 것들
						dt.setCompanyname(companyname);
						dt.setTitle(title);
						dt.setCareer(career);
						dt.setDegree(degree);
						dt.setLocation(location);
						dt.setImage(image);
						dao.insertData(dt);

					}

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// ArrayList<Job> list = (ArrayList<Job>) dao.getDataAll();
		return 0;
	}
}
