/*
 * Copyright 2014 Common Semantics  (commonsemantics.org)
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

import org.commonsemantics.grails.groups.model.GroupPrivacy
import org.commonsemantics.grails.groups.model.GroupRole
import org.commonsemantics.grails.groups.model.GroupStatus
import org.commonsemantics.grails.groups.model.UserStatusInGroup
import org.commonsemantics.grails.groups.utils.DefaultGroupPrivacy
import org.commonsemantics.grails.groups.utils.DefaultGroupRoles
import org.commonsemantics.grails.groups.utils.DefaultGroupStatus
import org.commonsemantics.grails.groups.utils.DefaultUserStatusInGroup

/**
 * @author Paolo Ciccarese <paolo.ciccarese@gmail.com>
 */
class GroupsInitializationService {

	def grailsApplication;
	
	def initializeRoles() {
		def enumeration = null;
		def enumerationClass = grailsApplication.config.org.commonsemantics.grails.groups.init.roles
		if(enumerationClass!=null && 
				((enumerationClass instanceof ConfigObject && !enumerationClass.isEmpty()) || 
				(enumerationClass instanceof String && enumerationClass.trim().length()>0))) {
			log.info "Selected enumeration roles " + enumerationClass
			enumeration = this.getClass().classLoader.findClass(enumerationClass)
		} else {
			log.info "Selected default enumeration roles"
			enumeration = DefaultGroupRoles;
		}
		
		enumeration.values().each {
			if(!GroupRole.findByAuthority(it.value())) {
				new GroupRole(authority: it.value(), ranking: it.ranking(), label: it.label(), description: it.description()).save(failOnError: true)
				log.info "Initialized: " + it.value()
			} else {
				log.info "Found: " + it.value()
			}
		}
	}
	
	def initializeStatus() {
		def enumeration = null;
		def enumerationClass = grailsApplication.config.org.commonsemantics.grails.groups.init.status
		if(enumerationClass!=null && 
				((enumerationClass instanceof ConfigObject && !enumerationClass.isEmpty()) || 
				(enumerationClass instanceof String && enumerationClass.trim().length()>0))) {
			log.info "Selected enumeration status " + enumerationClass
			enumeration = this.getClass().classLoader.findClass(enumerationClass)
		} else {
			log.info "Selected default enumeration status"
			enumeration = DefaultGroupStatus;
		}
		
		enumeration.values().each {
			if(!GroupStatus.findByValue(it.value())) {
				new GroupStatus(value: it.value(), uuid: it.uuid(), label: it.label(), description: it.description()).save(failOnError: true)
				log.info "Initialized: " + it.value()
			} else {
				log.info "Found: " + it.value()
			}
		}
	}
	
	def initializePrivacy() {
		def enumeration = null;
		def enumerationClass = grailsApplication.config.org.commonsemantics.grails.groups.init.privacy
		if(enumerationClass!=null && 
				((enumerationClass instanceof ConfigObject && !enumerationClass.isEmpty()) || 
				(enumerationClass instanceof String && enumerationClass.trim().length()>0))) {
			log.info "Selected enumeration privacy " + enumerationClass
			enumeration = this.getClass().classLoader.findClass(enumerationClass)
		} else {
			log.info "Selected default enumeration privacy"
			enumeration = DefaultGroupPrivacy;
		}
		
		enumeration.values().each {
			if(!GroupPrivacy.findByValue(it.value())) {
				new GroupPrivacy(value: it.value(), uuid: it.uuid(), label: it.label(), description: it.description()).save(failOnError: true)
				log.info "Initialized: " + it.value()
			} else {
				log.info "Found: " + it.value()
			}
		}
	}
	
	def initializeUserStatusInGroup() {
		def enumeration = null;
		def enumerationClass = grailsApplication.config.org.commonsemantics.grails.groups.init.statusingroup
		if(enumerationClass!=null && 
				((enumerationClass instanceof ConfigObject && !enumerationClass.isEmpty()) || 
				(enumerationClass instanceof String && enumerationClass.trim().length()>0))) {
			log.info "Selected enumeration user status " + enumerationClass
			enumeration = this.getClass().classLoader.findClass(enumerationClass)
		} else {
			log.info "Selected default enumeration user status "
			enumeration = DefaultUserStatusInGroup;
		}
		
		enumeration.values().each {
			if(!UserStatusInGroup.findByValue(it.value())) {
				new UserStatusInGroup(value: it.value(), label: it.label(), description: it.description()).save(failOnError: true)
				log.info "Initialized: " + it.value()
			} else {
				log.info "Found: " + it.value()
			}
		}
	}
}
