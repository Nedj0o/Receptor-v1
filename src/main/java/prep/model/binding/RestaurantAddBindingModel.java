package prep.model.binding;

import org.hibernate.validator.constraints.Length;

public class RestaurantAddBindingModel {
    private String name;
    private String address;
    private String category;

    public RestaurantAddBindingModel() {
    }

    @Length(min = 2, message = "Name length must be more than two characters!")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 10, message = "Address length must be more than ten characters!")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
