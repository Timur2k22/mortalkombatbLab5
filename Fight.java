/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.belov.mortalkombatbversion;

//ADD IMAGE!!!
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;

/**
 * Класс описания возможных действий персонажей во время борьбы
 * @author Мария
 */
public class Fight {

    ChangeTexts change = new ChangeTexts();
    int kind_attack[] = {0};
    int experiences[] = {40, 90, 180, 260, 410};
    EnemyFabric fabric = new EnemyFabric();
    int i = 1;
    int k = -1;
    int stun = 0;
    double v = 0.0;
    int NRoundsLoc=2; // 2 совпадает с this.NRoundsLoc = level + 2 в начале игры;
    int location = 1;


    /**
     * Описание результата от выбранных действий игрока и врага
     */
    public void Move(Player p1, Player p2, JLabel l, JLabel l2) {
        if (stun == 1) {
            p1.setAttack(-1);
        }
        switch (Integer.toString(p1.getAttack()) + Integer.toString(p2.getAttack())) {
            case "10":
                v = Math.random();
                if (p1 instanceof ShaoKahn & v < 0.15) {
                    p2.setHealth(-(int) (p1.getDamage() * 0.5));
                    l2.setText("Your block is broken");

                } else {
                    p1.setHealth(-(int) (p2.getDamage() * 0.5));
                    l2.setText(p2.getName() + " counterattacked");
                }
                break;
            case "11":
                p2.setHealth(-p1.getDamage());
                l2.setText(p1.getName() + " attacked");
                break;
            case "00":
                v = Math.random();
                if (v <= 0.5) {
                    stun = 1;
                }
                l2.setText("Both defended themselves");
                break;
            case "01":
                l2.setText(p1.getName() + " didn't attacked");
                break;
            case "-10":
                l.setText(p1.getName() + " was stunned");
                stun = 0;
                l2.setText(p2.getName() + " didn't attacked");
                break;
            case "-11":
                p1.setHealth(-p2.getDamage());
                l.setText(p1.getName() + " was stunned");
                stun = 0;
                l2.setText(p2.getName() + " attacked");
                break;
        }
    }

    /**
     * Описание действий при выборе игроком атаки/защиты
     * @param human
     * @param enemy
     * @param a выбор игрока атаковать или защищаться
     * a - attack (1) or defend (0)
     */
    public void Hit(Player human, Player enemy, int a, JLabel label, JLabel label2, JDialog dialog,
            JLabel label3, CharacterAction action, JProgressBar pr1, JProgressBar pr2,
            JDialog dialog1, JDialog dialog2, JFrame frame, ArrayList<Result> results,
            JLabel label4, JLabel label5, JLabel label6, JLabel label7, JLabel label8, 
            Items[] items, JRadioButton rb, JLabel ChangeLocationLabel, JPanel BackgroundPanel) {
        label7.setText("");
        human.setAttack(a);
        if(enemy.getRecoveryAttempt() & enemy instanceof ShaoKahn ){
            /*
           игрок защищается (a == 0) -> босс восстанавливает 50% от текущего урона
            игрок защищается (a == 1) -> босс получает двойной урон
            */
            
            ((ShaoKahn)enemy).changeHealthAttemptRes(a, human.getDamage());
            
            enemy.SetRecoveryAttempt(false);
        }

        ChangeLocationLabel.setText("");
        if (k < kind_attack.length - 1) {
            k++;
        } else {
            kind_attack = action.ChooseBehavior(enemy, action);
            k = 0;
        }
        enemy.setAttack(kind_attack[k]);
        if (i % 2 == 1) {
            Move(human, enemy, label7, label8);
        } else {
            Move(enemy, human, label7, label8);
        }
        i++;
        change.RoundTexts(human, enemy, label, label2, i, label6);
        action.HP(human, pr1);
        action.HP(enemy, pr2);
        
        if( (i+(int)(Math.random() * 5)) % 4 == 0 & enemy instanceof ShaoKahn ){
            // в рандомный момент Босс пытается восстановить здоровье
     
        enemy.SetRecoveryAttempt(true); 
        }
            
            
        if (human.getHealth() <= 0 & items[2].getCount() > 0) {
            human.setNewHealth((int) (human.getMaxHealth() * 0.05));
            items[2].setCount(-1);
            action.HP(human, pr1);
            label2.setText(human.getHealth() + "/" + human.getMaxHealth());
            rb.setText(items[2].getName() + ", " + items[2].getCount() + " шт");
            label7.setText("Вы воскресли");
        }
        // they are playing inside one round until dead
        if (human.getHealth() <= 0 | enemy.getHealth() <= 0) {            
             EndRound(human, enemy, dialog, label3, 
                     action, items, ChangeLocationLabel,results, dialog1, dialog2,
                       frame, label4, label5, BackgroundPanel);
           
        }   
    }

