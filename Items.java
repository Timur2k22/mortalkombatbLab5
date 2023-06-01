/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.belov.mortalkombatbversion;

/**
 * Класс описания мешка с предметами
 *
 * @author Мария
 */
public class Items {

    private String name;
    private int count;

    /**
     * Конструктор
     */
    public Items(String n, int c) {
        this.name = n;
        this.count = c;
    }

    /**
     * set метод
     */
    public void setName(String s) {
        this.name = s;
    }

    /**
     * set метод
     */
    public void setCount(int c) {
        this.count += c;
    }

    /**
     * get метод
     */
    public String getName() {
        return this.name;
    }

    /**
     * get метод
     */
    public int getCount() {
        return this.count;
    }
}
