package main;

import org.w3c.dom.Attr;

import java.util.*;

/**
 * Created by bleeben on 9/12/2015.
 */
public enum Attribute {
    RED('R'), BLUE('B'), GREEN('G'), LIGHT('L'), DARK('D'), HEART('H'), JAMMER('J'), POISON('P'), CLEARED('?');
    private char representation;
    private Attribute(char representation) {
        this.representation=representation;
    }
    private static final Attribute[] values = values();
    private static final int size = values.length;
    private static final Random random = new Random();
    private static Set<Attribute> defaultAttributes;

    public static Attribute randomAttribute() {
        int index = random.nextInt(size);
        return values[index];
    }

    public static Set<Attribute> getDefaultAttributes(){
        if (Attribute.defaultAttributes==null)
            constructDefaultAttributes();
        return Attribute.defaultAttributes;
    }

    @Override
    public String toString() {
        return "("+representation+")";
    }

    private static void constructDefaultAttributes(){
        defaultAttributes = new HashSet<Attribute>();
        defaultAttributes.add(RED);
        defaultAttributes.add(BLUE);
        defaultAttributes.add(GREEN);
        defaultAttributes.add(LIGHT);
        defaultAttributes.add(DARK);
        defaultAttributes.add(HEART);
        defaultAttributes = Collections.unmodifiableSet(defaultAttributes);
    }
}
