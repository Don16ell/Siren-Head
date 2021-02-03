
package net.mcreator.sirenhead.entity;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.common.DungeonHooks;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.World;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelBase;

import net.mcreator.sirenhead.ElementsSirenHeadMod;

@ElementsSirenHeadMod.ModElement.Tag
public class EntityCartoondog extends ElementsSirenHeadMod.ModElement {
	public static final int ENTITYID = 7;
	public static final int ENTITYID_RANGED = 8;
	public EntityCartoondog(ElementsSirenHeadMod instance) {
		super(instance, 5);
	}

	@Override
	public void initElements() {
		elements.entities
				.add(() -> EntityEntryBuilder.create().entity(EntityCustom.class).id(new ResourceLocation("siren_head", "cartoondog"), ENTITYID)
						.name("cartoondog").tracker(64, 3, true).egg(-16777216, -10092544).build());
	}

	@Override
	public void init(FMLInitializationEvent event) {
		Biome[] spawnBiomes = {Biome.REGISTRY.getObject(new ResourceLocation("forest")),};
		EntityRegistry.addSpawn(EntityCustom.class, 1, 1, 2, EnumCreatureType.AMBIENT, spawnBiomes);
		DungeonHooks.addDungeonMob(new ResourceLocation("siren_head:cartoondog"), 180);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(EntityCustom.class, renderManager -> {
			return new RenderLiving(renderManager, new Modelcartoon_dog(), 0.5f) {
				protected ResourceLocation getEntityTexture(Entity entity) {
					return new ResourceLocation("siren_head:textures/cartoon_dog.png");
				}
			};
		});
	}
	public static class EntityCustom extends EntityMob {
		public EntityCustom(World world) {
			super(world);
			setSize(0.6f, 1.8f);
			experienceValue = 5443;
			this.isImmuneToFire = true;
			setNoAI(!true);
		}

