/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import physics.MotionState;

/**
 *
 * @author david
 */
public class BulletComponent implements Component{
    public MotionState motionState; 
    public btRigidBody.btRigidBodyConstructionInfo bodyInfo; 
    public btCollisionObject body; 
    
    public BulletComponent() {
        
    }
}
