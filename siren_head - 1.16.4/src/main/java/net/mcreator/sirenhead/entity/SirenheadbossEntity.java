
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
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
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
public class SirenheadbossEntity extends SirenHeadModModElements.ModElement {
	public static EntityType entity = null;
	public SirenheadbossEntity(SirenHeadModModElements instance) {
		super(instance, 2);
		FMLJavaModLoadingContext.get().getModEventBus().register(new ModelRegisterHandler());
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void initElements() {
		entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER).setShouldReceiveVelocityUpdates(true)
				.setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new).immuneToFire().size(0.6f, 1.8f))
						.build("sirenheadboss").setRegistryName("sirenheadboss");
		elements.entities.add(() -> entity);
		elements.items.add(() -> new SpawnEggItem(entity, -12113920, -7536640, new Item.Properties().group(ItemGroup.MISC))
				.setRegistryName("sirenheadboss_spawn_egg"));
	}

	@SubscribeEvent
	public void addFeatureToBiomes(BiomeLoadingEvent event) {
		boolean biomeCriteria = false;
		if (new ResourceLocation("forest").equals(event.getName()))
			biomeCriteria = true;
		if (!biomeCriteria)
			return;
		event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(entity, 1, 1, 2));
	}

	@Override
	public void init(FMLCommonSetupEvent event) {
		DeferredWorkQueue.runLater(this::setupAttributes);
		EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				MonsterEntity::canMonsterSpawn);
		DungeonHooks.addDungeonMob(entity, 180);
	}
	private static class ModelRegisterHandler {
		@SubscribeEvent
		@OnlyIn(Dist.CLIENT)
		public void registerModels(ModelRegistryEvent event) {
			RenderingRegistry.registerEntityRenderingHandler(entity, renderManager -> {
				return new MobRenderer(renderManager, new Modelsiren_head_boss(), 0.5f) {
					@Override
					public ResourceLocation getEntityTexture(Entity entity) {
						return new ResourceLocation("siren_head_mod:textures/siren_head_boss.png");
					}
				};
			});
		}
	}
	private void setupAttributes() {
		AttributeModifierMap.MutableAttribute ammma = MobEntity.func_233666_p_();
		ammma = ammma.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.5);
		ammma = ammma.createMutableAttribute(Attributes.MAX_HEALTH, 600);
		ammma = ammma.createMutableAttribute(Attributes.ARMOR, 25);
		ammma = ammma.createMutableAttribute(Attributes.ATTACK_DAMAGE, 55);
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
			experienceValue = 1213;
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
			this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, PlayerEntity.class, true, true));
			this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, ServerPlayerEntity.class, true, true));
			this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, GolemEntity.class, true, true));
			this.targetSelector.addGoal(6, new NearestAttackableTargetGoal(this, AnimalEntity.class, true, true));
			this.targetSelector.addGoal(7, new HurtByTargetGoal(this));
			this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
			this.goalSelector.addGoal(9, new SwimGoal(this));
		}

		@Override
		public CreatureAttribute getCreatureAttribute() {
			return CreatureAttribute.UNDEFINED;
		}

		@Override
		public net.minecraft.util.SoundEvent getAmbientSound() {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("siren_head_mod:siren_head_sound"));
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

	// Made with Blockbench 3.7.5
	// Exported for Minecraft version 1.15
	// Paste this class into your mod and generate all required imports
	public static class Modelsiren_head_boss extends EntityModel<Entity> {
		private final ModelRenderer head;
		private final ModelRenderer r_siren;
		private final ModelRenderer l_siren;
		private final ModelRenderer body;
		private final ModelRenderer r_arm;
		private final ModelRenderer l_arm;
		private final ModelRenderer r_leg;
		private final ModelRenderer l_leg;
		public Modelsiren_head_boss() {
			textureWidth = 128;
			textureHeight = 128;
			head = new ModelRenderer(this);
			head.setRotationPoint(0.0F, -26.0F, 0.0F);
			head.setTextureOffset(0, 66).addBox(-2.0F, -13.0F, -2.0F, 4.0F, 13.0F, 4.0F, 0.0F, false);
			r_siren = new ModelRenderer(this);
			r_siren.setRotationPoint(0.0F, 45.0F, 0.0F);
			head.addChild(r_siren);
			r_siren.setTextureOffset(68, 49).addBox(-3.0F, -55.0F, -3.0F, 1.0F, 6.0F, 6.0F, 0.0F, false);
			r_siren.setTextureOffset(68, 18).addBox(-4.0F, -56.0F, -3.0F, 1.0F, 8.0F, 6.0F, 0.0F, false);
			r_siren.setTextureOffset(28, 71).addBox(-9.0F, -57.0F, -4.0F, 5.0F, 9.0F, 1.0F, 0.0F, false);
			r_siren.setTextureOffset(16, 71).addBox(-9.0F, -57.0F, 3.0F, 5.0F, 9.0F, 1.0F, 0.0F, false);
			r_siren.setTextureOffset(64, 9).addBox(-9.0F, -48.0F, -4.0F, 5.0F, 1.0F, 8.0F, 0.0F, false);
			r_siren.setTextureOffset(64, 0).addBox(-9.0F, -57.0F, -4.0F, 5.0F, 1.0F, 8.0F, 0.0F, false);
			l_siren = new ModelRenderer(this);
			l_siren.setRotationPoint(13.0F, 45.0F, 0.0F);
			head.addChild(l_siren);
			l_siren.setTextureOffset(36, 24).addBox(-11.0F, -55.0F, -3.0F, 1.0F, 6.0F, 6.0F, 0.0F, false);
			l_siren.setTextureOffset(68, 68).addBox(-10.0F, -56.0F, -3.0F, 1.0F, 8.0F, 6.0F, 0.0F, false);
			l_siren.setTextureOffset(38, 60).addBox(-9.0F, -57.0F, -4.0F, 5.0F, 9.0F, 1.0F, 0.0F, false);
			l_siren.setTextureOffset(0, 50).addBox(-9.0F, -57.0F, 3.0F, 5.0F, 9.0F, 1.0F, 0.0F, false);
			l_siren.setTextureOffset(20, 62).addBox(-9.0F, -48.0F, -4.0F, 5.0F, 1.0F, 8.0F, 0.0F, false);
			l_siren.setTextureOffset(60, 40).addBox(-9.0F, -57.0F, -4.0F, 5.0F, 1.0F, 8.0F, 0.0F, false);
			body = new ModelRenderer(this);
			body.setRotationPoint(0.0F, 24.0F, 0.0F);
			body.setTextureOffset(0, 0).addBox(-4.0F, -50.0F, -2.0F, 8.0F, 22.0F, 4.0F, 0.0F, false);
			r_arm = new ModelRenderer(this);
			r_arm.setRotationPoint(-6.0F, -25.0F, -1.0F);
			r_arm.setTextureOffset(52, 24).addBox(-2.0F, -1.0F, -1.0F, 4.0F, 20.0F, 4.0F, 0.0F, false);
			r_arm.setTextureOffset(52, 52).addBox(-2.0F, 19.0F, -1.0F, 4.0F, 20.0F, 4.0F, 0.0F, false);
			l_arm = new ModelRenderer(this);
			l_arm.setRotationPoint(6.0F, -25.0F, -1.0F);
			l_arm.setTextureOffset(48, 0).addBox(-2.0F, -1.0F, -1.0F, 4.0F, 20.0F, 4.0F, 0.0F, false);
			l_arm.setTextureOffset(12, 46).addBox(-2.0F, 19.0F, -1.0F, 4.0F, 20.0F, 4.0F, 0.0F, false);
			r_leg = new ModelRenderer(this);
			r_leg.setRotationPoint(-2.0F, -4.0F, -1.5F);
			r_leg.setTextureOffset(36, 36).addBox(-2.0F, 0.0F, -0.5F, 4.0F, 20.0F, 4.0F, 0.0F, false);
			r_leg.setTextureOffset(32, 0).addBox(-2.0F, 20.0F, -0.5F, 4.0F, 20.0F, 4.0F, 0.0F, false);
			l_leg = new ModelRenderer(this);
			l_leg.setRotationPoint(2.0F, -4.0F, 0.0F);
			l_leg.setTextureOffset(0, 26).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 20.0F, 4.0F, 0.0F, false);
			l_leg.setTextureOffset(20, 22).addBox(-2.0F, 20.0F, -2.0F, 4.0F, 20.0F, 4.0F, 0.0F, false);
		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,
				float alpha) {
			head.render(matrixStack, buffer, packedLight, packedOverlay);
			body.render(matrixStack, buffer, packedLight, packedOverlay);
			r_arm.render(matrixStack, buffer, packedLight, packedOverlay);
			l_arm.render(matrixStack, buffer, packedLight, packedOverlay);
			r_leg.render(matrixStack, buffer, packedLight, packedOverlay);
			l_leg.render(matrixStack, buffer, packedLight, packedOverlay);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}

		public void setRotationAngles(Entity e, float f, float f1, float f2, float f3, float f4) {
			this.head.rotateAngleY = f3 / (180F / (float) Math.PI);
			this.head.rotateAngleX = f4 / (180F / (float) Math.PI);
			this.r_leg.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
			this.l_leg.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
			this.l_arm.rotateAngleX = MathHelper.cos(f * 0.6662F) * f1;
			this.r_arm.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * f1;
		}
	}
}
