package dtos;

import java.io.Serializable;

/**
 *
 * @author TNM
 */
public class ResourceDTO implements Serializable {

    private String id, name, color, category;
    private int roleLevel, quantity, amountAvailable;

    public ResourceDTO() {
    }

    public ResourceDTO(String id, String name, String color, String category, int roleLevel, int quantity, int amountAvailable) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.category = category;
        this.roleLevel = roleLevel;
        this.quantity = quantity;
        this.amountAvailable = amountAvailable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getRoleLevel() {
        return roleLevel;
    }

    public void setRoleLevel(int roleLevel) {
        this.roleLevel = roleLevel;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAmountAvailable() {
        return amountAvailable;
    }

    public void setAmountAvailable(int amountAvailable) {
        this.amountAvailable = amountAvailable;
    }

}
