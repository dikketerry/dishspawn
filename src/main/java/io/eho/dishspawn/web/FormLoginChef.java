package io.eho.dishspawn.web;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

//todo: check, not sure if this is actually used (due to default PostMapping Spring security)
@Getter @Setter
public class FormLoginChef {

    @NotNull(message = "cannot be empty")
    private String userName;

    @NotNull(message = "cannot be empty")
    private String password;

}
