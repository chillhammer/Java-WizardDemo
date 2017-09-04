import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {

    private Handler handler;
    private Camera camera;
    private SpriteSheet ss;

    public MouseInput(Handler handler, Camera camera, SpriteSheet ss) {
        this.handler = handler;
        this.camera = camera;
        this.ss = ss;
    }
    public void mousePressed(MouseEvent e) {
        if (camera == null) return;
        int mx =  (e.getX() + (int) camera.getX());
        int my =  (e.getY() + (int) camera.getY());

        Wizard wizard = (Wizard) handler.getObjectById(ID.Player);
        if (wizard != null) {
            int xOffset = 16;
            int yOffset = 16;
            if (wizard.getAmmo() > 0) {
                handler.addObject(new Bullet((int) wizard.getX() + xOffset, (int) wizard.getY() + yOffset,
                        ID.Bullet, handler, ss, mx, my));
                //wizard.setAmmo(wizard.getAmmo()-1);
            }
        }
    }
}
