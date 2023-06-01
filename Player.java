/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.belov.mortalkombatbversion;

/**
 * Класс персонажа
 * @author Мария
 */
public class Player {
    
    private int level;
    private int health;
    private int maxhealth;
    private int damage;
    private int attack;
    private boolean recoveryAttempt;
    
    /**
     * Конструктор
     */
    public Player(int level, int health, int damage, int attack){
        this.level=level;
        this.health=health;
        this.damage=damage;
        this.attack=attack;
        this.maxhealth=health;
    }
   
    /**
     * set метод 
     */
    public void setLevel(){
        this.level++;
    }
    /**
     * set метод 
     */
    public void setHealth(int h){
        this.health+=h;
    }
    /**
     * set метод 
     */
    public void setNewHealth(int h){
        this.health=h;
    }
    /**
     * set метод 
     */
    public void setDamage(int d){ 
        this.damage+=d;
    }
    /**
     * set метод 
     */
    public void setAttack(int a){
        this.attack=a;
    }
    /**
     * set метод 
     */
    public void setMaxHealth(int h){ 
        this.maxhealth+=h;
    }
    /**
     * get метод 
     */
    public int getLevel(){
        return this.level;
    }
    /**
     * get метод 
     */
    public int getHealth(){
        return this.health;
    }
    /**
     * get метод 
     */
    public int getDamage(){
        return this.damage;
    }
    /**
     * get метод 
     */
    public int getAttack(){
        return this.attack;
    }
    /**
     * get метод 
     */
    public int getMaxHealth(){
        return this.maxhealth;
    }
    /**
     * get метод 
     */
    public String getName(){
        return "";
    }
    /**
     * set метод 
     */
    public void SetRecoveryAttempt(boolean recovery){
        this.recoveryAttempt = recovery;
    }
    /**
     * get метод 
     */
    public boolean getRecoveryAttempt(){
        return this.recoveryAttempt;
    }
   
}
