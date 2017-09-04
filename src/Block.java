import java.awt.*;

public class Block extends GameObject {


    public Block(int x, int y, ID id, SpriteSheet ss) {
        super(x, y, id, null, ss);
        sprite = ss.grabImage(1,0,Game.GS,Game.GS);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(sprite, (int)x, (int)y, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, Game.GS, Game.GS);
    }
}
