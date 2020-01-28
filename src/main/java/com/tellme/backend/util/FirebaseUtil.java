/*
 * Copyright 2020 - André Thiele, Benjamin Will
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package com.tellme.backend.util;

import com.google.firebase.auth.UserRecord;
import com.tellme.backend.model.AuthUser;

public class FirebaseUtil {

	public static AuthUser mapRecordToAuthUser(UserRecord userRecord) {
		String email = userRecord.getEmail();
		return AuthUser.builder().email(email).build();
	}
}
