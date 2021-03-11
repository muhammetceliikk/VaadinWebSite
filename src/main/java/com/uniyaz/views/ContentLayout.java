package com.uniyaz.views;

import com.uniyaz.components.CategoryComboBox;
import com.uniyaz.components.ImageUploader;
import com.uniyaz.components.MyButton;
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
    TextField categoryName, contentName;
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
        setSizeFull();
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
                    Notification.show("Category added");
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
        addComponent(contentTable);
    }

    public void addContent() {

        removeAllComponents();

        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setSizeUndefined();
        addComponent(contentLayout);

        CategoryComboBox createComboBox = new CategoryComboBox("Select page to add Content");
        ComboBox categoryComboBox = createComboBox.getComboBox();
        contentLayout.addComponent(categoryComboBox);

        contentName = new TextField("Enter content name");
        contentLayout.addComponent(contentName);

        addUploadBar(contentLayout);

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
                            FileResource fileResource = (FileResource) image.getSource();
                            byte[] bytes = new byte[(int) fileResource.getSourceFile().length()];
                            fileResource.getStream().getStream().read(bytes);
                            content.setImage(bytes);
                            content.setName(contentName.getValue());
                            content.setData(contentText.getValue());
                            databaseService.addContent(selectedCategory, content);
                            Notification.show("Content added");
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        addContent();
                    }
                });
            }
        });

        contentLayout.setComponentAlignment(addButton, Alignment.MIDDLE_RIGHT);
        setComponentAlignment(contentLayout, Alignment.TOP_CENTER);
    }

    public void addUploadBar(VerticalLayout contentLayout) {

        VerticalLayout uploadLayout = new VerticalLayout();
        contentLayout.addComponent(uploadLayout);

        image = new Image();

        ImageUploader receiver = new ImageUploader(image);
        Upload upload = new Upload("Upload it here", receiver);
        upload.setButtonCaption("Start Upload");
        upload.addSucceededListener(receiver);
        uploadLayout.addComponent(upload);
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
                                Notification.show("Content deleted");
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

    public void updateContent(Content content) {
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

        contentLayout.setComponentAlignment(updateButton, Alignment.MIDDLE_RIGHT);
        setComponentAlignment(contentLayout, Alignment.TOP_CENTER);

        updateButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                try {
                    databaseService.updateContent(content, contentName, contentText);
                    Notification.show("Content updated");
                    listContents();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void fillContentsById(String id) {

        removeAllComponents();

        try {

            List<Content> contentArrayList = databaseService.getContents(id);
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
