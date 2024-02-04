package com.ssafy.farmily.type;

import lombok.Getter;

@Getter
public enum FileCategory {
	IMAGE("jpeg", "jpg", "png", "gif"),
	;

	private final String[] extensions;

	FileCategory(String... extensions) {
		this.extensions = extensions;
	}
}
