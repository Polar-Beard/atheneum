package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import daos.CategoryDAO;
import daos.StoryDAO;
import model.Category;
import model.Story;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Sara on 3/28/2017.
 */
public class CategoryController extends Controller {

    private static CategoryDAO categoryDAO;
    private static StoryDAO storyDAO;

    private static ObjectMapper objectMapper;

    static{
        categoryDAO = new CategoryDAO();
        storyDAO = new StoryDAO();

        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    //Returns the number of categories specified.
    public Result getCategories(int number){
        List<Category> categories = categoryDAO.getCategories(number);

        Collections.sort(categories, new Comparator<Category>() {
            @Override
            public int compare(Category o1, Category o2) {
                return Long.compare(o1.getOrder(), o2.getOrder());
            }
        });

        for(Category category: categories){
            List<Story> stories = storyDAO.getStoriesByQuery(category.getSearchQuery(), 10);
            category.setStoryList(stories);
        }

        JsonNode jsonNode = objectMapper.valueToTree(categories);
        return ok(jsonNode);
    }




}
