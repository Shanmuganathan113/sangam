package com.sangam.sangam.dto;

import lombok.Data;

@Data
public class TeamAdminDTO {
	private String userId;
	private String userName;
	private Integer roleId;
	private String roleName;

	public TeamAdminDTO(Object[] data) {
		this.userId = data[0].toString();
		this.userName = data[1].toString();
		this.roleId = Integer.parseInt(data[2].toString());
		this.roleName = data[3].toString();
	}
}
