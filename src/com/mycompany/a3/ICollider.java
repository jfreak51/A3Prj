package com.mycompany.a3;

import java.util.ArrayList;

public interface ICollider {
	public boolean collidesWith(GameObject otherObject);
	public void handleCollision(GameObject otherObject, GameWorld gw);
}
