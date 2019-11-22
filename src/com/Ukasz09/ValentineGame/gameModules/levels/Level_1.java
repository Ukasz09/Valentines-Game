package com.Ukasz09.ValentineGame.gameModules.levels;

import com.Ukasz09.ValentineGame.gameModules.sprites.creatures.Player;
import com.Ukasz09.ValentineGame.gameModules.effects.collisionAvoidEffect.NormalCollisionAvoid;
import com.Ukasz09.ValentineGame.gameModules.sprites.items.weapons.Weapon;
import com.Ukasz09.ValentineGame.gameModules.utilitis.ViewManager;
import com.Ukasz09.ValentineGame.gameModules.sprites.creatures.LittleMonster;
import com.Ukasz09.ValentineGame.gameModules.sprites.items.others.Coin;
import com.Ukasz09.ValentineGame.gameModules.sprites.creatures.Monster;
import com.Ukasz09.ValentineGame.gameModules.effects.kickEffect.KickByLittleMonster;
import com.Ukasz09.ValentineGame.gameModules.effects.kickEffect.TeleportKick;
import com.Ukasz09.ValentineGame.graphicModule.texturesPath.BackgroundPath;
import com.Ukasz09.ValentineGame.graphicModule.texturesPath.ImageSheetProperty;
import com.Ukasz09.ValentineGame.graphicModule.texturesPath.SpritesProperties;
import com.Ukasz09.ValentineGame.soundsModule.soundsPath.SoundsPath;
import com.Ukasz09.ValentineGame.soundsModule.soundsPath.SoundsPlayer;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class Level_1 extends AllLevels {
    public static final String BACKGROOUND_IMAGE_PATH = BackgroundPath.BACKGROUD_IMAGE_PATH_L_0;
    public static final String BACKGROUND_SOUND_PATH = SoundsPath.BACKGROUND_SOUND_PATH_1;
    public static final double BACKGROUND_SOUND_VOLUME = 0.1;

    private final int amountOfSmallCoins = 5;
    private final int amountOfNormalCoins = 3;
    private final int amountOfBigCoins = 2;

    private final int amountOfMonsters = 10;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Level_1(ViewManager manager) {
        super(manager,new Image(BACKGROOUND_IMAGE_PATH));
        setAmountOfCoins(amountOfSmallCoins, amountOfNormalCoins, amountOfBigCoins);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void spawnEnemies(ArrayList<Monster> enemiesList) {
        ImageSheetProperty sheetProperty= SpritesProperties.littleMonsterSheetProperty();
//        Image littleMonsterImage = new Image(SpritesPath.LITTLE_MONSTER1_SHEET_PATH);
//        ColorAdjust monochrome = new ColorAdjust();
////        monochrome.setSaturation(-1.0);
//        Random random=new Random();
//        boolean negative=random.nextInt(1)==0;
//        double neagativeMul=1;
//        if (negative)
//            neagativeMul=-1;
//        double hue=(double)(random.nextInt(100))/100*neagativeMul;
//        monochrome.setHue(hue);
//        ImageView iv =new ImageView(littleMonsterImage);
//        iv.setEffect(monochrome);
//        SnapshotParameters params = new SnapshotParameters();
//        params.setFill(Color.TRANSPARENT);
//        Image huedImage = iv.snapshot(params, null);
        LittleMonster littleMonster;
        for (int i = 0; i < amountOfMonsters; i++) {
            littleMonster = new LittleMonster(sheetProperty, new KickByLittleMonster(new TeleportKick()), getManager(), new NormalCollisionAvoid());
            littleMonster.setStartedPosition();
            enemiesList.add(littleMonster);
        }
    }

    @Override
    public void setPlayerStartPosition(Player player) {
        double posX = getDefaultPlayerPosition().getX();
        double posY = getDefaultPlayerPosition().getY();
        player.setPosition(posX, posY);
    }

    @Override
    public void render(ArrayList<Monster> enemiesList, ArrayList<Coin> coinsList, ArrayList<Weapon> shotsList, Player player) {
        defaultLevelRender(enemiesList, coinsList, shotsList, player.getTotalScore());
    }

    @Override
    public SoundsPlayer getBackgroundSound() {
        return new SoundsPlayer(BACKGROUND_SOUND_PATH, BACKGROUND_SOUND_VOLUME, true);
    }

    @Override
    public int amountOfSpawnEnemies() {
        return amountOfMonsters;
    }
}
