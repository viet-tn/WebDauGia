package com.ute.webdaugia.beans;

import java.time.LocalDateTime;

public class Orders {
    private int idOrder,id_Product,id_User,rule,Price;
    private LocalDateTime Time,Time_to_close;

    public Orders() {
    }

    public Orders(int idOrder, int id_Product, int id_User, int rule, int price, LocalDateTime time, LocalDateTime time_to_close) {
        this.idOrder = idOrder;
        this.id_Product = id_Product;
        this.id_User = id_User;
        this.rule = rule;
        Price = price;
        Time = time;
        Time_to_close = time_to_close;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public int getId_Product() {
        return id_Product;
    }

    public int getId_User() {
        return id_User;
    }

    public int getRule() {
        return rule;
    }

    public int getPrice() {
        return Price;
    }

    public LocalDateTime getTime() {
        return Time;
    }

    public LocalDateTime getTime_to_close() {
        return Time_to_close;
    }
}
