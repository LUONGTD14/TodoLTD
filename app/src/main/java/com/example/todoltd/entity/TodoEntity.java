package com.example.todoltd.entity;

public class TodoEntity {
    private int idTodo;
    private int categoryTodo;
    private String nameTodo, detailTodo, dueDateTodo, imgTodo;
    private int statusTodo;

    public TodoEntity() {
    }

    public TodoEntity(int idTodo, int categoryTodo, String nameTodo, String detailTodo, String dueDateTodo, String imgTodo, int statusTodo) {
        this.idTodo = idTodo;
        this.categoryTodo = categoryTodo;
        this.nameTodo = nameTodo;
        this.detailTodo = detailTodo;
        this.dueDateTodo = dueDateTodo;
        this.imgTodo = imgTodo;
        this.statusTodo = statusTodo;
    }

    public int getIdTodo() {
        return idTodo;
    }

    public void setIdTodo(int idTodo) {
        this.idTodo = idTodo;
    }

    public int getCategoryTodo() {
        return categoryTodo;
    }

    public void setCategoryTodo(int categoryTodo) {
        this.categoryTodo = categoryTodo;
    }

    public String getNameTodo() {
        return nameTodo;
    }

    public void setNameTodo(String nameTodo) {
        this.nameTodo = nameTodo;
    }

    public String getDetailTodo() {
        return detailTodo;
    }

    public void setDetailTodo(String detailTodo) {
        this.detailTodo = detailTodo;
    }

    public String getDueDateTodo() {
        return dueDateTodo;
    }

    public void setDueDateTodo(String dueDateTodo) {
        this.dueDateTodo = dueDateTodo;
    }

    public String getimgTodo() {
        return imgTodo;
    }

    public void setimgTodo(String imgTodo) {
        this.imgTodo = imgTodo;
    }

    public int getStatusTodo() {
        return statusTodo;
    }

    public void setStatusTodo(int statusTodo) {
        this.statusTodo = statusTodo;
    }
}
