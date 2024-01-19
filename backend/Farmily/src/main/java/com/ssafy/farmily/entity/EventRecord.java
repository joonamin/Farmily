package com.ssafy.farmily.entity;

import java.util.List;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;

@Entity
@DiscriminatorValue("E")
@Getter
public class EventRecord extends Record {
	@OneToMany(mappedBy = "event")
	private List<ImageCard> imageCards;
}
