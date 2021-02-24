package com.sye.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
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

import static com.sye.game.utils.Constants.PPM;

public class main extends ApplicationAdapter {

    private boolean DEBUG = false;
    private final float SCALE = 1.0f;

    private OrthographicCamera camera;

    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;

    private Box2DDebugRenderer b2dr;
    private World world;
    private Body player, plataform;

    private SpriteBatch batch;
    private Texture tex;

    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w / SCALE, h / SCALE);


        world = new World(new Vector2(0, 0f), false);
        b2dr = new Box2DDebugRenderer();

        player = createBox(60, 100,40, 60, false);
        plataform = createBox(0, 0, 64, 32, true);

        batch = new SpriteBatch();
        tex = new Texture("alienYellow.png");

        map = new TmxMapLoader().load("Maps/SYEmap.tmx");
        tmr = new OrthogonalTiledMapRenderer(map);
    }

    @Override
    public void render() {
        update(Gdx.graphics.getDeltaTime());

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tmr.render();

        batch.begin();
        batch.draw(tex, player.getPosition().x * PPM - (tex.getWidth() /2), player.getPosition().y * PPM - (tex.getHeight() / 2));
        batch.end();

        b2dr.render(world, camera.combined.scl(PPM));
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
        tex.dispose();
        tmr.dispose();
        map.dispose();
    }

    public void update(float delta){
        world.step(1 / 60f, 6, 2);

        imputUpdate(delta);
        //cameraUpdate(delta);
        camera.update();
        tmr.setView(camera.combined, 1, 1, 720, 480);
        batch.setProjectionMatrix(camera.combined);
    }

    public void imputUpdate(float delta){
        int horizontalForce = 0;
        int verticalForce = 0;

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            horizontalForce -= 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            horizontalForce +=1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            verticalForce += 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            verticalForce -=1;
        }

        player.setLinearVelocity(horizontalForce * 5, player.getLinearVelocity().y);
        player.setLinearVelocity(player.getLinearVelocity().x, verticalForce * 5);

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
}