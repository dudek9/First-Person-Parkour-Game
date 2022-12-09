/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.mygdx.game.Core;
import com.mygdx.game.GameWorld;
import com.mygdx.game.Settings;
import user.GameUI;

/**
 *
 * @author david
 */
public class GameScreen implements Screen{
   Core game; 
   public GameWorld gameWorld;
   GameUI gameUI;
   
   public GameScreen(Core game) { 
       this.game = game;
       gameUI = new GameUI(game);
       gameWorld = new GameWorld(gameUI); 
       Gdx.input.setCursorCatched(true);
       Settings.Paused = false; 
       Gdx.input.setInputProcessor(gameUI.stage);
   } 

    @Override
    public void show() {
        
    }

    @Override
    public void render(float f) {
        gameUI.update(f);
        gameWorld.render(f);
        gameUI.render();
    }
    
    @Override
    public void resize(int width, int height) {
        gameWorld.resize(width, height);
        gameUI.resize(width, height);
    }
    @Override
    public void dispose() {
        gameWorld.dispose(); 
        gameUI.dispose(); 
    }
    
    @Override
    public void pause() {
        
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void hide() {
        
    }

    
}
