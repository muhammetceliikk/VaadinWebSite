package com.uniyaz.domain;

import java.io.Serializable;

public abstract class BaseDomain implements Serializable {
    public abstract int getId();
    public abstract String getName();
}
