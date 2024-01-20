package com.ssafy.farmily.exception;

import org.springframework.http.HttpStatus;

import com.ssafy.farmily.common.Message;
import com.ssafy.farmily.common.StatusEnum;

import lombok.Getter;

@Getter
public class NotFoundFamilyId extends RuntimeException {
	public Message errorMessage() {
		Message message = new Message();
		message.setStatus(StatusEnum.NOT_FOUND);
		message.setMessage("가족을 찾지 못했습니다.");
		message.setData(null);
		return message;
	}
}
