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

/**
* @author Paolo Ciccarese <paolo.ciccarese@gmail.com>
*/
enum DefaultUserStatusInGroup {
	
	ACTIVE("IG_ACTIVE", "Active", ""),
	PENDING("IG_PENDING", "Pending", "Used for the groups that require acceptance"),
	LOCKED("IG_LOCKED", "Locked", "Not active but visible"),
	SUSPENDED("IG_SUSPENDED", "Suspended", "Not active and not visible"),
	DELETED("IG_DELETED", "Deleted", "Not active, not visible and not restorable.")
	
	DefaultUserStatusInGroup(String value, String label, String description) {
		this.value = value
		this.label = label;
		this.description = description;
	}
	
	private final String value
	public String value() { return value }
	private final String label
	public String label() { return label }
	private final String description
	public String description() { return description }
}
