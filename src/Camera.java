public class Camera {
    private float x, y;

    public Camera( float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void tick(GameObject object) {
        x += ((object.getX() - x) - Game.WINDOWWIDTH/2) * 0.15f;
        y += ((object.getY() - y) - Game.WINDOWHEIGHT/2) * 0.15f;

        if(x <= 0) x = 0;
        if(x >= Game.ROOMWIDTH - Game.WINDOWWIDTH) x = Game.ROOMWIDTH - Game.WINDOWWIDTH;
        if(y <= 0) y = 0;
        if(y >= Game.ROOMHEIGHT - Game.WINDOWHEIGHT) y = Game.ROOMHEIGHT - Game.WINDOWHEIGHT;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }


}
