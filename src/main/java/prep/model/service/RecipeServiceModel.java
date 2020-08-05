package prep.model.service;

public class RecipeServiceModel extends BaseServiceModel{
    private String name;
    private String description;
    private CategoryServiceModel category;
    private UserServiceModel commiter;
    private int likes;

    public RecipeServiceModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryServiceModel getCategory() {
        return category;
    }

    public void setCategory(CategoryServiceModel category) {
        this.category = category;
    }

    public UserServiceModel getCommiter() {
        return commiter;
    }

    public void setCommiter(UserServiceModel commiter) {
        this.commiter = commiter;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}


