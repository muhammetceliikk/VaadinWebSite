package com.uniyaz.functions;

import com.uniyaz.components.CategoryComboBox;
import com.uniyaz.components.ImageUploader;
import com.uniyaz.components.MyButton;
import com.uniyaz.domain.Category;
import com.uniyaz.domain.Content;
import com.uniyaz.service.DatabaseService;
import com.uniyaz.views.ImageContentLayout;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ContentFunctions extends VerticalLayout {

    @PropertyId("name")
    TextField contentName;

    @PropertyId("data")
    RichTextArea contentText;

    FieldGroup binder;
    BeanItem<Content> contentBeanItem;

    Image image = new Image();
    DatabaseService databaseService = new DatabaseService();
    MyButton addButton;
    Table contentTable;
    IndexedContainer container;

    public ContentFunctions() {
        buildContentLayout();
    }

    public void buildContentLayout() {
        setSizeFull();
    }

    public void addContent() {

        removeAllComponents();
        setSizeUndefined();

        CategoryComboBox createComboBox = new CategoryComboBox("Select page to add Content");
        ComboBox categoryComboBox = createComboBox.getComboBox();
        addComponent(categoryComboBox);

        contentName = new TextField("Enter content name");
        contentName.setNullRepresentation("");
        addComponent(contentName);

        addUploadBar(this);

        contentText = new RichTextArea();
        contentText.setNullRepresentation("");
        addComponent(contentText);

        addButton = new MyButton("Add");
        addComponent(addButton);

        contentBeanItem = new BeanItem<Content>(new Content());
        binder = new FieldGroup(contentBeanItem);
        binder.bindMemberFields(this);

        categoryComboBox.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                Category selectedCategory = (Category) valueChangeEvent.getProperty().getValue();

                addButton.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        try {
                            FileResource fileResource = (FileResource) image.getSource();
                            byte[] bytes = new byte[(int) fileResource.getSourceFile().length()];
                            fileResource.getStream().getStream().read(bytes);

                            binder.commit();

                            contentBeanItem.getBean().getCategory().setId(selectedCategory.getId());
                            contentBeanItem.getBean().getCategory().setName(selectedCategory.getName());
                            contentBeanItem.getBean().setImage(bytes);
                            databaseService.addContent(contentBeanItem.getBean());
                            Notification.show("Content added");
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (FieldGroup.CommitException e) {
                            e.printStackTrace();
                        }
                        addContent();
                    }
                });
            }
        });

        setComponentAlignment(addButton, Alignment.MIDDLE_RIGHT);
    }

    public void listContents() throws SQLException, ClassNotFoundException {

        removeAllComponents();

        createTable();
        fillTable();
    }

    public void createTable(){

        contentTable = new Table();
        contentTable.setSizeFull();
        addComponent(contentTable);

        container = new IndexedContainer();

        container.addContainerProperty("Category ID", Integer.class, null);
        container.addContainerProperty("Category name", String.class, null);
        container.addContainerProperty("Content ID", Integer.class, null);
        container.addContainerProperty("Content name", String.class, null);
        container.addContainerProperty("Update", MyButton.class, null);
        container.addContainerProperty("Delete", MyButton.class, null);

        contentTable.setContainerDataSource(container);
    }

    public void fillTable() throws SQLException, ClassNotFoundException {

        List<Content> contentArrayList = databaseService.getContents();
        for (Content content : contentArrayList) {

            MyButton updateButton = new MyButton();
            updateButton.setIcon(FontAwesome.EDIT);

            MyButton deleteButton = new MyButton();
            deleteButton.setIcon(FontAwesome.REMOVE);

            Object id = contentTable.addItem();
            Item item = container.getItem(id);
            item.getItemProperty("Category ID").setValue(content.getCategory().getId());
            item.getItemProperty("Category name").setValue(content.getCategory().getName());
            item.getItemProperty("Content ID").setValue(content.getId());
            item.getItemProperty("Content name").setValue(content.getName());
            item.getItemProperty("Update").setValue(updateButton);
            item.getItemProperty("Delete").setValue(deleteButton);

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

    public void updateContent(Content content) {
        removeAllComponents();

        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setSizeUndefined();
        addComponent(contentLayout);

        contentName = new TextField("Content Name");
        contentName.setNullRepresentation("");
        contentName.setSizeUndefined();
        addComponent(contentName);
        //contentLayout.addComponent(contentName);

        contentText = new RichTextArea();
        contentText.setNullRepresentation("");
        contentText.setSizeUndefined();
        addComponent(contentText);
        //contentLayout.addComponent(contentText);

        MyButton updateButton = new MyButton();
        updateButton.setIcon(FontAwesome.EDIT);
        addComponent(updateButton);
        //contentLayout.addComponent(updateButton);

        contentBeanItem = new BeanItem<Content>(content);
        binder = new FieldGroup(contentBeanItem);
        binder.bindMemberFields(this);
        //binder.bindMemberFields(contentLayout);

        setComponentAlignment(contentName, Alignment.TOP_CENTER);
        setComponentAlignment(contentText, Alignment.TOP_CENTER);
        setComponentAlignment(updateButton, Alignment.MIDDLE_RIGHT);

        //contentLayout.setComponentAlignment(updateButton, Alignment.MIDDLE_RIGHT);
       // setComponentAlignment(contentLayout, Alignment.TOP_CENTER);

        updateButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                try {
                    binder.commit();
                    databaseService.updateContent(contentBeanItem.getBean());
                    Notification.show("Content updated");
                    listContents();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (FieldGroup.CommitException e) {
                    e.printStackTrace();
                }
            }
        });
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
}
