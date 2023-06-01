/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.belov.mortalkombatbversion;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

/**
 * Класс для описания действий вражеского персонажа и изменения характеристик игроков
 * @author Мария
 * изменили то как ставим health + damage = can be chosen
 */
public class CharacterAction {

    ImageFixer fixer = new ImageFixer();
    private final int experience_for_next_level[] = {40, 90, 180, 260, 410, 1000};
    private final int kind_fight[][] = {{1, 0}, {1, 1, 0}, {0, 1, 0}, {1, 1, 1, 1}};

    private Player enemyes[] = new Player[5];
    EnemyFabric fabric = new EnemyFabric();
    private Player enemy = null;

    /**
     * Создание вражеских персонажей
     */
    public void setEnemyes() 
    {
        enemyes[0] = fabric.create(0, 0);
        enemyes[1] = fabric.create(1, 0);
        enemyes[2] = fabric.create(2, 0);
        enemyes[3] = fabric.create(3, 0);
        enemyes[4] = fabric.create(4, 0);
    }

    /**
     * Получение вражеских персонажей
     * @return 
     */
    public Player[] getEnemyes() 
    {
        return this.enemyes;
    }

    /**
     * Выбор вражеского персонажа
     * @return вражеский персонаж
     */
    public Player ChooseEnemy(JLabel label, JLabel label2, JLabel text, JLabel label3) 
    {
        int i = (int) (Math.random() * 4);
        ImageIcon icon = null;
        ImageIcon img = null;
        switch (i) 
        {
            case 0:
                enemy = enemyes[0];
                img = new ImageIcon(System.getProperty("user.dir")+"/imgSource/Baraka.jpg");
                icon = fixer.getScaledImage(img.getImage());
                label2.setText("Baraka (танк)");
                break;
            case 1:
                enemy = enemyes[1];
                img = new ImageIcon(System.getProperty("user.dir")+"/imgSource/Sub-Zero.jpg");
                icon =fixer.getScaledImage(img.getImage());
                label2.setText("Sub-Zero (маг)");
                break;
            case 2:
                enemy = enemyes[2];
                img = new ImageIcon(System.getProperty("user.dir")+"/imgSource/Liu_Kang.jpg");
                icon = fixer.getScaledImage(img.getImage());
                label2.setText("Liu Kang (боец)");
                break;
            case 3:
                enemy = enemyes[3];
                img = new ImageIcon(System.getProperty("user.dir")+"/imgSource/Sonya_Blade.jpg");
                icon = fixer.getScaledImage(img.getImage());
                label2.setText("Sonya Blade (солдат)");
                break;
        }
        label.setIcon(icon);
        text.setText(Integer.toString(enemy.getDamage()));
        label3.setText(Integer.toString(enemy.getHealth()) + "/" + Integer.toString(enemy.getMaxHealth()));
        return enemy;
    }

    /**
     * Выбор босса
     * @return вражеский персонаж
     */
    public Player ChooseBoss(JLabel label, JLabel label2, JLabel text, JLabel label3) 
    {
        ImageIcon boss_img = new ImageIcon(System.getProperty("user.dir")+"/imgSource/Shao_Kahn.jpg");
        ImageIcon icon1 = fixer.getScaledImage(boss_img.getImage());
        label2.setText("Shao Kahn (босс)");
        enemy = enemyes[4];
       
        label.setIcon(icon1);
        text.setText(Integer.toString(enemy.getDamage()));
        label3.setText(Integer.toString(enemy.getHealth()) + "/" + Integer.toString(enemy.getMaxHealth()));
        return enemy;
    }

    /**
     * Выбор базовой стратегии врага в зависимости от его вида
     * @return стратегия врага
     */
    public int[] EnemyBehavior(int k1, int k2, int k3, int k4, double i) 
    {
        int arr[] = null;
        if (i < k1 * 0.01) {
            arr = kind_fight[0];
        }
        if (i >= k1 * 0.01 & i < (k1 + k2) * 0.01) {
            arr = kind_fight[1];
        }
        if (i >= (k1 + k2) * 0.01 & i < (k1 + k2 + k3) * 0.01) {
            arr = kind_fight[2];
        }
        if (i >= (k1 + k2 + k3) * 0.01 & i < 1) {
            arr = kind_fight[3];
        }
        return arr;
    }

    /**
     * Описание выбора базовой стратегии врага в зависимости от его вида
     * @param enemy
     * @param action
     * @return стратегия врага
     */
    public int[] ChooseBehavior(Player enemy, CharacterAction action) 
    {
        int arr[] = null;
        double i = Math.random();
        if (enemy instanceof Baraka) {
            arr = action.EnemyBehavior(15, 15, 60, 10, i);
        }
        if (enemy instanceof SubZero) {
            arr = action.EnemyBehavior(25, 25, 0, 50, i);
        }
        if (enemy instanceof LiuKang) {
            arr = action.EnemyBehavior(13, 13, 10, 64, i);
        }
        if (enemy instanceof SonyaBlade) {
            arr = action.EnemyBehavior(25, 25, 50, 0, i);
        }
        if (enemy instanceof ShaoKahn) {
            arr = action.EnemyBehavior(10, 45, 0, 45, i);
        }
        return arr;
    }

    /**
     * Установление полосы здоровья игрока в Jframe
     * @param player
     * @param progress
     */
    public void HP(Player player, JProgressBar progress) 
    {
        if (player.getHealth() >= 0) {
            progress.setValue(player.getHealth());
        } else {
            progress.setValue(0);
        }
    }

