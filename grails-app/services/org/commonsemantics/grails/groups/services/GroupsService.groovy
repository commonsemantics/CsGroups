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
				log.error('Error for field "' + item + '" which cannot be null')
				//cmd.errors.reject(g.message(code: 'org.commonsemantics.grails.general.message.error.cannotbenull', default: 'Field cannot be null'),
				//	[item, 'class User'] as Object[],
				//	'[Property [{0}] of class [{1}] does not match confirmation]')
				cmd.errors.rejectValue(item, g.message(code: 'default.blank.message', default: 'Field cannot be null'))
				validationFailed=true;
			}
		}
		validationFailed;
	}
	
//	def listUserGroups(def user, def _max, def _offset, def sort, def _order) {
//		
//		/*
//		def groups = [];
//		def groupsCount = [:]
//		def groupsStatus = [:]
//		Group.list().each { agroup ->
//			if(UserGroup.findByUserAndGroup(user, agroup)!=null) {
//				groupsCount.put (agroup.id, UserGroup.findAllWhere(group: agroup).size())
//				groupsStatus.put (agroup.id, agroup.status)
//				groups.add (agroup)
//			}
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
//		
//			groups = Group.withCriteria {
//				maxResults(_max?.toInteger())
//				firstResult(_offset?.toInteger())
//				order(sort, _order)
//			}
//		}
//		*/
//		
//		def userGroups = [];
//		UserGroup.createCriteria()
//		
//		def searchResult = UserGroup.createCriteria().list(
//			max:_max, offset:_offset) {
//				eq('user', user);
//		}
//		def allUserGroups = searchResult
//		
//		/*
//		def userGroups = [];
//		def groupsCount = [:]
//		def groupsStatus = [:]
//		def allUserGroups = UserGroup.findAllByUser(user);
//		allUserGroups.each { userGroup ->
//			groupsCount.put (userGroup.group, UserGroup.findAllWhere(group: userGroup.group).size())
//			groupsStatus.put (userGroup.group, userGroup.group.status)
//		}
//		
//		if (sort == 'groupsCount') {
//			groupsCount = groupsCount.sort{ a, b -> a.value <=> b.value }
//			if(_order == "desc")
//				groupsCount.each { groupCount ->
//					userGroups.add UserGroup.findByGroup(groupCount.key);
//				}
//			else
//				groupsCount.reverseEach { groupCount ->
//					userGroups.add UserGroup.findByGroup(groupCount.key);
//				}
//		}
//		*/
//		
//		allUserGroups
//	}
//
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
//	
//	def listUserGroups(def user) {
//		def userGroups = [];
//		def allUserGroups = [];
//		def searchResult = UserGroup.createCriteria().list() {
//			eq('user', user);
//		}
//		searchResult.each {
//			println it.group.enabled
//			if(it.group.enabled!=false) {
//				allUserGroups.add it
//			}
//		}
//		allUserGroups
//	}
}
