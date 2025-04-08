package software.bernie.geckolib.loading.math.value;

import software.bernie.geckolib.animatable.processing.AnimationState;
import software.bernie.geckolib.loading.math.MathValue;

import java.util.Set;

/**
 * {@link MathValue} value supplier
 *
 * <p>
 * <b>Contract:</b>
 * <br>
 * Negated equivalent of the stored value; returning a positive number if the stored value is negative, or a negative value if the stored value is positive
 */
public record Negative(MathValue value) implements MathValue {
    @Override
    public double get(AnimationState<?> animationState) {
        return -this.value.get(animationState);
    }

    @Override
    public boolean isMutable() {
        return this.value.isMutable();
    }

    @Override
    public Set<Variable> getUsedVariables() {
        return this.value.getUsedVariables();
    }

    @Override
    public String toString() {
        if (this.value instanceof Constant)
            return "-" + this.value;

        return "-" + "(" + this.value + ")";
    }
}
