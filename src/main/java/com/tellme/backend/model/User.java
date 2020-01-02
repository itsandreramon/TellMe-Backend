package com.tellme.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tellme.backend.utils.Constants;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @NotBlank
    @JsonProperty(Constants.USER_KEY_UID)
    String uid;

    @NotBlank
    @JsonProperty(Constants.USER_KEY_NAME)
    String name;

    @NotBlank
    @JsonProperty(Constants.USER_KEY_USERNAME)
    String username;

    @NotBlank
    @JsonProperty(Constants.USER_KEY_EMAIL)
    String email;

    @JsonProperty(Constants.USER_KEY_AVATAR)
    String avatar;

    @JsonProperty(Constants.USER_KEY_FOLLOWS)
    List<String> follows;

    @JsonProperty(Constants.USER_KEY_FOLLOWERS)
    List<String> followers;
}
