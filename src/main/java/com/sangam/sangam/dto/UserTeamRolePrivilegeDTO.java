package com.sangam.sangam.dto;

import java.util.List;

import javax.faces.model.SelectItem;

import lombok.Data;

@Data
public class UserTeamRolePrivilegeDTO {
	private String userId;
	private String  teamId;
	private String teamName;
	private Integer roleId;
	private String role;
	private List<SelectItem> listRolePrivilege;
	public UserTeamRolePrivilegeDTO(String teamId, String teamName, String userId, Integer roleId, String role,
			List<SelectItem> listRolePrivilege) {
		this.teamId = teamId;
		this.teamName = teamName;
		this.roleId = roleId;
		this.role = role;
		this.listRolePrivilege = listRolePrivilege;
	}
	public UserTeamRolePrivilegeDTO() {
		super();
	}
}
