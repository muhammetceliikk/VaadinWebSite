package com.uniyaz.components;

import com.uniyaz.databaseService.DatabaseService;
import com.uniyaz.domain.Category;
import com.uniyaz.ui.LayoutUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SideBarLayout extends VerticalLayout {
    public SideBarLayout() {
        //label.setValue(((LayoutUI) UI.getCurrent()).getBodyLayout().getContentLayout().getContentLabel().getValue());
        setSizeFull();

        addStyleName("layout-with-border");
        buildSideBarLayout();
    }

    public void buildSideBarLayout() {

        removeAllComponents();
        DatabaseService databaseService = new DatabaseService();
        List<Category> categoryList = new ArrayList<Category>();
        try {
            categoryList=databaseService.GetCategories();
            for (Category category : categoryList) {
                MyButton myButton = new MyButton(category.getName());
                myButton.setId(String.valueOf(category.getId()));
                myButton.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        ((LayoutUI) UI.getCurrent()).getMainLayout().getBodyLayout().getContentLayout().showContents(String.valueOf(category.getId()));
                    }
                });
                addComponent(myButton);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
