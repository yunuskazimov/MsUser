package az.xazar.msuser.client.model.permission;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Locale;

public enum Role {
    USER, ADMIN, REPORTER, MANAGER, SUPER;

    @JsonValue
    public String toLower() {
        return this.toString().toLowerCase(Locale.ENGLISH);
    }
}