    /**
     * Описание действий при завершении раунда
     * @param human
     * @param enemy
     */
    public void EndRound(Player human, Player enemy, JDialog dialog,
            JLabel label, CharacterAction action, Items[] items,
            JLabel ChangeLocationLabel, ArrayList<Result> results,
            JDialog dialog1, JDialog dialog2, JFrame frame, JLabel label4, JLabel label5, JPanel BackgroundPanel) {
        System.out.println("Round played");
        
        ((Human)human).setNRound(); /// add 1 round inside location
        ChangeLocationLabel.setText("");

        dialog.setVisible(true);
        dialog.setBounds(300, 150, 700, 600);
        if (human.getHealth() > 0) {
            label.setText("You win");
            ((Human) human).setWin();

            if (enemy instanceof ShaoKahn) {
                action.AddItems(38, 23, 8, items);
                action.AddPointsBoss(((Human) human), action.getEnemyes());
            } else {
                action.AddItems(25, 15, 5, items);
                action.AddPoints(((Human) human), action.getEnemyes());
            }
        } else {
            label.setText(enemy.getName() + " win");
        }
         /// do we need to change location?
         
         if(((Human)human).getNRound() == this.NRoundsLoc){
         
         this.location++;
         
         // set up number of rounds for next location
         SetNRoundsLocation(human.getLevel());
         ((Human)human).setZeroRound();

         // show that location are changed
         ChangeLocationLabel.setText("Location changed");
         
         }
        if(this.location == ((Human)human).getNLocations()+1 ){
            System.out.println("End Final Round");
        EndFinalRound(((Human) human), action, results, dialog1, dialog2,
                       frame, label4, label5);
        }else{
            dialog.setVisible(true);
            dialog.setBounds(300, 150, 700, 600);
        }

        i = 1;
        k = -1;
        kind_attack = ResetAttack();

    }
    
    /**
     * Установление кол-ва раундов для локации в зависимости от уровня игрока
     * @param level уровень игрока
     */
    private void SetNRoundsLocation(int level)
    {
        this.NRoundsLoc = level + 2;
    }

    /**
     * Описание действий при завершении финального раунда
     * @param human
     */
    public void EndFinalRound(Human human, CharacterAction action,
            ArrayList<Result> results, JDialog dialog1, JDialog dialog2, JFrame frame,
            JLabel label1, JLabel label2) {
        String text = "Победа не на вашей стороне";
        if (human.getHealth() > 0) {
            human.setWin();
            action.AddPoints(human, action.getEnemyes());
            text = "Победа на вашей стороне";
        }
        boolean top = false;
        if (results == null) {
            top = true;
        } else {
            int i = 0;
            for (int j = 0; j < results.size(); j++) {
                if (human.getPoints() < results.get(j).getPoints()) {
                    i++;
                }
            }
            if (i < 10) {
                top = true;
            }
        }
        if (top) {
            dialog1.setVisible(true);
            dialog1.setBounds(150, 150, 600, 500);
            label1.setText(text);
        } else {
            dialog2.setVisible(true);
            dialog2.setBounds(150, 150, 470, 360);
            label2.setText(text);
        }
        frame.dispose();
    }

    public int[] ResetAttack() {
        int a[] = {0};
        return a;
    }

    /**
     * Описание действий для нового раунда
     * @param human
     * @return враг для нового раунда
     */
    public Player NewRound(Player human, JLabel label, JProgressBar pr1,
            JProgressBar pr2, JLabel label2, JLabel text, JLabel label3, CharacterAction action) {

        Player enemy1 = null;
        if (((Human) human).getNRound() == this.NRoundsLoc-1) { /// тут поправить на выбор либо босса либо врага в зависимости он номера текущего раунда
            enemy1 = action.ChooseBoss(label, label2, text, label3);
        } else {
            enemy1 = action.ChooseEnemy(label, label2, text, label3);
        }
        pr1.setMaximum(human.getMaxHealth());
        pr2.setMaximum(enemy1.getMaxHealth());
        human.setNewHealth(human.getMaxHealth());
        enemy1.setNewHealth(enemy1.getMaxHealth());
        action.HP(human, pr1);
        action.HP(enemy1, pr2);
        return enemy1;
    }

}
