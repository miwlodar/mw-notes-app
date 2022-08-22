//DTO class for User details with fields validation conducted during custom registration
package io.github.miwlodar.user;

import io.github.miwlodar.validation.FieldMatch;
import io.github.miwlodar.validation.ValidEmail;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@FieldMatch.List({
    @FieldMatch(first = "password", second = "matchingPassword", message = "fields must match")
})
public class CreateUserDto {

	@NotEmpty(message = "is required")
	@Size(message = "is required")
	private String userName;

	@NotEmpty(message = "is required")
	@Size(min = 7, message = "must be at least 7 characters")
	private String password;

	@NotEmpty(message = "is required")
	@Size(min = 7, message = "must be at least 7 characters")
	private String matchingPassword;

	@NotEmpty(message = "is required")
	@Size(message = "is required")
	private String firstName;

	@NotEmpty(message = "is required")
	@Size(message = "is required")
	private String lastName;

	@ValidEmail
	@NotEmpty(message = "is required")
	@Size(message = "is required")
	private String email;

	public CreateUserDto() {
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
