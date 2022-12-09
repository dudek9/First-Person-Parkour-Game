/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.utils.Array;
import components.CharacterComponent;
import components.CollectibleComponent;
import components.InteractableComponent;
import components.PlayerComponent;
import components.StatusComponent;

/**
 *
 * @author david
 */
public class MyContactListener extends ContactListener{
  
   //public Array<Entity> objects;
    public Entity character;
    
  @Override 
  public void onContactStarted(btCollisionObject colObj0, btCollisionObject colObj1) { 
      if (colObj0.userData instanceof Entity && colObj1.userData
      instanceof
      Entity) { 
           Entity entity0 = (Entity) colObj0.userData; 
           Entity entity1 = (Entity) colObj1.userData; 
           if (entity0.getComponent(CharacterComponent.class) !=
           null &&
           entity1.getComponent(CollectibleComponent.class) != null)
           { 
               if (entity0.getComponent(CollectibleComponent.class) !=
               null) { 
                   if
                   (entity0.getComponent
                   (StatusComponent.class).available) 
                   entity1.getComponent
                   (PlayerComponent.class).score += 10; 
                   entity0.getComponent
                   (StatusComponent.class).available =
                       false;
                   System.out.println("COLLECTED");
               } else { 
                   if (entity1.getComponent
                      (StatusComponent.class).available); 
                      entity0.getComponent
                      (PlayerComponent.class).score += 10; 
                      entity1.getComponent
                      (StatusComponent.class).available = 
                      false;
                      System.out.println("COLLECTED");
               } 
           } 
       } 
   }
  
    @Override
    public void onContactProcessed(int userValue0, int userValue1) {
      //Entity entity0 = (Entity)(userValue0);
      System.out.println("U0 " + userValue0);
      System.out.println("U1 " + userValue1);

      //ladders and player
      if ((userValue0 == 1 && userValue1 == 3) || (userValue0 == 3 && userValue1 == 1)) {
          System.out.println("LADDER COLLIDE");
          character.getComponent(CharacterComponent.class).walkDirection.add(Vector3.Y);   
      }
      
      //floor(lava)
      if ((userValue0 == 1 && userValue1 == 2) || (userValue0 == 2 && userValue1 == 1)) {
          System.out.println("LAVA COLLIDE");
          //decrease health
          character.getComponent(PlayerComponent.class).health--;   
      }
    }
}
