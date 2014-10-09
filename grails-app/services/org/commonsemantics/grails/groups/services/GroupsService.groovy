/*
 * Copyright 2013 Common Semantics  (commonsemantics.org)
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
package org.commonsemantics.grails.groups.services

import org.codehaus.groovy.grails.plugins.web.taglib.ValidationTagLib
import org.commonsemantics.grails.groups.model.Group
import org.commonsemantics.grails.groups.model.GroupPrivacy
import org.commonsemantics.grails.groups.model.UserGroup
import org.commonsemantics.grails.groups.model.UserStatusInGroup
import org.commonsemantics.grails.groups.utils.DefaultGroupPrivacy
import org.commonsemantics.grails.groups.utils.DefaultGroupStatus
import org.commonsemantics.grails.groups.utils.DefaultUserStatusInGroup
import org.commonsemantics.grails.groups.utils.GroupsUtils

/**
 * @author Paolo Ciccarese <paolo.ciccarese@gmail.com>
 */
class GroupsService {

	def grailsApplication;
	
	def validateGroup(def cmd) {
		boolean validationFailed = false;
		def mandatory = GroupsUtils.getGroupDynamicMandatoryFields(grailsApplication);
		log.debug 'Mandatory fields: ' + mandatory
		
		if(!cmd.validate()) {
			log.error 'Group Validation Failed'
			validationFailed=true;
		}
		
		def g = new ValidationTagLib()
		mandatory.each { item ->
			if(!(cmd[item]!=null && cmd[item].trim().length()>0)) {
				log.error('Error for field "' + item + ' - ' + cmd[item])
				//cmd.errors.reject(g.message(code: 'org.commonsemantics.grails.general.message.error.cannotbenull', default: 'Field cannot be null'),
				//	[item, 'class User'] as Object[],
				//	'[Property [{0}] of class [{1}] does not match confirmation]')
				if(cmd.errors[item]==null) // This check if a validation error for this field is already present from previous validation steps
					cmd.errors.rejectValue(item, g.message(code: 'default.blank.message', default: 'Field cannot be null'))
				validationFailed=true;
			}
		}
		validationFailed;
	}
	
	def listGroups(def max, def offset, def sort, def _order) {
		
		def groups = [];
		def groupsCount = [:]
		Group.list().each { agroup ->
			groupsCount.put (agroup.id, UserGroup.findAllWhere(group: agroup).size())
		}
		def groupsStatus = [:]
		Group.list().each { agroup ->
			groupsStatus.put (agroup.id, GroupsUtils.getStatusValue(agroup))
		}
		
		if (sort == 'groupsCount') {
			groupsCount = groupsCount.sort{ a, b -> a.value <=> b.value }
			if(_order == "desc")
				groupsCount.each { groupCount ->
					groups.add Group.findById(groupCount.key);
				}
			else
				groupsCount.reverseEach { groupCount ->
					groups.add Group.findById(groupCount.key);
				}
		} else if (sort == 'status') {
			groupsStatus = groupsStatus.sort{ a, b -> a.value.compareTo(b.value) }
			if(_order == "desc")
				groupsStatus.each { groupStatus ->
					groups.add Group.findById(groupStatus.key);
				}
			else
				groupsStatus.reverseEach { groupStatus ->
					groups.add Group.findById(groupStatus.key);
				}
		} else {
			groups = Group.withCriteria {
				maxResults(max?.toInteger())
				firstResult(offset?.toInteger())
				order(sort, _order)
			}
		}
		
		[groups, groupsCount]
	}
	
	def searchGroups(def name, def shortName, def max, def offset, def _sort, def _order) {
		def groups = [];
		def groupsCount = [:]
		def groupsStatus = [:]
		Group.list().each { agroup ->
			groupsCount.put (agroup.id, UserGroup.findAllWhere(group: agroup).size())
			groupsStatus.put (agroup.id, GroupsUtils.getStatusLabel(agroup))
		}

		// Search with no ordering
		def groupCriteria = Group.createCriteria();
		def r = [];

		if(name!=null && name.trim().length()>0 &&
		shortName!=null && shortName.trim().length()>0) {
			println 'case 1'
			r = groupCriteria.list {
				maxResults(max?.toInteger())
				firstResult(offset?.toInteger())
				order(_sort, _order)
				and {
					like('name', name)
					like('shortName', shortName)
				}
			}
		} else if(name!=null && name.trim().length()>0 &&
		(shortName==null || shortName.trim().length()==0)) {
			println 'case 2'
			r = groupCriteria.list {
				maxResults(max?.toInteger())
				firstResult(offset?.toInteger())
				order(_sort, _order)
				like('name', name)
			}
		} else if((name==null || name.trim().length()==0) &&
		shortName!=null &&  shortName.trim().length()>0) {
			println 'case 3'
			r = groupCriteria.list {
				maxResults(max?.toInteger())
				firstResult(offset?.toInteger())
				order(_sort, order)
				like('shortName', shortName)
			}
		} else {
			println 'case 4'
			r = groupCriteria.list {
				maxResults(max?.toInteger())
				firstResult(offset?.toInteger())
				order(_sort, _order)
			}
		}
		groups = r.toList();
		//}


		def groupsResults = []
		groups.each { groupItem ->
			def groupResult = [id:groupItem.id, name:groupItem.name, shortName: groupItem.shortName,
						description: groupItem.description, status: GroupsUtils.getStatusLabel(groupItem), dateCreated: groupItem.dateCreated]
			groupsResults << groupResult
		}

		def paginationResults = ['offset':offset+max, 'sort':_sort, 'order':_order]
		def results = [groups: groupsResults, pagination: paginationResults, groupsCount: groupsCount]
	}
	
