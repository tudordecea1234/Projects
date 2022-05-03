package project.lab6.domain.validators;

import project.lab6.domain.UserEventInfo;

public class UserEventInfoValidator implements Validator<UserEventInfo> {

    @Override
    public void validate(UserEventInfo entity) throws ValidationException {
        String errors = "";
        if (entity.getIdUser() == null)
            errors += "invalid idUser\n";
        if (entity.getIdEvent() == null)
            errors += "invalid idEvent";
        if (!errors.equals(""))
            throw new ValidationException(errors);
    }
}
