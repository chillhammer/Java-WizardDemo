import java.awt.*;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class Bullet extends GameObject {
    private int width, height;
    public Bullet(int x, int y, ID id, Handler handler, SpriteSheet ss, int targetX, int targetY) {
        super(x, y, id, handler, ss);
        width = 8;
        height = 8;
        float speedIntensity = 8.0f;
        float dx = targetX - x;
        float dy = targetY - y;



        float d = (float) Math.sqrt(dx*dx + dy*dy);
        speedIntensity += d*0.02;

        velX = speedIntensity * dx/d;
        velY = speedIntensity * dy/d;
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        Iterator<GameObject> iter = handler.object.iterator();
        while(iter.hasNext()) {
            GameObject next;
            try {
                next = iter.next();
            } catch (ConcurrentModificationException e) {
                break;
            }
            if (next.getId() == ID.Block) {
                if (next.getBounds().intersects(getBounds())) {
                    handler.removeObject(this);
                    break;
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.green);
        g.fillOval((int)x, (int)y, width, height);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, width, height);
    }
}
