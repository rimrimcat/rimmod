package net.rimrim.rimmod.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.rimrim.rimmod.RimMod;
import net.rimrim.rimmod.block.DebugInserterBlock;
import net.rimrim.rimmod.block.properties.InserterState;
import net.rimrim.rimmod.blockentity.DebugInserterBlockEntity;
import net.rimrim.rimmod.client.renderer.DebugInserterBER;
import net.rimrim.rimmod.client.ui.RenderableSlider;
import net.rimrim.rimmod.client.utils.TransformMap;
import net.rimrim.rimmod.client.utils.TransformValue;

public class DebugInserterControlScreen extends Screen {
    private final ResourceLocation BACKGROUND_LOCATION = ResourceLocation.fromNamespaceAndPath(
            RimMod.MODID,
            "textures/gui/container/inserter.png");

    public final int leftPos = 100;
    public final int topPos = 0;

    public final int imageWidth = 176;
    public final int imageHeight = 166;

    public static final int sliderWidth = 150;
    public static final int sliderHeight = 20;
    public static final int horizontalMargin = 10; // between slider and screen
    public static final int verticalMargin = 10;
    public static final int horizontalSpacing = 5; // between sliders
    public static final int verticalSpacing = 10;

    public static final int buttonWidth = 100;
    public static final int buttonHeight = 20;

    public static final float minValue = -1.0F;
    public static final float maxValue = 1.0F;

    public static String modelPart = "grabber_left";


