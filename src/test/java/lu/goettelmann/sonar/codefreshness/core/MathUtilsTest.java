package lu.goettelmann.sonar.codefreshness.core;

import org.junit.Assert;
import org.junit.Test;

public class MathUtilsTest {

    @Test
    public void testLog10() {
        float base = 10;
        double delta = 0.0001;
        Assert.assertEquals(0, MathUtils.log(1, base), delta);
        Assert.assertEquals(0.3010, MathUtils.log(2, base), delta);
        Assert.assertEquals(0.4771, MathUtils.log(3, base), delta);
        Assert.assertEquals(0.6021, MathUtils.log(4, base), delta);
        Assert.assertEquals(0.6990, MathUtils.log(5, base), delta);
        Assert.assertEquals(1, MathUtils.log(10, base), delta);
        Assert.assertEquals(2, MathUtils.log(100, base), delta);
        Assert.assertEquals(3, MathUtils.log(1000, base), delta);
    }

    @Test
    public void testLog2() {
        float base = 2;
        double delta = 0.0001;
        Assert.assertEquals(0, MathUtils.log(1, base), delta);
        Assert.assertEquals(1, MathUtils.log(2, base), delta);
        Assert.assertEquals(1.5850, MathUtils.log(3, base), delta);
        Assert.assertEquals(2, MathUtils.log(4, base), delta);
        Assert.assertEquals(2.3219, MathUtils.log(5, base), delta);
        Assert.assertEquals(3, MathUtils.log(8, base), delta);
        Assert.assertEquals(3.3219, MathUtils.log(10, base), delta);
        Assert.assertEquals(4, MathUtils.log(16, base), delta);
        Assert.assertEquals(5, MathUtils.log(32, base), delta);
        Assert.assertEquals(6, MathUtils.log(64, base), delta);
        Assert.assertEquals(6.6439, MathUtils.log(100, base), delta);
    }

}
