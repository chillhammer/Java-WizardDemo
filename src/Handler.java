import java.awt.*;
import java.util.LinkedList;

public class Handler {

    LinkedList<GameObject> object;

    private boolean up = false, down = false, right = false, left = false;

    public Handler() {
         object = new LinkedList<GameObject>();
    }

    public void tick() {
        for(int i = 0; i < object.size(); i++) {
            object.get(i).tick();
        }
    }

    public void render(Graphics g) {
        for(int i = 0; i < object.size(); i++) {
            object.get(i).render(g);
        }
    }

    /**
     *
     * @param id id to check for
     * @return first object found with the same id
     */
    public GameObject getObjectById (ID id) {
        for(int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
            if (tempObject.getId() == id) {
                return tempObject;
            }
        }
        return null;
    }

    public void addObject(GameObject tempObject) {
        object.add(tempObject);
    }

    public void removeObject(GameObject tempObject) {
        object.remove(tempObject);
    }

    //No Lag on Key Press
    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }
}
