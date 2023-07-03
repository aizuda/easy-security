package com.aizuda.easy.security.domain;

public class LocalEntity {

    private Object user;

    private Boolean special = false;

    private Boolean project = false;

    public Boolean getSpecial() {
        return special;
    }

    public void setSpecial(Boolean special) {
        this.special = special;
    }

    public Boolean getProject() {
        return project;
    }

    public void setProject(Boolean project) {
        this.project = project;
    }

    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
    }

}
