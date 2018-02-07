package com.epam.rd.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
public class EntityUser {
	@Getter
	Long id;
	@Getter @Setter String login;
	@Getter @Setter String password;
	@Getter @Setter Integer level;

}
