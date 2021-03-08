package com.uniyaz.components;

import com.uniyaz.ui.LayoutUI;
import com.vaadin.data.Property;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;

public class ContentLayout extends VerticalLayout {
    Label contentLabel;
    public ContentLayout() {

        setSizeFull();
        contentLabel=new Label("CONTENT");
        addStyleName("layout-with-border");
        contentLabel.addStyleName(ValoTheme.LABEL_LARGE);
        contentLabel.addStyleName(ValoTheme.LABEL_BOLD);
        contentLabel.addStyleName(ValoTheme.LABEL_H4);
        addComponent(contentLabel);
    }

    public void ContentLayoutFillBy(String type){

        TextField categoryName;
        TextField categoryID;

        setSizeFull();
        removeAllComponents();

        MyButton saveButton = new MyButton("Save");

        switch(type){

            case("Add Category"):

                categoryID = new TextField("Enter category ID");
                addComponent(categoryID);
                categoryName = new TextField("Enter category name");
                addComponent(categoryName);
                saveButton.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        Button button = new Button(categoryName.getValue());
                        button.setData(categoryID.getValue());
                        button.addStyleName(ValoTheme.BUTTON_FRIENDLY);
                        ((LayoutUI) UI.getCurrent()).getMainLayout().getBodyLayout().getSideBarLayout().addComponent(button);
                    }
                });
                addComponent(saveButton);
                break;
            case("Delete Category"):
                deleteCategory();
                break;
            case("Add Content"):
                break;
            case("Delete Content"):
                break;
        }

    }

    private void deleteCategory() {

        MyButton deleteButton = new MyButton("Delete");

        ComboBox componentComboBox = new ComboBox("Components");
        ArrayList <Component> componentData = new ArrayList<Component>();
        int count=((LayoutUI) UI.getCurrent()).getMainLayout().getBodyLayout().getSideBarLayout().getComponentCount();
        for(int i=0;i<count;i++){
            Component c=((LayoutUI) UI.getCurrent()).getMainLayout().getBodyLayout().getSideBarLayout().getComponent(i);
            componentData.add(c);
            componentComboBox.addItem(c);
            componentComboBox.setItemCaption(c,c.getCaption());
        }

        componentComboBox.setNullSelectionAllowed(false);
        addComponent(componentComboBox);


        componentComboBox.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                Component temp= (Component) valueChangeEvent.getProperty().getValue();

                deleteButton.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        ((LayoutUI) UI.getCurrent()).getMainLayout().getBodyLayout().getSideBarLayout().removeComponent(temp);
                        removeAllComponents();
                        deleteCategory();
                    }
                });
            }
        });
        addComponent(deleteButton);
    }

    public Label getContentLabel() {
        return contentLabel;
    }

    public void setContentLabel(String contentLabel) {
        this.contentLabel.setValue(contentLabel);
    }

}