		@Override
		protected void initEntityAI() {
			super.initEntityAI();
			this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.2, false));
			this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityAnimal.class, true, true));
			this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true, true));
			this.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntityPlayerMP.class, true, true));
			this.tasks.addTask(5, new EntityAIWander(this, 1));
			this.targetTasks.addTask(6, new EntityAIHurtByTarget(this, false));
			this.tasks.addTask(7, new EntityAILookIdle(this));
			this.tasks.addTask(8, new EntityAISwimming(this));
		}

		@Override
		public EnumCreatureAttribute getCreatureAttribute() {
			return EnumCreatureAttribute.UNDEFINED;
		}

		@Override
		protected Item getDropItem() {
			return null;
		}

		@Override
		public net.minecraft.util.SoundEvent getAmbientSound() {
			return (net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY
					.getObject(new ResourceLocation("siren_head:cartoon_dog_song"));
		}

		@Override
		public net.minecraft.util.SoundEvent getHurtSound(DamageSource ds) {
			return (net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.generic.hurt"));
		}

		@Override
		public net.minecraft.util.SoundEvent getDeathSound() {
			return (net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.generic.death"));
		}

		@Override
		protected float getSoundVolume() {
			return 1.0F;
		}

		@Override
		public boolean attackEntityFrom(DamageSource source, float amount) {
			if (source.getImmediateSource() instanceof EntityArrow)
				return false;
			if (source == DamageSource.FALL)
				return false;
			if (source == DamageSource.DROWN)
				return false;
			return super.attackEntityFrom(source, amount);
		}

		@Override
		protected void applyEntityAttributes() {
			super.applyEntityAttributes();
			if (this.getEntityAttribute(SharedMonsterAttributes.ARMOR) != null)
				this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(50D);
			if (this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED) != null)
				this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6D);
			if (this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH) != null)
				this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(950D);
			if (this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE) != null)
				this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(80D);
		}
	}

	// Made with Blockbench 3.7.5
	// Exported for Minecraft version 1.12
	// Paste this class into your mod and generate all required imports
	public static class Modelcartoon_dog extends ModelBase {
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
			body.cubeList.add(new ModelBox(body, 0, 24, -4.5F, -44.0F, -5.0F, 9, 19, 6, 0.0F, false));
			rightleg = new ModelRenderer(this);
			rightleg.setRotationPoint(3.0F, 3.0F, 0.0F);
			setRotationAngle(rightleg, 0.0F, -0.3491F, 0.0F);
			rightleg.cubeList.add(new ModelBox(rightleg, 30, 59, -2.0152F, -4.0F, -2.1736F, 3, 13, 2, 0.0F, false));
			rightleg3 = new ModelRenderer(this);
			rightleg3.setRotationPoint(-1.0152F, 10.0F, -0.1736F);
			rightleg.addChild(rightleg3);
			rightleg3.cubeList.add(new ModelBox(rightleg3, 20, 49, -0.9848F, -2.0F, -1.8263F, 3, 13, 2, 0.0F, false));
			rightleg3.cubeList.add(new ModelBox(rightleg3, 62, 62, -1.3321F, 9.0F, -3.796F, 4, 2, 4, 0.0F, false));
			leftleg = new ModelRenderer(this);
			leftleg.setRotationPoint(-3.0F, 3.0F, 0.0F);
			setRotationAngle(leftleg, 0.0F, 0.1745F, 0.0F);
			leftleg.cubeList.add(new ModelBox(leftleg, 10, 49, -0.9848F, -4.0F, -2.1736F, 3, 13, 2, 0.0F, false));
			leftleg2 = new ModelRenderer(this);
			leftleg2.setRotationPoint(0.0152F, 10.0F, -0.1736F);
			leftleg.addChild(leftleg2);
			leftleg2.cubeList.add(new ModelBox(leftleg2, 0, 49, -1.0152F, -2.0F, -1.8263F, 3, 13, 2, 0.0F, false));
			leftleg2.cubeList.add(new ModelBox(leftleg2, 60, 18, -1.6527F, 9.0F, -3.9696F, 4, 2, 4, 0.0F, false));
			head = new ModelRenderer(this);
			head.setRotationPoint(0.0F, -24.0F, 0.0F);
			head.cubeList.add(new ModelBox(head, 0, 0, -6.0F, -13.0F, -8.0F, 12, 12, 12, 0.0F, false));
			right_ear = new ModelRenderer(this);
			right_ear.setRotationPoint(-6.0F, -11.0F, -3.5F);
			head.addChild(right_ear);
			cube_r1 = new ModelRenderer(this);
			cube_r1.setRotationPoint(0.0F, -8.0F, 0.0F);
			right_ear.addChild(cube_r1);
			setRotationAngle(cube_r1, 0.0F, 0.0F, -0.6981F);
			cube_r1.cubeList.add(new ModelBox(cube_r1, 58, 42, -12.0622F, 0.5F, 0.0F, 6, 3, 3, 0.0F, false));
			cube_r2 = new ModelRenderer(this);
			cube_r2.setRotationPoint(0.0F, 0.0F, 0.0F);
			right_ear.addChild(cube_r2);
			setRotationAngle(cube_r2, 0.0F, 0.0F, 0.5236F);
			cube_r2.cubeList.add(new ModelBox(cube_r2, 60, 12, -6.0F, -3.0F, 0.0F, 6, 3, 3, 0.0F, false));
			left_ear = new ModelRenderer(this);
			left_ear.setRotationPoint(13.0F, -11.0F, -3.5F);
			head.addChild(left_ear);
			cube_r3 = new ModelRenderer(this);
			cube_r3.setRotationPoint(7.0F, 4.0F, 0.0F);
			left_ear.addChild(cube_r3);
			setRotationAngle(cube_r3, 0.0F, 0.0F, 0.6981F);
			cube_r3.cubeList.add(new ModelBox(cube_r3, 24, 24, -12.0622F, 0.5F, 0.0F, 6, 3, 3, 0.0F, false));
			cube_r4 = new ModelRenderer(this);
			cube_r4.setRotationPoint(-2.0F, -3.0F, 0.0F);
			left_ear.addChild(cube_r4);
			setRotationAngle(cube_r4, 0.0F, 0.0F, -0.5236F);
			cube_r4.cubeList.add(new ModelBox(cube_r4, 57, 0, -6.0F, -3.0F, 0.0F, 6, 3, 3, 0.0F, false));
			mouth = new ModelRenderer(this);
			mouth.setRotationPoint(0.0F, 0.0F, 0.0F);
			head.addChild(mouth);
			mouth.cubeList.add(new ModelBox(mouth, 36, 0, -3.0F, -8.0F, -17.0F, 6, 3, 9, 0.0F, false));
			mouth.cubeList.add(new ModelBox(mouth, 39, 15, -3.0F, -4.0F, -17.0F, 6, 2, 9, 0.0F, false));
			right_arm = new ModelRenderer(this);
			right_arm.setRotationPoint(-6.5F, -19.5F, -2.0F);
			setRotationAngle(right_arm, 0.0F, 0.0F, 0.0873F);
			right_arm.cubeList.add(new ModelBox(right_arm, 46, 46, -2.0F, -0.5F, -2.0F, 4, 25, 4, 0.0F, false));
			right_hand = new ModelRenderer(this);
			right_hand.setRotationPoint(-2.5F, 26.5F, 0.0F);
			right_arm.addChild(right_hand);
			setRotationAngle(right_hand, 0.0F, 0.0F, 0.0873F);
			right_hand.cubeList.add(new ModelBox(right_hand, 46, 34, -2.0304F, -2.3473F, -2.0F, 8, 4, 4, 0.0F, false));
			right_hand.cubeList.add(new ModelBox(right_hand, 0, 64, -2.0266F, 1.7399F, -1.0F, 2, 3, 2, 0.0F, false));
			right_hand.cubeList.add(new ModelBox(right_hand, 62, 53, 0.962F, 1.4784F, -1.0F, 2, 3, 2, 0.0F, false));
			right_hand.cubeList.add(new ModelBox(right_hand, 62, 48, 3.9506F, 1.2169F, -1.0F, 2, 3, 2, 0.0F, false));
			left_arm = new ModelRenderer(this);
			left_arm.setRotationPoint(6.5F, -19.5F, -2.0F);
			setRotationAngle(left_arm, 0.0F, 0.0F, -0.0873F);
			left_arm.cubeList.add(new ModelBox(left_arm, 30, 30, -2.0F, -0.5F, -2.0F, 4, 25, 4, 0.0F, false));
			left_hand = new ModelRenderer(this);
			left_hand.setRotationPoint(-2.5F, 26.5F, 0.0F);
			left_arm.addChild(left_hand);
			left_hand.cubeList.add(new ModelBox(left_hand, 42, 26, -1.4904F, -2.8473F, -2.0F, 8, 4, 4, 0.0F, false));
			left_hand.cubeList.add(new ModelBox(left_hand, 36, 0, 4.5096F, 0.7437F, -1.0F, 2, 3, 2, 0.0F, false));
			left_hand.cubeList.add(new ModelBox(left_hand, 0, 5, 1.5096F, 0.9784F, -1.0F, 2, 3, 2, 0.0F, false));
			left_hand.cubeList.add(new ModelBox(left_hand, 0, 0, -1.4904F, 1.2169F, -1.0F, 2, 3, 2, 0.0F, false));
			bb_main = new ModelRenderer(this);
			bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
			bb_main.cubeList.add(new ModelBox(bb_main, 8, 64, -1.0F, -48.0F, -3.0F, 2, 3, 2, 1.0F, false));
		}

		@Override
		public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
			body.render(f5);
			rightleg.render(f5);
			leftleg.render(f5);
			head.render(f5);
			right_arm.render(f5);
			left_arm.render(f5);
			bb_main.render(f5);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}

		public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
			super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
			this.right_arm.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * f1;
			this.leftleg.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
			this.head.rotateAngleY = f3 / (180F / (float) Math.PI);
			this.head.rotateAngleX = f4 / (180F / (float) Math.PI);
			this.rightleg.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
			this.left_arm.rotateAngleX = MathHelper.cos(f * 0.6662F) * f1;
		}
	}
}
