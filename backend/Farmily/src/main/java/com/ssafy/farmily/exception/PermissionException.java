package com.ssafy.farmily.exception;

import com.ssafy.farmily.common.Message;

public class PermissionException extends RuntimeException {
	public Message errorMessage() {
		Message message = new Message();
		// message.setStatus(StatusEnum.NOT_FOUND);
		message.setMessage("권한이 없습니다.");
		message.setData(null);
		return message;
	}
}
