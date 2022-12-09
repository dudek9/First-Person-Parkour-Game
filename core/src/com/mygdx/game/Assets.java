/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
/**
 *
 * @author david
 */
public class Assets {
   public static Skin skin; 
   public Assets() { 
       skin = new Skin(); 
       FileHandle fileHandle = 
       Gdx.files.internal("uiskin.json"); 
       FileHandle atlasFile = fileHandle.sibling("uiskin.atlas"); 
       if (atlasFile.exists()) { 
           skin.addRegions(new TextureAtlas(atlasFile)); 
       } 
       skin.load(fileHandle); 
   }
   
   public static void dispose() { 
       skin.dispose(); 
   } 
}
