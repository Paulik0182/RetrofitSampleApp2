package com.android.retrofitsampleapp2.domain;

import com.google.gson.annotations.SerializedName;

public class GitUserEntity {

    private int id;
    private String login;

    @SerializedName("node_id")
    private String nodeId;

    public GitUserEntity(int id, String name, String description) {
        this.id = id;
        this.login = name;
        this.nodeId = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
}
