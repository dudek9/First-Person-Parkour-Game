/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;

/**
 *
 * @author david
 */
public class MotionState extends btMotionState{
    
   private final Matrix4 transform;
    
   public MotionState(final Matrix4 transform) { 
       this.transform = transform; 
   }
   
   @Override 
   public void getWorldTransform(final Matrix4 worldTrans) { 
       worldTrans.set(transform); 
   }
   
   @Override 
   public void setWorldTransform(final Matrix4 worldTrans) { 
       transform.set(worldTrans); 
   } 
}
