package com.uniyaz.components;

import com.uniyaz.service.DatabaseService;
import com.uniyaz.domain.Category;
import com.vaadin.ui.ComboBox;

import java.sql.SQLException;
import java.util.List;

public class CategoryComboBox extends ComboBox {
    DatabaseService databaseService = new DatabaseService();
    ComboBox comboBox;

    public CategoryComboBox(String caption){
        this.comboBox=sendCategoryComboBox(caption);
    }

    public ComboBox sendCategoryComboBox(String caption){
        ComboBox categoryComboBox = new ComboBox();
        categoryComboBox.setCaption(caption);

        try {
            List<Category> categoryList = databaseService.getCategories();
            int count = categoryList.size();
            for (int i = 0; i < count; i++) {
                Category category = categoryList.get(i);
                categoryComboBox.addItem(category);
                categoryComboBox.setId(String.valueOf(category.getId()));
                categoryComboBox.setItemCaption(category, category.getName());
            }
            categoryComboBox.setNullSelectionAllowed(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return categoryComboBox;
    }

    public ComboBox getComboBox() {
        return comboBox;
    }

    public void setComboBox(ComboBox comboBox) {
        this.comboBox = comboBox;
    }

}
