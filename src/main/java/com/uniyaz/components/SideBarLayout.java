package com.uniyaz.components;

import com.uniyaz.ui.LayoutUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class SideBarLayout extends VerticalLayout {
    public SideBarLayout() {
        //label.setValue(((LayoutUI) UI.getCurrent()).getBodyLayout().getContentLayout().getContentLabel().getValue());
        setSizeFull();

        addStyleName("layout-with-border");
        buildSideBarLayout();
    }

    private void buildSideBarLayout() {
        MyButton button1 = new MyButton("Anasayfa");
        button1.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Label label= new Label("Anasayfa");
                ((LayoutUI) UI.getCurrent()).getMainLayout().getBodyLayout().getContentLayout().addComponent(label);
            }
        });
        addComponent(button1);

        MyButton button2 = new MyButton("İletişim");
        button2.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                ((LayoutUI) UI.getCurrent()).getMainLayout().getBodyLayout().getContentLayout().setContentLabel("İletişim");
            }
        });
        addComponent(button2);

        MyButton button3 = new MyButton("Hakkımızda");
        button3.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                ((LayoutUI) UI.getCurrent()).getMainLayout().getBodyLayout().getContentLayout().setContentLabel("Hakkımızda");
            }
        });
        addComponent(button3);
    }
}
