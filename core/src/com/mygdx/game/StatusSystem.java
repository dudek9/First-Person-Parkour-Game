/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import components.StatusComponent;
import java.util.Iterator;

/**
 *
 * @author david
 */
public class StatusSystem extends EntitySystem{
   private ImmutableArray<Entity> entities; 
   private GameWorld gameWorld; 
 
   public StatusSystem(GameWorld gameWorld) { 
       this.gameWorld = gameWorld; 
   } 
 
   @Override 
   public void addedToEngine(Engine engine) { 
       entities =
       engine.getEntitiesFor(Family.all(StatusComponent.class).get()); 
   } 
 
   @Override 
   public void update(float deltaTime) { 
       Iterator iterator = entities.iterator(); 
       while(iterator.hasNext()){ 
           Entity entity = (Entity) iterator.next(); 
           if(!entity.getComponent(StatusComponent.class).available){ 
               gameWorld.remove(entity); 
           } 
       } 
   } 
}
