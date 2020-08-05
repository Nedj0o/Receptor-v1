package prep.model.binding;

import org.hibernate.validator.constraints.Length;
import prep.model.entity.CategoryName;

import javax.validation.constraints.NotNull;

public class RecipeAddBindingModel {
    private String name;
    private String description;
    private CategoryName category;

    public RecipeAddBindingModel() {
    }

    @Length(min = 2, message = "Username length must be more than two characters!")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 2, message = "Description length must be more than three characters!")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = "Enter valid category name!")
    public CategoryName getCategory() {
        return category;
    }

    public void setCategory(CategoryName category) {
        this.category = category;
    }

}
