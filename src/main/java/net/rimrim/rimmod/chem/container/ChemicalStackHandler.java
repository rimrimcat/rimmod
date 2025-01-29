package net.rimrim.rimmod.chem.container;

public class ChemicalStackHandler {

    private final float volume; // m3

    // Is closed or not

    public ChemicalStackHandler() {
        this.volume = 1;
    }

    public ChemicalStackHandler(float volume) {
        this.volume = volume;
    }




    protected void onLoad() {}

    protected void onContentsChanged() {}

}
