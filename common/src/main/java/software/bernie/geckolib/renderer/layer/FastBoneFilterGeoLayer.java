package software.bernie.geckolib.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import org.apache.logging.log4j.util.TriConsumer;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.base.GeoRenderer;

import java.util.List;
import java.util.function.Supplier;

/**
 * A more efficient version of {@link BoneFilterGeoLayer}
 * <p>
 * This version requires that you provide the list of bones to filter up-front, so that the bone hierarchy doesn't need to be traversed
 */
public class FastBoneFilterGeoLayer<T extends GeoAnimatable, O, R extends GeoRenderState> extends BoneFilterGeoLayer<T, O, R> {
	protected final Supplier<List<String>> boneSupplier;

	public FastBoneFilterGeoLayer(GeoRenderer<T, O, R> renderer) {
		this(renderer, List::of);
	}

	public FastBoneFilterGeoLayer(GeoRenderer<T, O, R> renderer, Supplier<List<String>> boneSupplier) {
		this(renderer, boneSupplier, (bone, animatable, partialTick) -> {});
	}

	public FastBoneFilterGeoLayer(GeoRenderer<T, O, R> renderer, Supplier<List<String>> boneSupplier, TriConsumer<GeoBone, R, Float> checkAndApply) {
		super(renderer, checkAndApply);

		this.boneSupplier = boneSupplier;
	}

	/**
	 * Return a list of bone names to grab to then be filtered
	 * <p>
	 * This is even more efficient if you use a cached list.
	 */
	protected List<String> getAffectedBones() {
		return boneSupplier.get();
	}

	@Override
	public void preRender(R renderState, PoseStack poseStack, BakedGeoModel bakedModel, @Nullable RenderType renderType, MultiBufferSource bufferSource,
						  @Nullable VertexConsumer buffer, int packedLight, int packedOverlay, int renderColor) {
		float partialTick = renderState.getGeckolibData(DataTickets.PARTIAL_TICK);

		for (String boneName : getAffectedBones()) {
			this.renderer.getGeoModel().getBone(boneName).ifPresent(bone -> checkAndApply(bone, renderState, partialTick));
		}
	}
}
