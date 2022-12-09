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
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import components.ModelComponent;

/**
 *
 * @author david
 */
public class RenderSystem extends EntitySystem{
   private ImmutableArray<Entity> entities; 
   private final ModelBatch batch; 
   private final Environment environment;
   
   public RenderSystem(ModelBatch batch, Environment environment) { 
       this.batch = batch;              
       this.environment = environment; 
   } 
   
   @Override
   public void addedToEngine(Engine e) { 
       entities = e.getEntitiesFor(Family.all(ModelComponent.class).get()); 
   } 
   
   @Override
   public void update(float delta) { 
       for (int i = 0; i < entities.size(); i++) { 
           ModelComponent mod = 
           entities.get(i).getComponent(ModelComponent.class); 
           batch.render(mod.instance, environment); 
       } 
   } 
}
