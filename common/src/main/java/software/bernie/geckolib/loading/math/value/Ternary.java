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
 * Returns one of two stored values dependent on the result of the stored condition value.
 * This returns such that a non-zero result from the condition will return the <b>true</b> stored value, otherwise returning the <b>false</b> stored value
 */
public record Ternary(MathValue condition, MathValue trueValue, MathValue falseValue, Set<Variable> usedVariables) implements MathValue {
    public Ternary(MathValue condition, MathValue trueValue, MathValue falseValue) {
        this(condition, trueValue, falseValue, MathValue.collectUsedVariables(condition, trueValue, falseValue));
    }

    @Override
    public double get(AnimationState<?> animationState) {
        return this.condition.get(animationState) != 0 ? this.trueValue.get(animationState) : this.falseValue.get(animationState);
    }

    @Override
    public boolean isMutable() {
        return this.condition.isMutable() || this.trueValue.isMutable() || this.falseValue.isMutable();
    }

    @Override
    public String toString() {
        return this.condition.toString() + " ? " + this.trueValue.toString() + " : " + this.falseValue.toString();
    }
}
