package ru.kata.spring.boot_rest.demo.exception_handling;

public class UserIncorrectData {
    private String info;

    public UserIncorrectData() {
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
