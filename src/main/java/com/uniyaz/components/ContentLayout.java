package com.uniyaz.components;

import com.uniyaz.databaseService.DatabaseService;
import com.uniyaz.domain.Category;
import com.uniyaz.domain.Content;
import com.uniyaz.ui.LayoutUI;
import com.vaadin.data.Property;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.List;

public class ContentLayout extends VerticalLayout {
    TextField categoryName, categoryID, contentID, contentName;
    RichTextArea contentText;
    DatabaseService databaseService = new DatabaseService();
    Content tempContent;
    ComboBox categoryComboBox, contentComboBox;
    Grid grid;

    public ContentLayout() {
        setSizeFull();

        // Create a grid
        grid = new Grid();
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.addColumn("id", Integer.class);
        grid.addColumn("name", String.class);
        grid.addColumn("data", String.class);
        addComponent(grid);
    }


    public void ContentLayoutFillBy(String type) {
        setSizeFull();
        removeAllComponents();
        ((LayoutUI) UI.getCurrent()).getMainLayout().getBodyLayout().getSideBarLayout().removeAllComponents();

        switch (type) {

            case ("Add Category"):
                addCategory();
                break;
            case ("Delete Category"):
                deleteCategory();
                break;
            case ("Add Content"):
                addContent();
                break;
            case ("Delete Content"):
                deleteContent();
                break;
        }

    }

    public void addCategory() {

        removeAllComponents();
        MyButton saveButton = new MyButton("Save");

        categoryName = new TextField("Enter category name");
        addComponent(categoryName);

        saveButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                try {
                    databaseService.addCategory(categoryName.getValue());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                addCategory();
            }
        });
        addComponent(saveButton);
    }

    public void deleteCategory() {

        removeAllComponents();
        MyButton deleteButton = new MyButton("Delete");
        ComboBox categoryComboBox = getCategoryComboBox("Categories");
        addComponent(categoryComboBox);

        categoryComboBox.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                Category temp = (Category) valueChangeEvent.getProperty().getValue();

                deleteButton.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        try {
                            databaseService.deleteCategory(String.valueOf(temp.getId()));
                            deleteCategory();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        deleteCategory();
                    }
                });
            }
        });
        addComponent(deleteButton);
    }

    public void addContent() {
        removeAllComponents();
        MyButton saveButton = new MyButton("Save");
        ComboBox categoryComboBox = getCategoryComboBox("Select page to add content");
        addComponent(categoryComboBox);

        contentName = new TextField("Enter content name");
        addComponent(contentName);

        contentText = new RichTextArea();
        addComponent(contentText);

        addComponent(saveButton);

        categoryComboBox.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                Category temp = (Category) valueChangeEvent.getProperty().getValue();

                saveButton.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        try {
                            databaseService.addContent(String.valueOf(temp.getId()), contentName.getValue(), contentText.getValue());
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        addContent();
                    }
                });
            }
        });

    }

    public void deleteContent() {
        removeAllComponents();
        MyButton deleteButton = new MyButton("Delete");
        categoryComboBox = getCategoryComboBox("Select page to delete content");
        addComponent(categoryComboBox);

        categoryComboBox.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                if (getComponentCount() > 2) {
                    removeComponent(getComponent(2));
                    removeComponent(getComponent(1));
                }
                if (getComponentCount() > 1) {
                    removeComponent(getComponent(1));
                }
                Category temp = (Category) valueChangeEvent.getProperty().getValue();
                ComboBox contentComboBox = getContentComboBox("Select content to delete", String.valueOf(temp.getId()));
                addComponent(contentComboBox);

                contentComboBox.addValueChangeListener(new Property.ValueChangeListener() {
                    @Override
                    public void valueChange(Property.ValueChangeEvent contentChangeEvent) {
                        tempContent = (Content) contentChangeEvent.getProperty().getValue();
                        addComponent(deleteButton);
                    }
                });
            }
        });

        deleteButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                try {
                    databaseService.deleteContent(String.valueOf(tempContent.getId()));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                deleteContent();
            }
        });

    }

    public ComboBox getCategoryComboBox(String caption) {

        ComboBox categoryComboBox = new ComboBox();
        categoryComboBox.setCaption(caption);
        try {
            List<Category> categoryList = databaseService.getCategories();
            int count = categoryList.size();
            for (int i = 0; i < count; i++) {
                Category c = categoryList.get(i);
                categoryComboBox.addItem(c);
                categoryComboBox.setId(String.valueOf(c.getId()));
                categoryComboBox.setItemCaption(c, c.getName());
            }
            categoryComboBox.setNullSelectionAllowed(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return categoryComboBox;
    }

    public ComboBox getContentComboBox(String caption, String id) {

        ComboBox contentComboBox = new ComboBox();
        contentComboBox.setCaption(caption);
        try {
            List<Content> contentArrayList = databaseService.getContents(id);
            int count = contentArrayList.size();
            for (int i = 0; i < count; i++) {
                Content c = contentArrayList.get(i);
                contentComboBox.addItem(c);
                contentComboBox.setId(String.valueOf(c.getId()));
                contentComboBox.setItemCaption(c, c.getName());
            }
            contentComboBox.setNullSelectionAllowed(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return contentComboBox;
    }

    public void showContents(String id) {
        grid.getContainerDataSource().removeAllItems();
        try {
            List<Content> contentArrayList = databaseService.getContents(id);
            for (Content content : contentArrayList) {
                grid.addRow(content.getId(), content.getName(), content.getData());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
