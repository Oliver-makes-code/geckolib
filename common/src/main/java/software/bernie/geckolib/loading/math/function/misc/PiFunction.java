package software.bernie.geckolib.loading.math.function.misc;

import software.bernie.geckolib.animatable.processing.AnimationState;
import software.bernie.geckolib.loading.math.MathValue;
import software.bernie.geckolib.loading.math.function.MathFunction;
import software.bernie.geckolib.loading.math.value.Constant;

/**
 * {@link MathFunction} value supplier
 *
 * <p>
 * <b>Contract:</b>
 * <br>
 * Returns <a href="https://en.wikipedia.org/wiki/Pi">PI</a>
 */
public final class PiFunction extends MathFunction {
    public PiFunction(MathValue... values) {
        super(values);
    }

    @Override
    public String getName() {
        return "math.pi";
    }

    @Override
    public double compute(AnimationState<?> animationState) {
        return Math.PI;
    }

    @Override
    public boolean isMutable(MathValue... values) {
        return false;
    }

    @Override
    public int getMinArgs() {
        return 0;
    }

    @Override
    public MathValue[] getArgs() {
        return new MathValue[] {new Constant(Math.PI)};
    }
}
