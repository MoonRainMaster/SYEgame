package com.sye.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.sye.game.utils.TiledObjectUtil;

import static com.sye.game.utils.Constants.PPM;

public class main extends ApplicationAdapter {

    private boolean DEBUG = false;
    private final float SCALE = 1.0f;

    private OrthographicCamera camera;

    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;

    private Box2DDebugRenderer b2dr;
    private World world;
    private Body player, plataform, player2, player3, player4;

    private SpriteBatch batch, batch2, batch3, batch4;
    private Texture tex, tex2, tex3, tex4;
    //batch de los dados y atributos
    private SpriteBatch batch1_dado,batch2_dado,batch3_dado,batch4_dado,batch5_dado, batch6_dado, batch7_boton;
    private Texture tex1_dado, tex2_dado, tex3_dado, tex4_dado, tex5_dado, tex6_dado,tex7_boton;

    private int random;
    private float x_dado=100;
    private float y_dado=390;
    private int ancho_dado=50;
    private int alto_dado=50;



    @Override
    public void create() {

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w / SCALE, h / SCALE);


        world = new World(new Vector2(0, 0f), false);
        b2dr = new Box2DDebugRenderer();

        plataform = createBox(0, 0, 64, 32, true);
        player = createBox(60, 100,20, 10, false);
        player2 = createBox(60, 100, 20, 10, false);
        player3 = createBox(60, 100, 20, 10, false);
        player4 = createBox(60, 100, 20, 10, false);

        batch = new SpriteBatch();
        tex = new Texture("alienYellow.png");

        batch2 = new SpriteBatch();
        tex2 = new Texture("alienBlue.png");

        batch3 = new SpriteBatch();
        tex3 = new Texture("alienPink.png");

        batch4 = new SpriteBatch();
        tex4 = new Texture("alienGreen.png");

        // inicio de los bach de los dados y texturas

        batch1_dado = new SpriteBatch();
        tex1_dado= new Texture("1dado.png");

        batch2_dado = new SpriteBatch();
        tex2_dado= new Texture("2dado.png");

        batch3_dado = new SpriteBatch();
        tex3_dado= new Texture("3dado.png");

        batch4_dado = new SpriteBatch();
        tex4_dado= new Texture("4dado.png");

        batch5_dado = new SpriteBatch();
        tex5_dado= new Texture("5dado.png");

        batch6_dado = new SpriteBatch();
        tex6_dado= new Texture("6dado.png");

        batch7_boton=new SpriteBatch();
        tex7_boton= new Texture("boton_dado.jpg");

        map = new TmxMapLoader().load("Maps/SYEmap.tmx");
        tmr = new OrthogonalTiledMapRenderer(map);

        TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("Collisions").getObjects());
    }

    @Override
    public void render() {
        update(Gdx.graphics.getDeltaTime());

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tmr.render();

        batch.begin();
        batch.draw(tex, player.getPosition().x * PPM - (tex.getWidth() /2) + 2, player.getPosition().y * PPM - (tex.getHeight() / 2) + 17);
        batch.end();

        batch2.begin();
        batch2.draw(tex2, player2.getPosition().x * PPM - (tex.getWidth() /2) + 2, player2.getPosition().y * PPM - (tex.getHeight() / 2) + 17);
        batch2.end();

        batch3.begin();
        batch3.draw(tex3, player3.getPosition().x * PPM - (tex.getWidth() /2) + 2, player3.getPosition().y * PPM - (tex.getHeight() / 2) + 17);
        batch3.end();

        batch4.begin();
        batch4.draw(tex4, player4.getPosition().x * PPM - (tex.getWidth() /2) + 2, player4.getPosition().y * PPM - (tex.getHeight() / 2) + 17);
        batch4.end();

        //Batch de los dados
        batch7_boton.begin();
        batch7_boton.draw(tex7_boton,100,390,60,50);
        batch7_boton.end();

        if(random==1){
            batch1_dado.begin();
            batch1_dado.draw(tex1_dado,30,390,50,50);
            batch1_dado.end();
        }else if(random==2){
            batch2_dado.begin();
            batch2_dado.draw(tex2_dado,30,390,50,50);
            batch2_dado.end();
        }else if(random==3){
            batch3_dado.begin();
            batch3_dado.draw(tex3_dado,30,390,50,50);
            batch3_dado.end();
        }else if(random==4){
            batch4_dado.begin();
            batch4_dado.draw(tex4_dado,30,390,50,50);
            batch4_dado.end();
        }else if(random==5){
            batch5_dado.begin();
            batch5_dado.draw(tex5_dado,30,390,50,50);
            batch5_dado.end();
        }else if(random==6){
            batch6_dado.begin();
            batch6_dado.draw(tex6_dado,30,390,50,50);
            batch6_dado.end();
        }

        //b2dr.render(world, camera.combined.scl(PPM));
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width / SCALE, height / SCALE);
    }

    @Override
    public void dispose() {
        world.dispose();
        b2dr.dispose();
        batch.dispose();
        batch2.dispose();
        batch3.dispose();
        batch4.dispose();
        tex.dispose();
        tex2.dispose();
        tex3.dispose();
        tex4.dispose();
        tmr.dispose();
        map.dispose();

    }

    public void update(float delta){
        world.step(1 / 60f, 1, 5);

        imputUpdate(delta);
        //cameraUpdate(delta);
        camera.update();
        tmr.setView(camera.combined, 1, 1, 720, 480);
        batch.setProjectionMatrix(camera.combined);
        batch2.setProjectionMatrix(camera.combined);
        batch3.setProjectionMatrix(camera.combined);
        batch4.setProjectionMatrix(camera.combined);

        //dados
        batch.setProjectionMatrix(camera.combined);
        batch1_dado.setProjectionMatrix(camera.combined);
        batch2_dado.setProjectionMatrix(camera.combined);
        batch3_dado.setProjectionMatrix(camera.combined);
        batch4_dado.setProjectionMatrix(camera.combined);
        batch5_dado.setProjectionMatrix(camera.combined);
        batch6_dado.setProjectionMatrix(camera.combined);
    }

    public void imputUpdate(float delta){
        int horizontalForce = 0, horizontalForce2 = 0, horizontalForce3 = 0, horizontalForce4 = 0;
        int verticalForce = 0, verticalForce2 = 0, verticalForce3 = 0, verticalForce4 = 0;

        //Teclas para el jugador 1;
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            horizontalForce -= 1;
            //player.setTransform(60 / PPM, 100 / PPM, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {horizontalForce +=1; }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {verticalForce += 1; }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){verticalForce -=1; }
        //Teclas para el jugador 2
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {verticalForce2 += 1;}
        if(Gdx.input.isKeyPressed(Input.Keys.S)) {verticalForce2 -= 1;}
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {horizontalForce2 += 1;}
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {horizontalForce2 -= 1;}

        //Teclas para el jugador3
        if(Gdx.input.isKeyPressed(Input.Keys.NUMPAD_8)) {verticalForce3 += 1;}
        if(Gdx.input.isKeyPressed(Input.Keys.NUMPAD_5)) {verticalForce3 -= 1;}
        if(Gdx.input.isKeyPressed(Input.Keys.NUMPAD_6)) {horizontalForce3 += 1;}
        if(Gdx.input.isKeyPressed(Input.Keys.NUMPAD_4)) {horizontalForce3 -= 1;}

        //Teclas para el jugador 4
        if(Gdx.input.isKeyPressed(Input.Keys.I)) {verticalForce4 += 1;}
        if(Gdx.input.isKeyPressed(Input.Keys.K)) {verticalForce4 -= 1;}
        if(Gdx.input.isKeyPressed(Input.Keys.L)) {horizontalForce4 += 1;}
        if(Gdx.input.isKeyPressed(Input.Keys.J)) {horizontalForce4 -= 1;}

        if(Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            System.out.print(player.getPosition().x);
            System.out.print(", ");
            System.out.print(player.getPosition().y);
            System.out.println();
            playerActivate();
        }

        player.setLinearVelocity(horizontalForce * 5, player.getLinearVelocity().y);
        player.setLinearVelocity(player.getLinearVelocity().x, verticalForce * 5);

        player2.setLinearVelocity(horizontalForce2 * 5, player2.getLinearVelocity().y);
        player2.setLinearVelocity(player2.getLinearVelocity().x, verticalForce2 * 5);

        player3.setLinearVelocity(horizontalForce3 * 5, player3.getLinearVelocity().y);
        player3.setLinearVelocity(player3.getLinearVelocity().x, verticalForce3 * 5);

        player4.setLinearVelocity(horizontalForce4 * 5, player4.getLinearVelocity().y);
        player4.setLinearVelocity(player4.getLinearVelocity().x, verticalForce4 * 5);

        //generador random de los dados
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        float x1=Gdx.input.getX();
        float y1=Gdx.input.getY();

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {

            if(x1>=x_dado && x1<160 && h-y1>y_dado && h-y1<447){
                if (Gdx.input.isTouched()) {
                    random = (int) (Math.random() * 6 + 1);
                }
            }

        }

    }

    public void cameraUpdate(float delta){
        Vector3 position = camera.position;
        position.x = player.getPosition().x * PPM ;
        position.y = player.getPosition().y * PPM;
        camera.position.set(position);

        camera.update();
    }

    public Body createBox(int x, int y, int width, int heigth, boolean isStatic){
        Body pBody;
        BodyDef def = new BodyDef();

        if(isStatic)
            def.type = BodyDef.BodyType.StaticBody;
        else
            def.type = BodyDef.BodyType.DynamicBody;

        def.position.set(x / PPM, y / PPM);
        def.fixedRotation = true;
        pBody = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((width / 2) / PPM, heigth / 2 / PPM);

        pBody.createFixture(shape, 1.0f);
        shape.dispose();

        return pBody;
    }

    public void playerActivate(){

        if (player.getPosition().x > 5.429 && player.getPosition().x < 8.640 && player.getPosition().y < 3.423 && player.getPosition().y > 2.037){
            player.setTransform(0, 0, 0);
        }

    }
}