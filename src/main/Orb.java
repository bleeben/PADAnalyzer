package main;

import org.w3c.dom.Attr;

/**
 * Created by bleeben on 9/12/2015.
 */
public class Orb {
    private Attribute attribute;
    private boolean isHidden;
    private boolean isLocked;
    private boolean isEnhance;

    public Orb() {
        this(Attribute.CLEARED);
    }

    public Orb(Attribute attribute) {
        this(attribute, false, false, false);
    }

    public Orb(Attribute attribute, boolean isHidden, boolean isLocked, boolean isEnhance) {
        this.attribute = attribute;
        this.isHidden = isHidden;
        this.isLocked = isLocked;
        this.isEnhance = isEnhance;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setIsHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public boolean isEnhance() {
        return isEnhance;
    }

    public void setIsEnhance(boolean isEnhance) {
        this.isEnhance = isEnhance;
    }

    public void clearOrb() {
        setAttribute(Attribute.CLEARED);
        setIsHidden(false);
        setIsLocked(false);
        setIsEnhance(false);
    }

    public boolean isCleared() {
        return getAttribute() == Attribute.CLEARED;
    }

    @Override
    public String toString() {
        String result = "{"+attribute;
        if (isEnhance)
            result += "+";
        else
            result += " ";
        // TODO locked and hidden have no implementation yet
        result += "}";
        return result;
    }
}
