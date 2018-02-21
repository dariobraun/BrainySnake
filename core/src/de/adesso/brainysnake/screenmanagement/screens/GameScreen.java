package de.adesso.brainysnake.screenmanagement.screens;


import de.adesso.brainysnake.BrainySnake;
import de.adesso.brainysnake.Gamelogic.GameMaster;

public class GameScreen extends AbstractScreen {

    public BrainySnake getBrainySnake() {
        return brainySnake;
    }

    private BrainySnake brainySnake;

    public GameScreen(GameMaster gameMaster) {
        brainySnake = new BrainySnake(gameMaster);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void initialize() {
        brainySnake.create();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        brainySnake.render();
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        super.dispose();
        brainySnake.toggleMusic();
    }
}
