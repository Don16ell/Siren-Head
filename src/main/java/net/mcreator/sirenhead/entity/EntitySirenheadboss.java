
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
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.BossInfo;
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
public class EntitySirenheadboss extends ElementsSirenHeadMod.ModElement {
	public static final int ENTITYID = 3;
	public static final int ENTITYID_RANGED = 4;
	public EntitySirenheadboss(ElementsSirenHeadMod instance) {
		super(instance, 2);
	}

	@Override
	public void initElements() {
		elements.entities
				.add(() -> EntityEntryBuilder.create().entity(EntityCustom.class).id(new ResourceLocation("siren_head", "sirenheadboss"), ENTITYID)
						.name("sirenheadboss").tracker(64, 3, true).egg(-7905792, -7077888).build());
	}

	@Override
	public void init(FMLInitializationEvent event) {
		Biome[] spawnBiomes = {Biome.REGISTRY.getObject(new ResourceLocation("forest")),};
		EntityRegistry.addSpawn(EntityCustom.class, 1, 1, 2, EnumCreatureType.AMBIENT, spawnBiomes);
		DungeonHooks.addDungeonMob(new ResourceLocation("siren_head:sirenheadboss"), 180);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(EntityCustom.class, renderManager -> {
			return new RenderLiving(renderManager, new Modelsiren_head_boss_mob(), 0.5f) {
				protected ResourceLocation getEntityTexture(Entity entity) {
					return new ResourceLocation("siren_head:textures/siren_head_boss_mob.png");
				}
			};
		});
	}
	public static class EntityCustom extends EntityMob {
		public EntityCustom(World world) {
			super(world);
			setSize(0.6f, 2f);
			experienceValue = 750;
			this.isImmuneToFire = false;
			setNoAI(!true);
		}

		@Override
		protected void initEntityAI() {
			super.initEntityAI();
			this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.2, false));
			this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true, true));
			this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityPlayerMP.class, true, true));
			this.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntityAnimal.class, true, true));
			this.tasks.addTask(5, new EntityAIWander(this, 1));
			this.targetTasks.addTask(6, new EntityAIHurtByTarget(this, true));
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
					.getObject(new ResourceLocation("siren_head:siren_head_roar"));
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
				this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(25D);
			if (this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED) != null)
				this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
			if (this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH) != null)
				this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(650D);
			if (this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE) != null)
				this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(60D);
		}

		@Override
		public boolean isNonBoss() {
			return false;
		}
		private final BossInfoServer bossInfo = new BossInfoServer(this.getDisplayName(), BossInfo.Color.YELLOW, BossInfo.Overlay.NOTCHED_10);
		@Override
		public void addTrackingPlayer(EntityPlayerMP player) {
			super.addTrackingPlayer(player);
			this.bossInfo.addPlayer(player);
		}

		@Override
		public void removeTrackingPlayer(EntityPlayerMP player) {
			super.removeTrackingPlayer(player);
			this.bossInfo.removePlayer(player);
		}

		@Override
		public void onUpdate() {
			super.onUpdate();
			this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
		}
	}

	// Made with Blockbench 3.7.5
	// Exported for Minecraft version 1.12
	// Paste this class into your mod and generate all required imports
	public static class Modelsiren_head_boss_mob extends ModelBase {
		private final ModelRenderer head;
		private final ModelRenderer r_siren;
		private final ModelRenderer l_siren;
		private final ModelRenderer body;
		private final ModelRenderer r_arm;
		private final ModelRenderer l_arm;
		private final ModelRenderer r_leg;
		private final ModelRenderer l_leg;
		public Modelsiren_head_boss_mob() {
			textureWidth = 256;
			textureHeight = 256;
			head = new ModelRenderer(this);
			head.setRotationPoint(0.0F, -66.0F, 0.0F);
			head.cubeList.add(new ModelBox(head, 48, 16, -2.0F, -24.0F, -2.0F, 4, 24, 4, 0.0F, false));
			r_siren = new ModelRenderer(this);
			r_siren.setRotationPoint(0.0F, -15.0F, 0.0F);
			head.addChild(r_siren);
			r_siren.cubeList.add(new ModelBox(r_siren, 24, 16, -6.0F, -2.0F, -2.0F, 4, 4, 4, 0.0F, false));
			r_siren.cubeList.add(new ModelBox(r_siren, 96, 68, -14.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F, false));
			r_siren.cubeList.add(new ModelBox(r_siren, 104, 104, -26.0F, -6.0F, -6.0F, 12, 12, 12, 0.0F, false));
			l_siren = new ModelRenderer(this);
			l_siren.setRotationPoint(0.0F, -15.0F, 0.0F);
			head.addChild(l_siren);
			l_siren.cubeList.add(new ModelBox(l_siren, 16, 0, 2.0F, -2.0F, -2.0F, 4, 4, 4, 0.0F, false));
			l_siren.cubeList.add(new ModelBox(l_siren, 24, 0, 6.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F, false));
			l_siren.cubeList.add(new ModelBox(l_siren, 96, 44, 14.0F, -6.0F, -6.0F, 12, 12, 12, 0.0F, false));
			body = new ModelRenderer(this);
			body.setRotationPoint(0.0F, -43.0F, 0.0F);
			body.cubeList.add(new ModelBox(body, 68, 0, -6.0F, -24.0F, -4.0F, 12, 48, 8, 0.0F, false));
			r_arm = new ModelRenderer(this);
			r_arm.setRotationPoint(-8.0F, -66.0F, 0.0F);
			r_arm.cubeList.add(new ModelBox(r_arm, 24, 24, -2.0F, -1.0F, -4.0F, 4, 80, 8, 0.0F, false));
			l_arm = new ModelRenderer(this);
			l_arm.setRotationPoint(8.0F, -66.0F, 0.0F);
			l_arm.cubeList.add(new ModelBox(l_arm, 0, 0, -2.0F, -1.0F, -4.0F, 4, 80, 8, 0.0F, false));
			r_leg = new ModelRenderer(this);
			r_leg.setRotationPoint(-3.0F, -18.0F, 0.0F);
			r_leg.cubeList.add(new ModelBox(r_leg, 76, 76, -3.0F, -1.0F, -4.0F, 6, 60, 8, 0.0F, false));
			l_leg = new ModelRenderer(this);
			l_leg.setRotationPoint(3.0F, -18.0F, 0.0F);
			l_leg.cubeList.add(new ModelBox(l_leg, 48, 48, -3.0F, -1.0F, -4.0F, 6, 60, 8, 0.0F, false));
		}

		@Override
		public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
			head.render(f5);
			body.render(f5);
			r_arm.render(f5);
			l_arm.render(f5);
			r_leg.render(f5);
			l_leg.render(f5);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}

		public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
			super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
			this.head.rotateAngleY = f3 / (180F / (float) Math.PI);
			this.head.rotateAngleX = f4 / (180F / (float) Math.PI);
			this.r_leg.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
			this.l_leg.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
			this.l_arm.rotateAngleX = MathHelper.cos(f * 0.6662F) * f1;
			this.r_arm.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * f1;
		}
	}
}
