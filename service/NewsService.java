package kr.co.acorn.commit.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.acorn.commit.model.News;
import kr.co.acorn.commit.repository.NewsDao;

@Service
public class NewsService {
	@Autowired
	private NewsDao dao;

	@Autowired
	private News dt;

	public int getData() {
		for (int i = 1; i < 11; i++) {
			// IT Daily(뉴스) 주소 가져오기
			final String url = "http://www.itdaily.kr/news/articleList.html?page=" + i
					+ "&total=90830&box_idxno=&sc_section_code=S1N1&view_type=sm";

			try {
				Document doc1 = Jsoup.connect(url).get(); // 주소에 있는 html 파일 가져다 넣기
				Elements goSiteUrlElements = doc1.getElementsByClass("type2"); // 뉴스 세부 페이지 url 가져오기 위함1
				Elements goUrl1 = goSiteUrlElements.select("a[href]"); // 뉴스 세부 페이지 url 가져오기 위함2
				Elements goUrl2 = goSiteUrlElements.select("img[src]"); // 뉴스 세부 페이지 url 가져오기 위함2
				Elements links = goUrl1.select("a[href]"); // 뉴스 세부 페이지 url 가져오기 위함4
//				Elements links2 = goUrl2.select("img[src]"); // 뉴스 세부 페이지 src 가져오기 위함

				List<String> newsHrefs = new ArrayList<>();
				for (Element link : links) {
					String href = link.attr("href");
					if (!newsHrefs.contains(href)) {
						newsHrefs.add(href);
					}
					// System.out.println(href);
				} // 세부 사이트 url 완료

//				List<String> imgHrefs = new ArrayList<>();
//				for (Element src : links2) {
//					String srcUrl = src.attr("src");
//					if (!imgHrefs.contains(src)) {
//						imgHrefs.add(srcUrl);
////						System.out.println(srcUrl);
//					}
//				} // 세부 사이트 src 완료
//				
				// System.out.println(uniqueHrefs.size());

				for (String href : newsHrefs) {
					try {
						String realHref = "http://www.itdaily.kr" + href;
						Document doc2 = Jsoup.connect(realHref).get();
						String title = doc2.getElementsByClass("heading").text(); // 뉴스 제목
						String subTitle = doc2.getElementsByClass("subheading").text(); // 뉴스 서브 타이틀
						String writer = doc2.select(".infomation li").get(0).text().split(" ")[1]; // 뉴스 작성자

						String originDateStr = doc2.select(".infomation li").get(1).text().split(" ")[1]; // 뉴스 게시일
						String dateFormat = "yyyy-MM-dd"; // 주어진 날짜와 시간 정보의 형식
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
						Timestamp originDate = new Timestamp(sdf.parse(originDateStr).getTime());
						Calendar cal = Calendar.getInstance();
						cal.setTime(originDate);

						Elements content = doc2.select("#article-view-content-div").select("p"); // 뉴스 본문
						String origin = "IT DAILY"; // 뉴스 출처
						String image = doc2.select(".photo-layout img[src]").attr("src"); // 뉴스 세부 페이지 src 가져오기 위함
						// String contents =
						// doc2.select("#article-view-content-div").select("p").text().substring(9); //
						// 뉴스 본문 혹시 모르니까 주석으로 남겨놓을게요

						// insert하는 순간의 시간으로 createDate 설정
						dt.setCreateDate(new Timestamp(System.currentTimeMillis()));
						dt.setTitle(title);
						dt.setSubTitle(subTitle);
						dt.setWriter(writer);
						dt.setOriginDate(originDate);
						dt.setContent(content.toString());
						dt.setOrigin(origin);
						dt.setImage(image);
						dao.insertDataNews(dt);

					} catch (Exception e) {
						e.printStackTrace();
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
