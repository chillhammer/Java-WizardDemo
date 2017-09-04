import java.awt.*;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Random;

public class Enemy extends GameObject{

    //private Handler handler;
    private Random r = new Random();
    private int choose;
    private int hp;
    private int speedIncrements;

    public Enemy(int x, int y, ID id, Handler handler, SpriteSheet ss) {
        super(x, y, id, handler, ss);
        choose = 0;
        hp = 100;
        speedIncrements = 3;
        sprite = ss.grabImage(2,0,Game.GS,Game.GS);
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        choose = r.nextInt(25);

        Iterator<GameObject> iter = handler.object.iterator();
        while(iter.hasNext()) {
            GameObject next = null;
            try {
                next = iter.next();
            } catch(ConcurrentModificationException e) {
                System.out.println("Concurrent Error");
            }
            if (next == null) break;
            if (next.getId() == ID.Block) {
                if (next.getBounds().intersects(getBigBounds() )) {
                    x -= velX;
                    y -= velY;
                    velX = 0;
                    velY = 0;
                    break;
                }
            }

            if (next.getId() == ID.Bullet) {
                if(getBounds().intersects(next.getBounds())) {
                    hp -= 50;
                    handler.removeObject(next);
                    break;
                }
            }
        }

        if(choose == 0) {
            velX = (r.nextInt(speedIncrements*2)-speedIncrements);
            velY = (r.nextInt(speedIncrements*2)-speedIncrements);
        }

        if (hp <= 0) {
            handler.removeObject(this);
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(sprite, (int)x, (int)y, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, Game.GS, Game.GS);
    }

    public Rectangle getBigBounds() {
        return new Rectangle((int)x-Game.GS/2, (int)y-Game.GS/2,
                Game.GS+Game.GS/2, Game.GS+Game.GS/2);
    }
}
