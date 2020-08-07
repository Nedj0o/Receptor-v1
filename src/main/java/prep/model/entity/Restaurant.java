package prep.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="restaurants")
public class Restaurant extends BaseEntity{

    private String name;
    private String address;
    private String category;
    private int stars;

    public Restaurant() {

    }

    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "address", nullable = false, unique = true)
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

    @Column
    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

}
