/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import com.badlogic.ashley.core.Component;

/**
 *
 * @author david
 */
public class PlayerComponent implements Component{
   public float health; 
   public static int score; 
   
   public PlayerComponent() { 
       health = 100; 
       score = 0; 
   } 
}
