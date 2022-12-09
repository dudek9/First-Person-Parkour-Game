/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import components.CharacterComponent;
import components.ModelComponent;
import components.PlayerComponent;
import user.GameUI;

/**
 *
 * @author david
 */
public class PlayerSystem extends EntitySystem implements EntityListener{
   
   private Entity player; 
   private PlayerComponent playerComponent; 
   private CharacterComponent characterComponent; 
   private ModelComponent modelComponent; 
   private final Vector3 tmp = new Vector3(); 
   private final Camera camera;
   private float velocity;
   private boolean air = false;
   private GameWorld gameWorld;
   private boolean climbing;
   private GameUI gameUI;
   
   public PlayerSystem(GameWorld gameWorld, GameUI gameUI, Camera camera) { 
       this.camera = camera;
       this.gameUI = gameUI;
       this.gameWorld = gameWorld; 
   } 
 
   @Override 
   public void addedToEngine(Engine engine) { 
       engine.addEntityListener(Family.all
       (PlayerComponent.class).get(), this); 
   }    
   
   @Override 
   public void entityAdded(Entity entity) { 
       player = entity; 
       playerComponent = entity.getComponent(PlayerComponent.class); 
       characterComponent = entity.getComponent(CharacterComponent.class); 
       modelComponent = entity.getComponent(ModelComponent.class); 
   }
   
   @Override 
   public void update(float delta) { 
       if (player == null) return; 
       updateMovement(delta);
       updateStatus();
       checkGameOver();
   }
   
   private void updateStatus() { 
       gameUI.healthWidget.setValue(playerComponent.health); 
   } 
   
   private void updateMovement(float delta) { 
       float deltaX = -Gdx.input.getDeltaX() * 0.5f; 
       float deltaY = -Gdx.input.getDeltaY() * 0.5f; 
       
       velocity = 10;
       
       tmp.set(0, 0, 0);
       
       characterComponent.characterDirection.set(1, 0, 0).rot(modelComponent.instance.transform).nor();
       
       if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) && characterComponent.characterController.canJump())
           velocity += 10;
       
       if (Gdx.input.isKeyPressed(Input.Keys.W))
            characterComponent.walkDirection.add(camera.direction);
       
       if (Gdx.input.isKeyPressed(Input.Keys.S)) 
            characterComponent.walkDirection.sub(camera.direction);
       
       if (Gdx.input.isKeyPressed(Input.Keys.A))
            tmp.set(camera.direction).crs(camera.up).nor().scl(-1);
       
       if (Gdx.input.isKeyPressed(Input.Keys.D))
            tmp.set(camera.direction).crs(camera.up).nor().scl(1);
       
       if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && characterComponent.characterController.canJump()) {
           characterComponent.characterController.jump(new Vector3(0, 15f, 0));
           
       }
       
       //tmp.add(0.5f, 0, 0);
       
       characterComponent.walkDirection.add(tmp); 
       characterComponent.walkDirection.scl(velocity * delta); 
       characterComponent.characterController.setWalkDirection(characterComponent.walkDirection); 
       
       camera.rotate(camera.up, deltaX); 
       tmp.set(camera.direction).crs(camera.up).nor(); 
       camera.direction.rotate(tmp, deltaY); 
       
       characterComponent.walkDirection.set(0, 0, 0);
       
       //Move 
       Matrix4 ghost = new Matrix4(); 
       Vector3 translation = new Vector3(); 
       characterComponent.ghostObject.getWorldTransform(ghost);   
       ghost.getTranslation(translation);
       
       modelComponent.instance.transform.set(translation.x,
       translation.y, translation.z, camera.direction.x,
       camera.direction.y, camera.direction.z, 0); 
       camera.position.set(translation.x, translation.y, translation.z);
       //System.out.println("X; " + translation.x + "Y; " + translation.y + "Z; " + translation.z);
       
       camera.update(true); 
   }
   
   private void checkGameOver() { 
       if (playerComponent.health <= 0 && !Settings.Paused) { 
           Settings.Paused = true; 
           gameUI.gameOverWidget.gameOver(); 
       }
       
       else if (playerComponent.score >= 100 && !Settings.Paused) {
           Settings.Paused = true; 
           gameUI.gameOverWidget.gameOver(); 
       }
   } 
   
   private void setClimbing(boolean bool) {
       climbing = bool;
   }
   

    @Override
    public void entityRemoved(Entity entity) {
        
    }
}
