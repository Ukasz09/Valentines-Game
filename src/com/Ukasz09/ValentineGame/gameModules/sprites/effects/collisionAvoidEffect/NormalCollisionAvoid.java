package com.Ukasz09.ValentineGame.gameModules.sprites.effects.collisionAvoidEffect;

import com.Ukasz09.ValentineGame.gameModules.sprites.creatures.Monster;
import com.Ukasz09.ValentineGame.gameModules.sprites.creatures.Sprite;
import javafx.geometry.Point2D;

import java.util.ArrayList;

public class NormalCollisionAvoid implements ICollisionAvoidWay {

    private Point2D offsetAfterCollision(Monster m1, Monster m2) {
        double velocityX = m1.getVelocityX() * 2;
        double velocityY = m1.getVelocityY() * 2;

        double addX = 0;
        double addY = 0;

        //above or below
        if (m1.isUpSideToTarget(m2))
            addY -= velocityY;
        else addY += velocityY;

        //left or right
        if (m1.isLeftSideToTarget(m2))
            addX -= velocityX;
        else addX += velocityX;

        return new Point2D(addX, addY);
    }

    @Override
    public void updateCords(Sprite target, Monster monster, ArrayList<Monster> monstersList) {
        //if no collision with target
        if (!monster.intersects(target)) {
            monster.updateByVelocity(target);

            for (Monster m : monstersList) {
                if (monster.intersects(m) && monster != m) {
                    double addPositionX = offsetAfterCollision(monster, m).getX();
                    double addPositionY = offsetAfterCollision(monster, m).getY();
                    double monsterPositionX = monster.getPositionX();
                    double monsterPositionY = monster.getPositionY();

                    monsterPositionX += addPositionX;
                    monsterPositionY += addPositionY;

                    monster.setPosition(monsterPositionX, monsterPositionY);
                    m.setPosition(m.getPositionX() - addPositionX, m.getPositionY() - addPositionY);
                    return;
                }
            }
        }
    }
}
