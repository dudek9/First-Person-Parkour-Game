/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btAxisSweep3;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btGhostPairCallback;
import com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import components.BulletComponent;
import components.CharacterComponent;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.utils.Array;
import physics.MyContactListener;

/**
 *
 * @author david
 */
public class BulletSystem extends EntitySystem implements EntityListener{
   
   public final btCollisionConfiguration collisionConfiguration; 
   public final btCollisionDispatcher dispatcher; 
   public final btBroadphaseInterface broadphase; 
   public final btConstraintSolver solver; 
   public final btDiscreteDynamicsWorld collisionWorld; 
   private btGhostPairCallback ghostPairCallback; 
   public int maxSubSteps = 5; 
   public float fixedTimeStep = 1f / 60f;
   private MyContactListener myContactListener;
   
   @Override 
   public void addedToEngine(Engine engine) { 
       engine.addEntityListener(Family.all(BulletComponent.class).get(), this); 
   }
   
   public BulletSystem() { 
       collisionConfiguration = new btDefaultCollisionConfiguration(); 
       dispatcher = new btCollisionDispatcher(collisionConfiguration); 
       broadphase = new btAxisSweep3(new Vector3(-1000, -1000, -1000), new Vector3(1000, 1000, 1000)); 
       solver = new btSequentialImpulseConstraintSolver(); 
       collisionWorld = new btDiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration); 
       ghostPairCallback = new btGhostPairCallback(); 
       broadphase.getOverlappingPairCache().setInternalGhostPairCallback(ghostPairCallback); 
       collisionWorld.setGravity(new Vector3(0, -9.81f, 0));
       System.out.println(collisionWorld.getGravity());
       
       myContactListener = new MyContactListener(); 
       myContactListener.enable();
   }
   
   @Override 
   public void update(float deltaTime) { 
       collisionWorld.stepSimulation(deltaTime, maxSubSteps, fixedTimeStep);
       collisionWorld.applyGravity();
       collisionWorld.clearForces();
   } 
   public void dispose() { 
       collisionWorld.dispose(); 
       if (solver != null) solver.dispose(); 
       if (broadphase != null) broadphase.dispose(); 
       if (dispatcher != null) dispatcher.dispose(); 
       if (collisionConfiguration != null) {
           collisionConfiguration.dispose(); 
           ghostPairCallback.dispose(); 
       }
   } 
   @Override 
   public void entityAdded(Entity entity) { 
       BulletComponent bulletComponent =
       entity.getComponent(BulletComponent.class); 
       if (bulletComponent.body != null) { 
           collisionWorld.addRigidBody((btRigidBody)bulletComponent.body); 
       } 
   } 
   public void removeBody(Entity entity) { 
       BulletComponent comp = entity.getComponent(BulletComponent.class); 
       
       if (comp != null) 
            collisionWorld.removeCollisionObject(comp.body); 
       
       CharacterComponent character = entity.getComponent(CharacterComponent.class); 
       
       if (character != null) { 
           collisionWorld.removeAction
           (character.characterController); 
           collisionWorld.removeCollisionObject
           (character.ghostObject); 
       } 
   }
   
   public void setContactListener(Entity character) {
       myContactListener.character = character;
   }
   
   @Override 
   public void entityRemoved(Entity entity) { 
   } 

}
