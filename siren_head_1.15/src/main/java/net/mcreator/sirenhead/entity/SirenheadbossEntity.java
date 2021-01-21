
package net.mcreator.sirenhead.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.server.ServerBossInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.World;
import net.minecraft.world.BossInfo;
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
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.Entity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.MobRenderer;

import net.mcreator.sirenhead.SirenHeadModElements;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;

@SirenHeadModElements.ModElement.Tag
public class SirenheadbossEntity extends SirenHeadModElements.ModElement {
	public static EntityType entity = null;
	public SirenheadbossEntity(SirenHeadModElements instance) {
		super(instance, 2);
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
	}

	@Override
	public void initElements() {
		entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.AMBIENT).setShouldReceiveVelocityUpdates(true)
				.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new).size(0.6f, 2.5f)).build("sirenheadboss")
						.setRegistryName("sirenheadboss");
		elements.entities.add(() -> entity);
		elements.items.add(() -> new SpawnEggItem(entity, -7905792, -7077888, new Item.Properties().group(ItemGroup.MISC))
				.setRegistryName("sirenheadboss_spawn_egg"));
	}

	@Override
	public void init(FMLCommonSetupEvent event) {
		for (Biome biome : ForgeRegistries.BIOMES.getValues()) {
			boolean biomeCriteria = false;
			if (ForgeRegistries.BIOMES.getKey(biome).equals(new ResourceLocation("forest")))
				biomeCriteria = true;
			if (!biomeCriteria)
				continue;
			biome.getSpawns(EntityClassification.AMBIENT).add(new Biome.SpawnListEntry(entity, 1, 1, 2));
		}
		EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canSpawnOn);
		DungeonHooks.addDungeonMob(entity, 180);
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void registerModels(ModelRegistryEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(entity, renderManager -> {
			return new MobRenderer(renderManager, new Modelsiren_head_boss(), 0.5f) {
				@Override
				public ResourceLocation getEntityTexture(Entity entity) {
					return new ResourceLocation("siren_head:textures/siren_head_boss.png");
				}
			};
		});
	}
	public static class CustomEntity extends MonsterEntity {
		public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
			this(entity, world);
		}

		public CustomEntity(EntityType<CustomEntity> type, World world) {
			super(type, world);
			experienceValue = 689;
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
			this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, PlayerEntity.class, true, true));
			this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, ServerPlayerEntity.class, true, true));
			this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, AnimalEntity.class, true, true));
			this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 1));
			this.targetSelector.addGoal(6, new HurtByTargetGoal(this).setCallsForHelp(this.getClass()));
			this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
			this.goalSelector.addGoal(8, new SwimGoal(this));
		}

		@Override
		public CreatureAttribute getCreatureAttribute() {
			return CreatureAttribute.UNDEFINED;
		}

		@Override
		public net.minecraft.util.SoundEvent getAmbientSound() {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("siren_head:siren_head_roar"));
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

		@Override
		protected void registerAttributes() {
			super.registerAttributes();
			if (this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED) != null)
				this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3);
			if (this.getAttribute(SharedMonsterAttributes.MAX_HEALTH) != null)
				this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(600);
			if (this.getAttribute(SharedMonsterAttributes.ARMOR) != null)
				this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(25);
			if (this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE) == null)
				this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
			this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(55);
			if (this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE) == null)
				this.getAttributes().registerAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
			this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(2D);
			if (this.getAttribute(SharedMonsterAttributes.ATTACK_KNOCKBACK) == null)
				this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_KNOCKBACK);
			this.getAttribute(SharedMonsterAttributes.ATTACK_KNOCKBACK).setBaseValue(2D);
		}

		@Override
		public boolean isNonBoss() {
			return false;
		}
		private final ServerBossInfo bossInfo = new ServerBossInfo(this.getDisplayName(), BossInfo.Color.YELLOW, BossInfo.Overlay.NOTCHED_10);
		@Override
		public void addTrackingPlayer(ServerPlayerEntity player) {
			super.addTrackingPlayer(player);
			this.bossInfo.addPlayer(player);
		}

		@Override
		public void removeTrackingPlayer(ServerPlayerEntity player) {
			super.removeTrackingPlayer(player);
			this.bossInfo.removePlayer(player);
		}

		@Override
		public void updateAITasks() {
			super.updateAITasks();
			this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
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
			this.l_arm.rotateAngleY = MathHelper.cos(f * 0.6662F) * f1;
			this.r_arm.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * f1;
		}
	}
}
