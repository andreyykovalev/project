package com.epam.rd.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class EntityPackage {
	@Getter
	@Setter Long id;
	@Getter @Setter	Double price;
	@Getter @Setter Integer type;
	@Getter @Setter String image;
	@Getter @Setter String name;
	@Getter @Setter String description;
	@Getter @Setter Integer languageId;

}
