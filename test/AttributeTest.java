import main.Attribute;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by bleeben on 9/13/2015.
 */
public class AttributeTest {

    @Test
    public void testRandomAttribute() throws Exception {
        Assert.assertNotNull(Attribute.randomAttribute());
    }

    @Test
    public void testDefaultAttributes() throws Exception {
        Attribute[] validAttributes = new Attribute[]{
                Attribute.RED, Attribute.BLUE, Attribute.GREEN,
                Attribute.LIGHT, Attribute.DARK, Attribute.HEART};
        Attribute[] extraAttributes = new Attribute[]{
                Attribute.JAMMER, Attribute.POISON, Attribute.CLEARED};
        for (Attribute element:validAttributes){
            Assert.assertTrue(Attribute.getDefaultAttributes().contains(element));
        }
        for (Attribute element:extraAttributes){
            Assert.assertFalse(Attribute.getDefaultAttributes().contains(element));
        }
    }
}