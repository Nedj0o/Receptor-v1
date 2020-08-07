package prep.model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    private String username;
    private String password;
    private String email;
    private Role role;
    private List<Recipe> recipes;

    public User() {
    }

    public List<Role> authorities() {
        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setRoleName(this.getRole().getRoleName().toString().equals("ADMIN") ?
                RoleName.ADMIN : RoleName.USER);
        roles.add(role);
        return roles;
    }

    @Column(name = "username", nullable = false, unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "email", nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ManyToOne
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

}
