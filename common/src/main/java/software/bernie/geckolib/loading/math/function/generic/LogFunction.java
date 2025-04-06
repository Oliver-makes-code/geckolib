package software.bernie.geckolib.loading.math.function.generic;

import software.bernie.geckolib.animatable.processing.AnimationState;
import software.bernie.geckolib.loading.math.MathValue;
import software.bernie.geckolib.loading.math.function.MathFunction;

/**
 * {@link MathFunction} value supplier
 *
 * <p>
 * <b>Contract:</b>
 * <br>
 * Returns the log value (euler base) of the input value
 */
public final class LogFunction extends MathFunction {
    private final MathValue value;

    public LogFunction(MathValue... values) {
        super(values);

        this.value = values[0];
    }

    @Override
    public String getName() {
        return "math.ln";
    }

    @Override
    public double compute(AnimationState<?> animationState) {
        return Math.log((float)this.value.get(animationState));
    }

    @Override
    public int getMinArgs() {
        return 1;
    }

    @Override
    public MathValue[] getArgs() {
        return new MathValue[] {this.value};
    }
}
