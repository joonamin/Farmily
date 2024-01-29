package com.ssafy.farmily.validation.validator;

import com.ssafy.farmily.validation.annotation.InRectangle;
import com.ssafy.farmily.validation.annotation.NotInverted;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import utils.DateRange;
import utils.Position;

public class InRectangleValidator
	implements ConstraintValidator<InRectangle, Position>
{
	private int minRow;
	private int minCol;
	private int maxRow;
	private int maxCol;

	@Override
	public void initialize(InRectangle annotation) {
		this.minRow = annotation.minRow();
		this.minCol = annotation.minCol();
		this.maxRow = annotation.maxRow();
		this.maxCol = annotation.maxCol();
	}

	@Override
	public boolean isValid(Position position, ConstraintValidatorContext constraintValidatorContext) {
		int row = position.getRow();
		int col = position.getCol();
		return minRow <= row && row <= maxRow && minCol <= col && col <= maxCol;
	}
}
