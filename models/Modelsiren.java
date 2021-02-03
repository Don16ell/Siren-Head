// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

public static class Modelsiren extends EntityModel<Entity> {
	private final ModelRenderer bb_main;
	private final ModelRenderer body;
	private final ModelRenderer right_arm;
	private final ModelRenderer left_arm;
	private final ModelRenderer right_leg;
	private final ModelRenderer left_leg;

	public Modelsiren() {
		textureWidth = 64;
		textureHeight = 32;

		bb_main = new ModelRenderer(this);
		bb_main.setRotationPoint(0.0F, -16.0F, 0.0F);
		bb_main.setTextureOffset(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, -0.5F, false);
		bb_main.setTextureOffset(0, 16).addBox(-4.0F, -6.0F, -4.0F, 8.0F, 8.0F, 8.0F, -0.5F, false);

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 24.0F, 0.0F);
		body.setTextureOffset(32, 16).addBox(-4.0F, -38.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);

		right_arm = new ModelRenderer(this);
		right_arm.setRotationPoint(-5.0F, -13.0F, 0.0F);
		right_arm.setTextureOffset(56, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 30.0F, 2.0F, 0.0F, false);

		left_arm = new ModelRenderer(this);
		left_arm.setRotationPoint(5.0F, -13.0F, 0.0F);
		left_arm.setTextureOffset(56, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 30.0F, 2.0F, 0.0F, false);

		right_leg = new ModelRenderer(this);
		right_leg.setRotationPoint(-2.0F, -1.0F, 0.0F);
		right_leg.setTextureOffset(56, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 30.0F, 2.0F, 0.0F, false);

		left_leg = new ModelRenderer(this);
		left_leg.setRotationPoint(2.0F, -1.0F, 0.0F);
		left_leg.setTextureOffset(55, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 30.0F, 2.0F, 0.0F, false);
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		right_arm.render(matrixStack, buffer, packedLight, packedOverlay);
		left_arm.render(matrixStack, buffer, packedLight, packedOverlay);
		right_leg.render(matrixStack, buffer, packedLight, packedOverlay);
		left_leg.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
		this.bb_main.rotateAngleY = f3 / (180F / (float) Math.PI);
		this.bb_main.rotateAngleX = f4 / (180F / (float) Math.PI);
		this.right_arm.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * f1;
		this.left_leg.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
		this.left_arm.rotateAngleX = MathHelper.cos(f * 0.6662F) * f1;
		this.right_leg.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
	}
}