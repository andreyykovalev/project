package com.epam.rd.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Builder
public class EntityWorkOrder {
	@Getter
	Long id;
	@Getter @Setter EntityCustomer customer;
	@Getter @Setter EntityPackage packages;
	@Getter @Setter Date createdAt;
	@Getter @Setter Date dateEnd;
	@Getter @Setter Boolean status;


	public EntityWorkOrder(EntityCustomer customer, EntityPackage packages, Date createdAt, Date dateEnd) {
		this.customer = customer;
		this.packages = packages;
		this.createdAt = createdAt;
		this.dateEnd = dateEnd;
	}
	public EntityWorkOrder(Long id, EntityCustomer customer, EntityPackage packages, Date createdAt, Date dateEnd, boolean status) {
		this.id = id;
		this.customer = customer;
		this.packages = packages;
		this.createdAt = createdAt;
		this.dateEnd = dateEnd;
		this.status = status;
	}

	public EntityWorkOrder(Long id, EntityCustomer customer, EntityPackage packages, Date dateEnd) {

	}

	public EntityWorkOrder(long id) {
	}
}