     /**
     * Выбор улучшения игрока
     * @return 
     */
    public int askWhatPromote()
    {
        String[] options = {"Health", "Damage"};
        int ans = JOptionPane.showOptionDialog(null, "Choose what to boost?", "selection", 0,1,null, options, options[0]);
        return ans;
    }
    
    /**
     * Добавление очков и опыта за победу над врагом
     * @param human
     * @param enemyes
     */
    public void AddPoints(Human human, Player[] enemyes) 
    {
        switch (human.getLevel()) 
        {
            case 0:
                human.setExperience(20);
                human.setPoints(25 + human.getHealth() / 4);
                break;
            case 1:
                human.setExperience(25);
                human.setPoints(30 + human.getHealth() / 4);
                break;
            case 2:
                human.setExperience(30);
                human.setPoints(35 + human.getHealth() / 4);
                break;
            case 3:
                human.setExperience(40);
                human.setPoints(45 + human.getHealth() / 4);
                break;
            case 4:
                human.setExperience(50);
                human.setPoints(55 + human.getHealth() / 4);
                break;
        }
        for (int i = 0; i < 5; i++) 
        {
            if (experience_for_next_level[i] == human.getExperience()) 
            {
                human.setLevel();
                human.setNextExperience(experience_for_next_level[i + 1]);
                
                int promotion = askWhatPromote();
                
                NewHealthHuman(human, promotion);
                for (int j = 0; j < 4; j++) {
                    NewHealthEnemy(enemyes[j], human);
                }
            }
        }
    }

     /**
     * Добавление очков и опыта за победу над боссом
     * @param human
     * @param enemyes
     */
    public void AddPointsBoss(Human human, Player[] enemyes) 
    {
        switch (human.getLevel()) 
        {
            case 2:
                human.setExperience(30);
                human.setPoints(45 + human.getHealth() / 2);
                break;
            case 4:
                human.setExperience(50);
                human.setPoints(65 + human.getHealth() / 2);
                break;
        }
        for (int i = 0; i < 5; i++) 
        {
            if (experience_for_next_level[i] == human.getExperience()) {
                human.setLevel();
                human.setNextExperience(experience_for_next_level[i + 1]);
                int promotion = askWhatPromote();
                NewHealthHuman(human, promotion);
                for (int j = 0; j < 4; j++) {
                    NewHealthEnemy(enemyes[j], human);
                }
            }
        }
    }

    /**
     * Добавление предметов в мешок
     */
    public void AddItems(int k1, int k2, int k3, Items[] items) 
    {
        double i = Math.random();
        if (i < k1 * 0.01) {
            items[0].setCount(1);
        }
        if (i >= k1 * 0.01 & i < (k1 + k2) * 0.01) {
            items[1].setCount(1);
        }
        if (i >= (k1 + k2) * 0.01 & i < (k1 + k2 + k3) * 0.01) {
            items[2].setCount(1);
        }
    }

     /**
     * Результат выбора улучшения характеристики
     * @param human
     * @param chosen_option
     */
    public void NewHealthHuman(Human human,int chosen_option) 
    {
        int hp = 0;
        int damage = 0;
        switch (human.getLevel()) 
        {
            case 1:
                hp = 25;
                damage = 3;
                break;
            case 2:
                hp = 30;
                damage = 3;
                break;
            case 3:
                hp = 30;
                damage = 4;
                break;
            case 4:
                hp = 40;
                damage = 6;
                break;
        }
        if(chosen_option == 0){
        human.setMaxHealth(hp + (human.getLevel()+1)*5 );
        }
        else{
        human.setDamage(damage + + (human.getLevel()+1)*5 );}           
    }

    /**
     * Увеличение характеристик врага при переходе на новый уровень
     * @param enemy
     * @param human
     */
    public void NewHealthEnemy(Player enemy, Human human) {
        int hp = 0;
        int damage = 0;
        switch (human.getLevel()) {
            case 1:
                hp = 32;
                damage = 25;
                break;
            case 2:
                hp = 30;
                damage = 20;
                break;
            case 3:
                hp = 23;
                damage = 24;
                break;
            case 4:
                hp = 25;
                damage = 26;
                break;
        }
        enemy.setMaxHealth((int) enemy.getMaxHealth() * hp / 100);
        enemy.setDamage((int) enemy.getDamage() * damage / 100);
        enemy.setLevel();
    }

     /**
     * Выбор какой предмет использовать
     */
    public void UseItem(Player human, Items[] items, String name, JDialog dialog, JDialog dialog1) 
    {
        switch (name) {
            case "jRadioButton1":
                if (items[0].getCount() > 0) {
                    human.setHealth((int) (human.getMaxHealth() * 0.25));
                    items[0].setCount(-1);
                } else {
                    dialog.setVisible(true);
                    dialog.setBounds(300, 200, 400, 300);
                }
                break;
            case "jRadioButton2":
                if (items[1].getCount() > 0) {
                    human.setHealth((int) (human.getMaxHealth() * 0.5));
                    items[1].setCount(-1);
                } else {
                    dialog.setVisible(true);
                    dialog.setBounds(300, 200, 400, 300);
                }
                break;
            case "jRadioButton3":
                dialog.setVisible(true);
                dialog.setBounds(300, 200, 400, 300);
                break;
        }
        
        if(dialog.isVisible()==false){
            dialog1.dispose();
        }
    }
}