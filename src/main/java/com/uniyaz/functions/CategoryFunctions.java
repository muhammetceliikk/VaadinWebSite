package com.uniyaz.functions;

import com.uniyaz.components.MyButton;
import com.uniyaz.domain.Category;
import com.uniyaz.service.DatabaseService;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.List;

public class CategoryFunctions extends VerticalLayout {

    DatabaseService databaseService=new DatabaseService();

    @PropertyId("name")
    TextField categoryName;

    MyButton addButton;
    FieldGroup binder;
    BeanItem<Category> categoryBeanItem;

    public CategoryFunctions() {
        buildCategoryLayout();

    }

    public void buildCategoryLayout(){
        removeAllComponents();
        setSizeFull();
    }

    public void addCategory(){
        removeAllComponents();
        setSizeUndefined();

        categoryName = new TextField("Enter category name");
        categoryName.setNullRepresentation("");
        addComponent(categoryName);

        addButton = new MyButton("Add");
        addComponent(addButton);

        categoryBeanItem = new BeanItem<Category>(new Category());
        binder = new FieldGroup(categoryBeanItem);
        binder.bindMemberFields(this);

        addButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                try {
                    binder.commit();
                    databaseService.addCategory(categoryBeanItem.getBean());
                    Notification.show("Category added");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (FieldGroup.CommitException e) {
                    e.printStackTrace();
                }
                addCategory();
            }
        });

        setComponentAlignment(addButton, Alignment.MIDDLE_CENTER);
    }

    public void listCategories(){
        removeAllComponents();

        Table contentTable = new Table();
        addComponent(contentTable);

        contentTable.setSizeFull();
        contentTable.addContainerProperty("ID", Integer.class, null);
        contentTable.addContainerProperty("Category Name", TextField.class, null);
        contentTable.addContainerProperty("Update", MyButton.class, null);
        contentTable.addContainerProperty("Delete", MyButton.class, null);

        try {
            List<Category> categoryList = databaseService.getCategories();
            for (Category category : categoryList) {

                MyButton updateButton = new MyButton();
                updateButton.setIcon(FontAwesome.EDIT);
                MyButton deleteButton = new MyButton();
                deleteButton.setIcon(FontAwesome.REMOVE);

                Object id = contentTable.addItem();
                Item item = contentTable.getItem(id);

                Property categoryID = item.getItemProperty("ID");
                Property category_name = item.getItemProperty("Category Name");
                Property update = item.getItemProperty("Update");
                Property delete = item.getItemProperty("Delete");

                categoryID.setValue(category.getId());
                TextField deneme = new TextField();
                deneme.setValue(category.getName());
                category_name.setValue(deneme);
                update.setValue(updateButton);
                delete.setValue(deleteButton);

                updateButton.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        try {
                            TextField test = (TextField) category_name.getValue();
                            databaseService.updateCategory(category, test);
                            Notification.show("Category updated");
                            listCategories();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });

                deleteButton.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        try {
                            databaseService.deleteCategory(category);
                            Notification.show("Category deleted");
                            listCategories();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
