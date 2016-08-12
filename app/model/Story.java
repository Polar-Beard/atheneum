package model;

@Entity
public class Story {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private long storyId;

    private String title;
    private String description;
    private String author;
    private int viewCount;

    public Story(String title, String description, String author){
        this.title  = title;
        this.description = description;
        this.author = author;
        this.viewCount = 0;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public String getAuthor(){
        return author;
    }

    public int getViewCount(){
        return viewCount;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public void setViewCount(int viewCount){
        this.viewCount = viewCount;
    }
}