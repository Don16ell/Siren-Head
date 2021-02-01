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
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
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