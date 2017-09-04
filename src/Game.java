import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable{
    private static final long serialVersionUID = 1L;
    public static final int GS = 32; //grid space
    public static final int ROOMWIDTH = 66 * GS;
    public static final int ROOMHEIGHT = 52 * GS;
    public static final int WINDOWWIDTH = 1000;
    public static final int WINDOWHEIGHT = 563;
    private boolean isRunning = false;
    private Thread thread;
    private Handler handler;
    private Camera camera;
    private SpriteSheet ss;

    private BufferedImage level;
    private BufferedImage spriteSheet;

    public Game() {
        new Window(WINDOWWIDTH, WINDOWHEIGHT, "Wizard Game", this);
        start();

        handler = new Handler();
        camera = new Camera(0, 0);
        this.addKeyListener(new KeyInput(handler));

        BufferedImageLoader loader = new BufferedImageLoader();
        level = loader.loadImage("/wizard_level.png");
        spriteSheet = loader.loadImage("/sprite_sheet.png");

        ss = new SpriteSheet(spriteSheet);

        this.addMouseListener(new MouseInput(handler, camera, ss));

        loadLevel(level);
    }

    private void start() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    private void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public SpriteSheet getSs() {
        return ss;
    }

    @Override
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) {
                tick();
                delta --;
            }
            render();
            frames ++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
            }
        }
        stop();
    }

    public void tick() {
        handler.tick();
        Wizard wizard = (Wizard) handler.getObjectById(ID.Player);
        if(wizard != null)
            camera.tick(wizard);
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g= bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        //////////////////////////////////////

        g.setColor(Color.gray);
        g.fillRect(0, 0, WINDOWWIDTH, WINDOWHEIGHT);

        g2d.translate(-camera.getX(), -camera.getY());

        handler.render(g);

        g2d.translate(camera.getX(), camera.getY());
        //////////////////////////////////////

        g.dispose();
        bs.show();
    }

    //loading level
    private void loadLevel(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        for (int xx = 0; xx < w; xx++) {
            for(int yy = 0; yy < h; yy++) {
                int pixel = image.getRGB(xx, yy);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                if(red == 255)
                    handler.addObject(new Block(xx * GS, yy * GS, ID.Block, ss));

                if(blue == 255)
                    handler.addObject(new Wizard(xx * GS, yy * GS, ID.Player, handler, ss));

                if(green == 255)
                    handler.addObject(new Enemy(xx * GS, yy * GS, ID.Enemy, handler, ss));
            }
        }
    }

    public static void main(String[] args) {
        new Game();
    }
}
