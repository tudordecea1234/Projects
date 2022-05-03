package project.lab6.domain.validators;

import project.lab6.domain.Event1;

public class EventValidator implements Validator<Event1> {
    @Override
    public void validate(Event1 event) throws ValidationException {
        String errors = "";
        if (event.getName() == null || event.getName().trim().equals(""))
            errors = "The name of the event cannot be empty!\n";
        if (event.getLocation() == null || event.getLocation().trim().equals(""))
            errors = "The location of the event cannot be empty!\n";
        if (event.getDescription() == null || event.getDescription().trim().equals(""))
            errors = "The description of the event cannot be empty!\n";
        if (event.getDate() == null)
            errors = "The date of the event cannot be empty!\n";
        if (!errors.equals(""))
            throw new ValidationException(errors);
    }
}
