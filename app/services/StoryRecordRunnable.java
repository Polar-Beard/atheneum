package services;

import daos.StoryDAO;
import daos.StoryRecordDAO;
import daos.UserDAO;
import model.Story;
import model.StoryRecord;
import model.User;

/**
 * Created by Sara on 3/31/2017.
 */
public class StoryRecordRunnable implements Runnable {

    private String userEmail;
    private Story story;
    private UserDAO userDAO;

    public StoryRecordRunnable(String userEmail, Story story){
        this.userEmail = userEmail;
        this.story = story;
        this.userDAO = new UserDAO();
    }

    @Override
    public void run(){
        User user = userDAO.findUserByEmail(userEmail);
        StoryRecord storyRecord = new StoryRecord(user, story);
        storyRecord.setStateFetched(true);
        //StoryRecordDAO.addStoryRecord(storyRecord);
    }
}
