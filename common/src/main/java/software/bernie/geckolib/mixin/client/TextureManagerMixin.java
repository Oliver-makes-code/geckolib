package software.bernie.geckolib.mixin.client;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Injection into TextureManager's access point for runtime-derived textures to allow GeckoLib to swap them out with {@code AnimatableTexture} for animated texture purposes
 * <p>
 * Because AnimatedTexture extends {@link net.minecraft.client.renderer.texture.SimpleTexture SimpleTexture}, the replacement should be seamless
 */
@Mixin(value = TextureManager.class, priority = 2000)
public abstract class TextureManagerMixin {
	@Shadow protected abstract TextureContents loadContentsSafe(ResourceLocation textureId, ReloadableTexture texture);

	@Shadow public abstract void register(ResourceLocation path, AbstractTexture texture);

	@WrapOperation(method = "getTexture(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/texture/AbstractTexture;",
			at = @At(value = "NEW", target = "(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/texture/SimpleTexture;"),
			require = 0)
	private SimpleTexture geckolib$replaceAnimatableTexture(ResourceLocation location, Operation<SimpleTexture> original) {
		// TODO reinstate
		/*AnimatableTexture animatableTexture = new AnimatableTexture(location);

		TextureContents contents = loadContentsSafe(location, animatableTexture);

		if (animatableTexture.isAnimated()) {
			animatableTexture.apply(contents);
			register(location, animatableTexture);

			return animatableTexture;
		}*/

		return original.call(location);
	}

	@WrapWithCondition(method = "getTexture(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/texture/AbstractTexture;",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureManager;registerAndLoad(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/client/renderer/texture/ReloadableTexture;)V"),
			require = 0)
	private boolean geckolib$skipAnimatableTextureRegistration(TextureManager textureManager, ResourceLocation id, ReloadableTexture texture) {
		return true;//!(texture instanceof AnimatableTexture);
	}
}
