package vn.fit.hcmus.truyenfull_restapi.controller;

import org.apache.thrift.TException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import thrift.generated.DataException;
import thrift.generated.TruyenFullService;
import vn.fit.hcmus.truyenfull_restapi.model.Category;
import vn.fit.hcmus.truyenfull_restapi.model.Chapter;
import vn.fit.hcmus.truyenfull_restapi.model.Comic;
import vn.fit.hcmus.truyenfull_restapi.repository.CategoryRepository;
import vn.fit.hcmus.truyenfull_restapi.repository.ChapterRepository;
import vn.fit.hcmus.truyenfull_restapi.repository.ComicRepositiory;
import vn.fit.hcmus.truyenfull_restapi.selector.TruyenFullChapterSelector;
import vn.fit.hcmus.truyenfull_restapi.selector.TruyenFullComicSelector;
import vn.fit.hcmus.truyenfull_restapi.selector.TruyenFullSelector;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class CrawlerController implements TruyenFullService.Iface {


    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ComicRepositiory comicRepsitory;

    @Autowired
    ChapterRepository chapterRepository;

    /* CRAWL THE LOAI */
    @GetMapping("/crawlCategory")
    public Set<Category> getListCategory() throws IOException {
//        Chuyển List sang Set.
        Set<Category> categories = categoryRepository.findAll().stream().collect(Collectors.toSet());
        Document document = Jsoup.connect("https://truyenfull.vn/").get();
        Elements elements = document.select("div.row > div.col-md-4 > ul > li > a");
        for (Element element : elements) {
            Category newCategory = new Category();
            String[] temp = (element.attr("href").split("/"));
            String name = element.text();
            String urlName = temp[temp.length - 1];
            newCategory.setName(name);
            newCategory.setUrlname(urlName);
            categories.add(newCategory);
        }

        categoryRepository.saveAll(categories);
        return categories;
    }


    // Num truyen vao chi de test request , chua thuc hien xu ly
    @Override
    public boolean crawTruyen(int num) throws DataException, TException, IOException {
        TruyenFullSelector selector = new TruyenFullSelector();
        String urlComic;
        boolean hasNext = false;
        String urlNextPage = selector.mainUrl();
        do{
            System.out.println("Truyen hot Page: ");
            Document document = Jsoup.connect(urlNextPage).get();
            Elements elements = document.select(selector.getComicListSelector());
            Elements elements1 = document.select(selector.getCurrChapterOfComic());
            int i=0;
            for (Element element : elements) {
                System.out.println("Tên truyện: " + element.text());
                urlComic = element.attr("href");
                System.out.println("URL COMIC: "+urlComic);

                //SAI selector
                System.out.println("URLCURRCHAPTER: "+elements1.get(i).attr("href"));
                crawlComic(urlComic,elements1.get(i).attr("href"));
                System.out.println("Hoàn thành: "+element.text());
                System.out.println(" ");
                i++;
            }

            Element nextPageButton = document.selectFirst(selector.getPageSelector().getNextComicPageSelector());
            if(nextPageButton !=null) {
                if (!nextPageButton.attr("href").equals("javascript:void(0)")) {
                    hasNext = true;
                    urlNextPage = nextPageButton.attr("href");
                    System.out.println("Truyen hot link page:" + urlNextPage);
                } else {
                    hasNext = false;
                }
            }
            else{ // Trường hợp tất cả chapter chỉ có 1 page
                hasNext = false;
            }
        } while (hasNext);
        return true;
    }

    // CAO TRUYEN HOT
    @GetMapping("/crawlerComics")
    public boolean crawlTruyenHot() throws IOException {
        TruyenFullSelector selector = new TruyenFullSelector();
        String urlComic;
        boolean hasNext = false;
        String urlNextPage = selector.mainUrl();
        do{

            System.out.println("Truyen hot Page: ");
            Document document = Jsoup.connect(urlNextPage).get();
            Elements elements = document.select(selector.getComicListSelector());
            Elements elements1 = document.select(selector.getCurrChapterOfComic());

            int i=0;
            for (Element element : elements) {
                System.out.println("Tên truyện: " + element.text());
                urlComic = element.attr("href");
                System.out.println("URL COMIC: "+urlComic);

                //FIX BUG SAI SELECTOR
                System.out.println("URLCURRCHAPTER: "+elements1.get(i).attr("href"));
                crawlComic(urlComic,elements1.get(i).attr("href"));
                System.out.println("Hoàn thành: "+element.text());
                System.out.println(" ");
                i++;
            }

            Element nextPageButton = document.selectFirst(selector.getPageSelector().getNextComicPageSelector());
            if(nextPageButton !=null) {
                if (!nextPageButton.attr("href").equals("javascript:void(0)")) {
                    hasNext = true;
                    urlNextPage = nextPageButton.attr("href");
                    System.out.println("Truyen hot link page:" + urlNextPage);
                } else {
                    hasNext = false;
                }
            }
            else{ // Trường hợp tất cả chapter chỉ có 1 page
                hasNext = false;
            }

        } while (hasNext);
        return true;
    }

    public boolean crawlComic(String urlComic,String urlCurrChapter) throws IOException {
        TruyenFullComicSelector selector = TruyenFullComicSelector.getInstance();
        String url = urlComic;
        System.out.println("URLCURRCHAPTER:  "+urlCurrChapter);
        String[] temp = urlComic.split("/");
        String urlName = temp[temp.length - 1];
//        Nếu comic đã được crawl rồi
        if(comicRepsitory.findByUrlname(urlName) != null ) {
            // Và cũng đã crawl hết chapter thì không crawl nữa
            if(!isCrawledChapter(urlCurrChapter)) {
                crawlChapterOfCommic(url,comicRepsitory.findByUrlname(urlName));
                return true;
            }
            // Ngược lại nếu còn chapter chưa crawl thì crawl chapters và add vào comic đó.
            else{

                System.out.println("Đã crawl truyện "+urlName);

                return true;
            }
        }

//        Các trường hợp còn lại thì crawl bình thường, chapter được crawl rồi thì bỏ qua.
        Document document = Jsoup.connect(url).get();
        Comic crawledComic = new Comic();
//        Set các field cho comic, trừ chapterlist
        crawledComic.setName(document.selectFirst(selector.title()).text());
        crawledComic.setUrlname(urlName);
        Element element = document.selectFirst(selector.rate());
        float rate = Float.parseFloat(element.text());
        //System.out.println("SCORE: "+rate);
        crawledComic.setRate(rate);
        crawledComic.setAuthor(document.selectFirst(selector.author()).text());
        if(document.selectFirst(selector.doneFlag()) == null){
            crawledComic.setStatus("Đang ra");
        }
        else {
            crawledComic.setStatus(document.selectFirst(selector.doneFlag()).text());
        }
        String source = document.selectFirst(selector.dataFrom()) != null
                ? document.selectFirst(selector.dataFrom()).text()
                : "null";
        crawledComic.setSource(source);
//      Set các thể loại cho comic
        Elements genres = document.select(selector.category());
        for (Element genre : genres) {
            Category category =  categoryRepository.findByName(genre.attr("title"));
            //category.getComicList().add(crawledComic);
            crawledComic.getCategoryList().add(category);
        }
        comicRepsitory.save(crawledComic);
        crawlChapterOfCommic(url,crawledComic);
        return true;
    }
//    Hàm kiểm tra chapter đã được crawl chưa thông qua tên chapter
    public boolean isCrawledChapter(String urlChapter) throws IOException {
        Document document = Jsoup.connect(urlChapter).get();
       System.out.println("TEST: "+urlChapter);
        TruyenFullChapterSelector chapterSelector = TruyenFullChapterSelector.getInstance();
        Element chapter_title = document.selectFirst(chapterSelector.name_index());
        if(chapter_title == null)
            return true;
        String title = "";
        title = chapter_title.attr("title");
        int index = title.indexOf(":");
//        Set các field cho chapter
        if(chapterRepository.findByName(chapter_title.text()) != null){
           // System.out.println("XEM CAI FILE BY NAME: "+title.substring(index+1));
           // System.out.println("TRUYEN DA CRAWL CHAPTER NAY: ");
            return true;}
            //System.out.println(" CHUA CRAWL CHAPTER");
            return false;
    }
    public boolean isCrawledChapter2(String urlChapter) throws IOException {
        Document document = Jsoup.connect(urlChapter).get();
        // System.out.println("TEST: "+urlChapter);
        TruyenFullChapterSelector chapterSelector = TruyenFullChapterSelector.getInstance();
        Element chapter_title = document.selectFirst(chapterSelector.name_index());

        String title = "";
        if(chapter_title==null){
            return true;
        }
        title = chapter_title.attr("title");

        int index = title.indexOf(":");
//        Set các field cho chapter
        if(chapterRepository.findByName(chapter_title.text())!=null){
            // System.out.println("XEM CAI FILE BY NAME: "+title.substring(index+1));
            // System.out.println("TRUYEN DA CRAWL CHAPTER NAY: ");
            return true;}
        else {
            //System.out.println(" CHUA CRAWL CHAPTER");
            return false;
        }
    }

    public void crawlChapterOfCommic(String commicUrl,Comic comic) throws IOException {
        boolean hasNext = false;
        TruyenFullComicSelector comicSelector = TruyenFullComicSelector.getInstance();
        String urlChapter;
        int indexChapter = 1;
        do {
            Document document = Jsoup.connect(commicUrl).get();
            Elements elements = document.select(comicSelector.getChapterList());

            for (Element element : elements) {
                urlChapter = element.attr("href");
                System.out.println(urlChapter);
//                Nếu chapter đã được crawled rồi thì pass
                if(isCrawledChapter2(element.attr("href"))) {
                    System.out.println("Đã crawl chapter "+element.attr("href"));
                    continue;
                }
                crawlChapterDetails(urlChapter,comic,indexChapter);
                indexChapter++;
            }
            Element nextPageButton = document.select(comicSelector.getNextChapterPageSelector()).first();
            if(nextPageButton !=null) {
                if (!nextPageButton.attr("href").equals("javascript:void(0)")) {
                    hasNext = true;
                commicUrl = nextPageButton.attr("href");
                System.out.println("Chapter link:" + commicUrl);
                } else {
                    hasNext = false;
                }
            }
            else{ // Trường hợp tất cả chapter chỉ có 1 page
                hasNext = false;
            }
        } while (hasNext);
    }

    public boolean crawlChapterDetails(String urlChapter,Comic comic,int chapterIndex) throws IOException {
        TruyenFullChapterSelector chapterSelector = TruyenFullChapterSelector.getInstance();
        Document document = Jsoup.connect(urlChapter).get();
        Chapter crawledChapter = new Chapter();
        Element chapter_title = document.selectFirst(chapterSelector.name_index());
        String title = "";
        if(chapter_title.attr("title").equals(null))
            System.out.println("Title chapter is null");
        else {
             title = chapter_title.attr("title");
        }
        int index = title.indexOf(":");
//        Set các field cho chapter
        crawledChapter.setName(chapter_title.text());
        crawledChapter.setContent(document.selectFirst(chapterSelector.content()).text());

        crawledChapter.setIndex(new Long(chapterIndex));
        comic.addChapter(crawledChapter);
        chapterRepository.save(crawledChapter);
        return true;
    }
}