    public DebugInserterControlScreen() {
        super(Component.literal("Transformation Control UI"));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    protected void addSliders(InserterState state) {
        int currentXslider;
        int currentYslider;
        int currentXlabel;
        int currentYlabel;
        // Get values
        TransformMap tmap = DebugInserterBER.tmap;
        TransformValue values = tmap.get(state, modelPart);

        currentXslider = horizontalMargin;
        currentYslider = verticalMargin;
        currentXlabel = horizontalMargin + sliderWidth + horizontalSpacing;
        // QX
        addRenderableWidget(
                new RenderableSlider(
                        currentXslider, currentYslider, sliderWidth, sliderHeight,
                        values.qx, minValue, maxValue, value -> {
                    if (values.updateQuaternion('x', value)) {
                        tmap.put(state, modelPart, values);
                        this.init();
                    }
                }
                )
        );
        addRenderableOnly(Button.builder(Component.literal("QX"), null)
                .pos(currentXlabel, currentYslider)
                .size(30, sliderHeight)
                .build());


        currentYslider += (sliderHeight + verticalSpacing);

        // QY
        addRenderableWidget(
                new RenderableSlider(
                        currentXslider, currentYslider, sliderWidth, sliderHeight,
                        values.qy, minValue, maxValue, value -> {
                    if (values.updateQuaternion('y', value)) {
                        tmap.put(state, modelPart, values);
                        this.init();
                    }
                }
                )
        );
        addRenderableOnly(Button.builder(Component.literal("QY"), null)
                .pos(currentXlabel, currentYslider)
                .size(30, sliderHeight)
                .build());


        currentYslider += (sliderHeight + verticalSpacing);
        // QZ
        addRenderableWidget(
                new RenderableSlider(
                        currentXslider, currentYslider, sliderWidth, sliderHeight,
                        values.qz, minValue, maxValue, value -> {
                    if (values.updateQuaternion('z', value)) {

                        tmap.put(state, modelPart, values);
                        this.init();
                    }
                }
                )
        );
        addRenderableOnly(Button.builder(Component.literal("QZ"), null)
                .pos(currentXlabel, currentYslider)
                .size(30, sliderHeight)
                .build());

        currentYslider += (sliderHeight + verticalSpacing);
        // QW
        addRenderableWidget(
                new RenderableSlider(
                        currentXslider, currentYslider, sliderWidth, sliderHeight,
                        values.qw, minValue, maxValue, value -> {
                    if (values.updateQuaternion('w', value)) {
                        tmap.put(state, modelPart, values);
                        this.init();
                    }
                }
                )
        );
        addRenderableOnly(Button.builder(Component.literal("QZ"), null)
                .pos(currentXlabel, currentYslider)
                .size(30, sliderHeight)
                .build());

        currentYslider += (sliderHeight + verticalSpacing);
        // PX
        addRenderableWidget(
                new RenderableSlider(
                        currentXslider, currentYslider, sliderWidth, sliderHeight,
                        values.px, minValue, maxValue, value -> {
                    values.px = value;
                    tmap.put(state, modelPart, values);
                }
                )
        );
        addRenderableOnly(Button.builder(Component.literal("PX"), null)
                .pos(currentXlabel, currentYslider)
                .size(30, sliderHeight)
                .build());

        currentYslider += (sliderHeight + verticalSpacing);
        // PY
        addRenderableWidget(
                new RenderableSlider(
                        currentXslider, currentYslider, sliderWidth, sliderHeight,
                        values.py, minValue, maxValue, value -> {
                    values.py = value;
                    tmap.put(state, modelPart, values);
                }
                )
        );
        addRenderableOnly(Button.builder(Component.literal("PY"), null)
                .pos(currentXlabel, currentYslider)
                .size(30, sliderHeight)
                .build());


        currentYslider += (sliderHeight + verticalSpacing);
        // PZ
        addRenderableWidget(
                new RenderableSlider(
                        currentXslider, currentYslider, sliderWidth, sliderHeight,
                        values.pz, minValue, maxValue, value -> {
                    values.pz = value;
                    tmap.put(state, modelPart, values);
                }
                )
        );
        addRenderableOnly(Button.builder(Component.literal("PZ"), null)
                .pos(currentXlabel, currentYslider)
                .size(30, sliderHeight)
                .build());

        currentXslider = width - horizontalMargin - sliderWidth;
        currentYslider = verticalMargin;
        currentXlabel = width - horizontalMargin - sliderWidth - horizontalSpacing - 30;
        // TX
        addRenderableWidget(
                new RenderableSlider(
                        currentXslider, currentYslider, sliderWidth, sliderHeight,
                        values.tx, minValue, maxValue, value -> {
                    values.tx = value;
                    tmap.put(state, modelPart, values);
                }
                )
        );
        addRenderableOnly(Button.builder(Component.literal("TX"), null)
                .pos(currentXlabel, currentYslider)
                .size(30, sliderHeight)
                .build());

        currentYslider += (sliderHeight + verticalSpacing);
        // TY
        addRenderableWidget(
                new RenderableSlider(
                        currentXslider, currentYslider, sliderWidth, sliderHeight,
                        values.ty, minValue, maxValue, value -> {
                    values.ty = value;
                    tmap.put(state, modelPart, values);
                }
                )
        );
        addRenderableOnly(Button.builder(Component.literal("TY"), null)
                .pos(currentXlabel, currentYslider)
                .size(30, sliderHeight)
                .build());

        currentYslider += (sliderHeight + verticalSpacing);
        // TZ
        addRenderableWidget(
                new RenderableSlider(
                        currentXslider, currentYslider, sliderWidth, sliderHeight,
                        values.tz, minValue, maxValue, value -> {
                    values.tz = value;
                    tmap.put(state, modelPart, values);
                }
                )
        );
        addRenderableOnly(Button.builder(Component.literal("TZ"), null)
                .pos(currentXlabel, currentYslider)
                .size(30, sliderHeight)
                .build());
    }


    @Override
    protected void init() {
        this.clearWidgets();

        InserterState state = DebugInserterBlockEntity.state;

        addSliders(state);

        addRenderableWidget(
                Button.builder(Component.literal("Reset Values"), button -> resetValues())
                        .pos(width - horizontalMargin - buttonWidth, 10 + (sliderHeight * 3 + verticalSpacing * 4))
                        .size(buttonWidth, buttonHeight)
                        .build()
        );

        addRenderableWidget(
                Button.builder(Component.literal(state.name), button -> nextState())
                        .pos(width - horizontalMargin - buttonWidth, 10 + (sliderHeight * 3 + verticalSpacing * 5 + buttonHeight))
                        .size(buttonWidth, buttonHeight)
                        .build()
        );

        addRenderableWidget(
                Button.builder(Component.literal(modelPart), button -> nextPart())
                        .pos(width - horizontalMargin - buttonWidth, 10 + (sliderHeight * 3 + verticalSpacing * 6 + buttonHeight * 2))
                        .size(buttonWidth, buttonHeight)
                        .build()
        );

        // Add Labels


    }

//    @Override
//    protected void renderBackground(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
//        /*
//         * Renders the background texture to the screen. 'leftPos' and
//         * 'topPos' should already represent the top left corner of where
//         * the texture should be rendered as it was precomputed from the
//         * 'imageWidth' and 'imageHeight'. The two zeros represent the
//         * integer u/v coordinates inside the PNG file, whose size is
//         * represented by the last two integers (typically 256 x 256).
//         */
//
//    }


    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.minecraft.level == null) {
            this.renderPanorama(guiGraphics, partialTick);
        }

//        this.renderBlurredBackground();
//        this.renderMenuBackground(guiGraphics);
        net.neoforged.neoforge.common.NeoForge.EVENT_BUS.post(new net.neoforged.neoforge.client.event.ScreenEvent.BackgroundRendered(this, guiGraphics));
    }


    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        // Assume we have some Component 'label'
        // 'label' is drawn at 'labelX' and 'labelY'
        // The color is an ARGB value, if the alpha is less than 4, than the alpha is set to 255
        // The final boolean renders the drop shadow when true
        // graphics.drawString(this.font, this.label, this.labelX, this.labelY, 0x404040, false);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (minecraft == null) return;

        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }


    // Resets all values to their default state
    private void resetValues() {
        TransformMap tmap = DebugInserterBER.tmap;
        tmap.put(DebugInserterBlockEntity.state, modelPart, tmap.DEFAULT);
        // Update sliders to reflect the reset values
        this.init(); // Reinitialize the screen to reset sliders
    }

    private void nextState() {
        switch (DebugInserterBlockEntity.state) {
            case WAIT_SOURCE -> DebugInserterBlockEntity.state = InserterState.TAKING;
            case TAKING -> DebugInserterBlockEntity.state = InserterState.TRANSFERRING_1;
            case TRANSFERRING_1 -> DebugInserterBlockEntity.state = InserterState.TRANSFERRING_2;
            case TRANSFERRING_2 -> DebugInserterBlockEntity.state = InserterState.WAIT_DESTINATION;
            case WAIT_DESTINATION -> DebugInserterBlockEntity.state = InserterState.INSERTING;
            case INSERTING -> DebugInserterBlockEntity.state = InserterState.RETURNING_1;
            case RETURNING_1 -> DebugInserterBlockEntity.state = InserterState.RETURNING_2;
            case RETURNING_2 -> DebugInserterBlockEntity.state = InserterState.WAIT_SOURCE;
        }
        this.init();
    }

    private void nextPart() {
        switch (modelPart) {
            case "grabber_left" -> modelPart = "grabber_right";
            case "grabber_right" -> modelPart = "arm_1";
            case "arm_1" -> modelPart = "arm_2";
            case "arm_2" -> modelPart = "grabber_left";
        }
        this.init();
    }

}