package software.bernie.geckolib.animation.keyframe;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import software.bernie.geckolib.animation.EasingType;
import software.bernie.geckolib.loading.math.MathValue;
import software.bernie.geckolib.loading.math.value.Variable;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Animation keyframe data
 *
 * @param length The length (in ticks) the keyframe lasts for
 * @param startValue The value to start the keyframe's transformation with
 * @param endValue The value to end the keyframe's transformation with
 * @param easingType The {@code EasingType} to use for transformations
 * @param easingArgs The arguments to provide to the easing calculation
 */
public record Keyframe<T extends MathValue>(double length, T startValue, T endValue, EasingType easingType, List<T> easingArgs) {
	public Keyframe(double length, T startValue, T endValue) {
		this(length, startValue, endValue, EasingType.LINEAR);
	}

	public Keyframe(double length, T startValue, T endValue, EasingType easingType) {
		this(length, startValue, endValue, easingType, new ObjectArrayList<>(0));
	}

	/**
	 * Extract and collect all {@link Variable}s used in this keyframe
	 */
	public Set<Variable> getUsedVariables() {
		Set<Variable> usedVariables = new ReferenceOpenHashSet<>();

		if (this.startValue.isMutable())
			usedVariables.addAll(this.startValue.getUsedVariables());

		if (this.endValue.isMutable())
			usedVariables.addAll(this.endValue.getUsedVariables());

		for (MathValue easingArg : this.easingArgs) {
			if (easingArg.isMutable())
				usedVariables.addAll(easingArg.getUsedVariables());
		}

		return usedVariables;
	}

	/**
	 * Extract and collect all {@link Variable}s used in the given {@link MathValue}
	 */
	private static void findVariablesFromMathValue(MathValue mathValue, Set<Variable> variables) {
		if (!mathValue.isMutable())
			return;

		variables.addAll(mathValue.getUsedVariables());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.length, this.startValue, this.endValue, this.easingType, this.easingArgs);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null || getClass() != obj.getClass())
			return false;

		return hashCode() == obj.hashCode();
	}
}
