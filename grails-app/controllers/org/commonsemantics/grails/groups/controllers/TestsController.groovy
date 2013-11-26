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
import org.commonsemantics.grails.groups.utils.DefaultGroupPrivacy
import org.commonsemantics.grails.groups.utils.DefaultGroupStatus


/**
 * @author Paolo Ciccarese <paolo.ciccarese@gmail.com>
 */
class TestsController {

	static defaultAction = "index"

	def agentsService;
	def usersService;
	def groupsService;

	def index = { render (view:'tests') }
	
	def testShowGroup = {
		log.debug("[TEST] show-group " + (params.groupId?("(id:" + params.groupId + ")"):"(No id specified)"));
		def group = getGroup(params.groupId)
		render (view:'group-show', model:[label:params.testId, description:params.testDescription, group:group]);
	}
	
	def testEditGroup = {
		log.debug("[TEST] edit-group " + (params.groupId?("(id:" + params.groupId + ")"):"(No id specified)"));
		def group = getGroup(params.groupId)
		render (view:'group-edit', model:[label:params.testId, description:params.testDescription, group:group]);
	}
	
	def testUpdateGroup = { GroupEditCommand cmd ->
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
	
				updateGroupStatus(group, params.groupStatus)
				updateGroupPrivacy(group, params.groupPrivacy);
				
				render (view:'group-show', model:[label:params.testId, description:params.testDescription, group:group]);
				return;
			}
		}
		render (view:'group-edit', model:[label:params.testId, description:params.testDescription, group:cmd]);
	}
	
	def updateGroupStatus(def group, def status) {
		println 'xxxxxxxxx ' + status
		if(status.equals(DefaultGroupStatus.ACTIVE.value())) {
			group.enabled = true
			group.locked = false
		} else if(status.equals(DefaultGroupStatus.DISABLED.value())) {
			group.enabled = false
			group.locked = false
		} else if(status.equals(DefaultGroupStatus.LOCKED.value())) {
			group.enabled = true
			group.locked = true
		}
	}
	
	def updateGroupPrivacy(def group, def privacy) {
		if(privacy==DefaultGroupPrivacy.PRIVATE.value()) {
			group.privacy = GroupPrivacy.findByValue(DefaultGroupPrivacy.PRIVATE.value());
		} else if(privacy==DefaultGroupPrivacy.RESTRICTED.value()) {
			group.privacy = GroupPrivacy.findByValue(DefaultGroupPrivacy.RESTRICTED.value());
		} else if(privacy==DefaultGroupPrivacy.PUBLIC.value()) {
			group.privacy = GroupPrivacy.findByValue(DefaultGroupPrivacy.PUBLIC.value());
		}
		println 'lllllll ' + group.privacy
	}
	
	def testCreateGroup = {
		log.debug("[TEST] create-group ");
		render (view:'group-create', model:[label:params.testId, description:params.testDescription]);
	}
	
	private def getGroup(def id) {
		def group;
		if(id==null)  group = Group.list()[0];
		else group = Group.findById(id);
		group
	}
}