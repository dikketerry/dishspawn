package io.eho.dishspawn.web;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class FormLoginChef {

    @NotNull
    private String userName;

    @NotNull
    private String password;

}
