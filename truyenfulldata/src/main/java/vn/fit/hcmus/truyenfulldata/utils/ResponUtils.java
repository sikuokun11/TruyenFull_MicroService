package vn.fit.hcmus.truyenfulldata.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import vn.fit.hcmus.truyenfulldata.model.Category;
import vn.fit.hcmus.truyenfulldata.model.Chapter;
import vn.fit.hcmus.truyenfulldata.model.Comic;


import java.util.List;

public class ResponUtils {
    private static ObjectMapper mapper = new ObjectMapper();

    //    Ham tra ve mot Chapter
    public static ObjectNode returnChapter(Chapter chapter){
        ObjectNode node = mapper.createObjectNode();
        node.put("chapter",chapter.getIndex());
        node.put("name",chapter.getName());
        node.put("content",chapter.getContent());
        return node;
    }

    //    Ham tra ve mot Chapter ma khong bao gom content
    public static ObjectNode returnChapterWithoutContent(Chapter chapter){
        ObjectNode node = mapper.createObjectNode();
        node.put("chapter",chapter.getIndex());
        node.put("name",chapter.getName());
        //node.put("content",chapter.getContent());
        return node;
    }


    //    Ham tra ve mot List<Chapter>
    public static ArrayNode returnListChapter(List<Chapter> chapters){
        ArrayNode nodes = mapper.createArrayNode();
        for (Chapter chapter : chapters) {
            nodes.add((returnChapter(chapter)));
        }
        return nodes;
    }

    //    Ham tra ve mot List<Chapter> voi Chapter khong bao gom Content
    public static ArrayNode returnListChapterWithoutContent(List<Chapter> chapters){
        ArrayNode nodes = mapper.createArrayNode();
        for (Chapter chapter : chapters) {
            nodes.add((returnChapterWithoutContent(chapter)));
        }
        return nodes;
    }

    //    Ham tra ve mot Comic
    public static ObjectNode returnComic(Comic comic){
        ObjectNode node = mapper.createObjectNode();
        node.put("id",comic.getId());
        node.put("name",comic.getName());
        node.put("author",comic.getAuthor());
        node.put("source",comic.getSource());
        node.put("status",comic.getStatus());
        node.set("chapter-list",returnListChapterWithoutContent(comic.getChapterList()));
//        node.put("category-list",returnListNameCategory(comic.getCategoryList()));
        return node;
    }

    //    Hàm trả về một name của một Comic
    public static ObjectNode returnNameComic(Comic comic){
        ObjectNode node = mapper.createObjectNode();
        node.put("id",comic.getId());
        node.put("name",comic.getName());
        return node;
    }

    //    Ham tra ve mot List<Comic>
    public static ArrayNode returnListComic(List<Comic> comics){
        ArrayNode nodes = mapper.createArrayNode();
        for (Comic comic : comics) {
            nodes.add((returnComic(comic)));
        }
        return nodes;
    }

    //    Ham tra ve mot List Name <Comic>
    public static ArrayNode returnListNameComic(List<Comic> comics){
        ArrayNode nodes = mapper.createArrayNode();
        for (Comic comic : comics) {
            nodes.add((returnNameComic(comic)));
        }
        return nodes;
    }

    //    Hàm trả về một Category
    public static ObjectNode returnCategory(Category category){
        ObjectNode node = mapper.createObjectNode();
        node.put("id",category.getId());
        node.put("name",category.getName());
        node.set("comic-list",returnListNameComic(category.getComicList()));
        return node;
    }

    //    Hàm trả về một name của một Comic
    public static ObjectNode returnNameCategory(Category category){
        ObjectNode node = mapper.createObjectNode();
        node.put("id",category.getId());
        node.put("name",category.getName());
        return node;
    }

    //    Ham tra ve mot List<Category>
    public static ArrayNode returnListCategory(List<Category> categories){
        ArrayNode nodes = mapper.createArrayNode();
        for (Category category : categories) {
            nodes.add((returnCategory(category)));
        }
        return nodes;
    }

    //    Ham tra ve mot List Name <Comic>
    public static ArrayNode returnListNameCategory(List<Category> categories){
        ArrayNode nodes = mapper.createArrayNode();
        for (Category category : categories) {
            nodes.add((returnNameCategory(category)));
        }
        return nodes;
    }


}