package terrains;

import renderEngine.Loader;
import textures.ModelTexture;

public class RoadTerrain extends Terrain {

	public RoadTerrain(float gridX, float gridZ, Loader loader, ModelTexture texture) {
		super(gridX, gridZ, loader, texture,2710);
	}

}
