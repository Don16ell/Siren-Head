
package net.mcreator.sirenhead.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.biome.Biome;
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
public class CartoondogEntity extends SirenHeadModElements.ModElement {
	public static EntityType entity = null;
	public CartoondogEntity(SirenHeadModElements instance) {
		super(instance, 5);
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
	}

	@Override
	public void initElements() {
		entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.AMBIENT).setShouldReceiveVelocityUpdates(true)
				.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new).immuneToFire().size(0.6f, 1.8f))
						.build("cartoondog").setRegistryName("cartoondog");
		elements.entities.add(() -> entity);
		elements.items.add(() -> new SpawnEggItem(entity, -16777216, -65536, new Item.Properties().group(ItemGroup.MISC))
				.setRegistryName("cartoondog_spawn_egg"));
	}

	@Override
	public void init(FMLCommonSetupEvent event) {
		for (Biome biome : ForgeRegistries.BIOMES.getValues()) {
			biome.getSpawns(EntityClassification.AMBIENT).add(new Biome.SpawnListEntry(entity, 1, 1, 2));
		}
		EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canSpawnOn);
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void registerModels(ModelRegistryEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(entity, renderManager -> {
			return new MobRenderer(renderManager, new cartoon_dog(), 0.5f) {
				@Override
				public ResourceLocation getEntityTexture(Entity entity) {
					return new ResourceLocation("siren_head:textures/cartoon_dog.png");
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
			experienceValue = 7868;
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
			this.goalSelector.addGoal(4, new RandomWalkingGoal(this, 1));
			this.targetSelector.addGoal(5, new HurtByTargetGoal(this));
			this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
			this.goalSelector.addGoal(7, new SwimGoal(this));
		}

		@Override
		public CreatureAttribute getCreatureAttribute() {
			return CreatureAttribute.UNDEFINED;
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
				this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6);
			if (this.getAttribute(SharedMonsterAttributes.MAX_HEALTH) != null)
				this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(950);
			if (this.getAttribute(SharedMonsterAttributes.ARMOR) != null)
				this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(100);
			if (this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE) == null)
				this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
			this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(75);
			if (this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE) == null)
				this.getAttributes().registerAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
			this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(115D);
			if (this.getAttribute(SharedMonsterAttributes.ATTACK_KNOCKBACK) == null)
				this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_KNOCKBACK);
			this.getAttribute(SharedMonsterAttributes.ATTACK_KNOCKBACK).setBaseValue(2D);
		}
	}

	// Made with Blockbench 3.7.4
	// Exported for Minecraft version 1.15
	// Paste this class into your mod and generate all required imports
	public class cartoon_dog extends EntityModel<Entity> {
		private final ModelRenderer unknown_bone;
		private final ModelRenderer body;
		private final ModelRenderer left_leg;
		private final ModelRenderer left_leg2;
		private final ModelRenderer right_leg;
		private final ModelRenderer right_leg2;
		private final ModelRenderer head;
		private final ModelRenderer right_ear;
		private final ModelRenderer right_ear2;
		private final ModelRenderer left_ear;
		private final ModelRenderer left_ear2;
		private final ModelRenderer mouth;
		private final ModelRenderer right_arm;
		private final ModelRenderer right_hand;
		private final ModelRenderer left_arm;
		private final ModelRenderer left_hand;
		public cartoon_dog() {
			textureWidth = 128;
			textureHeight = 128;
			unknown_bone = new ModelRenderer(this);
			unknown_bone.setRotationPoint(0.0F, 24.0F, 0.0F);
			unknown_bone.setTextureOffset(66, 25).addBox(-1.0F, -48.0F, -3.0F, 2.0F, 3.0F, 2.0F, 1.0F, false);
			body = new ModelRenderer(this);
			body.setRotationPoint(0.0F, 24.0F, 0.0F);
			body.setTextureOffset(0, 39).addBox(-4.5F, -44.0F, -5.0F, 9.0F, 19.0F, 6.0F, 0.0F, false);
			left_leg = new ModelRenderer(this);
			left_leg.setRotationPoint(3.0F, -1.0F, 0.0F);
			left_leg.setTextureOffset(64, 0).addBox(-2.0152F, 0.0F, -2.1736F, 3.0F, 13.0F, 2.0F, 0.0F, false);
			left_leg2 = new ModelRenderer(this);
			left_leg2.setRotationPoint(-1.0152F, 14.0F, -0.1736F);
			left_leg.addChild(left_leg2);
			left_leg2.setTextureOffset(20, 64).addBox(-0.9848F, -2.0F, -1.8263F, 3.0F, 13.0F, 2.0F, 0.0F, false);
			left_leg2.setTextureOffset(24, 39).addBox(-1.3321F, 9.0F, -3.796F, 4.0F, 2.0F, 4.0F, 0.0F, false);
			right_leg = new ModelRenderer(this);
			right_leg.setRotationPoint(-3.0F, -1.0F, 0.0F);
			right_leg.setTextureOffset(10, 64).addBox(-0.9848F, 0.0F, -2.1736F, 3.0F, 13.0F, 2.0F, 0.0F, false);
			right_leg2 = new ModelRenderer(this);
			right_leg2.setRotationPoint(0.0152F, 14.0F, -0.1736F);
			right_leg.addChild(right_leg2);
			right_leg2.setTextureOffset(0, 64).addBox(-1.0152F, -2.0F, -1.8263F, 3.0F, 13.0F, 2.0F, 0.0F, false);
			right_leg2.setTextureOffset(36, 25).addBox(-1.6527F, 9.0F, -3.9696F, 4.0F, 2.0F, 4.0F, 0.0F, false);
			head = new ModelRenderer(this);
			head.setRotationPoint(0.0F, 5.6667F, -0.6667F);
			head.setTextureOffset(0, 0).addBox(-6.0F, -42.6667F, -7.3333F, 12.0F, 12.0F, 12.0F, 0.0F, false);
			right_ear = new ModelRenderer(this);
			right_ear.setRotationPoint(-2.0F, -42.6667F, -0.8333F);
			head.addChild(right_ear);
			setRotationAngle(right_ear, 0.0F, 0.0F, -0.6109F);
			right_ear.setTextureOffset(40, 40).addBox(-2.4181F, -6.6489F, -2.5F, 2.0F, 6.0F, 2.0F, 0.0F, false);
			right_ear2 = new ModelRenderer(this);
			right_ear2.setRotationPoint(-9.784F, -44.2134F, -2.3333F);
			head.addChild(right_ear2);
			setRotationAngle(right_ear2, 0.0F, 0.0F, 0.6981F);
			right_ear2.setTextureOffset(0, 0).addBox(-1.0F, -4.5F, -1.0F, 2.0F, 9.0F, 2.0F, 0.0F, false);
			left_ear = new ModelRenderer(this);
			left_ear.setRotationPoint(1.0F, -37.6667F, -0.8333F);
			head.addChild(left_ear);
			setRotationAngle(left_ear, 0.0F, 0.0F, 0.6109F);
			left_ear.setTextureOffset(36, 0).addBox(-1.55F, -11.573F, -2.5F, 2.0F, 6.0F, 2.0F, 0.0F, false);
			left_ear2 = new ModelRenderer(this);
			left_ear2.setRotationPoint(3.0236F, -19.2538F, -1.5F);
			left_ear.addChild(left_ear2);
			setRotationAngle(left_ear2, 0.0F, 0.0F, 1.8762F);
			left_ear2.setTextureOffset(0, 24).addBox(7.1924F, -7.7929F, -1.0F, 2.0F, 9.0F, 2.0F, 0.0F, false);
			mouth = new ModelRenderer(this);
			mouth.setRotationPoint(0.0F, 0.0F, 0.0F);
			head.addChild(mouth);
			mouth.setTextureOffset(36, 36).addBox(-3.0F, -37.6667F, -19.3333F, 6.0F, 3.0F, 12.0F, 0.0F, false);
			mouth.setTextureOffset(0, 24).addBox(-3.0F, -34.6667F, -19.3333F, 6.0F, 3.0F, 12.0F, 0.0F, false);
			right_arm = new ModelRenderer(this);
			right_arm.setRotationPoint(-6.5F, -19.5F, -2.0F);
			setRotationAngle(right_arm, 0.0F, 0.0F, 0.0873F);
			right_arm.setTextureOffset(30, 51).addBox(-2.0F, -0.5F, -2.0F, 4.0F, 25.0F, 4.0F, 0.0F, false);
			right_hand = new ModelRenderer(this);
			right_hand.setRotationPoint(-2.5F, 26.5F, 0.0F);
			right_arm.addChild(right_hand);
			setRotationAngle(right_hand, 0.0F, 0.0F, 0.0873F);
			right_hand.setTextureOffset(46, 59).addBox(-2.0304F, -2.3473F, -2.0F, 8.0F, 4.0F, 4.0F, 0.0F, false);
			right_hand.setTextureOffset(64, 20).addBox(-2.0266F, 1.7399F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);
			right_hand.setTextureOffset(64, 15).addBox(0.962F, 1.4784F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);
			right_hand.setTextureOffset(60, 29).addBox(3.9506F, 1.2169F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);
			left_arm = new ModelRenderer(this);
			left_arm.setRotationPoint(6.5F, -19.5F, -2.0F);
			setRotationAngle(left_arm, 0.0F, 0.0F, -0.0873F);
			left_arm.setTextureOffset(48, 0).addBox(-2.0F, -0.5F, -2.0F, 4.0F, 25.0F, 4.0F, 0.0F, false);
			left_hand = new ModelRenderer(this);
			left_hand.setRotationPoint(-2.5F, 26.5F, 0.0F);
			left_arm.addChild(left_hand);
			left_hand.setTextureOffset(46, 51).addBox(-1.4904F, -2.8473F, -2.0F, 8.0F, 4.0F, 4.0F, 0.0F, false);
			left_hand.setTextureOffset(52, 29).addBox(4.5096F, 0.7437F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);
			left_hand.setTextureOffset(44, 31).addBox(1.5096F, 0.9784F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);
			left_hand.setTextureOffset(36, 31).addBox(-1.4904F, 1.2169F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);
		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,
				float alpha) {
			unknown_bone.render(matrixStack, buffer, packedLight, packedOverlay);
			body.render(matrixStack, buffer, packedLight, packedOverlay);
			left_leg.render(matrixStack, buffer, packedLight, packedOverlay);
			right_leg.render(matrixStack, buffer, packedLight, packedOverlay);
			head.render(matrixStack, buffer, packedLight, packedOverlay);
			right_arm.render(matrixStack, buffer, packedLight, packedOverlay);
			left_arm.render(matrixStack, buffer, packedLight, packedOverlay);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}

		public void setRotationAngles(Entity e, float f, float f1, float f2, float f3, float f4) {
			this.left_leg.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
			this.right_arm.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * f1;
			this.head.rotateAngleY = f3 / (180F / (float) Math.PI);
			this.head.rotateAngleX = f4 / (180F / (float) Math.PI);
			this.right_leg.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
			this.left_arm.rotateAngleX = MathHelper.cos(f * 0.6662F) * f1;
		}
	}
}
