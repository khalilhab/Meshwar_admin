package com.meshwar.dashboard.data.network.model;


import com.meshwar.dashboard.interfaces.TaskListener;

public class ItemAny<T, ID> {

    private ID id;
    private String name;
    private String value;
    private String image;
    private int resId;
    private int iconTint;
    private int bgTint;
    private int count;
    private TaskListener<T> task;
    private Object model;

    public ItemAny() {
    }

    public ItemAny(String name) {
        this.name = name;
    }

    public ItemAny(ID id, String name) {
        this.id = id;
        this.name = name;
    }

    public ItemAny(ID id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public ItemAny(ID id, String name, TaskListener<T> task) {
        this.id = id;
        this.name = name;
        this.task = task;
    }

    public ItemAny(ID id, String name, TaskListener<T> task, Object model) {
        this.id = id;
        this.name = name;
        this.task = task;
        this.model = model;
    }

    public ItemAny(ID id, String name, Object model) {
        this.id = id;
        this.name = name;
        this.model = model;
    }

    public ItemAny(String name, TaskListener<T> task) {
        this.name = name;
        this.task = task;
    }

    public ItemAny(String name, Integer resId, TaskListener<T> task) {
        this.name = name;
        this.resId = resId;
        this.task = task;
    }

    public ItemAny(String name, String value, Integer resId, TaskListener<T> task) {
        this.name = name;
        this.value = value;
        this.resId = resId;
        this.task = task;
    }

    public ItemAny(String name, Integer resId, TaskListener<T> task, int iconTint, int bgTint) {
        this.name = name;
        this.resId = resId;
        this.task = task;
        this.iconTint = iconTint;
        this.bgTint = bgTint;
    }

    public ItemAny(String name, Integer resId, TaskListener<T> task, int iconTint) {
        this.name = name;
        this.resId = resId;
        this.task = task;
        this.iconTint = iconTint;
    }

    public ItemAny(ID id, String name, Integer resId) {
        this.id = id;
        this.name = name;
        this.resId = resId;
    }

    public ItemAny(String name, Integer resId, Integer count, TaskListener<T> task) {
        this.name = name;
        this.resId = resId;
        this.count = count;
        this.task = task;
    }

    public Object getModel() {
        return model;
    }

    public void setModel(Object model) {
        this.model = model;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskListener<T> getTask() {
        return task;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setTask(TaskListener<T> task) {
        this.task = task;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getIconTint() {
        return iconTint;
    }

    public void setIconTint(int iconTint) {
        this.iconTint = iconTint;
    }

    public int getBgTint() {
        return bgTint;
    }

    public void setBgTint(int bgTint) {
        this.bgTint = bgTint;
    }
}
