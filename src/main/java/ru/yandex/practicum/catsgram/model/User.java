package ru.yandex.practicum.catsgram.model;

import java.time.LocalDate;
import java.util.Objects;

public class User {
	private String email;
	private String nickname;
	private LocalDate birthdate;

	public String getEmail() {
		return email;
	}

	public String getNickname() {
		return nickname;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	@Override
	public String toString() {
		return "User{" +
				"email='" + email + '\'' +
				", nickname='" + nickname + '\'' +
				", birthdate=" + birthdate +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof User)) return false;
		User user = (User) o;
		return getEmail().equals(user.getEmail());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getEmail());
	}
}
