package net.rimrim.rimmod.chem.correlation;


import net.rimrim.rimmod.chem.correlation.type.base.BaseForm;

public class Forms {
    public static BaseForm ARDEN_BUCK = new BaseForm(
            4, 1,
            (F) -> {
                return F.c(0) * (float) Math.exp((F.c(1) - F.p(0) / F.c(2)) * (F.p(0) / (F.c(3) + F.p(0))));
            }
    );

    public static BaseForm ANTOINE = new BaseForm(
            3, 1,
            (F) -> {
                return (float) Math.exp(F.c(0) - F.c(1) / (F.p(0) + F.c(2)));
            }
    );


}
