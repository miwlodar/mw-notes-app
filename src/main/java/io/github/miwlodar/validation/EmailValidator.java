package io.github.miwlodar.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private static final Pattern PATTERN = Pattern.compile(EMAIL_PATTERN);

	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		if (email == null) {
			return false;
		}

		final Matcher matcher = PATTERN.matcher(email);

		return matcher.matches();
	}
}


