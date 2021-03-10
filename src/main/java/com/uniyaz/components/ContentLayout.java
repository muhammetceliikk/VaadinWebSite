package com.uniyaz.components;

import com.uniyaz.databaseService.DatabaseService;
import com.uniyaz.domain.Category;
import com.uniyaz.domain.Content;
import com.uniyaz.ui.LayoutUI;
import com.vaadin.client.ui.Icon;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.event.ContextClickEvent;
import com.vaadin.event.LayoutEvents;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.SQLException;
import java.util.List;

public class ContentLayout extends VerticalLayout {
    TextField categoryName, contentName;
    RichTextArea contentText;
    DatabaseService databaseService = new DatabaseService();
    Content selectedContent;
    ComboBox categoryComboBox;

    public ContentLayout() {
        setSizeFull();

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
        VerticalLayout categoryLayout = new VerticalLayout();
        categoryLayout.setSizeUndefined();
        addComponent(categoryLayout);

        categoryName = new TextField("Enter category name");
        categoryLayout.addComponent(categoryName);

        MyButton addButton = new MyButton("Add");
        categoryLayout.addComponent(addButton);

        addButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                try {
                    Category category = new Category();
                    category.setName(categoryName.getValue());
                    databaseService.addCategory(category);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                addCategory();
            }
        });

        categoryLayout.setComponentAlignment(addButton, Alignment.MIDDLE_CENTER);
        setComponentAlignment(categoryLayout, Alignment.MIDDLE_CENTER);
    }

    public void deleteCategory() {

        removeAllComponents();
        MyButton deleteButton = new MyButton("Delete");

        ComboBox categoryComboBox = getCategoryComboBox("Categories");
        addComponent(categoryComboBox);

        categoryComboBox.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                Category selectedCategory = (Category) valueChangeEvent.getProperty().getValue();

                deleteButton.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        try {
                            databaseService.deleteCategory(selectedCategory);
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

        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setSizeUndefined();
        addComponent(contentLayout);

        ComboBox categoryComboBox = getCategoryComboBox("Select page to add content");
        contentLayout.addComponent(categoryComboBox);

        contentName = new TextField("Enter content name");
        contentLayout.addComponent(contentName);

        contentText = new RichTextArea();
        contentLayout.addComponent(contentText);

        MyButton addButton = new MyButton("Add");
        contentLayout.addComponent(addButton);

        categoryComboBox.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                Category selectedCategory = (Category) valueChangeEvent.getProperty().getValue();

                addButton.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        try {
                            Content content = new Content();
                            content.setName(contentName.getValue());
                            content.setData(contentText.getValue());
                            databaseService.addContent(selectedCategory, content);
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

        contentLayout.setComponentAlignment(addButton, Alignment.MIDDLE_RIGHT);
        setComponentAlignment(contentLayout, Alignment.MIDDLE_CENTER);
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
                } else if (getComponentCount() > 1) {
                    removeComponent(getComponent(1));
                }

                Category selectedCategory = (Category) valueChangeEvent.getProperty().getValue();

                ComboBox contentComboBox = getContentComboBox("Select content to delete", String.valueOf(selectedCategory.getId()));
                addComponent(contentComboBox);

                contentComboBox.addValueChangeListener(new Property.ValueChangeListener() {
                    @Override
                    public void valueChange(Property.ValueChangeEvent contentChangeEvent) {
                        selectedContent = (Content) contentChangeEvent.getProperty().getValue();
                        addComponent(deleteButton);
                    }
                });
            }
        });

        deleteButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                try {
                    databaseService.deleteContent(selectedContent);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                deleteContent();
            }
        });

    }

    public void listCategories() {
        removeAllComponents();

        Table contentTable = new Table();
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
        addComponent(contentTable);
    }

    public void listContents() {
        removeAllComponents();

        Table contentTable = new Table();
        contentTable.setSizeFull();
        contentTable.addContainerProperty("Category ID", Integer.class, null);
        contentTable.addContainerProperty("Category name", String.class, null);
        contentTable.addContainerProperty("Content ID", Integer.class, null);
        contentTable.addContainerProperty("Content name", String.class, null);
        contentTable.addContainerProperty("Update", MyButton.class, null);
        contentTable.addContainerProperty("Delete", MyButton.class, null);

        try {
            List<Category> categoryList = databaseService.getCategories();
            for (Category category : categoryList) {
                List<Content> contentArrayList = databaseService.getContents(String.valueOf(category.getId()));
                for (Content content : contentArrayList) {
                    MyButton updateButton = new MyButton();
                    updateButton.setIcon(FontAwesome.EDIT);
                    MyButton deleteButton = new MyButton();
                    deleteButton.setIcon(FontAwesome.REMOVE);

                    Object id = contentTable.addItem();
                    Item item = contentTable.getItem(id);

                    Property categoryID = item.getItemProperty("Category ID");
                    Property category_name = item.getItemProperty("Category name");
                    Property contentID = item.getItemProperty("Content ID");
                    Property content_name = item.getItemProperty("Content name");
                    Property update = item.getItemProperty("Update");
                    Property delete = item.getItemProperty("Delete");

                    categoryID.setValue(category.getId());
                    category_name.setValue(category.getName());
                    contentID.setValue(content.getId());
                    content_name.setValue(content.getName());
                    update.setValue(updateButton);
                    delete.setValue(deleteButton);

                    updateButton.addClickListener(new Button.ClickListener() {
                        @Override
                        public void buttonClick(Button.ClickEvent clickEvent) {
                            updateContent(content);
                        }
                    });

                    deleteButton.addClickListener(new Button.ClickListener() {
                        @Override
                        public void buttonClick(Button.ClickEvent clickEvent) {
                            try {
                                databaseService.deleteContent(content);
                                listContents();
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        addComponent(contentTable);
    }

    public void updateContent(Content content){
        removeAllComponents();

        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setSizeUndefined();
        addComponent(contentLayout);

        TextField contentName = new TextField("Content Name");
        contentName.setValue(content.getName());
        contentLayout.addComponent(contentName);

        RichTextArea contentText = new RichTextArea();
        contentText.setValue(content.getData());
        contentLayout.addComponent(contentText);

        MyButton updateButton = new MyButton();
        updateButton.setIcon(FontAwesome.EDIT);
        contentLayout.addComponent(updateButton);

        contentLayout.setComponentAlignment(updateButton,Alignment.MIDDLE_RIGHT);
        setComponentAlignment(contentLayout,Alignment.TOP_CENTER);

        updateButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                try {
                    databaseService.updateContent(content,contentName,contentText);
                    listContents();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
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

    public ComboBox getContentComboBox(String caption, String id) {

        ComboBox contentComboBox = new ComboBox();
        contentComboBox.setCaption(caption);

        try {
            List<Content> contentArrayList = databaseService.getContents(id);
            int count = contentArrayList.size();
            for (int i = 0; i < count; i++) {
                Content content = contentArrayList.get(i);
                contentComboBox.addItem(content);
                contentComboBox.setId(String.valueOf(content.getId()));
                contentComboBox.setItemCaption(content, content.getName());
            }
            contentComboBox.setNullSelectionAllowed(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return contentComboBox;
    }

    public void fillContents(String id) {

        removeAllComponents();

        try {

            List<Content> contentArrayList = databaseService.getContents(id);
            int arraySize = contentArrayList.size();
            int verticalCount = (arraySize / 3) + 1;

            for (int iterator = 0; iterator < verticalCount; iterator++) {
                VerticalLayout verticalLayout2 = new VerticalLayout();
                HorizontalLayout inHorizontalLayout = new HorizontalLayout();
                inHorizontalLayout.setSizeFull();
                verticalLayout2.addComponent(inHorizontalLayout);
                addComponent(verticalLayout2);
            }

            for (int iterator = 0; iterator < arraySize; iterator++) {
                Content content = contentArrayList.get(iterator);

                MyButton button = new MyButton(content.getName());
                button.setId(String.valueOf(content.getId()));
                button.setData(content.getData());
                button.setIcon(FontAwesome.FILE);
                button.setStyleName("link");

                button.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        removeAllComponents();
                        Label label = new Label();
                        label.setContentMode(ContentMode.HTML);
                        label.setValue((String) clickEvent.getButton().getData());
                        addComponent(label);
                    }
                });

                VerticalLayout verticalLayout = (VerticalLayout) getComponent(iterator / 3);
                HorizontalLayout horizontalLayout = (HorizontalLayout) (verticalLayout.getComponent(0));

                horizontalLayout.addComponent(button);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
