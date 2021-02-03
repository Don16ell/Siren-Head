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
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
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