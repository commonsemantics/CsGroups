/*
 * Copyright 2013  Common Semantics (commonsemantics.org)
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.commonsemantics.grails.groups.controllers

import org.commonsemantics.grails.groups.commands.GroupEditCommand
import org.commonsemantics.grails.groups.model.Group
import org.commonsemantics.grails.groups.model.GroupPrivacy
import org.commonsemantics.grails.groups.model.UserGroup
import org.commonsemantics.grails.groups.utils.DefaultGroupPrivacy
import org.commonsemantics.grails.groups.utils.DefaultGroupStatus
import org.commonsemantics.grails.users.model.Role
import org.commonsemantics.grails.users.model.UserRole


/**
 * @author Paolo Ciccarese <paolo.ciccarese@gmail.com>
 */
class TestsController {

	static defaultAction = "index"

	def agentsService;
	def usersService;
	def groupsService;

	def index = { render (view:'tests') }
	
	def showGroup = {
		log.debug("[TEST] show-group " + (params.groupId?("(id:" + params.groupId + ")"):"(No id specified)"));
		def group = getGroup(params.groupId)
		def counter = UserGroup.findAllWhere(group: group).size()
		render (view:'group-show', model:[label:params.testId, description:params.testDescription, group:group]);
	}
	
	def editGroup = {
		log.debug("[TEST] edit-group " + (params.groupId?("(id:" + params.groupId + ")"):"(No id specified)"));
		def group = getGroup(params.groupId)
		render (view:'group-edit', model:[label:params.testId, description:params.testDescription, group:group]);
	}
	
	def updateGroup = { GroupEditCommand cmd ->
		def validationFailed = groupsService.validateGroup(cmd);
		if (validationFailed) {
			log.error("[TEST] While Updating Group " + cmd.errors)
		} else {
			def group = Group.findById(params.id);
			log.debug("[TEST] Updating Group " + group)
			if(group!=null) {
				group.name = params.name;
				group.shortName = params.shortName;
				group.description = params.description;
	
				groupsService.updateGroupStatus(group, params.groupStatus)
				groupsService.updateGroupPrivacy(group, params.groupPrivacy);
				
				render (view:'group-show', model:[label:params.testId, description:params.testDescription, group:group]);
				return;
			}
		}
		render (view:'group-edit', model:[label:params.testId, description:params.testDescription, group:cmd]);
	}
	
//	private def updateGroupStatus(def group, def status) {
//		log.debug 'Group ' + group + ' status ' + status
//		if(status.equals(DefaultGroupStatus.ACTIVE.value())) {
//			group.enabled = true
//			group.locked = false
//		} else if(status.equals(DefaultGroupStatus.DISABLED.value())) {
//			group.enabled = false
//			group.locked = false
//		} else if(status.equals(DefaultGroupStatus.LOCKED.value())) {
//			group.enabled = true
//			group.locked = true
//		}
//	}
//	
//	private def updateGroupPrivacy(def group, def privacy) {
//		log.debug 'Group ' + group + ' privacy ' + privacy
//		if(privacy==DefaultGroupPrivacy.PRIVATE.value()) {
//			group.privacy = GroupPrivacy.findByValue(DefaultGroupPrivacy.PRIVATE.value());
//		} else if(privacy==DefaultGroupPrivacy.RESTRICTED.value()) {
//			group.privacy = GroupPrivacy.findByValue(DefaultGroupPrivacy.RESTRICTED.value());
//		} else if(privacy==DefaultGroupPrivacy.PUBLIC.value()) {
//			group.privacy = GroupPrivacy.findByValue(DefaultGroupPrivacy.PUBLIC.value());
//		}
//	}
	
	def createGroup = {
		log.debug("[TEST] create-group ");
		render (view:'group-create', model:[label:params.testId, description:params.testDescription]);
	}
	
	def listGroups = {
		log.debug("[TEST] list-groups max:" + params.max + " offset:" + params.offset)
		
		def groupsUsersCount = [:]
		def groups = Group.list(params);
		
		groups.each { group ->
			groupsUsersCount.put (group.id, UserGroup.findAllWhere(group: group).size())
		}
		
		render (view:'groups-list', model:[label:params.testId, description:params.testDescription, groups:groups, groupsUsersCount: groupsUsersCount,
			groupsTotal: Group.count(), max: params.max, offset: params.offset, controller:'tests', action: 'testListGroups']);
	}
	
	def lockGroup = {
		def group = Group.findById(params.id)
		log.debug 'Locking Group ' + group
		group.locked = true
		group.enabled = true;
		if(params.redirect)
			redirect(action:params.redirect, params:[testId:params.testId, testDescription:params.testDescription])
		else
			render (view:'group-show', model:[label:params.testId, description:params.testDescription, item: group])
	}

	def unlockGroup = {
		def group = Group.findById(params.id)
		log.debug 'Unlocking Group ' + group
		group.locked = false
		group.enabled = true;
		if(params.redirect)
			redirect(action:params.redirect, params:[testId:params.testId, testDescription:params.testDescription])
		else
			render (view:'group-show', model:[label:params.testId, description:params.testDescription, item: group])
	}

	def enableGroup = {
		def group = Group.findById(params.id)
		log.debug 'Enabling Group ' + group
		group.enabled = true
		group.locked = false
		if(params.redirect)
			redirect(action:params.redirect, params:[testId:params.testId, testDescription:params.testDescription])
		else
			render (view:'group-show', model:[label:params.testId, description:params.testDescription, item: group])
	}

	def disableGroup = {
		def group = Group.findById(params.id)
		log.debug 'Disabling Group ' + group
		group.enabled = false
		group.locked = false
		if(params.redirect)
			redirect(action:params.redirect, params:[testId:params.testId, testDescription:params.testDescription])
		else
			render (view:'group-show', model:[label:params.testId, description:params.testDescription, item: group])
	}
	
	def listGroupUsers = {
		log.debug("[TEST] show-group " + (params.id?("(id:" + params.id + ")"):"(No id specified)"));
		
		def group = getGroup(params.id)
		
		if (!params.max) params.max = 10
		if (!params.offset) params.offset = 0
		//if (!params.sort) params.sort = "username"
		//if (!params.order) params.order = "asc"

		def userGroups = UserGroup.findAllByGroup(group, [max: params.max, sort: params.sort, order: params.order, offset: params.offset]);

		render (view:'group-users-list', model:[label:params.testId, description:params.testDescription, 
			group: group, "userGroups" : userGroups, "usersTotal": userGroups.size(), "usersroles": UserRole.list(), "roles" : Role.list()])
	}
	
	private def getGroup(def id) {
		def group;
		if(id==null)  group = Group.list()[0];
		else group = Group.findById(id);
		group
	}
}