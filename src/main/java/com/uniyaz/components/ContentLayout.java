package com.uniyaz.components;

import com.uniyaz.databaseService.DatabaseService;
import com.uniyaz.domain.Category;
import com.uniyaz.domain.Content;
import com.uniyaz.ui.LayoutUI;
import com.vaadin.data.Property;
import com.vaadin.shared.ui.MultiSelectMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContentLayout extends VerticalLayout {
    TextField categoryName, categoryID, contentID, contentName;
    RichTextArea contentText;
    DatabaseService databaseService = new DatabaseService();
    Content tempContent;
    ComboBox componentComboBox, contentComboBox;

    public ContentLayout() {
        setSizeFull();
    }


    public void ContentLayoutFillBy(String type) {
        setSizeFull();
        removeAllComponents();
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

    private void deleteContent() {
        MyButton deleteButton = new MyButton("Delete");
        componentComboBox = getSideBarContent("Select page to delete content");
        addComponent(componentComboBox);


        componentComboBox.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                if (getComponentCount() > 2) {
                    removeComponent(getComponent(2));
                    removeComponent(getComponent(1));
                }
                if (getComponentCount() > 1) {
                    removeComponent(getComponent(1));
                }
                Component temp = (Component) valueChangeEvent.getProperty().getValue();
                ComboBox contentComboBox = getContentComboBox("Select content to delete", temp.getId());
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
                    databaseService.DeleteContent(String.valueOf(tempContent.getId()));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void addContent() {
        MyButton saveButton = new MyButton("Save");
        ComboBox componentComboBox = getSideBarContent("Select page to add content");
        addComponent(componentComboBox);

        contentName = new TextField("Enter content name");
        addComponent(contentName);

        contentText = new RichTextArea();
        addComponent(contentText);

        addComponent(saveButton);

        componentComboBox.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                Component temp = (Component) valueChangeEvent.getProperty().getValue();

                saveButton.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        try {
                            databaseService.AddContent(temp.getId(), contentName.getValue(), contentText.getValue());
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        ((LayoutUI) UI.getCurrent()).getMainLayout().getBodyLayout().getSideBarLayout().buildSideBarLayout();
                        deleteCategory();
                    }
                });
            }
        });
        /*
                Table table = new Table();
                table.setHeight("250px");

                table.setColumnHeaders("NO", "NO1");
                table.setSelectable(true);
                table.setMultiSelectMode(MultiSelectMode.SIMPLE);
                table.setMultiSelect(false);
                addComponent(table);*/
    }

    private void addCategory() {
        MyButton saveButton = new MyButton("Save");

        categoryName = new TextField("Enter category name");
        addComponent(categoryName);
        saveButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                MyButton button = new MyButton(categoryName.getValue());
                button.addStyleName(ValoTheme.BUTTON_FRIENDLY);
                try {
                    databaseService.AddCategory(categoryName.getValue());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                ((LayoutUI) UI.getCurrent()).getMainLayout().getBodyLayout().getSideBarLayout().buildSideBarLayout();
            }
        });
        addComponent(saveButton);
    }

    private void deleteCategory() {

        removeAllComponents();
        MyButton deleteButton = new MyButton("Delete");
        ComboBox componentComboBox = getSideBarContent("Components");
        addComponent(componentComboBox);

        componentComboBox.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                Component temp = (Component) valueChangeEvent.getProperty().getValue();

                deleteButton.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        try {
                            databaseService.DeleteCategory(temp.getId());
                            deleteCategory();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        ((LayoutUI) UI.getCurrent()).getMainLayout().getBodyLayout().getSideBarLayout().buildSideBarLayout();
                    }
                });
            }
        });
        addComponent(deleteButton);
    }

    public ComboBox getSideBarContent(String caption) {
        ComboBox componentComboBox = new ComboBox();
        componentComboBox.setCaption(caption);
        ArrayList<Component> componentData = new ArrayList<Component>();
        int count = ((LayoutUI) UI.getCurrent()).getMainLayout().getBodyLayout().getSideBarLayout().getComponentCount();
        for (int i = 0; i < count; i++) {
            Component c = ((LayoutUI) UI.getCurrent()).getMainLayout().getBodyLayout().getSideBarLayout().getComponent(i);
            componentData.add(c);
            componentComboBox.addItem(c);
            componentComboBox.setItemCaption(c, c.getCaption());
        }

        componentComboBox.setNullSelectionAllowed(false);
        return componentComboBox;
    }

    public void showContents(String id) {
        removeAllComponents();
        try {
            List<Content> contentArrayList = databaseService.GetContents(id);
            for (Content content : contentArrayList) {
                TextField textField = new TextField();
                textField.setValue(content.getName());
                textField.setData(content.getData());
                addComponent(textField);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ComboBox getContentComboBox(String caption, String id) {

        ComboBox contentComboBox = new ComboBox();
        contentComboBox.setCaption(caption);
        try {
            List<Content> contentArrayList = databaseService.GetContents(id);
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
}
