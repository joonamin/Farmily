package com.ssafy.farmily.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@DiscriminatorValue("D")
@Getter
public class DailyRecord extends Record {

}
