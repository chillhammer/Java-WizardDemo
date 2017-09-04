import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class GameObject {

    //Properties
    protected float x, y;
    protected float velX = 0, velY = 0;
    protected ID id;
    protected Handler handler;
    protected SpriteSheet ss;
    protected BufferedImage sprite;



    //Methods
    public GameObject(int x, int y, ID id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }
    public GameObject(int x, int y, ID id, Handler handler, SpriteSheet ss) {
        this(x, y, id);
        this.handler = handler;
        this.ss = ss;
    }

    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract Rectangle getBounds();

    public float getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }
    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}
