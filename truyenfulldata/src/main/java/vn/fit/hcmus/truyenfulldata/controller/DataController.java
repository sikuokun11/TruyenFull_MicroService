package vn.fit.hcmus.truyenfulldata.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import thrift.generated.TruyenFullData;
import vn.fit.hcmus.truyenfulldata.model.Category;
import vn.fit.hcmus.truyenfulldata.model.Comic;
import vn.fit.hcmus.truyenfulldata.repository.CategoryRepository;
import vn.fit.hcmus.truyenfulldata.repository.ComicRepository;
import vn.fit.hcmus.truyenfulldata.repository.RedisRepository;
import vn.fit.hcmus.truyenfulldata.utils.ResponUtils;

import java.io.IOException;
import java.util.List;

@Controller
public class DataController implements TruyenFullData.Iface {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ComicRepository comicRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisRepository redisRepository;

//    private RedisTemplate<String,Comic> redisTemplate;
//    private HashOperations hashOperations;







    @Override
    public String getAllComicsOfCategory(String name) throws TException{


        Category category = categoryRepository.findByName(name);
        ObjectMapper mapper = new ObjectMapper();

        String json = null;
        try {
             json = mapper.writeValueAsString(ResponUtils.returnCategory(category));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public String getAllChapterOfAComic(String name) throws TException {
        Comic comic = comicRepository.findByName(name);
        ObjectMapper mapper = new ObjectMapper();

        String json = null;
        try {
            json = mapper.writeValueAsString(ResponUtils.returnComic(comic));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    //HAM DE TEST XEM CO LAY COMIC TU DATABASE LEN DC K , kKHONG PHAI CATEGORY, DAY CHI DE TEST
    // TEST CHUC NANG BACK UP ALL COMIC VAO REDIS
    /**
     *
     *  HAM BACKUP ALL DU LIEU TRUYEN COMIC VAO REDIS
     *  CHUA DAT LAI TEN ^^!
     * */
    @Override
    public String getAllCategoryInDatabase() throws TException {

        List<Comic> comics = comicRepository.findAll();
        for(Comic comic : comics){
            //List<Chapter> chapters = comic.getChapterList();

                redisTemplate.opsForHash().putIfAbsent("Comic", comic.getId(),comic.getChapterList() );
                // tao 1 hash khac co id la id cua truyen do
            // nho lam



        }
        return "GET TRUYEN TU DATABASE LUU VAO REDIS";
    }


    // TEST THU LAY TRUYEN CO ID = 3 TREN REDIS
    @Override
    public String getAllComicOfDatabase() throws TException {

        //String b = "20";

        //List<Chapter> chapters = new ArrayList(redisRepository.findAllStory().values());

         //return   redisTemplate.opsForHash().values("Comic").toString();
       //return redisRepository.findStory(Integer.parseInt(b));
       // List<Comic> comics = new ArrayList(redisRepository.findAllStory().values());
        //return redisRepository.findStory(Integer.parseInt("3"));
        return  redisRepository.findAllStory().toString();

    }

    @Override
    public String getComicWithHighestRate() throws TException {

//          CACH TIM TRUYEN HIGHEST RATETING = Thuat toan tim Max
//        List <Comic> comics = comicRepository.findAll();
//        float max = comics.get(0).getRate();
//        String res="";
//       for (int i=1;i<comics.size();i++)
//       {
//           if(comics.get(i).getRate()>=max)
//           {
//               max = comics.get(i).getRate();
//           }
//       }
//       for (Comic comic:comics){
//           if(max == comic.getRate()){
//               res= comic.getName();
//           }
//       }
////        System.out.println(res);
//       Comic comic1 = comicRepository.findByName(res);
//       String temp = ResponUtils.returnComic(comic1).toString();
//       return temp;

//   CACH 2:     lam bang Query
        List<Comic> comics = comicRepository.findByTop1();
        String temp = ResponUtils.returnListComic(comics).toString();
        return temp;
    }

    @Override
    public String getAllComicOfAuthor(String name) throws TException {
        List<Comic> comics = comicRepository.findByAuthor(name);
        String temp = ResponUtils.returnListComic(comics).toString();
        return temp;

    }

    @Override
    public String getAllComicByStatus(String name) throws TException {
        List<Comic> comics = comicRepository.findByStatus(name);
        String temp = ResponUtils.returnListComic(comics).toString();
        return temp;
    }

    @Override
    public int countNumberOfComic() throws TException {
                // TIM TONG SO TRUYEN CO TRONG CSDL
        List<Comic> comics = comicRepository.findAll();
        Integer sum = comics.size();
        return sum;
    }

    @Override
    public String insertComic(String author, String name, String rate, String source, String status, String urlname) throws TException {

        Float rating = Float.valueOf(rate);
        comicRepository.insertComic(author,name, rating ,source,status,urlname);


        Comic comic = comicRepository.findByName(name);
        redisTemplate.opsForHash().putIfAbsent("Comic",comic.getId(),comic);
        return "SUCCESSFULLY INSERT";
    }


    @Override
    public String deleteComic(String name) throws TException {
                // XOA TRUYEN TRONG CO SO DU LIEU
        comicRepository.deleteComicByName(name);
        return "SUCCESSFULLY DELETE";
    }

    @Override
    public String updateComic(String oldname, String newname) throws TException {
        //UPDATE TEN TRUYEN
        comicRepository.updateComicName(oldname,newname);
        return "SUCCESSFULLY UPDATE";
    }


    public Object findStory(int id) {
        return redisTemplate.opsForHash().get("Comic", id);
    }



}
