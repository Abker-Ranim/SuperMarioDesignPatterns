package mariopatterns.gameobject;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class CompositeGameObject implements GameObject {
    private final List<GameObject> children = new ArrayList<>();

    public void add(GameObject obj) { children.add(obj); }
    public void remove(GameObject obj) { children.remove(obj); }
    public void clear() { children.clear(); }

    @Override
    public void update() {
        children.removeIf(obj -> !obj.isAlive());
        children.forEach(GameObject::update);
    }

    @Override
    public void render(Graphics2D g) {
        children.forEach(obj -> obj.render(g));
    }

    @Override
    public boolean isAlive() { return true; }
}