	def updateGroupStatus(def group, def status) {
		log.debug 'Group ' + group + ' status ' + status
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
		log.debug 'Group ' + group + ' privacy ' + privacy
		if(privacy==DefaultGroupPrivacy.PRIVATE.value()) {
			group.privacy = GroupPrivacy.findByValue(DefaultGroupPrivacy.PRIVATE.value());
		} else if(privacy==DefaultGroupPrivacy.RESTRICTED.value()) {
			group.privacy = GroupPrivacy.findByValue(DefaultGroupPrivacy.RESTRICTED.value());
		} else if(privacy==DefaultGroupPrivacy.PUBLIC.value()) {
			group.privacy = GroupPrivacy.findByValue(DefaultGroupPrivacy.PUBLIC.value());
		}
	}
	
	def listUserGroups(def user) {
		def allUserGroups = [];
		def searchResult = UserGroup.createCriteria().list() {
			eq('user', user);
		}
		searchResult.each {
			if(it.group.enabled!=false) {
				allUserGroups.add it
			}
		}
		allUserGroups
	}
	
	def listUsersGroups(def user) {
		def allUsersGroups = [];
		def searchResult = UserGroup.createCriteria().list() {
			eq('user', user);
		}
		searchResult.each {
			if(it.group.enabled!=false) {
				allUsersGroups.add it.group
			}
		}
		allUsersGroups
	}
	
	def listUserGroups(def user, def _max, def _offset, def sort, def _order) {		
		def searchResult = UserGroup.createCriteria().list(
			max:_max, offset:_offset) {
				eq('user', user);
		}
		def allUserGroups = searchResult
		
		allUserGroups
	}
	
	def updateUserInGroupStatus(def usergroup, def status) {
		log.debug 'UserGroup ' + usergroup + ' status ' + status
		if(status.equals(DefaultUserStatusInGroup.ACTIVE.value())) {
			usergroup.status = UserStatusInGroup.findByValue(DefaultUserStatusInGroup.ACTIVE.value());
		} else if(status.equals(DefaultUserStatusInGroup.SUSPENDED.value())) {
			usergroup.status = UserStatusInGroup.findByValue(DefaultUserStatusInGroup.SUSPENDED.value());
		} else if(status.equals(DefaultUserStatusInGroup.LOCKED.value())) {
			usergroup.status = UserStatusInGroup.findByValue(DefaultUserStatusInGroup.LOCKED.value());
		}
	}
	
	def boolean updateUserRoleInGroup(def usergroup, def role, def value) {
		if(value=='on') {
			if(!usergroup.roles.contains(role)) {
				usergroup.roles.add(role);
				return true;
			}
		}
	}

//	def getUserGroups(def user) {
//		def ur = []
//		def userGroups = []
//		ur = UserGroup.findAllByUser(user)
//		ur.each { userGroups.add(it.group)}
//		return ur;
//	}
//	
//	def listGroups(def max, def offset, def sort, def _order) {
//		
//		def groups = [];
//		def groupsCount = [:]
//		Group.list().each { agroup ->
//			groupsCount.put (agroup.id, UserGroup.findAllWhere(group: agroup).size())
//		}
//		def groupsStatus = [:]
//		Group.list().each { agroup ->
//			groupsStatus.put (agroup.id, GroupUtils.getStatusValue(agroup))
//		}
//		
//		if (sort == 'groupsCount') {
//			groupsCount = groupsCount.sort{ a, b -> a.value <=> b.value }
//			if(_order == "desc")
//				groupsCount.each { groupCount ->
//					groups.add Group.findById(groupCount.key);
//				}
//			else
//				groupsCount.reverseEach { groupCount ->
//					groups.add Group.findById(groupCount.key);
//				}
//		} else if (sort == 'status') {
//			groupsStatus = groupsStatus.sort{ a, b -> a.value.compareTo(b.value) }
//			if(_order == "desc")
//				groupsStatus.each { groupStatus ->
//					groups.add Group.findById(groupStatus.key);
//				}
//			else
//				groupsStatus.reverseEach { groupStatus ->
//					groups.add Group.findById(groupStatus.key);
//				}
//		} else {
//			groups = Group.withCriteria {
//				maxResults(max?.toInteger())
//				firstResult(offset?.toInteger())
//				order(sort, _order)
//			}
//		}
//		
//		[groups, groupsCount]
//	}
//	
//	def listSystemGroups(def system, def _max, def _offset, def sort, def _order) {
//		
//		
//		def groupsCount = [:]
//		system.groups.each { agroup ->
//			groupsCount.put (agroup.id, UserGroup.findAllWhere(group: agroup).size())
//		}
//		
//		[system.groups, groupsCount]
//	}
//	
//	def listGroupUsers(def group, def _max, def _offset, def sort, def _order) {
//		
//		def userGroups = UserGroup.findAllByGroup(group);
//		userGroups
//	}

}
