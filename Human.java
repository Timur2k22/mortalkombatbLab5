/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.belov.mortalkombatbversion;


/**
 * Класс персонажа игрока
 * @author Мария
 */
public class Human extends Player{
    

    private int points;
    private int experience;
    private int win;
    private int nextexperience;
    private int Nlocations;
    private int NRound =0;

    /**
     * Конструктор 
     */
    public Human(int level, int health, int  damage, int attack, int location){
        super (level, health, damage, attack);
      
        this.points=0;
        this.experience=0;
        this.nextexperience=40;
        this.win=0;
        this.NRound=0;
        this.Nlocations = location;
    }
    /**
     * set метод 
     */
    public void setNLocations(int n){
    this.Nlocations = n;
    }
    /**
     * get метод 
     */
    public int getNLocations(){
    return this.Nlocations;
    }
    /**
     * get метод 
     */
    public int getPoints(){
        return this.points;
    }
    /**
     * get метод 
     */
    public int getExperience(){
        return this.experience;
    }
    /**
     * get метод 
     */
    public int getNextExperience(){
        return this.nextexperience;
    }
    /**
     * get метод 
     */
    public int getWin(){
        return this.win;
    }
    /**
     * set метод 
     */
    public void setPoints(int p){
        this.points+=p;
    }
    /**
     * set метод 
     */
    public void setExperience(int e){
        this.experience+=e;
    }
    /**
     * set метод 
     */
    public void setNextExperience(int e){
        this.nextexperience=e;
    }
    /**
     * set метод 
     */
    public void setWin(){
        this.win++;
    }
    /**
     * get метод 
     */
    @Override
    public String getName(){
        return "You";
    }
    /**
     * set метод 
     */
    public void setNRound(){
        this.NRound ++;
    }
    /**
     * get метод 
     */
    public int getNRound(){
        return this.NRound;
    }
    /**
     * set метод 
     */
    public void setZeroRound(){
        this.NRound=0;
    }
        
}