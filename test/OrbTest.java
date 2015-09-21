import main.Attribute;
import main.Orb;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by bleeben on 9/13/2015.
 */
public class OrbTest {

    @Test
    public void testDefaultConstructor() throws Exception {
        Orb orb = new Orb();
        Assert.assertTrue(orb.isCleared());
    }

    @Test
    public void testClearOrb() throws Exception {
        Orb orb = new Orb(Attribute.BLUE);
        orb.clearOrb();
        //Assert.assertEquals(orb.getAttribute(), main.Attribute.CLEARED);
        Assert.assertEquals(orb.isEnhance(), false);
        Assert.assertEquals(orb.isLocked(), false);
        Assert.assertEquals(orb.isHidden(), false);
    }

    @Test
    public void testEquals() throws Exception {
        Orb orb1 = new Orb(Attribute.BLUE);
        Orb orb2 = new Orb(Attribute.BLUE);
        orb2.setIsEnhance(true);
        Assert.assertNotEquals(orb1, orb2);
        orb2.setIsEnhance(false);
        Assert.assertNotEquals(orb1,orb2);
    }
}