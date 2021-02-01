// Made with Blockbench 3.7.5
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

public class normal_siren_head extends EntityModel<Entity> {
	private final ModelRenderer head;
	private final ModelRenderer r_siren;
	private final ModelRenderer l_siren;
	private final ModelRenderer body;
	private final ModelRenderer rightArm;
	private final ModelRenderer leftArm;
	private final ModelRenderer rightLeg;
	private final ModelRenderer leftLeg;

	public normal_siren_head() {
		textureWidth = 64;
		textureHeight = 64;

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.setTextureOffset(32, 44).addBox(-2.0F, -43.0F, -2.0F, 4.0F, 12.0F, 4.0F, -0.5F, false);

		r_siren = new ModelRenderer(this);
		r_siren.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.addChild(r_siren);
		r_siren.setTextureOffset(32, 20).addBox(-7.0F, -41.0F, -3.0F, 6.0F, 6.0F, 6.0F, -0.5F, false);

		l_siren = new ModelRenderer(this);
		l_siren.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.addChild(l_siren);
		l_siren.setTextureOffset(32, 32).addBox(1.0F, -41.0F, -3.0F, 6.0F, 6.0F, 6.0F, -0.5F, false);

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, -14.0F, 0.0F);
		body.setTextureOffset(24, 0).addBox(-4.0F, -18.0F, -2.0F, 8.0F, 16.0F, 4.0F, 0.0F, false);

		rightArm = new ModelRenderer(this);
		rightArm.setRotationPoint(-3.0F, -12.0F, 0.0F);
		rightArm.setTextureOffset(24, 24).addBox(-3.0F, -20.0F, -1.0F, 2.0F, 36.0F, 2.0F, 0.0F, false);

		leftArm = new ModelRenderer(this);
		leftArm.setRotationPoint(5.0F, -12.0F, 0.0F);
		leftArm.setTextureOffset(16, 16).addBox(-1.0F, -20.0F, -1.0F, 2.0F, 36.0F, 2.0F, 0.0F, false);

		rightLeg = new ModelRenderer(this);
		rightLeg.setRotationPoint(-2.0F, -2.0F, 0.0F);
		rightLeg.setTextureOffset(8, 8).addBox(-1.0F, -14.0F, -1.0F, 2.0F, 40.0F, 2.0F, 0.0F, false);

		leftLeg = new ModelRenderer(this);
		leftLeg.setRotationPoint(2.0F, -2.0F, 0.0F);
		leftLeg.setTextureOffset(0, 0).addBox(-1.0F, -14.0F, -1.0F, 2.0F, 40.0F, 2.0F, 0.0F, false);
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		head.render(matrixStack, buffer, packedLight, packedOverlay);
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		rightArm.render(matrixStack, buffer, packedLight, packedOverlay);
		leftArm.render(matrixStack, buffer, packedLight, packedOverlay);
		rightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
		leftLeg.render(matrixStack, buffer, packedLight, packedOverlay);
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
		this.rightLeg.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
		this.rightArm.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * f1;
		this.leftArm.rotateAngleX = MathHelper.cos(f * 0.6662F) * f1;
		this.leftLeg.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
	}
}