package vn.fit.hcmus.truyenfull_api.controller;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import vn.fit.hcmus.truyenfull_api.config.TruyenFullClientCrawler;
import vn.fit.hcmus.truyenfull_api.config.TruyenFullDataClient;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class TruyenfullApiController {
    @Autowired
    TruyenFullClientCrawler clientCrawler;

    @Autowired
    TruyenFullDataClient clientData;

    @GetMapping("/crawler")
    public String crawlerComics(HttpServletRequest request) throws IOException, TException, TTransportException {
        int num =Integer.parseInt(request.getParameter( "num"));
        if (clientCrawler.crawTruyen(num)){
            return "SUCCESSFULL CRAWLING "+num+" COMICS";
        }
        return "FAIL TO CRAWL";
    }

    @GetMapping(value = "/Comicname", produces = "application/json")
    public String Comicname(HttpServletRequest request) throws TException {
        String name = request.getParameter("name");
        if(StringUtils.isEmpty(name)){
            return "Fail to find";
        }
        else
        {
            return clientData.getAllComicsOfCategory(name);
        }
    }

    @GetMapping(value = "/findcomic", produces = "application/json")
    public String FindComic(HttpServletRequest request) throws TException {
        String name = request.getParameter("name");
        if(StringUtils.isEmpty(name)){
            return "Fail to find";
        }
        else
        {
            return clientData.getAllChapterOfAComic(name);
        }
    }
    // tim truyen tren redis
    @GetMapping(value = "/findComicRedis")
    public String FindComicRedis() throws TException {
        return clientData.getAllComicOfDatabase();
    }


    //tim truyen co rating cao nhat
    @GetMapping(value = "/HighestRating",produces = "application/json")
    public String HighestRating(HttpServletRequest request) throws TException{
            return clientData.getComicWithHighestRate();
    }
    // tim truyen theo tac gia
    @GetMapping(value = "/findComicByAuthor",produces = "application/json")
    public String FindComicByAuthor(HttpServletRequest request) throws TException{
        String name = request.getParameter("name");
        return clientData.getAllComicOfAuthor(name );
    }

    // tim truyen theo trang thai
    @GetMapping(value = "/findComicByStatus",produces = "application/json")
    public String FindComicByStatus(HttpServletRequest request) throws TException{
        String name = request.getParameter("name");

        return clientData.getAllComicByStatus(name );
    }

    @PostMapping(value = "/insertComic",produces = "application/json")
    public String inSertComic(HttpServletRequest request) throws TException{
        String author = request.getParameter("author");
        String name = request.getParameter("name");
        String rate = request.getParameter("rate");
        String source = request.getParameter("source");
        String status = request.getParameter("status");
        String urlname = request.getParameter("urlname");

        clientData.insertComic(author,name,rate,source,status,urlname);
        return "CHECK YOUR COMIC IN DATABASE";

    }

    @PostMapping(value = "/updateComic",produces = "application/json")
    public String updateComic(HttpServletRequest request) throws TException {
        String oldName = request.getParameter("oldName");
        String newName = request.getParameter("newName");
        clientData.updateComic(oldName,newName);
        return "CHECK YOUR COMIC INDATABASE";
    }

    // xoa truyen
    @DeleteMapping(value = "/deleteComic",produces = "application/json")
    public String deletComic(HttpServletRequest request) throws TException {
        String name = request.getParameter("name");
        clientData.deleteComic(name);
        return "CHECK DATABASE";
    }

    @GetMapping(value = "/insertdbtoredis",produces = "application/json")
    public String InsertDbToRedis()throws TException {
        clientData.getAllCategoryInDatabase();
        return "CHECK REDIS";
    }


}
