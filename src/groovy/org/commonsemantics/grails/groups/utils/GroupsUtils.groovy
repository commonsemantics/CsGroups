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
package org.commonsemantics.grails.groups.utils

import org.apache.log4j.Logger;
import org.commonsemantics.grails.groups.model.Group
import org.commonsemantics.grails.utils.LoggingUtils


/**
* @author Paolo Ciccarese <paolo.ciccarese@gmail.com>
*/
class GroupsUtils {

	static Logger log = Logger.getLogger(GroupsUtils.class) // log4j
	
	static def getGroupConfigurationMandatoryFields(def grailsApplication) {
		def mandatory = [];
		if(grailsApplication.config.org.commonsemantics.grails.groups.model.fields.mandatory.size()>0) {
			mandatory.addAll(Eval.me(grailsApplication.config.org.commonsemantics.grails.groups.model.fields.mandatory));
		}
		return mandatory;
	}
	
	static def getGroupDynamicMandatoryFields(def grailsApplication) {
		//if(isGroupStaticPropertyExisting('mandatory')) { 
			def mandatory = Group.mandatory.clone() as Set;
			mandatory.addAll(getGroupConfigurationMandatoryFields(grailsApplication));
			return mandatory;
		//} else return [];
	}
	
	static boolean isGroupFieldRequired(def grailsApplication, def fieldName) {
		// Mandatory fields by dynamic configuration
		def mandatoryByConfiguration = getGroupDynamicMandatoryFields(grailsApplication)
		// Mandatory fields by static coding
		if(!Group.constraints[fieldName]?.nullable) mandatoryByConfiguration.add(fieldName);
		
		if((isGroupStaticPropertyExisting('mandatory') && fieldName in Group.mandatory) || fieldName in mandatoryByConfiguration) {
			log.debug LoggingUtils.LOG_CONF + ' Group mandatory field: ' + fieldName;
			return true;
		}
		return false;
	}
	
	static def isGroupStaticPropertyExisting(def name) {
		Group.class.declaredFields.find {
			it.name == 'x' && isStatic(it.modifiers)
		}
	}
	
	static String getStatusValue(Group group) {
		if(group.isEnabled()) {
			 if(group.isLocked()) return DefaultGroupStatus.LOCKED.value();
			 else return DefaultGroupStatus.ACTIVE.value();
		} else {
			return DefaultGroupStatus.DISABLED.value();
		}
	}
	
	static String getStatusUuid(Group group) {
		if(group.isEnabled()) {
			 if(group.isLocked()) return DefaultGroupStatus.LOCKED.uuid();
			 else return DefaultGroupStatus.ACTIVE.uuid();
		} else {
			return DefaultGroupStatus.DISABLED.uuid();
		}
	}
	
	static String getStatusLabel(Group group) {
		if(group.isEnabled()) {
			 if(group.isLocked()) return DefaultGroupStatus.LOCKED.label();
			 else return DefaultGroupStatus.ACTIVE.label();
		} else {
			return DefaultGroupStatus.DISABLED.label();
		}
	}
}
