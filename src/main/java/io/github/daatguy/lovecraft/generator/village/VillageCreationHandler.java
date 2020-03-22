package io.github.daatguy.lovecraft.generator.village;

import java.util.List;
import java.util.Random;

import net.minecraft.util.EnumFacing;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces.PieceWeight;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;
import net.minecraft.world.gen.structure.StructureVillagePieces.Village;
import net.minecraftforge.fml.common.registry.VillagerRegistry.IVillageCreationHandler;

public class VillageCreationHandler implements IVillageCreationHandler {

	public Class<? extends Village> villageClass;
	public int weight;
	public int limitPerVillage;

	/**
	 * Handles registering village pieces
	 * @param villageClass
	 * @param weight
	 * @param limitPerVillage
	 */
	public VillageCreationHandler(Class<? extends Village> villageClass,
			int weight, int limitPerVillage) {
		this.villageClass = villageClass;
		this.weight = weight;
		this.limitPerVillage = limitPerVillage;
	}

	@Override
	public PieceWeight getVillagePieceWeight(Random random, int i) {
		return new PieceWeight(getComponentClass(), weight, limitPerVillage);
	}

	@Override
	public Class<? extends Village> getComponentClass() {
		return villageClass;
	}

	@Override
	public Village buildComponent(PieceWeight parPieceWeight, Start parStart,
			List<StructureComponent> parPiecesList, Random parRand,
			int parMinX, int parMinY, int parMinZ, EnumFacing parFacing,
			int parType) {
		if (this.villageClass == VillageOpiumDen.class) {
			StructureBoundingBox structBB = StructureBoundingBox
					.getComponentToAddBoundingBox(parMinX, parMinY, parMinZ, 0,
							0, 0, 9, 7, 12, parFacing);
			return new VillageOpiumDen(parStart, parType, parRand, structBB,
					parFacing);
		} else {
			return null;
		}
	}

}
