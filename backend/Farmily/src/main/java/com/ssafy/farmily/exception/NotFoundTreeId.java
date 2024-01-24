package com.ssafy.farmily.exception;

import com.ssafy.farmily.common.Message;

public class NotFoundTreeId extends RuntimeException{
	public Message errorMessage() {
		Message message = new Message();
		// message.setStatus(StatusEnum.NOT_FOUND);
		message.setMessage("나무를 찾지 못했습니다.");
		message.setData(null);
		return message;
	}
}
