package com.uniyaz.views;

import com.uniyaz.components.CategoryComboBox;
import com.uniyaz.components.ImageUploader;
import com.uniyaz.components.MyButton;
import com.uniyaz.functions.CategoryFunctions;
import com.uniyaz.functions.ContentFunctions;
import com.uniyaz.service.DatabaseService;
import com.uniyaz.domain.*;
import com.uniyaz.ui.LayoutUI;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.server.*;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.io.*;
import java.sql.SQLException;
import java.util.List;

public class ContentLayout extends VerticalLayout {
    CategoryFunctions categoryFunctions;
    ContentFunctions contentFunctions;
    TextField contentName;
    RichTextArea contentText;
    DatabaseService databaseService = new DatabaseService();
    Image image = new Image();

    public ContentLayout() {
        setSizeFull();

        Label welcome = new Label("Welcome");
        welcome.setSizeUndefined();
        welcome.setValue("Welcome");
        addComponent(welcome);
        setComponentAlignment(welcome,Alignment.TOP_CENTER);
    }

    public void ContentLayoutFillBy(String type) {
        removeAllComponents();
        ((LayoutUI) UI.getCurrent()).getMainLayout().getBodyLayout().getSideBarLayout().removeAllComponents();

        switch (type) {

            case ("Add Category"):
                addCategory();
                break;
            case ("Add Content"):
                addContent();
                break;
            case ("List Contents"):
                listContents();
                break;
            case ("List Categories"):
                listCategories();
                break;
        }

    }

    public void addCategory() {

        removeAllComponents();
        categoryFunctions = new CategoryFunctions();
        addComponent(categoryFunctions);
        categoryFunctions.addCategory();

        setComponentAlignment(categoryFunctions, Alignment.MIDDLE_CENTER);
    }

    public void listCategories() {
        removeAllComponents();

        categoryFunctions = new CategoryFunctions();
        addComponent(categoryFunctions);
        categoryFunctions.listCategories();

    }

    public void addContent() {

        removeAllComponents();

        contentFunctions = new ContentFunctions();
        addComponent(contentFunctions);
        contentFunctions.addContent();

        setComponentAlignment(contentFunctions,Alignment.TOP_CENTER);

    }

    public void listContents() {
        removeAllComponents();

        try {
            contentFunctions = new ContentFunctions();
            addComponent(contentFunctions);
            contentFunctions.listContents();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void fillContentsById(String id) {

        removeAllComponents();

        try {

            List<Content> contentArrayList = databaseService.getContents2(id);
            System.out.println(contentArrayList.size());
            int arraySize = contentArrayList.size();
            int verticalCount = (arraySize / 3) + 1;

            for (int iterator = 0; iterator < verticalCount; iterator++) {
                VerticalLayout row = new VerticalLayout();
                HorizontalLayout column = new HorizontalLayout();
                column.setSizeFull();
                row.addComponent(column);
                addComponent(row);
            }

            for (int iterator = 0; iterator < arraySize; iterator++) {
                Content content = contentArrayList.get(iterator);

                VerticalLayout row = (VerticalLayout) getComponent(iterator / 3);
                HorizontalLayout column = (HorizontalLayout) (row.getComponent(0));

                ImageContentLayout imageContentLayout = new ImageContentLayout(content);
                column.addComponent(imageContentLayout);

                Button button = (Button) imageContentLayout.getComponent(1);
                button.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        openContent(clickEvent);
                    }
                });
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void openContent(Button.ClickEvent clickEvent) {

        removeAllComponents();
        setSizeFull();

        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setSizeFull();
        addComponent(contentLayout);

        Label header = new Label();
        header.setStyleName(ValoTheme.LABEL_LARGE);
        header.addStyleName(ValoTheme.LABEL_BOLD);
        header.setContentMode(ContentMode.HTML);
        header.setValue(clickEvent.getButton().getCaption());
        header.setSizeUndefined();
        contentLayout.addComponent(header);

        Label data = new Label();
        data.setContentMode(ContentMode.HTML);
        data.setValue((String) clickEvent.getButton().getData());
        contentLayout.addComponent(data);

        contentLayout.setComponentAlignment(header, Alignment.TOP_CENTER);
        contentLayout.setExpandRatio(header, 0.1f);
        contentLayout.setExpandRatio(data, 0.9f);

        setComponentAlignment(contentLayout, Alignment.MIDDLE_CENTER);
    }
}
