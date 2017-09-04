import java.awt.*;

public class Wizard extends GameObject {

    public static final float BASEMAXSPD = 7.0f;
    public static final float BASEACCSPD = 0.7f;
    private static final int width = 32;
    private static final int height = 32;
    private float maxSpdDef;
    private float maxSpd;
    private float accSpd;
    private double dir;
    private int ammo;
    private int hp;

    public Wizard(int x, int y, ID id, Handler handler, SpriteSheet ss) {
        super(x, y, id, handler, ss);
        maxSpdDef = BASEMAXSPD;
        maxSpd = maxSpdDef;
        accSpd = BASEACCSPD;
        ammo = 100;
        hp = 100;
        sprite = ss.grabImage(0,0,width,height);
    }

    @Override
    public void tick() {

        int keyHor = (handler.isRight() ? 1 : 0) - (handler.isLeft() ? 1 : 0);
        int keyVer = (handler.isDown() ? 1 : 0) - (handler.isUp() ? 1 : 0);

        //If Key Pressed
        if (keyHor != 0 || keyVer != 0) {
            dir = Math.atan2((double) keyVer , (double) keyHor);

            if (keyHor != 0)
                velX += accSpd * Math.cos(dir);
            else
                velX -= Math.signum(velX)*accSpd;

            if (keyVer != 0)
                velY += accSpd * Math.sin(dir);
            else
                velY -= Math.signum(velY)*accSpd;

            //Diagonal
            if (keyHor != 0 && keyVer != 0) {
                maxSpd = 0.707f * maxSpdDef;
            } else {
                maxSpd = maxSpdDef;
            }
        }

        //Friction
        if(keyHor == 0) {
            velX -= Math.signum(velX)*accSpd*0.5;
            if (Math.abs(velX) < accSpd) {
                velX = 0;
            }
        }

        if(keyVer == 0) {
            velY -= Math.signum(velY)*accSpd*0.5;
            if (Math.abs(velY) < accSpd) {
                velY = 0;
            }
        }

        //Clamping Horizontal
        if (velX > maxSpd) {
            velX = (float) maxSpd;
        }
        if (velX < -maxSpd) {
            velX = -maxSpd;
        }

        //Clamping Vertical
        if (velY > maxSpd) {
            velY = (float)  maxSpd;
        }
        if (velY < -maxSpd) {
            velY = (float) -maxSpd;
        }

        collision();

        x += velX;
        y += velY;
    }

    private void collision() {
        for(int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if( tempObject.getId() == ID.Block) {
                if(!place_free(x,y,getBounds(),tempObject.getBounds())) {
                    x -= velX;
                    y -= velY;
                }
                if(!place_free(x + velX, y, getBounds(), tempObject.getBounds())) {
                    while(place_free(x + Math.signum(velX), y, getBounds(), tempObject.getBounds())) {
                        x += Math.signum(velX);
                    }
                    velX = 0;
                }

                if(!place_free(x , y + velY, getBounds(), tempObject.getBounds())) {
                    while(place_free(x,y + Math.signum(velY), getBounds(), tempObject.getBounds())) {
                        y += Math.signum(velY);
                    }
                    velY = 0;
                }
            }
        }
    }

    private boolean place_free(float x, float y, Rectangle rect1, Rectangle rect2) {
        rect1.x = (int) x;
        rect1.y = (int) y;
        return (!rect1.intersects(rect2));
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(sprite, (int)x, (int)y, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x,(int) y, width, height);
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }
}
