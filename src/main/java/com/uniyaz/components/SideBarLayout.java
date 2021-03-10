package com.uniyaz.components;

import com.uniyaz.databaseService.DatabaseService;
import com.uniyaz.domain.Category;
import com.uniyaz.ui.LayoutUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.SQLException;
import java.util.List;

public class SideBarLayout extends VerticalLayout {
    VerticalLayout insideBar;
    public SideBarLayout() {
        //label.setValue(((LayoutUI) UI.getCurrent()).getBodyLayout().getContentLayout().getContentLabel().getValue());
        setSizeFull();
        addStyleName("layout-with-border");
        buildSideBarLayout();
    }

    public void buildSideBarLayout() {
        removeAllComponents();

        insideBar = new VerticalLayout();
        addComponent(insideBar);

        DatabaseService databaseService = new DatabaseService();
        List<Category> categoryList;
        try {
            categoryList=databaseService.getCategories();
            for (Category category : categoryList) {
                MyButton myButton = new MyButton(category.getName());
                myButton.setId(String.valueOf(category.getId()));
                myButton.setStyleName(ValoTheme.BUTTON_LINK);
                myButton.addStyleName(ValoTheme.BUTTON_LARGE);
                myButton.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        ((LayoutUI) UI.getCurrent()).getMainLayout().getBodyLayout().getContentLayout().fillContentsById(String.valueOf(category.getId()));
                    }
                });
                insideBar.addComponent(myButton);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        setComponentAlignment(insideBar, Alignment.MIDDLE_CENTER);
    }
}
