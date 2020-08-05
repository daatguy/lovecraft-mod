package daatguy.lovecraft.world.leyline;

import java.awt.Color;
import java.util.UUID;

public class FogAffecter {
	
	public Color color;
	public float weight;
	public UUID entityUUID;
	
	public FogAffecter(Color color, float weight, UUID entityUUID) {
		this.color = color;
		this.weight = weight;
		this.entityUUID = entityUUID;
	}
}