package com.uniyaz.domain;

import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;

public class MyButton extends Button {

    public MyButton(){
        setStyleName(ValoTheme.BUTTON_FRIENDLY);
    }

    public MyButton(String caption){
        setStyleName(ValoTheme.BUTTON_FRIENDLY);
        setCaption(caption);
    }
}
