package com.uniyaz.components;

import com.uniyaz.ui.LayoutUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;
import java.util.Collection;

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

        Button save = new Button("Save");
        save.setSizeUndefined();
        save.addStyleName(ValoTheme.BUTTON_FRIENDLY);

        Button delete = new Button("Delete");
        delete.setSizeUndefined();
        delete.addStyleName(ValoTheme.BUTTON_FRIENDLY);

        switch(type){
            case("Add Category"):
                categoryID = new TextField("Enter category ID");
                addComponent(categoryID);
                categoryName = new TextField("Enter category name");
                addComponent(categoryName);
                save.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        Button button = new Button(categoryName.getValue());
                        button.setData(categoryID.getValue());
                        ((LayoutUI) UI.getCurrent()).getMainLayout().getBodyLayout().getSideBarLayout().addComponent(button);
                    }
                });
                addComponent(save);
                break;
            case("Delete Category"):

                ArrayList <String> componentData = new ArrayList<String>();
                int count=((LayoutUI) UI.getCurrent()).getMainLayout().getBodyLayout().getSideBarLayout().getComponentCount();
                for(int i=0;i<count;i++){
                    Component c=((LayoutUI) UI.getCurrent()).getMainLayout().getBodyLayout().getSideBarLayout().getComponent(i);
                    componentData.add(c.getCaption());
                }

                ComboBox componentComboBox = new ComboBox("Components",componentData);
                addComponent(componentComboBox);

                save.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                    }
                });

                /*addComponent(delete);
                int count=((LayoutUI) UI.getCurrent()).getMainLayout().getBodyLayout().getSideBarLayout().getComponentCount();
                System.out.println(count);
                for(int i=0;i<count;i++){
                    Component c=((LayoutUI) UI.getCurrent()).getMainLayout().getBodyLayout().getSideBarLayout().getComponent(0);
                    ((LayoutUI) UI.getCurrent()).getMainLayout().getBodyLayout().getSideBarLayout().removeComponent(c);
                }*/
                addComponent(delete);
                break;
            case("Add Content"):
                break;
            case("Delete Content"):
                break;
        }

    }

    public Label getContentLabel() {
        return contentLabel;
    }

    public void setContentLabel(String contentLabel) {
        this.contentLabel.setValue(contentLabel);
    }

}
