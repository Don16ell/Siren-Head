
package net.mcreator.sirenhead.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.World;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.DamageSource;
import net.minecraft.network.IPacket;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.Entity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.MobRenderer;

import net.mcreator.sirenhead.SirenHeadModModElements;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;

@SirenHeadModModElements.ModElement.Tag
public class CartoondogEntity extends SirenHeadModModElements.ModElement {
	public static EntityType entity = null;
	public CartoondogEntity(SirenHeadModModElements instance) {
		super(instance, 4);
		FMLJavaModLoadingContext.get().getModEventBus().register(new ModelRegisterHandler());
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void initElements() {
		entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.AMBIENT).setShouldReceiveVelocityUpdates(true)
				.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new).immuneToFire().size(0.6f, 1.8f))
						.build("cartoondog").setRegistryName("cartoondog");
		elements.entities.add(() -> entity);
		elements.items.add(() -> new SpawnEggItem(entity, -16777216, -10092544, new Item.Properties().group(ItemGroup.MISC))
				.setRegistryName("cartoondog_spawn_egg"));
	}

	@SubscribeEvent
	public void addFeatureToBiomes(BiomeLoadingEvent event) {
		boolean biomeCriteria = false;
		if (new ResourceLocation("forest").equals(event.getName()))
			biomeCriteria = true;
		if (!biomeCriteria)
			return;
		event.getSpawns().getSpawner(EntityClassification.AMBIENT).add(new MobSpawnInfo.Spawners(entity, 1, 1, 2));
	}

	@Override
	public void init(FMLCommonSetupEvent event) {
		DeferredWorkQueue.runLater(this::setupAttributes);
		EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canSpawnOn);
		DungeonHooks.addDungeonMob(entity, 180);
	}
	private static class ModelRegisterHandler {
		@SubscribeEvent
		@OnlyIn(Dist.CLIENT)
		public void registerModels(ModelRegistryEvent event) {
			RenderingRegistry.registerEntityRenderingHandler(entity, renderManager -> {
				return new MobRenderer(renderManager, new Modelcartoon_dog(), 0.5f) {
					@Override
					public ResourceLocation getEntityTexture(Entity entity) {
						return new ResourceLocation("siren_head_mod:textures/cartoon_dog.png");
					}
				};
			});
		}
	}
	private void setupAttributes() {
		AttributeModifierMap.MutableAttribute ammma = MobEntity.func_233666_p_();
		ammma = ammma.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.6);
		ammma = ammma.createMutableAttribute(Attributes.MAX_HEALTH, 950);
		ammma = ammma.createMutableAttribute(Attributes.ARMOR, 50);
		ammma = ammma.createMutableAttribute(Attributes.ATTACK_DAMAGE, 80);
		ammma = ammma.createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 2);
		ammma = ammma.createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 2);
		GlobalEntityTypeAttributes.put(entity, ammma.create());
	}
	public static class CustomEntity extends MonsterEntity {
		public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
			this(entity, world);
		}

		public CustomEntity(EntityType<CustomEntity> type, World world) {
			super(type, world);
			experienceValue = 5443;
			setNoAI(false);
		}

		@Override
		public IPacket<?> createSpawnPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}

		@Override
		protected void registerGoals() {
			super.registerGoals();
			this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false));
			this.goalSelector.addGoal(2, new RandomWalkingGoal(this, 1));
			this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
			this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
			this.goalSelector.addGoal(5, new SwimGoal(this));
		}

		@Override
		public CreatureAttribute getCreatureAttribute() {
			return CreatureAttribute.UNDEFINED;
		}

		@Override
		public net.minecraft.util.SoundEvent getAmbientSound() {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("siren_head_mod:cartoon_dog_sound"));
		}

		@Override
		public net.minecraft.util.SoundEvent getHurtSound(DamageSource ds) {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
		}

		@Override
		public net.minecraft.util.SoundEvent getDeathSound() {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
		}

		@Override
		public boolean attackEntityFrom(DamageSource source, float amount) {
			if (source.getImmediateSource() instanceof ArrowEntity)
				return false;
			if (source == DamageSource.FALL)
				return false;
			if (source == DamageSource.DROWN)
				return false;
			return super.attackEntityFrom(source, amount);
		}
	}

	// Made with Blockbench 3.7.4
	// Exported for Minecraft version 1.15
	// Paste this class into your mod and generate all required imports
	public static class Modelcartoon_dog extends EntityModel<Entity> {
		private final ModelRenderer body;
		private final ModelRenderer rightleg;
		private final ModelRenderer rightleg3;
		private final ModelRenderer leftleg;
		private final ModelRenderer leftleg2;
		private final ModelRenderer head;
		private final ModelRenderer right_ear;
		private final ModelRenderer cube_r1;
		private final ModelRenderer cube_r2;
		private final ModelRenderer left_ear;
		private final ModelRenderer cube_r3;
		private final ModelRenderer cube_r4;
		private final ModelRenderer mouth;
		private final ModelRenderer right_arm;
		private final ModelRenderer right_hand;
		private final ModelRenderer left_arm;
		private final ModelRenderer left_hand;
		private final ModelRenderer bb_main;
		public Modelcartoon_dog() {
			textureWidth = 128;
			textureHeight = 128;
			body = new ModelRenderer(this);
			body.setRotationPoint(0.0F, 24.0F, 0.0F);
			body.setTextureOffset(0, 24).addBox(-4.5F, -44.0F, -5.0F, 9.0F, 19.0F, 6.0F, 0.0F, false);
			rightleg = new ModelRenderer(this);
			rightleg.setRotationPoint(3.0F, 3.0F, 0.0F);
			setRotationAngle(rightleg, 0.0F, -0.3491F, 0.0F);
			rightleg.setTextureOffset(30, 59).addBox(-2.0152F, -4.0F, -2.1736F, 3.0F, 13.0F, 2.0F, 0.0F, false);
			rightleg3 = new ModelRenderer(this);
			rightleg3.setRotationPoint(-1.0152F, 10.0F, -0.1736F);
			rightleg.addChild(rightleg3);
			rightleg3.setTextureOffset(20, 49).addBox(-0.9848F, -2.0F, -1.8263F, 3.0F, 13.0F, 2.0F, 0.0F, false);
			rightleg3.setTextureOffset(62, 62).addBox(-1.3321F, 9.0F, -3.796F, 4.0F, 2.0F, 4.0F, 0.0F, false);
			leftleg = new ModelRenderer(this);
			leftleg.setRotationPoint(-3.0F, 3.0F, 0.0F);
			setRotationAngle(leftleg, 0.0F, 0.1745F, 0.0F);
			leftleg.setTextureOffset(10, 49).addBox(-0.9848F, -4.0F, -2.1736F, 3.0F, 13.0F, 2.0F, 0.0F, false);
			leftleg2 = new ModelRenderer(this);
			leftleg2.setRotationPoint(0.0152F, 10.0F, -0.1736F);
			leftleg.addChild(leftleg2);
			leftleg2.setTextureOffset(0, 49).addBox(-1.0152F, -2.0F, -1.8263F, 3.0F, 13.0F, 2.0F, 0.0F, false);
			leftleg2.setTextureOffset(60, 18).addBox(-1.6527F, 9.0F, -3.9696F, 4.0F, 2.0F, 4.0F, 0.0F, false);
			head = new ModelRenderer(this);
			head.setRotationPoint(0.0F, -24.0F, 0.0F);
			head.setTextureOffset(0, 0).addBox(-6.0F, -13.0F, -8.0F, 12.0F, 12.0F, 12.0F, 0.0F, false);
			right_ear = new ModelRenderer(this);
			right_ear.setRotationPoint(-6.0F, -11.0F, -3.5F);
			head.addChild(right_ear);
			cube_r1 = new ModelRenderer(this);
			cube_r1.setRotationPoint(0.0F, -8.0F, 0.0F);
			right_ear.addChild(cube_r1);
			setRotationAngle(cube_r1, 0.0F, 0.0F, -0.6981F);
			cube_r1.setTextureOffset(58, 42).addBox(-12.0622F, 0.5F, 0.0F, 6.0F, 3.0F, 3.0F, 0.0F, false);
			cube_r2 = new ModelRenderer(this);
			cube_r2.setRotationPoint(0.0F, 0.0F, 0.0F);
			right_ear.addChild(cube_r2);
			setRotationAngle(cube_r2, 0.0F, 0.0F, 0.5236F);
			cube_r2.setTextureOffset(60, 12).addBox(-6.0F, -3.0F, 0.0F, 6.0F, 3.0F, 3.0F, 0.0F, false);
			left_ear = new ModelRenderer(this);
			left_ear.setRotationPoint(13.0F, -11.0F, -3.5F);
			head.addChild(left_ear);
			cube_r3 = new ModelRenderer(this);
			cube_r3.setRotationPoint(7.0F, 4.0F, 0.0F);
			left_ear.addChild(cube_r3);
			setRotationAngle(cube_r3, 0.0F, 0.0F, 0.6981F);
			cube_r3.setTextureOffset(24, 24).addBox(-12.0622F, 0.5F, 0.0F, 6.0F, 3.0F, 3.0F, 0.0F, false);
			cube_r4 = new ModelRenderer(this);
			cube_r4.setRotationPoint(-2.0F, -3.0F, 0.0F);
			left_ear.addChild(cube_r4);
			setRotationAngle(cube_r4, 0.0F, 0.0F, -0.5236F);
			cube_r4.setTextureOffset(57, 0).addBox(-6.0F, -3.0F, 0.0F, 6.0F, 3.0F, 3.0F, 0.0F, false);
			mouth = new ModelRenderer(this);
			mouth.setRotationPoint(0.0F, 0.0F, 0.0F);
			head.addChild(mouth);
			mouth.setTextureOffset(36, 0).addBox(-3.0F, -8.0F, -17.0F, 6.0F, 3.0F, 9.0F, 0.0F, false);
			mouth.setTextureOffset(39, 15).addBox(-3.0F, -4.0F, -17.0F, 6.0F, 2.0F, 9.0F, 0.0F, false);
			right_arm = new ModelRenderer(this);
			right_arm.setRotationPoint(-6.5F, -19.5F, -2.0F);
			setRotationAngle(right_arm, 0.0F, 0.0F, 0.0873F);
			right_arm.setTextureOffset(46, 46).addBox(-2.0F, -0.5F, -2.0F, 4.0F, 25.0F, 4.0F, 0.0F, false);
			right_hand = new ModelRenderer(this);
			right_hand.setRotationPoint(-2.5F, 26.5F, 0.0F);
			right_arm.addChild(right_hand);
			setRotationAngle(right_hand, 0.0F, 0.0F, 0.0873F);
			right_hand.setTextureOffset(46, 34).addBox(-2.0304F, -2.3473F, -2.0F, 8.0F, 4.0F, 4.0F, 0.0F, false);
			right_hand.setTextureOffset(0, 64).addBox(-2.0266F, 1.7399F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);
			right_hand.setTextureOffset(62, 53).addBox(0.962F, 1.4784F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);
			right_hand.setTextureOffset(62, 48).addBox(3.9506F, 1.2169F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);
			left_arm = new ModelRenderer(this);
			left_arm.setRotationPoint(6.5F, -19.5F, -2.0F);
			setRotationAngle(left_arm, 0.0F, 0.0F, -0.0873F);
			left_arm.setTextureOffset(30, 30).addBox(-2.0F, -0.5F, -2.0F, 4.0F, 25.0F, 4.0F, 0.0F, false);
			left_hand = new ModelRenderer(this);
			left_hand.setRotationPoint(-2.5F, 26.5F, 0.0F);
			left_arm.addChild(left_hand);
			left_hand.setTextureOffset(42, 26).addBox(-1.4904F, -2.8473F, -2.0F, 8.0F, 4.0F, 4.0F, 0.0F, false);
			left_hand.setTextureOffset(36, 0).addBox(4.5096F, 0.7437F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);
			left_hand.setTextureOffset(0, 5).addBox(1.5096F, 0.9784F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);
			left_hand.setTextureOffset(0, 0).addBox(-1.4904F, 1.2169F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);
			bb_main = new ModelRenderer(this);
			bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
			bb_main.setTextureOffset(8, 64).addBox(-1.0F, -48.0F, -3.0F, 2.0F, 3.0F, 2.0F, 1.0F, false);
		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,
				float alpha) {
			body.render(matrixStack, buffer, packedLight, packedOverlay);
			rightleg.render(matrixStack, buffer, packedLight, packedOverlay);
			leftleg.render(matrixStack, buffer, packedLight, packedOverlay);
			head.render(matrixStack, buffer, packedLight, packedOverlay);
			right_arm.render(matrixStack, buffer, packedLight, packedOverlay);
			left_arm.render(matrixStack, buffer, packedLight, packedOverlay);
			bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}

		public void setRotationAngles(Entity e, float f, float f1, float f2, float f3, float f4) {
			this.right_arm.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * f1;
			this.leftleg.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
			this.head.rotateAngleY = f3 / (180F / (float) Math.PI);
			this.head.rotateAngleX = f4 / (180F / (float) Math.PI);
			this.rightleg.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
			this.left_arm.rotateAngleX = MathHelper.cos(f * 0.6662F) * f1;
		}
	}
}
